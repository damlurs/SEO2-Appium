package com.ntt.acoe.framework.selenium;

import org.openqa.selenium.By;

/*
 * @author Santosh Hariprasad (NTT Badge Id: 244583,
 *         Santhosh.Hariprasad@NTTDATA.com)
 * @version 1.0
 * @since 2015-01-01
 */
public class FrameworkBy {
	public static By getBy(String property, String value) {
		if (property.equalsIgnoreCase("id")) {
			return By.id(value);
		} else if (property.equalsIgnoreCase("name")) {
			return By.name(value);
		} else if (property.equalsIgnoreCase("className")) {
			return By.className(value);
		} else if (property.equalsIgnoreCase("cssSelector")) {
			return By.cssSelector(value);
		} else if (property.equalsIgnoreCase("linkText")) {
			return By.linkText(value);
		} else if (property.equalsIgnoreCase("partialLinkText")) {
			return By.partialLinkText(value);
		} else if (property.equalsIgnoreCase("tagName")) {
			return By.tagName(value);
		} else if (property.equalsIgnoreCase("xpath")) {
			return By.xpath(value);
		} else {
			return null;
		}
	}
}
