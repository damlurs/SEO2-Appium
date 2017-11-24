package com.ntt.acoe.framework.selenium;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.FileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;

import com.ntt.acoe.framework.selenium.report.Reporting;

/*
 * @author Santosh Hariprasad (NTT Badge Id: 244583,
 *         Santhosh.Hariprasad@NTTDATA.com)
 * @version 1.0
 * @since 2015-01-01
 */
public class NTTRemoteWebElement extends org.openqa.selenium.remote.RemoteWebElement {
	protected FileDetector fileDetector;
	protected java.lang.String id;
	protected RemoteWebDriver parent;
	public WebElement webElement = null;

	public NTTRemoteWebElement() {
	}

	public NTTRemoteWebElement(By by) {
		super();
		if (by == null) {
			webElement = null;
			Reporting.report("ERROR", "Loading WebElement with By construction - failed - Exception: By is null");
		} else {
			try {
				webElement = NTTDriver.driver.findElement(by);
				// Reporting.report("DONE", "Loading WebElement:" +
				// webElement.toString() + " - with By construction -
				// successful");
				// fileDetector = webElement.get
				id = ((RemoteWebElement) webElement).getId();
				// this=webElement.getLocation();
			} catch (Exception e) {
				webElement = null;
				Reporting.report("ERROR", "Loading WebElement:null - with By construction - failed - Exception:" + e.getMessage());
			}
		}

	}

	@Override
	public void clear() {
		try {
			if (webElement == null) {
				Reporting.report("ERROR", "clear - failed - Exception: Element is null");
			} else {
				webElement.clear();
				Reporting.report("DONE", "Element:" + webElement.toString() + " - clear - successful");
			}
		} catch (Exception e) {
			Reporting.report("ERROR", "clear - failed - Exception:" + e.getMessage());
		}
	}

	@Override
	public void click() {
		try {
			if (webElement == null) {
				Reporting.report("ERROR", "click - failed - Exception: Element is null");
			} else {
				webElement.click();
				Reporting.report("DONE", "Element:" + webElement.toString() + " - click - successful");
			}
		} catch (Exception e) {
			Reporting.report("ERROR", "click - failed - Exception:" + e.getMessage());
		}
	}

	@Override
	public WebElement findElement(By arg0) {
		try {
			if (webElement == null) {
				Reporting.report("ERROR", "findElement - failed - Exception: Element is null");
			} else {
				Reporting.report("DONE", "Element:" + webElement.toString() + " - findElement - successful");
				return webElement.findElement(arg0);
			}
		} catch (Exception e) {
			Reporting.report("ERROR", "Element:" + webElement.toString() + " - findElement - failed - Exception:" + e.getMessage());
		}
		return this;
	}

	@Override
	public List<WebElement> findElements(By arg0) {
		try {
			if (webElement == null) {
				Reporting.report("ERROR", "findElements - failed - Exception: Element is null");
			} else {
				Reporting.report("DONE", "Element:" + webElement.toString() + " - findElements - successful");
				return webElement.findElements(arg0);
			}
		} catch (Exception e) {
			Reporting.report("ERROR", "Element:" + webElement.toString() + " - findElements - failed - Exception:" + e.getMessage());
		}
		return null;
	}

	@Override
	public String getAttribute(String arg0) {
		try {
			if (webElement == null) {
				Reporting.report("ERROR", "getAttribute - failed - Exception: Element is null");
			} else {
				Reporting.report("DONE", "Element:" + webElement.toString() + " - getAttribute for " + arg0 + " - successful");
				return webElement.getAttribute(arg0);
			}
		} catch (Exception e) {
			Reporting.report("ERROR", "Element:" + webElement.toString() + " - getAttribute for " + arg0 + " - failed - Exception:" + e.getMessage());
		}
		return arg0;
	}

	@Override
	public String getCssValue(String arg0) {
		try {
			if (webElement == null) {
				Reporting.report("ERROR", "getCssValue - failed - Exception: Element is null");
			} else {
				Reporting.report("DONE", "Element:" + webElement.toString() + " - getCssValue for " + arg0 + " - successful");
				return webElement.getCssValue(arg0);
			}
		} catch (Exception e) {
			Reporting.report("ERROR", "Element:" + webElement.toString() + " - getCssValue for " + arg0 + " - failed - Exception:" + e.getMessage());
		}
		return arg0;
	}

	@Override
	public Point getLocation() {
		try {
			if (webElement == null) {
				Reporting.report("ERROR", "getLocation - failed - Exception: Element is null");
			} else {
				Reporting.report("DONE", "Element:" + webElement.toString() + " - getLocation - successful");
				return webElement.getLocation();
			}
		} catch (Exception e) {
			Reporting.report("ERROR", "Element:" + webElement.toString() + " - getLocation - failed - Exception:" + e.getMessage());
		}
		return null;
	}

	@Override
	public Dimension getSize() {
		try {
			if (webElement == null) {
				Reporting.report("ERROR", "getSize - failed - Exception: Element is null");
			} else {
				Reporting.report("DONE", "Element:" + webElement.toString() + " - getSize - successful");
				return webElement.getSize();
			}
		} catch (Exception e) {
			Reporting.report("ERROR", "Element:" + webElement.toString() + " - getSize - failed - Exception:" + e.getMessage());
		}
		return null;
	}

	@Override
	public String getTagName() {
		try {
			if (webElement == null) {
				Reporting.report("ERROR", "getTagName - failed - Exception: Element is null");
			} else {
				Reporting.report("DONE", "Element:" + webElement.toString() + " - getTagName - successful");
				return webElement.getTagName();
			}
		} catch (Exception e) {
			Reporting.report("ERROR", "Element:" + webElement.toString() + " - getTagName - failed - Exception:" + e.getMessage());
		}
		return null;
	}

	@Override
	public String getText() {
		try {
			if (webElement == null) {
				Reporting.report("ERROR", "getText - failed - Exception: Element is null");
			} else {
				Reporting.report("DONE", "Element:" + webElement.toString() + " - getText - successful");
				return webElement.getText();
			}
		} catch (Exception e) {
			Reporting.report("ERROR", "Element:" + webElement.toString() + " - getText - failed - Exception:" + e.getMessage());
		}
		return null;
	}

	@Override
	public boolean isDisplayed() {
		try {
			if (webElement == null) {
				Reporting.report("ERROR", "isDisplayed - failed - Exception: Element is null");
			} else {
				Reporting.report("DONE", "Element:" + webElement.toString() + " - isDisplayed - successful");
				return webElement.isDisplayed();
			}
		} catch (Exception e) {
			Reporting.report("ERROR", "Element:" + webElement.toString() + " - isDisplayed - failed - Exception:" + e.getMessage());
		}
		return false;
	}

	@Override
	public boolean isEnabled() {
		try {
			if (webElement == null) {
				Reporting.report("ERROR", "isEnabled - failed - Exception: Element is null");
			} else {
				Reporting.report("DONE", "Element:" + webElement.toString() + " - isEnabled - successful");
				return webElement.isEnabled();
			}
		} catch (Exception e) {
			Reporting.report("ERROR", "Element:" + webElement.toString() + " - isEnabled - failed - Exception:" + e.getMessage());
		}
		return false;
	}

	@Override
	public boolean isSelected() {
		try {
			if (webElement == null) {
				Reporting.report("ERROR", "isSelected - failed - Exception: Element is null");
			} else {
				Reporting.report("DONE", "Element:" + webElement.toString() + " - isSelected - successful");
				return webElement.isSelected();
			}
		} catch (Exception e) {
			Reporting.report("ERROR", "Element:" + webElement.toString() + " - isSelected - failed - Exception:" + e.getMessage());
		}
		return false;
	}

	@Override
	public void sendKeys(CharSequence... arg0) {
		try {
			if (webElement == null) {
				Reporting.report("ERROR", "sendKeys - failed - Exception: Element is null");
			} else {
				webElement.sendKeys(arg0);
				Reporting.report("DONE", "Element:" + webElement.toString() + " - sendKeys with data " + String.valueOf(arg0) + " - successful");
			}
		} catch (Exception e) {
			Reporting.report("ERROR", "Element:" + webElement.toString() + " - sendKeys with data " + String.valueOf(arg0) + " - failed - Exception:" + e.getMessage());
		}
	}

	@Override
	public void submit() {
		try {
			if (webElement == null) {
				Reporting.report("ERROR", "submit - failed - Exception: Element is null");
			} else {
				webElement.submit();
				Reporting.report("DONE", "Element:" + webElement.toString() + " - submit - successful");
			}
		} catch (Exception e) {
			Reporting.report("ERROR", "Element:" + webElement.toString() + " - submit - failed - Exception:" + e.getMessage());
		}
	}

}
