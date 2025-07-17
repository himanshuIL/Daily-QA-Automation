package test;

import org.openqa.selenium.Keys;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.List;

@Test
public class MyTest {
        public static void main(String[] args) {
            // Setup ChromeOptions
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-notifications");

            // Launch Chrome6
            WebDriver driver = new ChromeDriver(options);

            try {
                // Open Supertails website
                driver.get("https://www.supertails.com/");
                driver.manage().window().maximize();
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));


                WebElement searchbox = driver.findElement(By.id("mainfrm"));
                searchbox.sendKeys("Pedigree");
                searchbox.sendKeys(Keys.ENTER);
                // Click on the search icon (usually a magnifying glass)
                WebElement searchIcon = driver.findElement(By.cssSelector("button[aria-label='Search']"));
                searchIcon.click();

                // Wait a little
                Thread.sleep(1000);

                // Find the search input field
//                WebElement searchInput = driver.findElement(By.xpath("//input[@placeholder='Search']"));
//
//                // Enter search query
//                searchInput.sendKeys("dog food");
//
//                // Submit search (press Enter)
//                searchInput.submit();

                // Wait for results to load
//                Thread.sleep(3000);
//
//                // Grab the search results
//                List<WebElement> results = driver.findElements(By.cssSelector("a.product-name"));
//
//                // Print product titles
//                System.out.println("Search Results for 'dog food':");
//                for (WebElement result : results) {
//                    System.out.println("- " + result.getText());
//                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // Close browser
                driver.quit();
            }
        }
}
