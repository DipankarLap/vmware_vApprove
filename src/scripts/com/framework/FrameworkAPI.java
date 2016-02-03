package scripts.com.framework;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.thoughtworks.selenium.SeleneseTestBase;

public class FrameworkAPI extends SeleneseTestBase {

	public  final String FAIL = "Fail";
	public  final String PASS = "Pass";
	
	
	public String projectName;
	public String moduleName;
	public String testSetName;
	public String testScriptName;

	//Mobile Capabilities
	public String platformName;
	public String platformVersion;
	public String browserName="";
	public String deviceOrientation;
	public String deviceName;
	public String appiumVersion;
	public String deviceType="";
	
	//App capabilities
	public String appPath;
	public String appPackage;
	public String appActivity;


	public String currentUser=System.getProperty("user.name");
	public String sauceUser;
	public Hashtable<String,String> mobCapabilities;
	public AppiumDriver driver;
	public DesiredCapabilities capabilities;	
	public String automationPath=System.getenv("Automation_Path");
	public FrameworkUtility utility;
	public  int iStepCount = 1;   
	public String sResultFlag;
	private enumSeleniumSelenses enumCommand;
	public Date startTime;
	public Date endTime;
	public String timeDiff;
	public String duration;
	public  String sURL = "";
	public String instance="";
	public String executionTime="";
	public String accessKey="";
	

	// Reporting
	protected  ArrayList<String> dateTime = new ArrayList<String>();
	protected  ArrayList<String> description = new ArrayList<String>();
	protected  ArrayList<String> status = new ArrayList<String>();
	protected  ArrayList<String> verification = new ArrayList<String>();
	protected  ArrayList<String> commandName = new ArrayList<String>();

	private enum enumSeleniumSelenses {
		implicitWait,isElementInVisible,assertValue, assertSubText, assertText, assertTextPresent, assertTitle, assertConfirmation, assertAlert, clickAndWait, clickSpecifiedLink, compareURLs, compare, captureEntirePageScreenshot, click, closeBrowser, deleteAllVisibleCookies, eOpenWindow, eClickAndWait, getXpathCount, getSelectedLabel, getCookieByName, getText, getAttribute, getURL,  getTitle, goBack, getSelectOptions, getAllWindowNames, getValue, isSpecifiedOptionChecked, isChecked, isElementPresent, openURL, openWindow,  sendReport, select, sendReportWithOutExit, selectWindow, waitForFrameAndSelect,  typeSpecifiedText, type, typeEmpty, verifyElementPresent, verifyValue, verifySubText, verifyTextPresent, verifyText, verifyElement, waitForElementPresent, waitForTextPresent, waitForPopUp, windowMaximize, waitUntilElementVisible, waitForAlertPresent, waitForValue, waitForPageToLoad, eOpen, verifyTitle, verifyConfirmation, verifyAlert, typePassword, selectSpecifiedOption, selectFrame,clickSpecifiedLinkNWait,clickAndWaitForElementPresent
	}
	
	/**
	 * This method checks whether given string is enum or not if the given
	 * string is enum then this method returns enum type of that string.
	 * @param sCommand,  Command specified in Test Script
	 * @return enumSeleniumSelenses returns enum constant
	 */
	private enumSeleniumSelenses checkEnumConstant(String sCommand) {
		try {
			enumCommand = enumSeleniumSelenses.valueOf(sCommand);
			return enumCommand;
		} catch (Exception e) {
			e.printStackTrace();
			reportUnknowSeleniumCmdErr(sCommand);
		}
		return null;
	}
	
	/**
	 * constructor with following list of parameters
	 * @param projectName
	 * @param moduleName
	 * @param testSetName
	 * @param testScriptName
	 * @param sauceUser
	 * @param mobCapabilities
	 */
	public FrameworkAPI(String projectName,String moduleName,String testSetName,String testScriptName,String instanceName,String sauceUser,Hashtable<String,String> mobCapabilities){

		browserName = "";
		currentUser = java.lang.System.getProperty("user.name");
		automationPath = java.lang.System.getenv("Automation_Path");
		iStepCount = 1;
		sResultFlag = "Pass";
		sURL = "";
		instance = "";
		executionTime = "";
		this.accessKey = "";
		dateTime = new ArrayList();
		description = new ArrayList();
		status = new ArrayList();
		verification = new ArrayList();
		commandName = new ArrayList();
		this.projectName = projectName;
		this.moduleName = moduleName;
		this.testSetName = testSetName;
		this.testScriptName = testScriptName;
		this.sauceUser = sauceUser;
		this.mobCapabilities = mobCapabilities;
		instance = instanceName;
		currentUser = java.lang.System.getProperty("user.name");

		
		mobileCapabilities(mobCapabilities);
		utility = new FrameworkUtility(projectName,this);
		this.executionTime=utility.getCurrentDateNTime();
		try{
			if(this.sauceUser.equalsIgnoreCase("Local"))
				invokeAppLocally(false);
			else {		
				AccessDBMethods obj= new AccessDBMethods(projectName);	
				String accessKey=obj.getSauceuserAccessKey(this.sauceUser);
				
				System.out.println("Sauce Username: "+this.sauceUser+ "\n"+"AccessKey: "+accessKey);
				invokeAppInSauce(false);	
			}			
		}catch (Exception e) {

			e.printStackTrace();
			reportStepDtlsWithScreenshot("FrameworkAPI Object Initialization ","Thrown an error : "+ utility.encodeSpecialCharacters(e.toString()), FAIL);			
		}
	}
	
	/**
	 * Constructor with following list of parameters
	 * @param projectName
	 * @param moduleName
	 * @param testSetName
	 * @param testScriptName
	 * @param sauceUser
	 * @param mobCapabilities
	 * @param appPath
	 * @param appPackage
	 * @param appActivity
	 */
	public FrameworkAPI(String projectName,String moduleName,String testSetName,String testScriptName,String sauceUser,String accessKey,Hashtable<String,String> mobCapabilities,String appPath,String appPackage,String appActivity){

		browserName = "";
		currentUser = java.lang.System.getProperty("user.name");
		automationPath = java.lang.System.getenv("Automation_Path");
		iStepCount = 1;
		sResultFlag = "Pass";
		sURL = "";
		instance = "";
		executionTime = "";
		this.accessKey = "";
		dateTime = new ArrayList();
		description = new ArrayList();
		status = new ArrayList();
		verification = new ArrayList();
		commandName = new ArrayList();
		this.projectName = projectName;
		this.moduleName = moduleName;
		this.testSetName = testSetName;
		this.testScriptName = testScriptName;
		this.sauceUser = sauceUser;
		this.mobCapabilities = mobCapabilities;
		this.appPath = appPath;
		this.appPackage = appPackage;
		this.appActivity = appActivity;
		
		mobileCapabilities(mobCapabilities);
		utility=new FrameworkUtility(projectName,this);
		try{
			if(this.sauceUser.equalsIgnoreCase("Local"))
				invokeAppLocally(false);
			else {		
				//AccessDBMethods obj= new AccessDBMethods(projectName);	
				//accessKey=obj.getSauceuserAccessKey(this.sauceUser);
				this.accessKey=accessKey;
				System.out.println("Sauce Username: "+this.sauceUser
						+ "\n"+"AccessKey: "+accessKey);
				invokeAppInSauce(true);	
			}			
		}catch (Exception e) {

			e.printStackTrace();
			reportStepDtlsWithScreenshot("FrameworkAPI Object Initialization ","Thrown an error : "+ utility.encodeSpecialCharacters(e.toString()), FAIL);			
		}	
	}
	
	/**
	 *Invoke app locally using emulator or real device
	 */
	public void invokeAppLocally(boolean apkDetails){
		if(deviceName.toLowerCase().contains("android")){
			capabilities=DesiredCapabilities.android();
		}
		else if(deviceName.toLowerCase().contains("iphone")){
			capabilities=DesiredCapabilities.iphone();
		}
		else if(deviceName.toLowerCase().contains("ipad")){
			capabilities=DesiredCapabilities.ipad();
		}

		//Device Capabilities
		capabilities.setCapability("appiumVersion",appiumVersion);
		capabilities.setCapability("deviceName",deviceName);
		capabilities.setCapability("device-orientation",deviceOrientation);
		capabilities.setCapability("browserName",browserName);
		capabilities.setCapability("platformVersion",platformVersion);
		capabilities.setCapability("platformName",platformName);
		
		if(apkDetails){
			capabilities.setCapability("app",appPath);
			capabilities.setCapability("app-Package",appPackage);
			capabilities.setCapability("app-Activity",appActivity);		 
		}
		
		 URL url=null;
		try {
			url = new URL("http://127.0.0.1:4723/wd/hub");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		if(deviceName.toLowerCase().contains("android")){
			driver = new AndroidDriver(url, capabilities);
			
			}
			else{
			driver = new IOSDriver(url, capabilities);
			}
		startTime= new Date();
	}

	/**
	 *Invoke app  in saucelabs
	 */
	public void invokeAppInSauce(boolean apkDetails){
		try{
			if(deviceName.toLowerCase().contains("android")){
				capabilities=DesiredCapabilities.android();
			}else if(deviceName.toLowerCase().contains("iphone")){
				capabilities=DesiredCapabilities.iphone();

			}
			else if(deviceName.toLowerCase().contains("ipad")){
				capabilities=DesiredCapabilities.ipad();
			}

			//Device Capabilities
			capabilities.setCapability("appiumVersion",appiumVersion);
			capabilities.setCapability("deviceName",deviceName);
			capabilities.setCapability("device-orientation",deviceOrientation);
			capabilities.setCapability("browserName",browserName);
			capabilities.setCapability("platformVersion",platformVersion);
			capabilities.setCapability("platformName",platformName);

			if(apkDetails){
			
				capabilities.setCapability("app",appPath);
				capabilities.setCapability("app-Package",appPackage);
				capabilities.setCapability("app-Activity",appActivity);		 
			}
		
			//Sauce Capabilities
			capabilities.setCapability("record-video", true);
			capabilities.setCapability("video-upload-on-pass", false);
			capabilities.setCapability("record-screenshots", false);
			capabilities.setCapability("sauce-advisor", false);
			capabilities.setCapability("requireWindowFocus", true);
			capabilities.setCapability("name",testScriptName);
			
			if(deviceName.toLowerCase().contains("android")){
			driver = new AndroidDriver(new URL(MessageFormat.format("http://{0}:{1}@ondemand.saucelabs.com:80/wd/hub", sauceUser, accessKey)), capabilities);
			
			}
			else{
			driver = new IOSDriver(new URL(MessageFormat.format("http://{0}:{1}@ondemand.saucelabs.com:80/wd/hub", sauceUser, accessKey)), capabilities);
			}
			startTime = new Date();
		}
		catch(Exception e){
			e.printStackTrace();
			reportStepDtlsWithScreenshot("FrameworkAPI Object Initialization","Thrown an error : "+ utility.encodeSpecialCharacters(e.toString()), FAIL);			
		}
	}
	
	public void startSelenium(String url){
		try{
			sURL=url;
			driver.get(sURL);				
			driver.manage().window().maximize();			
			System.out.println(browserName + "Browser Launched With URL: " +  sURL);

			commandName.add("OPen");
			reportStepDetails(" Launching main page","Successfully launched main page: "+ utility.encodeSpecialCharacters(sURL)+" in  "+ browserName+" browser ", PASS);
		}
		catch(Exception e){
			e.printStackTrace();
			
		}
	}
	
	public String getCurrentBrowserVersion(){		
		return capabilities.getVersion();
	}
	
	/**
	 * Get the desired capabilities for a particular emulator 
	 * @param appCapabilities
	 */
	public void mobileCapabilities(Hashtable<String,String> appCapabilities){
		this.platformName = appCapabilities.get("platformName");
		this.platformVersion = appCapabilities.get("platformVersion");
		this.browserName = appCapabilities.get("browserName");
		this.deviceOrientation = appCapabilities.get("device-orientation");
		this.deviceName = appCapabilities.get("deviceName");
		this.appiumVersion = appCapabilities.get("appiumVersion");
		if(appCapabilities.contains("deviceType")){
			this.deviceType = appCapabilities.get("deviceType");
		}
	}
	
	/**
	 * This method is used to capture Screenshot and fails the script 
	 * @param ver
	 * @param des
	 * @param stepStatus
	 */
	public void reportStepDtlsWithScreenshot(String ver, String des,String stepStatus) {		

		captureScreenShot();
		verification.add(ver);	
		description.add(des +"Screenshot:"+ testScriptName + "_"+iStepCount + ".png");
		System.out.println("Report step details method path:"+des + "Screenshot:" + automationPath +"Results" +File.separator+ "Screenshots"
				+ File.separator + testScriptName + "_" + utility.getCurrentDateNTime() + "_"+ iStepCount + ".png");
		dateTime.add(utility.getExecutionTime());	
		status.add(stepStatus);
		if(stepStatus.equalsIgnoreCase(FAIL)){
			sResultFlag = stepStatus;
			fail("command:"+ver+"||"+"description:"+des+"||"+stepStatus);
		}
		iStepCount += 1;
	}
	
	/**
	 * This method captures the screenshot.
	 */
	public String captureScreenShot() {
		String screenShotFilePath=null;
		try {	
			File scrFile =null;

			if(this.sauceUser.equalsIgnoreCase("Local")){
				scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			}
			else{
				scrFile=((TakesScreenshot)new  Augmenter().augment(driver)).getScreenshotAs(OutputType.FILE);
			}

			String screenShotPah=this.automationPath + "Results\\" + this.projectName +  "\\" + this.currentUser + "\\" + utility.getDateInDDMMYY() + "\\" + moduleName + "\\" + this.testSetName + "\\";

			File fi= new File(screenShotPah);
			if(!fi.exists()){
				fi.mkdirs();
			}
			System.out.println("Screenshot Path: "+ screenShotPah + testScriptName + "_"+iStepCount + ".png");
			FileUtils.copyFile(scrFile, new File(screenShotPah+ testScriptName + "_"+iStepCount + ".png"));
			FileUtils.copyFileToDirectory(new File(screenShotPah+ testScriptName + "_"+iStepCount + ".png"), new File(utility.getTempPath()+"CurrentRunReports\\"));
			screenShotFilePath="Screenshot:" + testScriptName + "_"+iStepCount + ".png";

		} catch (Exception e) {
			e.printStackTrace();
		}
		return screenShotFilePath;
	}
	
	/**
	 * This method adds step details to array lists used for reporting
	 * @param ver
	 *            Verification
	 * @param desc
	 *            Description
	 * @param status
	 *            Pass (or) Fail status
	 */
	public  void reportStepDetails(String ver, String des, String stepStatus) {
		verification.add(ver);		
		description.add(des);		
		status.add(stepStatus);		
		dateTime.add(utility.getExecutionTime());
		if(stepStatus.equalsIgnoreCase(FAIL))
			sResultFlag = stepStatus;
		iStepCount += 1;
	}
	
	/**
	 * This method takes selenese command in the form of string and checks that
	 * string is available in the enum list or not. If available, then
	 * corresponding case will be executed and will return value of type String.
	 * 
	 * @param sCommand
	 *            Selenese Command
	 * @return String either Pass (or) Fail (or) value returned by selenese
	 *         command
	 */
	public String perform(String sCommand) {
		String sText = "";
		try {
			commandName.add(sCommand);
			enumCommand = checkEnumConstant(sCommand);
			switch (enumCommand) {
			case deleteAllVisibleCookies:
				driver.manage().deleteAllCookies();
				reportStepDetails("The Command \"deleteAllVisibleCookies\"","Successfully deleted all cookies visible to the current page",PASS);
				return PASS;
				
			case implicitWait:
				driver.manage().timeouts().implicitlyWait(120,TimeUnit.SECONDS);
				return PASS;
				
			case waitForPageToLoad:
				driver.manage().timeouts()
				.pageLoadTimeout(60, TimeUnit.SECONDS);
				return PASS;
			case getURL:
				sText = driver.getCurrentUrl();
				reportStepDetails(
						"Retrieving the absolute URL of current page",
						"URL of current page:" + utility.encodeSpecialCharacters(sText),
						PASS);
				return (sText);
			case getTitle:
				sText = driver.getTitle();
				reportStepDetails("Retrieving page title",
						"Title of current page:"
								+ utility.encodeSpecialCharacters(sText), PASS);
				return (sText);
			case closeBrowser:
				driver.close();
				reportStepDetails("Closing window",
						"Successfully closed window", PASS);
				return PASS;
			case goBack:
				driver.navigate().back();
				reportStepDetails("Going back to the previous page",
						"Successfully navigated to previous page", PASS);
				return PASS;
			default:
				reportUnknowSeleniumCmdErr(sCommand);
			}

		} catch (Exception e) {
			e.printStackTrace();
			reportException(sCommand, e.toString());
		}
		return "";
	}
	
	/**
	 * This method takes selenese command and its target in the form of string
	 * and checks that string is available in the enum or not. If string is
	 * available in the enum list then corresponding case will be executed and
	 * will return value of type String.
	 * 
	 * @param sCommand
	 *            Selenese command
	 * @param sTarget
	 *            an element locator (or) timeout (or) expected value
	 * @return String either Pass (or) Fail (or) Value returned by selenese
	 *         command
	 */
	public String perform(String sCommand, String sTarget) {
		int sTargetFlag = 0;
		String sText = "";
		String sValue = "";
		boolean bFlag = false;
		commandName.add(sCommand);
		try {
			if (sCommand.equals("waitForPageToLoad")
					|| sCommand.equals("windowMaximize")
					|| sCommand.equals("assertAlert")
					|| sCommand.equals("verifyAlert")
					|| sCommand.equals("isElementPresent")
					|| sCommand.equals("getAttribute")
					|| sCommand.equals("selectWindow")||sCommand.equals("selectFrame")
					|| sCommand.equals("getCookieByName")
					|| sCommand.equals("openURL") || sCommand.equals("eOpen")
					|| sCommand.equals("assertTitle")
					|| sCommand.equals("verifyTitle")
					|| sCommand.equals("assertConfirmation")
					|| sCommand.equals("verifyConfirmation")
					|| sCommand.equals("waitForAlertPresent")
					|| sCommand.equals("eClickAndWait")					
					|| sCommand.equals("waitForElementPresent")
					|| sCommand.equals("implicitWait")){
				sTargetFlag = 1;

			} else if (sTarget.contains("&&")) {
				String[] sTargetArray = sTarget.split("&&");
				for (int i = 0; i < sTargetArray.length; i++) {
					if (isElementPresent(sTargetArray[i])) {
						sTarget = sTargetArray[i];
						sTargetFlag = 1;
						break;
					}
				}
			} else if (isElementPresent(sTarget)) {
				sTargetFlag = 1;
			}

			if (sCommand.equals("verifyElementPresent") && sTargetFlag == 0) {
				return (FAIL);
			}			

			if (sTargetFlag == 1) {

				enumCommand = checkEnumConstant(sCommand);
				switch (enumCommand) {


				case waitForAlertPresent:
					boolean flag = false;
					int secsToWait = Integer.parseInt(sTarget);

					for (int second = 0;; second++) {
						if (second >= secsToWait) {
							flag = false;
							break;
						}
						if (isAlertPresent()) {
							flag = true;
							break;
						}
						Thread.sleep(1000);
					}

					return Boolean.toString(flag);

				case waitForElementPresent:
					flag = false;
					secsToWait = 60;

					for (int second = 0;; second++) {
						if (second >= secsToWait) {
							flag = false;
							break;
						}
						if (isElementPresent(sTarget)) {
							flag = true;
							break;
						}
						Thread.sleep(1000);
					}
					if(!flag){
						reportStepDtlsWithScreenshot(
								"Checking the existence of element in :"
										+ sCommand+" command:" ,
										"Element verification \"" + sTarget
										+ "\" failed", FAIL);
					}

					return Boolean.toString(flag);

				case waitForValue:
					flag = false;
					for (int second = 0;; second++) {
						if (second >= 10) {
							flag = false;
							break;
						}
						sText = webElement(sTarget).getAttribute("value");
						if (sText.length() > 0) {
							flag = true;
							break;
						}
						Thread.sleep(1000);
					}

					return sText;

				case getValue:
					sText = webElement(sTarget).getAttribute("value");
					return sText;

				case click:					
					webElement(sTarget).click();
					reportStepDetails("Clicking button/link/image",
							"Successfully clicked the button/link/image", PASS);
					return PASS;
				case isChecked:
					bFlag = webElement(sTarget).isSelected();
					if (bFlag)
						reportStepDetails(
								"Verifying whether checkbox/radio button is checked or not",
								"Checkbox/radio button is checked", PASS);
					else
						reportStepDetails(
								"Verifying whether checkbox/radio button is checked or not",
								"Checkbox/radio button is not checked", PASS);
					return (Boolean.toString(bFlag));
				case waitForPageToLoad:

					driver.manage().timeouts()
					.pageLoadTimeout(60, TimeUnit.SECONDS);

					reportStepDtlsWithScreenshot(
							"Waiting for a new page to load",
							"Execution waited for new page to get loaded", PASS);
					return PASS;
					
				case implicitWait:
					driver.manage().timeouts().implicitlyWait(Long.parseLong(sTarget),TimeUnit.SECONDS);
					return PASS;
				
				case openURL:
					driver.get(sTarget);
					reportStepDetails("Opening URL", "Successfully loaded URL:"
							+utility. encodeSpecialCharacters(sTarget), PASS);
					return PASS;
				
				case eOpen:
					sValue = utility.getExcelData(sTarget);
					driver.get(sValue);
					reportStepDetails("Opening URL", "Successfully loaded URL:"
							+ utility.encodeSpecialCharacters(sValue), PASS);
					return sValue;
				case assertTitle:
					sText = driver.getTitle().trim();
					if (sText.equals(sTarget)) {
						reportStepDetails("Expected page title : "
								+ utility.encodeSpecialCharacters(sTarget),
								"Successfully validated page title:  "
										+ utility.encodeSpecialCharacters(sText), PASS);
					} else {						
						reportStepDtlsWithScreenshot("Expected page title : "
								+ utility.encodeSpecialCharacters(sTarget),
								"Displayed page title :  "
										+ utility.encodeSpecialCharacters(sText), FAIL);

					}
					return sText;
				case verifyTitle:
					sValue = utility.getExcelData(sTarget);
					sText = driver.getTitle().trim();
					if (sText.equals(sValue)) {
						reportStepDetails(sTarget + " Expected page title : "
								+ utility.encodeSpecialCharacters(sValue),
								"Successfully validated page title:  "
										+ utility.encodeSpecialCharacters(sText), PASS);
					} else {						
						reportStepDtlsWithScreenshotWithoutExit(sTarget
								+ " Expected page title : "
								+ utility.encodeSpecialCharacters(sValue),
								"Displayed page title :  "
										+ utility.encodeSpecialCharacters(sText), FAIL);

					}
					return sText;

				case assertConfirmation:
					if (isAlertPresent()) {
						sText = driver.switchTo().alert().getText();
						driver.switchTo().alert().accept();
						if (sText.equals(sTarget)) {
							reportStepDetails("Expected confirmation message: "
									+ utility.encodeSpecialCharacters(sTarget),
									"Successfully validated confirmation message:  "
											+ utility.encodeSpecialCharacters(sText),
											PASS);
						} else {							
							reportStepDtlsWithScreenshot(
									"Expected confirmation message: "
											+ utility.encodeSpecialCharacters(sTarget),
											"Displayed confirmation message:  "
													+ utility.encodeSpecialCharacters(sText),
													FAIL);
						}
					} else {
						reportStepDetails("Verifying confirmation message",
								"There is no confirmation message", FAIL);
					}
					return sText;
				case verifyConfirmation:
					if (isAlertPresent()) {
						sText = driver.switchTo().alert().getText();
						driver.switchTo().alert().accept();
						sValue = utility.getExcelData(sTarget);
						if (sText.equals(sValue)) {
							reportStepDetails(sTarget
									+ " Expected confirmation message: "
									+ utility.encodeSpecialCharacters(sValue),
									"Successfully validated confirmation message:  "
											+ utility.encodeSpecialCharacters(sText),
											PASS);
						} else {							
							reportStepDtlsWithScreenshotWithoutExit(sTarget
									+ " Expected confirmation message: "
									+ utility.encodeSpecialCharacters(sValue),
									"Displayed confirmation message:  "
											+ utility.encodeSpecialCharacters(sText),
											FAIL);
						}
					} else {
						reportStepDetails(sTarget
								+ " Verifying confirmation message",
								"There is no confirmation message", FAIL);
					}
					return sText;
				case windowMaximize:
					driver.manage().window().maximize();
					reportStepDetails("Maximizing window",
							"Successfully maximized window", PASS);
					return PASS;
				case typeEmpty:
					webElement(sTarget).clear();
					reportStepDetails("Clearing an input text field",
							"Successfully cleared text in input field", PASS);
					return PASS;
				case eClickAndWait:
					sTarget = utility.getExcelData(sTarget).trim();
					webElement(sTarget).click();
					sText = utility.encodeSpecialCharacters(webElement(sTarget)
							.getText());
					// waitForPageToLoad().setTimeToWait(30000);
					driver.manage().timeouts()
					.pageLoadTimeout(60, TimeUnit.SECONDS);
					reportStepDetails("Clicking button/link/image:" + sText,
							"Successfully clicked the button/link/image:"
									+ sText, PASS);
					return PASS;
				case verifyElementPresent:
					// sText = webElement(sTarget).getText();				
					reportStepDetails("Verifying element existence",
							"Element exists.", PASS);
					return PASS;
				case isElementPresent:
					sText = Boolean.toString(isElementPresent(sTarget));
					reportStepDetails(
							"Checking the existence of element:",
							"Element exists", PASS);

					return sText;
				case assertAlert:
					sText = driver.switchTo().alert().getText();
					if (sText.equals(sTarget)) {
						reportStepDetails("Expected alert message: "
								+ utility.encodeSpecialCharacters(sTarget),
								"Successfully validated alert message:  "
										+ utility.encodeSpecialCharacters(sText), PASS);
					} else {						
						reportStepDtlsWithScreenshot("Expected alert message: "
								+ utility.encodeSpecialCharacters(sTarget),
								"Displayed alert message: "
										+ utility.encodeSpecialCharacters(sText), FAIL);
					}
					return sText;
				case verifyAlert:
					sValue = utility.getExcelData(sTarget).trim();
					sText = driver.switchTo().alert().getText();
					if (sText.equals(sValue)) {
						reportStepDetails(sTarget
								+ " Expected alert message : "
								+ utility.encodeSpecialCharacters(sValue),
								"Successfully validated alert message:  "
										+ utility.encodeSpecialCharacters(sText), PASS);
					} else {						
						reportStepDtlsWithScreenshotWithoutExit(sTarget
								+ " Expected alert message : "
								+ utility.encodeSpecialCharacters(sValue),
								"Displayed  alert message:  "
										+ utility.encodeSpecialCharacters(sText), FAIL);

					}
					return sText;
				case getText:
					sText = webElement(sTarget).getText();
					return sText;
				case selectWindow:
					driver.switchTo().window(sTarget);

					reportStepDetails("Selecting the window "
							+ utility.encodeSpecialCharacters(sTarget),
							"Successfully selected window with ID "
									+ utility.encodeSpecialCharacters(sTarget), PASS);
					return PASS;
				case selectFrame:					

					if(sTarget.matches("[0-9]+")){
						int index=Integer.parseInt(sTarget);						
						driver.switchTo().frame(index);
					} else{					
						driver.switchTo().frame(sTarget);
					}					

					reportStepDetails("Selecting the window "
							+ utility.encodeSpecialCharacters(sTarget),
							"Successfully selected window with ID "
									+ utility.encodeSpecialCharacters(sTarget), PASS);
					return PASS;

				case waitForFrameAndSelect:

					WebDriverWait wait=new WebDriverWait(driver,100);
					if(sTarget.matches("[0-9]+")){
						int index=Integer.parseInt(sTarget);						
						driver.switchTo().frame(index);
					} else{						
						wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(sTarget));
						driver.switchTo().frame(sTarget);
					}					

					reportStepDetails("Selecting the window "
							+ utility.encodeSpecialCharacters(sTarget),
							"Successfully selected window with ID "
									+ utility.encodeSpecialCharacters(sTarget), PASS);
					return PASS;

				case getAttribute:
					sTarget = sTarget.substring(0, sTarget.lastIndexOf("@"));
					String atribute_name = sTarget.substring(sTarget
							.lastIndexOf("@") + 1);
					sText = webElement(sTarget).getAttribute(atribute_name);
					return sText;
				case getXpathCount:
					sText = Integer.toString(driver.findElements(
							By.xpath(sTarget)).size());
					return sText;
				case getSelectedLabel:
					Select foo = new Select(webElement(sTarget));
					sText = foo.getFirstSelectedOption().getText();					
					reportStepDetails("Retrieving selected option label",
							"Drop-down contains label:"
									+ utility.encodeSpecialCharacters(sText), PASS);
					return sText;
				case getCookieByName:
					sText = driver.manage().getCookieNamed(sTarget).toString();
					reportStepDetails("Retrieving the value of cookie \""
							+ utility.encodeSpecialCharacters(sTarget) + "\"",
							"Value of cookie \""
									+ utility.encodeSpecialCharacters(sTarget) + "\":"
									+ utility.encodeSpecialCharacters(sText), PASS);
					return sText;
				case clickAndWait:

					WebDriverWait wait1 = new WebDriverWait(driver, 250);
					WebElement element = wait1.until(ExpectedConditions.elementToBeClickable(by(sTarget)));
					element.click();

					if(browserName.contains("Safari")){
						Thread.sleep(30000);
					}else{
						driver.manage().timeouts()
						.pageLoadTimeout(250, TimeUnit.SECONDS);
					}

					reportStepDetails("Clicking link/image/button",
							"Successfully clicked link/image/button", PASS);
					return PASS;
				default:
					reportUnknowSeleniumCmdErr(sCommand);
				}
			} else {
				captureScreenShot();
				reportElementNotFound(sCommand);
			}
		} catch (Exception e) {
			e.printStackTrace();

			reportException(sCommand, e.toString());
		}
		return "";
	}

	/**
	 * This method takes selenese command and its target in the form of string
	 * and checks that string is available in the enum or not. If string is
	 * available in the enum list then corresponding case will be executed and
	 * will return value of type String.
	 * 
	 * @param sCommand
	 *            Selenese Command
	 * @param sTarget
	 *            an element locator
	 * @param sTitle
	 *            label name or expected value or excel column name or timeout
	 * @return String either Pass (or) Fail (or) value returned by selenese
	 *         command
	 */
	public String perform(String sCommand, String sTarget, String sTitle) {
		String sText, encodedTitle, sValue = "";
		int sTargetFlag = 0;
		boolean checked, flag = false;
		commandName.add(sCommand);
		try {
			encodedTitle = utility.encodeSpecialCharacters(sTitle);
			if (sCommand.equals("sendReport") || sCommand.equals("compare")
					|| sCommand.equals("openWindow")
					|| sCommand.equals("waitForPopUp")
					|| sCommand.equals("compareURLs")
					|| sCommand.equals("captureEntirePageScreenshot")
					|| sCommand.equals("waitForElementPresent")
					|| sCommand.equals("waitForTextPresent")
					|| sCommand.equals("eOpenWindow")
					|| sCommand.equals("isElementInVisible")
					|| sCommand.equals("waitUntilElementVisible")
					|| sCommand.equals("verifyElementPresent")
					|| sCommand.equals("getAttribute")) {				
				sTargetFlag = 1;
			} else if (sTarget.contains("&&")) {
				String[] sTargetArray = sTarget.split("&&");
				for (int i = 0; i < sTargetArray.length; i++) {
					if (isElementPresent(sTargetArray[i])) {
						sTarget = sTargetArray[i];
						sTargetFlag = 1;
						break;
					}
				}
			} else if (isElementPresent(sTarget)) {
				sTargetFlag = 1;
			}

			if (sTargetFlag == 1) {


				enumCommand = checkEnumConstant(sCommand);
				switch (enumCommand) {
				case verifyElementPresent:
					reportStepDetails("Validated: " + encodedTitle
							+ " Element is available ",
							"Successfully validated element:  "
									+ utility.encodeSpecialCharacters(sTitle), PASS);

					return sTitle;

				case assertValue:
					sText = webElement(sTarget).getAttribute("value").trim();
					sTitle = sTitle.trim();
					if (sText.equals(sTitle)) {
						reportStepDetails("Expected value: " + encodedTitle,
								"Successfully validated value: "
										+ utility.encodeSpecialCharacters(sText), PASS);
					} else {
						reportStepDtlsWithScreenshot("Expected value: "
								+ encodedTitle, "Actual value: "
										+ utility.encodeSpecialCharacters(sText), FAIL);
					}
					return sText;
				case isElementInVisible:
					sText="Element is Present";
					Thread.sleep(3000); 
					try{				    	
						if (webElement(sTarget).isDisplayed()) {
							sText = webElement(sTarget).getText();
							reportStepDtlsWithScreenshotWithoutExit("Validated Element :" + encodedTitle,	sText, FAIL);
						}	
						else{				    	 				    	
							sText = "Element is Not Present";
							reportStepDetails("Validated Element: "+ encodedTitle, sText
									, PASS);
						}
					}
					catch(Exception e){
						sText = "Element is Not Present";
						reportStepDetails("Validated Element: "+ encodedTitle, sText
								, PASS);
						e.printStackTrace();
					}				     
					return sText;
				case eOpenWindow:
					sValue = utility.getExcelData(sTarget);
					driver.navigate().to(sValue);
					reportStepDetails("Opening popup window " + encodedTitle,
							"Successfully opened " + encodedTitle
							+ " popup window", PASS);
					return sValue;
				case waitUntilElementVisible:
					flag = false;
					int secsToWait = Integer.parseInt(sTitle);

					for (int second = 0;; second++) {
						if (second >= secsToWait) {
							flag = false;
							break;
						}
						if (webElement(sTarget).isDisplayed()) {
							flag = true;
							break;
						}
						Thread.sleep(1000);
					}
					return Boolean.toString(flag);
				case isSpecifiedOptionChecked:
					checked = webElement(sTarget).isSelected();
					if (checked)
						reportStepDetails("Verifying whether \"" + encodedTitle
								+ "\" is selected or not", "\"" + encodedTitle
								+ "\" is selected", PASS);
					else
						reportStepDetails("Verifying whether \"" + encodedTitle
								+ "\" is selected or not", "\"" + encodedTitle
								+ "\" is not selected", PASS);
					return (Boolean.toString(checked));
				case typeEmpty:
					webElement(sTarget).clear();
					reportStepDetails("Setting value of " + encodedTitle,
							encodedTitle + " is set to : \"\"", PASS);
					return PASS;
				case type:
					if(!sTarget.contains("class"))
						sValue = readDataFromExcel(sTitle);
					else
						sValue = sTitle;
					System.out.println("Input Value of \""+ sTitle + "\": " +sValue);
					WebElement elmnt = webElement(sTarget);
					elmnt.clear();
					elmnt.sendKeys(sValue);
					reportStepDetails("Setting value of " + encodedTitle,
							encodedTitle + " is set to value: \""
									+ utility.encodeSpecialCharacters(sValue) + "\"",
									PASS);
					return sValue;
				case typePassword:
					sValue = utility.getExcelData(sTitle);
					elmnt = webElement(sTarget);
					elmnt.clear();
					String value=utility.encryptPassword(sValue);
					elmnt.sendKeys(sValue);
					reportStepDetails("Setting value of " + encodedTitle,
							encodedTitle + " is set to : "+value, PASS);
					return sValue;
				case isChecked:
					checked = false;
					encodedTitle = utility.encodeSpecialCharacters(sTitle);
					checked = webElement(sTarget).isSelected();
					if (checked)
						reportStepDetails("Verifying whether \"" + encodedTitle
								+ "\" is selected or not", "\"" + encodedTitle
								+ "\" is selected", PASS);

					else
						reportStepDetails("Verifying whether \"" + encodedTitle
								+ "\" is selected or not", "\"" + encodedTitle
								+ "\" is not selected", PASS);
					return Boolean.toString(checked);
				case waitForElementPresent:

					secsToWait = Integer.parseInt(sTitle);
					//System.out.println("enter into driverWaitForElementPresent");
					WebDriverWait wait = new WebDriverWait(driver, secsToWait);
					WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(by(sTarget)));
					if(element==null){
						reportStepDtlsWithScreenshot(
								"Checking the existence of element in :"
										+ sCommand+" command:" ,
										"Element verification \"" + sTarget
										+ "\" failed", FAIL);
					}
					return element.toString();		


				case waitForValue:
					flag = false;
					secsToWait = Integer.parseInt(sTitle);
					sText = "";
					for (int second = 0;; second++) {
						if (second >= secsToWait) {
							flag = false;
							break;
						}
						sText = webElement(sTarget).getAttribute("value")
								.trim();
						if (sText.trim().length() > 0) {
							flag = true;
							break;
						}
						Thread.sleep(1000);
					}
					return sText;

				case getText:
					encodedTitle = utility.encodeSpecialCharacters(sTitle);
					sText = webElement(sTarget).getText();
					reportStepDetails("Retrieving " + encodedTitle,
							"Retrieved " + encodedTitle + ":"
									+ utility.encodeSpecialCharacters(sText), PASS);
					return sText;
				case getAttribute:

					encodedTitle = utility.encodeSpecialCharacters(sTitle);
					sTarget = sTarget.substring(0, sTarget.lastIndexOf("@"));
					String atribute_type = sTarget.substring(sTarget
							.lastIndexOf("@") + 1);
					sText = webElement(sTarget).getAttribute(atribute_type);
					reportStepDetails("Retrieving " + encodedTitle,
							"Retrieved " + encodedTitle + ":"
									+ utility.encodeSpecialCharacters(sText), PASS);
					return sText;
				case select:
					sValue = utility.getExcelData(sTitle);
					boolean b=false;
					if (sValue.startsWith("label="))
						sValue = sValue.substring(sValue.indexOf("=") + 1);
					WebElement select = webElement(sTarget);
					List<WebElement> options = select.findElements(By
							.tagName("option"));
					for (WebElement option : options) {
						if (option.getText().equals(sValue)) {
							option.click();
							b=true;
							break;
						}
					}
                  if(b){
                	  reportStepDetails("Selecting an option from drop-down:"
  							+ encodedTitle,
  							"Successfully selected the list value: "
  									+ utility.encodeSpecialCharacters(sValue), PASS);
                  }
                  else{
                	  reportStepDtlsWithScreenshot("Label in select is"+sValue, "Label in Select not found", FAIL);
                  }
					
					return sValue;

				case captureEntirePageScreenshot:					
					String screenshotFile=captureScreenShot();
					reportStepDetails(
							utility.encodeSpecialCharacters(sTarget),
							screenshotFile, PASS);

					return PASS;

				case getXpathCount:

					encodedTitle = utility.encodeSpecialCharacters(sTitle);
					sText = String.valueOf(driver.findElements(
							By.xpath(sTarget)).size());
					reportStepDetails("Verifying " + encodedTitle, "Number of "
							+ encodedTitle + ": " + sText, PASS);
					return sText;
				case openWindow:
					driver.navigate().to(sTarget);
					reportStepDetails("Opening popup window " + encodedTitle,
							"Successfully opened " + encodedTitle
							+ " popup window", PASS);
					return PASS;

				case verifyValue:
					sValue = utility.getExcelData(sTitle).trim();
					sText = webElement(sTarget).getAttribute("value").trim();
					if (sText.equals(sValue)) {
						reportStepDetails(encodedTitle + "  Expected value : "
								+ utility.encodeSpecialCharacters(sValue),
								"Successfully validated value:  "
										+ utility.encodeSpecialCharacters(sText), PASS);
						return sText;
					} else {					
						reportStepDtlsWithScreenshot(encodedTitle
								+ " Expected value : "
								+ utility.encodeSpecialCharacters(sValue),
								"Displayed value :  "
										+ utility.encodeSpecialCharacters(sText), FAIL);
					}
					return sText;
				case getValue:

					encodedTitle = utility.encodeSpecialCharacters(sTitle).trim();
					sText = webElement(sTarget).getAttribute("value").trim();
					reportStepDetails("Retrieving value of input field: "
							+ encodedTitle, "Successfully retrieved value: "
									+ utility.encodeSpecialCharacters(sText), PASS);
					return sText;
				case sendReport:
					reportStepDetails(utility.encodeSpecialCharacters(" "+sTarget),
							utility.encodeSpecialCharacters(sTitle), PASS);
				case verifySubText:
					sValue = utility.getExcelData(sTitle).trim();
					sText = webElement(sTarget).getText().trim();
					if (sText.contains(sValue)) {
						reportStepDetails(" "+"Verifying text " + encodedTitle,
								"\"" + utility.encodeSpecialCharacters(sValue)
								+ "\" text appears in \""
								+ utility.encodeSpecialCharacters(sText) + "\"",
								PASS);
					} else {						
						reportStepDtlsWithScreenshot(" "+"Verifying text "
								+ encodedTitle, "\""
										+ utility.encodeSpecialCharacters(sValue)
										+ "\" text doesn't appears \""
										+ utility.encodeSpecialCharacters(sText) + "\"", FAIL);
					}
					return sText;
				case assertSubText:
					sText = webElement(sTarget).getText().trim();
					if (sText.contains(sTitle)) {
						reportStepDetails("Verifying text:\"" + encodedTitle
								+ "\"", "\"" + encodedTitle
								+ "\" text appears in \""
								+ utility.encodeSpecialCharacters(sText) + "\"", PASS);
					} else {						
						reportStepDtlsWithScreenshot("Verifying text:\""
								+ encodedTitle + "\"", "\"" + encodedTitle
								+ "\" text doesn't appears in \""
								+ utility.encodeSpecialCharacters(sText) + "\"", FAIL);
					}
					return sText;
				case verifyTextPresent:

					sValue = utility.getExcelData(sTitle).trim();
					sText = webElement(sTarget).getText().trim();
					if (sText.equals(sValue)) {
						reportStepDetails(" "+"Validated: " + encodedTitle
								+ " Expected text : "
								+ utility.encodeSpecialCharacters(sValue),
								"Displayed  text :  "
										+ utility.encodeSpecialCharacters(sText), PASS);
					} else {						
						reportStepDtlsWithScreenshotWithoutExit(" "+"Validated: "
								+ encodedTitle + " Expected text : "
								+ utility.encodeSpecialCharacters(sValue),
								"Displayed  text :  "
										+ utility.encodeSpecialCharacters(sText), FAIL);
					}
					return sText;
				case verifyText:

					sText = webElement(sTarget).getText().trim();
					sValue = sTitle.trim();
					if (sText.equals(sValue)) {
						reportStepDetails(" "+"Validated: " + encodedTitle
								+ " Expected text : "
								+ utility.encodeSpecialCharacters(sValue),
								"Successfully validated text:  "
										+ utility.encodeSpecialCharacters(sText), PASS);
					} else {						
						reportStepDtlsWithScreenshotWithoutExit(" "+"Validated: "
								+ encodedTitle + " Expected text : "
								+ utility.encodeSpecialCharacters(sValue),
								"Displayed  text :  "
										+ utility.encodeSpecialCharacters(sText), FAIL);

					}
					return sText;
				case assertText:
					sText = webElement(sTarget).getText().trim();
					sTitle = sTitle.trim();
					if (sText.equals(sTitle)) {
						reportStepDetails(" "+"Verification:Expected text : " + encodedTitle,
								"Successfully validated text:  "
										+ utility.encodeSpecialCharacters(sText), PASS);
					} else {						
						reportStepDtlsWithScreenshot(" "+"Expected text : "
								+ encodedTitle, "Displayed  text :  "
										+ utility.encodeSpecialCharacters(sText), FAIL);
						//stopSelenium();
					}
					return sText;
				case assertTextPresent:
					sText = webElement(sTarget).getText().trim();
					sTitle = sTitle.trim();
					if (sText.equals(sTitle)) {
						reportStepDetails(" "+"Expected text : " + encodedTitle,
								"Successfully validated text:  "
										+ utility.encodeSpecialCharacters(sText), PASS);
					} else {	
						reportStepDtlsWithScreenshotWithoutExit(" "+"Expected text : "
								+ encodedTitle, "Displayed  text :  "
										+ utility.encodeSpecialCharacters(sText), FAIL);
					}
					return sText;
				case click:

					if (sTitle.endsWith(".id") || sTitle.endsWith(".name")
							|| sTitle.endsWith(".link"))
						encodedTitle = utility.encodeSpecialCharacters(sTitle
								.substring(0, sTitle.lastIndexOf(".")));
					else						
						encodedTitle = utility.encodeSpecialCharacters(sTitle);
					webElement(sTarget).click();
					reportStepDetails("Clicking button/link/image:"
							+ encodedTitle,
							"Successfully clicked button/link/image:"
									+ encodedTitle, PASS);
					return PASS;
				case clickAndWait:

					WebDriverWait wait1 = new WebDriverWait(driver, 250);
					WebElement element1 = wait1.until(ExpectedConditions.elementToBeClickable(by(sTarget)));

					if (sTitle.endsWith(".id") || sTitle.endsWith(".name")
							|| sTitle.endsWith(".link"))
						encodedTitle = utility.encodeSpecialCharacters(sTitle
								.substring(0, sTitle.lastIndexOf(".")));
					else
						encodedTitle = utility.encodeSpecialCharacters(sTitle);
					element1.click();

					try{					
						driver.manage().timeouts()
						.pageLoadTimeout(250, TimeUnit.SECONDS);
					}catch(TimeoutException te){
						reportException(sCommand, sTitle);
					}	
					catch(Exception e){
						reportException(sCommand, sTitle);
					}

					reportStepDetails("Clicking button/link/image:"
							+ encodedTitle,
							"Successfully clicked button/link/image:"
									+ encodedTitle, PASS);
					return PASS;

					//android.widget.EditText
				
				case clickSpecifiedLink:
					webElement(sTarget).click();
					reportStepDetails("Clicking button/link/image:"
							+ encodedTitle,
							"Successfully clicked button/link/image:"
									+ encodedTitle, PASS);
					return PASS;
				case clickSpecifiedLinkNWait:
					webElement(sTarget).click();
					// waitForPageToLoad().setTimeToWait(30000);
					driver.manage().timeouts()
					.pageLoadTimeout(60, TimeUnit.SECONDS);
					reportStepDtlsWithScreenshot("Clicking button/link/image:"
							+ encodedTitle,
							"Successfully clicked the button/link/image:"
									+ encodedTitle, PASS);
					return PASS;
				case verifyElement:

				case compare:
					String sTitleString = "";
					String sTargetString = "";
					sTarget = sTarget.trim();
					sTitle = sTitle.trim();
					if (sTarget.contains(":")) {
						String[] sTargetVlaue = sTarget.split(":");
						if (sTargetVlaue[1].equals("")) {
							sTarget = sTargetVlaue[0];
							sTargetString = sTarget;
						} else {
							sTarget = sTargetVlaue[1];
							sTargetString = sTargetVlaue[0] + sTarget;
						}
					} else {
						sTargetString = sTarget;
					}
					if (sTitle.contains(":")) {
						String[] sTitleVlaue = sTitle.split(":");
						if (sTitleVlaue[1].equals("")) {
							sTitle = sTitleVlaue[0];
							sTitleString = sTitle;
						} else {
							sTitle = sTitleVlaue[1];
							sTitleString = sTitleVlaue[0] + sTitle;
						}
					} else {
						sTitleString = sTitle;
					}
					sTarget = sTarget.trim();
					sTitle = sTitle.trim();
					if (sTarget.equals(sTitle)) {
						reportStepDetails(
								utility.encodeSpecialCharacters(sTargetString),
								utility.encodeSpecialCharacters(sTitleString), PASS);
						return PASS;
					} else {

						reportStepDtlsWithScreenshot(
								utility.encodeSpecialCharacters(sTargetString),
								utility.encodeSpecialCharacters(sTitleString), FAIL);
						return FAIL;

					}
				case compareURLs:
					String sTitleString1 = "";
					String sTargetString1 = "";
					sTarget = sTarget.trim();
					sTitle = sTitle.trim();
					if (sTarget.contains("::")) {
						String[] sTargetVlaue = sTarget.split("::");
						if (sTargetVlaue[1].equals("")) {
							sTarget = sTargetVlaue[0];
							sTargetString1 = sTarget;
						} else {
							sTarget = sTargetVlaue[1];
							sTargetString1 = sTargetVlaue[0] + ": " + sTarget;
						}
					} else {
						sTargetString1 = sTarget;
					}
					if (sTitle.contains("::")) {
						String[] sTitleVlaue = sTitle.split("::");
						if (sTitleVlaue[1].equals("")) {
							sTitle = sTitleVlaue[0];
							sTitleString1 = sTitle;
						} else {
							sTitle = sTitleVlaue[1];
							sTitleString1 = sTitleVlaue[0] + ": " + sTitle;
						}
					} else {
						sTitleString1 = sTitle;
					}
					sTarget = sTarget.trim();
					sTitle = sTitle.trim();
					if (sTarget.equals(sTitle)) {
						reportStepDetails(
								utility.encodeSpecialCharacters(sTargetString1),
								utility.encodeSpecialCharacters(sTitleString1), PASS);
						return PASS;
					} else {
						reportStepDtlsWithScreenshot(
								utility.encodeSpecialCharacters(sTargetString1),
								utility.encodeSpecialCharacters(sTitleString1), FAIL);
						return FAIL;
					}

				default:
					reportUnknowSeleniumCmdErr(sCommand);
				}
			} else {
				if (sCommand.equals("verifyElement")) {
					encodedTitle = utility.encodeSpecialCharacters(sTitle);
					reportStepDtlsWithScreenshot(
							"Checking the existence of element:" + encodedTitle,
							"Element verification \"" + encodedTitle
							+ "\" failed", FAIL);
					return Boolean.toString(false);
				} else if (sCommand.equals("verifyElementPresent")) {					
					encodedTitle = utility.encodeSpecialCharacters(sTitle);
					reportStepDtlsWithScreenshotWithoutExit(
							"Checking the existence of element:" + encodedTitle,
							"Element verification \"" + encodedTitle
							+ "\" failed", FAIL);					
				} else {
					captureScreenShot();
					reportElementNotFound(sCommand);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			if(sCommand.contains("waitFor")){
				reportException(sCommand, "Expected");
			}else{
				reportException(sCommand, sTitle);
			}
		}
		return "";
	}
	
	/**
	 * This method takes selenese command and its target in the form of string
	 * and checks that string is available in the enum or not. If string is
	 * available in the enum list then corresponding case will be executed and
	 * will return value of type String.
	 * 
	 * @param sCommand
	 *            Selenese Command
	 * @param sTarget
	 *            an element locator (or) verification report (or) timeout
	 * @param sTitle
	 *            label name or expected value or excel column name (or)
	 *            Description
	 * @param sResult
	 *            label name or Pass/Fail status or timeout
	 * @return String either Pass (or) Fail (or) value returned by selenese
	 *         command
	 */
	public String perform(String sCommand, String sTarget, String sTitle,String sResult) {
		String sText = "";
		String encodedTitle = "";
		int sTargetFlag = 0;
		boolean flag = false;
		commandName.add(sCommand);
		try {
			if (sCommand.equals("sendReport")
					|| sCommand.equals("sendReportWithOutExit")
					|| sCommand.equals("captureEntirePageScreenshot")
					|| sCommand.equals("waitForElementPresent")) {
				sTargetFlag = 1;
			} else if (sTarget.contains("&&")) {
				String[] sTargetArray = sTarget.split("&&");
				for (int i = 0; i < sTargetArray.length; i++) {
					if (isElementPresent(sTargetArray[i])) {
						sTarget = sTargetArray[i];
						sTargetFlag = 1;
						break;
					}
				}
			} else if (isElementPresent(sTarget)) {
				sTargetFlag = 1;
			}

			if (sTargetFlag == 1) {

				enumCommand = checkEnumConstant(sCommand);
				switch (enumCommand) {
				case type:
					String sValue = sTitle;
					WebElement elmnt1 = webElement(sTarget);
					elmnt1.clear();
					elmnt1.sendKeys(sValue);
					reportStepDetails("Setting value of " + sResult,
							sResult + " is set to value: \""
									+ utility.encodeSpecialCharacters(sValue) + "\"",
									PASS);
					return sValue;
				case typePassword:
					sValue = sTitle;
					WebElement eleObj = webElement(sTarget);
					eleObj.clear();
					eleObj.sendKeys(sValue);

					String value=utility.encryptPassword(sValue);

					reportStepDetails("Setting value of " + sResult,
							sResult + " is set to : "+value, PASS);
					return sValue;
				case select:
					sValue = sTitle;
					if (sValue.startsWith("label="))
						sValue = sValue.substring(sValue.indexOf("=") + 1);
					WebElement select = webElement(sTarget);
					List<WebElement> options = select.findElements(By
							.tagName("option"));
					for (WebElement option : options) {

						if (option.getText().equals(sValue)) {
							option.click();
							break;
						}
					}
					
					
					/*
					 * Select select = new Select(webElement(sTarget));
					 * select.selectByValue(sValue);
					 */
					reportStepDetails("Selecting an option from drop-down:"
							+ sResult,
							"Successfully selected the list value : "
									+ utility.encodeSpecialCharacters(sValue), PASS);
					return sValue;

				case sendReport:
					if (sResult.equalsIgnoreCase(FAIL)) {						
						reportStepDtlsWithScreenshot(utility.encodeSpecialCharacters(" "+sTarget),
								utility.encodeSpecialCharacters(sTitle), FAIL);	
						//stopSelenium();
					} else if(sResult.equalsIgnoreCase(PASS)){
						reportStepDetails(utility.encodeSpecialCharacters(" "+sTarget),
								utility.encodeSpecialCharacters(sTitle), PASS);
					}
					else{
						reportUnknowSeleniumCmdErr("sendReport with argument as " +sResult);
					}
					return sResult;
				case captureEntirePageScreenshot:

					reportStepDtlsWithScreenshot(
							utility.encodeSpecialCharacters(sTarget),
							utility.encodeSpecialCharacters(sTitle), sResult);
					return PASS;
				case clickAndWait:	

					WebDriverWait wait1 = new WebDriverWait(driver, 250);
					WebElement element1 = wait1.until(ExpectedConditions.elementToBeClickable(by(sTarget)));

					encodedTitle = utility.encodeSpecialCharacters(sTitle);
					element1.click();
					if(browserName.contains("Safari")){
						Thread.sleep(Integer.parseInt(sResult));
					}else{

						try{					
							driver.manage().timeouts()
							.pageLoadTimeout(250, TimeUnit.SECONDS);
						}catch(TimeoutException te){
							reportException(sCommand, sTitle);
						}	
						catch(Exception e){
							reportException(sCommand, sTitle);
						}
					}					
					reportStepDetails("Clicking button/link/image:"
							+ encodedTitle,
							"Successfully clicked button/link/image:"
									+ encodedTitle, PASS);

					return PASS;
				case clickAndWaitForElementPresent:					
					encodedTitle = utility.encodeSpecialCharacters(sTitle);
					webElement(sTarget).click();
					WebDriverWait wait = new WebDriverWait(driver, 200);
					try{
						wait.until(ExpectedConditions.visibilityOfElementLocated(by(sResult)));	
					}catch(TimeoutException te){
						//reportException(sCommand, te.toString());
						reportException(sCommand, sTitle);
					}	
					catch(Exception e){
						reportException(sCommand, sTitle);
					}
					reportStepDetails("Clicking button/link/image:"
							+ encodedTitle,
							"Successfully clicked button/link/image:"
									+ encodedTitle, PASS);

					return PASS;

				case sendReportWithOutExit:
					if (sResult.equalsIgnoreCase(FAIL)) {						
						reportStepDtlsWithScreenshotWithoutExit(
								utility.encodeSpecialCharacters(" "+sTarget),
								utility.encodeSpecialCharacters(sTitle), FAIL);
					} else if(sResult.equalsIgnoreCase(PASS)){
						reportStepDetails(utility.encodeSpecialCharacters(" "+sTarget),
								utility.encodeSpecialCharacters(sTitle), PASS);
					}
					else{
						reportUnknowSeleniumCmdErr("sendReportWithOutExit with argument as " +sResult);
					}
					return sResult;
				case waitForElementPresent:
					flag = false;
					int secsToWait = Integer.parseInt(sTitle);

					for (int second = 0;; second++) {
						if (second >= secsToWait) {
							flag = false;
							break;
						}
						if (isElementPresent(sTarget)) {
							flag = true;
							break;
						}
						Thread.sleep(1000);
					}
					if (!flag) {						
						encodedTitle = utility.encodeSpecialCharacters(sResult);					
						reportStepDtlsWithScreenshot(
								"Checking the existence of element:"
										+ encodedTitle,
										"Element verification \"" + encodedTitle
										+ "\" failed", FAIL);
					}
					return Boolean.toString(flag);
				case verifyText:
					/*sValue = getExcelData(sTitle);
					sText = webElement(sTarget).getText().trim();*/
					sText = webElement(sTarget).getText().trim();
					sTitle = sTitle.trim();
					encodedTitle = utility.encodeSpecialCharacters(sResult);
					if (sText.equalsIgnoreCase(sTitle)) {
						reportStepDetails(" "+"Expected " + encodedTitle + ": "
								+ utility.encodeSpecialCharacters(sTitle),
								"Successfully validated " + encodedTitle
								+ ":  "
								+ utility.encodeSpecialCharacters(sText), PASS);
					} else {						
						reportStepDtlsWithScreenshotWithoutExit(" "+"Expected " + encodedTitle
								+ ": " + utility.encodeSpecialCharacters(sTitle),
								"Displayed " + encodedTitle + ":  "
										+ utility.encodeSpecialCharacters(sText), FAIL);				
					}
					return sText;
				case assertText:

					sText = webElement(sTarget).getText().trim();

					sTitle = sTitle.trim();
					//encodedTitle = encodeSpecialCharacters(getExcelData(sResult));
					encodedTitle = utility.encodeSpecialCharacters(sResult);
					if (sText.equalsIgnoreCase(sTitle)) {
						reportStepDetails(" "+"Expected " + encodedTitle + ": "
								+ utility.encodeSpecialCharacters(sTitle),
								"Successfully validated " + encodedTitle
								+ ":  "
								+ utility.encodeSpecialCharacters(sText), PASS);
					} else {						
						reportStepDtlsWithScreenshot(" "+"Expected " + encodedTitle
								+ ": " + utility.encodeSpecialCharacters(sTitle),
								"Displayed " + encodedTitle + ":  "
										+ utility.encodeSpecialCharacters(sText), FAIL);
						//stopSelenium();
					}

					return sText;

				case assertValue:
					sText = webElement(sTarget).getAttribute("value").trim();
					sTitle = sTitle.trim();
					//encodedTitle = encodeSpecialCharacters(getExcelData(sResult));
					encodedTitle = utility.encodeSpecialCharacters(sResult);
					if (sText.equalsIgnoreCase(sTitle)) {
						reportStepDetails("Expected " + encodedTitle + ": "
								+ utility.encodeSpecialCharacters(sTitle),
								"Successfully validated " + encodedTitle + ":"
										+utility.encodeSpecialCharacters(sText), PASS);
					} else {						
						reportStepDtlsWithScreenshot("Expected " + encodedTitle
								+ ": " + utility.encodeSpecialCharacters(sTitle),
								"Displayed " + encodedTitle + ":  "
										+ utility.encodeSpecialCharacters(sText), FAIL);
					}
					return sText;
				case typeSpecifiedText:
					encodedTitle = utility.encodeSpecialCharacters(sResult);
					WebElement elmnt = webElement(sTarget);
					elmnt.clear();
					//System.out.println("sTitlesTitle"+sTitle);
					elmnt.sendKeys(sTitle);
					reportStepDetails("Setting value of " + encodedTitle,
							encodedTitle + " is set to value: \""
									+ utility.encodeSpecialCharacters(sTitle) + "\"",
									PASS);
					return sTitle;
				case assertTextPresent:
					sText = webElement(sTarget).getText().trim();
					sTitle = sTitle.trim();
					//encodedTitle = encodeSpecialCharacters(getExcelData(sResult));
					encodedTitle = utility.encodeSpecialCharacters(sResult);
					if (sText.equals(sTitle)) {
						reportStepDetails(" "+"Expected " + encodedTitle + ": "
								+ utility.encodeSpecialCharacters(sTitle),
								"Successfully validated " + encodedTitle
								+ ":  "
								+ utility.encodeSpecialCharacters(sText), PASS);
					} else {						
						reportStepDtlsWithScreenshotWithoutExit(" "+"Expected " + encodedTitle
								+ ": " + utility.encodeSpecialCharacters(sTitle),
								"Displayed " + encodedTitle + ":  "
										+ utility.encodeSpecialCharacters(sText), FAIL);
						//stopSelenium();
						//fail(sCommand);
					}
					return sText;
				case assertSubText:
					sText = webElement(sTarget).getText().trim();
					//encodedTitle = encodeSpecialCharacters(getExcelData(sResult));
					encodedTitle = utility.encodeSpecialCharacters(sResult);
					if (sText.contains(sTitle)) {
						reportStepDetails("Verifying " + encodedTitle + ":\""
								+ utility.encodeSpecialCharacters(sTitle) + "\"", "\""
										+ utility.encodeSpecialCharacters(sTitle)
										+ "\" text appears in \""
										+ utility.encodeSpecialCharacters(sText) + "\"", PASS);
					} else {						
						reportStepDtlsWithScreenshot("Verifying "
								+ encodedTitle + ":\""
								+ utility.encodeSpecialCharacters(sTitle) + "\"", "\""
										+ utility.encodeSpecialCharacters(sTitle)
										+ "\" text doesn't appears in \""
										+ utility.encodeSpecialCharacters(sText) + "\"", FAIL);
					}
					return sText;
				default:
					reportUnknowSeleniumCmdErr(sCommand);
				}
			} else {
				captureScreenShot();
				reportElementNotFound(sCommand,sResult);
			}

		} catch (Exception e) {

			e.printStackTrace();
			captureScreenShot();
			reportException(sCommand, sResult);
		}
		return "";
	}

	/**
	 * This method takes selenese command in the form of string and checks that
	 * string is available in the enum or not. If string is available in the
	 * enum list then corresponding case will be executed and will return array
	 * of type String.
	 * 
	 * @param sCommand
	 *            Selenese Command
	 * @return array of Strings
	 */
	public String[] performArray(String sCommand) {
		try {
			enumCommand = checkEnumConstant(sCommand);
			switch (enumCommand) {
			case getAllWindowNames:
				Set<String> availableWindows = driver.getWindowHandles();
				String[] windowHandles = availableWindows
						.toArray(new String[availableWindows.size()]);
				
				reportStepDetails("Retrieving all window names","No of windows identified:\"" + windowHandles.length+ "\"", PASS);
				return windowHandles;
			default:
				reportUnknowSeleniumCmdErr(sCommand);
			}
		} catch (Exception e) {
			e.printStackTrace();
			reportException(sCommand, e.toString());
		}
		return null;
	}
	
	/**
	 * This method takes selenese command and target in the form of string and
	 * checks that string is available in the enum or not. If string is
	 * available in the enum list then corresponding case will be executed and
	 * will return array of type String.
	 * 
	 * @param sCommand
	 *            Selenese Command
	 * @param sTarget
	 *            an element locator
	 * @return array of Strings
	 */
	public String[] performArray(String sCommand, String sTarget) {
		int sTargetFlag = 0;
		try {
			if (sTarget.contains("&&")) {
				String[] sTargetArray = sTarget.split("&&");
				for (int t = 0; t < sTargetArray.length; t++) {
					if (isElementPresent(sTargetArray[t])) {
						sTarget = sTargetArray[t];
						sTargetFlag = 1;
						break;
					}
				}
			} else if (isElementPresent(sTarget)) {
				sTargetFlag = 1;
			}

			if (sTargetFlag == 1) {
				commandName.add(sCommand);
				enumCommand = checkEnumConstant(sCommand);
				switch (enumCommand) {
				case getSelectOptions:
					Select select = new Select(webElement(sTarget));
					List<WebElement> options = select.getOptions();
					String[] optionnames = new String[options.size()];
					for (int i = 0; i < options.size(); i++) {
						WebElement we = options.get(i);
						optionnames[i] = we.getText();
					}
					
					reportStepDetails("Retrieving all list of options from select drop-down","No of options available in the drop-down \""+ optionnames.length + "\"", PASS);
					return optionnames;
				default:
					reportUnknowSeleniumCmdErr(sCommand);
				}
			} else {
				captureScreenShot();
				reportElementNotFound(sCommand);
			}
		} catch (Exception e) {
			e.printStackTrace();
			reportException(sCommand, e.toString());
		}
		return null;
	}

	/**
	 * This method takes selenese command and target in the form of string and
	 * checks that string is available in the enum or not. If string is
	 * available in the enum list then corresponding case will be executed and
	 * will return array of type String.
	 * 
	 * @param sCommand
	 *            Selenese Command
	 * @param sTarget
	 *            an element locator
	 * @param sTitle
	 *            labelname (or) fieldname
	 * @return array of Strings
	 */
	public String[] performArray(String sCommand, String sTarget, String sTitle) {
		int sTargetFlag = 0;
		String encodedTitle = "";
		try {
			if (sTarget.contains("&&")) {
				String[] sTargetArray = sTarget.split("&&");
				for (int t = 0; t < sTargetArray.length; t++) {
					if (isElementPresent(sTargetArray[t])) {
						sTarget = sTargetArray[t];
						sTargetFlag = 1;
						break;
					}
				}
			} else if (isElementPresent(sTarget)) {
				sTargetFlag = 1;
			}

			if (sTargetFlag == 1) {
				commandName.add(sCommand);
				enumCommand = checkEnumConstant(sCommand);
				switch (enumCommand) {
				case getSelectOptions:
					encodedTitle = utility.encodeSpecialCharacters(utility.getExcelData(sTitle));
					Select select = new Select(webElement(sTarget));
					List<WebElement> options = select.getOptions();
					String[] optionnames = new String[options.size()];
					for (int i = 0; i < options.size(); i++) {
						WebElement we = options.get(i);
						optionnames[i] = we.getText();
					}
					
					reportStepDetails("Retrieving all list of options from drop-down \""+ encodedTitle + "\"","No of options available in the drop-down \""+ encodedTitle + "\": "+ optionnames.length, PASS);
					return optionnames;
				default:
					reportUnknowSeleniumCmdErr(sCommand);
				}
			} else {
				captureScreenShot();
				reportElementNotFound(sCommand,sTitle);
			}
		} catch (Exception e) {
			e.printStackTrace();
			reportException(sCommand, e.toString());
		}
		return null;
	}
	
	public boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException Ex) {
			return false;
		}
	}
	
	public boolean isElementPresent(String sTarget) {
		try {					
			webElement(sTarget);
			return true;
		} catch (NoSuchElementException e) {			
			return false;
		}catch(StaleElementReferenceException s){
			return false;
		}
		catch(Exception e){
			return false;
		}
	}
	
	/**
	 * This method reports element not found message and stops execution
	 * @param sCommand
	 *            selenese command specified in the script
	 */
	private void reportElementNotFound(String sCommand) {
		
		reportStepDtlsWithScreenshot("The element of a command " + "\""+ sCommand + "\" not found","The element of a command " + "\""+ sCommand + "\" not found at step : " + iStepCount+ " : on the displayed page", FAIL);
	}
	
	private void reportElementNotFound(String sCommand,String sTitle) {

		reportStepDtlsWithScreenshot("\""+sTitle+"\" element  locator not found","The \""+sTitle+"\" element  locator not found to " + "\""+ sCommand + "\" at step : " + iStepCount+ " : on the displayed page", FAIL);
	}
	
	/**
	 * This method reports error message corresponding to exception and stops
	 * execution.
	 * 
	 * @param sCommand
	 *            selenese command specified in the script
	 * @param sErrMsg
	 *            error message corresponding to exception
	 */
	private void reportException(String sCommand, String elementName) {
		
		commandName.add("Exception");
		reportStepDtlsWithScreenshot(sCommand,elementName+" element is not found", FAIL);
	}
	
	/**
	 * This method reports unknown selenium command error to end user.
	 * 
	 * @param sCommand
	 *            selenese command specified in the script
	 */
	private void reportUnknowSeleniumCmdErr(String sCommand) {
		commandName.add("UnKnown Command");
		reportStepDtlsWithScreenshot("Unknown Framework Command " + "\"" + sCommand + "\"", "Unknown Selenium/effecta Command " + "\""+ sCommand + "\"please Contact Automtaion team", FAIL);
	}
	
	public WebElement webElement(String sTarget) {

		if (sTarget.startsWith("css")) {
			sTarget = sTarget.substring(4);
			return driver.findElement(By.cssSelector(sTarget));
		}
		if (sTarget.startsWith("//") ) {
			return driver.findElement(By.xpath(sTarget));
		}
		if (sTarget.startsWith("xpath")) {
			sTarget = sTarget.substring(6);
			return driver.findElement(By.xpath(sTarget));
		}
		if (sTarget.startsWith("name")) {
			sTarget = sTarget.substring(5);
			return driver.findElement(By.name(sTarget));
		}
		if (sTarget.startsWith("id")) {
			sTarget = sTarget.substring(3);
			return driver.findElement(By.id(sTarget));
		}
		if (sTarget.startsWith("link")) {
			sTarget = sTarget.substring(5);
			return driver.findElement(By.linkText(sTarget));
		}
		if (sTarget.startsWith("class")) {
			String index = sTarget.substring(sTarget.length()-1,sTarget.length());
			sTarget = sTarget.substring(5,sTarget.length()-1);
			return (WebElement)driver.findElementsByClassName(sTarget).get(Integer.parseInt(index));
		}
		
		return null;
	}
	
	/**
	 * This method is used to capture screenshot and it will not terminate if stepstatus is fail
	 * @param ver
	 * @param des
	 * @param stepStatus
	 */
	public void reportStepDtlsWithScreenshotWithoutExit(String ver, String des,String stepStatus) {

		captureScreenShot();

		verification.add(ver);	
		description.add(des +"Screenshot:"+ testScriptName + "_"+iStepCount + ".png");
		System.out.println("Report step details method path:"+des + "Screenshot:" + automationPath +"Results" +File.separator+ "Screenshots"
				+ File.separator + testScriptName + "_" + utility.getCurrentDateNTime() + "_"+ iStepCount + ".png");
		status.add(stepStatus);
		dateTime.add(utility.getExecutionTime());		
		if(stepStatus.equalsIgnoreCase(FAIL))
			sResultFlag = stepStatus;

	}
	
	/**
	 * Description: Useful to get value/data of the provided key of Currently executing Test Script
	 * 
	 * @param columnName (Name of the cell/Column Heading in data sheet)
	 * @return value of the provided key
	 */
	public  String readDataFromExcel(String columnName) {

		String resultValue = "";
		String sInputDataFilePath = automationPath + "Data"
				+ File.separator + projectName + File.separator +
				"scripts" + File.separator + moduleName;

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

						if(FilenameUtils.getBaseName(fileName).equals(testScriptName)) {
							dataFilePath = fileName;
							flag = true;
							break;
						}					
					}
				} else {
					System.err.println("No Data Files Are Available In The Path: " +  sInputDataFilePath);
				}

				if(!flag) {
					System.err.println("Data File Is Not Available In \"" + sInputDataFilePath +"\" For The Class: \"" +  testScriptName + "\"");
				}
			} else {
				System.err.println("Please Verify The Specified Class:"+ testScriptName +" Packages : " + sInputDataFilePath);
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

					int cellCount = keysRow.getLastCellNum();

					for(int i=0; i<cellCount; i++) {

						Cell cell = keysRow.getCell(i);
						if(cell==null) {
							continue;
						}
						String key = cell.getStringCellValue();

						if(key.equals(columnName)) {
							Row valuesRow = worksheet.getRow(1);

							if(valuesRow == null) {
								System.err.println("NO DATA IS AVAILABLE IN THE DATA FILE: " +  dataFilePath);
							} else {

								Cell valCell = valuesRow.getCell(i);
								if(valCell == null) {
									resultValue = "";
								} else {
									switch (valCell.getCellType()) {
									case 0:  
										resultValue = "" + Double.valueOf(valCell.getNumericCellValue()).longValue();  
										break;  
									case 1:  
										resultValue = "" + valCell.getStringCellValue();  
										break;  
									case 2:                               
										break;
									}
								}

							}
						}							
					}							
				} else {
					System.err.println("Please Provide Data For The Data File: " + dataFilePath);
				}						
			} else {
				commandName.add("readDataFromExcel");
				reportStepDetails("Retrieving TestData","TestData sheet doesn't exists under path \""+  sInputDataFilePath + "\"", FAIL);
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
		return resultValue;
	}
	
	public By by(String sTarget) {
		if (sTarget.startsWith("css")) {
			sTarget = sTarget.substring(4);
			return By.cssSelector(sTarget);
		}
		if (sTarget.startsWith("//") ) {
			return By.xpath(sTarget);
		}
		if (sTarget.startsWith("xpath")) {
			sTarget = sTarget.substring(6);
			return By.xpath(sTarget);
		}
		if (sTarget.startsWith("name")) {
			sTarget = sTarget.substring(5);
			return By.name(sTarget);
		}
		if (sTarget.startsWith("id")) {
			sTarget = sTarget.substring(3);
			return By.id(sTarget);
		}
		if (sTarget.startsWith("link")) {
			sTarget = sTarget.substring(5);
			return By.linkText(sTarget);
		}
		return null;
	}
	
	/**
	 * This method is used to getURL from constant Properties file which is in Constants folder
	 * @param Key of the url in properties file
	 * @param instance name
	 * 
	 */
	public String getURL(String constantName, String instanceName){
		String url = "";
		try {

			Properties prop = new Properties();

			prop.load(new FileInputStream(automationPath + projectName + "\\Constants\\constant.properties"));
			url = prop.getProperty(constantName);
			if(url!=null){
				System.out.println("URL retrived successfully:"+url);
			}
			else
				System.err.println("url is not found");
			if(instanceName.toLowerCase().equals("prod")){
				if (url.contains("instanceName")){
					url = url.replace("-instanceName", "");
					if(url.contains("comm")){
						url = url.replace("comm", "communities");
					}
				}else{
					if(url.contains("comm")){
						url = url.replace("comm", "communities");
					}
				}

			}else{
				if (url.contains("instanceName"))
					url = url.replace("instanceName", instanceName);
			}
		}
		catch (FileNotFoundException e) {
			System.err.println("Contants Properties File: " + automationPath + projectName + "\\Constants\\constant.properties" + "Not Found");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return url;
	}
	
	/**
	 * 
	 * @param constantName
	 * @return
	 */
	public String getConstantValue(String constantName) {
		String url = "";	   
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(automationPath + projectName + "\\Constants\\constant.properties"));
			url = prop.getProperty(constantName);	    
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return url;
	}
}
