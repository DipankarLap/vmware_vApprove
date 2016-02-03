package scripts.com.concur;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.sikuli.script.Screen;
import org.testng.annotations.Test;

import scripts.com.utils.Xls_Reader;



public class concurportal {

	public static WebDriver driver;
	public static date dat;
	public static Xls_Reader xlsReader=new Xls_Reader("D:\\Workspace\\Mobile_Framework\\vApprove\\concurexcel\\Expense_test_data.xlsx");
	public static Xls_Reader xlsReader_output=new Xls_Reader("D:\\Workspace\\Mobile_Framework\\vApprove\\concurexcel\\Expense_output_data.xlsx");

	@Test
	public  void main() throws Exception {

		dat = new date();
		System.setProperty("webdriver.chrome.driver", "D:/Workspace/Mobile_Framework/Resources/chromedriver.exe");
		 driver = new ChromeDriver();
		//driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(20000, TimeUnit.SECONDS);


		driver.get("https://implementation.concursolutions.com/");
		driver.manage().window().maximize();
		driver.findElement(By.id("userid")).sendKeys(xlsReader.getCellData("Login", "UserName", 2));
		driver.findElement(By.id("password")).sendKeys(xlsReader.getCellData("Login", "Password", 2));
		driver.findElement(By.id("btnSubmit")).click();

		//clicking Expenses 
		//driver.findElement(By.linkText("Expense")).click();
		driver.findElement(By.className("icon-plus-2")).click();
		Thread.sleep(5000);

		for(int i=2;i<=xlsReader.getRowCount("TestCases");i++){
			if(xlsReader.getCellData("TestCases", "RunMode", i).equalsIgnoreCase("Y")){
				String testCase=xlsReader.getCellData("TestCases", "TSID", i);

				//Ticket name
				Method[] methods = concurportal.class.getMethods();

				for(Method method : methods){
					if(method.getName().equalsIgnoreCase(xlsReader.getCellData("TestCases", "TSID", i))){
						System.out.println(method.getName());
						method.invoke(this, testCase);
					}
				}

			}else{
				System.out.println("No test case to execute");
			}

		}
	}

	//Hardware over $3000
	public static void hardwareMore3000(String testCase) throws Exception{

		driver.manage().timeouts().implicitlyWait(20000, TimeUnit.SECONDS);

		//Create new report
		driver.findElement(By.xpath("//*[@id='GatewayHome_ReportTiles']/ul/li[1]/a/div")).click();
		Thread.sleep(3000);
		System.out.println("sheetdata=  "+xlsReader.getCellData(testCase, "TicketName", 2));
		driver.findElement(By.id("Report_1356_TRAVELER_ISNEW_Name")).sendKeys(xlsReader.getCellData(testCase, "TicketName", 2));//Report_2_TRAVELER_ISNEW_Name
		driver.findElement(By.id("Report_1356_TRAVELER_ISNEW_Purpose")).sendKeys(xlsReader.getCellData(testCase, "BusinessPurpose", 2));//Report_2_TRAVELER_ISNEW_Purpose
		Thread.sleep(4000);

		driver.findElement(By.xpath("//*[@id='ext-gen199']")).click();//ext-gen197
		Thread.sleep(5000);
		
		//click on new expense
		driver.findElement(By.cssSelector(".x-btn-text.menu_newexpense2")).click();

		Thread.sleep(2000);

		//Select Purchase of Hardware from the expense				
		driver.findElement(By.xpath("//div[contains(text(),'Purchase of Hardware')]")).click();
		Thread.sleep(3000);

		
		//Date
		driver.findElement(By.id("Expense_1205_TRAVELER_P1059_ADJAMT_HD_TransactionDate")).sendKeys(dat.getDate(0, "MM/dd/yyyy"));

		//Hardware type selecting Laptop
		driver.findElement(By.id("Expense_1205_TRAVELER_P1059_ADJAMT_HD_Custom17Name")).sendKeys(xlsReader.getCellData("hardwareMore3000", "HardwareType", 2));

		//Amount
		driver.findElement(By.id("Expense_1205_TRAVELER_P1059_ADJAMT_HD_TransactionAmount")).sendKeys(xlsReader.getCellData("hardwareMore3000", "Amount", 2));

		//comment
		driver.findElement(By.id("Expense_1205_TRAVELER_P1059_ADJAMT_HD_Comment")).sendKeys(xlsReader.getCellData("hardwareMore3000", "Comment", 2));

		attachFile();

		//Save
		driver.findElement(By.xpath("//button[contains(text(),'Save')]")).click();
		Thread.sleep(9000);
		//Submit Report
		driver.findElement(By.xpath("//button[contains(text(),'Submit Report')]")).click();
		Thread.sleep(9000);

		//Submit and Accept
		driver.findElement(By.xpath("//button[contains(text(),'Accept & Submit')]")).click();
		Thread.sleep(5000);

	
		//Submit report		
		driver.findElements(By.xpath("//button[contains(text(),'Submit Report')]")).get(1).click();
		Thread.sleep(5000);

		//Close popup
		driver.navigate().refresh();

		System.out.println("Expense created sucessfully");	
		dataExtraction(testCase,xlsReader.getCellData(testCase, "TicketName", 2));

	}

	//Hardware Purchase of Laptop
	public static void hardwareLaptop(String testCase) throws Exception{

		driver.manage().timeouts().implicitlyWait(20000, TimeUnit.SECONDS);

		//Create new report
		driver.findElement(By.xpath("//*[@id='GatewayHome_ReportTiles']/ul/li[1]/a/div")).click();
		Thread.sleep(3000);
		driver.findElement(By.id("Report_2_TRAVELER_ISNEW_Name")).sendKeys(xlsReader.getCellData(testCase, "TicketName", 2));
		driver.findElement(By.id("Report_2_TRAVELER_ISNEW_Purpose")).sendKeys(xlsReader.getCellData(testCase, "BusinessPurpose", 2));
		Thread.sleep(4000);

		driver.findElement(By.xpath("//*[@id='ext-gen199']")).click();
		Thread.sleep(5000);
		//click on new expense
		driver.findElement(By.cssSelector(".x-btn-text.menu_newexpense2")).click();
		Thread.sleep(2000);

		//Selecting Purchase of Hardware from the expense		
		driver.findElement(By.xpath("//div[contains(text(),'Purchase of Hardware')]")).click();
		Thread.sleep(3000);

		//Date
		driver.findElement(By.id("Expense_1205_TRAVELER_P1059_ADJAMT_HD_TransactionDate")).sendKeys(dat.getDate(0, "MM/dd/yyyy"));

		//Hardware type selecting Laptop
		driver.findElement(By.id("Expense_1205_TRAVELER_P1059_ADJAMT_HD_Custom17Name")).sendKeys(xlsReader.getCellData("HardwareLaptop", "HardwareType", 2));

		//Amount
		driver.findElement(By.id("Expense_1205_TRAVELER_P1059_ADJAMT_HD_TransactionAmount")).sendKeys(xlsReader.getCellData("HardwareLaptop", "Amount", 2));
		
		//comment
		driver.findElement(By.id("Expense_1205_TRAVELER_P1059_ADJAMT_HD_Comment")).sendKeys(xlsReader.getCellData("HardwareLaptop", "Comment", 2));

		attachFile();

		//Save
		driver.findElement(By.xpath("//button[contains(text(),'Save')]")).click();
		Thread.sleep(3000);
		//Submit Report
		driver.findElement(By.xpath("//button[contains(text(),'Submit Report')]")).click();
		Thread.sleep(3000);

		//Submit and Accept
		driver.findElement(By.xpath("//button[contains(text(),'Accept & Submit')]")).click();
		Thread.sleep(10000);

		//Submit report
		driver.findElements(By.xpath("//button[contains(text(),'Submit Report')]")).get(1).click();
		Thread.sleep(9000);

		//Close popup
		driver.navigate().refresh();
		Thread.sleep(5000);
		System.out.println("Expense created sucessfully");	

		dataExtraction(testCase,xlsReader.getCellData(testCase, "TicketName", 2));


	}

//Promotions
	public static void promotions(String testCase) throws Exception{

		driver.manage().timeouts().implicitlyWait(20000, TimeUnit.SECONDS);

		//Create new report
		driver.findElement(By.xpath("//*[@id='GatewayHome_ReportTiles']/ul/li[1]/a/div")).click();
		Thread.sleep(3000);
		driver.findElement(By.id("Report_2_TRAVELER_ISNEW_Name")).sendKeys(xlsReader.getCellData(testCase, "TicketName", 2));
		driver.findElement(By.id("Report_2_TRAVELER_ISNEW_Purpose")).sendKeys(xlsReader.getCellData(testCase, "BusinessPurpose", 2));
		Thread.sleep(4000);

		driver.findElement(By.xpath("//*[@id='ext-gen199']")).click();
		Thread.sleep(5000);
		//click on new expense
		driver.findElement(By.cssSelector(".x-btn-text.menu_newexpense2")).click();
		Thread.sleep(2000);
		
		//Selecting promotions from the expense		
		driver.findElement(By.xpath("//div[contains(text(),'Promotions and Giveaways (non employee)')]")).click();
		Thread.sleep(3000);
		//Date
		driver.findElement(By.id("Expense_1196_TRAVELER_P1059_ADJAMT_HD_TransactionDate")).sendKeys(dat.getDate(0, "MM/dd/yyyy"));
		Thread.sleep(3000);	

		//Enter Vendor Name 
		driver.findElement(By.id("Expense_1196_TRAVELER_P1059_ADJAMT_HD_VendorDescription")).sendKeys(xlsReader.getCellData("promotions", "EnterVendorName", 2));

		//Amount 
		driver.findElement(By.id("Expense_1196_TRAVELER_P1059_ADJAMT_HD_TransactionAmount")).sendKeys(xlsReader.getCellData("promotions", "Amount", 2));

		//Comment
		driver.findElement(By.id("Expense_1196_TRAVELER_P1059_ADJAMT_HD_Comment")).sendKeys(xlsReader.getCellData("promotions", "Comment", 2));

		//Adding Recipients
		driver.findElement(By.id("Expense_1196_TRAVELER_P1059_ADJAMT_HD_Attendees_AtnGrid_AddAtnComboId")).clear();
		driver.findElement(By.id("Expense_1196_TRAVELER_P1059_ADJAMT_HD_Attendees_AtnGrid_AddAtnComboId")).sendKeys("deswal");
		driver.findElement(By.xpath("//div[contains(text(),'Deswal, Saurabh')]")).click();
		Thread.sleep(3000);

		//Select checkbox to Add recipients
		driver.findElement(By.xpath("//a[contains(text(),'Deswal, Saurabh')]")).click();
		Thread.sleep(4000);
		
		//click Cancel on popup
		driver.findElements(By.xpath("//button[contains(text(),'Cancel')]")).get(2).click();
		Thread.sleep(4000);
		
		//Save
		driver.findElement(By.xpath("//button[contains(text(),'Save')]")).click();
	  	Thread.sleep(3000);
	  	
	  //Submit Report
	  	driver.findElement(By.xpath("//button[contains(text(),'Submit Report')]")).click();
	  	Thread.sleep(3000);

	  	//Submit and Accept
	  	driver.findElement(By.xpath("//button[contains(text(),'Accept & Submit')]")).click();
	  	Thread.sleep(10000);

	  	driver.findElement(By.cssSelector(".x-btn-text.btn_af_insert_above")).click();
	  	driver.findElement(By.id("Step10")).clear();
	  	driver.findElement(By.id("Step10")).sendKeys("deswal");
	  	Thread.sleep(4000);
	  	driver.findElement(By.xpath("//div[contains(text(),'sdeswalxxx@vmware.com')]")).click();

	  	//Submit report
	  	driver.findElements(By.xpath("//button[contains(text(),'Submit Report')]")).get(1).click();
	  	Thread.sleep(3000);

	  	//Close popup
	  	driver.navigate().refresh();
	  	System.out.println("Expense created sucessfully");	
	  	dataExtraction(testCase,xlsReader.getCellData(testCase, "TicketName", 2));

	}
	
	//Gift
	public static void gift(String testCase) throws Exception{
		
	}

//Ninety days older
	public static void ninetyDaysOlder(String testCase) throws Exception{

		driver.manage().timeouts().implicitlyWait(20000, TimeUnit.SECONDS);

		//Create new report
		driver.findElement(By.xpath("//*[@id='GatewayHome_ReportTiles']/ul/li[1]/a/div")).click();
		Thread.sleep(3000);
		driver.findElement(By.id("Report_2_TRAVELER_ISNEW_Name")).sendKeys(xlsReader.getCellData(testCase, "TicketName", 2));
		driver.findElement(By.id("Report_2_TRAVELER_ISNEW_Purpose")).sendKeys(xlsReader.getCellData(testCase, "BusinessPurpose", 2));
		Thread.sleep(4000);

		driver.findElement(By.xpath("//*[@id='ext-gen199']")).click();
		Thread.sleep(5000);
		
		//click on new expense
		driver.findElement(By.cssSelector(".x-btn-text.menu_newexpense2")).click();
		Thread.sleep(2000);
		
		//Selecting Others from the expense		
		driver.findElement(By.xpath("//div[contains(text(),'Other')]")).click();
		Thread.sleep(3000);
		//Entering the date
		driver.findElement(By.id("Expense_1249_TRAVELER_P1059_ADJAMT_HD_TransactionDate")).sendKeys(dat.getDate(-90, "MM/dd/yyyy"));
		Thread.sleep(3000);
		//Business purpose
		driver.findElement(By.id("Expense_1249_TRAVELER_P1059_ADJAMT_HD_Description")).sendKeys(xlsReader.getCellData("ninetyDaysOlder", "BusinessPurpose", 2));
		//Enter Vendor Name 
		driver.findElement(By.id("Expense_1249_TRAVELER_P1059_ADJAMT_HD_VendorDescription")).sendKeys(xlsReader.getCellData("ninetyDaysOlder", "EnterVendorName", 2));
		//Amount
		driver.findElement(By.id("Expense_1249_TRAVELER_P1059_ADJAMT_HD_TransactionAmount")).sendKeys(xlsReader.getCellData("ninetyDaysOlder", "Amount", 2));
		//Comment
		driver.findElement(By.id("Expense_1249_TRAVELER_P1059_ADJAMT_HD_Comment")).sendKeys(xlsReader.getCellData("ninetyDaysOlder", "Comment", 2));
		//Save
		driver.findElement(By.xpath("//button[contains(text(),'Save')]")).click();
		Thread.sleep(3000);
		//Submit Report
		driver.findElement(By.xpath("//button[contains(text(),'Submit Report')]")).click();
		Thread.sleep(3000);

		//Submit and Accept
		driver.findElement(By.xpath("//button[contains(text(),'Accept & Submit')]")).click();
		Thread.sleep(10000);

		driver.findElement(By.cssSelector(".x-btn-text.btn_af_insert_above")).click();
	    driver.findElement(By.id("Step10")).clear();
	    driver.findElement(By.id("Step10")).sendKeys("deswal");
	    Thread.sleep(4000);
	    driver.findElement(By.xpath("//div[contains(text(),'Deswal, Saurabh')]")).click();

		//Submit report
		driver.findElements(By.xpath("//button[contains(text(),'Submit Report')]")).get(1).click();
		Thread.sleep(3000);

		//Close popup
		driver.navigate().refresh();

		System.out.println("Expense created sucessfully");	
		dataExtraction(testCase,xlsReader.getCellData(testCase, "TicketName", 2));
	}

//Other
	public static void other(String testCase) throws Exception{
		
		driver.manage().timeouts().implicitlyWait(20000, TimeUnit.SECONDS);

		//Create new report
		//driver.findElement(By.xpath("//*[@id='GatewayHome_ReportTiles']/ul/li[1]/a/div")).click();
		Thread.sleep(2000);
		//driver.findElement(By.id("Report_1356_TRAVELER_ISNEW_Name")).click();
		driver.findElement(By.id("Report_1356_TRAVELER_ISNEW_Name")).sendKeys(xlsReader.getCellData(testCase, "TicketName", 2));//Report_2_TRAVELER_ISNEW_Name
		driver.findElement(By.id("Report_1356_TRAVELER_ISNEW_Purpose")).click();
		driver.findElement(By.id("Report_1356_TRAVELER_ISNEW_Purpose")).sendKeys(xlsReader.getCellData(testCase, "BusinessPurpose", 2));//Report_2_TRAVELER_ISNEW_Purpose
		Thread.sleep(4000);

		driver.findElement(By.xpath("//*[@id='ext-gen199']")).click();
		Thread.sleep(4000);

		//click on new expense
		driver.findElement(By.cssSelector(".x-btn-text.menu_newexpense2")).click();
		Thread.sleep(3000);
		
		//Selecting Others from the expense		
		driver.findElement(By.xpath("//div[contains(text(),'Other')]")).click();
		Thread.sleep(3000);
		
		//Entering the date
		driver.findElement(By.id("Expense_1249_TRAVELER_P1059_ADJAMT_HD_TransactionDate")).sendKeys(dat.getDate(0, "MM/dd/yyyy"));
		Thread.sleep(3000);
		//Business purpose
		driver.findElement(By.id("Expense_1249_TRAVELER_P1059_ADJAMT_HD_Description")).sendKeys(xlsReader.getCellData("Other", "BusinessPurpose", 2));
		//Enter Vendor Name 
		driver.findElement(By.id("Expense_1249_TRAVELER_P1059_ADJAMT_HD_VendorDescription")).sendKeys(xlsReader.getCellData("Other", "EnterVendorName", 2));
		//Amount
		driver.findElement(By.id("Expense_1249_TRAVELER_P1059_ADJAMT_HD_TransactionAmount")).sendKeys(xlsReader.getCellData("Other", "Amount", 2));
		//Comment
		driver.findElement(By.id("Expense_1249_TRAVELER_P1059_ADJAMT_HD_Comment")).sendKeys(xlsReader.getCellData("Other", "Comment", 2));
		//Save
		driver.findElement(By.xpath("//button[contains(text(),'Save')]")).click();
		Thread.sleep(3000);
		//Submit Report
		driver.findElement(By.xpath("//button[contains(text(),'Submit Report')]")).click();
		Thread.sleep(3000);

		//Submit and Accept
		driver.findElement(By.xpath("//button[contains(text(),'Accept & Submit')]")).click();
		Thread.sleep(10000);

		driver.findElement(By.cssSelector(".x-btn-text.btn_af_insert_above")).click();
	    driver.findElement(By.id("Step10")).clear();
	    driver.findElement(By.id("Step10")).sendKeys("deswal");
	    Thread.sleep(4000);
	    driver.findElement(By.xpath("//div[contains(text(),'Deswal, Saurabh')]")).click();
	    
	    
		//Submit report
		driver.findElements(By.xpath("//button[contains(text(),'Submit Report')]")).get(1).click();
		Thread.sleep(3000);

		//Close popup
		driver.navigate().refresh();
		Thread.sleep(3000);
		System.out.println("Expense created sucessfully");		
		dataExtraction(testCase,xlsReader.getCellData(testCase, "TicketName", 2));
	}


//Hotel Over $1500
	public static void hotelOver1500(String testCase) throws InterruptedException, AWTException{

		driver.manage().timeouts().implicitlyWait(20000, TimeUnit.SECONDS);

		//Create new report
		driver.findElement(By.xpath("//*[@id='GatewayHome_ReportTiles']/ul/li[1]/a/div")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("Report_2_TRAVELER_ISNEW_Name")).sendKeys(xlsReader.getCellData(testCase, "TicketName", 2));
		driver.findElement(By.id("Report_2_TRAVELER_ISNEW_Purpose")).sendKeys(xlsReader.getCellData(testCase, "BusinessPurpose", 2));
		Thread.sleep(4000);
		driver.findElement(By.xpath("//*[@id='ext-gen197']")).click();
		Thread.sleep(4000);
		
		//click on new expense
		driver.findElement(By.cssSelector(".x-btn-text.menu_newexpense2")).click();
		Thread.sleep(2000);

		//Select lodging from new expense 
		driver.findElement(By.xpath("//div[contains(text(),'Lodging')][3]")).click();
		Thread.sleep(3000);
		driver.findElement(By.id("Expense_1234_TRAVELER_VenListKey7_P1059_ADJAMT_HD_TransactionDate")).sendKeys(dat.getDate(0, "MM/dd/yyyy"));
		Thread.sleep(3000);
		driver.findElement(By.id("Expense_1234_TRAVELER_VenListKey7_P1059_ADJAMT_HD_LocName")).sendKeys(xlsReader.getCellData("hotelOver1500", "CityofPurchase", 2));
		Thread.sleep(3000);
		driver.findElement(By.id("Expense_1234_TRAVELER_VenListKey7_P1059_ADJAMT_HD_VenLiName")).sendKeys(xlsReader.getCellData("hotelOver1500", "Vendor", 2));
		Thread.sleep(3000);
		driver.findElement(By.id("Expense_1234_TRAVELER_VenListKey7_P1059_ADJAMT_HD_TransactionAmount")).sendKeys(xlsReader.getCellData("hotelOver1500", "Amount", 2));
		Thread.sleep(3000);
		driver.findElement(By.id("Expense_1234_TRAVELER_VenListKey7_P1059_ADJAMT_HD_Comment")).sendKeys(xlsReader.getCellData("hotelOver1500", "Comment ", 2));
		Thread.sleep(3000);
		attachFile();

		//itemize
		driver.findElement(By.xpath("//button[contains(text(),'Itemize')]")).click();
		Thread.sleep(3000);
		//in-date
		driver.findElement(By.id("ItemzWizd_Htl_LODNG_15059_IN__checkInDate")).sendKeys(dat.getDate(-1, "MM/dd/yyyy"));
		Thread.sleep(3000);
		//room rent
		driver.findElement(By.id("ItemzWizd_Htl_LODNG_15059_IN__roomRate")).sendKeys(xlsReader.getCellData("hotelOver1500", "RoomRate", 2));
		Thread.sleep(3000);
		//save itemize
		driver.findElement(By.xpath("//button[contains(text(),'Save Itemizations')]")).click();
		Thread.sleep(3000);
		//Submit report
		driver.findElement(By.xpath("//button[contains(text(),'Submit Report')]")).click();
		Thread.sleep(3000);
		//Submit and Accept
		driver.findElement(By.xpath("//button[contains(text(),'Accept & Submit')]")).click();
		Thread.sleep(10000);
		driver.findElements(By.xpath("//button[contains(text(),'Submit Report')]")).get(1).click();
		Thread.sleep(3000);
		//Close popup
		driver.navigate().refresh();
		Thread.sleep(3000);
		System.out.println("Expense created sucessfully");
		dataExtraction(testCase,xlsReader.getCellData(testCase, "TicketName", 2));
	}
	
	//Airfare over $1500
	public static void airfareOver1500(String testCase) throws InterruptedException, AWTException{
		//Create new report
		driver.findElement(By.xpath("//*[@id='GatewayHome_ReportTiles']/ul/li[1]/a/div")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("Report_2_TRAVELER_ISNEW_Name")).sendKeys(xlsReader.getCellData(testCase, "TicketName", 2));
		driver.findElement(By.id("Report_2_TRAVELER_ISNEW_Purpose")).sendKeys(xlsReader.getCellData(testCase, "BusinessPurpose", 2));
		Thread.sleep(4000);
		driver.findElement(By.xpath("//*[@id='ext-gen197']")).click();
		Thread.sleep(4000);
		
		//click on new expense
		driver.findElement(By.cssSelector(".x-btn-text.menu_newexpense2")).click();
		Thread.sleep(5000);

		//Select airFare
		driver.findElement(By.id("etListSearchItem_0_0")).click();
		Thread.sleep(3000);

		driver.findElement(By.id("Expense_1164_TRAVELER_VenListKey1019_P1059_ADJAMT_HD_TransactionDate")).click();
		driver.findElement(By.id("Expense_1164_TRAVELER_VenListKey1019_P1059_ADJAMT_HD_TransactionDate")).click();
		driver.findElement(By.id("Expense_1164_TRAVELER_VenListKey1019_P1059_ADJAMT_HD_TransactionDate")).sendKeys(dat.getDate(0, "MM/dd/yyyy"));
		Thread.sleep(1000);

		driver.findElement(By.id("Expense_1164_TRAVELER_VenListKey1019_P1059_ADJAMT_HD_VenLiName")).click();
		driver.findElement(By.id("Expense_1164_TRAVELER_VenListKey1019_P1059_ADJAMT_HD_VenLiName")).click();
		driver.findElement(By.id("Expense_1164_TRAVELER_VenListKey1019_P1059_ADJAMT_HD_VenLiName")).sendKeys(xlsReader.getCellData("airfareOver1500", "Vendor", 2));
		Thread.sleep(1000);

		driver.findElement(By.id("Expense_1164_TRAVELER_VenListKey1019_P1059_ADJAMT_HD_Custom17Name")).click();
		driver.findElement(By.id("Expense_1164_TRAVELER_VenListKey1019_P1059_ADJAMT_HD_Custom17Name")).click();
		driver.findElement(By.id("Expense_1164_TRAVELER_VenListKey1019_P1059_ADJAMT_HD_Custom17Name")).sendKeys(xlsReader.getCellData("airfareOver1500", "ClassofService", 2));

		driver.findElement(By.id("Expense_1164_TRAVELER_VenListKey1019_P1059_ADJAMT_HD_LocName")).click();
		driver.findElement(By.id("Expense_1164_TRAVELER_VenListKey1019_P1059_ADJAMT_HD_LocName")).click();
		driver.findElement(By.id("Expense_1164_TRAVELER_VenListKey1019_P1059_ADJAMT_HD_LocName")).sendKeys(xlsReader.getCellData("airfareOver1500", "CityofPurchase", 2));

		driver.findElement(By.id("Expense_1164_TRAVELER_VenListKey1019_P1059_ADJAMT_HD_TransactionAmount")).click();
		driver.findElement(By.id("Expense_1164_TRAVELER_VenListKey1019_P1059_ADJAMT_HD_TransactionAmount")).clear();
		driver.findElement(By.id("Expense_1164_TRAVELER_VenListKey1019_P1059_ADJAMT_HD_TransactionAmount")).sendKeys(xlsReader.getCellData("airfareOver1500", "Amount", 2));
		driver.findElement(By.id("Expense_1164_TRAVELER_VenListKey1019_P1059_ADJAMT_HD_Comment")).click();
		driver.findElement(By.id("Expense_1164_TRAVELER_VenListKey1019_P1059_ADJAMT_HD_Comment")).click();
		driver.findElement(By.id("Expense_1164_TRAVELER_VenListKey1019_P1059_ADJAMT_HD_Comment")).clear();
		driver.findElement(By.id("Expense_1164_TRAVELER_VenListKey1019_P1059_ADJAMT_HD_Comment")).sendKeys(xlsReader.getCellData("airfareOver1500", "Comment", 2));
		Thread.sleep(5000);
		attachFile();

		//save
		driver.findElement(By.xpath("//button[contains(text(),'Save')]")).click();
		
		Thread.sleep(5000);
		//Submit report
		driver.findElement(By.xpath("//button[contains(text(),'Submit Report')]")).click();
		Thread.sleep(3000);
		//Submit and Accept
		driver.findElement(By.xpath("//button[contains(text(),'Accept & Submit')]")).click();
		Thread.sleep(10000);
		driver.findElements(By.xpath("//button[contains(text(),'Submit Report')]")).get(1).click();
		Thread.sleep(3000);
		//Close popup
		driver.navigate().refresh();
		Thread.sleep(3000);
		System.out.println("Expense created sucessfully");
		dataExtraction(testCase,xlsReader.getCellData(testCase, "TicketName", 2));

	}
	
//Attach the receipt 
	public static void attachFile() throws InterruptedException, AWTException{
		driver.manage().timeouts().implicitlyWait(20000, TimeUnit.SECONDS);
		//attach
		driver.findElement(By.xpath("//button[contains(text(),'Attach Receipt')]")).click();
		Thread.sleep(2000);
		driver.findElement(By.className("x-form-file")).click();

		// Specify the file location with extension
		StringSelection sel = new StringSelection("D:\\Workspace\\Mobile_Framework\\vApprove\\Data\\Chrysanthemum.jpg");

		// Copy to clipboard
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(sel,null);
		System.out.println("selection" +sel);

		// Wait for 5 seconds
		Thread.sleep(5000);

		// Create object of Robot class
		Robot robot = new Robot();
		Thread.sleep(2000);

		// Press Enter
		robot.keyPress(KeyEvent.VK_ENTER);

		// Release Enter
		robot.keyRelease(KeyEvent.VK_ENTER);

		// Press CTRL+V
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		Thread.sleep(9000);

		// Release CTRL+V
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_V);
		Thread.sleep(9000);

		//Press Enter 
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		Thread.sleep(9000);
		System.out.println("before attach");
		driver.findElements(By.xpath("//button[contains(text(),'Attach')]")).get(2).click();
		System.out.println("after attach");
		Thread.sleep(7000);
	}

	public static void dataExtraction(String testCase,String ticketName){
		List<WebElement> dashBoardSize=driver.findElements(By.xpath("//html/body/div[3]/div/div/div[1]/div/div/div/div[6]/div/div/div[1]/div[2]/div/div/ul/li"));
		System.out.println(dashBoardSize.size());
		System.out.println("ticketName is :::"+ticketName);
		for(int i=2;i<=dashBoardSize.size();i++){
			String tcName=driver.findElement(By.xpath("//html/body/div[3]/div/div/div[1]/div/div/div/div[6]/div/div/div[1]/div[2]/div/div/ul/li["+i+"]/a/div[2]/div[1]/h3")).getText();
			System.out.println("tcName ::"+tcName);
			xlsReader_output.setCellData(testCase, "TicketName", 2, tcName);
			if(tcName.equalsIgnoreCase(ticketName)){
				String amount1=driver.findElement(By.xpath("//html/body/div[3]/div/div/div[1]/div/div/div/div[6]/div/div/div[1]/div[2]/div/div/ul/li["+i+"]/a/div[2]/div[2]")).getText();
				String amount=amount1.split("INR")[1];
				System.out.println("amount is "+amount);
				xlsReader_output.setCellData(testCase, "Amount", 2, amount);
				List<WebElement> innerDivSize=driver.findElements(By.xpath("//html/body/div[3]/div/div/div[1]/div/div/div/div[6]/div/div/div[1]/div[2]/div/div/ul/li["+i+"]/a/div[2]/div[3]/div"));
				if(innerDivSize.size()==2){
					String exception=driver.findElement(By.xpath("//html/body/div[3]/div/div/div[1]/div/div/div/div[6]/div/div/div[1]/div[2]/div/div/ul/li["+i+"]/a/div[2]/div[3]/div[1]/div")).getText();
					String status1=driver.findElement(By.xpath("//html/body/div[3]/div/div/div[1]/div/div/div/div[6]/div/div/div[1]/div[2]/div/div/ul/li["+i+"]/a/div[2]/div[3]/div[2]")).getText();
					System.out.println("exception::"+exception+""+"status:::"+status1);
					String status = status1.replace("Deswal, Saurabh", "");					
					xlsReader_output.setCellData(testCase, "Status", 2, status);

				}else{
					String status1=driver.findElement(By.xpath("//html/body/div[3]/div/div/div[1]/div/div/div/div[6]/div/div/div[1]/div[2]/div/div/ul/li["+i+"]/a/div[2]/div[3]/div")).getText();
					System.out.println("status:::"+status1);
					String status = status1.replace("Deswal, Saurabh", "");		
					xlsReader_output.setCellData(testCase, "Status", 2, status);
					driver.quit();	
				}
				break;
			}
		}

	}

}
