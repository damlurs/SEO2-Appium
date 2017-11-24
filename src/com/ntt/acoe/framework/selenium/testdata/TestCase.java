package com.ntt.acoe.framework.selenium.testdata;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/*
 * @author Santosh Hariprasad (NTT Badge Id: 244583,
 *         Santhosh.Hariprasad@NTTDATA.com)
 * @version 1.0
 * @since 2015-01-01
 */

public class TestCase {

	protected static int getRow(String testDataFile, String sheetName, int testCaseIdColNo, String testCaseId) throws IOException {
		if (testDataFile.toLowerCase().endsWith(".xlsx")) {
			return getRowXLSX(testDataFile, sheetName, testCaseIdColNo, testCaseId);
		} else if (testDataFile.toLowerCase().endsWith(".xls")) {
			return getRowXLS(testDataFile, sheetName, testCaseIdColNo, testCaseId);
		} else {
			return -1;
		}
	}

	private static int getRowXLSX(String testDataFile, String sheetName, int testCaseIdColNo, String testCaseId) throws IOException {
		int matchedCol = 1;

		FileInputStream file = new FileInputStream(new File(testDataFile));
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		XSSFSheet sheet = workbook.getSheet(sheetName);
		Iterator<Row> rowIterator = sheet.iterator();

		while (rowIterator.hasNext()) {
			Row currentRow = rowIterator.next();
			Cell testCaseIdCell = currentRow.getCell(testCaseIdColNo - 1);
			if (testCaseIdCell.getStringCellValue().equalsIgnoreCase(testCaseId)) {
				break;
			}

			matchedCol = matchedCol + 1;
		}

		return matchedCol;
	}

	private static int getRowXLS(String testDataFile, String sheetName, int testCaseIdColNo, String testCaseId) throws IOException {
		int matchedCol = 1;

		FileInputStream file = new FileInputStream(new File(testDataFile));
		HSSFWorkbook workbook = new HSSFWorkbook(file);
		HSSFSheet sheet = workbook.getSheet(sheetName);
		Iterator<Row> rowIterator = sheet.iterator();

		while (rowIterator.hasNext()) {
			Row currentRow = rowIterator.next();
			Cell testCaseIdCell = currentRow.getCell(testCaseIdColNo - 1);
			if (testCaseIdCell.getStringCellValue().equalsIgnoreCase(testCaseId)) {
				break;
			}

			matchedCol = matchedCol + 1;
		}

		return matchedCol;
	}

	public static void main(String[] args) throws IOException {
		System.out.println(TestData.getRow("C:/Users/Vijaya_Bhaskar_Devir/Desktop/Automation/CCA/POC1/FMSTestData.xls", "GroupMaintenance", 1, "FMS_ST_TC_ID_22"));
	}

}
