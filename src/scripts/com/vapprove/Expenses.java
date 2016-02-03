package scripts.com.vapprove;

import io.appium.java_client.MobileElement;
import io.appium.java_client.SwipeElementDirection;

import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import scripts.com.framework.*;


public class Expenses extends MobileEngine{

	String projectName			=			"MyVMWareAutomation";
	String moduleName			=			"IOS" + "_" + "I_PHONE";
	String testSetName			=			"Expenses";
	String testScriptName		=			"Expenses";
	String appPackage			=			"com.vmware.vApprove";
	String appActivity			=			".VapproveActivity";

	//Sauce variables
	String appPath				=			"sauce-storage:vApprove.zip";
	String userName				=			"rrouthu";
	String accessKey			=			"62522eef-7e87-4ac3-ac17-f67c4bacc736";
	Hashtable<String, String> mobileCapabilities;


	/**
	 *  Sets the Test Environment with the given environmental details 
	 * @throws InterruptedException 
	 */
	@BeforeTest
	@Parameters("env")
	public void setTestEnvironment(String env) throws InterruptedException {
		

		if("Local".equalsIgnoreCase(env))
			this.userName = "Local";

		//working settings for Android
		mobileCapabilities = new Hashtable<String, String>();
		mobileCapabilities.put("appiumVersion", "1.3.7");
		mobileCapabilities.put("deviceName", "Android Emulator");
		mobileCapabilities.put("deviceOrientation", "portrait");
		mobileCapabilities.put("browserName", "null");
		mobileCapabilities.put("platformVersion", "4.4");
		mobileCapabilities.put("platformName", "Android");
		mobileCapabilities.put("appPackage", appPackage);
		mobileCapabilities.put("appActivity", appActivity);

		super.setUp(projectName, moduleName,testSetName, testScriptName, userName, accessKey, mobileCapabilities, appPath, appPackage, appActivity);
	}

	/**
	 * Actual method under Test
	 * @throws InterruptedException 
	 */
	@Test
	public void expensetest() throws InterruptedException {
		String className = "android.widget.EditText";
		
		try {
			mobileAppDriver.driver.manage().timeouts().implicitlyWait(1000, TimeUnit.SECONDS);
			mobileAppDriver.perform("sendReport", "Verifying User is able to Launch vApprove App " ,"User is able to Launch vApprove App sucessfully", "Pass");
			Thread.sleep(500);
			//screenshot
			mobileAppDriver.perform("captureEntirePageScreenshot", "Initial Spalsh screen of the application",  "Initial Spalsh screen of the application", "Pass");		
			mobileAppDriver.perform("click", "//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.widget.TextView[1]", "clicked on the initial splash screen");
											  //android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.widget.TextView[1]
											  //android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[2]/android.widget.FrameLayout[1]/android.view.View[1]/android.widget.TextView[1]
			Thread.sleep(500);

			//UserName
			mobileAppDriver.perform("typeSpecifiedText", "class"+className+"0", "sdeswal", "User Name");
			mobileAppDriver.driver.findElementByName("Approval Central").click();

			//password
			mobileAppDriver.perform("typePassword", "class"+className+"1", "testing", "Password");
			mobileAppDriver.driver.findElementByName("Approval Central").click();
			Thread.sleep(500);

			mobileAppDriver.perform("captureEntirePageScreenshot", "verifying Login screen page",  "Entered username and Password sucessfully", "Pass");

			mobileAppDriver.perform("click", "name=Login", "Login button displayed");			

			WebDriverWait wait = new WebDriverWait(mobileAppDriver.driver, 10);
			WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[3]/android.view.View[1]/android.widget.FrameLayout[1]/android.widget.ListView[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.widget.FrameLayout[1]/android.view.View[1]")));
			Thread.sleep(20000);
			String ticketCount=mobileAppDriver.perform("getText", "//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[3]/android.view.View[1]/android.widget.FrameLayout[1]/android.widget.ListView[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[2]/android.widget.TextView[1]", "Selected CONCUR option from Dashboard screen");
System.out.println("ticketCount   "+ticketCount);
			//Clicking on Expenses tab
			mobileAppDriver.perform("click", "//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[3]/android.view.View[1]/android.widget.FrameLayout[1]/android.widget.ListView[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[2]/android.widget.TextView[1]", "Selected CONCUR option from Dashboard screen");
			Thread.sleep(2000);              
			mobileAppDriver.perform("captureEntirePageScreenshot", "verifying CONCUR home screen",  "CONCUR home screen is displayed sucessfully", "Pass");
							
			if(Integer.parseInt(ticketCount)>0){			
		/*//TicketName
			Thread.sleep(3000);
			
			String ticketname1=mobileAppDriver.driver.findElement(By.xpath("//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[3]/android.view.View[1]/android.view.View[1]/android.widget.FrameLayout[1]/android.widget.ListView[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[2]/android.widget.TextView[1]")).getText();
			System.out.println("ticket name is "+ticketname1);
			mobileAppDriver.perform("click", "//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[3]/android.view.View[1]/android.view.View[1]/android.widget.FrameLayout[1]/android.widget.ListView[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[2]", "The expense ticket name :" + ticketname1);	
			Thread.sleep(2000);
			
			//to obtain cost center screen
			mobileAppDriver.driver.findElement(By.xpath("//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[3]/android.widget.FrameLayout[1]/android.widget.ListView[1]/android.widget.FrameLayout[6]/android.view.View[1]/android.view.View[1]/android.view.View[1]")).click();
			Thread.sleep(1000);

			mobileAppDriver.perform("captureEntirePageScreenshot", "verifying the presence of Cost Center on the screen",  "Cost Center is displayed sucessfully", "Pass");
			Thread.sleep(1000);
			
			//Back button
			mobileAppDriver.driver.findElementByXPath("//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[2]/android.view.View[1]/android.view.View[1]/android.widget.ImageView[1]").click();
			Thread.sleep(2000);
			mobileAppDriver.driver.swipe(801, 2236, 769, 1416, 1000);
			//mobileAppDriver.driver.swipe(350, 1063, 370, 948, 1000);
			Thread.sleep(2000);
			
			//Clicking approve
			mobileAppDriver.perform("click", "//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[3]/android.widget.FrameLayout[1]/android.widget.ListView[1]/android.widget.FrameLayout[7]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.widget.FrameLayout[2]/android.view.View[1]/android.widget.TextView[1]", "The expense ticket name :" + ticketname1);

			//popup handling  and clicking approve
			mobileAppDriver.perform("click", "//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.widget.ScrollView[1]/android.view.View[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[5]/android.widget.Button[2]", "Approve Button on the Popup");
			Thread.sleep(2000);
			mobileAppDriver.perform("captureEntirePageScreenshot", "verifying the ticket can be approve",  "Ticket approved sucessfully", "Pass");
			//clicking Done after approved
			mobileAppDriver.perform("click", "name=Done", "Done Button");*/
			
/*			//TicketName
			Thread.sleep(3000);
			String ticketname2=mobileAppDriver.driver.findElement(By.xpath("//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[3]/android.view.View[1]/android.view.View[1]/android.widget.FrameLayout[1]/android.widget.ListView[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[2]/android.widget.TextView[1]")).getText();
																			//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[3]/android.view.View[1]/android.view.View[1]/android.widget.FrameLayout[1]/android.widget.ListView[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[2]/android.widget.TextView[1]
			System.out.println("ticket name is "+ticketname2);
			mobileAppDriver.perform("click", "//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[3]/android.view.View[1]/android.view.View[1]/android.widget.FrameLayout[1]/android.widget.ListView[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[2]", "The expense ticket name :" + ticketname2);	
											  //android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[3]/android.view.View[1]/android.view.View[1]/android.widget.FrameLayout[1]/android.widget.ListView[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[2]/android.widget.TextView[1]
			Thread.sleep(2000);
			//SwipeUp
			mobileAppDriver.driver.swipe(801, 2236, 769, 1416, 1000);
			//mobileAppDriver.driver.swipe(350, 1063, 370, 948, 1000);
			Thread.sleep(2000);*/
				//TicketName
				Thread.sleep(3000);
				
				String ticketname2=mobileAppDriver.driver.findElement(By.xpath("//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[3]/android.view.View[1]/android.view.View[1]/android.widget.FrameLayout[1]/android.widget.ListView[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[2]/android.widget.TextView[1]")).getText();
				System.out.println("ticket name is "+ticketname2);
				//mobileAppDriver.perform("click", "//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[3]/android.view.View[1]/android.view.View[1]/android.widget.FrameLayout[1]/android.widget.ListView[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[2]", "The expense ticket name :" + ticketname2);	
				Thread.sleep(2000);
			
				mobileAppDriver.driver.swipe(801, 2236, 769, 1416, 1000);
				//mobileAppDriver.driver.swipe(350, 1063, 370, 948, 1000);
				Thread.sleep(2000);

			//Clicking Reject
			mobileAppDriver.perform("click", "//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[3]/android.view.View[1]/android.view.View[1]/android.widget.FrameLayout[1]/android.widget.ListView[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[2]", "The expense ticket name :" + ticketname2);	
			Thread.sleep(2000);
			mobileAppDriver.perform("click", "//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[3]/android.widget.FrameLayout[1]/android.widget.ListView[1]/android.widget.FrameLayout[7]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.widget.TextView[1]", "The expense ticket name :" + ticketname2);	
			mobileAppDriver.perform("click", "id=com.vmware.vApprove:id/titanium_ui_edittext", "textbox to enter comments");

			//Entering text into reject textbox
			mobileAppDriver.perform("typeSpecifiedText", "id=com.vmware.vApprove:id/titanium_ui_edittext", "Testing", "Comments for rejection");
			Thread.sleep(500);
			mobileAppDriver.perform("captureEntirePageScreenshot", "Verifying the commnets textbox",  "commnets textbox is displayed and accepting the text", "Pass");

			mobileAppDriver.driver.hideKeyboard();
			mobileAppDriver.perform("click", "//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.widget.ScrollView[1]/android.view.View[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[5]/android.widget.Button[2]", "Reject Button on the popup");

			Thread.sleep(2000);
			mobileAppDriver.perform("captureEntirePageScreenshot", "verifying the ticket can Reject",  "Ticket Rejected sucessfully", "Pass");

			//clicking Done after rejected
			mobileAppDriver.perform("click", "name=Done", "Done Button");
			}else{
				//If no Tickets are available 
			
				if(mobileAppDriver.driver.findElement(By.name("No data found.")).isDisplayed()){
				System.out.println("No data found.");
				}
		}
		//Logout from the Application
		mobileAppDriver.perform("click","//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[2]/android.view.View[1]/android.view.View[1]/android.widget.ImageView[1]");
		Thread.sleep(2000);
		mobileAppDriver.perform("click","//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[2]/android.view.View[2]/android.view.View[2]/android.view.View[1]/android.widget.ImageView[1]");
		Thread.sleep(2000);
		mobileAppDriver.perform("click","//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[2]/android.view.View[1]/android.widget.FrameLayout[1]/android.widget.ListView[1]/android.widget.FrameLayout[3]/android.view.View[1]/android.view.View[1]/android.view.View[2]/android.view.View[1]/android.widget.ImageView[1]");
		System.out.println("Logout Successfull");
		Thread.sleep(3000);
		
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	
	}
}
