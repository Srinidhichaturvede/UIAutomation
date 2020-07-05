/**
 * @author Srinidhi Chaturvede
 *
 */

package test.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.java.en.When;

public class DriverInitialization {

	protected static WebDriver driver = null;
	private static Properties Config = null;
	public static Properties Voi = null;

	private boolean headless = false;

	public static String IEdriverpath = System.getProperty("user.dir") + File.separator + "driver" + File.separator
			+ "IEDriverServer.exe";
	public static String Chromedriverpath = System.getProperty("user.dir") + File.separator + "driver" + File.separator
			+ "chromedriver.exe";
	public static String Mozilladriverpath = System.getProperty("user.dir") + File.separator + "driver" + File.separator
			+ "geckodriver.exe";

	/**
	 * This method to initialize the property file
	 */
	public DriverInitialization() {
		if (driver == null) {
			// Initialize the property file.
			Config = new Properties();
			Voi = new Properties();
			System.out.println(System.getProperty("user.dir"));
			String config = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
					+ File.separator + "resources" + File.separator + "object repo" + File.separator
					+ "Config.properties";
			String voi = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
					+ File.separator + "resources" + File.separator + "object repo" + File.separator + "Voi.properties";
			System.out.println(config);
			System.out.println(Chromedriverpath);
			try {
				FileInputStream fs = new FileInputStream(config);
				Config.load(fs);
				FileInputStream vi = new FileInputStream(voi);
				Voi.load(vi);
			} catch (Exception E) {
				return;
			}
		}
	}

	/**
	 * Method to Invoke Different Browsers
	 */
	public void BrowserInitialization() {
		if (headless) {
			setHeadLess();
		} else {
			String browser = Config.getProperty(Constants.PROPERTY_BROWSER);
			switch (browser) {
			// case "IE":
			// System.setProperty("webdriver.ie.driver", IEdriverpath);
			// driver = new InternetExplorerDriver();
			// driver.manage().deleteAllCookies();
			// System.out.println("Internet explorer browser launched");
			// break;
			case "Chrome":
				System.setProperty("webdriver.chrome.driver", Chromedriverpath);
				DesiredCapabilities cap = DesiredCapabilities.chrome();
				cap.setCapability("applicationCacheEnabled", false);
				driver = new ChromeDriver();
				driver.manage().deleteAllCookies();
				break;
			case "Mozilla":
				System.setProperty("webdriver.gecko.driver", Mozilladriverpath);
				driver = new FirefoxDriver();
				driver.manage().deleteAllCookies();
				break;
			}
		}
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
	}

	/**
	 * method to set headless browser
	 */
	private void setHeadLess() {
		String browser = Config.getProperty(Constants.PROPERTY_BROWSER);
		switch (browser) {
		case "IE":
			// TODO
			break;

		case "Chrome":
			ChromeOptions options = new ChromeOptions();
			options.addArguments("headless");
			options.addArguments("window-size=1200x600");
			driver = new ChromeDriver(options);
			break;
		}

	}

	/**
	 * method to handle frames
	 * 
	 */
	public void switchFrame() throws InterruptedException {
		int Last = 0;
		Thread.sleep(1000);
		driver.switchTo().defaultContent();
		Thread.sleep(1000);
		List<WebElement> frames = driver.findElements(By.tagName("iframe"));
		for (WebElement frame : frames) {
			System.out.println((frame.getAttribute("Name")));
		}
		System.out.println(frames.size());
		Last = frames.size() - 2;
		System.out.println(Last);
		driver.switchTo().frame(Last);
		Thread.sleep(1000);
	}

	/**
	 * method to handle alert window
	 */
	public boolean isAlertPresent() {
		try {
			Alert alert = driver.switchTo().alert();
			alert.accept();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * method to launch the application URL
	 */
	public void ApplicationURL() {
		try {
			driver.get(Config.getProperty(Constants.APPLICATION_URL));
			driver.manage().window().maximize();
			driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);

		} catch (Exception e) {
			System.out.println("Error in launching the application URL");
			;
		}
	}

	/**
	 * method to take screen shot
	 */
	public void captureScreenShot() {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(source, new File("./screenshots/" + System.currentTimeMillis() + "Screen.png"));
		} catch (IOException e) {

			System.out.println("Error while capturing screen shot");
		}
		System.out.println("Screen shot taken for the transaction");
		System.out.println("===========================================================");
	}

}
