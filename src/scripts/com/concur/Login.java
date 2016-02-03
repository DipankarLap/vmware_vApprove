package scripts.com.concur;

import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import scripts.com.framework.MobileEngine;
import scripts.com.utils.Xls_Reader;

public class Login extends MobileEngine {
	
	public  Xls_Reader xlsReader=new Xls_Reader("D:\\Workspace\\Mobile_Framework\\vApprove\\Data\\vApproveData.xlsx");
	String env;
	 
	
	String projectName			=			"vApproveAutomation";
	String moduleName			=			"Android" + "_" + "I_PHONE";
	public String testSetName			=			"";
	public String testScriptName		=			"";
	String appPackage			=			"com.vmware.vApprove";
	String appActivity			=			".VapproveActivity";

	//Sauce variables
	String appPath				=			"sauce-storage:vApprove.zip";
	String userName				=			"rrouthu";
	String accessKey			=			"62522eef-7e87-4ac3-ac17-f67c4bacc736";
	Hashtable<String, String> mobileCapabilities;


	/**
	 *  Sets the Test Environment with the given environmental details 
	 */
	
	public void setTestEnvironment(String env) {

		if("Local".equalsIgnoreCase(env))
			this.userName = "Local";

		//working settings for Android
		mobileCapabilities = new Hashtable<String, String>();
		mobileCapabilities.put("appiumVersion", "1.3.7");
		mobileCapabilities.put("deviceName", "Android Emulator");
		mobileCapabilities.put("deviceOrientation", "portrait");
		mobileCapabilities.put("browserName", "");
		mobileCapabilities.put("platformVersion", "4.4");
		mobileCapabilities.put("platformName", "Android");
		mobileCapabilities.put("appPackage", appPackage);
		mobileCapabilities.put("appActivity", appActivity);

		super.setUp(projectName, moduleName,testSetName, testScriptName, userName, accessKey, mobileCapabilities, appPath, appPackage, appActivity);
	}

	/**
	 * Actual method under Test
	 */
	public  void setup_Login(int j) throws Exception {
		mobileAppDriver.driver.manage().timeouts().implicitlyWait(2000,TimeUnit.SECONDS);

		String className = "android.widget.EditText";
		String buttonClassName = "android.widget.Button";

		try {
			mobileAppDriver.perform("sendReport", "Verifying User Ability to Launch vApprove App " ,"User able to Launch vApprove App", "Pass");

	    //Clicking the Login Button on the initial splash screen to obtain the Login screen
		mobileAppDriver.perform("click", "//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[2]/android.widget.FrameLayout[1]/android.view.View[1]/android.widget.TextView[1]", "Initial Login Button");							   
		Thread.sleep(20000);
		mobileAppDriver.perform("type", "class"+className+"0", xlsReader.getCellData("Login", "UserName", j), "User Name");
		Thread.sleep(300);
		mobileAppDriver.driver.findElementByClassName("android.widget.ImageView").click();
		
		mobileAppDriver.perform("type", "class"+className+"1", xlsReader.getCellData("Login", "Password", j), "Password");
		Thread.sleep(300);
		mobileAppDriver.driver.findElementByClassName("android.widget.ImageView").click();
		Thread.sleep(3000);
		
		//Screenshot
		mobileAppDriver.perform("captureEntirePageScreenshot", "verifying Login screen page",  "Entered username and Password sucessfully", "Pass");
		mobileAppDriver.perform("click", "class"+buttonClassName+"0", "Login Button");
		Thread.sleep(20000);


	}
	catch (InterruptedException e) {
		e.printStackTrace();
	}
}

}