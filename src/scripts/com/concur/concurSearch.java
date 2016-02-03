package scripts.com.concur;

import scripts.com.utils.Xls_Reader;
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

public class concurSearch extends Login {

	Map<String, String>searchResult=new HashMap<String, String>();
	Xls_Reader output=new Xls_Reader("D:\\Workspace\\Mobile_Framework\\vApprove\\concurexcel\\Expense_output_data.xlsx");
	//Xls_Reader output=new Xls_Reader("C:\\MobileAutomation\\vApprove\\concurexcel\\Expense_output_data.xlsx");//remote
	
	public static Xls_Reader xlsReader_in=new Xls_Reader("D:\\Workspace\\Mobile_Framework\\vApprove\\concurexcel\\Expense_test_data.xlsx");
	//public static Xls_Reader xlsReader_in=new Xls_Reader("C:\\MobileAutomation\\vApprove\\concurexcel\\Expense_test_data.xlsx");//remote
	
	
	@BeforeTest
	@Parameters("env")
	public   void beforeTest(String env){
		super.testScriptName = "concurSearch";
		super.testSetName = "Search Desc";
		setTestEnvironment(env);
		
	}

	@Test
	public void searchtest() throws Exception{	
		
		try{
			mobileAppDriver.driver.manage().timeouts().implicitlyWait(900, TimeUnit.SECONDS);
		for(int i=2;i<=xlsReader_in.getRowCount("TestCases");i++){
			if(xlsReader_in.getCellData("TestCases", "RunMode", i).equalsIgnoreCase("Y")){
				String testCase=xlsReader_in.getCellData("TestCases", "TSID", i);
					for(int k=2;k<=xlsReader.getRowCount("Login");k++){
								
						setup_Login(k);
						Thread.sleep(2000);	
						//Wait until Dashboard screen is displayed
						WebDriverWait wait = new WebDriverWait(mobileAppDriver.driver, 10);
						WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[3]/android.view.View[1]/android.widget.FrameLayout[1]/android.widget.ListView[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.widget.FrameLayout[1]/android.view.View[1]")));
						Thread.sleep(20000);
						
						//Click on Concur tab
						mobileAppDriver.perform("click", "//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[3]/android.view.View[1]/android.widget.FrameLayout[1]/android.widget.ListView[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[2]/android.widget.TextView[1]", "Selected CONCUR option from Dashboard screen");
						Thread.sleep(2000);
						mobileAppDriver.perform("captureEntirePageScreenshot", "verifying CONCUR home screen",  "CONCUR home screen is displayed sucessfully", "Pass");
						
						//click on Search Button	
						mobileAppDriver.perform("click","//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[2]/android.view.View[2]/android.view.View[1]/android.widget.ImageView[1]");
						Thread.sleep(1000);
						//Entering the text to Search
						mobileAppDriver.perform("type","//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[2]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[2]/android.widget.FrameLayout[1]/android.widget.EditText[1]",output.getCellData(testCase, "TicketName", 2), "Search");
						
						//Matching Tickets size
						int availableTickets= Integer.parseInt(mobileAppDriver.perform("getXpathCount","//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[3]/android.widget.FrameLayout[1]/android.widget.ListView[1]/android.widget.FrameLayout", "Search Tickets Size"));
						String ticketname="";
						for(int j=1;j<=availableTickets;j++){
							 ticketname=mobileAppDriver.perform("getText","//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[3]/android.widget.FrameLayout[1]/android.widget.ListView[1]/android.widget.FrameLayout["+j+"]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.widget.TextView[1]","TicketName");
							if(ticketname.equalsIgnoreCase(output.getCellData(testCase, "TicketName", 2))){
								
								mobileAppDriver.perform("click","//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[3]/android.widget.FrameLayout[1]/android.widget.ListView[1]/android.widget.FrameLayout["+j+"]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.widget.TextView[1]","TicketName Matched");
								break;
							}
						}
						//Ticket Name
					String ticketnameExp=mobileAppDriver.driver.findElementByXPath("//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[2]/android.view.View[2]/android.view.View[1]/android.widget.TextView[1]").getText();
					//mobileAppDriver.perform("click","//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[3]/android.view.View[1]/android.view.View[1]/android.widget.FrameLayout[1]/android.widget.ListView[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[2]/android.widget.TextView[1]", "Ticket:"+ticketnameExp);
					Thread.sleep(2000);
			
					//Amount
					String amount1=mobileAppDriver.driver.findElementByXPath("//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[2]/android.view.View[2]/android.view.View[1]/android.widget.TextView[2]").getText();
					Thread.sleep(2000);
					
					String amount=amount1.split("Rs.")[1]; 
					
					//status
					String status=mobileAppDriver.driver.findElementByXPath("//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[3]/android.widget.FrameLayout[1]/android.widget.ListView[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.widget.TextView[2]").getText();
					
					if(ticketnameExp.equalsIgnoreCase(output.getCellData(testCase, "TicketName", 2))){
						mobileAppDriver.perform("sendReport", "Comparing Ticket Name", "Ticket Name on Portal: " + output.getCellData(testCase, "TicketName", 2)+" ; "+"Ticket NAme in Mobile: "+ticketnameExp, "Pass");				
					}else{
						mobileAppDriver.perform("sendReportWithOutExit", "Comparing Ticket Name", "Ticket Name on Portal: " + output.getCellData(testCase, "TicketName", 2)+" ; "+"Ticket NAme in Mobile: "+ticketnameExp, "Fail");
					}
									
					if(amount.equalsIgnoreCase(output.getCellData(testCase, "Amount", 2))){
						mobileAppDriver.perform("sendReport", "Comparing Amount Value", "Amount Value on Portal: " + output.getCellData(testCase, "Amount", 2)+" ; "+"Amount Value in Mobile: "+amount, "Pass");				
					}else{
						mobileAppDriver.perform("sendReportWithOutExit", "Comparing Amount Value", "Amount Value on Portal: " + output.getCellData(testCase, "Amount", 2)+" ; "+"Amount Value in Mobile: "+amount, "Fail");
					}
					
					if(status.equalsIgnoreCase(output.getCellData(testCase, "Status", 2))){
						mobileAppDriver.perform("sendReport", "Comparing Status ", "Status on Portal: " + output.getCellData(testCase, "Status", 2)+" ; "+"Status in Mobile: "+status, "Pass");				
					}else{
						mobileAppDriver.perform("sendReportWithOutExit", "Comparing Status ", "Status on Portal: " + output.getCellData(testCase, "Status", 2)+" ; "+"Amount Value in Mobile: "+status, "Pass");
					}
					
					mobileAppDriver.driver.swipe(350, 1063, 370, 948, 1000);
					
						mobileAppDriver.perform("captureEntirePageScreenshot", "Verifying the approve and Reject buttons on the screen",  "Approve and Reject buttons are displayed on screen", "Pass");

						if(xlsReader_in.getCellData("TestCases", "Approve/Reject", i).equalsIgnoreCase("Approve")){
							
							//Click on approve
							mobileAppDriver.perform("click","//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[3]/android.widget.FrameLayout[1]/android.widget.ListView[1]/android.widget.FrameLayout[8]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.widget.FrameLayout[2]/android.view.View[1]/android.widget.TextView[1]", "Approve Button");
							Thread.sleep(2000);
							mobileAppDriver.perform("captureEntirePageScreenshot", "verifying the ticket can be approve",  "Approve pop message sucessfully displayed", "Pass");
							Thread.sleep(200);
							//pop up approve button
							mobileAppDriver.perform("click","//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.widget.ScrollView[1]/android.view.View[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[5]/android.widget.Button[2]", "Approve Button on Popup message");
							Thread.sleep(2000);
							mobileAppDriver.perform("captureEntirePageScreenshot", "verifying the ticket can be approve",  "Approved the ticket sucessfully", "Pass");
							mobileAppDriver.perform("click", "name=Done", "Done Button");	
						}
						else if(xlsReader_in.getCellData("TestCases", "Approve/Reject", i).equalsIgnoreCase("Reject")){

							//Click on Reject
							mobileAppDriver.perform("click","//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[3]/android.widget.FrameLayout[1]/android.widget.ListView[1]/android.widget.FrameLayout[8]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.widget.TextView[1]", "Reject Button");
							WebElement reject_textbox = mobileAppDriver.driver.findElementById("com.vmware.vApprove:id/titanium_ui_edittext");
							reject_textbox.click();
							mobileAppDriver.perform("type", "//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.widget.ScrollView[1]/android.view.ViewGroup[1]/android.widget.FrameLayout[1]/android.view.ViewGroup[1]/android.view.ViewGroup[2]/android.widget.FrameLayout[1]/android.widget.EditText[1]", "testing","Comment textfield");
							
							Thread.sleep(500);
							mobileAppDriver.perform("captureEntirePageScreenshot", "Verifying the commnets textbox",  "commnets textbox is displayed and accepting the text", "Pass");
							reject_textbox.sendKeys("Testing");
							mobileAppDriver.driver.hideKeyboard();
							
							mobileAppDriver.perform("click","//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.widget.ScrollView[1]/android.view.View[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[5]/android.widget.Button[2]", "Reject Button on popup message");
							Thread.sleep(500);
							Thread.sleep(2000);
							mobileAppDriver.perform("captureEntirePageScreenshot", "verifying the ticket can Reject",  "Ticket Rejected sucessfully", "Pass");
							mobileAppDriver.perform("click", "name=Done", "Done Button");
							Thread.sleep(2000);
						}
						else{
							System.out.println("Neither approve nor reject");
							WebElement backbtn = (WebElement) mobileAppDriver.driver.findElementsByClassName("android.widget.ImageView").get(0);
							backbtn.click();
							Thread.sleep(2000);
						}
									
					}
				
				}
			}
		
		
		
		}catch(Exception e) {
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
