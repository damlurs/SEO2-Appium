package com.ntt.acoe.framework.selenium;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/*
 * @author Santosh Hariprasad (NTT Badge Id: 244583,
 *         Santhosh.Hariprasad@NTTDATA.com)
 * @version 1.0
 * @since 2015-01-01
 */
public class Driver extends org.openqa.selenium.firefox.FirefoxDriver {
	public static WebDriver driver = getDriver();

	public static void setUp() {
		WebDriver driver = new FirefoxDriver();
		// File browser=new
		// File("C:\\IOL\\IOLAutomation\\project\\lib\\IEDriverServer.exe");
		// System.setProperty("webdriver.ie.driver", browser.getAbsolutePath());
		if (driver == null) {
			// driver = new InternetExplorerDriver();
			driver = new FirefoxDriver();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
			driver.manage().window().maximize();
		}

	}

	public WebElement getWebElement(String locatorBy, String objectPropertyValue, int timeOut) throws Exception {
		WebElement webElement = null; // to store the webelement
		try {
			Class<?> byClass = Class.forName(By.class.getName());
			Method byMethod = byClass.getMethod(locatorBy, String.class);
			By newBy = (By) byMethod.invoke(null, objectPropertyValue);
			WebDriverWait wait = new WebDriverWait(driver, timeOut);// the
																	// maximum
																	// time to
																	// wait
																	// before
																	// failing
			webElement = wait.until(ExpectedConditions.visibilityOfElementLocated(newBy));

			/*
			 * Class<?> webElementClass =
			 * Class.forName(Driver.webDriver.getClass().getName()); Method
			 * findElementMethod = webElementClass.getMethod("findElement",new
			 * Class[]{By.class}); webElement = (WebElement)
			 * findElementMethod.invoke(Driver.webDriver,newBy);
			 */

		} catch (NoSuchElementException exNoElement) {
			throw exNoElement;
		} catch (Exception genException) {
			throw new Exception("Failed in object identification for locator: " + locatorBy + "and property: " + objectPropertyValue + " Detailed Information :" + genException);
		}
		return webElement;
	}

	public WebElement findElement(String locatorBy, String objectPropertyValue) {
		WebElement webElement = null;
		try {
			Class<?> byClass = Class.forName(By.class.getName());
			Method byMethod = byClass.getMethod(locatorBy, String.class);
			By newBy = (By) byMethod.invoke(null, objectPropertyValue);
			WebDriverWait wait = new WebDriverWait(driver, 30);// the maximum
																// time to wait
																// before
																// failing
			webElement = wait.until(ExpectedConditions.visibilityOfElementLocated(newBy));
		} catch (NoSuchElementException exNoElement) {
			throw exNoElement;
		} catch (Exception genException) {
			try {
				throw new Exception("Failed in object identification for locator: " + locatorBy + "and property: " + objectPropertyValue + " Detailed Information :" + genException);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return webElement;
	}

	public static void tearDown() {
		driver.close();
		driver.quit();
	}

	public static WebDriver getDriver() {
		if (driver == null) {
			setUp();
		}
		return Driver.driver;
	}

	public static void scrollDown() {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollTo(0,Math.max(document.documentElement.scrollHeight," + "document.body.scrollHeight,document.documentElement.clientHeight));");
		} catch (Exception e) {
		}
	}

	public static void scrollDownHalf() {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollTo(0,Math.max(document.documentElement.scrollHeight," + "document.body.scrollHeight,document.documentElement.clientHeight)/2);");
		} catch (Exception e) {
		}
	}
}
