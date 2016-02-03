package scripts.com.coupa;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

import scripts.com.utils.Xls_Reader;



public class Coupaportal {

	public static Xls_Reader xlsReader=new Xls_Reader("D:\\Workspace\\Mobile_Framework\\vApprove\\Coupadata\\coupa_data.xlsx");
	public static Xls_Reader xlsReader_output=new Xls_Reader("D:\\Workspace\\Mobile_Framework\\vApprove\\Coupadata\\coupaoutput.xlsx");
	
	@Test
	public  void main() throws Exception {

		WebDriver driver =new FirefoxDriver();

		driver.get("https://vmware-test.coupahost.com/user/home");
		driver.manage().window().maximize();

		//Fetching login credentials from xl file
		driver.findElement(By.name("user[login]")).sendKeys(xlsReader.getCellData("login", "UserName", 2));
		driver.findElement(By.name("user[password]")).sendKeys(xlsReader.getCellData("login", "Password", 2));
		Thread.sleep(500);
		driver.findElement(By.xpath("//button/span[contains(text(),'Sign In')]")).click();
		Thread.sleep(500);

		//Select the option Write
		driver.findElement(By.cssSelector("#write_click > img")).click();

		//Ticket name
		WebElement description = driver.findElement(By.id("description"));
		description.sendKeys(xlsReader.getCellData("input", "TicketName", 2));

		// Supplier
		driver.findElement(By.id("requisition_line_supplier_search")).sendKeys(xlsReader.getCellData("input", "Supplier", 2));
		Thread.sleep(3000);

		// Commodity
		driver.findElement(By.id("requisition_line_commodity_id_input")).sendKeys(xlsReader.getCellData("input", "Commodity", 2));
		driver.findElement(By.xpath("//html/body/ul[1]/li/a/strong")).click();
		Thread.sleep(3000);

		//Price
		WebElement price = driver.findElement(By.id("requisition_line_unit_price"));
		price.clear();
		price.sendKeys(xlsReader.getCellData("input", "Price", 2));
		Thread.sleep(3000);

		//Date
		driver.findElement(By.id("requisition_line_local_need_by_date")).sendKeys(xlsReader.getCellData("input", "Date", 2));

		//Add to cart
		driver.findElement(By.xpath("//button/span[contains(text(),'Add to Cart')]")).click();
		Thread.sleep(10000);

		//click on Cart
		driver.findElement(By.id("cart")).click();
		Thread.sleep(5000);

		//Select Checkbox 
		driver.findElement(By.name("checked_lines[]")).click();
		Thread.sleep(9000);

		//click Add button 
		driver.findElement(By.xpath("//*[@id='open_add_approver_form_link']/span")).click();
		Thread.sleep(5000);

		//Add Approver
		driver.findElement(By.id("approver")).sendKeys("deswal");
		Thread.sleep(5000);
		driver.findElement(By.xpath("//a[contains(text(),'Saurabh')]")).click();
		Thread.sleep(5000);

		//click on Add
		driver.findElement(By.xpath("//*[@id='add_approver_link']/span")).click();
		Thread.sleep(9000);

		//Click on Submit Button
		driver.findElement(By.xpath("//*[@id='submit_for_approval_link']")).click();
		Thread.sleep(20000);	    

		//Ticket Name
		String ticketName =  driver.findElement(By.cssSelector(".req_lines>strong")).getText();

		//Supplier
		String supplier =  driver.findElement(By.cssSelector(".smallText>span")).getText();
		
		String status = driver.findElement(By.xpath("//html/body/div[1]/div[4]/div[3]/div[1]/section[1]/div[2]/div/div[1]/div[3]")).getText();
		
		String words[] = status.split(" ");
		String lastTwo = words[words.length - 2] + " "+ words[words.length - 1]; // last two words
		

		//Amount
		String amount =  driver.findElement(By.cssSelector(".price>span")).getText();
		System.out.println(ticketName +" "+supplier +" "+amount+ lastTwo);

		//Extract the data to an Excel Sheet
		xlsReader_output.setCellData("OutputData", "TicketName", 2, ticketName);
		xlsReader_output.setCellData("OutputData", "Supplier", 2, supplier);
		xlsReader_output.setCellData("OutputData", "Status", 2, lastTwo);
		xlsReader_output.setCellData("OutputData", "Amount", 2, amount);
		Thread.sleep(2000);
		System.out.println("Data Extracted Sucessfully");    

		driver.quit();
	}

}
