package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.practice.genericUtility.FileUtility;
import com.practice.genericUtility.WebDriverUtility;

public class Planit_TC3 {
	
	@Test
	
	public void TC3()throws Throwable {
		
		/*Object Creation for Lib*/
		WebDriverUtility wLib = new WebDriverUtility();
		FileUtility fLib=new FileUtility();
			
		/*common Data*/
		String URL=fLib.getPropertyKeyValue("url");
		
		/* Navigate to app*/	
		System.setProperty("webdriver.chrome.driver","./drivers/chromedriver.exe");
		WebDriver driver=new ChromeDriver();
		wLib.waitUntilPageLoad(driver);
	
		/* Load the URL*/
		driver.get(URL);
		driver.manage().window().maximize();
	
		/* Click on Start Shopping button*/
		driver.findElement(By.xpath("//a[@class='btn btn-success btn-large']")).click();
		Thread.sleep(5000);
	
		/* Getting the Xpath of Stuffed Frog,Fluffy Bunny,Valentine Bear */
		String stuffedFrogPrice=driver.findElement(By.xpath("//h4[text()='Stuffed Frog']/..//span")).getText();
		String fluffyBunnyPrice=driver.findElement(By.xpath("//h4[text()='Fluffy Bunny']/..//span")).getText();
		String valentineBearPrice=driver.findElement(By.xpath("//h4[text()='Valentine Bear']/..//span")).getText();
	
		/* Initializing the variables for Stuffed Frog,Fluffy Bunny,Valentine Bear*/ 
		int noOfStuffedFrogs=2;
		int noOfFluffyBunnys=5;
		int noOfValentineBears=3;	
		float sumOfSubtotal=0;
	
		/* 1.Buy 2 Stuffed Frog, 5 Fluffy Bunny, 3 Valentine Bear */
		for(int i=0; i<noOfStuffedFrogs;i++)
		driver.findElement(By.xpath("//h4[text()='Stuffed Frog']/..//a")).click();	
		for(int i=0; i<noOfFluffyBunnys;i++)	
		driver.findElement(By.xpath("//h4[text()='Fluffy Bunny']/..//a")).click();
		for(int i=0; i<noOfValentineBears;i++)	
		driver.findElement(By.xpath("//h4[text()='Valentine Bear']/..//a")).click();
	
		/* 2.Go to the cart page */
		driver.findElement(By.xpath("//li/a[contains(text(),'Cart')]")).click();
		Thread.sleep(5000);
	
		/* Getting the Xpath for the Item, Price,Quantity,Subtotal */
		for(int i=1;i<4;i++) 
		{
		String Item=driver.findElement(By.xpath("//table[@class='table table-striped cart-items']//tr["+i+"]/td[1]")).getText();
		String Price=driver.findElement(By.xpath("//table[@class='table table-striped cart-items']//tr["+i+"]/td[2]")).getText();
		String Quantity=driver.findElement(By.xpath("//table[@class='table table-striped cart-items']//tr["+i+"]//input")).getAttribute("value");
		String Subtotal=driver.findElement(By.xpath("//table[@class='table table-striped cart-items']//tr["+i+"]/td[4]")).getText();
		
		/* 3.Verify the Subtotal for each product is correct*/
		String []temp=Price.split("\\$");
		float calculatedTotal=Float.parseFloat(temp[1])*Integer.valueOf(Quantity);
		calculatedTotal= Math.round(calculatedTotal * 100.0f) / 100.0f;
		String ActualSubtotal="$"+calculatedTotal;
		Assert.assertEquals(ActualSubtotal, Subtotal);
		System.out.println(" ASSERT PASSED : Multiplaying the Price and Quantity of each Item and validating with the Subtotal ");
	
		/* 4.Verify the price for each product */
		if(Item.contains("Stuffed Frog")) {		
			Assert.assertEquals(stuffedFrogPrice, Price);
			System.out.println(" ASSERT PASSED : Price of Stuffed Frog VERIFIED");
		}
		else if (Item.contains("Fluffy Bunny"))	{		
			Assert.assertEquals(fluffyBunnyPrice, Price);
			System.out.println(" ASSERT PASSED : Price of Fluffy Bunny VERIFIED");
		}
		else 	
		{
			Assert.assertEquals(valentineBearPrice, Price);
			System.out.println(" ASSERT PASSED : Price of Valentine Bear VERIFIED");
		}
	
		/* Storing the Subtotal of all three items in sumOfSubtotal*/		
		String [] temp1=Subtotal.split("\\$");
		sumOfSubtotal += Float.parseFloat(temp1[1]);
		
	
	}
		/* 5. Verify that total = sum of (Subtotals)*/
		String [] Total=driver.findElement(By.xpath("//strong[@class='total ng-binding']")).getText().split(":");
		float actualtotal=Float.parseFloat(Total[1].trim());
		sumOfSubtotal= Math.round(actualtotal * 100.0f) / 100.0f;
		Assert.assertEquals(actualtotal, sumOfSubtotal);
		System.out.println(" ASSERT PASSED : VERIFIED that Total is sum of SubTotals");
	
   }
}
