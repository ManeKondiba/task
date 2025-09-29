package test;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;
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

        // Wait until search button is clickable
        WebElement searchBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-search-submit-button")));
        searchBtn.click();

     
        // Apply filters
        WebElement Display= wait.until( ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Analogue']")));
        Display.click();

        WebElement Band_Material= wait.until( ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Leather']")));
        Band_Material.click();
        
        WebElement Brand= wait.until( ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='a-size-base a-color-base'][normalize-space()='Titan']")));
        Brand.click();   

        WebElement Discount= wait.until( ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='25% Off or more']")));
        Discount.click();
        
        
        
        // Wait for slider handles
       
        WebElement minSlider = wait.until(ExpectedConditions.visibilityOfElementLocated(
        	    By.xpath("(//input[@id='p_36/range-slider_slider-item_lower-bound-slider'])[1]")));
        	WebElement maxSlider = wait.until(ExpectedConditions.visibilityOfElementLocated(
        	    By.xpath("(//input[@id='p_36/range-slider_slider-item_upper-bound-slider'])[1]")));

    
        	 Actions act = new Actions(driver);
       

        // Move min slider (towards ₹3,500)
        act.clickAndHold(minSlider).moveByOffset(40, 0).build().perform();
        Thread.sleep(2000);

        // Move max slider (towards ₹5,500)
        act.clickAndHold(maxSlider).moveByOffset(-55, 0).perform();
        Thread.sleep(2000);

        // Click "Go" button
        WebElement goButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//input[@aria-label='Go - Submit price range']")));
        goButton.click();

        // Wait for filtered results to load
        Thread.sleep(3000); 

;

         
       // Get first product details
       WebElement firstProduct = wait.until(ExpectedConditions.visibilityOfElementLocated(
               By.cssSelector("div.s-main-slot div[data-component-type='s-search-result']")));
       String price = "";
       String mrp = "";
       String discount = "";
       try {
           price = firstProduct.findElement(By.cssSelector("span.a-price-whole")).getText();
       } catch (Exception e) {
           System.out.println("Price not found");
       }
       try {
           mrp = firstProduct.findElement(By.xpath(".//span[@class='a-price a-text-price']//span[@class='a-offscreen']")).getText();
       } catch (Exception e) {
           System.out.println("MRP not found");
       }
       try {
           discount = firstProduct.findElement(By.cssSelector("span.a-letter-space + span")).getText();
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
