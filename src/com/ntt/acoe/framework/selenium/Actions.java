package com.ntt.acoe.framework.selenium;

import org.openqa.selenium.WebDriver;

/*
 * @author Santosh Hariprasad (NTT Badge Id: 244583,
 *         Santhosh.Hariprasad@NTTDATA.com)
 * @version 1.0
 * @since 2015-01-01
 */
public class Actions extends org.openqa.selenium.interactions.Actions {
	public org.openqa.selenium.interactions.Actions a = this;

	public Actions(WebDriver driver) {
		super((WebDriver) driver);
	}

	public org.openqa.selenium.interactions.Actions contextClick(NTTRemoteWebElement onElement) {
		org.openqa.selenium.WebElement e = onElement.webElement;
		return super.contextClick(e);
	}

	public org.openqa.selenium.interactions.Actions contextClick() {
		return super.contextClick(null);
	}
}
