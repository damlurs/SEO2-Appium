package com.ntt.acoe.framework.selenium.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ntt.acoe.framework.config.Environment;
import com.ntt.acoe.framework.loggers.ScriptLogger;
import com.ntt.acoe.framework.selenium.verify.Assert;

/* 
 * @author Santosh Hariprasad (NTT Badge Id: 244583,
 *         Santhosh.Hariprasad@NTTDATA.com)
 * @version 1.0
 * @since 2015-01-01
 */
public class ExcelReportUpdate {
	static String reportFilename = "";

	public static void updateResult(String configFile, String testCaseName, String status) throws IOException {
		FileInputStream file = new FileInputStream(new File(configFile));
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		int sheetCount = workbook.getNumberOfSheets();
		for (int i = 1; i < sheetCount; i++) {
			XSSFSheet sheet = workbook.getSheetAt(i);
			Iterator<Row> rowIterator = sheet.iterator();

			rowIterator.next();
			while (rowIterator.hasNext()) {
				Row currentRow = rowIterator.next();

				String testCase = "";
				Cell testcaseCell = currentRow.getCell(0);
				if (testcaseCell == null) {
					break;
				}

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

				if (testCase.trim().equalsIgnoreCase(testCaseName)) {
					Cell resultCell = currentRow.getCell(1);
					resultCell.setCellValue(status);
					DateFormat df = new SimpleDateFormat("HH:mm:ss");
					Date dt = new Date();
					String currentTime = df.format(dt);
					Cell endTimeCell = currentRow.getCell(10);
					endTimeCell.setCellValue(currentTime);
					break;
				}
			}
		}

		XSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);
		file.close();

		FileOutputStream fos = new FileOutputStream(new File(configFile));
		workbook.write(fos);
		workbook.close();
		fos.close();
	}

	public static void updateStartTime() throws IOException, InterruptedException {
		try {
			String testCaseName = Environment.get("TestScript");
			String reportFile = Environment.get("report_excel_filename") + "-" + Environment.get("execution_environment") + "-" + Environment.get("execution_build") + ".xlsx";

			Thread.sleep(20000);
			if ((!reportFile.contains("/")) && (!reportFile.contains("\\"))) {
				ExcelReportUpdate.reportFilename = Environment.get("report_path") + "/" + reportFile;
			} else {
				ExcelReportUpdate.reportFilename = reportFile;
			}

			reportFile = ExcelReportUpdate.reportFilename;

			FileInputStream file = new FileInputStream(new File(reportFile));
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			int sheetCount = workbook.getNumberOfSheets();

			for (int i = 1; i < sheetCount; i++) {
				XSSFSheet sheet = workbook.getSheetAt(i);
				Iterator<Row> rowIterator = sheet.iterator();
				rowIterator.next();
				while (rowIterator.hasNext()) {
					Row currentRow = rowIterator.next();

					String testCase = "";
					Cell testcaseCell = currentRow.getCell(0);
					if (testcaseCell == null) {
						break;
					}

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

					DateFormat df = new SimpleDateFormat("HH:mm:ss");
					Date dt = new Date();
					String currentTime = df.format(dt);
					// System.out.println("currentTime"+currentTime+"
					// "+testCaseName.trim()+" "+testCase);
					if (testCase.trim().equalsIgnoreCase(testCaseName)) {
						Cell startTimeCell = currentRow.getCell(9);
						startTimeCell.setCellValue(currentTime);
						break;
					}
				}
			}

			file.close();

			FileOutputStream fos = new FileOutputStream(new File(reportFile));
			workbook.write(fos);
			workbook.close();
			fos.close();			
		} catch (FileNotFoundException e) {
			System.out.println("Exception occured while updating excel report. Excel report file is not found:"+ExcelReportUpdate.reportFilename+"  Please check the the environment is properly configured for these: execution_environment , execution_build, report_excel_template, report_excel_filename");
			ScriptLogger.log.info("Exception occured while updating excel report. Excel report file is not found:"+ExcelReportUpdate.reportFilename+"  Please check the the environment is properly configured for these: execution_environment , execution_build, report_excel_template, report_excel_filename");
		}catch(Exception e){
			System.out.println("Exception occured while updating excel report. Excel report file:"+ExcelReportUpdate.reportFilename+"  Please check the the environment is properly configured for these: execution_environment , execution_build, report_excel_template, report_excel_filename");
			ScriptLogger.log.info("Exception occured while updating excel report. Excel report file:"+ExcelReportUpdate.reportFilename+"  Please check the the environment is properly configured for these: execution_environment , execution_build, report_excel_template, report_excel_filename");			
		}
	}

}
