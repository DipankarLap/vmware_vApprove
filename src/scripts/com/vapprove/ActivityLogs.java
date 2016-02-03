package scripts.com.vapprove;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import scripts.com.concur.Login;

public class ActivityLogs extends Login {

	@BeforeTest
	@Parameters("env")
	public   void beforeTest(String env){
		super.testScriptName = "ActivityLogs";
		super.testSetName = "ActivityLogs Desc";
		setTestEnvironment(env);
	}


	@Test
	public void activityLog() throws Exception{
		setup_Login(2);
		Thread.sleep(20000);
		mobileAppDriver.driver.manage().timeouts().implicitlyWait(800, TimeUnit.SECONDS);

		//slider click		
		mobileAppDriver.perform("click","//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[2]/android.view.View[2]/android.view.View[1]/android.widget.ImageView[1]", "Slider");

		mobileAppDriver.perform("captureEntirePageScreenshot", "Slider screen",  "Slider screen", "Pass");

		//click Activity logs
		mobileAppDriver.perform("click", "//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[2]/android.view.View[2]/android.view.View[1]/android.widget.FrameLayout[1]/android.widget.ListView[1]/android.widget.FrameLayout[2]/android.view.View[1]/android.view.View[1]/android.view.View[2]/android.widget.TextView[1]", "Activitylog");
		Thread.sleep(9000);

		//screenshot
		mobileAppDriver.perform("captureEntirePageScreenshot", "Activity Log screen",  "Activity Log screen", "Pass");


		//click Filter
		mobileAppDriver.perform("click", "name=Filter", "Filter");
		Thread.sleep(800);

		//Screenshot
		mobileAppDriver.perform("captureEntirePageScreenshot", "Filter options",  "Filter options", "Pass");

		//click concur
		mobileAppDriver.perform("click", "//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[4]/android.widget.FrameLayout[1]/android.view.View[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.widget.ListView[1]/android.widget.FrameLayout[2]/android.view.View[1]/android.view.View[1]/android.widget.TextView[1]", "Concur");
										  //android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[4]/android.widget.FrameLayout[1]/android.view.View[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.widget.ListView[1]/android.widget.FrameLayout[2]/android.view.View[1]/android.view.View[1]/android.widget.TextView[1]
		Thread.sleep(800);
		//Screenshot
		mobileAppDriver.perform("captureEntirePageScreenshot", "Filter Concur",  "Filter Concur", "Pass");

		//click Filter
		mobileAppDriver.perform("click", "name=Filter", "Filter");
		Thread.sleep(500);

		//click Coupa
		mobileAppDriver.perform("click", "//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[4]/android.widget.FrameLayout[1]/android.view.View[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.widget.ListView[1]/android.widget.FrameLayout[3]/android.view.View[1]/android.view.View[1]/android.widget.TextView[1]","Coupa");
		Thread.sleep(800);
		//Screenshot
		mobileAppDriver.perform("captureEntirePageScreenshot", "Filter Coupa",  "Filter Coupa", "Pass");

		//click Filter
		mobileAppDriver.perform("click", "name=Filter", "Filter");
		Thread.sleep(800);

		//click Helpnow
		mobileAppDriver.perform("click", "//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[4]/android.widget.FrameLayout[1]/android.view.View[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.widget.ListView[1]/android.widget.FrameLayout[4]/android.view.View[1]/android.view.View[1]/android.widget.TextView[1]", "HelpNow");
		Thread.sleep(800);
		//screenshot
		mobileAppDriver.perform("captureEntirePageScreenshot", "Filter HelpNow",  "Filter HelpNow", "Pass");

		//slider click		
		mobileAppDriver.perform("click","//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[2]/android.view.View[2]/android.view.View[1]/android.widget.FrameLayout[1]/android.widget.ListView[1]/android.widget.FrameLayout[2]/android.view.View[1]/android.view.View[1]/android.view.View[2]/android.widget.TextView[1]", "Slider");
		Thread.sleep(5000);
		
		//logout
		mobileAppDriver.perform("click", "//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[1]/android.view.View[2]/android.view.View[1]/android.widget.FrameLayout[1]/android.widget.ListView[1]/android.widget.FrameLayout[3]/android.view.View[1]/android.view.View[1]/android.view.View[2]/android.view.View[1]/android.widget.ImageView[1]", "Logout");
		Thread.sleep(800);

		//screenshot
		mobileAppDriver.perform("captureEntirePageScreenshot", "Logout from the application",  "Logged out from the application", "Pass");

	}

}
