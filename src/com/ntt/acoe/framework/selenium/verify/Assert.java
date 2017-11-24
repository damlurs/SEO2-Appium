package com.ntt.acoe.framework.selenium.verify;

import java.util.List;
import java.util.Random;

import com.ntt.acoe.framework.config.Configuration;
import com.ntt.acoe.framework.config.Environment;
import com.ntt.acoe.framework.loggers.ScriptLogger;
import com.ntt.acoe.framework.selenium.By;
import com.ntt.acoe.framework.selenium.FrameworkDriver;
import com.ntt.acoe.framework.selenium.Select;
import com.ntt.acoe.framework.selenium.report.Reporting;
import com.ntt.acoe.framework.selenium.report.TestResult;
import com.ntt.acoe.framework.selenium.test.CurrentTest;
import com.ntt.acoe.framework.selenium.util.AWTScreenshot;

/*
 * @author Santosh Hariprasad (NTT Badge Id: 244583,
 *         Santhosh.Hariprasad@NTTDATA.com)
 * @version 1.0
 * @since 2015-01-01
 */
public class Assert {

	public static void main(String[] args) {
		Assert.assertFalse(false);
		Assert.assertFalse(true);
	}

	public static void warning() {
		try {
			org.testng.Assert.fail();
			Reporting.reportResult("WARN", " making test warning");
			generateScreenshot(false);
		} catch (Error e) {
			Reporting.reportResult("WARN", " making test marning! " + e.getMessage());
			generateScreenshot(false);
		}
	}

	public static void warning(String message) {
		try {
			Reporting.reportResult("WARN", message);			
			generateScreenshot(true);
		} catch (Error e) {			
			generateScreenshot(true);
		}
	}

	public static void fail() {
		try {
			org.testng.Assert.fail();
			Reporting.reportResult("FAIL", " making test fail");
			generateScreenshot(false);
		} catch (Error e) {
			Reporting.reportResult("FAIL", " making test fail - failed" + e.getMessage());
			generateScreenshot(false);
		}
	}

	public static void pass(String message) {
		try {
			Reporting.reportResult("PASS", message);
			generateScreenshot(true);
		} catch (Error e) {
			Reporting.reportResult("FAIL", message + ". making this test pass - failed. Error:" + e.getMessage());
			generateScreenshot(true);
		}
	}

	public static void done(String message) {
		try {
			System.out.println(message);
			ScriptLogger.log.info("DONE " + message);
			Reporting.reportResult("DONE", message);
			generateScreenshot(true);
		} catch (Error e) {
			e.printStackTrace();
			System.out.println(message + ". failed while writing done to report. Error:" + e.getMessage());
			ScriptLogger.log.info("DONE " + message + ". failed while writing done to report. Error:" + e.getMessage());
			Reporting.reportResult("FAIL", message + ". failed while writing done to report. Error:" + e.getMessage());
			generateScreenshot(true);
		}
	}

	public static void fail(String message) {
		try {
			Reporting.reportResult("FAIL", message);
			org.testng.Assert.fail(message);
			generateScreenshot(false);
		} catch (Error e) {
			// Reporting.reportResult("FAIL", " making test fail with a message
			// - failed"+e.getMessage());
			generateScreenshot(false);
		}
	}

	public static void assertTrue(boolean actual) {
		try {
			org.testng.Assert.assertTrue(actual);
			Reporting.reportResult("PASS", "Assertion passed - Expected: true , Actual:" + actual + " - passed");
			generateScreenshot(true);
		} catch (Error e) {
			Reporting.reportResult("FAIL", "Assertion failed - Expected: true , Actual:" + actual + " - " + e.getMessage());
			generateScreenshot(false);
		}
	}

	public static void assertTrue(boolean actual, String message) {
		try {
			org.testng.Assert.assertTrue(actual);
			Reporting.reportResult("PASS", message);
			generateScreenshot(true);
		} catch (Error e) {
			Reporting.reportResult("FAIL", message + " - " + e.getMessage());
			generateScreenshot(false);
		}
	}

	public static void assertFalse(boolean actual) {
		try {
			org.testng.Assert.assertFalse(actual);
			Reporting.reportResult("PASS", "Assertion passed - Expected: false , Actual:" + actual + " - passed");
			generateScreenshot(true);
		} catch (Error e) {
			Reporting.reportResult("FAIL", "Assertion failed - Expected: false , Actual:" + actual + " - " + e.getMessage());
			generateScreenshot(false);
		}
	}

	public static void assertFalse(boolean actual, String message) {
		try {
			org.testng.Assert.assertFalse(actual);
			Reporting.reportResult("PASS", message);
			generateScreenshot(true);
		} catch (Error e) {
			Reporting.reportResult("FAIL", message + " - " + e.getMessage());
			generateScreenshot(false);
		}
	}

	public static void assertNotNull(java.lang.Object object) {
		try {
			org.testng.Assert.assertNotNull(object);
			Reporting.reportResult("PASS", "Assertion passed - Expected: Not null , Actual:" + object + " - passed");
			generateScreenshot(true);
		} catch (Error e) {
			Reporting.reportResult("FAIL", "Assertion failed - Expected: Not null , Actual:" + object + " - " + e.getMessage());
			generateScreenshot(false);
		}
	}

	public static void assertNull(java.lang.Object object) {
		try {
			org.testng.Assert.assertNull(object);
			Reporting.reportResult("PASS", "Assertion passed - Expected: null , Actual:" + object + " - passed");
			generateScreenshot(true);
		} catch (Error e) {
			Reporting.reportResult("FAIL", "Assertion failed - Expected: null , Actual:" + object + " - " + e.getMessage());
			generateScreenshot(false);
		}
	}

	public static void assertSame(java.lang.Object actual, java.lang.Object expected) {
		try {
			org.testng.Assert.assertSame(actual, expected);
			Reporting.reportResult("PASS", "Assertion passed - Same, Expected: " + expected + " , Actual:" + expected + " - passed");
			generateScreenshot(true);
		} catch (Error e) {
			Reporting.reportResult("FAIL", "Assertion failed - Same, Expected: " + expected + "  , Actual:" + expected + " - " + e.getMessage());
			generateScreenshot(false);
		}
	}

	public static void assertNotSame(java.lang.Object actual, java.lang.Object expected) {
		try {
			org.testng.Assert.assertNotSame(actual, expected);
			Reporting.reportResult("PASS", "Assertion passed - NotSame, Expected: " + expected + " , Actual:" + expected + " - passed");
			generateScreenshot(true);
		} catch (Error e) {
			Reporting.reportResult("FAIL", "Assertion failed - NotSame, Expected: " + expected + "  , Actual:" + expected + " - " + e.getMessage());
			generateScreenshot(false);
		}
	}

	public static void assertNotEquals(double actual1, double actual2, double delta) {
		try {
			org.testng.Assert.assertNotEquals(actual1, actual2, delta);
			Reporting.reportResult("PASS", "Assertion pass - Expected: true , Actual:" + actual1 + " - " + actual2);
			generateScreenshot(true);
		} catch (Error e) {
			Reporting.reportResult("FAIL", "Assertion fail - Expected: true , Actual:" + actual1 + " - " + actual2);
		}
	}

	public static void assertNotEquals(float actual1, float actual2, float delta) {
		try {
			org.testng.Assert.assertNotEquals(actual1, actual2, delta);
			Reporting.reportResult("PASS", "Assertion pass - Expected: true , Actual:" + actual1 + " - " + actual2);
			generateScreenshot(true);
		} catch (Error e) {
			Reporting.reportResult("FAIL", "Assertion fail - Expected: true , Actual:" + actual1 + " - " + actual2);
			generateScreenshot(false);
		}
	}

	public static void assertNotEquals(java.lang.Object actual1, java.lang.Object actual2) {
		try {
			org.testng.Assert.assertNotEquals(actual1, actual2);
			Reporting.reportResult("PASS", "Assertion pass - Expected: true , Actual:" + actual1 + " - " + actual2);
			generateScreenshot(true);
		} catch (Error e) {
			Reporting.reportResult("FAIL", "Assertion fail - Expected: true , Actual:" + actual1 + " - " + actual2);
			generateScreenshot(false);
		}
	}

	public static void assertEquals(byte[] actual, byte[] expected) {
		try {
			org.testng.Assert.assertEquals(actual, expected);
			Reporting.reportResult("PASS", "Assertion passed - Expected: " + expected + " , Actual:" + actual + " - passed");
			generateScreenshot(true);
		} catch (Error e) {
			Reporting.reportResult("FAIL", "Assertion failed - Expected: " + expected + " , Actual:" + actual + " - " + e.getMessage());
			generateScreenshot(false);
		}
	}

	public static void assertEquals(byte actual, byte expected) {
		try {
			org.testng.Assert.assertEquals(actual, expected);
			Reporting.reportResult("PASS", "Assertion passed - Expected: " + expected + " , Actual:" + actual + " - passed");
			generateScreenshot(true);
		} catch (Error e) {
			Reporting.reportResult("FAIL", "Assertion failed - Expected: " + expected + " , Actual:" + actual + " - " + e.getMessage());
			generateScreenshot(false);
		}
	}

	public static void assertEquals(char actual, char expected) {
		try {
			org.testng.Assert.assertEquals(actual, expected);
			Reporting.reportResult("PASS", "Assertion passed - Expected: " + expected + " , Actual:" + actual + " - passed");
			generateScreenshot(true);
		} catch (Error e) {
			Reporting.reportResult("FAIL", "Assertion failed - Expected: " + expected + " , Actual:" + actual + " - " + e.getMessage());
			generateScreenshot(false);
		}
	}

	@SuppressWarnings("rawtypes")
	public static void assertEquals(java.util.Collection actual, java.util.Collection expected) {
		try {
			org.testng.Assert.assertEquals(actual, expected);
			Reporting.reportResult("PASS", "Assertion passed - Expected: " + expected + " , Actual:" + actual + " - passed");
			generateScreenshot(true);
		} catch (Error e) {
			Reporting.reportResult("FAIL", "Assertion failed - Expected: " + expected + " , Actual:" + actual + " - " + e.getMessage());
			generateScreenshot(false);
		}
	}

	public static void assertEquals(double actual, double expected, double delta) {
		try {
			org.testng.Assert.assertEquals(actual, expected, delta);
			Reporting.reportResult("PASS", "Assertion passed - Expected: " + expected + " , Actual:" + actual + " - passed");
			generateScreenshot(true);
		} catch (Error e) {
			Reporting.reportResult("FAIL", "Assertion failed - Expected: " + expected + " , Actual:" + actual + " - " + e.getMessage());
			generateScreenshot(false);
		}
	}

	public static void assertEquals(float actual, float expected, float delta) {
		try {
			org.testng.Assert.assertEquals(actual, expected, delta);
			Reporting.reportResult("PASS", "Assertion passed - Expected: " + expected + " , Actual:" + actual + " - passed");
			generateScreenshot(true);
		} catch (Error e) {
			Reporting.reportResult("FAIL", "Assertion failed - Expected: " + expected + " , Actual:" + actual + " - " + e.getMessage());
			generateScreenshot(false);
		}
	}
	
	public static void assertEquals(float actual, float expected, float delta, String message) {
		try {
			org.testng.Assert.assertEquals(actual, expected, delta);
			Reporting.reportResult("PASS", message + " - Expected: " + expected + " , Actual:" + actual + " - passed");
			generateScreenshot(true);
		} catch (Error e) {
			Reporting.reportResult("FAIL", message + " - Expected: " + expected + " , Actual:" + actual + " - " + e.getMessage());
			generateScreenshot(false);
		}
	}	

	public static void assertEquals(int actual, int expected) {
		try {
			org.testng.Assert.assertEquals(actual, expected);
			Reporting.reportResult("PASS", "Assertion passed - Expected: " + expected + " , Actual:" + actual + " - passed");
			generateScreenshot(true);
		} catch (Error e) {
			Reporting.reportResult("FAIL", "Assertion failed - Expected: " + expected + " , Actual:" + actual + " - " + e.getMessage());
			generateScreenshot(false);
		}
	}
	
	public static void assertEquals(int actual, int expected, String message) {
		try {
			org.testng.Assert.assertEquals(actual, expected);
			Reporting.reportResult("PASS", message + " - Expected: " + expected + " , Actual:" + actual + " - passed");
			generateScreenshot(true);
		} catch (Error e) {
			Reporting.reportResult("FAIL", message + " - Expected: " +  expected + " , Actual:" + actual + " - " + e.getMessage());
			generateScreenshot(false);
		}
	}	

	public static void assertEquals(long actual, long expected) {
		try {
			org.testng.Assert.assertEquals(actual, expected);
			Reporting.reportResult("PASS", "Assertion passed - Expected: " + expected + " , Actual:" + actual + " - passed");
			generateScreenshot(true);
		} catch (Error e) {
			Reporting.reportResult("FAIL", "Assertion failed - Expected: " + expected + " , Actual:" + actual + " - " + e.getMessage());
			generateScreenshot(false);
		}
	}

	public static void assertEquals(java.util.Map<?, ?> actual, java.util.Map<?, ?> expected) {
		try {
			org.testng.Assert.assertEquals(actual, expected);
			Reporting.reportResult("PASS", "Assertion passed - Expected: " + expected + " , Actual:" + actual + " - passed");
			generateScreenshot(true);
		} catch (Error e) {
			Reporting.reportResult("FAIL", "Assertion failed - Expected: " + expected + " , Actual:" + actual + " - " + e.getMessage());
			generateScreenshot(false);
		}
	}

	public static void assertEquals(java.lang.Object[] actual, java.lang.Object[] expected) {
		try {
			org.testng.Assert.assertEquals(actual, expected);
			Reporting.reportResult("PASS", "Assertion passed - Expected: " + expected + " , Actual:" + actual + " - passed");
			generateScreenshot(true);
		} catch (Error e) {
			Reporting.reportResult("FAIL", "Assertion failed - Expected: " + expected + " , Actual:" + actual + " - " + e.getMessage());
			generateScreenshot(false);
		}
	}

	public static void assertEquals(java.lang.Object actual, java.lang.Object expected) {
		try {
			org.testng.Assert.assertEquals(actual, expected);
			Reporting.reportResult("PASS", "Assertion passed - Expected: " + expected + " , Actual:" + actual + " - passed");
			generateScreenshot(true);
		} catch (Error e) {
			Reporting.reportResult("FAIL", "Assertion failed - Expected: " + expected + " , Actual:" + actual + " - " + e.getMessage());
			generateScreenshot(false);
		}
	}

	@SuppressWarnings("rawtypes")
	public static void assertEquals(java.util.Set actual, java.util.Set expected) {
		try {
			org.testng.Assert.assertEquals(actual, expected);
			Reporting.reportResult("PASS", "Assertion passed - Expected: " + expected + " , Actual:" + actual + " - passed");
			generateScreenshot(true);
		} catch (Error e) {
			Reporting.reportResult("FAIL", "Assertion failed - Expected: " + expected + " , Actual:" + actual + " - " + e.getMessage());
			generateScreenshot(false);
		}
	}

	public static void assertEquals(short actual, short expected) {
		try {
			org.testng.Assert.assertEquals(actual, expected);
			Reporting.reportResult("PASS", "Assertion passed - Expected: " + expected + " , Actual:" + actual + " - passed");
			generateScreenshot(true);
		} catch (Error e) {
			Reporting.reportResult("FAIL", "Assertion failed - Expected: " + expected + " , Actual:" + actual + " - " + e.getMessage());
			generateScreenshot(false);
		}
	}

	public static void assertEquals(java.lang.String actual, java.lang.String expected) {
		try {
			org.testng.Assert.assertEquals(actual, expected);
			Reporting.reportResult("PASS", "Assertion passed - Expected: " + expected + " , Actual:" + actual + " - passed");
			generateScreenshot(true);
		} catch (Error e) {
			Reporting.reportResult("FAIL", "Assertion failed - Expected: " + expected + " , Actual:" + actual + " - " + e.getMessage());
			generateScreenshot(false);
		}
	}

	public static void assertEquals(java.lang.String actual, java.lang.String expected, String message) {
		try {
			org.testng.Assert.assertEquals(actual, expected);
			Reporting.reportResult("PASS", message + " - Expected: " + expected + " , Actual:" + actual + " - passed");
			generateScreenshot(true);
		} catch (Error e) {
			Reporting.reportResult("FAIL", message + " - Expected: " + expected + " , Actual:" + actual + " - " + e.getMessage());
			generateScreenshot(false);
		}
	}

	public static void assertEqualsNoOrder(java.lang.Object[] actual, java.lang.Object[] expected) {
		try {
			org.testng.Assert.assertEquals(actual, expected);
			Reporting.reportResult("PASS", "Assertion passed - Expected: " + expected + " , Actual:" + actual + " - passed");
			generateScreenshot(true);
		} catch (Error e) {
			Reporting.reportResult("FAIL", "Assertion failed - Expected: " + expected + " , Actual:" + actual + " - " + e.getMessage());
			generateScreenshot(false);
		}
	}

	public static void assertWebElement(String elementName) {
		try {
			if (elementName.equalsIgnoreCase("") || elementName == null) {
				System.out.println("Verifying WebElement:" + elementName + " is empty or null, check your fieldname");
				Assert.fail("Verifying WebElement:" + elementName + " is empty or null, check your fieldname");
			} else if (By.logicalName(elementName) == null) {
				System.out.println("Verifying WebElement:" + elementName + " does not exist");
				Assert.fail("Verifying WebElement:" + elementName + " does not exist");
			} else if (!FrameworkDriver.driver.findElement(By.logicalName(elementName)).isDisplayed()) {
				System.out.println("Verifying if WebElement:" + elementName + " is exist - success");
				Assert.pass("Verifying if WebElement:" + elementName + " is exist - success");
				System.out.println("Verifying WebElement:" + elementName + " is not displayed");
				Assert.fail("Verifying WebElement:" + elementName + " is not displayed");
			} else if (!FrameworkDriver.driver.findElement(By.logicalName(elementName)).isEnabled()) {
				System.out.println("Verifying if WebElement:" + elementName + " is exist - success");
				Assert.pass("Verifying if WebElement:" + elementName + " is exist - success");
				System.out.println("Verifying if WebElement:" + elementName + " is displayed - success");
				Assert.pass("Verifying if WebElement:" + elementName + " is displayed - success");
				System.out.println("Verifying WebElement:" + elementName + " is not enabled");
				Assert.fail("Verifying WebElement:" + elementName + " is not enabled");
			} else if (elementName.endsWith("WL")) {
				System.out.println("Verifying if WebElement:" + elementName + " is exist - success");
				Assert.pass("Verifying if WebElement:" + elementName + " is exist - success");
				System.out.println("Verifying if WebElement:" + elementName + " is displayed - success");
				Assert.pass("Verifying if WebElement:" + elementName + " is displayed - success");
				System.out.println("Verifying if WebElement:" + elementName + " is enabled - success");
				Assert.pass("Verifying if WebElement:" + elementName + " is enabled - success");

				Select s = new Select(FrameworkDriver.driver.findElement(By.logicalName(elementName)));
				String selectedOption = s.getFirstSelectedOption().getText();
				try {
					s.selectByIndex(0);
					s.selectByVisibleText(selectedOption);
					System.out.println("Filling the form, Selected field:" + elementName + " - success");
					Assert.pass("Filling the form, Selected field:" + elementName + " - success");
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("Verifying WebElement:" + elementName + " . Error while selecting an item");
					Assert.fail("Verifying WebElement:" + elementName + " . Error while selecting an item");
				}
			} else if (elementName.endsWith("RB")) {
				System.out.println("Verifying if WebElement:" + elementName + " is exist - success");
				Assert.pass("Verifying if WebElement:" + elementName + " is exist - success");
				System.out.println("Verifying if WebElement:" + elementName + " is displayed - success");
				Assert.pass("Verifying if WebElement:" + elementName + " is displayed - success");
				System.out.println("Verifying if WebElement:" + elementName + " is enabled - success");
				Assert.pass("Verifying if WebElement:" + elementName + " is enabled - success");

				List<org.openqa.selenium.WebElement> radioButtons = FrameworkDriver.driver.findElements(By.logicalName(elementName));
				org.openqa.selenium.WebElement selectedRadioButton = null;
				for (org.openqa.selenium.WebElement radioButton : radioButtons) {
					if (!radioButton.isSelected()) {
						selectedRadioButton = radioButton;
						break;
					}
				}

				if (selectedRadioButton != null) {
					for (org.openqa.selenium.WebElement radioButton : radioButtons) {
						if (!radioButton.isSelected()) {
							radioButton.click();
						}
					}
					if (!selectedRadioButton.isSelected()) {
						selectedRadioButton.click();
					}
				}
			} else if (elementName.endsWith("CB")) {
				System.out.println("Verifying if WebElement:" + elementName + " is exist - success");
				Assert.pass("Verifying if WebElement:" + elementName + " is exist - success");
				System.out.println("Verifying if WebElement:" + elementName + " is displayed - success");
				Assert.pass("Verifying if WebElement:" + elementName + " is displayed - success");
				System.out.println("Verifying if WebElement:" + elementName + " is enabled - success");
				Assert.pass("Verifying if WebElement:" + elementName + " is enabled - success");

				List<org.openqa.selenium.WebElement> checkBoxes = FrameworkDriver.driver.findElements(By.logicalName(elementName));
				org.openqa.selenium.WebElement selectedCheckBox = null;
				for (org.openqa.selenium.WebElement checkBox : checkBoxes) {
					if (!checkBox.isSelected()) {
						selectedCheckBox = checkBox;
						break;
					}
				}

				if (selectedCheckBox != null) {
					for (org.openqa.selenium.WebElement checkBox : checkBoxes) {
						if (!checkBox.isSelected()) {
							checkBox.click();
						}
					}
					if (!selectedCheckBox.isSelected()) {
						selectedCheckBox.click();
					}
				}
			} else if (elementName.endsWith("TX")) {
				System.out.println("Verifying if WebElement:" + elementName + " is exist - success");
				Assert.pass("Verifying if WebElement:" + elementName + " is exist - success");
				System.out.println("Verifying if WebElement:" + elementName + " is displayed - success");
				Assert.pass("Verifying if WebElement:" + elementName + " is displayed - success");
				System.out.println("Verifying if WebElement:" + elementName + " is enabled - success");
				Assert.pass("Verifying if WebElement:" + elementName + " is enabled - success");

				try {
					String textboxSelectedValue = FrameworkDriver.driver.findElement(By.logicalName(elementName)).getAttribute("value");
					FrameworkDriver.driver.findElement(By.logicalName(elementName)).sendKeys("");
					FrameworkDriver.driver.findElement(By.logicalName(elementName)).sendKeys(textboxSelectedValue);
					System.out.println("Verifying if WebElement:" + elementName + " can be typed with value - success");
					Assert.pass("Verifying if WebElement:" + elementName + " can be typed with value - success");
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("Verifying WebElement:" + elementName + " fail");
					Assert.fail("Verifying WebElement:" + elementName + " fail.  Error (" + e.getStackTrace() + ")");
				}

			} else {
				System.out.println("Verifying if WebElement:" + elementName + " is exist - success");
				Assert.pass("Verifying if WebElement:" + elementName + " is exist - success");
				System.out.println("Verifying if WebElement:" + elementName + " is displayed - success");
				Assert.pass("Verifying if WebElement:" + elementName + " is displayed - success");
				System.out.println("Verifying if WebElement:" + elementName + " is enabled - success");
				Assert.pass("Verifying if WebElement:" + elementName + " is enabled - success");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Verifying WebElement:" + elementName + " - fail");
			Assert.fail("Verifying WebElement:" + elementName + " fail.  Error (" + e.getStackTrace() + ")");
		}
	}

	public static void validate() {
		try {
			AWTScreenshot.captureScreenshot(Configuration.REPORT_PATH + "/screenshots/" + CurrentTest.tcId + "_" + CurrentTest.stepId + ".png");
		} catch (Exception e) {
		}

		if (CurrentTest.expected.equals(CurrentTest.actual)) {
			TestResult.addResultRecord(CurrentTest.tcId, CurrentTest.tcTitle, CurrentTest.tcStatus, CurrentTest.stepId, CurrentTest.expected, CurrentTest.actual, "Pass", CurrentTest.comments);
		} else {
			TestResult.addResultRecord(CurrentTest.tcId, CurrentTest.tcTitle, CurrentTest.tcStatus, CurrentTest.stepId, CurrentTest.expected, CurrentTest.actual, "Fail", CurrentTest.comments);
		}
	}

	public static void error(Exception e) {
		try {
			Reporting.reportResult("FAIL", "Error:"+e.getMessage()+" Stack Trace: "+e.getStackTrace().toString());
			ScriptLogger.log.info("FAIL " + "Error:"+e.getMessage()+" Stack Trace: "+e.getStackTrace().toString());
			System.out.println("FAIL " + "Error:"+e.getMessage()+" Stack Trace: ");
			e.printStackTrace();
			generateScreenshot(true);
		} catch (Error e1) {
			generateScreenshot(true);
		}
	}
	
	public static void error(Exception e, String message) {
		try {
			Reporting.reportResult("FAIL", message+".  Error:"+e.getMessage()+" Stack Trace: "+e.getStackTrace());
			ScriptLogger.log.info("FAIL " + message+".  Error:"+e.getMessage()+" Stack Trace: "+e.getStackTrace().toString());
			System.out.println("FAIL " + "Error:"+e.getMessage()+" Stack Trace: ");
			e.printStackTrace();
			generateScreenshot(true);
		} catch (Error e1) {
			generateScreenshot(true);
		}
	}
	
	private static void generateScreenshot(boolean result) {		
		if (result) {
			makeTestPass();
			if (Environment.get("capture_screenshot_on").toLowerCase().trim().contains("pass")) {				
				captureScreenshot();
			}
		} else {
			makeTestFail();			
			if (Environment.get("capture_screenshot_on").toLowerCase().trim().contains("fail")) {				
				captureScreenshot();
			}
		}
	}

	private static void makeTestPass() {
		TestResult.addResultRecord(CurrentTest.tcId, CurrentTest.tcTitle, CurrentTest.tcStatus, CurrentTest.stepId, CurrentTest.expected, CurrentTest.actual, "Pass", CurrentTest.comments);
	}

	private static void makeTestFail() {
		TestResult.addResultRecord(CurrentTest.tcId, CurrentTest.tcTitle, CurrentTest.tcStatus, CurrentTest.stepId, CurrentTest.expected, CurrentTest.actual, "Fail", CurrentTest.comments);
	}

	private static void captureScreenshot() {
		try {
			// AWTScreenshot.captureScreenshot(Environment.get("report_path")
			// + "/screenshots/" + Environment.get("TestScript") + "_"
			// + Environment.get("TestMethod") + ".png");
			// Reporting.report("DONE", "Capturing
			// screenshot:"+Environment.get("report_path")
			// + "/screenshots/" + Environment.get("TestScript") + "_"
			// + Environment.get("TestMethod") + ".png");
			AWTScreenshot.captureScreenshot(Environment.get("report_path") + "/screenshots/" + Environment.get("TestScript") + ".png");
			Reporting.report("DONE", "Capturing screenshot:" + Environment.get("report_path") + "/screenshots/" + Environment.get("TestScript") + ".png");
		} catch (Exception e) {
			Reporting.report("ERROR", "Failed to capture screenshot. Exception:" + e.getMessage());
		}
	}
}
