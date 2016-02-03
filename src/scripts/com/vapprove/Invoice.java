package scripts.com.vapprove;

import io.appium.java_client.MobileElement;
import io.appium.java_client.SwipeElementDirection;

import java.util.Hashtable;
import java.util.concurrent.TimeUnit;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import scripts.com.framework.*;


public class Invoice extends MobileEngine{

	String projectName			=			"MyVMWareAutomation";
	String moduleName			=			"IOS" + "_" + "I_PHONE";
	String testSetName			=			"Invoice";
	String testScriptName		=			"Invoice";
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
	@BeforeTest
	@Parameters("env")
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

	@Test
	public void invoicetest() throws Exception{	
		String className = "android.widget.EditText";
		String buttonClassName = "android.widget.Button";

		try {

			mobileAppDriver.driver.manage().timeouts().implicitlyWait(1000, TimeUnit.SECONDS);
			mobileAppDriver.perform("sendReport", "Verifying User is able to Launch vApprove App " ,"User is able to Launch vApprove App sucessfully", "Pass");
			Thread.sleep(500);
			//screenshot
			mobileAppDriver.perform("captureEntirePageScreenshot", "Initial Spalsh screen of the application",  "Initial Spalsh screen of the application", "Pass");
			mobileAppDriver.perform("click", "//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[2]/android.widget.FrameLayout[1]/android.view.View[1]/android.widget.TextView[1]", "clicked on the initial splash screen");

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
				
			//Clicking on Invoice tab
			mobileAppDriver.perform("click", "//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[3]/android.view.View[1]/android.widget.FrameLayout[1]/android.widget.ListView[1]/android.widget.FrameLayout[4]/android.view.View[1]/android.view.View[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[2]/android.widget.TextView[1]", "Selected CONCUR option from Dashboard screen");
			Thread.sleep(2000);
			mobileAppDriver.perform("captureEntirePageScreenshot", "verifying CONCUR home screen",  "CONCUR home screen is displayed sucessfully", "Pass");
			if(Integer.parseInt(ticketCount)>0){
			//TicketName
			String ticketname1=mobileAppDriver.perform("getText", "//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[4]/android.view.View[1]/android.view.View[1]/android.widget.FrameLayout[1]/android.widget.ListView[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[2]/android.widget.TextView[2]", "Invoice Ticket Name");
			mobileAppDriver.perform("click", "//android.widget.ListView/android.widget.FrameLayout[1]", ticketname1);
			Thread.sleep(2000);

			//String ticketname = ticketname1.split("Reminder:")[1];
			System.out.println(ticketname1);

			//Amount
			String amount=mobileAppDriver.perform("getText", "//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[2]/android.view.View[2]/android.view.View[1]/android.widget.TextView[2]", " Invoice amount");
			Thread.sleep(2000);				

			//status
			String status=mobileAppDriver.perform("getText","//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[3]/android.widget.FrameLayout[1]/android.widget.ListView[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.widget.TextView[2]", "Invoice status");

			//Invoice Number
			String invoicenumber=mobileAppDriver.perform("getText","//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[3]/android.widget.FrameLayout[1]/android.widget.ListView[1]/android.widget.FrameLayout[2]/android.view.View[1]/android.view.View[1]/android.widget.TextView[2]", "Invoice Number");

			//Invoice Supplier
			String InvoiceSupplier=mobileAppDriver.perform("getText","//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[3]/android.widget.FrameLayout[1]/android.widget.ListView[1]/android.widget.FrameLayout[5]/android.view.View[1]/android.view.View[1]/android.widget.TextView[2]", "Invoice Supplier");

			System.out.println("text is ::::"+ticketname1+"  "+"Amount is ::::"+amount+"   "+"status is::::"+status+"   "+"Invoice Number is :::"+invoicenumber+" "+"Invoice Supplier is:::" +InvoiceSupplier);



			mobileAppDriver.perform("captureEntirePageScreenshot", "Invoice detail Screen",  "Invoice detail screen displayed sucessfully", "Pass");

			mobileAppDriver.driver.swipe(516, 1552, 527, 494, 500);
			mobileAppDriver.driver.swipe(516, 1552, 527, 494, 500);
			mobileAppDriver.driver.swipe(516, 1552, 527, 494, 500);
			mobileAppDriver.driver.swipe(516, 1552, 527, 494, 500);
		
			//**************Rejection***********************


			//TicketName
			String ticketname2=mobileAppDriver.perform("getText", "//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[4]/android.view.View[1]/android.view.View[1]/android.widget.FrameLayout[1]/android.widget.ListView[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[2]/android.widget.TextView[2]", "ticketname");
			mobileAppDriver.perform("click", "//android.widget.ListView/android.widget.FrameLayout[1]", ticketname2);
			Thread.sleep(2000);

			String ticketnam = ticketname1.split("Reminder:")[1];
			System.out.println(ticketnam);


			//Amount
			String amount2=mobileAppDriver.perform("getText", "//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[2]/android.view.View[2]/android.view.View[1]/android.widget.TextView[2]","amount1");
			Thread.sleep(2000);				

			//status
			String status2=mobileAppDriver.perform("getText","//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[3]/android.widget.FrameLayout[1]/android.widget.ListView[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.widget.TextView[2]", "status");

			//Invoice Number
			String invoicenumber2=mobileAppDriver.perform("getText","//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[3]/android.widget.FrameLayout[1]/android.widget.ListView[1]/android.widget.FrameLayout[2]/android.view.View[1]/android.view.View[1]/android.widget.TextView[2]", "Requestor");

			//Invoice Date
			String invoiceDate2=mobileAppDriver.perform("getText","//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[3]/android.widget.FrameLayout[1]/android.widget.ListView[1]/android.widget.FrameLayout[2]/android.view.View[1]/android.view.View[1]/android.widget.TextView[2]", "ExpenseDate");

			//Invoice Supplier
			String InvoiceSupplier2=mobileAppDriver.perform("getText","//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[3]/android.widget.FrameLayout[1]/android.widget.ListView[1]/android.widget.FrameLayout[5]/android.view.View[1]/android.view.View[1]/android.widget.TextView[2]", "SubmissionDate");

			System.out.println("text is ::::"+ticketnam+"  "+"Amount is ::::"+amount2+"   "+"status is::::"+status2+"   "+"Invoice Number is :::"+invoicenumber2+" "+"Invoice Supplier is:::" +InvoiceSupplier2);



			mobileAppDriver.perform("captureEntirePageScreenshot", "Invoice detail Screen",  "Invoice detail screen displayed sucessfully", "Pass");


			int j = 500;
			MobileElement abc =(MobileElement)mobileAppDriver.driver.findElementByName("Invoice Lines"); 
			abc.swipe(SwipeElementDirection.UP, j); 
			abc.swipe(SwipeElementDirection.UP, j); 
			abc.swipe(SwipeElementDirection.UP, j); 
			abc.swipe(SwipeElementDirection.UP, j); 
			abc.swipe(SwipeElementDirection.UP, j); 
			abc.swipe(SwipeElementDirection.UP, j); 
			abc.swipe(SwipeElementDirection.UP, j); 
			abc.swipe(SwipeElementDirection.UP, j);
			abc.swipe(SwipeElementDirection.UP, j); 
			abc.swipe(SwipeElementDirection.UP, j); 
			abc.swipe(SwipeElementDirection.UP, j); 
			abc.swipe(SwipeElementDirection.UP, j);		
			abc.swipe(SwipeElementDirection.UP, j); 
			abc.swipe(SwipeElementDirection.UP, j);
			abc.swipe(SwipeElementDirection.UP, j); 
			abc.swipe(SwipeElementDirection.UP, j); 
			abc.swipe(SwipeElementDirection.UP, j); 
			abc.swipe(SwipeElementDirection.UP, j);	

			MobileElement abc1 =(MobileElement)mobileAppDriver.driver.findElementByName("Purchase Order Lines"); 
			abc1.swipe(SwipeElementDirection.UP, j); 
			abc1.swipe(SwipeElementDirection.UP, j); 
			abc1.swipe(SwipeElementDirection.UP, j); 
			abc1.swipe(SwipeElementDirection.UP, j); 
			abc1.swipe(SwipeElementDirection.UP, j); 
			abc1.swipe(SwipeElementDirection.UP, j); 
			abc1.swipe(SwipeElementDirection.UP, j); 
			abc1.swipe(SwipeElementDirection.UP, j);
			abc1.swipe(SwipeElementDirection.UP, j); 
			abc1.swipe(SwipeElementDirection.UP, j); 
			abc1.swipe(SwipeElementDirection.UP, j); 
			abc1.swipe(SwipeElementDirection.UP, j);
			abc1.swipe(SwipeElementDirection.UP, j); 
			abc1.swipe(SwipeElementDirection.UP, j);
			abc1.swipe(SwipeElementDirection.UP, j); 
			abc1.swipe(SwipeElementDirection.UP, j); 
			abc1.swipe(SwipeElementDirection.UP, j); 
			abc1.swipe(SwipeElementDirection.UP, j);
			abc1.swipe(SwipeElementDirection.UP, j); 
			abc1.swipe(SwipeElementDirection.UP, j); 
			abc1.swipe(SwipeElementDirection.UP, j); 
			abc1.swipe(SwipeElementDirection.UP, j);
			abc1.swipe(SwipeElementDirection.UP, j); 
			abc1.swipe(SwipeElementDirection.UP, j);
			abc1.swipe(SwipeElementDirection.UP, j); 
			abc1.swipe(SwipeElementDirection.UP, j); 
			abc1.swipe(SwipeElementDirection.UP, j); 
			abc1.swipe(SwipeElementDirection.UP, j);

			//Clicking Reject
			mobileAppDriver.perform("click", "//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[3]/android.widget.FrameLayout[1]/android.widget.ListView[1]/android.widget.FrameLayout[8]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.widget.TextView[1]", "Clicked on the Reject Button");

			mobileAppDriver.perform("click", "id=com.vmware.vApprove:id/titanium_ui_edittext", "Clicked the textbox to enter comments");


			//Entering text into reject textbox
			mobileAppDriver.perform("typeSpecifiedText", "id=com.vmware.vApprove:id/titanium_ui_edittext", "Testing", "Comments for rejection");
			Thread.sleep(500);
			mobileAppDriver.perform("captureEntirePageScreenshot", "Verifying the commnets textbox",  "commnets textbox is displayed and accepting the text", "Pass");

			mobileAppDriver.driver.hideKeyboard();
			mobileAppDriver.perform("click", "//android.widget.Button[contains(@text,'Reject')]", "Clicked the Reject Button on the popup");

			Thread.sleep(2000);
			mobileAppDriver.perform("captureEntirePageScreenshot", "verifying the ticket can Reject",  "Ticket can be Rejected sucessfully", "Pass");

			//clicking Done after rejected
			mobileAppDriver.perform("click", "name=Done", "Clicked on the Done Button");
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

