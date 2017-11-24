package com.ntt.acoe.framework.selenium;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.ntt.acoe.framework.config.Environment;
import com.ntt.acoe.framework.run.TestRunner;
import com.ntt.acoe.framework.selenium.report.Reporting;
import com.ntt.acoe.framework.selenium.verify.Assert;

/*
 * @author Santosh Hariprasad (NTT Badge Id: 244583,
 *         Santhosh.Hariprasad@NTTDATA.com)
 * @version 1.0
 * @since 2015-01-01
 */
public class NTTDriver implements WebDriver, HasInputDevices {
	public static WebDriver driver;

	public static void setup() {		
		try {
			if((Environment.get("driver")==null || Environment.get("driver").trim()=="")&&(Environment.get("use_ntt_driver").trim().equalsIgnoreCase("yes")||Environment.get("use_ntt_driver").trim().equalsIgnoreCase("true"))){
				System.out.println("Driver is not defined in environment(ie/firefox/chrome/gecko) - so loading with FirefoxDriver");
				driver = new FirefoxDriver();
			}else{
				System.out.println("Loading the driver from environment:" + Environment.get("driver"));
				if (Environment.get("driver").trim().equalsIgnoreCase("chrome")) {
					System.setProperty("webdriver.chrome.driver", Environment.get("driver_path"));
					driver = new ChromeDriver();
				} else if (Environment.get("driver").trim().equalsIgnoreCase("ie")) {
					System.setProperty("webdriver.ie.driver", Environment.get("driver_path"));
					DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
					capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
					true);
					capabilities.setCapability("useLegacyInternalServer", true);
					capabilities.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
					capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
					
					driver = new InternetExplorerDriver(capabilities);					
				} else if (Environment.get("driver").trim().equalsIgnoreCase("firefox")) {
					if(Environment.get("driver_path")==null || Environment.get("driver_path").trim().equalsIgnoreCase("")){
						System.out.println("Driver Path is not defined in environment for firefox - so launching with default options, Please setup value for driver_path if selenium version is 3.0 or above");
						driver = new FirefoxDriver();
					}else{
						System.setProperty("webdriver.gecko.driver", Environment.get("driver_path"));
						driver = new FirefoxDriver();
					}
				} else if (Environment.get("driver").trim().equalsIgnoreCase("gecko")) {
					System.setProperty("webdriver.gecko.driver", Environment.get("driver_path"));
					driver = new FirefoxDriver();					
				} else {
					System.out.println("Driver is defined in environment: "+Environment.get("driver")+" is not supported. Supported options are: ie/firefox/chrome/gecko - so loading with FirefoxDriver");
					if(Environment.get("driver_path")==null || Environment.get("driver_path").trim().equalsIgnoreCase("")){
						System.out.println("Driver Path is not defined in environment for firefox - so launching with default options, Please setup value for driver_path if selenium version is 3.0 or above");
						driver = new FirefoxDriver();
					}else{
						System.setProperty("webdriver.gecko.driver", Environment.get("driver_path"));
						driver = new FirefoxDriver();
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Loading the driver from environment:" + Environment.get("driver"));
			e.printStackTrace();
			//driver = new FirefoxDriver();
			Assert.error(e, "Failed loading driver(Browser). Please check values for driver and driver_path in Environment sheet");
			TestRunner.stopExecution("Failed loading driver(Browser). Please check values for driver and driver_path in Environment sheet");
		}

		Reporting.report("DONE", "NTT Driver loaded, setting up timeouts");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(100, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();

		try {
			String timeout = Environment.get("timeout");
			String timeouts = Environment.get("timeouts");
			String defaultTimeouts = "30,120,120";

			if (timeouts != null) {
				defaultTimeouts = timeouts;
			} else if (timeout != null) {
				defaultTimeouts = timeout;
			} else {
				defaultTimeouts = "30,120,120";
			}

			Reporting.report("DONE", "Setting up timeouts");
			String[] timeoutTokens = defaultTimeouts.split("\\,", -1);
			try {
				Reporting.report("DONE", "Setting up timeouts, setting up implicitly timeout from environment to " + timeoutTokens[0] + " sec");
				driver.manage().timeouts().implicitlyWait(Integer.valueOf(timeoutTokens[0]), TimeUnit.SECONDS);
			} catch (Exception e) {
				Reporting.report("DONE", "Setting up timeouts, exception occured, setting up default implicitly timeout to 30 sec");
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			}

			try {
				Reporting.report("DONE", "Setting up timeouts, setting up pageLoadTimeout from environment to " + timeoutTokens[1] + " sec");
				driver.manage().timeouts().pageLoadTimeout(Integer.valueOf(timeoutTokens[0]), TimeUnit.SECONDS);
			} catch (Exception e) {
				Reporting.report("DONE", "Setting up timeouts, exception occured, setting up default pageLoadTimeout to 120 sec");
				driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
			}

			try {
				Reporting.report("DONE", "Setting up timeouts, setting up setScriptTimeout timeout from environment to " + timeoutTokens[2] + " sec");
				driver.manage().timeouts().setScriptTimeout(Integer.valueOf(timeoutTokens[0]), TimeUnit.SECONDS);
			} catch (Exception e) {
				Reporting.report("DONE", "Setting up timeouts, exception occured, setting up default setScriptTimeout to 120 sec");
				driver.manage().timeouts().setScriptTimeout(120, TimeUnit.SECONDS);
			}

		} catch (Exception e) {
			e.printStackTrace();
			Reporting.report("DONE", "Exception raised while setting up timeouts from environment, so going with default timeouts");
		}
	}

	
	public static WebDriver getDriver1() {
		if (driver == null) {
			setup();
		}

		return driver;
	}

	public NTTDriver() {
		driver = getDriver1();
	}

	@Override
	public void close() {
		driver.close();
	}

	public NTTRemoteWebElement findElement(com.ntt.acoe.framework.selenium.By by) {
		NTTRemoteWebElement nTTRemoteWebElement = new NTTRemoteWebElement(by);
		return nTTRemoteWebElement;
	}

	public List<WebElement> findElements(com.ntt.acoe.framework.selenium.By arg0) {
		return driver.findElements(arg0);
	}

	@Override
	public NTTRemoteWebElement findElement(org.openqa.selenium.By by) {
		NTTRemoteWebElement nTTRemoteWebElement = new NTTRemoteWebElement(by);
		return nTTRemoteWebElement;
	}

	@Override
	public List<WebElement> findElements(org.openqa.selenium.By arg0) {
		return driver.findElements(arg0);
	}

	@Override
	public void get(String arg0) {
		driver.get(arg0);
	}

	@Override
	public String getCurrentUrl() {
		return driver.getCurrentUrl();
	}

	@Override
	public String getPageSource() {
		return driver.getPageSource();
	}

	@Override
	public String getTitle() {
		return driver.getTitle();
	}

	@Override
	public String getWindowHandle() {
		return driver.getWindowHandle();
	}

	@Override
	public Set<String> getWindowHandles() {
		return driver.getWindowHandles();
	}

	@Override
	public Options manage() {
		return driver.manage();
	}

	@Override
	public Navigation navigate() {
		return driver.navigate();
	}

	@Override
	public void quit() {
		driver.quit();
	}

	@Override
	public TargetLocator switchTo() {
		return driver.switchTo();
	}

	@Override
	public Keyboard getKeyboard() {
		return ((RemoteWebDriver) driver).getKeyboard();
	}

	@Override
	public Mouse getMouse() {
		// return ((RemoteWebDriver) driver).getMouse();
		try {
			if (Environment.get("driver").equalsIgnoreCase("chrome")) {
				return ((ChromeDriver) driver).getMouse();
			} else if (Environment.get("driver").equalsIgnoreCase("ie")) {
				return ((InternetExplorerDriver) driver).getMouse();
			} else if (Environment.get("driver").equalsIgnoreCase("firefox")) {
				return ((FirefoxDriver) driver).getMouse();
			} else {
				return ((FirefoxDriver) driver).getMouse();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ((FirefoxDriver) driver).getMouse();
		}
	}

}
