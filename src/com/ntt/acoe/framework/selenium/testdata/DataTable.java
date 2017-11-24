package com.ntt.acoe.framework.selenium.testdata;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
//import org.apache.poi.xssf.usermodel.XSSFCell;
//import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
//import org.apache.poi.xssf.usermodel.XSSFRow;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.ntt.acoe.framework.config.Environment;
import com.ntt.acoe.framework.loggers.ScriptLogger;
import com.ntt.acoe.framework.selenium.report.Reporting;
import com.ntt.acoe.framework.selenium.verify.Assert;

import org.apache.poi.ss.usermodel.Row;

/*
 * @author Santosh Hariprasad (NTT Badge Id: 244583,
 *         Santhosh.Hariprasad@NTTDATA.com)
 * @version 1.0
 * @since 2015-01-01
 */

public class DataTable {
	String[][] matchedData;
	public int rowCount;
	String fileName;
	String sheetName;
	String testCaseId;
	ArrayList<Row> matchedRows = new ArrayList<Row>();
	FormulaEvaluator evaluator = null;
	//public static final Row.MissingCellPolicy CREATE_NULL_AS_BLANK;
	


	public DataTable(String fileName, String sheetName, String testCaseID) {
		if (fileName.trim().equalsIgnoreCase("") || fileName == null) {
			Assert.fail("Location: com.dell.acoe.framework.selenium.testdata.DataTable - Error while reading data from file:" + fileName + "  File name can not be empty.  Please check the file name");
			System.out.println(
					"Location: com.dell.acoe.framework.selenium.testdata.DataTable - Error while reading data from file:" + fileName + "  File name can not be empty.  Please check the file name");
			throw new NullPointerException(
					"Location: com.dell.acoe.framework.selenium.testdata.DataTable - Error while reading data from file:" + fileName + "  File name can not be empty.  Please check the file name");
		}

		if (sheetName.trim().equalsIgnoreCase("") || sheetName == null) {
			Assert.fail("Location: com.dell.acoe.framework.selenium.testdata.DataTable - Error while reading data from file:" + fileName + "  and from sheet:" + sheetName
					+ ". Sheet name can not be empty.  Please check the sheet name");
			System.out.println("Location: com.dell.acoe.framework.selenium.testdata.DataTable - Error while reading data from file:" + fileName + "  and from sheet:" + sheetName
					+ ". Sheet name can not be empty.  Please check the sheet name");
			throw new NullPointerException("Location: com.dell.acoe.framework.selenium.testdata.DataTable - Error while reading data from file:" + fileName + "  and from sheet:" + sheetName
					+ ". Sheet name can not be empty.  Please check the sheet name");
		}

		if (testCaseID.trim().equalsIgnoreCase("") || testCaseID == null) {
			Assert.fail("Location: com.dell.acoe.framework.selenium.testdata.DataTable - Error while reading data from file:" + fileName + "  and from sheet:" + sheetName + " and test case id:"
					+ testCaseID + ". Test case ID can not be empty.  Please check the test case ID");
			System.out.println("Location: com.dell.acoe.framework.selenium.testdata.DataTable - Error while reading data from file:" + fileName + "  and from sheet:" + sheetName + " and test case id:"
					+ testCaseID + ". Test case ID can not be empty.  Please check the test case ID");
			throw new NullPointerException("Location: com.dell.acoe.framework.selenium.testdata.DataTable - Error while reading data from file:" + fileName + "  and from sheet:" + sheetName
					+ " and test case id:" + testCaseID + ". Test case ID can not be empty.  Please check the test case ID");
		}

		if ((!fileName.contains("/")) && (!fileName.contains("\\"))) {
			this.fileName = Environment.get("test_data_path") + "/" + fileName;
		} else {
			this.fileName = fileName;
		}

		this.sheetName = sheetName.trim();
		this.testCaseId = testCaseID.trim();

		File f = new File(this.fileName);
		if (!f.exists()) {
			// Reporting.report("ERROR", "Location:
			// com.dell.acoe.framework.selenium.testdata.DataTable - Error while
			// reading data from file:" + fileName
			// + " File does not exist. Please check the file name and path and
			// letter case and extension");
			System.out.println("Location: com.dell.acoe.framework.selenium.testdata.DataTable - Error while reading data from file:" + fileName
					+ "  File does not exist.  Please check the file name and path and letter case and extension");
			Assert.fail("Location: com.dell.acoe.framework.selenium.testdata.DataTable - Error while reading data from file:" + fileName
					+ "  File does not exist.  Please check the file name and path and letter case and extension");

			throw new NullPointerException("Location: com.dell.acoe.framework.selenium.testdata.DataTable - Error while reading data from file:" + fileName
					+ "  File does not exist.  Please check the file name and path and letter case and extension");
		}

		if (!doesSheetExist()) {
			// Reporting.report("ERROR", "Location:
			// com.dell.acoe.framework.selenium.testdata.DataTable - Error while
			// reading data from file:" + fileName + " and from sheet:" +
			// sheetName
			// + ". Sheet name can not be empty. Please check the sheet name and
			// letter case");
			System.out.println("Location: com.dell.acoe.framework.selenium.testdata.DataTable - Error while reading data from file:" + fileName + "  and from sheet:" + sheetName
					+ ". Sheet name can not be empty.  Please check the sheet name and letter case");
			Assert.fail("Location: com.dell.acoe.framework.selenium.testdata.DataTable - Error while reading data from file:" + fileName + "  and from sheet:" + sheetName
					+ ". Sheet name can not be empty.  Please check the sheet name and letter case");
			throw new NullPointerException("Location: com.dell.acoe.framework.selenium.testdata.DataTable - Error while reading data from file:" + fileName + "  and from sheet:" + sheetName
					+ ". Sheet name can not be empty.  Please check the sheet name and letter case");
		}

		if (!doesTestCaseIdExist()) {
			// Reporting.report("ERROR", "Location:
			// com.dell.acoe.framework.selenium.testdata.DataTable - Error while
			// reading data from file:" + fileName + " and from sheet:" +
			// sheetName
			// + " and Test case id:" + this.testCaseId + ". No matched rows
			// found with this test case id. Please check the test case id and
			// letter case and spaces");
			System.out.println("Location: com.dell.acoe.framework.selenium.testdata.DataTable - Error while reading data from file:" + fileName + "  and from sheet:" + sheetName + " and Test case id:"
					+ this.testCaseId + ". No matched rows found with this test case id.  Please check the test case id and letter case and spaces");
			Assert.fail("Location: com.dell.acoe.framework.selenium.testdata.DataTable - Error while reading data from file:" + fileName + "  and from sheet:" + sheetName + " and Test case id:"
					+ this.testCaseId + ". No matched rows found with this test case id.  Please check the test case id and letter case and spaces");
			throw new NullPointerException("Location: com.dell.acoe.framework.selenium.testdata.DataTable - Error while reading data from file:" + fileName + "  and from sheet:" + sheetName
					+ " and Test case id:" + this.testCaseId + ". No matched rows found with this test case id.  Please check the test case id and letter case and spaces");
		}

		rowCount = matchedRows.size() - 1;
	}

	private boolean doesSheetExist() {
		InputStream file = null;
		try {
			file = new FileInputStream(this.fileName);
		} catch (FileNotFoundException e) {
			// Reporting.report("ERROR", "Location:
			// com.dell.acoe.framework.selenium.testdata.DataTable - Error while
			// reading data from file:" + fileName
			// + " File does not exist. Please check the file name and path and
			// letter case and extension");
			System.out.println("Location: com.dell.acoe.framework.selenium.testdata.DataTable - Error while reading data from file:" + fileName
					+ "  File does not exist.  Please check the file name and path and letter case and extension");
			Assert.fail("Location: com.dell.acoe.framework.selenium.testdata.DataTable - Error while reading data from file:" + fileName
					+ "  File does not exist.  Please check the file name and path and letter case and extension");

		}

		HSSFWorkbook workbook = null;
		try {
			workbook = new HSSFWorkbook(file);
			HSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);
		} catch (IOException e) {
			// Reporting.report("ERROR", "Location:
			// com.dell.acoe.framework.selenium.testdata.DataTable - Error while
			// reading data from file:" + fileName
			// + " File is not valid XLSX or XLS file. Please check the file
			// name and path and letter case and extension");
			System.out.println("Location: com.dell.acoe.framework.selenium.testdata.DataTable - Error while reading data from file:" + fileName
					+ "  File is not valid XLSX or XLS file.  Please check the file name and path and letter case and extension");
			Assert.fail("Location: com.dell.acoe.framework.selenium.testdata.DataTable - Error while reading data from file:" + fileName
					+ "  File is not valid XLSX or XLS file.  Please check the file name and path and letter case and extension");

		}

		HSSFSheet sheet = workbook.getSheet(sheetName.trim());
		evaluator = workbook.getCreationHelper().createFormulaEvaluator();

		if (sheet == null) {
			return false;
		} else {
			return true;
		}
	}

	private boolean doesTestCaseIdExist() {
		boolean isTestCaseIDExist = false;
		InputStream file = null;
		try {
			file = new FileInputStream(this.fileName);
		} catch (FileNotFoundException e) {
			// Reporting.report("ERROR", "Location:
			// com.dell.acoe.framework.selenium.testdata.DataTable - Error while
			// reading data from file:" + fileName
			// + " File does not exist. Please check the file name and path and
			// letter case and extension");
			System.out.println("Location: com.dell.acoe.framework.selenium.testdata.DataTable - Error while reading data from file:" + fileName
					+ "  File does not exist.  Please check the file name and path and letter case and extension");
			Assert.fail("Location: com.dell.acoe.framework.selenium.testdata.DataTable - Error while reading data from file:" + fileName
					+ "  File does not exist.  Please check the file name and path and letter case and extension");

		}

		HSSFWorkbook workbook = null;
		try {
			workbook = new HSSFWorkbook(file);
		} catch (IOException e) {
			// Reporting.report("ERROR", "Location:
			// com.dell.acoe.framework.selenium.testdata.DataTable - Error while
			// reading data from file:" + fileName
			// + " File is not valid XLSX or XLS file. Please check the file
			// name and path and letter case and extension");
			System.out.println("Location: com.dell.acoe.framework.selenium.testdata.DataTable - Error while reading data from file:" + fileName
					+ "  File is not valid XLSX or XLS file.  Please check the file name and path and letter case and extension");
			Assert.fail("Location: com.dell.acoe.framework.selenium.testdata.DataTable - Error while reading data from file:" + fileName
					+ "  File is not valid XLSX or XLS file.  Please check the file name and path and letter case and extension");

		}

		HSSFSheet sheet = workbook.getSheet(sheetName.trim());

		if (sheet == null) {
			// Reporting.report("ERROR", "Location:
			// com.dell.acoe.framework.selenium.testdata.DataTable - Error while
			// reading data from file:" + fileName + " and sheet:" + sheetName
			// + ". Please check the sheet name and letter case or spaces");
			System.out.println("Location: com.dell.acoe.framework.selenium.testdata.DataTable - Error while reading data from file:" + fileName + " and sheet:" + sheetName
					+ ".  Please check the sheet name and letter case or spaces");
			Assert.fail("Location: com.dell.acoe.framework.selenium.testdata.DataTable - Error while reading data from file:" + fileName + " and sheet:" + sheetName
					+ ".  Please check the sheet name and letter case or spaces");

		}

		int s = sheet.getFirstRowNum();
		int l = sheet.getLastRowNum();
		System.out.println("DataTable: Sheet:"+sheetName+" FirstRow:"+s+" ,LastRow:"+l);
		if (l <= s) {
			Assert.fail("Location: com.dell.acoe.framework.selenium.testdata.DataTable - Error while reading data from file:" + fileName + " and sheet:" + sheetName
					+ ". No rows found in the sheet. Please check if the sheet contain proper data.");
			System.out.println("Location: com.dell.acoe.framework.selenium.testdata.DataTable - Error while reading data from file:" + fileName + " and sheet:" + sheetName
					+ ". No rows found in the sheet. Please check if the sheet contain proper data.");
		} else if ((l - s) < 1) {
			// Reporting.report("ERROR", "Location:
			// com.dell.acoe.framework.selenium.testdata.DataTable - Error while
			// reading data from file:" + fileName + " and sheet:" + sheetName
			// + ". Single row found in the sheet. Please check if the sheet
			// contain proper data.");
			System.out.println("Location: com.dell.acoe.framework.selenium.testdata.DataTable - Error while reading data from file:" + fileName + " and sheet:" + sheetName
					+ ". Single row found in the sheet. Please check if the sheet contain proper data.");
			Assert.fail("Location: com.dell.acoe.framework.selenium.testdata.DataTable - Error while reading data from file:" + fileName + " and sheet:" + sheetName
					+ ". Single row found in the sheet. Please check if the sheet contain proper data.");

		} else {
			Iterator<Row> rowIterator = sheet.iterator();
			matchedRows.add(rowIterator.next());
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				
				if (row == null) {
					continue;
				} else {
					Cell cell = row.getCell(row.getFirstCellNum(), Row.MissingCellPolicy.RETURN_NULL_AND_BLANK);
					if (cell == null) {
					} else {
						if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
							if (cell.getStringCellValue().trim().equalsIgnoreCase(testCaseId.trim())) {
								matchedRows.add(row);
								isTestCaseIDExist = true;
							}
						}
					}
				}

			}
		}

		return isTestCaseIDExist;
	}

	public String getValue(String fieldName) {
		return getValue(fieldName, 1);
	}

	public String getValue(String fieldName, int rowNo) {
		String cellValue = "";
		int columnNo = -1;
		Row headerRow = matchedRows.get(0);
		for (Cell cell : headerRow) {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				cellValue = cell.getStringCellValue();
				break;
			case Cell.CELL_TYPE_NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					cellValue = cell.getDateCellValue().toString();
				} else {
					cellValue = String.valueOf((int) cell.getNumericCellValue());
				}
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				cellValue = String.valueOf(cell.getBooleanCellValue());
				break;
			case Cell.CELL_TYPE_FORMULA:
				//cellValue = cell.getCellFormula();
				cellValue = evaluator.evaluate(cell).getStringValue();
				break;
			default:
				cellValue = "";
			}
			if (cellValue.trim().equalsIgnoreCase(fieldName.trim())) {
				columnNo = cell.getColumnIndex();
				break;
			}
		}

		if (columnNo == -1) {
			// Reporting.report("ERROR", "Location:
			// com.dell.acoe.framework.selenium.testdata.DataTable - Error while
			// reading data from file:" + fileName + " and from sheet:" +
			// sheetName
			// + " and Test case id:" + this.testCaseId + " and
			// Field:"+fieldName+". No matched column found with this test case
			// id. Please check the column name and letter case and spaces");
			System.out.println("Location: com.dell.acoe.framework.selenium.testdata.DataTable - Error while reading data from file:" + fileName + "  and from sheet:" + sheetName + " and Test case id:"
					+ this.testCaseId + " and Field:" + fieldName + ". No matched column found with this test case id.  Please check the column name and letter case and spaces");
			Assert.fail("Location: com.dell.acoe.framework.selenium.testdata.DataTable - Error while reading data from file:" + fileName + "  and from sheet:" + sheetName + " and Test case id:"
					+ this.testCaseId + " and Field:" + fieldName + ". No matched column found with this test case id.  Please check the column name and letter case and spaces");

		}

		cellValue = "";
		try {
			Row row = matchedRows.get(rowNo);
			Cell cell = row.getCell(columnNo);

			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				cellValue = cell.getStringCellValue();
				break;
			case Cell.CELL_TYPE_NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					cellValue = cell.getDateCellValue().toString();
				} else {
					cellValue = String.valueOf((int) cell.getNumericCellValue());
				}
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				cellValue = String.valueOf(cell.getBooleanCellValue());
				break;
			case Cell.CELL_TYPE_FORMULA:
				//cellValue = cell.getCellFormula();
				cellValue = evaluator.evaluate(cell).getStringValue();
				break;
			default:
				cellValue = "";
				break;
			}
		} catch (Exception e) {
			ScriptLogger.log.info("Location: com.dell.acoe.framework.selenium.testdata.DataTable - Error while reading data from file:" + fileName + "  and from sheet:" + sheetName
					+ " and Test case id:" + this.testCaseId + " and Field:" + fieldName + ". Exception raised while reading column value.  Please check the column data for the given row. Exception:"
					+ e.getMessage());
			System.out.println("Location: com.dell.acoe.framework.selenium.testdata.DataTable - Error while reading data from file:" + fileName + "  and from sheet:" + sheetName + " and Test case id:"
					+ this.testCaseId + " and Field:" + fieldName + ". Exception raised while reading column value.  Please check the column data for the given row. Exception:" + e.getMessage());
			// Assert.done("Location:
			// com.dell.acoe.framework.selenium.testdata.DataTable - Error while
			// reading data from file:" + fileName + " and from sheet:" +
			// sheetName
			// + " and Test case id:" + this.testCaseId + " and
			// Field:"+fieldName+". Exception raised while reading column value.
			// Please check the column data for the given row.
			// Exception:"+e.getMessage());
		}

		return cellValue;
	}

	public void setValue(String fieldName, int rowNo, String value) {
		String cellValue = "";
		int columnNo = -1;
		Row headerRow = matchedRows.get(0);
		for (Cell cell : headerRow) {
			switch (cell.getCellTypeEnum()) {
			case STRING:
				cellValue = cell.getStringCellValue();
				break;
			case NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					cellValue = cell.getDateCellValue().toString();
				} else {
					cellValue = String.valueOf((int) cell.getNumericCellValue());
				}
				break;
			case BOOLEAN:
				cellValue = String.valueOf(cell.getBooleanCellValue());
				break;
			case FORMULA:
				cellValue = cell.getCellFormula();
				break;
			default:
				cellValue = "";
				break;
			}
			if (cellValue.trim().equalsIgnoreCase(fieldName.trim())) {
				columnNo = cell.getColumnIndex();
				break;
			}
		}

		if (columnNo == -1) {
			// Reporting.report("ERROR", "Location:
			// com.dell.acoe.framework.selenium.testdata.DataTable - Error while
			// writing data to file:" + fileName + " and to sheet:" + sheetName
			// + " and Test case id:" + this.testCaseId + " and
			// Field:"+fieldName+". No matched column found with this test case
			// id. Please check the column name and letter case and spaces");
			System.out.println("Location: com.dell.acoe.framework.selenium.testdata.DataTable - Error while writing data to file:" + fileName + "  and from sheet:" + sheetName + " and Test case id:"
					+ this.testCaseId + " and Field:" + fieldName + ". No matched column found with this test case id.  Please check the column name and letter case and spaces");
			Assert.fail("Location: com.dell.acoe.framework.selenium.testdata.DataTable - Error while writing data to file:" + fileName + "  and to sheet:" + sheetName + " and Test case id:"
					+ this.testCaseId + " and Field:" + fieldName + ". No matched column found with this test case id.  Please check the column name and letter case and spaces");

		}

		try {
			// System.out.println("Row:"+rowNo+" Col:"+columnNo);
			FileInputStream testDataFileIS = new FileInputStream(this.fileName);
			HSSFWorkbook wb = new HSSFWorkbook(testDataFileIS);
			HSSFSheet sheet = wb.getSheet(this.sheetName);
			HSSFRow row = sheet.getRow(matchedRows.get(rowNo).getRowNum());
			HSSFCell cell = row.getCell(columnNo, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
			cell.setCellValue(value);
			testDataFileIS.close();
			FileOutputStream testDataFileOS = new FileOutputStream(new File(this.fileName));
			wb.write(testDataFileOS);
			testDataFileOS.close();
		} catch (Exception e) {
			// Reporting.report("ERROR", "Location:
			// com.dell.acoe.framework.selenium.testdata.DataTable - Error while
			// writing data to file:" + fileName + " and to sheet:" + sheetName
			// + " and Test case id:" + this.testCaseId + " and
			// Field:"+fieldName+". Check if the file is available and not in
			// use currently. Exception found:"+e.getMessage());
			System.out.println("Location: com.dell.acoe.framework.selenium.testdata.DataTable - Error while writing data to file:" + fileName + "  and from sheet:" + sheetName + " and Test case id:"
					+ this.testCaseId + " and Field:" + fieldName + ". Check if the file is available and not in use currently. Exception found:" + e.getMessage());
			Assert.fail("Location: com.dell.acoe.framework.selenium.testdata.DataTable - Error while writing data to file:" + fileName + "  and to sheet:" + sheetName + " and Test case id:"
					+ this.testCaseId + " and Field:" + fieldName + ". Check if the file is available and not in use currently. Exception found:" + e.getMessage());

			e.printStackTrace();
		}
	}

}
