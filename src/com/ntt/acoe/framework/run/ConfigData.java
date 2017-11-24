package com.ntt.acoe.framework.run;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import com.ntt.acoe.framework.selenium.report.Reporting;

/*
 * @author Santosh Hariprasad (NTT Badge Id: 244583,
 *         Santhosh.Hariprasad@NTTDATA.com)
 * @version 1.0
 * @since 2015-01-01
 */
public class ConfigData {

	public static String getValue(String configFile, String sheetName, String property) throws IOException {
		String matchedData = "Not found";
		FileInputStream file = new FileInputStream(new File(configFile));
		HSSFWorkbook workbook = new HSSFWorkbook(file);
		HSSFSheet sheet = workbook.getSheet(sheetName);
		Iterator<Row> rowIterator = sheet.iterator();
		while (rowIterator.hasNext()) {
			Row currentRow = rowIterator.next();
			Cell propertyCell = currentRow.getCell(0);
			// System.out.println("Row testCaseIdCell:"+testCaseIdCell+
			// " testStepIdCell:"+testStepIdCell);
			if (propertyCell.getStringCellValue().equalsIgnoreCase(property)) {
				Cell valueCell = currentRow.getCell(1);
				String currentCellValue = "";
				if (valueCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
					currentCellValue = String.valueOf(valueCell.getNumericCellValue());
				} else if (valueCell.getCellType() == Cell.CELL_TYPE_STRING) {
					currentCellValue = valueCell.getStringCellValue();
				} else if (valueCell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
					currentCellValue = String.valueOf(valueCell.getBooleanCellValue());
				} else if (valueCell.getCellType() == Cell.CELL_TYPE_BLANK) {
					currentCellValue = "";
				} else if (valueCell.getCellType() == Cell.CELL_TYPE_FORMULA) {
					currentCellValue = String.valueOf(valueCell.getCellFormula());
				} else if (valueCell.getCellType() == Cell.CELL_TYPE_ERROR) {
					currentCellValue = "Error reading data";
				}
				// System.out.println("Colval:"+currentCellValue);
				matchedData = currentCellValue;
				break;
			}
		}
		return matchedData;
	}

	public static ArrayList<Test> getTests(String configFile, String sheetName) throws IOException {
		FileInputStream file = new FileInputStream(new File(configFile));
		HSSFWorkbook workbook = new HSSFWorkbook(file);
		HSSFSheet sheet = workbook.getSheet(sheetName);
		Iterator<Row> rowIterator = sheet.iterator();

		ArrayList<Test> tests = new ArrayList<Test>();
		rowIterator.next();
		while (rowIterator.hasNext()) {
			Row currentRow = rowIterator.next();

			String sno = "";
			Cell snoCell = currentRow.getCell(0);
			Reporting.report("DONE", "Reading tests from file:" + configFile + " , sheet: TestPlan, Row:" + currentRow.getRowNum());
			try {
				if (snoCell.getCellTypeEnum() == CellType.NUMERIC) {
					sno = String.valueOf(snoCell.getNumericCellValue());
				} else if (snoCell.getCellTypeEnum() == CellType.STRING) {
					sno = snoCell.getStringCellValue();
				} else if (snoCell.getCellTypeEnum() == CellType.BOOLEAN) {
					sno = String.valueOf(snoCell.getBooleanCellValue());
				} else if (snoCell.getCellTypeEnum() == CellType.BLANK) {
					sno = "";
				} else if (snoCell.getCellTypeEnum() == CellType.FORMULA) {
					sno = String.valueOf(snoCell.getCellFormula());
				} else if (snoCell.getCellTypeEnum() == CellType.ERROR) {
					sno = "Error reading data";
				}
			} catch (Exception e1) {
				sno = "";
				Reporting.report("DONE", "Reading tests from file:" + configFile + " , sheet: TestPlan, Row:" + currentRow.getRowNum() + " S.No found empty");
			}

			String testCase = "";
			try {
				Cell testcaseCell = currentRow.getCell(1);
				if (testcaseCell.getCellTypeEnum() == CellType.NUMERIC) {
					testCase = String.valueOf(testcaseCell.getNumericCellValue());
				} else if (testcaseCell.getCellTypeEnum() == CellType.STRING) {
					testCase = testcaseCell.getStringCellValue();
				} else if (testcaseCell.getCellTypeEnum() == CellType.BOOLEAN) {
					testCase = String.valueOf(testcaseCell.getBooleanCellValue());
				} else if (testcaseCell.getCellTypeEnum() == CellType.BLANK) {
					testCase = "";
				} else if (testcaseCell.getCellTypeEnum() == CellType.FORMULA) {
					testCase = String.valueOf(testcaseCell.getCellFormula());
				} else if (testcaseCell.getCellTypeEnum() == CellType.ERROR) {
					testCase = "Error reading data";
				}
			} catch (Exception e1) {
				testCase = "";
				Reporting.report("DONE", "Reading tests from file:" + configFile + " , sheet: TestPlan, Row:" + currentRow.getRowNum() + " Test Case found empty");
			}

			System.out.println("Reading test from test plan:" + sno + "Test case:" + testCase);
			String canExecute = "";
			try {
				Cell canExecuteCell = currentRow.getCell(2);
				if (canExecuteCell.getCellTypeEnum() == CellType.NUMERIC) {
					canExecute = String.valueOf(canExecuteCell.getNumericCellValue());
				} else if (canExecuteCell.getCellTypeEnum() == CellType.STRING) {
					canExecute = canExecuteCell.getStringCellValue();
				} else if (canExecuteCell.getCellTypeEnum() == CellType.BOOLEAN) {
					canExecute = String.valueOf(canExecuteCell.getBooleanCellValue());
				} else if (canExecuteCell.getCellTypeEnum() == CellType.BLANK) {
					canExecute = "";
				} else if (canExecuteCell.getCellTypeEnum() == CellType.FORMULA) {
					canExecute = String.valueOf(canExecuteCell.getCellFormula());
				} else if (canExecuteCell.getCellTypeEnum() == CellType.ERROR) {
					canExecute = "Error reading data";
				}
			} catch (Exception e) {
				canExecute = "NO";
				e.printStackTrace();
			}

			String newSNO = "";
			if (sno.contains(".")) {
				newSNO = sno.split("\\.", -1)[0];
			} else {
				newSNO = sno;
			}

			if (!testCase.trim().equalsIgnoreCase("")) {
				tests.add(new Test(Integer.valueOf(newSNO), testCase.trim(), canExecute.trim()));
			}
		}
		return tests;
	}

	public static ArrayList<Script> getScripts(String configFile, String sheetName) throws IOException {
		FileInputStream file = new FileInputStream(new File(configFile));
		HSSFWorkbook workbook = new HSSFWorkbook(file);
		HSSFSheet sheet = workbook.getSheet(sheetName);
		Iterator<Row> rowIterator = sheet.iterator();

		ArrayList<Script> scripts = new ArrayList<Script>();
		rowIterator.next();
		while (rowIterator.hasNext()) {
			Row currentRow = rowIterator.next();

			String sno = "";
			Cell snoCell = currentRow.getCell(0);
			//System.out.println("SNo Type: "+snoCell.getCellTypeEnum());
			if (snoCell.getCellTypeEnum() == CellType.NUMERIC) {
				sno = String.valueOf(snoCell.getNumericCellValue());
			} else if (snoCell.getCellTypeEnum() == CellType.STRING) {
				sno = snoCell.getStringCellValue();
			} else if (snoCell.getCellTypeEnum() == CellType.BOOLEAN) {
				sno = String.valueOf(snoCell.getBooleanCellValue());
			} else if (snoCell.getCellTypeEnum() == CellType.BLANK) {
				sno = "";
			} else if (snoCell.getCellTypeEnum() == CellType.FORMULA) {
				sno = String.valueOf(snoCell.getCellFormula());
			} else if (snoCell.getCellTypeEnum() == CellType.ERROR) {
				sno = "Error reading data";
			}

			String testCase = "";
			Cell testcaseCell = currentRow.getCell(1);
			if (testcaseCell.getCellTypeEnum() == CellType.NUMERIC) {
				testCase = String.valueOf(testcaseCell.getNumericCellValue());
			} else if (testcaseCell.getCellTypeEnum() == CellType.STRING) {
				testCase = testcaseCell.getStringCellValue();
			} else if (testcaseCell.getCellTypeEnum() == CellType.BOOLEAN) {
				testCase = String.valueOf(testcaseCell.getBooleanCellValue());
			} else if (testcaseCell.getCellTypeEnum() == CellType.BLANK) {
				testCase = "";
			} else if (testcaseCell.getCellTypeEnum() == CellType.FORMULA) {
				testCase = String.valueOf(testcaseCell.getCellFormula());
			} else if (testcaseCell.getCellTypeEnum() == CellType.ERROR) {
				testCase = "Error reading data";
			}

			String scriptPackage = "";
			Cell scriptPackageCell = currentRow.getCell(2);
			if (scriptPackageCell.getCellTypeEnum() == CellType.NUMERIC) {
				scriptPackage = String.valueOf(scriptPackageCell.getNumericCellValue());
			} else if (scriptPackageCell.getCellTypeEnum() == CellType.STRING) {
				scriptPackage = scriptPackageCell.getStringCellValue();
			} else if (scriptPackageCell.getCellTypeEnum() == CellType.BOOLEAN) {
				scriptPackage = String.valueOf(scriptPackageCell.getBooleanCellValue());
			} else if (scriptPackageCell.getCellTypeEnum() == CellType.BLANK) {
				scriptPackage = "";
			} else if (scriptPackageCell.getCellTypeEnum() == CellType.FORMULA) {
				scriptPackage = String.valueOf(scriptPackageCell.getCellFormula());
			} else if (scriptPackageCell.getCellTypeEnum() == CellType.ERROR) {
				scriptPackage = "Error reading data";
			}

			String scriptClass = "";
			Cell scriptClassCell = currentRow.getCell(3);
			if (scriptClassCell.getCellTypeEnum() == CellType.NUMERIC) {
				scriptClass = String.valueOf(scriptClassCell.getNumericCellValue());
			} else if (scriptClassCell.getCellTypeEnum() == CellType.STRING) {
				scriptClass = scriptClassCell.getStringCellValue();
			} else if (scriptClassCell.getCellTypeEnum() == CellType.BOOLEAN) {
				scriptClass = String.valueOf(scriptClassCell.getBooleanCellValue());
			} else if (scriptClassCell.getCellTypeEnum() == CellType.BLANK) {
				scriptClass = "";
			} else if (scriptClassCell.getCellTypeEnum() == CellType.FORMULA) {
				scriptClass = String.valueOf(scriptClassCell.getCellFormula());
			} else if (scriptClassCell.getCellTypeEnum() == CellType.ERROR) {
				scriptClass = "Error reading data";
			}

			String scriptMethod = "";
			Cell scriptMethodCell = currentRow.getCell(4);
			if (scriptMethodCell.getCellTypeEnum() == CellType.NUMERIC) {
				scriptMethod = String.valueOf(scriptMethodCell.getNumericCellValue());
			} else if (scriptMethodCell.getCellTypeEnum() == CellType.STRING) {
				scriptMethod = scriptMethodCell.getStringCellValue();
			} else if (scriptMethodCell.getCellTypeEnum() == CellType.BOOLEAN) {
				scriptMethod = String.valueOf(scriptMethodCell.getBooleanCellValue());
			} else if (scriptMethodCell.getCellTypeEnum() == CellType.BLANK) {
				scriptMethod = "";
			} else if (scriptMethodCell.getCellTypeEnum() == CellType.FORMULA) {
				scriptMethod = String.valueOf(scriptMethodCell.getCellFormula());
			} else if (scriptMethodCell.getCellTypeEnum() == CellType.ERROR) {
				scriptMethod = "Error reading data";
			}				
			scripts.add(new Script(Integer.valueOf(sno), testCase.trim(), scriptPackage.trim(), scriptClass.trim(), scriptMethod.trim()));
		}
		return scripts;
	}

	public static ArrayList<Property> getProperties(String configFile, String sheetName) throws IOException {
		FileInputStream file = new FileInputStream(new File(configFile));
		HSSFWorkbook workbook = new HSSFWorkbook(file);
		HSSFSheet sheet = workbook.getSheet(sheetName);
		Iterator<Row> rowIterator = sheet.iterator();

		ArrayList<Property> props = new ArrayList<Property>();
		rowIterator.next();
		while (rowIterator.hasNext()) {
			Row currentRow = rowIterator.next();

			String property = "";
			Cell propertyCell = currentRow.getCell(0);
			if (propertyCell.getCellTypeEnum() == CellType.NUMERIC) {
				property = String.valueOf(propertyCell.getNumericCellValue());
			} else if (propertyCell.getCellTypeEnum() == CellType.STRING) {
				property = propertyCell.getStringCellValue();
			} else if (propertyCell.getCellTypeEnum() == CellType.BOOLEAN) {
				property = String.valueOf(propertyCell.getBooleanCellValue());
			} else if (propertyCell.getCellTypeEnum() == CellType.BLANK) {
				property = "";
			} else if (propertyCell.getCellTypeEnum() == CellType.FORMULA) {
				property = String.valueOf(propertyCell.getCellFormula());
			} else if (propertyCell.getCellTypeEnum() == CellType.ERROR) {
				property = "Error reading data";
			}

			String value = "";
			Cell valueCell = currentRow.getCell(1);
			if (valueCell.getCellTypeEnum() == CellType.NUMERIC) {
				value = String.valueOf(valueCell.getNumericCellValue());
			} else if (valueCell.getCellTypeEnum() == CellType.STRING) {
				value = valueCell.getStringCellValue();
			} else if (valueCell.getCellTypeEnum() == CellType.BOOLEAN) {
				value = String.valueOf(valueCell.getBooleanCellValue());
			} else if (valueCell.getCellTypeEnum() == CellType.BLANK) {
				value = "";
			} else if (valueCell.getCellTypeEnum() == CellType.FORMULA) {
				value = String.valueOf(valueCell.getCellFormula());
			} else if (valueCell.getCellTypeEnum() == CellType.ERROR) {
				value = "Error reading data";
			}

			props.add(new Property(property.trim(), value.trim()));
		}
		return props;
	}

	public static void main(String args[]) throws IOException {
		System.out.println("Data:" + ConfigData.getValue("C:/iolautomation/TestData/Driver.xls", "Sheet1", "Username"));
		System.out.println("Data:" + ConfigData.getValue("C:/iolautomation/TestData/Driver.xls", "Sheet1", "Password1"));
	}

}
