package scripts.com.framework;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;

import org.openqa.selenium.remote.RemoteWebDriver;

import scripts.com.saucelabs.saucerest.SauceREST;

import com.thoughtworks.selenium.webdriven.commands.WaitForPageToLoad;

public class MobileAppDriver extends FrameworkAPI {

		public String sImagePath = "";
		public String sImageExt = "";
		public String sResultsFolderPath = "";
		public String sResultFilePath = "";
		public String sResultExcelFilePath = "";
		public String sValidationResultFilePath = "";
		String jobID = null;

		public MobileAppDriver(String projectName, String moduleName,
				String testSetName, String testScriptName, String instanceName,
				String sauceUser, Hashtable<String, String> mobileCapabilities) {
			super(projectName, moduleName, testSetName, testScriptName,
					instanceName, sauceUser, mobileCapabilities);
		}

		public MobileAppDriver(String projectName, String moduleName,
				String testSetName, String testScriptName, String sauceUser,
				String accessKey, Hashtable<String, String> mobileCapabilities,
				String appPath, String appPackage, String appActivity) {
			super(projectName, moduleName, testSetName, testScriptName, sauceUser,
					accessKey, mobileCapabilities, appPath, appPackage, appActivity);
		}

		/**
		 * Initialize properties is used to set path for image and result file and
		 * even result excel file
		 */
		public void initializeProperties() {
			this.sImagePath = (automationPath + "MetaDataSources\\" + projectName
					+ "\\" + testScriptName + "\\Images\\");
			this.sImageExt = ".PNG";
			this.sResultsFolderPath = (automationPath + "Results\\" + projectName
					+ "\\" + instance + "\\" + currentUser + "\\"
					+ utility.getDateInDDMMYY() + "\\" + moduleName + "\\"
					+ testSetName + "\\");
			this.sResultFilePath = (this.sResultsFolderPath + testScriptName + "_"
					+ jobID + "_" + sResultFlag + ".html");
			this.sValidationResultFilePath = (this.sResultsFolderPath
					+ testScriptName + "_Test" + "_" + jobID + "_" + sResultFlag + ".html");
			this.sResultExcelFilePath = (this.sResultsFolderPath + testScriptName
					+ "_" + jobID + "_" + sResultFlag + ".xls");
		}

		/**
		 * This method is to quit selenium driver and generate the report
		 */

		public void stopSelenium() {
			try {
				System.out.println("Entered into stop selenium" + iStepCount);
				if (sauceUser.equalsIgnoreCase("Local"))
					stopSeleniumRunningLocally();
				else
					stopSeleniumRunningInSauceLabs();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		/**
		 * This method is to generate the report without quitting the driver
		 */

		public void stopSeleniumWithOutExit() {
			try {
				System.out.println("Entered into stop selenium" + iStepCount);
				if (this.sauceUser.equalsIgnoreCase("Local"))
					stopSeleniumRunningLocallyWithOutExit();
				else
					stopSeleniumRunningInSauceLabsWithOutExit();
				startTime = new Date();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/**
		 * This method will close application, stops webdriver and calls report
		 * generation method.
		 * 
		 * @throws Exception
		 */
		public void stopSeleniumRunningLocally() throws Exception {
			try {
				if (driver != null) {
					jobID = ((RemoteWebDriver) driver).getSessionId().toString();
					System.out.println("jobid=" + jobID);
				} else
					System.out
							.println("***driver object is null while type casting from webdriver to remotewebdriver for jobid****");

				System.out.println("ResultFlag = " + this.sResultFlag);
				endTime = new Date();
				resultXMLFileCreation();
				this.driver.quit();
				if (browserName.length() > 2) {
					killIEAndChromeDrivers();
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				System.out.println("entered into finally");

			}
		}

		/**
		 * This method will close application, stops webdriver and calls report
		 * generation method.
		 * 
		 * @throws Exception
		 */
		public void stopSeleniumRunningLocallyWithOutExit() throws Exception {
			String jobID = null;
			try {
				if (driver != null) {
					jobID = ((RemoteWebDriver) driver).getSessionId().toString();
					System.out.println("Session/Job ID: " + jobID);
				} else
					System.out
							.println("***driver object is null while type casting from webdrier to remotewebdrier for jobid****");
				endTime = new Date();
				resultXMLFileCreation();
				dateTime.clear();
				description.clear();
				status.clear();
				verification.clear();
				commandName.clear();
				iStepCount = 1;

			} catch (Exception e) {

				e.printStackTrace();
			} finally {

				sResultFlag = PASS;
			}
		}
		
		/**
		 * This method will close application, stops web driver and calls report
		 * generation method.
		 * 
		 * @throws Exception
		 */
		public void stopSeleniumRunningInSauceLabs() throws Exception {
			jobID = ((RemoteWebDriver) driver).getSessionId().toString();
			try {
				endTime = new Date();
				resultXMLFileCreation();

			} catch (Exception e) {

				e.printStackTrace();
			} finally {

				updateSauceJobInfo(jobID);
				driver.quit();
				if (browserName.length() > 2) {
					killIEAndChromeDrivers();
				}

			}
		}

		/**
		 * This method generates the report without close the browser generation
		 * method.
		 * 
		 * @throws Exception
		 */
		public void stopSeleniumRunningInSauceLabsWithOutExit() throws Exception {
			jobID = ((RemoteWebDriver) driver).getSessionId().toString();
			try {
				endTime = new Date();
				resultXMLFileCreation();
				dateTime.clear();
				description.clear();
				status.clear();
				verification.clear();
				commandName.clear();
				iStepCount = 1;
			} catch (Exception e) {

				e.printStackTrace();
			} finally {
				updateSauceJobInfo(jobID);
				sResultFlag = PASS;
			}
		}
		
		public void quitBrowser() throws Exception {
			driver.quit();
			killIEAndChromeDrivers();
		}

		/**
		 * This method will kills IE and chrome drivers
		 */
		public void killIEAndChromeDrivers() throws Exception {
			final String KILL = "taskkill /IM ";
			String processName = null;
			if (browserName.toLowerCase().contains("internet")) {
				processName = "IEDriverServer.exe"; // IE process
			} else if (browserName.toLowerCase().contains("chrome")) {
				processName = "chromeDriverServer.exe"; // Chrome process
			}

			Runtime.getRuntime().exec(KILL + processName);
			Thread.sleep(3000); // Allow OS to kill the process
		}
		
		public void testNgFail() {
			fail("Fail");

		}

		public void updateSauceJobInfo(String jobID) {
			SauceREST client = new SauceREST(sauceUser, accessKey);
			System.out.println("ResultFlag: " + sResultFlag);
			if (sResultFlag.equalsIgnoreCase("pass"))
				client.jobPassed(jobID);
			else if (sResultFlag.equalsIgnoreCase("fail"))
				client.jobFailed(jobID);

		}

		public boolean verifyText(String locator, String text) {
			return webElement(locator).getText().contains(text);
		}

		public WaitForPageToLoad waitForPageToLoad() {
			return new WaitForPageToLoad();
		}

		/**
		 * XML File is Created using this method
		 */
		public void resultXMLFileCreation() {
			initializeProperties();

			Writer output1 = null;
			try {
				String xmlstring;
				String username;
				timeDiff = utility.getDateTimeDifference(startTime, endTime);
				duration = utility.getTime(startTime, endTime);
				SimpleDateFormat sdf;
				sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss z");
				String TCStartTime = sdf.format(startTime);
				String TCEndTime = sdf.format(endTime);
				username = System.getProperty("user.name");
				xmlstring = "<?xml version=" + (char) 34 + "1.0" + (char) 34
						+ " encoding=" + (char) 34 + "UTF-8" + (char) 34
						+ " standalone=" + (char) 34 + "no" + (char) 34
						+ "?><?xml-stylesheet type=" + (char) 34 + "text/xsl"
						+ (char) 34 + " href=" + (char) 34 + automationPath
						+ "Results" + File.separator + "Results.xsl" + (char) 34
						+ "?>";
				String newline = System.getProperty("line.separator");
				xmlstring = xmlstring + newline + utility.addspace(2)
						+ "<TestCase>" + newline + utility.addspace(4)
						+ "<Details>" + newline;
				xmlstring = xmlstring + utility.addspace(4) + "<ProjectName>"
						+ projectName + "</ProjectName>" + newline;
				if (browserName.length() > 2) {
					xmlstring = xmlstring + utility.addspace(4) + "<Environment>"
							+ instance + "</Environment>" + newline;
					xmlstring = xmlstring + utility.addspace(4) + "<Browser>"
							+ browserName + "</Browser>" + newline;
				}
				xmlstring = xmlstring + utility.addspace(4) + "<ModuleName>"
						+ moduleName + "</ModuleName>" + newline;
				xmlstring = xmlstring + utility.addspace(4) + "<TestCaseName>"
						+ testScriptName + "</TestCaseName>" + newline;
				xmlstring = xmlstring + utility.addspace(4) + "<User>" + username
						+ "</User>" + newline;
				xmlstring = xmlstring + utility.addspace(4) + "<Date>"
						+ utility.now("yyyy.MM.dd  'at' hh:mm:ss ") + "</Date>"
						+ newline;
				xmlstring = xmlstring + utility.addspace(4) + "<StartTime>"
						+ TCStartTime + "</StartTime>" + newline;
				xmlstring = xmlstring + utility.addspace(4) + "<EndTime>"
						+ TCEndTime + "</EndTime>" + newline;
				xmlstring = xmlstring + utility.addspace(4) + "<Duration>"
						+ timeDiff + "</Duration>" + newline;
				xmlstring = xmlstring + utility.addspace(4) + "<RunId>" + jobID
						+ "</RunId>" + newline;
				xmlstring = xmlstring + utility.addspace(4) + "<AppiumVersion>"
						+ appiumVersion + "</AppiumVersion>" + newline;
				xmlstring = xmlstring + utility.addspace(4) + "<DeviceName>"
						+ deviceName + "</DeviceName>" + newline;
				xmlstring = xmlstring + utility.addspace(4) + "<DeviceOrientation>"
						+ deviceOrientation + "</DeviceOrientation>" + newline;
				xmlstring = xmlstring + utility.addspace(4) + "<PlatformVersion>"
						+ platformVersion + "</PlatformVersion>" + newline;
				xmlstring = xmlstring + utility.addspace(4) + "<PlatformName>"
						+ platformName + "</PlatformName>" + newline;

				xmlstring = xmlstring + utility.addspace(4) + "<AppActivity>"
						+ appActivity + "</AppActivity>" + newline;

				xmlstring = xmlstring + utility.addspace(4) + "<Result>"
						+ sResultFlag + "</Result>" + newline;
				xmlstring = xmlstring + utility.addspace(4) + "</Details>"
						+ newline + newline;
				xmlstring = xmlstring + utility.addspace(4) + "<Steps>" + newline;
				int vSize = verification.size();

				if (vSize < 0) {
					commandName.add("Exception");
					reportStepDetails("No Selenium code " + "\"",
							"Please Contact Functional/Automation team :  ", FAIL);
				}
				for (int i1 = 0; i1 < verification.size(); i1++) {

					xmlstring = xmlstring + utility.addspace(6) + "<Step>"
							+ newline;
					xmlstring = xmlstring + utility.addspace(8) + "<StepNumber>"
							+ (i1 + 1) + "</StepNumber>" + newline;

					xmlstring = xmlstring + utility.addspace(8) + "<Verification>"
							+ verification.get(i1) + "</Verification>" + newline;
					String sDescriptioncontent = description.get(i1);
					if (sDescriptioncontent.contains("Screenshot:")) {
						xmlstring = xmlstring
								+ utility.addspace(8)
								+ "<Description>"
								+ sDescriptioncontent.substring(0,
										sDescriptioncontent.indexOf("Screenshot:"))
								+ "</Description>" + newline;
						xmlstring = xmlstring
								+ utility.addspace(8)
								+ "<Screenshot>"
								+ sDescriptioncontent.substring(sDescriptioncontent
										.indexOf("Screenshot:") + 11)
								+ "</Screenshot>" + newline;
					} else
						xmlstring = xmlstring + utility.addspace(8)
								+ "<Description>" + description.get(i1)
								+ "</Description>" + newline;
					xmlstring = xmlstring + utility.addspace(8) + "<Status>"
							+ status.get(i1) + "</Status>" + newline;
					xmlstring = xmlstring + utility.addspace(8) + "<DateTime>"
							+ dateTime.get(i1) + "</DateTime>" + newline;
					xmlstring = xmlstring + utility.addspace(6) + "</Step>"
							+ newline;
				}

				xmlstring = xmlstring + utility.addspace(4) + "</Steps>" + newline;
				xmlstring = xmlstring + utility.addspace(2) + "</TestCase>"
						+ newline;

				File dir = new File(this.sResultsFolderPath);
				if (!dir.exists()) {
					dir.mkdirs();
				}

				// create xml in the temp folder for testng highlevel reporting
				String resultHtml = this.sResultFilePath;// html report path For

				String tempXml = utility.getTempPath() + testScriptName + ".xml";
				System.out.println("**Temp Xml**" + tempXml);

				// write result xml to temp folder in the tempXmlPath
				File xmlfile = new File(tempXml);
				output1 = new BufferedWriter(new FileWriter(xmlfile));
				output1.write(xmlstring);
				System.out.println("from resultXMLFileCreation()  ---->>>>>    "+utility.getTempPath());
				System.out.println(tempXml  + "  Done");
				System.out.println(resultHtml  + "  Done");
				output1.close();
				generateHtml(tempXml, resultHtml);
				File reportFile = new File(utility.getTempPath()
						+ "CurrentRunReports");
				if (!reportFile.exists()) {
					reportFile.mkdir();
				}

				generateHtml(tempXml, utility.getTempPath() + "CurrentRunReports\\"
						+ testScriptName + ".html");// html report path, generating
													// html report for testng high
													// level reporting
				// xmlfile.delete();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		/**
		 * Takes xml File path and Html file path and generate html report
		 * 
		 * @param xmlFile
		 * @param htmlFilePath
		 */

		public void generateHtml(String xmlFile, String htmlFilePath) {
			String xslFile = automationPath + "Results\\Results.xsl";
			try {
				TransformerFactory tFactory = TransformerFactory.newInstance();
				Transformer transformer = tFactory.newTransformer(new javax.xml.transform.stream.StreamSource(xslFile));
				transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
				transformer.transform(new javax.xml.transform.stream.StreamSource(xmlFile), new javax.xml.transform.stream.StreamResult(new FileOutputStream(htmlFilePath)));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
}
