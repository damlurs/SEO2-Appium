package com.ntt.acoe.framework.selenium;

import java.util.List;

import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.support.ui.Select;

import com.ntt.acoe.framework.selenium.testdata.DataTable;
import com.ntt.acoe.framework.selenium.verify.Assert;

/*
 * @author Santosh Hariprasad (NTT Badge Id: 244583,
 *         Santhosh.Hariprasad@NTTDATA.com)
 * @version 1.0
 * @since 2015-01-01
 */
public class Forms {

	public static void fill(String sScreenName, String[] aFields, DataTable oTestData) {
		Assert.pass("Start --> SeO2.Forms.fill");
		System.out.println("Start --> SeO2.Forms.fill");

		if (sScreenName.endsWith(".")) {
			sScreenName = sScreenName.substring(0, sScreenName.length() - 1);
		}

		for (int i = 0; i < aFields.length; i++) {
			System.out.println("Filling the form, Fieldname:" + sScreenName + "." + aFields[i]);
			try {
				if (!oTestData.getValue(aFields[i]).equalsIgnoreCase("")) {
					if (aFields[i].equalsIgnoreCase("") || aFields[i] == null) {
					} else if (By.logicalName(sScreenName + "." + aFields[i]) == null) {
						System.out.println("Filling the form, Fieldname:" + sScreenName + "." + aFields[i] + " does not exist");
						Assert.fail("Filling the form, Fieldname:" + sScreenName + "." + aFields[i] + " does not exist");
					} else if (!FrameworkDriver.driver.findElement(By.logicalName(sScreenName + "." + aFields[i])).isDisplayed()) {
						System.out.println("Filling the form, Fieldname:" + sScreenName + "." + aFields[i] + " is not displayed");
						Assert.fail("Filling the form, Fieldname:" + sScreenName + "." + aFields[i] + " is not displayed");
					} else if (!FrameworkDriver.driver.findElement(By.logicalName(sScreenName + "." + aFields[i])).isEnabled()) {
						System.out.println("Filling the form, Fieldname:" + sScreenName + "." + aFields[i] + " is not enabled");
						Assert.fail("Filling the form, Fieldname:" + sScreenName + "." + aFields[i] + " is not enabled");
					} else if (aFields[i].endsWith("WL")) {
						Select s = new Select(FrameworkDriver.driver.findElement(By.logicalName(sScreenName + "." + aFields[i])));
						try {
							s.selectByVisibleText(oTestData.getValue(aFields[i]));
						} catch (Exception e) {
							try {
								s.selectByValue(oTestData.getValue(aFields[i]));
							} catch (Exception e1) {
								Assert.error(e1, "Filling the form, Not selected for Fieldname:" + sScreenName + "." + aFields[i] + "(" + oTestData.getValue(aFields[i]) + ") - failed");
							}
						}
						System.out.println("Filling the form, selected for Fieldname:" + sScreenName + "." + aFields[i] + "(" + oTestData.getValue(aFields[i]) + ") - success");
						Assert.pass("Filling the form, selected for Fieldname:" + sScreenName + "." + aFields[i] + "(" + oTestData.getValue(aFields[i]) + ") - success");
					} else if (aFields[i].endsWith("RB")) {
						List<org.openqa.selenium.WebElement> radioButtons = FrameworkDriver.driver.findElements(By.logicalName(sScreenName + "." + aFields[i]));
						for (org.openqa.selenium.WebElement radioButton : radioButtons) {
							String sRadioButtonValue = radioButton.getAttribute("value");
							if (sRadioButtonValue.equalsIgnoreCase(oTestData.getValue(aFields[i]))) {
								if (!radioButton.isSelected()) {
									radioButton.click();
									System.out
											.println("Filling the form, Radio button selected for Fieldname:" + sScreenName + "." + aFields[i] + "(" + oTestData.getValue(aFields[i]) + ") - success");
									Assert.pass("Filling the form, Radio button selected for Fieldname:" + sScreenName + "." + aFields[i] + "(" + oTestData.getValue(aFields[i]) + ") - success");
									break;
								}
							}
						}
					} else if (aFields[i].endsWith("CB")) {
						List<org.openqa.selenium.WebElement> checkBoxes = FrameworkDriver.driver.findElements(By.logicalName(sScreenName + "." + aFields[i]));
						for (org.openqa.selenium.WebElement checkBox : checkBoxes) {
							if (oTestData.getValue(aFields[i]).equals(checkBox.getText())) {
								checkBox.click();
								System.out.println("Filling the form, Check box selected for Fieldname:" + sScreenName + "." + aFields[i] + "(" + oTestData.getValue(aFields[i]) + ") - success");
								Assert.pass("Filling the form, Check box selected for Fieldname:" + sScreenName + "." + aFields[i] + "(" + oTestData.getValue(aFields[i]) + ") - success");
								break;
							}
						}
					} else if (aFields[i].endsWith("MSG")) {
						String actualMessage = FrameworkDriver.driver.findElement(By.logicalName(sScreenName + "." + aFields[i])).getText();
						String expectedMessage = oTestData.getValue(aFields[i]);
						System.out.println("Filling the form, Comparing Fieldname:" + aFields[i] + "(Expected:" + expectedMessage + " , Actual: " + actualMessage + ") - success");
						Assert.assertEquals(actualMessage, expectedMessage, "Filling the form, Comparing Fieldname:" + aFields[i]);
					} else {
						if (aFields[i].endsWith("BN")) {
							if (By.logicalName(sScreenName + "." + aFields[i]) != null) {
								FrameworkDriver.driver.findElement(By.logicalName(sScreenName + "." + aFields[i])).click();
								System.out.println("Filling the form, Click on Fieldname:" + aFields[i] + "(" + oTestData.getValue(aFields[i]) + ") - success");
								Assert.pass("Filling the form, Click on Fieldname:" + aFields[i] + "(" + oTestData.getValue(aFields[i]) + ") - success");
							} else {
								System.out.println("Filling the form, Fieldname:" + sScreenName + "." + aFields[i] + " does not exist");
								Assert.fail("Filling the form, Fieldname:" + sScreenName + "." + aFields[i] + " does not exist");
							}
						} else {
							try {
								FrameworkDriver.driver.findElement(By.logicalName(sScreenName + "." + aFields[i])).clear();
								FrameworkDriver.driver.findElement(By.logicalName(sScreenName + "." + aFields[i])).sendKeys(oTestData.getValue(aFields[i]));
								System.out.println("Filling the form, Fieldname:" + aFields[i] + "(" + oTestData.getValue(aFields[i]) + ") - success");
								Assert.pass("Filling the form, Fieldname:" + aFields[i] + "(" + oTestData.getValue(aFields[i]) + ") - success");
							}catch(InvalidElementStateException inv){
								inv.printStackTrace();
								System.out.println("Filling the form, Fieldname:" + sScreenName + "." + aFields[i] + " - pass");
								Assert.pass("Filling the form, Fieldname:" + sScreenName + "." + aFields[i] + " - pass");
							}
							catch (Exception e) {
								e.printStackTrace();
								System.out.println("Filling the form, Fieldname:" + sScreenName + "." + aFields[i] + " - fail");
								Assert.fail("Filling the form, Fieldname:" + sScreenName + "." + aFields[i] + " fail.  Error (" + e.getStackTrace().toString() + ")");
							}
						}
					}
				} else {
					if (aFields[i].endsWith("BN")) {
						if (By.logicalName(sScreenName + "." + aFields[i]) != null) {
							FrameworkDriver.driver.findElement(By.logicalName(sScreenName + "." + aFields[i])).click();
							System.out.println("Filling the form, Click on Fieldname:" + aFields[i] + "(" + oTestData.getValue(aFields[i]) + ") - success");
							Assert.pass("Filling the form, Click on Fieldname:" + aFields[i] + "(" + oTestData.getValue(aFields[i]) + ") - success");
						} else {
							System.out.println("Filling the form, Fieldname:" + sScreenName + "." + aFields[i] + " does not exist");
							Assert.fail("Filling the form, Fieldname:" + sScreenName + "." + aFields[i] + " does not exist");
						}
					}
				}
			} catch (Exception e) {
				if (aFields[i].endsWith("BN") || aFields[i].endsWith("LB") || aFields[i].endsWith("WE")) {
					if (By.logicalName(sScreenName + "." + aFields[i]) != null) {
						FrameworkDriver.driver.findElement(By.logicalName(sScreenName + "." + aFields[i])).click();
						System.out.println("Filling the form, Click on Fieldname:" + aFields[i] + "(" + oTestData.getValue(aFields[i]) + ") - success");
						Assert.pass("Filling the form, Click on Fieldname:" + aFields[i] + "(" + oTestData.getValue(aFields[i]) + ") - success");
					} else {
						System.out.println("Filling the form, Fieldname:" + sScreenName + "." + aFields[i] + " does not exist");
						Assert.fail("Filling the form, Fieldname:" + sScreenName + "." + aFields[i] + " does not exist");
					}
				} else {
					e.printStackTrace();
					System.out.println("Filling the form, Fieldname:" + sScreenName + "." + aFields[i] + " - fail");
					Assert.fail("Filling the form, Fieldname:" + sScreenName + "." + aFields[i] + " fail.  Error (" + e.getMessage().toString() + ")");
				}
			}
		}
		Assert.pass("End --> SeO2.Forms.fill");
		System.out.println("End --> SeO2.Forms.fill");
	}

	public static void verifyFields(String sScreenName, String[] aFields, DataTable oTestData) {
		Assert.pass("Start --> SeO2.Forms.verifyFields with data from excel");
		System.out.println("Start --> SeO2.Forms.verifyFields with data from excel");
		boolean isReadonlyField = false;

		if (sScreenName.endsWith(".")) {
			sScreenName = sScreenName.substring(0, sScreenName.length() - 1);
		}

		for (int i = 0; i < aFields.length; i++) {
			try {
				try {
					Assert.pass("SeO2.Forms.verifyFields with data: " + aFields[i] + "(" + oTestData.getValue(aFields[i]) + ")");
					System.out.println("Forms.verifyFields with data: " + aFields[i] + "(" + oTestData.getValue(aFields[i]) + ")");
				} catch (Exception e) {
					e.printStackTrace();
				}
				try{
					if(aFields[i].trim().endsWith("-r")||aFields[i].trim().endsWith("-R")){
						aFields[i] = aFields[i].substring(0, aFields[i].length()-2);
						isReadonlyField = true;
					}
				}catch(Exception e){
				}				
				if (!oTestData.getValue(aFields[i]).equalsIgnoreCase("")) {
					if (aFields[i].equalsIgnoreCase("") || aFields[i] == null) {
					} else if (By.logicalName(sScreenName + "." + aFields[i]) == null) {
						System.out.println("Verifying Fields, Fieldname:" + sScreenName + "." + aFields[i] + " does not exist");
						Assert.fail("Verifying Fields, Fieldname:" + sScreenName + "." + aFields[i] + " does not exist");
					} else if (!FrameworkDriver.driver.findElement(By.logicalName(sScreenName + "." + aFields[i])).isDisplayed()) {
						System.out.println("Verifying Fields, Fieldname:" + sScreenName + "." + aFields[i] + " is not displayed");
						Assert.fail("Verifying Fields, Fieldname:" + sScreenName + "." + aFields[i] + " is not displayed");
					} else if (!FrameworkDriver.driver.findElement(By.logicalName(sScreenName + "." + aFields[i])).isEnabled()) {
						System.out.println("Verifying Fields, Fieldname:" + sScreenName + "." + aFields[i] + " is not enabled");
						Assert.fail("Verifying Fields, Fieldname:" + sScreenName + "." + aFields[i] + " is not enabled");
					} else if (aFields[i].endsWith("WL")) {
						Select s = new Select(FrameworkDriver.driver.findElement(By.logicalName(sScreenName + "." + aFields[i])));
						String selectedOption = s.getFirstSelectedOption().getText();
						s.selectByVisibleText(oTestData.getValue(aFields[i]));
						System.out.println("Verifying Fields, Reading value for Fieldname:" + sScreenName + "." + aFields[i] + "(" + selectedOption + ") - success");
						// Assert.pass("Verifying Fields, selected value for
						// Fieldname:" + sScreenName + "." + aFields[i] + "(" +
						// oTestData.getValue(aFields[i]) + ") - success");
						Assert.assertEquals(selectedOption, oTestData.getValue(aFields[i]), "Verifying Fields, Reading value for Fieldname:" + sScreenName + "." + aFields[i]);
					} else if (aFields[i].endsWith("RB")) {
						List<org.openqa.selenium.WebElement> radioButtons = FrameworkDriver.driver.findElements(By.logicalName(sScreenName + "." + aFields[i]));
						for (org.openqa.selenium.WebElement radioButton : radioButtons) {
							String sRadioButtonValue = radioButton.getAttribute("value");
							if (radioButton.isSelected()) {
								Assert.assertEquals(sRadioButtonValue, oTestData.getValue(aFields[i]), "Verifying Fields, Reading value for Fieldname:" + sScreenName + "." + aFields[i]);
								break;
							}
						}
					}
				} else if (aFields[i].endsWith("CB")) {
					List<org.openqa.selenium.WebElement> checkBoxes = FrameworkDriver.driver.findElements(By.logicalName(sScreenName + "." + aFields[i]));
					for (org.openqa.selenium.WebElement checkBox : checkBoxes) {
						String sCheckBoxValue = checkBox.getAttribute("value");
						if (checkBox.isSelected()) {
							Assert.assertEquals(sCheckBoxValue, oTestData.getValue(aFields[i]), "Verifying Fields, Reading value for Fieldname:" + sScreenName + "." + aFields[i]);
							break;
						}
					}
				} else if (aFields[i].endsWith("BN")) {
					if (By.logicalName(sScreenName + "." + aFields[i]) != null) {
						FrameworkDriver.driver.findElement(By.logicalName(sScreenName + "." + aFields[i])).click();
						System.out.println("Verifying Fields, Click on Fieldname:" + aFields[i] + "(" + oTestData.getValue(aFields[i]) + ") - success");
						Assert.pass("Verifying Fields, Click on Fieldname:" + aFields[i] + "(" + oTestData.getValue(aFields[i]) + ") - success");
					} else {
						System.out.println("Verifying Fields, Fieldname:" + sScreenName + "." + aFields[i] + " does not exist");
						Assert.fail("Verifying Fields, Fieldname:" + sScreenName + "." + aFields[i] + " does not exist");
					}
				} else if (aFields[i].endsWith("MSG")) {
					String actualMessage = FrameworkDriver.driver.findElement(By.logicalName(sScreenName + "." + aFields[i])).getText();
					String expectedMessage = oTestData.getValue(aFields[i]);
					System.out.println("Verifying Fields, Comparing Fieldname:" + aFields[i] + "(Expected:" + expectedMessage + " , Actual: " + actualMessage + ") - success");
					Assert.assertEquals(actualMessage, expectedMessage, "Verifying Fields, Comparing Fieldname:" + aFields[i]);
				} else if (aFields[i].endsWith("TX")) {
					try {
						String actualValue = FrameworkDriver.driver.findElement(By.logicalName(sScreenName + "." + aFields[i])).getText();
						String expectedValue = oTestData.getValue(aFields[i]);
						FrameworkDriver.driver.findElement(By.logicalName(sScreenName + "." + aFields[i])).clear();
						FrameworkDriver.driver.findElement(By.logicalName(sScreenName + "." + aFields[i])).sendKeys(actualValue);
						System.out.println("Verifying Fields, Fieldname:" + aFields[i] + "(" + oTestData.getValue(aFields[i]) + ") - success");
						Assert.pass("Verifying Fields, Fieldname:" + aFields[i] + "(" + oTestData.getValue(aFields[i]) + ") - success");
						Assert.assertEquals(actualValue, expectedValue, "Verifying Fields, Fieldname:" + aFields[i]);
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("Verifying Fields, Fieldname:" + sScreenName + "." + aFields[i] + " - fail");
						Assert.fail("Verifying Fields, Fieldname:" + sScreenName + "." + aFields[i] + " fail.  Error (" + e.getStackTrace().toString() + ")");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Verifying Fields, Fieldname:" + sScreenName + "." + aFields[i] + " - fail");
				Assert.fail("Verifying Fields, Fieldname:" + sScreenName + "." + aFields[i] + " fail.  Error (" + e.getStackTrace().toString() + ")");
			}
		}
		Assert.pass("End --> SeO2.Forms.verifyFields with data from excel");
		System.out.println("End --> SeO2.Forms.verifyFields with data from excel");
	}

	public static void verifyFields(String sScreenName, String[] aFields, String[] aFieldValues) {
		Assert.pass("Start --> SeO2.Forms.verifyFields with data from array");
		System.out.println("Start --> SeO2.Forms.verifyFields with data from array");
		boolean isReadonlyField = false;

		if (sScreenName.endsWith(".")) {
			sScreenName = sScreenName.substring(0, sScreenName.length() - 1);
		}

		for (int i = 0; i < aFields.length; i++) {
			try {
				try {
					Assert.pass("SeO2.Forms.verifyFields with data: " + aFields[i] + "(" + aFieldValues[i] + ")");
					System.out.println("Forms.verifyFields with data: " + aFields[i] + "(" + aFieldValues[i] + ")");
				} catch (Exception e) {
					e.printStackTrace();
				}
				try{
					if(aFields[i].trim().endsWith("-r")||aFields[i].trim().endsWith("-R")){
						aFields[i] = aFields[i].substring(0, aFields[i].length()-2);
						isReadonlyField = true;
					}
				}catch(Exception e){
				}
				if (!aFieldValues[i].equalsIgnoreCase("")) {
					if (aFields[i].equalsIgnoreCase("") || aFields[i] == null) {
					} else if (By.logicalName(sScreenName + "." + aFields[i]) == null) {
						System.out.println("Verifying Fields, Fieldname:" + sScreenName + "." + aFields[i] + " does not exist");
						Assert.fail("Verifying Fields, Fieldname:" + sScreenName + "." + aFields[i] + " does not exist");
					} else if (!FrameworkDriver.driver.findElement(By.logicalName(sScreenName + "." + aFields[i])).isDisplayed()) {
						System.out.println("Verifying Fields, Fieldname:" + sScreenName + "." + aFields[i] + " is not displayed");
						Assert.fail("Verifying Fields, Fieldname:" + sScreenName + "." + aFields[i] + " is not displayed");
					} else if (!FrameworkDriver.driver.findElement(By.logicalName(sScreenName + "." + aFields[i])).isEnabled()) {
						System.out.println("Verifying Fields, Fieldname:" + sScreenName + "." + aFields[i] + " is not enabled");
						Assert.fail("Verifying Fields, Fieldname:" + sScreenName + "." + aFields[i] + " is not enabled");
					} else if (aFields[i].endsWith("WL")) {
						Select s = new Select(FrameworkDriver.driver.findElement(By.logicalName(sScreenName + "." + aFields[i])));
						String selectedOption = s.getFirstSelectedOption().getText();
						s.selectByVisibleText(aFieldValues[i]);
						System.out.println("Verifying Fields, Reading value for Fieldname:" + sScreenName + "." + aFields[i] + "(" + selectedOption + ") - success");
						// Assert.pass("Verifying Fields, selected value for
						// Fieldname:" + sScreenName + "." + aFields[i] + "(" +
						// oTestData.getValue(aFields[i]) + ") - success");
						Assert.assertEquals(selectedOption, aFieldValues[i], "Verifying Fields, Reading value for Fieldname:" + sScreenName + "." + aFields[i]);
					} else if (aFields[i].endsWith("RB")) {
						List<org.openqa.selenium.WebElement> radioButtons = FrameworkDriver.driver.findElements(By.logicalName(sScreenName + "." + aFields[i]));
						for (org.openqa.selenium.WebElement radioButton : radioButtons) {
							String sRadioButtonValue = radioButton.getAttribute("value");
							if (radioButton.isSelected()) {
								Assert.assertEquals(sRadioButtonValue, aFieldValues[i], "Verifying Fields, Reading value for Fieldname:" + sScreenName + "." + aFields[i]);
								break;
							}
						}
					}
				} else if (aFields[i].endsWith("CB")) {
					List<org.openqa.selenium.WebElement> checkBoxes = FrameworkDriver.driver.findElements(By.logicalName(sScreenName + "." + aFields[i]));
					for (org.openqa.selenium.WebElement checkBox : checkBoxes) {
						String sCheckBoxValue = checkBox.getAttribute("value");
						if (checkBox.isSelected()) {
							Assert.assertEquals(sCheckBoxValue, aFieldValues[i], "Verifying Fields, Reading value for Fieldname:" + sScreenName + "." + aFields[i]);
							break;
						}
					}
				} else if (aFields[i].endsWith("BN")) {
					if (By.logicalName(sScreenName + "." + aFields[i]) != null) {
						String actualMessage = "";
						try{
							actualMessage = FrameworkDriver.driver.findElement(By.logicalName(sScreenName + "." + aFields[i])).getAttribute("value");
						}catch(Exception e){
							actualMessage = FrameworkDriver.driver.findElement(By.logicalName(sScreenName + "." + aFields[i])).getText();
						}
						String expectedMessage = aFieldValues[i];
						System.out.println("Verifying Fields, Comparing Fieldname:" + aFields[i] + "(Expected:" + expectedMessage + " , Actual: " + actualMessage + ") - success");
						Assert.assertEquals(actualMessage, expectedMessage, "Verifying Fields, Comparing Fieldname:" + aFields[i]);
//						FrameworkDriver.driver.findElement(By.logicalName(sScreenName + "." + aFields[i])).click();
//						System.out.println("Verifying Fields, Click on Fieldname:" + aFields[i] + "(" + aFieldValues[i] + ") - success");
//						Assert.pass("Verifying Fields, Click on Fieldname:" + aFields[i] + "(" + aFieldValues[i] + ") - success");
					} else {
						System.out.println("Verifying Fields, Fieldname:" + sScreenName + "." + aFields[i] + " does not exist");
						Assert.fail("Verifying Fields, Fieldname:" + sScreenName + "." + aFields[i] + " does not exist");
					}
				} else if (aFields[i].endsWith("MSG")) {
					String actualMessage = FrameworkDriver.driver.findElement(By.logicalName(sScreenName + "." + aFields[i])).getText();
					String expectedMessage = aFieldValues[i];
					System.out.println("Verifying Fields, Comparing Fieldname:" + aFields[i] + "(Expected:" + expectedMessage + " , Actual: " + actualMessage + ") - success");
					Assert.assertEquals(actualMessage, expectedMessage, "Verifying Fields, Comparing Fieldname:" + aFields[i]);
				} else if (aFields[i].endsWith("TX")) {
					try {
						String actualValue = FrameworkDriver.driver.findElement(By.logicalName(sScreenName + "." + aFields[i])).getAttribute("value");
						String expectedValue = aFieldValues[i];
						FrameworkDriver.driver.findElement(By.logicalName(sScreenName + "." + aFields[i])).clear();
						FrameworkDriver.driver.findElement(By.logicalName(sScreenName + "." + aFields[i])).sendKeys(actualValue);
						System.out.println("Verifying Fields by typing value, Fieldname:" + aFields[i] + "(" + aFieldValues[i] + ") - success");
						Assert.pass("Verifying Fields by typing value, Fieldname:" + aFields[i] + "(" + aFieldValues[i] + ") - success");
						Assert.assertEquals(actualValue, expectedValue, "Verifying Fields, Fieldname:" + aFields[i]);
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("Verifying Fields, Fieldname:" + sScreenName + "." + aFields[i] + " - fail");
						Assert.fail("Verifying Fields, Fieldname:" + sScreenName + "." + aFields[i] + " fail.  Error (" + e.getStackTrace().toString() + ")");
					}
				}else {
					try {
						String actualValue = FrameworkDriver.driver.findElement(By.logicalName(sScreenName + "." + aFields[i])).getText();
						String expectedValue = aFieldValues[i];
						FrameworkDriver.driver.findElement(By.logicalName(sScreenName + "." + aFields[i])).clear();
						FrameworkDriver.driver.findElement(By.logicalName(sScreenName + "." + aFields[i])).sendKeys(actualValue);
						System.out.println("Verifying Fields, Fieldname:" + aFields[i] + "(" + aFieldValues[i] + ") - success");
						Assert.pass("Verifying Fields, Fieldname:" + aFields[i] + "(" + aFieldValues[i] + ") - success");
						Assert.assertEquals(actualValue, expectedValue, "Verifying Fields, Fieldname:" + aFields[i]);
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("Verifying Fields, Fieldname:" + sScreenName + "." + aFields[i] + " - fail");
						Assert.fail("Verifying Fields, Fieldname:" + sScreenName + "." + aFields[i] + " fail.  Error (" + e.getStackTrace().toString() + ")");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Verifying Fields, Fieldname:" + sScreenName + "." + aFields[i] + " - fail");
				Assert.fail("Verifying Fields, Fieldname:" + sScreenName + "." + aFields[i] + " fail.  Error (" + e.getStackTrace().toString() + ")");
			}
		}
		Assert.pass("End --> SeO2.Forms.verifyFields with data from array");
		System.out.println("End --> SeO2.Forms.verifyFields with data from array");
	}
	
	public static void verifyFields(String sScreenName, String[] aFields) {
		Assert.pass("Start --> SeO2.Forms.verifyFields");
		System.out.println("Start --> SeO2.Forms.verifyFields");
		boolean isReadonlyField = false;

		if (sScreenName.endsWith(".")) {
			sScreenName = sScreenName.substring(0, sScreenName.length() - 1);
		}

		for (int i = 0; i < aFields.length; i++) {
			Assert.done("SeO2.Forms.verifyFields with : " + aFields[i]);
			System.out.println("SeO2.Forms.verifyFields with :");
			try{
				if(aFields[i].trim().endsWith("-r")||aFields[i].trim().endsWith("-R")){
					aFields[i] = aFields[i].substring(0, aFields[i].length()-2);
					isReadonlyField = true;
				}
			}catch(Exception e){
			}
			try {
				if (aFields[i].equalsIgnoreCase("") || aFields[i] == null) {
					System.out.println("Verifying Fields, Fieldname:" + sScreenName + "." + aFields[i] + " is empty or null");
					Assert.fail("Verifying Fields, Fieldname:" + sScreenName + "." + aFields[i] + " is empty or null");
				} else if (By.logicalName(sScreenName + "." + aFields[i]) == null) {
					System.out.println("Verifying Fields, Fieldname:" + sScreenName + "." + aFields[i] + " does not exist, please check object properties");
					Assert.fail("Verifying Fields, Fieldname:" + sScreenName + "." + aFields[i] + " does not exist, please check object properties");
				}else{
					// if object exist
					System.out.println("Verifying Fields, Fieldname:" + sScreenName + "." + aFields[i] + " is exist");
					Assert.pass("Verifying Fields, Fieldname:" + sScreenName + "." + aFields[i] + " is exist");
					
					if (FrameworkDriver.driver.findElement(By.logicalName(sScreenName + "." + aFields[i])).isDisplayed()) {
						System.out.println("Verifying Fields, Fieldname:" + sScreenName + "." + aFields[i] + " is displayed");
						Assert.pass("Verifying Fields, Fieldname:" + sScreenName + "." + aFields[i] + " is displayed");
					
						if(isReadonlyField){
							boolean isEnabled = false;
							boolean isReadOnly = false;
							boolean isDisabled = false;
							try{
								isEnabled = FrameworkDriver.driver.findElement(By.logicalName(sScreenName + "." + aFields[i])).isEnabled();
							} catch (Exception e) {
							}
							try{
								String readOnlyValue = FrameworkDriver.driver.findElement(By.logicalName(sScreenName + "." + aFields[i])).getAttribute("readonly");
								String disabledValue = FrameworkDriver.driver.findElement(By.logicalName(sScreenName + "." + aFields[i])).getAttribute("disabled");
								
								if(readOnlyValue!=null){
									if(readOnlyValue.trim().equalsIgnoreCase("true")||readOnlyValue.trim().equalsIgnoreCase("readonly")){
										isReadOnly = true;
									}
								}
								
								if(disabledValue!=null){
									if(disabledValue.trim().equalsIgnoreCase("true")||disabledValue.trim().equalsIgnoreCase("disabled")){
										isDisabled = true;
									}
								}
								
							} catch (Exception e) {
								e.printStackTrace();
							}
							
							if(isEnabled || isReadOnly || isDisabled){
								System.out.println("Verifying Fields, Fieldname:" + sScreenName + "." + aFields[i] + " is readonly field");
								Assert.pass("Verifying Fields, Fieldname:" + sScreenName + "." + aFields[i] + " is readonly field");
							}else{
								System.out.println("Verifying Fields, Fieldname:" + sScreenName + "." + aFields[i] + " is not readonly field");
								Assert.fail("Verifying Fields, Fieldname:" + sScreenName + "." + aFields[i] + " is not readonly field");
							}
							}
						}
						
					}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Verifying Fields, Fieldname:" + sScreenName + "." + aFields[i] + " - fail");
				//Assert.fail("Verifying Fields, Fieldname:" + sScreenName + "." + aFields[i] + " fail.  Error (" + e.getStackTrace().toString() + ")");
				Assert.error(e, "Verifying Fields, Fieldname:" + sScreenName + "." + aFields[i] );
			}
		}
		Assert.pass("End --> SeO2.Forms.verifyFields");
		System.out.println("End --> SeO2.Forms.verifyFields");
	}

}
