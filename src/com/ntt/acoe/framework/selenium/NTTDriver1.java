package com.ntt.acoe.framework.selenium;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.ntt.acoe.framework.config.Environment;

/*
 * @author Santosh Hariprasad (NTT Badge Id: 244583,
 *         Santhosh.Hariprasad@NTTDATA.com)
 * @version 1.0
 * @since 2015-01-01
 */
public class NTTDriver1 implements WebDriver {
	public static WebDriver driver;

	public static void setup() {
		if (Environment.get("DRIVER").equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", Environment.get("DRIVER_PATH"));
			driver = new ChromeDriver();
		} else if (Environment.get("DRIVER").equalsIgnoreCase("ie")) {
			System.setProperty("webdriver.ie.driver", Environment.get("DRIVER_PATH"));
			driver = new InternetExplorerDriver();
		} else if (Environment.get("DRIVER").equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
		} else {
			driver = new FirefoxDriver();
		}

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	public static WebDriver getDriver1() {
		if (driver == null) {
			setup();
		}

		return driver;
	}

	public NTTDriver1() {
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

}
