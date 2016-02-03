package scripts.com.framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FrameworkUtility {

	private String projectName;
	private  Map<String, String> testData;
	public FrameworkAPI frameworkAPI;
	
	public FrameworkUtility(String projectName,FrameworkAPI frameworkAPI){
		this.projectName=projectName;
		this.frameworkAPI=frameworkAPI;
	}
	
	/**
	 * This method is used to get the date format in dd-MM-YY format.
	 * 
	 * 
	 * @return date in StringFormat
	 */
	public String getDateInDDMMYY()
	{
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy");
		String dateNow = formatter.format(currentDate.getTime());
		return dateNow;
	}
	
	/**
	 * This method is used to get the temp path
	 * @return tempPath
	 */

	public String getTempPath() {
		try{
			String cananicalpath=File.createTempFile("temp_file", "tmp").getCanonicalPath();		
			int s=cananicalpath.lastIndexOf("\\");			
			return cananicalpath.substring(0,s+1);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * This method encodes special characters in given string
	 * 
	 * @param inpParameter
	 * @return String encoded string
	 */
	public String encodeSpecialCharacters(String inpParameter) {
		inpParameter = inpParameter.replace("&", "&amp;");//&amp;
		inpParameter = inpParameter.replace("<", "&lt;");
		inpParameter = inpParameter.replace(">", "&gt;");
		inpParameter = inpParameter.replace("%3C", "&lt;");
		inpParameter = inpParameter.replace("%3E", "&gt;");
		return inpParameter;
	}
	
	/**
	 * This method returns Current Date and time in String format
	 * @return String value current data and time
	 */
	public  String getCurrentDateNTime() {		
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
		String dateNow = formatter.format(currentDate.getTime());		
		return dateNow;
	}
	
	/**
	 * This method returns current date and time as String according to given
	 * date format.
	 * 
	 * @return String Date
	 */
	public  String getExecutionTime() {
		return now("yyyy.MM.dd  'at' hh:mm:ss ");
	}
	

	/**
	 * This method takes date format and returns current date and time as String
	 * according to given date format.
	 * @param dateFormat
	 * @return String Date parsed in given date format
	 */
	public  String now(String dateFormat) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(cal.getTime());
	}
	
	/**
	 * This method returns value corresponding to given excel column(key) from
	 * Hashmap containing TestData
	 * 
	 * @param sTargetValue
	 *            excel column heading, we call key
	 * @return String containing value of a given key
	 * */
	public  String getExcelData(String sTargetValue) {	
		
		return frameworkAPI.readDataFromExcel(sTargetValue);
	}
	
	/**
	 * This method change the string as 
	 */
	public String encryptPassword(String pwd){
		int len = pwd.length();
		String cryPwd="";
		for(int i=0;i<len;i++){
			cryPwd=cryPwd+"*";

		}
		return cryPwd;
	}
	
	/**
	 * This method returns given number of spaces as a string
	 * 
	 * @param noofSpaces
	 *            number of spaces required
	 * @return String string of spaces
	 */
	public  String addspace(int noofSpaces) {
		String space = "";
		for (int i = 0; i < noofSpaces; i++) {
			space = space + (char) 32;
		}
		return space;
	}
	
	/**
	 * This method appends Current Date and time to value retrieved from excel
	 * sheet.
	 * 
	 * @param excelInputColName
	 *            Column name specified in excel sheet
	 * @return String value updated with current data and time
	 */
	public  String appendCurrentDateNTime(String excelInputColName) {
		String value = getExcelData(excelInputColName);
		if (!value.toLowerCase().startsWith("r_"))
			return value;
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
		String dateNow = formatter.format(currentDate.getTime());
		value = value.substring(value.indexOf("_") + 1);
		value = value + dateNow;
		return value;
	}

	/**
	 * This method first checks whether email ID is valid or not by calling
	 * isEmailIDValid() method and then appends the current date and time.
	 * 
	 * @param excelInputColName
	 *            Column name specified in excel sheet
	 * @return String EmailID updated with current data and time
	 */
	public  String appendCurrentDateNTimeToEmail(String excelInputColName) {
		String value = "";
		String email = getExcelData(excelInputColName);
		if (!email.toLowerCase().startsWith("r_"))
			return email;
		if (!isEmailIDValid(email)) {
			frameworkAPI.commandName.add("EmailValidation");
			frameworkAPI.reportStepDetails("Validating Email ID",
					"Invalid Email ID.Please specify valid Email ID", frameworkAPI.FAIL);
			// stopSelenium();
		}
		email = email.substring(email.indexOf("_") + 1);
		value = email.substring(0, (email.indexOf("@")));
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyHHmmss");
		String dateNow = formatter.format(currentDate.getTime());
		value = value + dateNow + email.substring(email.indexOf("@"));
		return value;
	}
	
	/**
	 * This method is to check given email is valid or not.
	 * 
	 * @param email
	 * @return true if email id is valid, otherwise false
	 */
	public  boolean isEmailIDValid(String email) {
		
		// Set the email pattern string
		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
		
		// Match the given string with the pattern
		Matcher m = p.matcher(email);
		
		// check whether match is found
		boolean matchFound = m.matches();
		return matchFound;
	}

	/**
	 * This method sorts and returns an array of float values.
	 * 
	 * @param arr
	 *            Array to sort
	 * @return Sorted array of floats
	 */
	public  float[] bubbleSort(float arr[]) {
		int i, j, arrLength;
		float temp;
		arrLength = arr.length;
		for (i = 0; i < arrLength; i++) {
			for (j = 1; j < (arrLength - i); j++) {
				if (arr[j - 1] > arr[j]) {
					temp = arr[j - 1];
					arr[j - 1] = arr[j];
					arr[j] = temp;
				}
			}
		}
		return arr;
	}
	
	/**
	 * This method gets array of strings and converts them into float array and
	 * returns the array of float values.
	 * 
	 * @param sarray
	 *            String array to be converted to float
	 * @return array of floats
	 */
	public  float[] convertStringArraytoFloatArray(String[] sArray)
			throws Exception {
		if (sArray != null) {
			float fArray[] = new float[sArray.length];
			for (int i = 0; i < sArray.length; i++) {
				fArray[i] = Float.parseFloat(sArray[i]);
			}
			return fArray;
		}
		return null;
	}

	
	/**
	 * This method takes String and converts into required date format
	 * 
	 * @param strDate
	 *            Input string
	 * @param reqDtFormat
	 *            Date format
	 * @return String Returns string converted to specified date format
	 */
	public  String convertStringToRequiredDateFormat(String strDate,
			String reqDtFormat) {
		String datestring2;
		try {
			Date date1 = new SimpleDateFormat(reqDtFormat).parse(strDate);
			datestring2 = new SimpleDateFormat("dd.MM.yyyy HH:mm")
			.format(date1);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return datestring2;
	}
	
	/**
	 * This method returns required currency format in the form of String.
	 * 
	 * @param price
	 *            Input price to format
	 * @return String updated price
	 */
	public  String Currencyformat(String price) {
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		return nf.format(Integer.parseInt(price));
	}

	
	/**
	 * This method returns the price with US format.
	 * 
	 * @param price
	 * @return String formatted price
	 */
	public String formatPrice(String price) {
		if (price.startsWith("$"))
			price = price.substring(1);

		NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.US);
		formatter.setMaximumFractionDigits(2);
		return formatter.format(Double.parseDouble(price));
	} 
	
	/**
	 * This method returns the price with UK format.
	 * 
	 * @param price
	 * @return String formatted price
	 */
	public  String formatUKPrice(String price) {
		if (price.startsWith("\uFFFD"))
			price = price.substring(1);

		NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.UK);
		formatter.setMaximumFractionDigits(2);
		return formatter.format(Double.parseDouble(price));
	}
	
	/**
	 * This method converts String into float.
	 * 
	 * @param str
	 *            String containing both characters and digits
	 * @return float value
	 */
	public  float getfloat(String str) {
		if (str == null) {
			return 0;
		}
		StringBuffer strBuff = new StringBuffer();
		char c;

		for (int i = 0; i < str.length(); i++) {
			c = str.charAt(i);

			if (Character.isDigit(c) || c == 46) {
				strBuff.append(c);
			}
		}
		return Float.valueOf(strBuff.toString().trim()).floatValue();
	}
	
	/**
	 * This method reads data from TestData excel sheet and stores in testdata
	 * Hashmap
	 */
	/**
	 * Description: Reads all data from current test script related data sheet (If Exist) and returns a map (all headings as keys & data as values of the respected key) 
	 * Deals with both HSSFWorkbook & XSSFWorkbook
	 * @return Map<String, String>
	 */
	public  HashMap<String,String> readDataFromExcel() {
		String sInputDataFilePath = frameworkAPI.automationPath + "Data"+ File.separator + frameworkAPI.projectName + File.separator +"scripts" + File.separator + frameworkAPI.moduleName;

		HashMap<String,String> namelist = new HashMap<String,String>();
		FileInputStream inputStream = null;
		try {			
			File dir = new File(sInputDataFilePath);
			String dataFilePath = null;
			if(dir.exists() && dir.isDirectory()) {
				boolean flag = false;
				File[] allFiles = dir.listFiles();

				if(allFiles.length > 0) {
					for(int i=0; i<allFiles.length; i++) {
						String fileName = allFiles[i].getAbsolutePath();

						if(FilenameUtils.getBaseName(fileName).equals(frameworkAPI.testScriptName)) {
							dataFilePath = fileName;
							flag = true;
							break;
						}					
					}
				} else {
					System.err.println("No Data Files Are Available In The Path: " +  sInputDataFilePath);
				}

				if(!flag) {
					System.err.println("Data File Is Not Available In \"" + sInputDataFilePath +"\" For The Class: \"" +  frameworkAPI.testScriptName + "\"");
				}
			} else {
				System.err.println("Please Verify The Specified Class:"+ frameworkAPI.testScriptName +" Packages : " + sInputDataFilePath);
			}			

			if (new File(dataFilePath).exists()) {

				Workbook workbook = null;
				Sheet worksheet = null;

				inputStream = new FileInputStream(dataFilePath);

				if(FilenameUtils.getExtension(dataFilePath).equalsIgnoreCase("xls")) {
					workbook = new HSSFWorkbook(inputStream);
				} else if(FilenameUtils.getExtension(dataFilePath).equalsIgnoreCase("xlsx")) {
					workbook = new XSSFWorkbook(inputStream);
				} else {
					System.err.println("\n\nPlease Provide Either \".xls\" or \".xlsx\" Excel File\n\n");
				}

				worksheet = workbook.getSheetAt(0);

				if(worksheet.getLastRowNum() >= 1) {

					Row keysRow = worksheet.getRow(0);					
					Row valuesRow = worksheet.getRow(1);

					int cellCount = keysRow.getLastCellNum();

					for(int i=0; i<cellCount; i++) {

						Cell cell = keysRow.getCell(i);
						if(cell==null) {
							continue;
						}
						
						String key = cell.getStringCellValue();
						String value = "";

						Cell valCell = valuesRow.getCell(i);
						if(valCell == null) {
							value = "";
						} else {
							switch (valCell.getCellType()) {
							case 0:  
								value = "" + Double.valueOf(valCell.getNumericCellValue()).longValue();
								break;  
							case 1:  
								value = "" + valCell.getStringCellValue();  
								break;  
							case 2:                               
								break;
							}
						}
						namelist.put(key, value);
					}


				} else {
					System.err.println("Please Provide Data For The Data File: " + dataFilePath);
				}					
			} else {
				frameworkAPI.commandName.add("readDataFromExcel");
				frameworkAPI.reportStepDetails("Retrieving TestData",
						"TestData sheet doesn't exists under path \""
								+  sInputDataFilePath + "\"", frameworkAPI.FAIL);
			}
		} catch (Exception e) {
			e.printStackTrace(System.out);
		} finally {
			try {
				if(inputStream!=null){ inputStream.close(); }
			} catch (IOException e) {
				System.out.println("" + e);
			}
		}
		return namelist;
	}
	
	/**
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public  String getDateTimeDifference(Date startDate, Date endDate)
	{
		String time = "";
		try {
			long diff = endDate.getTime() - startDate.getTime();
			long diffSeconds = diff / 1000L % 60L;
			long diffMinutes = diff / 60000L % 60L;
			long diffHours = diff / 3600000L % 24L;

			if (diffHours < 10L)
				time = time + "0" + diffHours + "h:";
			else {
				time = time + diffHours + "h:";
			}
			if (diffMinutes < 10L)
				time = time + "0" + diffMinutes + "m:";
			else {
				time = time + diffMinutes + "m:";
			}
			if (diffSeconds < 10L)
				time = time + "0" + diffSeconds+"s";
			else
				time = time + diffSeconds+"s";
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return time;
	}
	
	public  String getTime(Date startDate,Date endDate){
		String time="";
		try{
			long diff=endDate.getTime()-startDate.getTime();
			long diffSeconds = diff / 1000L % 60L;
			long diffMinutes = diff / 60000L % 60L;
			long diffHours = diff / 3600000L % 24L;
			if (diffHours>0L)
				time = time + diffHours + "h ";		
			if ( diffMinutes>0L)
				time = time + diffMinutes + "m ";
			if (diffSeconds >0L)
				time = time + diffSeconds+"s";
		}
		catch(Exception e){
			e.printStackTrace(); 
		}
		return time;
	}
	
	/**
	 * This method rounds a value to the specified number of decimal places.
	 * 
	 * @param Rval
	 *            a floating-point value to be rounded.
	 * @param Rp1
	 *            the number of decimal places in the return value.
	 * @return float Rounded float value
	 */
	public  float round(float Rval, int Rpl) {
		float p = (float) Math.pow(10, Rpl);
		Rval = Rval * p;
		float tmp = Math.round(Rval);
		return tmp / p;
	}
	
	/**
	 * 
	 * @param dbPath
	 * @return
	 */
	public Connection getConnectionObj(String dbPath){
		Connection conn = null;
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			String dbName;	     
			if (dbPath.contains("\\")) {
				dbName = dbPath + ".accdb";
			}
			else {
				dbName = frameworkAPI.automationPath+ "Data"+File.separator+frameworkAPI.projectName+ File.separator + dbPath + ".accdb";
			}
			System.out.println("Database Name: " + dbName);	     
			String database = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb, *.accdb)};Dbq=" + dbName;
			conn = DriverManager.getConnection(database);
			if(conn!=null){
				System.out.println("connection established");
			}
			else {
				System.err.println("connection not established");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	/**
	 * 
	 * @param dbName
	 * @param sSQL
	 * @return
	 */
	public HashMap<String,String> readDataFromAccessDB(String dbPath, String sSQL)
	{
		Connection conn = getConnectionObj(dbPath);
		HashMap<String,String> namelist = new HashMap<String,String>();
		Statement smt = null;
		try
		{
			smt = conn.createStatement();
			smt.execute(sSQL);
			ResultSet rs = smt.getResultSet();
			ResultSetMetaData rsmd = rs.getMetaData();
			rs.next();
			for (int i = 1; i <= rsmd.getColumnCount(); i++)
			{
				String sColName = rsmd.getColumnName(i);
				String sColVal = rs.getString(i);
				namelist.put(sColName, sColVal);
			}
			smt.close();
			conn.close();
		}
		catch (SQLException e)
		{
			frameworkAPI.commandName.add("Exception");
			frameworkAPI.reportStepDetails("Data not available", "Please check the availability of test data in the DB:"+ e.toString(), "Fail");
			e.printStackTrace();
		}
		catch (Exception e) {
			frameworkAPI.commandName.add("Exception");
			frameworkAPI.reportStepDetails(e.toString(), e.toString(), "Fail");
			e.printStackTrace();
		}

		return namelist;
	}
	
	/**
	 * 
	 * @param sSQL
	 * @return
	 */
	public HashMap<String,String>  readDataFromAccessDB(String sSQL)
	{
		String dbPath = "MYVMWare";
		Connection conn = getConnectionObj(dbPath);
		HashMap<String,String>  namelist = new HashMap<String,String> ();
		Statement smt = null;
		try
		{
			smt = conn.createStatement();
			smt.execute(sSQL);
			ResultSet rs = smt.getResultSet();
			ResultSetMetaData rsmd = rs.getMetaData();
			rs.next();
			for (int i = 1; i <= rsmd.getColumnCount(); i++)
			{
				String sColName = rsmd.getColumnName(i);
				String sColVal = rs.getString(i);
				namelist.put(sColName, sColVal);
			}
			smt.close();
			conn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return namelist;
	}

	public int readRowCountFromAccessDB(String sSQL)
	{
		String dbPath = "MYVMWare";
		Connection conn = getConnectionObj(dbPath);
		Statement smt = null;
		int rowCount=0;
		try
		{
			smt = conn.createStatement();
			smt.execute(sSQL);
			ResultSet rs = smt.getResultSet();

			while(rs.next())
			{
				rowCount=rs.getInt(1);		
			}	
			smt.close();
			conn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return rowCount;
	}

	public int readRowCountFromAccessDB(String dbPath,String sSQL)
	{
		Connection conn = getConnectionObj(dbPath);
		Statement smt = null;
		int rowCount=0;
		try
		{
			smt = conn.createStatement();
			smt.execute(sSQL);
			ResultSet rs = smt.getResultSet();

			while(rs.next())
			{
				rowCount=rs.getInt(1);		
			}	
			smt.close();
			conn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return rowCount;
	}
	
	/**
	 * 
	 * @param dbName
	 * @param sSQL
	 * @return
	 */
	public boolean insertDataIntoAccessDB(String dbName, String sSQL){
		Connection conn = getConnectionObj(dbName);
		Statement smt = null;
		boolean rs = false;
		try {
			smt = conn.createStatement();
			rs = smt.execute(sSQL);
			smt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
	/**
	 * 
	 * @param sSQL
	 * @return
	 */
	public boolean insertDataIntoAccessDB(String sSQL)
	{
		String dbPath = "MYVMWare";
		Connection conn = getConnectionObj(dbPath);
		Statement smt = null;
		boolean rs = false;
		try {
			smt = conn.createStatement();
			rs = smt.execute(sSQL);
			smt.close();
			conn.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	/**
	 * 
	 * @param sbrowser
	 * @return
	 */
	public static String[] splitString(String sbrowser)
	{
		String regex = " ";
		String[] p = sbrowser.split(regex);
		return p;
	}
	/**
	 * 
	 * @param hashMapItems
	 * @param currentScript
	 * @return
	 */
	public String[] getEnvironment(String hashMapItems, String currentScript) {
		try { 		  
			if(hashMapItems.contains("{")||hashMapItems.contains("}")){

				hashMapItems=hashMapItems.replace("{", "").replace("}", "");				
			}
			String[] moduleName = new String[5];
			if (currentScript.contains(".")) {
				moduleName = currentScript.split("\\.");	       
			}	
			String requiredtcAndOsAndBrowser = "";
			if(hashMapItems.contains(",")){
				String[] tcAndOsAndBrowser = hashMapItems.split(",");
				for (int i = 0; i < tcAndOsAndBrowser.length; i++) {	   
					if ((tcAndOsAndBrowser[i].contains(currentScript)) || (tcAndOsAndBrowser[i].contains(moduleName[(moduleName.length - 1)]))) {						
						requiredtcAndOsAndBrowser = tcAndOsAndBrowser[i];						
					}
				}	    
			}else{
				requiredtcAndOsAndBrowser=hashMapItems;
			}

			String osAndBrowser = requiredtcAndOsAndBrowser.split("=")[1];	      
			String[] envrnment = new String[2];
			String os = osAndBrowser.split("\\|\\|")[0];
			String browser = osAndBrowser.split("\\|\\|")[1];

			envrnment[0]=os;
			envrnment[1]=browser;

			return envrnment;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;    	
	}
	
	public String getSauceuserAccessKey(String sauceuser){
		try{
			String sauceUserAccessKey="";
			Connection conn = getConnectionObj("TestSets");
			Statement smt = conn.createStatement();	
			String sSQL="select SauceUserAccessKey from InterfaceData where SauceUser='"+sauceuser+"'";
			smt.execute(sSQL);
			ResultSet rs = smt.getResultSet();
			while(rs.next())
			{
				sauceUserAccessKey=rs.getString(1);		
			}	
			return sauceUserAccessKey;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}



	public void FileCopy(String sourceFilePath, String desFilePath){
		try{
			File sourceFile = new File(sourceFilePath);

			File targetFile = new File(desFilePath);

			//copy file from one location to other
			FileUtils.copyFile(sourceFile, targetFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
