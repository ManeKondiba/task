package test;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AmazonWatch {

    WebDriver driver;   // declare driver globally
    WebDriverWait wait;
    
    @BeforeClass
    public void setup() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://www.amazon.in");
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void searchWristWatch() throws InterruptedException {
    	
    	
    	
    		try {
    	            WebElement continueBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(
    	                    By.xpath("//button[text()='Continue shopping']")));
    	       
    	            continueBtn.click();
    	            System.out.println("Continue Shopping button clicked.");

    		} catch (Exception e) {
    		    System.out.println("Continue Shopping button not present. Skipping...");
    		}
    		
    		
   
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("twotabsearchtextbox")));
        searchBox.sendKeys("Wrist Watch");

        WebElement searchBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-search-submit-button")));
        searchBtn.click();
        
        // Wait for slider handles
       
       WebElement minSlider =wait.until(ExpectedConditions.elementToBeClickable(By.id("p_36/range-slider_slider-item_lower-bound-slider")));
        
      System.out.println("minvalue:"+ minSlider.getAttribute("value"));    //(0, 189)
      
       WebElement maxSlider =wait.until(ExpectedConditions.elementToBeClickable( By.id("p_36/range-slider_slider-item_upper-bound-slider")));
   
        System.out.println("maxvalue:"+ maxSlider.getAttribute("value"));
       
        JavascriptExecutor js = (JavascriptExecutor) driver;
        
     js.executeScript("arguments[0].value='41'; arguments[0].dispatchEvent(new Event('input')); arguments[0].dispatchEvent(new Event('change'));", minSlider);
     
     js.executeScript("arguments[0].value='51'; arguments[0].dispatchEvent(new Event('input')); arguments[0].dispatchEvent(new Event('change'));", maxSlider);
    
   
    
        // Click "Go" button
        WebElement goButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//input[@class='a-button-input' and @type='submit']"))); 
        goButton.click();

        // Wait for filtered results to load
        Thread.sleep(3000); 
    
        //Apply Filters
        WebElement Display= wait.until( ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Analogue']")));
        Display.click();

        WebElement Band_Material= wait.until( ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Leather']")));
        Band_Material.click();
        
        WebElement Brand= wait.until( ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='a-size-base a-color-base'][normalize-space()='Titan']")));
        Brand.click();   

        WebElement Discount= wait.until( ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='25% Off or more']")));
        Discount.click();
        
     
         
       // Get first product details
       WebElement firstProduct = wait.until(ExpectedConditions.visibilityOfElementLocated(
               By.xpath("//div[@data-component-type='s-search-result'][1]")));

       String price = "";
       String mrp = "";
       String discount = "";
       try {
           price = firstProduct.findElement(By.xpath("//span[@class='a-price-whole']")).getText();
       } catch (Exception e) {
           System.out.println("Price not found");
       }
       try {
           mrp = firstProduct.findElement(By.xpath("//span[@class='a-price a-text-price']//span[@aria-hidden='true']")).getText();
       } catch (Exception e) {
           System.out.println("MRP not found");
       }
       try {
           discount = firstProduct.findElement(By.xpath("//span[contains(text(), 'off')]")).getText();
       } catch (Exception e) {
           System.out.println("Discount not found");
       }
       System.out.println("Price: " + price);
       System.out.println("MRP: " + mrp);
       System.out.println("Discount: " + discount);
       
    }

     
    @AfterClass
    public void tearDown() {
       if (driver != null) {
            driver.quit();
        }
    }
}
