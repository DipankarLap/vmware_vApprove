package scripts.com.coupa;

import scripts.com.utils.Xls_Reader;
//import com.vmware.vApproveCentral.Events;
import scripts.com.concur.Login;
import scripts.com.framework.MobileAppDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.SwipeElementDirection;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class coupaSearch extends Login {


	Map<String, String>searchResult=new HashMap<String, String>();
	Xls_Reader output=null;
	public static Xls_Reader xlsReader_in=new Xls_Reader("D:\\Workspace\\Mobile_Framework\\vApprove\\Coupadata\\coupa_data.xlsx");
	

	@BeforeTest
	@Parameters("env")
	public   void beforeTest(String env){
		super.testScriptName = "coupaSearch";
		super.testSetName = "coupaSearch Desc";
		setTestEnvironment(env);
		output=new Xls_Reader("D:\\Workspace\\Mobile_Framework\\vApprove\\Coupadata\\coupaoutput.xlsx");
	}

	@Test
	public void searchtest() throws Exception{	

		try{

			for(int i=2;i<=xlsReader_in.getRowCount("input");i++){
				for(int k=2;k<=xlsReader_in.getRowCount("login");k++){
					setup_Login(k);
					Thread.sleep(20000);
					
					//Click on Coupa tab
					mobileAppDriver.perform("click", "//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[3]/android.view.View[1]/android.widget.FrameLayout[1]/android.widget.ListView[1]/android.widget.FrameLayout[2]/android.view.View[1]/android.view.View[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[2]/android.widget.TextView[1]", "Selected CONCUR option from Dashboard screen");
					Thread.sleep(2000);
					mobileAppDriver.perform("captureEntirePageScreenshot", "verifying CONCUR home screen",  "CONCUR home screen is displayed sucessfully", "Pass");
		
					//click("name", "Search");
					mobileAppDriver.perform("click","//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[2]/android.view.View[2]/android.view.View[1]/android.widget.ImageView[1]", "Search");
					//Entering the text to Search
					mobileAppDriver.perform("type","//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[2]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[2]/android.widget.FrameLayout[1]/android.widget.EditText[1]",output.getCellData("OutputData", "TicketName", 2), "Search");
					Thread.sleep(200);
				
					//Ticket Name
					String ticketname1=mobileAppDriver.perform("getText", "//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[3]/android.widget.FrameLayout[1]/android.widget.ListView[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.widget.TextView[1]","ticketname1");
					String ticketname=ticketname1.split(":")[1].trim();
					System.out.println(ticketname);

					mobileAppDriver.perform("click", "//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[3]/android.widget.FrameLayout[1]/android.widget.ListView[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.widget.TextView[1]", "");
					Thread.sleep(2000);

					//Amount
					String amount1=mobileAppDriver.perform("getText", "//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[2]/android.view.View[2]/android.view.View[1]/android.widget.TextView[2]", "Ticket Amount");
					Thread.sleep(2000);
					String amount=amount1.split("Rs.")[1];

					//Company
					String Company=mobileAppDriver.perform("getText", "//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[3]/android.widget.FrameLayout[1]/android.widget.ListView[1]/android.widget.FrameLayout[2]/android.view.View[1]/android.view.View[1]/android.widget.TextView[2]", "Ticket Company");


					if(ticketname.equalsIgnoreCase(output.getCellData("OutputData", "TicketName", 2))){
						mobileAppDriver.perform("sendReport", "Comparing Ticket Name", "Ticket Name on Portal: " + output.getCellData("OutputData", "TicketName", 2)+" ; "+"Ticket NAme in Mobile: "+ticketname, "Pass");				
					}else{
						mobileAppDriver.perform("sendReportWithOutExit", "Comparing Ticket Name", "Ticket Name on Portal: " + output.getCellData("OutputData", "TicketName", 2)+" ; "+"Ticket NAme in Mobile: "+ticketname, "Fail");
					}

					if(amount.equalsIgnoreCase(output.getCellData("OutputData", "Amount", 2))){
						mobileAppDriver.perform("sendReport", "Comparing Amount Value", "Amount Value in Portal: " + output.getCellData("OutputData", "Amount", 2)+" ; "+"Amount Value in Mobile: "+amount, "Pass");				
					}else{
						mobileAppDriver.perform("sendReportWithOutExit", "Comparing Amount Value", "Amount Value in Portal: " + output.getCellData("OutputData", "Amount", 2)+" ; "+"Amount Value in Mobile: "+amount, "Fail");
					}

					if(Company.equalsIgnoreCase(output.getCellData("OutputData", "Supplier", 2))){
						mobileAppDriver.perform("sendReport", "Comparing Supplier Name", "Supplier Name in Portal: " + output.getCellData("OutputData", "Supplier", 2)+" ; "+"Supplier Name in Mobile: "+Company, "Pass");				
					}else{
						mobileAppDriver.perform("sendReportWithOutExit","Comparing Supplier Name", "Supplier Name in Portal: " + output.getCellData("OutputData", "Supplier", 2)+" ; "+"Supplier Name in Mobile: "+Company, "Fail");
					}
					//Swipe to Approve
			//		mobileAppDriver.driver.swipe(290, 1012, 280, 600, 1000);
					mobileAppDriver.driver.swipe(1151, 2246, 1224,1484,1000);
					Thread.sleep(2000);
					
					//Capture screenshot			
					//Screenshot(ticketname);
					mobileAppDriver.perform("captureEntirePageScreenshot", ticketname,  ticketname, "Pass");

					if(xlsReader_in.getCellData("input", "Approve/Reject", 2).equalsIgnoreCase("Approve")){
						//Click on approve
						mobileAppDriver.perform("click","name=Approve", "Approve Button");

						Thread.sleep(200);
						//pop up approve button
						mobileAppDriver.perform("click","//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.widget.ScrollView[1]/android.view.View[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[4]/android.widget.Button[1]", "Approve Button on popup message");

						//click on Done after approved
						mobileAppDriver.perform("click","//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.widget.ScrollView[1]/android.view.View[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[4]/android.widget.Button[1]", "Done Button");
						Thread.sleep(1000);
					}
					else if(xlsReader_in.getCellData("input", "Approve/Reject", 2).equalsIgnoreCase("Reject")){

						//Clicking Reject
						mobileAppDriver.perform("click", "//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[3]/android.widget.FrameLayout[1]/android.widget.ListView[1]/android.widget.FrameLayout[9]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.widget.TextView[1]", "Reject Button");
						
						Thread.sleep(2000);
						mobileAppDriver.perform("captureEntirePageScreenshot", "verifying the Reject popup message",  "Reject popup message displayed sucessfully", "Pass");

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
					
					}
					else{
						System.out.println("Neither approve nor reject");
						WebElement backbtn = (WebElement) mobileAppDriver.driver.findElementsByClassName("android.widget.ImageView").get(0);
						backbtn.click();
						Thread.sleep(2000);
					}

				}

			}
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		//Logout from the Application
		mobileAppDriver.perform("click","//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[2]/android.view.View[1]/android.view.View[1]/android.widget.ImageView[1]");
		Thread.sleep(2000);
		mobileAppDriver.perform("click","//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[2]/android.view.View[2]/android.view.View[2]/android.view.View[1]/android.widget.ImageView[1]");
		Thread.sleep(2000);
		mobileAppDriver.perform("click","//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[2]/android.view.View[1]/android.widget.FrameLayout[1]/android.widget.ListView[1]/android.widget.FrameLayout[3]/android.view.View[1]/android.view.View[1]/android.view.View[2]/android.view.View[1]/android.widget.ImageView[1]");
		System.out.println("Logout Successfull");	
	}
}



