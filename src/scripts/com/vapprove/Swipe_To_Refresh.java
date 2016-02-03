package scripts.com.vapprove;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import scripts.com.concur.Login;

public class Swipe_To_Refresh extends Login {
	
	@BeforeTest
	@Parameters("env")
	public   void beforeTest(String env){
		super.testScriptName = "Swipe_To_Refresh";
		super.testSetName = "Swipe_To_Refresh Desc";
		setTestEnvironment(env);
		}
	@Test
	public void Swipetest() throws Exception{	
		try {	
		setup_Login(2);
		Thread.sleep(20000);
		mobileAppDriver.driver.manage().timeouts().implicitlyWait(800, TimeUnit.SECONDS);
		
		//Pull to refresh
		//mobileAppDriver.driver.swipe(508, 726, 508, 1490, 2000);	
		mobileAppDriver.driver.swipe(377, 185, 390, 702, 2000);	

		Thread.sleep(5000);
		//capturing the Screenshot while performing pull to refresh
		mobileAppDriver.perform("captureEntirePageScreenshot", "Verifying Swipe To Refresh functionality on Home screen",  "Swipe To Refresh functions sucessfully on Home screen", "Pass");
		//Logout from the Application
				mobileAppDriver.perform("click","//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[2]/android.view.View[2]/android.view.View[1]/android.widget.ImageView[1]");
				Thread.sleep(2000);				
				mobileAppDriver.perform("click","//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[2]/android.view.View[2]/android.view.View[1]/android.widget.FrameLayout[1]/android.widget.ListView[1]/android.widget.FrameLayout[3]/android.view.View[1]/android.view.View[1]/android.view.View[2]/android.widget.TextView[1]");
				System.out.println("Logout Successfull");		
				Thread.sleep(20000);		


} catch (InterruptedException e) {
	e.printStackTrace();
}
	}	
	}