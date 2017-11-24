package com.ntt.acoe.framework.selenium;

import java.util.List;

import org.openqa.selenium.WebElement;

import com.ntt.acoe.framework.selenium.report.Reporting;

/*
 * @author Santosh Hariprasad (NTT Badge Id: 244583,
 *         Santhosh.Hariprasad@NTTDATA.com)
 * @version 1.0
 * @since 2015-01-01
 */
public class Select extends org.openqa.selenium.support.ui.Select {
	public Select(WebElement element) {
		super(element);
		if (element == null) {
			select = null;
		} else {
			select = new org.openqa.selenium.support.ui.Select(element);
		}
	}

	org.openqa.selenium.support.ui.Select select = null;

	@Override
	public void deselectAll() {
		try {
			if (select == null) {
				Reporting.report("ERROR", "deselectAll - failed - Exception: select is null");
			} else {
				super.deselectAll();
				Reporting.report("DONE", "select:" + select.toString() + " - deselectAll - successful");
			}
		} catch (Exception e) {
			Reporting.report("ERROR", "deselectAll - failed - Exception:" + e.getMessage());
		}
	}

	@Override
	public void deselectByIndex(int index) {
		try {
			if (select == null) {
				Reporting.report("ERROR", "deselectByIndex - failed - Exception: Element is null");
			} else {
				super.deselectByIndex(index);
				Reporting.report("DONE", "Element:" + select.toString() + " - deselectByIndex - successful");
			}
		} catch (Exception e) {
			Reporting.report("ERROR", "deselectByIndex - failed - Exception:" + e.getMessage());
		}
	}

	@Override
	public void deselectByValue(java.lang.String value) {
		try {
			if (select == null) {
				Reporting.report("ERROR", "deselectByValue - failed - Exception: Element is null");
			} else {
				super.deselectByValue(value);
				Reporting.report("DONE", "Element:" + select.toString() + " - deselectByValue - successful");
			}
		} catch (Exception e) {
			Reporting.report("ERROR", "deselectByValue - failed - Exception:" + e.getMessage());
		}
	}

	@Override
	public void deselectByVisibleText(java.lang.String text) {
		try {
			if (select == null) {
				Reporting.report("ERROR", "deselectByVisibleText - failed - Exception: Element is null");
			} else {
				super.deselectByVisibleText(text);
				Reporting.report("DONE", "Element:" + select.toString() + " - deselectByVisibleText - successful");
			}
		} catch (Exception e) {
			Reporting.report("ERROR", "deselectByVisibleText - failed - Exception:" + e.getMessage());
		}
	}
	//
	// @Override
	// public String escapeQuotes(java.lang.String toEscape) {
	// try {
	// if (select == null) {
	// Reporting.report("ERROR", "escapeQuotes - failed - Exception: Element is
	// null");
	// return null;
	// } else {
	// Reporting.report("DONE", "Element:" + select.toString() + " -
	// escapeQuotes - successful");
	// return super.escapeQuotes(toEscape);
	// }
	// } catch (Exception e) {
	// Reporting.report("ERROR", "escapeQuotes - failed - Exception:" +
	// e.getMessage());
	// return null;
	// }
	//
	// }

	@Override
	public List<WebElement> getAllSelectedOptions() {
		try {
			if (select == null) {
				Reporting.report("ERROR", "getAllSelectedOptions - failed - Exception: Element is null");
				return null;
			} else {
				Reporting.report("DONE", "Element:" + select.toString() + " - getAllSelectedOptions - successful");
				return super.getAllSelectedOptions();
			}
		} catch (Exception e) {
			Reporting.report("ERROR", "getAllSelectedOptions - failed - Exception:" + e.getMessage());
			return null;
		}
	}

	@Override
	public WebElement getFirstSelectedOption() {
		try {
			if (select == null) {
				Reporting.report("ERROR", "getFirstSelectedOption - failed - Exception: Element is null");
				return null;
			} else {
				Reporting.report("DONE", "Element:" + select.toString() + " - getFirstSelectedOption - successful");
				return super.getFirstSelectedOption();
			}
		} catch (Exception e) {
			Reporting.report("ERROR", "getFirstSelectedOption - failed - Exception:" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<WebElement> getOptions() {
		try {
			if (select == null) {
				Reporting.report("ERROR", "getOptions - failed - Exception: Element is null");
				return null;
			} else {
				Reporting.report("DONE", "Element:" + select.toString() + " - getOptions - successful");
				return super.getOptions();
			}
		} catch (Exception e) {
			Reporting.report("ERROR", "getOptions - failed - Exception:" + e.getMessage());
			return null;
		}
	}

	@Override
	public boolean isMultiple() {
		try {
			if (select == null) {
				Reporting.report("ERROR", "isMultiple - failed - Exception: Element is null");
				return false;
			} else {
				Reporting.report("DONE", "Element:" + select.toString() + " - isMultiple - successful");
				return super.isMultiple();
			}
		} catch (Exception e) {
			Reporting.report("ERROR", "isMultiple - failed - Exception:" + e.getMessage());
			return false;
		}
	}

	@Override
	public void selectByIndex(int index) {
		try {
			if (select == null) {
				Reporting.report("ERROR", "selectByIndex - failed - Exception: Element is null");
			} else {
				super.selectByIndex(index);
				Reporting.report("DONE", "Element:" + select.toString() + " - selectByIndex - successful");
			}
		} catch (Exception e) {
			Reporting.report("ERROR", "selectByIndex - failed - Exception:" + e.getMessage());
		}
	}

	@Override
	public void selectByValue(java.lang.String value) {
		try {
			if (select == null) {
				Reporting.report("ERROR", "selectByValue - failed - Exception: Element is null");
			} else {
				super.selectByValue(value);
				Reporting.report("DONE", "Element:" + select.toString() + " - selectByValue - successful");
			}
		} catch (Exception e) {
			Reporting.report("ERROR", "selectByValue - failed - Exception:" + e.getMessage());
		}
	}

	@Override
	public void selectByVisibleText(java.lang.String text) {
		try {
			if (select == null) {
				Reporting.report("ERROR", "selectByVisibleText - failed - Exception: Element is null");
			} else {
				super.selectByVisibleText(text);
				Reporting.report("DONE", "Element:" + select.toString() + " - selectByVisibleText - successful");
			}
		} catch (Exception e) {
			Reporting.report("ERROR", "selectByVisibleText - failed - Exception:" + e.getMessage());
		}
	}
}
