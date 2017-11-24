package com.ntt.acoe.framework.selenium;

import java.lang.reflect.Method;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

/*
 * @author Santosh Hariprasad (NTT Badge Id: 244583,
 *         Santhosh.Hariprasad@NTTDATA.com)
 * @version 1.0
 * @since 2015-01-01
 */
public class WebElement {
	private static WebDriver driver;

	public static Object execute(String command, String testData, By by) {
		Class<?> params0[] = {};
		@SuppressWarnings("rawtypes")
		Class[] params1 = new Class[1];
		params1[0] = String.class;
		Object returnVal = null;

		try {

			if (testData != null && testData.trim() != "") {
				Method method1 = by.getClass().getMethod(command, params1);
				returnVal = method1.invoke(driver, testData);

			} else {
				Method method1 = by.getClass().getMethod(command, params0);
				returnVal = method1.invoke(driver, testData);
			}
		} catch (Exception e) {
		}
		return returnVal;
	}

	public static Object execute(String command, String testData, org.openqa.selenium.WebElement webElement) {
		Object returnVal = webElement.getText();

		if (command.equalsIgnoreCase("sendKeys")) {
			webElement.sendKeys(testData);

		} else if (command.equalsIgnoreCase("click")) {
			webElement.click();

		} else if (command.equalsIgnoreCase("selectByVisibleText")) {
			Select s = new Select(webElement);

			try {
				s.selectByVisibleText(testData);
			} catch (Exception e1) {
				try {
					s.selectByValue(testData);
				} catch (Exception e) {
					try {
						s.selectByIndex(Integer.valueOf(testData));
					} catch (NumberFormatException e2) {
					}
				}
			}

		}
		return returnVal;
	}

	public static org.openqa.selenium.WebElement getWebElement(By by) {
		return driver.findElement(by);
	}
}
