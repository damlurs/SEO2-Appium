package com.ntt.acoe.framework.selenium;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.naming.NamingException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
//import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
public class FrameworkDriver {
	public static WebDriver driver;
	 ////public static WebDriver driver = getNewDriver();
	//// public static WebDriver driver = new DellDriver();
	
	public FrameworkDriver(){
		driver = getNewDriverForEnv();		
	}

	public static WebDriver getDriver() {		
		return driver;
	}

	public static WebDriver getNewDriverForEnv() {
		//WebDriver driver = null;
		  //driver = null;

		try {
			//|| Environment.get("use_dell_driver").trim().equalsIgnoreCase("true"))
			if (Environment.get("use_ntt_driver").trim().equalsIgnoreCase("yes"))  {
				if (driver == null) {
					driver = new NTTDriver();
				}
			} else {
				//driver = getNewDriver();
				//driver = null;
				System.out.println("Loading Custom Driver..");
			}
		} catch (Exception e) {
			//driver = getNewDriver();
			//driver = null;
			e.printStackTrace();
		}

		return driver;
	}

	public static WebDriver getNewDriver() {
		//WebDriver driver = null;
		//driver = null;
		try {
			System.out.println("Loading the driver from environment:" + Environment.get("driver"));
			//(Environment.get("use_ntt_driver").trim().equalsIgnoreCase("no"))
			//(Environment.get("driver").trim().equalsIgnoreCase("none"))
			if ((Environment.get("use_ntt_driver").trim().equalsIgnoreCase("no"))) {
				System.out.println("Driver is defined as none so driver is returned as null. Caller has to setup their own driver");
				driver = null;
			}else if (Environment.get("driver").trim().equalsIgnoreCase("chrome")) {
				System.setProperty("webdriver.chrome.driver", Environment.get("driver_path"));
				driver = new ChromeDriver();
			} else if (Environment.get("driver").trim().equalsIgnoreCase("ie")) {
				System.setProperty("webdriver.ie.driver", Environment.get("driver_path"));
				DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
				capabilities.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
				capabilities.setCapability("requireWindowFocus", true);
				driver = new InternetExplorerDriver(capabilities);
			} else if (Environment.get("driver").trim().equalsIgnoreCase("firefox")) {
				if(Environment.get("driver_path")==null || Environment.get("driver_path").trim().equalsIgnoreCase("")){
					System.out.println("Driver Path is not defined in environment for firefox - so launching with default options, Please setup value for driver_path if selenium version is 3.0 or above");
					driver = new FirefoxDriver();
				}else if (Environment.get("driver").trim().equalsIgnoreCase("gecko")) {
					System.setProperty("webdriver.gecko.driver", Environment.get("driver_path"));
					driver = new FirefoxDriver();
				}
			} else if (Environment.get("driver").trim().equalsIgnoreCase("gecko")) {
				System.setProperty("webdriver.gecko.driver", Environment.get("driver_path"));
				driver = new FirefoxDriver();
			} else if (Environment.get("driver").trim().equalsIgnoreCase("edge")) {
				System.setProperty("webdriver.gecko.driver", Environment.get("driver_path"));
				driver = new EdgeDriver();
			}else if (Environment.get("driver").trim().equalsIgnoreCase("none")) {
				System.out.println("Driver is defined as none so driver is returned as null. Caller has to setup their own driver");
				driver = null;
			}else {
				System.out.println("Driver defined in environment: "+Environment.get("driver")+" is not supported. Supported options are: none/ie/firefox/chrome/gecko/edge - so loading with FirefoxDriver");
				if(Environment.get("driver_path")==null || Environment.get("driver_path").trim().equalsIgnoreCase("")){
					System.out.println("Driver Path is not defined in environment for firefox - so launching with default options, Please setup value for driver_path if selenium version is 3.0 or above");
					driver = new FirefoxDriver();
				}else{
					System.setProperty("webdriver.gecko.driver", Environment.get("driver_path"));
					driver = new FirefoxDriver();
				}
			}
		} catch (Exception e) {
			System.out.println("Loading the driver from environment:" + Environment.get("driver"));
			e.printStackTrace();
			Assert.error(e, "Failed loading driver(Browser). Please check values for driver and driver_path in Environment sheet");
			TestRunner.stopExecution("Failed loading driver(Browser). Please check values for driver and driver_path in Environment sheet");
		}

		if(driver!=null){
			Reporting.report("DONE", "Dell Driver loaded, setting up timeouts");
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

		return driver;
	}

	/*
	 * public static void setUp(){
	 * if(Configuration.DRIVER.equalsIgnoreCase("chrome")){
	 * System.setProperty("webdriver.chrome.driver",
	 * Configuration.DRIVER_CHROME_PATH); driver = new ChromeDriver(); }else
	 * if(Configuration.DRIVER.equalsIgnoreCase("ie")){
	 * System.setProperty("webdriver.ie.driver", Configuration.DRIVER_IE_PATH);
	 * driver = new InternetExplorerDriver(); }else
	 * if(Configuration.DRIVER.equalsIgnoreCase("firefox")){ driver = new
	 * FirefoxDriver(); }else{ driver = new FirefoxDriver(); }
	 * 
	 * driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	 * driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
	 * driver.manage().window().maximize(); }
	 */

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

	public WebElement findElement(String locatorBy, String objectPropertyValue) throws Exception {
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
			throw new Exception("Failed in object identification for locator: " + locatorBy + "and property: " + objectPropertyValue + " Detailed Information :" + genException);
		}
		return webElement;
	}

	public static void tearDown() {
		if(Environment.get("use_ntt_driver").trim().equalsIgnoreCase("yes")) {
			driver.close();
			driver.quit();
		}
		
	}

	/*
	 * public static WebDriver getDriver(){ try{ SimpleDateFormat sdf = new
	 * SimpleDateFormat("yyyy-MM-dd"); Date date1 = sdf.parse("2015-01-01");
	 * 
	 * Date date2 = new Date(); System.out.println(sdf.format(date1));
	 * System.out.println(sdf.format(date2));
	 * 
	 * if(date2.compareTo(date1)>0){ //System.out.println(
	 * "Your license has been expired! \nPlease contact Vijay Devireddy at (Vijaya_Bhaskar_Devir@dell.com)"
	 * ); }
	 * 
	 * }catch(ParseException ex){ ex.printStackTrace(); }
	 * 
	 * if(driver == null){ setUp(); }
	 * 
	 * return FrameworkDriver.driver; }
	 */

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
