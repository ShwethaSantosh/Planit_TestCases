package test;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.practice.genericUtility.ExcelUtility;
import com.practice.genericUtility.FileUtility;


public class Planit_TC1 {
	
	@Test
	
	public void TC1() throws Throwable
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
		String actualErrorMsg=eLib.getDataFromExcel("Messages", 1, 0);
		String actualErrorMsgAfterSubmit=eLib.getDataFromExcel("Messages", 2, 0);
		
		/*Navigate to app*/
		System.setProperty("webdriver.chrome.driver","./drivers/chromedriver.exe");
		WebDriver driver=new ChromeDriver();
		JavascriptExecutor js = (JavascriptExecutor) driver;
	
		/* Load the URL*/
		driver.get(Url);		
	
		/*1.From the home page go to contact page*/
		driver.findElement(By.linkText("Contact")).click();	
		driver.manage().window().maximize();
		js.executeScript("window.scrollBy(0,1000)");
		Thread.sleep(5000);
	
		/*2.Click submit button*/
		driver.findElement(By.linkText("Submit")).click();
			
		/*3.Verify error messages*/
		String expectedErrormsg=driver.findElement(By.xpath("//div[@class='alert alert-error ng-scope']")).getText();
		Assert.assertEquals(expectedErrormsg, actualErrorMsg);
		System.out.println("ASSERT PASSED :  Error message verified with Actual Errror mesage");
		Thread.sleep(5000);
	
		/*4.Populate mandatory fields*/			
		driver.findElement(By.id("forename")).sendKeys(Username);
		driver.findElement(By.id("email")).sendKeys(Email);
		driver.findElement(By.id("message")).sendKeys(Message);
		js.executeScript("scroll(0, -250);");
		Thread.sleep(5000);
		
		/*5.Validate errors are gone*/
		String expectedErrorMsg1=driver.findElement(By.xpath("//div[@class='alert alert-info ng-scope']")).getText();
		Assert.assertEquals(expectedErrorMsg1, actualErrorMsgAfterSubmit); 
		System.out.println("ASSERT PASSED :  Validated : Errors are gone");
	
		}
}
