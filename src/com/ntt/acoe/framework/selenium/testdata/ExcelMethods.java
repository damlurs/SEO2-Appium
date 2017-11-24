package com.ntt.acoe.framework.selenium.testdata;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ntt.acoe.framework.config.Configuration;
import com.ntt.acoe.framework.config.Environment;

/*
 * @author Santosh Hariprasad (NTT Badge Id: 244583,
 *         Santhosh.Hariprasad@NTTDATA.com)
 * @version 1.0
 * @since 2015-01-01
 */

public class ExcelMethods {

	/**
	 * To get excel row data into ArrayList.
	 * 
	 * @param fileName
	 * @param sheetName
	 * @param rowNum
	 */
	public static ArrayList<String> getRowData(String fileName, String sheetName, int rowNum) {
		rowNum = rowNum - 1;
		try {
			String path = fileName;
			// System.out.println("ExcelMethods.getRowData: getting row data
			// from file:"+fileName);
			ArrayList<String> values = new ArrayList<String>();
			InputStream ExcelFileToRead = new FileInputStream(path);
			XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);
			// System.out.println("ExcelMethods.getRowData: getting sheet from
			// file:-->"+sheetName+"<---- ...");
			XSSFSheet sheet = wb.getSheet(sheetName);
			// System.out.println("ExcelMethods.getRowData: getting sheet from
			// file:-->"+sheetName+"<---- ...completed");
			// XSSFRow row = sheet.getRow(rowNum); // Updated by Vijay
			// System.out.println("ExcelMethods.getRowData: getting header row
			// from file :-->"+fileName + "<-- and sheet -->"+ sheetName+"<----
			// Row:"+rowNum+ "...");
			XSSFRow row = sheet.getRow(rowNum);
			// System.out.println("ExcelMethods.getRowData: getting header row
			// from file :-->"+fileName + "<-- and sheet -->"+ sheetName+"<----
			// Row:"+rowNum+ "...completed");

			for (int i = 0; i < row.getLastCellNum(); i++) {
				XSSFCell cell = row.getCell(i);
				int type = cell.getCellType();
				if (type == Cell.CELL_TYPE_FORMULA) {
					cell.getCachedFormulaResultType();
					// System.out.println("Formulae:"+cell.getStringCellValue().trim());
					values.add(cell.getStringCellValue().trim());
				} else if (type == XSSFCell.CELL_TYPE_STRING) {
					// System.out.println("String:"+cell.getStringCellValue().trim());
					// System.out.println("ExcelMethods.getRowData: getting
					// header row value from file :-->"+fileName + "<-- and
					// sheet -->"+ sheetName+"<---- "+ " String Cell(" + i + ")
					// value ("+cell.getStringCellValue().trim()+" ...");
					values.add(cell.getStringCellValue().trim());
				} else if (type == XSSFCell.CELL_TYPE_NUMERIC) {
					// System.out.println("Numeric:"+cell.toString().trim());
					// System.out.println("ExcelMethods.getRowData: getting
					// header row value from file :-->"+fileName + "<-- and
					// sheet -->"+ sheetName+"<---- "+ " Numeric Cell(" + i + ")
					// value ("+cell.getStringCellValue().trim()+" ...");
					values.add(cell.toString().trim());
				} else if (type == Cell.CELL_TYPE_BLANK) {
					// System.out.println("Null:");
					// System.out.println("ExcelMethods.getRowData: getting
					// header row value from file :-->"+fileName + "<-- and
					// sheet -->"+ sheetName+"<---- "+ " Blank Cell(" + i + ")
					// value ( )");
					values.add(" ");
				}
			}
			return values;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ArrayList<String> getHeaderData(String fileName, String sheetName, int rowNum) {
		// System.out.println("ExcelMethods.getHeaderData: from file
		// :-->"+fileName + "<-- and sheet -->"+ sheetName+"<---- and
		// rowNum:"+rowNum);
		try {
			String path = fileName;
			ArrayList<String> values = new ArrayList<String>();
			InputStream ExcelFileToRead = new FileInputStream(path);
			XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);
			// System.out.println("ExcelMethods.getHeaderData: getting sheet
			// from file:-->"+sheetName+"<---- ...");
			XSSFSheet sheet = wb.getSheet(sheetName);
			// System.out.println("ExcelMethods.getHeaderData: getting sheet
			// from file:-->"+sheetName+"<---- ... completed");
			// System.out.println("ExcelMethods.getHeaderData: getting header
			// row from file :-->"+fileName + "<-- and sheet -->"+
			// sheetName+"<---- Row:"+rowNum+ "...");
			// System.out.println("Sheetname:"+sheet.getSheetName());
			XSSFRow row = sheet.getRow(rowNum);
			// System.out.println("ExcelMethods.getHeaderData: getting header
			// row from file :-->"+fileName + "<-- and sheet -->"+
			// sheetName+"<---- Row:"+rowNum+ "...completed");

			for (int i = 0; i < row.getLastCellNum(); i++) {
				XSSFCell cell = row.getCell(i);
				int type = cell.getCellType();
				if (type == Cell.CELL_TYPE_FORMULA) {
					cell.getCachedFormulaResultType();
					// System.out.println("Formulae:"+cell.getStringCellValue().trim());
					values.add(cell.getStringCellValue().trim());
				} else if (type == XSSFCell.CELL_TYPE_STRING) {
					// System.out.println("ExcelMethods.getHeaderData: getting
					// header row value from file :-->"+fileName + "<-- and
					// sheet -->"+ sheetName+"<---- "+ " String Cell(" + i + ")
					// value ("+cell.getStringCellValue().trim()+" ...");
					// System.out.println("String:"+cell.getStringCellValue().trim());
					values.add(cell.getStringCellValue().trim());
				} else if (type == XSSFCell.CELL_TYPE_NUMERIC) {
					// System.out.println("ExcelMethods.getHeaderData: getting
					// header row value from file :-->"+fileName + "<-- and
					// sheet -->"+ sheetName+"<---- "+ " Numeric Cell(" + i + ")
					// value ("+cell.getStringCellValue().trim()+" ...");
					// System.out.println("Numeric:"+cell.toString().trim());
					values.add(cell.toString().trim());
				} else if (type == Cell.CELL_TYPE_BLANK) {
					// System.out.println("Null:");
					// System.out.println("ExcelMethods.getHeaderData: getting
					// header row value from file :-->"+fileName + "<-- and
					// sheet -->"+ sheetName+"<---- "+ " Blank Cell(" + i + ")
					// value ("+cell.getStringCellValue().trim()+" ...");
					values.add(" ");
				}
			}
			return values;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * To get the count of numbers of rows in a excel sheet.
	 * 
	 * @param fileName
	 * @param sheetName
	 * 
	 */
	public static int getNumberOfRows(String fileName, String sheetName) {
		try {
			String path = fileName;
			InputStream ExcelFileToRead = new FileInputStream(path);
			XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);
			XSSFSheet sheet = wb.getSheet(sheetName);
			return sheet.getLastRowNum();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * To get work book by sheet name.
	 * 
	 * @param XSSFWorkbook
	 * @param sheetName
	 * 
	 */
	public static XSSFSheet getWorkbookSheetByName(XSSFWorkbook wb, String sheetName) {
		XSSFSheet sheet = null;
		int size = wb.getNumberOfSheets();
		for (int i = 0; i < size; i++) {
			String name = wb.getSheetName(i).trim();
			if (name.equals(sheetName)) {
				sheet = wb.getSheetAt(i);
				break;
			}
		}
		return sheet;
	}

	/**
	 * To get column value of specific row.
	 * 
	 * @param fileName
	 * @param columnValue
	 * @param sheetName
	 * @param rowNum
	 * 
	 */
	public static String getColumn(String fileName, String colVal, String sheetName, int rowNum) {
		ArrayList<String> headerRow = getHeaderData(fileName, sheetName, 0);
		for (int j = 0; j < headerRow.size(); j++) {
			System.out.println(
					"ExcelMethods.getColumn: getting header row value from file :-->" + fileName + "<-- and sheet -->" + sheetName + "<---- " + " Col value (" + colVal + " Row:" + rowNum + "...");
			if (headerRow.get(j).equals(colVal)) {
				ArrayList<String> rowVal = getRowData(fileName, sheetName, rowNum);
				return rowVal.get(j).toString();
			}
		}
		return null;
	}

	/**
	 * To get excel row number of first occurrence of a Group ID/Rate Def ID.
	 * 
	 * @param fileName
	 * @param sheetName
	 * @param cellVal
	 * @return int
	 */
	public static int getRowNum(String fileName, String sheetName, String cellVal) {
		System.out.println("ExcelMethods.getRowNum: from file :-->" + fileName + "<-- and sheet -->" + sheetName + "<---- and cellVal:" + cellVal);
		try {
			String path = fileName;
			InputStream ExcelFileToRead = new FileInputStream(path);
			XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);
			XSSFSheet sheet = wb.getSheet(sheetName);
			for (Row row : sheet) {
				Cell cell = row.getCell(0);
				int type = cell.getCellType();
				if (type == Cell.CELL_TYPE_FORMULA) {
					cell.getCachedFormulaResultType();
					if (cell.getStringCellValue().trim().equals(cellVal)) {
						// System.out.println("Formulae:"+row.getRowNum());
						return row.getRowNum();
					}
					/*
					 * else { logError("Row not found in datapool"); }
					 */
				} else if (type == Cell.CELL_TYPE_STRING) {
					if (cell.getStringCellValue().trim().equals(cellVal)) {
						// System.out.println("Normal:"+row.getRowNum());
						System.out.println("ExcelMethods.getRowNum: getting header row value from file :-->" + fileName + "<-- and sheet -->" + sheetName + "<---- " + " String Cell(0) value ("
								+ cell.getStringCellValue().trim() + " Row:" + row.getRowNum() + "...");
						return row.getRowNum();
					} /*
						 * else { logError("Row not found in datapool"); }
						 */
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * To update a cell of a row number.
	 * 
	 * @Param FileName
	 * @param sheetName
	 * @param rowNum
	 */
	public static void updateColumnData(String fileName, String sheetName, int rowNum, String colVal, String colName) {
		try {
			String path = fileName;
			InputStream ExcelFileToRead = new FileInputStream(path);
			XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);
			XSSFSheet sheet = wb.getSheet(sheetName);
			Cell cell = null;
			// Update the value of cell
			ArrayList<String> headerRow = getRowData(fileName, sheetName, 0);
			for (int j = 0; j < headerRow.size(); j++) {
				if (headerRow.get(j).equals(colName)) {
					cell = sheet.getRow(rowNum).getCell(j);
					cell.setCellValue(colVal);
				}
			}
			ExcelFileToRead.close();
			FileOutputStream outFile = new FileOutputStream(path);
			wb.write(outFile);
			outFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static int getRow(String testDataFile, String sheetName, int testCaseIdColNo, String testCaseId) throws IOException {
		return TestCase.getRow(testDataFile, sheetName, testCaseIdColNo, testCaseId);
	}

	public static String[] getData(String fileName, String sheet, String testCaseId) throws IOException {
		if (!fileName.contains("/") && !fileName.contains("\\")) {
			// fileName = Configuration.getValue("TEST_DATA_PATH")+"/"+fileName;
			fileName = Environment.get("test_data_path") + "/" + fileName;
		}
		int rowNo = getRow(fileName, sheet, 1, testCaseId);
		ArrayList<String> rowVal = getRowData(fileName, sheet, rowNo);
		String[] rowValues = new String[rowVal.size()];
		for (int i = 0; i < rowVal.size(); i++) {
			rowValues[i] = rowVal.get(i);
		}
		return rowValues;
	}
}