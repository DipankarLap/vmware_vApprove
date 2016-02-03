package scripts.com.concur;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.SwipeElementDirection;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import scripts.com.utils.Xls_Reader;

public class Events {

	public static AppiumDriver d;
	public static Xls_Reader xlsReader=new Xls_Reader("D:\\Projects\\ApprovalCenter\\Data\\vApproveData.xlsx");
	//Login login=new Login();

	public void approve(String ticketname) throws Exception{
		d.findElementByName("Approve").click();

		//popup handling  and clicking approve
		d.findElementByXPath("//android.widget.Button[contains(@text,'Approve')]").click();	
		Thread.sleep(500);
		Screenshot(ticketname+"Approve");

		//click on Done after approved
		d.findElementByName("Done").click();
	}
	public void reject() throws InterruptedException{
		d.findElementByName("Reject").click();
		WebElement reject_textbox = d.findElementById("com.vmware.vApprove:id/titanium_ui_edittext");
		reject_textbox.click();

		//Entering text into reject textbox
		reject_textbox.sendKeys("Testing");
		d.hideKeyboard();
		d.findElementByName("Reject").click();
		Thread.sleep(500);
//		Screenshot(ticketname+"Reject");

		//click on Done after rejected
		d.findElementByName("Done").click();
		Thread.sleep(2000);
	}
	public void scroll(){
		int j = 500;
		MobileElement abc =(MobileElement)d.findElementByName("Expense Lines"); 
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
		abc.swipe(SwipeElementDirection.UP, j); 
		abc.swipe(SwipeElementDirection.UP, j); 
		abc.swipe(SwipeElementDirection.UP, j);
	}
	//Method to Capture the Screenshots
	public void Screenshot(String filenameBefore) throws Exception{

		filenameBefore.trim();
		String filename = filenameBefore.replaceAll("[-+.^:,]","");
			org.openqa.selenium.WebDriver augmentedDriver = new Augmenter().augment(d);
			File screenshot = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
			String filenam = "D:\\Projects\\ApprovalCenter\\Screenshots\\"+filename+".jpeg";
			FileUtils.copyFile(screenshot, new File(filenam));
	}
	
	// compare titles
		public boolean compareTitle(String expectedVal) throws IOException{
			try{
				Assert.assertEquals(d.getTitle() , expectedVal   );

			}catch(Throwable t){


				closeBrowser1();

				return false;
			}
			return true;
		}
		// compare titles
		public boolean compareNumbers(int expectedVal, int actualValue) throws IOException{
			try{
				Assert.assertEquals(actualValue,expectedVal   );
			}catch(Throwable t){

				closeBrowser1();
				return false;
			}
			return true;
		}


		// many companies
		public boolean login(String username, String password){
			// log in
			return true;
		}

		

		public void navigateToURL(String URL){
			d.get(URL);
		}


		
		public void openApk() throws MalformedURLException, InterruptedException{
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("device", "Android");
			capabilities.setCapability("deviceName", "052b0137002e3a65");
			capabilities.setCapability("platformName", "Android");
			capabilities.setCapability("autoLaunch", true);
			capabilities.setCapability("platformVersion", "5.1.1");
			capabilities.setCapability("app-package", "com.vmware.vApprove");
			capabilities.setCapability("app-activity", ".VapproveActivity");

			d=new AndroidDriver(new URL("http://127.0.0.1:4000/wd/hub"), capabilities);
		}




		public String getCurrentUrl(){
			String url=null;
			try {

				url=d.getCurrentUrl();
			} catch (Exception e) {
			}

			return url;
		}


		public void setSpeed(int i) throws IOException{
			try{
				d.manage().timeouts().implicitlyWait(i, TimeUnit.MINUTES);
			}catch(Throwable t){

				closeBrowser1();
				return ;
			}
		}

		///////////// implicit wait..............
		public void waitImplicitly(int i) throws IOException{
			try{
				d.manage().timeouts().implicitlyWait(i, TimeUnit.MINUTES);
			}catch(Throwable t){

				closeBrowser1();
				return ;
			}
		}


		// for runtime dynamic web elements
		@SuppressWarnings( "null" )
		public int locatorType(String identifier){
			try{
				int id = 1;
				if(identifier=="id"){
					id=1;
				}else if(identifier=="className"){
					id=2;
				}else if(identifier=="tagName"){
					id=3;
				}else if(identifier=="name"){
					id=4;
				}else if(identifier=="linkText"){
					id=5;
				}else if(identifier=="partialLinkText"){
					id=6;
				}else if(identifier=="cssSelector"){
					id=7;
				}else if(identifier=="xpath"){
					id=8;
				}else if(identifier=="xpaths"){
					id=9;
				}else if(identifier=="tagNames"){
					id=10;
				}else if(identifier=="classNames"){
					id=11;
				}else{
					id=0;
					System.out.println("locator invalid in browser.java");
				}
				return id;
			}catch(Throwable t){

				return ( Integer ) null;
			}

		}

		public WebElement webElementId(String identifier,String locator){
			try{
				int id=locatorType(identifier);
				WebElement e=null;
				switch (id) {
				case 1:
					e=d.findElement(By.id(locator));
					break;
				case 2:
					e=d.findElement(By.className(locator));
					break;
				case 3:
					e=d.findElement(By.tagName(locator));
					break;
				case 4:
					e=d.findElement(By.name(locator));
					break;
				case 5:
					e=d.findElement(By.linkText(locator));
					break;
				case 6:
					e=d.findElement(By.partialLinkText(locator));
					break;
				case 7:
					e=d.findElement(By.cssSelector(locator));
					break;
				case 8:
					e=d.findElement(By.xpath(locator));
					break;
				case 9:
					e=(WebElement) d.findElements(By.xpath(locator));
					break;
				case 10:
					e=(WebElement) d.findElements(By.tagName(locator));
					break;
				case 11:
					e=(WebElement) d.findElements(By.className(locator));
					break;
				default:
					System.out.println("unable identify the element in browser.java");
					e=null;
				}
				return e;
			}catch(Throwable t){

				return null;
			}

		}


		public void sendKeys(String identifier,String locator,String content) throws IOException{
			try{
				WebElement e=webElementId(identifier, locator);
				clear(identifier, locator,content);


			}catch(Throwable t){


				return ;
			}
		}
		// to clear the fields
		public void clear1(String identifier,String locator){
			try{

				WebElement e=webElementId(identifier, locator);
				e.clear();
			}catch(Throwable t){

				return ;
			}
		}

		//to verify whether the field is editable 
		public void clear(String identifier,String locator,String content)
		{
			// boolean state = false;
			WebElement e=webElementId(identifier, locator);
			try{
				if (e.isEnabled())
				{
					// state = true;
					e.clear();
					e.sendKeys(content);
					Reporter.log(identifier+"="+locator+"is editable");
				}else{
					Reporter.log(identifier+"="+locator+"is not editable");
					return;
				}

			}catch(Throwable t){

				return ;
			}
		}


		
		public void click(String idetifier,String locator) throws IOException, InterruptedException{
			//try{
			try{
				WebElement e=webElementId(idetifier,locator);
				System.out.println("came for click on"+locator);
				e.click();
				waitForPageToLoad();
				System.out.println("click action happened on===="+locator);
				Thread.sleep(1000);

			}catch(Exception e){
				//Screenshot();
				e.printStackTrace();
			}

		}
		
		public WebElement locateElementsByIndex(String locator,int index) throws IOException, InterruptedException{
			//try{
			WebElement element=null;
			try{

				element=(WebElement) d.findElementsByClassName(locator).get(index);

			}catch(Exception e){
				e.printStackTrace();
			}
			return element;
		}
		/////////// click by send keys /////////////////////////////

		public void clickSendKeys(String idetifier,String locator,String Key) throws IOException{
			try{
				WebElement e=webElementId(idetifier,locator);
				e.sendKeys(Key);
				System.out.println("this is clickeddd and send keys...."+Key);

			}catch(Throwable t){

				return ;
			}
		}







		public void clear(String idetifier,String locator) throws IOException{
			try{
				WebElement e=webElementId(idetifier,locator);
				e.clear();
			}catch(Throwable t){

				return ;
			}
		}

		/// to get the text from the particular location
		public String getText(String idetifier,String locator) throws IOException{
			try{
				WebElement e=webElementId(idetifier,locator);

				return e.getText();
			}catch(Throwable t){

				return null;
			}
		}

		// to wait for expected conditions and elements

		public void waitForExpectedElementToVisible(String identifier,String locator,Integer time){

			WebDriverWait wait = new WebDriverWait(d, time);
			if(identifier.equalsIgnoreCase("xpath")){

				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
			}else if(identifier.equalsIgnoreCase("id")){

				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locator)));
			}else if(identifier.equalsIgnoreCase("className")){

				wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(locator)));

			}else if(identifier.equalsIgnoreCase("name")){

				wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(locator)));

			}


		}


		// to reload a page
		public void reload(){

			d.navigate().refresh();
		}

		// to wait for expected conditions and elements

		public void waitForExpectedElementToClickable(String identifier,String locator,Integer time){

			WebDriverWait wait = new WebDriverWait(d, time);
			if(identifier.equalsIgnoreCase("xpath")){

				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator)));

			}else if(identifier.equalsIgnoreCase("id")){

				wait.until(ExpectedConditions.elementToBeClickable(By.id(locator)));

			}else if(identifier.equalsIgnoreCase("className")){
				System.out.println("++++++++++++++++++++++++");
				wait.until(ExpectedConditions.elementToBeClickable(By.className(locator)));

			}


		}





		//to verify the visibility
		public boolean isVisible(String idetifier,String locator) throws IOException{
			try{
				WebElement e=webElementId(idetifier,locator);

				return e.isDisplayed();
			}catch(Throwable t){

				return false;
			}
		}


		public boolean verifyText(String identifier,String locator,String text) throws Exception{
			boolean s=false;WebElement e=null;


			e= webElementId(identifier, locator);
			System.out.println("get text value ="+e.getText());
			System.out.println("text to be compared value ="+text);
			s=(e.getText().equals(text));
			System.out.println(s);
			if(s==true){
				System.out.println("expected text present:::"+text+"======"+e.getText());

			}else {
				System.out.println("expected text not present ::::::::::"+text+"=!=!=!=!=!="+e.getText());



				throw new VerifyError("expected text not present ::::::::::"+text+"=!=!=!=!=!="+e.getText());
			}
			Thread.sleep(1000);
			return s;


		}

		public void verifyImg( String compareBy,String compareTo )// pass this method the image of your

		{
			boolean ret = true;
			try
			{
				BufferedImage original = ImageIO.read( new File( compareBy ) );// path
				// of
				// your
				// image
				// stored
				// in
				// your
				// machine
				BufferedImage copy = ImageIO.read( new URL( compareTo ) );

				Raster ras1 = original.getData();
				Raster ras2 = copy.getData();
				// Comparing the the two images for number of
				// bands,width & height.
				if( ras1.getNumBands() != ras2.getNumBands() || ras1.getWidth() != ras2.getWidth() || ras1.getHeight() != ras2.getHeight() )
				{
					ret = false;
				}
				else
				{
					// Once the band ,width & height matches,
					// comparing the images.

					search: for( int i = 0; i < ras1.getNumBands(); ++i )
					{
						for( int x = 0; x < ras1.getWidth(); ++x )
						{
							for( int y = 0; y < ras1.getHeight(); ++y )
							{
								if( ras1.getSample( x, y, i ) != ras2.getSample( x, y, i ) )
								{
									// If one of the
									// result is
									// false setting
									// the result as
									// false and
									// breaking the
									// loop.
									ret = false;
									break search;
								}
							}
						}
					}
				}
				if( ret == true )
				{
					System.out.println( "Image matches" );
				}
				else
				{
					System.out.println( "Image does not matches" );
				}

			}
			catch( IOException e )
			{
				System.out.println( e );
			}

		}


		public void verifyTitle(String title){
			try{
				//WebElement e= webElementId(identifier, locator);
				if(d.getTitle().equals(title)){
					System.out.println("page loaded with expected title===="+title);

				}else{
					System.out.println("page not loaded with expected title===="+title);
				}
			}catch(Throwable t){

				return ;
			}

		}


		public void waitForElement(long waitTime){
			try {
				Thread.sleep(waitTime);
			}catch(Throwable t){

				return ;
			}
		}
		public void waitUntilElementPresent(String element){
			try{

				WebElement elementPreset=(new WebDriverWait(d, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(element))));

			}catch(Throwable t){

				return ;
			}
		}
		public boolean verifyElementPresent(String identifier,String locator) throws IOException{
			try{
				boolean s;
				WebElement e=webElementId(identifier, locator);
				s=(e!=null);
				if(s==true){
					System.out.println("expected element is present>>>>>"+identifier+"::::::::"+locator);
				}else{
					System.out.println("expecetd element is not present>>>>>>"+identifier+"::::::::"+locator);
					return false;
				}


				return s;
			}catch(Throwable t){

				return false;
			}
		}



		public void sleepThread(long sleepTime){
			try {
				Thread.sleep(sleepTime);
			} catch(Throwable t){

				return ;
			}
		}

		public synchronized  void waitForPageToLoad() throws IOException{
			try {
				for(int i=0;i<50;){
					if(d.getTitle().length()!=0){
						System.out.println("page is loaded :::: "+d.getTitle());
						i=51;
						break;
					}else{
						Thread.sleep(1000L);
					}
				}
			} catch(Throwable t){

				return ;
			}
		}

		//close
		public void closeBrowser1() throws IOException{
			try{

				d.close();
				System.out.println("browser is closed...........................");
			}catch(Throwable t){

				return ;
			}
		}




		public void quitObject() throws IOException{
			try{
				d.quit();
			}catch(Throwable t){

				return ;
			}
		}
		//////////////////////////to select the options from dropdown
		public void select(String identifier,String locator,String content) throws IOException{

			// WebElement e=webElementId(identifier, locator);
			try{
				System.out.println("setting state before");
				WebElement dropDown = webElementId(identifier, locator);
				System.out.println("set state 1");
				Select sel = new Select(dropDown);
				System.out.println("set state 2");
				sel.selectByValue(content);
				// dropDown.click();
				System.out.println("set state successfully");
			}catch(Exception e) {
				e.printStackTrace();
			}
			//sel.selectByVisibleText(content);
		}

		//////////////////////////to select the options by visible text///////////////////////////////
		public void selectByVisibleText(String identifier,String locator,String content) throws IOException{
			try{
				// WebElement e=webElementId(identifier, locator);
				WebElement dropDown = webElementId(identifier, locator);
				Select sel = new Select(dropDown);
				//sel.selectByIndex(content);

				sel.selectByVisibleText(content);
				dropDown.click();

			}catch(Throwable t){

				closeBrowser1();

				return ;
			}

			//sel.selectByVisibleText(content);
		}


		///////////////////////////////////to get xpath count////////////////////////////////////
		public Integer getXpathCount(String locator) throws IOException{

			Integer xpathCount=0;
			try{
				xpathCount= d.findElements(By.xpath(locator)).size();
			}catch(Throwable t){

				closeBrowser1();

				return null;
			}
			return xpathCount;


		}

		public String getPageSource(WebDriver driver){

			String pageSource=d.getPageSource();
			return pageSource;
		}

		//Hide keyboard
		public void hideKeyboard(){
			d.hideKeyboard();	
		}

		public void keyEnter(){
			((AndroidDriver) d).sendKeyEvent(AndroidKeyCode.ENTER);
		}
	
	////////////////////////////
	
	
}
