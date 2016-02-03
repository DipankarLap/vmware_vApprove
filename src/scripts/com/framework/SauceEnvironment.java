package scripts.com.framework;

import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;


public class SauceEnvironment {

	DesiredCapabilities capabilities;
	String version;
	
	SauceEnvironment(DesiredCapabilities capabilities,String browser){
		System.out.println("SauceEnvironment constructor and browser:"+browser);
		this.capabilities=capabilities;
		version=splitString(browser.replace(" ", ""))[1];
		System.out.println("version:"+version);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String browser="Chrome 31";
		browser=browser.replace(" ", "");
		for(String s:splitString(browser))
			System.out.println(s);
		System.out.println(splitString(browser.replace(" ", ""))[1]);
	}

	/**XP
	 * 
	 * @param version
	 */
	public void xpFirefox(String version){
		capabilities.setPlatform(Platform.XP);
	    capabilities.setBrowserName("firefox");
		capabilities.setVersion(version);
	}	
	public void xpIE(String version){
		capabilities.setPlatform(Platform.XP);
	    capabilities.setBrowserName("internet explorer");
		capabilities.setVersion(version);
	}
	public void xpChrome(String version){
		capabilities.setPlatform(Platform.XP);
	    capabilities.setBrowserName("chrome");
		capabilities.setVersion(version);
	}		
	public void xpOpera(String version){
		capabilities.setPlatform(Platform.XP);
	    capabilities.setBrowserName("opera");
		capabilities.setVersion(version);
	}
	
	/**Mac:Snow Leopard
	 * 
	 * @param version
	 */
	public DesiredCapabilities macSnowFirefox(){		
		capabilities.setBrowserName("firefox");
		capabilities.setCapability("platform", "OS X 10.6");
		capabilities.setCapability("version", version);	
		return capabilities;
	}	
	public DesiredCapabilities macSnowSnowChrome(){		
		capabilities.setCapability("platform", "OS X 10.6");
	    capabilities.setBrowserName("chrome");	    
		capabilities.setCapability("version", version);	
		return capabilities;
	}		
	public DesiredCapabilities macSnowSafari(){
		capabilities.setCapability("platform", "OS X 10.6");
		capabilities.setBrowserName("safari");
		capabilities.setCapability("version", version);	
		return capabilities;
	}
	/**Mac:Mountain Lion
	 * 
	 * @param version
	 */	
	public DesiredCapabilities macMountainChrome(){
		//capabilities.setPlatform(Platform.MAC);
		capabilities.setCapability("platform", "OS X 10.8");
	    capabilities.setBrowserName("chrome");	    
		capabilities.setCapability("version", version);	
		return capabilities;
	}	
	public DesiredCapabilities macMountainSafari(){
		capabilities.setCapability("platform", "OS X 10.8");
		capabilities.setBrowserName("safari");
		capabilities.setCapability("version", version);	
		return capabilities;
	}	
	/**Mac:Mavericks
	 * 
	 * @param version
	 */
	public DesiredCapabilities macMavericksFirefox(){		
		capabilities.setBrowserName("firefox");
		capabilities.setCapability("platform", "OS X 10.9");
		capabilities.setCapability("version", version);
		return capabilities;
	}		
	public DesiredCapabilities macMavericksChrome(){
		//capabilities.setPlatform(Platform.MAC);
		capabilities.setCapability("platform", "OS X 10.9");
	    capabilities.setBrowserName("chrome");	    
		capabilities.setCapability("version", version);	
		return capabilities;
	}	
	public DesiredCapabilities macMavericksSafari(){
		capabilities.setCapability("platform", "OS X 10.9");
		capabilities.setBrowserName("safari");
		capabilities.setCapability("version", version);	
		return capabilities;
	}
	/**
	 * Windows 8.1	
	 * @param version
	 */
	public DesiredCapabilities win81Firefox(){
		capabilities.setCapability("platform","Windows 8.1");
	    capabilities.setBrowserName("firefox");
		capabilities.setVersion(version);
		return capabilities;
	}	
	public DesiredCapabilities win81IE(){
		capabilities.setCapability("platform","Windows 8.1");
	    capabilities.setBrowserName("internet explorer");
	    capabilities.setVersion(version);
	    return capabilities;
	}
	public DesiredCapabilities win81Chrome(){
		capabilities.setCapability("platform","Windows 8.1");
	    capabilities.setBrowserName("chrome");
	    capabilities.setVersion(version);
	    return capabilities;
	}	
	/**
	 * LINUX
	 * @param version
	 */
	public void linuxFirefox(String version){
		capabilities.setPlatform(Platform.LINUX);
	    capabilities.setBrowserName("firefox");
		capabilities.setVersion(version);
	}	
	public void linuxIE(String version){
		capabilities.setPlatform(Platform.LINUX);
	    capabilities.setBrowserName("internet explorer");
		capabilities.setVersion(version);
	}
	public void linuxChrome(String version){
		capabilities.setPlatform(Platform.LINUX);
	    capabilities.setBrowserName("chrome");
		capabilities.setVersion(version);
	}		
	public void linuxOpera(String version){
		capabilities.setPlatform(Platform.LINUX);
	    capabilities.setBrowserName("opera");
		capabilities.setVersion(version);
	}
	public void linuxSafari(String version){
		capabilities.setPlatform(Platform.MAC);
		capabilities.setBrowserName("safari");
		capabilities.setVersion(version);
	}
	
	/**
	 * Windows 7
	 * @param version
	 */
	public void win7Firefox(String version){
		capabilities.setCapability("platform","Windows 7");
	    capabilities.setBrowserName("firefox");
		capabilities.setVersion(version);
	}	
	public void win7IE(String version){
		capabilities.setCapability("platform","Windows 7");
	    capabilities.setBrowserName("internet explorer");
		capabilities.setVersion(version);
	}
	public void win7Chrome(String version){
		capabilities.setCapability("platform","Windows 7");
	    capabilities.setBrowserName("chrome");
		capabilities.setVersion(version);
	}		
	public void win7Opera(String version){
		capabilities.setCapability("platform","Windows 7");
	    capabilities.setBrowserName("opera");
		capabilities.setVersion(version);
	}
	public void win7Safari(String version){
		capabilities.setPlatform(Platform.MAC);
		capabilities.setBrowserName("safari");
		capabilities.setVersion(version);
	}
	
	/**
	 * Windows 8
	 * @param version
	 */
	public void win8Firefox(String version){
		capabilities.setCapability("platform","Windows 8");
	    capabilities.setBrowserName("firefox");
		capabilities.setVersion(version);
	}	
	public void win8IE(String version){
		capabilities.setCapability("platform","Windows 8");
	    capabilities.setBrowserName("internet explorer");
		capabilities.setVersion(version);
	}
	public void win8Chrome(String version){
		capabilities.setCapability("platform","Windows 8");
	    capabilities.setBrowserName("chrome");
		capabilities.setVersion(version);
	}		
	
	
	
	public static String[] splitString(String sbrowser){		
		String regex = "(?<=[\\w&&\\D])(?=\\d)";		
		String p[]=sbrowser.split(regex);		
		return p;
	}
}
