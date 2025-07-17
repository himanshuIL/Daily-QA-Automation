package generic;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;

import com.google.common.io.Files;

public class BaseTest extends Libraries {

	public static WebDriver driver;

	

	@BeforeClass
	public void openBrowser() throws Exception {
		System.out.println("Your OS version-->" + System.getProperty("os.name"));
		String osname = System.getProperty("os.name");
//		if (osname.toLowerCase().contains("linux")) {
//			System.setProperty("webdriver.chrome.driver", "");
//		} else if (osname.toLowerCase().contains("windows 11")) {
//			String filePathToSet = System.getProperty("user.dir") + "/driver/chromedriver.exe";
//			System.setProperty("webdriver.chrome.driver", filePathToSet);
//		} else {
//			String filePathToSet = System.getProperty("user.dir") + "/driver/mac/chromedriver";
//			System.setProperty("webdriver.chrome.driver", filePathToSet);
//		}
		ChromeOptions co = new ChromeOptions();
		
		co.addArguments("--disable-notifications");
//		co.addArguments("--headless=new");
//		co.addArguments("--window-size=1920,1080");
//		co.addArguments("--disable-gpu");
	      
		  driver = new ChromeDriver(co);

		System.out.println("Browser launched");
		driver.get(Libraries.fetchPropertyValue("prodURL").toString());
		System.out.println("URL Launched");
		driver.manage().window().maximize();
		System.out.println("browser maximized sucessfully");
		Thread.sleep(2000);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

	}

	@AfterClass
	public void closeBrowser() throws Exception {

		runAllureReportFolderCommand();
		Thread.sleep(8000);

		generateReport();

		generic.Email h = new generic.Email();
		h.sendEmail("reportSS/reportSS_.png");

		System.out.println("browser closed");
//		driver.close();
		driver.quit();

	}

	protected static String getClipboardText() throws IOException, UnsupportedFlavorException {
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		DataFlavor flavor = DataFlavor.stringFlavor;
		return (String) clipboard.getData(flavor);
	}

	private void runAllureReportFolderCommand() throws Exception {
		String command = Libraries.fetchPropertyValue("allureCommand2").toString();

		try {
			Process process = Runtime.getRuntime().exec(command);
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
			process.waitFor(); // Wait for the command to finish
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void generateReport() throws Exception {
		driver.get(Libraries.fetchPropertyValue("netlifyURl").toString());
		Thread.sleep(3000);
		WebElement loginOption = driver.findElement(By.xpath(Libraries.fetchPropertyValue("loginOptn")));
		loginOption.click();
		Thread.sleep(3000);
		WebElement email = driver.findElement(By.xpath(Libraries.fetchPropertyValue("id")));
		email.clear();
		email.sendKeys("infinitelocus176@gmail.com");
		Thread.sleep(2000);
		WebElement password = driver.findElement(By.xpath(Libraries.fetchPropertyValue("pass")));
		password.clear();
		password.sendKeys("InfiniteLocus@1707");
		Thread.sleep(1000);
		WebElement login = driver.findElement(By.xpath(Libraries.fetchPropertyValue("signIn")));
		login.click();
		Thread.sleep(15000);
		WebElement fileInput = driver.findElement(By.xpath(Libraries.fetchPropertyValue("fileUpload")));
		fileInput.click();
		Thread.sleep(2000);

		// Use StringSelection to copy the file path to the clipboard
		String filePath = Libraries.fetchPropertyValue("file").toString();
		StringSelection selection = new StringSelection(filePath);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(selection, null);

		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);

		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		Thread.sleep(1000);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		Thread.sleep(1000);
		robot.keyPress(KeyEvent.VK_LEFT);
		robot.keyRelease(KeyEvent.VK_LEFT);
		Thread.sleep(1000);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		Thread.sleep(25000);
		WebElement optionBtn = driver.findElement(By.xpath(Libraries.fetchPropertyValue("optionButton")));
		optionBtn.click();
		Thread.sleep(500);
		WebElement deployBtn = driver.findElement(By.xpath(Libraries.fetchPropertyValue("deploySetting")));
		deployBtn.click();
		Thread.sleep(5000);
		WebElement site = driver.findElement(By.xpath(Libraries.fetchPropertyValue("siteDetails")));
		site.click();
		Thread.sleep(1000);
		WebElement changeSiteName = driver.findElement(By.xpath(Libraries.fetchPropertyValue("changeSite")));
		changeSiteName.click();
		Thread.sleep(1000);
		WebElement field = driver.findElement(By.xpath(Libraries.fetchPropertyValue("siteInput")));
		field.click();
		Thread.sleep(1000);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_A);
		robot.keyRelease(KeyEvent.VK_A);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_BACK_SPACE);
		robot.keyRelease(KeyEvent.VK_BACK_SPACE);

		Date d = new Date();
		String timeDateStamp = d.toString().replace(":", "_").replace(" ", "_");
		field.sendKeys("supertailsreport" + timeDateStamp);
		Thread.sleep(1000);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_A);
		robot.keyRelease(KeyEvent.VK_A);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_C);
		robot.keyRelease(KeyEvent.VK_C);
		robot.keyRelease(KeyEvent.VK_CONTROL);
//		WebElement field1 = driver.findElement(By.xpath(Libraries.fetchPropertyValue("siteInput1")));
//		field1.click();
//		robot.keyPress(KeyEvent.VK_CONTROL);
//		robot.keyPress(KeyEvent.VK_V);
//		robot.keyRelease(KeyEvent.VK_V);
//		robot.keyRelease(KeyEvent.VK_CONTROL);
//		Thread.sleep(1000);
		WebElement saveBtn = driver.findElement(By.xpath(Libraries.fetchPropertyValue("saveButton")));
		saveBtn.click();
		Thread.sleep(4000);
		WebElement report = driver.findElement(By.xpath(Libraries.fetchPropertyValue("link")));
		report.click();
		Thread.sleep(5000);
		Set<String> wh = driver.getWindowHandles();
		Iterator itr = wh.iterator();
		String parent = (String) itr.next();
		String newTab = (String) itr.next();
		driver.switchTo().window(newTab);
		Thread.sleep(2000);
//		String testReport = driver.getCurrentUrl();
//		System.out.println(testReport);
		report();
		System.out.println(report());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File src = ts.getScreenshotAs(OutputType.FILE);
		File des = new File("reportSS/reportSS_.png");
		Files.copy(src, des);

	}
	
	public static String report() {
		return driver.getCurrentUrl();
	}

//	public void removePreviewBar() throws Exception {
//	String currentUrl = driver.getCurrentUrl();
//
//	if (currentUrl.equalsIgnoreCase("https://supertails.com/")) {
//		driver.getTitle();
//	} else {
//		WebElement frame = driver.findElement(By.xpath("//iframe[@id=\"preview-bar-iframe\"]"));
//		driver.switchTo().frame(frame);
//		driver.findElement(By.xpath(
//				"//button[@class=\"ui-button ui-button--transparent admin-bar__button--is-hidden-on-mobile\"]"))
//				.click();
//		driver.switchTo().defaultContent();
//	}
//}

}
