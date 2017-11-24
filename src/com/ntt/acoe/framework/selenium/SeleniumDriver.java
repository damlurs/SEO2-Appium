package com.ntt.acoe.framework.selenium;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
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
public class SeleniumDriver implements WebDriver {
	public static int TIME_OUT = 60000;

	public SeleniumDriver() {
		new FirefoxDriver();
	}

	@Override
	public void close() {
		this.close();
	}

	@Override
	public WebElement findElement(By arg0) {
		try {
			return this.findElement(arg0);
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<WebElement> findElements(By arg0) {
		return this.findElements(arg0);
	}

	@Override
	public void get(String arg0) {
		this.get(arg0);
	}

	@Override
	public String getCurrentUrl() {
		return this.getCurrentUrl();
	}

	@Override
	public String getPageSource() {
		return this.getPageSource();
	}

	@Override
	public String getTitle() {
		return this.getTitle();
	}

	@Override
	public String getWindowHandle() {
		return this.getWindowHandle();
	}

	@Override
	public Set<String> getWindowHandles() {
		return this.getWindowHandles();
	}

	@Override
	public Options manage() {
		return this.manage();
	}

	@Override
	public Navigation navigate() {
		return this.navigate();
	}

	@Override
	public void quit() {
		this.quit();
	}

	@Override
	public TargetLocator switchTo() {
		return this.switchTo();
	}

}
