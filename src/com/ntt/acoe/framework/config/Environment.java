package com.ntt.acoe.framework.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

/**
 *
  * @author Santosh Hariprasad (NTT Badge Id: 244583,
 *         Santhosh.Hariprasad@NTTDATA.com)
 * @version 1.0
 * @since 2015-01-01
 */
public class Environment {
	static Properties props = new Properties();

	public static String get(String key) {
		return Environment.props.getProperty(key.toLowerCase());
	}

	public static void put(String key, String val) {
		props.put(key.toLowerCase(), val);
	}

	public static void load(String envExcelFile, String sheetName) {
		props.put("home", System.getProperty("user.dir"));
		FileInputStream file = null;
		HSSFWorkbook workbook = null;
		HSSFSheet sheet = null;
		try {
			file = new FileInputStream(new File(envExcelFile));
			try {
				workbook = new HSSFWorkbook(file);
				sheet = workbook.getSheet(sheetName);
			} catch (IOException e) {
				System.out.println("Environment file not found, please check: " + envExcelFile);
				System.out.println("Stopping execution ...");
				System.exit(-1);
			}
		} catch (FileNotFoundException e) {
			System.out.println("Environment file not found, please check: " + envExcelFile);
			System.out.println("Stopping execution ...");
			System.exit(-1);
		}

		Iterator<Row> rowIterator = null;
		try {
			rowIterator = sheet.iterator();
		} catch (Exception e) {
			System.out.println("Environment file not found, please check: " + envExcelFile);
			System.out.println("Stopping execution ...");
			System.exit(-1);
		}

		while (rowIterator.hasNext()) {
			String prop = "";
			String propVal = "";

			Row currentRow = rowIterator.next();

			Cell propertyCell;
			try {
				propertyCell = currentRow.getCell(0);
				prop = propertyCell.getStringCellValue().toLowerCase().trim();

				if (!prop.equalsIgnoreCase("")) {
					String currentCellValue = "";

					try {
						Cell valueCell = currentRow.getCell(1);
						if (valueCell.getCellTypeEnum() == CellType.NUMERIC) {
							currentCellValue = String.valueOf(valueCell.getNumericCellValue());
						} else if (valueCell.getCellTypeEnum() == CellType.STRING) {
							currentCellValue = valueCell.getStringCellValue();
						} else if (valueCell.getCellTypeEnum() == CellType.BOOLEAN) {
							currentCellValue = String.valueOf(valueCell.getBooleanCellValue());
						} else if (valueCell.getCellTypeEnum() == CellType.BLANK) {
							currentCellValue = "";
						} else if (valueCell.getCellTypeEnum() == CellType.FORMULA) {
							currentCellValue = String.valueOf(valueCell.getCellFormula());
						} else if (valueCell.getCellTypeEnum() == CellType.ERROR) {
							currentCellValue = "Error reading data";
						}						
					} catch (Exception e) {
						e.printStackTrace();
					}

					propVal = currentCellValue.trim();					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (!prop.equalsIgnoreCase("")) {
				props.put(prop.trim(), propVal.trim());
			}			
		}
	}

	public void setupPaths() {
	}

	public static Properties getMatchedProps(String key){
		Properties matchedProps = new Properties();
		Enumeration<?> e = props.propertyNames();

	    while (e.hasMoreElements()) {
	      String key1 = (String) e.nextElement();
	      System.out.println(key1 + " -- " + props.getProperty(key1));
	      if(key1.startsWith(key)){
	    	  matchedProps.setProperty(key1, props.getProperty(key1));
	    	  System.out.println(key1 + " -- matched -- " + props.getProperty(key1));
	      }
	    }
	    
		return matchedProps;
	}
	
	public static void main(String[] args) {
		Environment.load("C:/automation/fmsadmin/config/SmokeTestSuite.xls", "Configuration");
		Set<Object> keys = Environment.props.keySet();
		for (Object k : keys) {
			String key = (String) k;
			System.out.println(key + ": " + Environment.props.getProperty(key));
		}

		System.out.println(Environment.get("HOME"));
	}

}
