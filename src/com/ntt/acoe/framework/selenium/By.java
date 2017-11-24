package com.ntt.acoe.framework.selenium;

import java.util.List;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

/*
 * @author Santosh Hariprasad (NTT Badge Id: 244583,
 *         Santhosh.Hariprasad@NTTDATA.com)
 * @version 1.0
 * @since 2015-01-01
 */
public class By extends org.openqa.selenium.By {

	// public static org.openqa.selenium.By logicalName(String logicalName)
	// throws InvalidObjectIdentificationPropertyException{
	public static org.openqa.selenium.By logicalName(String logicalName) {
		String property = null;
		String value = "";
		org.openqa.selenium.By by = null;

		try {
			String[] tokens = UIObjects.p.getProperty(logicalName).split("\\=", -1);
			property = tokens[0];

			int i = UIObjects.p.getProperty(logicalName).indexOf('=');
			value = UIObjects.p.getProperty(logicalName).substring(i + 1);

			// property = UIObjects.p.getProperty(logicalName).split("\\=",
			// -1)[0];
			// value = UIObjects.p.getProperty(logicalName).split("\\=")[1];
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (property.equalsIgnoreCase("id")) {
			by = org.openqa.selenium.By.id(value);
		} else if (property.equalsIgnoreCase("name")) {
			by = org.openqa.selenium.By.name(value);
		} else if (property.equalsIgnoreCase("className")) {
			by = org.openqa.selenium.By.className(value);
		} else if (property.equalsIgnoreCase("cssSelector")) {
			by = org.openqa.selenium.By.cssSelector(value);
		} else if (property.equalsIgnoreCase("linkText")) {
			by = org.openqa.selenium.By.linkText(value);
		} else if (property.equalsIgnoreCase("partialLinkText")) {
			by = org.openqa.selenium.By.partialLinkText(value);
		} else if (property.equalsIgnoreCase("tagName")) {
			by = org.openqa.selenium.By.tagName(value);
		} else if (property.equalsIgnoreCase("xpath")) {
			by = org.openqa.selenium.By.xpath(value);
		} else {
			// throw new InvalidObjectIdentificationPropertyException("The
			// identification property:"+property
			// + " is invalid. (value:"+value+")given in logical
			// name:"+logicalName+". Valid values are id, name, className,
			// cssSelector, linkText, partialLinkText, tagName, xpath");
		}

		return by;
	}

	@Override
	public List<WebElement> findElements(SearchContext arg0) {
		return null;
	}
}
