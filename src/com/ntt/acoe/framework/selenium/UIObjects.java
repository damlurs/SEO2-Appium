package com.ntt.acoe.framework.selenium;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.ntt.acoe.framework.config.Environment;
import com.ntt.acoe.framework.selenium.report.Reporting;

/*
 * @author Santosh Hariprasad (NTT Badge Id: 244583,
 *         Santhosh.Hariprasad@NTTDATA.com)
 * @version 1.0
 * @since 2015-01-01
 */
public class UIObjects {
	static String objectsFile = Environment.get("objects_file");
	static String[] objectSheets;
	static Properties p = getObjects(objectsFile);

	public static void loadSheets(String objectsFile) {
		// System.out.println("Loading sheets from Objects file: " +
		// objectsFile);
		Reporting.report("DONE", "Loading sheets from Objects file: " + objectsFile);
		FileInputStream file = null;
		try {
			file = new FileInputStream(new File(objectsFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		HSSFWorkbook workbook = null;
		try {
			workbook = new HSSFWorkbook(file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		int sheetCount = workbook.getNumberOfSheets();
		objectSheets = new String[sheetCount];
		for (int i = 0; i < sheetCount; i++) {
			objectSheets[i] = workbook.getSheetName(i);
		}

		System.out.println("Sheets from Objects file loaded: " + objectSheets.toString());
	}

	public static Properties getObjects(String configFile) {
		System.out.println("Loading Objects");
		if(p==null){
			p = new Properties();
		}
		
		if(configFile.trim().contains(";")){
			System.out.println("Objects are located in multiple files");
			String[] objectFileNameTokens = configFile.trim().split("\\;");
			for(String s:objectFileNameTokens){
				System.out.println("Loading Objects from file: " + s);
				addObjectsFromFile(s);
			}
		}else{
			System.out.println("Loading Objects from file: " + configFile);
			addObjectsFromFile(configFile);
		}
		
		
		System.out.println(" Total Objects loaded: " + p.size());
		return p;
	}
	
	private static void addObjectsFromFile(String configFile){
		FileInputStream file = null;
		try {
			file = new FileInputStream(new File(configFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		HSSFWorkbook workbook = null;
		try {
			workbook = new HSSFWorkbook(file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		int sheetCount = workbook.getNumberOfSheets();
		System.out.println(" SheetCount: " + sheetCount);
		for (int i = 0; i < sheetCount; i++) {
			HSSFSheet sheet = workbook.getSheetAt(i);
			Iterator<Row> rowIterator = sheet.iterator();
			System.out.println(" Loading objects from File:"+configFile+" and Sheet: " + sheet.getSheetName() + " Rows:");
			int j = 0;
			rowIterator.next();
			while (rowIterator.hasNext()) {
				try {
					// System.out.println(" Object HashCode:
					// "+rowIterator.hashCode());
					Row currentRow = rowIterator.next();
					System.out.print(currentRow.getRowNum() + ",");
					Cell logicalNameCell = null;
					try {
						logicalNameCell = currentRow.getCell(0);
					} catch (Exception e) {
					}
					Cell propertyCell = null;
					try {
						propertyCell = currentRow.getCell(1);
					} catch (Exception e) {
					}
					Cell valueCell = null;
					try {
						valueCell = currentRow.getCell(2);
					} catch (Exception e) {
					}

					if (logicalNameCell == null || propertyCell == null || valueCell == null) {
						Reporting.report("DONE", "Not loading UI Objects from the sheet:" + sheet.getSheetName() + " from Objects file: " + objectsFile + " found null for logical name or property or for value");
					} else {
						p.setProperty(logicalNameCell.getStringCellValue(), propertyCell.getStringCellValue() + "=" + valueCell.getStringCellValue());
					}
					
					//System.out.println("L:"+logicalNameCell.getStringCellValue()+" P:"+propertyCell.getStringCellValue()+" PV:"+ valueCell.getStringCellValue());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}		
	}

	public static void loadObjects(String[] sheetNames) {
		System.out.println("Loading Objects from file: " + UIObjects.objectsFile);
		//Properties p = new Properties();
		FileInputStream file = null;
		try {
			file = new FileInputStream(new File(UIObjects.objectsFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		HSSFWorkbook workbook = null;
		try {
			workbook = new HSSFWorkbook(file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		int sheetCount = workbook.getNumberOfSheets();
		for (int i = 0; i < sheetCount; i++) {
			String currentSheetName = workbook.getSheetName(i).trim();
			HSSFSheet sheet = workbook.getSheetAt(i);
			for (int j = 0; j < sheetNames.length; j++) {
				if (currentSheetName.equalsIgnoreCase(sheetNames[j].trim())) {
					Iterator<Row> rowIterator = sheet.iterator();
					rowIterator.next();
					while (rowIterator.hasNext()) {
						Row currentRow = rowIterator.next();
						Cell logicalNameCell = currentRow.getCell(0);
						Cell propertyCell = currentRow.getCell(1);
						Cell valueCell = currentRow.getCell(2);
						System.out.print("Object Logical name: " + logicalNameCell.getStringCellValue() + "  Property: " + propertyCell.getStringCellValue() + "  Property Value: "
								+ valueCell.getStringCellValue());
						p.setProperty(logicalNameCell.getStringCellValue(), propertyCell.getStringCellValue() + "=" + valueCell.getStringCellValue());
					}
					break;
				}
			}
		}

		System.out.println(" Total Objects loaded: " + p.size());
		UIObjects.p = p;
	}

	public static void main(String[] args) {
	}

}
