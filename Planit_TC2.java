package test;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.practice.genericUtility.ExcelUtility;
import com.practice.genericUtility.FileUtility;

public class Planit_TC2 {
	
	@Test (invocationCount = 5)
	
	public void TC2() throws Throwable
	{
		/*Object Creation for Lib*/
		FileUtility fLib=new FileUtility();
		ExcelUtility eLib=new ExcelUtility();
		
		/*common Data*/
		String Url=fLib.getPropertyKeyValue("url");
		String Username=fLib.getPropertyKeyValue("Forename");
		String Email=fLib.getPropertyKeyValue("Email");
		String Message=fLib.getPropertyKeyValue("Message");
		
		/*test script Data*/
		String actualMessage=eLib.getDataFromExcel("Messages", 3, 0);
					
		/*Navigate to app*/
		System.setProperty("webdriver.chrome.driver","./drivers/chromedriver.exe");
		WebDriver driver=new ChromeDriver();
		JavascriptExecutor js = (JavascriptExecutor) driver;
	
		/* Load the URL*/
		driver.get(Url);
		
		/*1.From the home page go to contact page*/
		driver.findElement(By.linkText("Contact")).click();	
		driver.manage().window().maximize();
		Thread.sleep(5000);	
				
		/*2.Populate mandatory fields*/				
		driver.findElement(By.id("forename")).sendKeys(Username);
		driver.findElement(By.id("email")).sendKeys(Email);
		driver.findElement(By.id("message")).sendKeys(Message);
		Thread.sleep(5000);
		js.executeScript("scroll(0, -250);");		
	
		 /*3.Click submit button*/
		 driver.findElement(By.linkText("Submit")).click();	
				 
		 /*4.Validate successful submission message*/
		 driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		 String expectedMsg=driver.findElement(By.cssSelector(".alert-success")).getText();
		 Assert.assertEquals(expectedMsg, actualMessage); 
		 System.out.println("ASSERT PASSED :  Validated : Successfull Submission Message");
	  }
}
	
	
	
	
	
	
	
