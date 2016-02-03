package scripts.com.framework;

import java.io.IOException;
import java.util.Hashtable;

import org.testng.annotations.AfterClass;

import scripts.com.framework.MobileAppDriver;

public class MobileEngine {

	public MobileAppDriver mobileAppDriver;
	
	public MobileEngine(){
		
	}
	
	/**
	 * Instantiates and yields MobileAppDriver in compliance with specifications provided
	 * 
	 * @param projectName
	 * @param moduleName
	 * @param testSetName
	 * @param testScriptName
	 * @param userName
	 * @param accessKey
	 * @param mobileCapabilities
	 * @param appPath
	 * @param appPackage
	 * @param appActivity
	 */
	public void setUp(String projectName, String moduleName,
			String testSetName, String testScriptName, String userName,
			String accessKey, Hashtable<String, String> mobileCapabilities,
			String appPath, String appPackage, String appActivity) {

		try {
			mobileAppDriver = new MobileAppDriver(projectName, moduleName,testSetName, testScriptName, userName, accessKey, mobileCapabilities,appPath, appPackage, appActivity);
			Thread.sleep(3000);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method is used to quit the driver and generate the html report
	 * @throws IOException
	 */
	@AfterClass
	public void oneTearDown() throws IOException {

		mobileAppDriver.stopSelenium();
	}
	
}
