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
		ChromeOptions co = new ChromeOptions();
		
		co.addArguments("--disable-notifications");
	      
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
			process.waitFor(); 
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
