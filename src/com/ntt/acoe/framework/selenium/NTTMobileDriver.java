package com.ntt.acoe.framework.selenium;

//Java
import java.net.URL;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.net.MalformedURLException;

//Selenium
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.html5.LocationContext;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.ContextAware;
import org.openqa.selenium.Rotatable;

//Appium
import io.appium.java_client.*;
import io.appium.java_client.android.*;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.StartsActivity;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;

//NTT Framework
import com.ntt.acoe.framework.config.Environment;
import com.ntt.acoe.framework.run.TestRunner;
import com.ntt.acoe.framework.selenium.report.Reporting;
import com.ntt.acoe.framework.selenium.verify.Assert;

public class NTTMobileDriver extends RemoteWebDriver {	
	public static AppiumDriver<MobileElement> driver;	
	public static DesiredCapabilities cap;
	
	public static void setup() throws MalformedURLException{		
		try {
			
			if((Environment.get("driver")==null || Environment.get("driver").trim()=="")&&(Environment.get("use_ntt_driver").trim().equalsIgnoreCase("yes")||Environment.get("use_ntt_driver").trim().equalsIgnoreCase("true"))){
				System.out.println("Driver is not defined in environment(android/ios) - so loading with android driver");
				androidCapabilities();
				driver = new AndroidDriver<MobileElement>(new URL(Environment.get("appium_server_url")), cap);				
				
			}else{				
				if (Environment.get("driver").trim().equalsIgnoreCase("android")) {
					System.out.println("Loading driver.. "+Environment.get("driver").trim());
					androidCapabilities();
					driver = new AndroidDriver<MobileElement>(new URL(Environment.get("appium_server_url")), androidCapabilities());					
				} else if (Environment.get("driver").trim().equalsIgnoreCase("ios")) {	
					//To be implemented				
					driver=null;
				} else {
					System.out.println("Driver is defined in environment: "+Environment.get("driver")+" is not supported. Supported options are: android/ios - so loading by default android driver");
					driver = null;				
				}
			}
		} catch (Exception e) {
			System.out.println("Loading the driver from environment:" + Environment.get("driver"));
			//e.printStackTrace();
			
			Assert.error(e, "Failed loading driver(Mobile). Please check values for driver and driver_path in Environment sheet");
			TestRunner.stopExecution("Failed loading driver(Mobile). Please check values for driver and driver_path in Environment sheet");
		}

		Reporting.report("DONE", "NTT Driver loaded, setting up timeouts");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);		

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

	public static DesiredCapabilities androidCapabilities() {
		try {
			cap = new DesiredCapabilities();
			cap.setCapability("deviceName", Environment.get("android_device_Name"));
			cap.setCapability("udid", Environment.get("android_udid")); //Give Device ID of your mobile phone
			cap.setCapability("platformName", Environment.get("android_platform_Name"));
			cap.setCapability("platformVersion", Environment.get("android_platform_Version"));
			cap.setCapability("appPackage", Environment.get("android_app_Package"));
			cap.setCapability("appActivity", Environment.get("android_app_Activity"));
			cap.setCapability("noReset", Environment.get("android_no_Reset"));
			cap.setCapability("unicodeKeyboard", true);
			cap.setCapability("resetKeyboard", true);
		}catch(Exception e) {
			e.printStackTrace();
		}		
		return cap;
	}
	
	public static AppiumDriver<MobileElement> getDriver() {
		try {
			if (driver == null) {
				setup();
			}
		}catch(Exception e) {
			//e.printStackTrace();
		}
		return driver;
	}

	public void NTTMobileDriver() {
		driver = getDriver();		
	}

	@Override
	public Keyboard getKeyboard() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mouse getMouse() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
