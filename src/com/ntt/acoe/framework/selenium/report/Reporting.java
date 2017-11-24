package com.ntt.acoe.framework.selenium.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ntt.acoe.framework.config.Environment;
import com.ntt.acoe.framework.loggers.ScriptLogger;
import com.ntt.acoe.framework.run.TestRunner;
import com.ntt.acoe.framework.selenium.verify.Assert;

/*
 * @author Santosh Hariprasad (NTT Badge Id: 244583,
 *         Santhosh.Hariprasad@NTTDATA.com)
 * @version 1.0
 * @since 2015-01-01
 */
public class Reporting {
	public static ArrayList<String> stepStatus = new ArrayList<String>();
	public static ArrayList<String> methodStatus = new ArrayList<String>();
	public static String scriptStatus = "unknown";
	public static String testCase = "";
	public static int currentTestScriptNo = 0;
	public static int currentTestCaseNo = 0;
	public static int passedTestCases = 0;
	public static Date scriptStartTime = new Date();
	public static Date scriptEndTime = new Date();
	public static Date reportStartTime = new Date();
	public static Date reportEndTime = new Date();	

	public static void setTestCase(String testCase) {
		Reporting.testCase = testCase;
		scriptStatus = "";
	}

	public static ArrayList<String> testCaseResult = new ArrayList<String>();

	public static void report(String status, String description) {
		stepStatus.add(status);
		ScriptLogger.log.info(status + " " + description);
		//Reporting.report(status, description);
	}

	public static void reportResult(String status, String description) {
		//ConfigData.
		System.out.println(Environment.get("TestScript") + "-" + Environment.get("TestMethod") + " : " + status + " - " + description);
		if (status.trim().equalsIgnoreCase("WARN") || status.trim().equalsIgnoreCase("WARNING")) {
			try {
				if (Environment.get("show_warning_as").trim() != "") {
					status = Environment.get("show_warning_as").trim().toUpperCase();
				} else {
					status = "WARNING";
				}
			} catch (Exception e) {
				status = "WARNING";
				ScriptLogger.log.info("WARN " + "Environment variable show_warning_as is not set properly. taking default as WARNING");
			}
		}

		stepStatus.add(status);
		ScriptLogger.log.info("ASSERTION " + testCase + " : " + status + " " + description);
		testCaseResult.add("TC : " + testCase + " : " + status + " : " + description);
		// Reporting.report(status, description);
		updateTestCaseReport(status, description);
		
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date dt = new Date();
		String d = df.format(dt);
		String screenshot = Environment.get("report_path") + "/screenshots/" + Environment.get("TestScript") + "_" + Reporting.currentTestCaseNo + ".png";
		XMLReporting.addStepElement(Environment.get("report_path") + "/TestScriptReport_" + TestRunner.timeStamp + ".xml", 
				Environment.get("TestScript"), Environment.get("TestMethod"), status, d, description, screenshot);
	}

	public static void startMethod(String method) {
		stepStatus = new ArrayList<String>();
		methodStatus = new ArrayList<String>();
		ScriptLogger.log.info("DONE " + method + " execution started");
		// Reporting.report("DONE", "Method: "+method+ " execution completed");
	}

	public static void closeMethod(String method) {
		ScriptLogger.log.info("DONE " + method + " execution completed");
		// Update method execution status
		String mStatus = "PASS";
		for (String s : stepStatus) {
			ScriptLogger.log.info(method + " - Step status:" + s);
		}
		ScriptLogger.log.info(method + " ------------");

		for (String s : stepStatus) {
			ScriptLogger.log.info(method + " - Step status:" + s);
			if (s.trim().equalsIgnoreCase("FAIL")) {
				mStatus = "FAIL";
				break;
			} else if (s.trim().equalsIgnoreCase("ERROR")) {
				// mStatus = "WARNING"; //Errors are showing as warning in test
				// script report, so making as fail
				mStatus = "FAIL";
				break;
			} else if (s.trim().equalsIgnoreCase("WARN") || s.trim().equalsIgnoreCase("WARNING")) {
				// mStatus = "WARNING";
				mStatus = "FAIL";
				break;
			} else if (s.trim().equalsIgnoreCase("") || s.trim().equalsIgnoreCase("UNKNOWN")) {
			} else if (s.trim().equalsIgnoreCase("UNKNOWN")) {
				mStatus = "FAIL";
			} else if (s.trim().equalsIgnoreCase("DONE")) {
				mStatus = "PASS";
			} else if (s.trim().equalsIgnoreCase("PASS")) {
				mStatus = "PASS";
			} else if (s.trim().equalsIgnoreCase("DONE")) {
				mStatus = "PASS";
			} else {
				mStatus = "FAIL";
				break;
			}
		}

		methodStatus.add(mStatus);
		ScriptLogger.log.info(mStatus + " " + method + " execution completed");
		// Reporting.report(mStatus, "Method: "+method+ " execution completed");
	}

	public static void startScript(String testScriptId) {
		scriptStatus = "UNKNOWN";
		ScriptLogger.log.info("DONE " + testScriptId + " execution started.");
		Reporting.scriptStartTime = new Date();
		// Reporting.report("DONE",
		// "Script "+testScriptId+" execution started ...");
		XMLReporting.addScriptElement(Environment.get("report_path") + "/TestScriptReport_" + TestRunner.timeStamp + ".xml", testScriptId, "");
	}

	public static void closeScript(String testScript) {
		// Update script execution status
		String scriptStatus = "PASS";
		for (String s : methodStatus) {
			if (s.trim().equalsIgnoreCase("FAIL")) {
				scriptStatus = "FAIL";
				break;
			} else if (s.trim().equalsIgnoreCase("ERROR")) {
				scriptStatus = "WARNING";
				break;
			} else if (s.trim().equalsIgnoreCase("WARN") || s.trim().equalsIgnoreCase("WARNING")) {
				scriptStatus = "WARNING";
			} else if (s.trim().equalsIgnoreCase("") || s.trim().equalsIgnoreCase("UNKNOWN")) {
				scriptStatus = "PASS";
			}
		}

		if(scriptStatus.trim().equalsIgnoreCase("PASS")){
			Reporting.passedTestCases = Reporting.passedTestCases + 1;
		}
		
		ScriptLogger.log.info(scriptStatus + " " + testScript + " execution completed");
		Reporting.scriptEndTime = new Date();
		try {
			// saveTestScriptReport(Configuration.REPORT_PATH+"/Status.txt",scriptStatus+"
			// , "+testScript);
			XMLReporting.updateScriptStatus(Environment.get("report_path") + "/TestScriptReport_" + TestRunner.timeStamp + ".xml", Environment.get("TestScript"), scriptStatus);
			updateTestScriptReport(Environment.get("REPORT_FILE"), scriptStatus + "," + testScript);

			try {
				updateTestScriptExcelReport(scriptStatus);
			} catch (Exception e) {
				// e.printStackTrace();
				System.out.println("There is an issue while updating the script status to excel report. Please check file:" + Environment.get("REPORT_FILE"));
			}
		} catch (IOException e) {
			// e.printStackTrace();
			System.out.println("There is an issue while updating the script status to excel report. Please check file:" + Environment.get("REPORT_FILE"));
		}
		// Reporting.report(scriptStatus, "Script: "+testScript+
		// " execution completed");
	}

	public static void closeReport() {		
		XMLReporting.updateReportElementEndTime(Environment.get("report_path") + "/TestScriptReport_" + TestRunner.timeStamp + ".xml", Reporting.currentTestCaseNo, Reporting.passedTestCases);
		closeTestScriptReport(Environment.get("REPORT_FILE"));		
	}

	public static void closeTestScriptReport(String fileName){
		Reporting.reportEndTime = new Date();
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		String ed = df.format(Reporting.reportEndTime);
		long reportDuration = (Reporting.reportEndTime.getTime()-Reporting.reportStartTime.getTime()) / (60 * 1000) % 60;
		
		try{
			String fileContent = new String(Files.readAllBytes(Paths.get(Environment.get("report_path") + "/TestScriptReport_" + TestRunner.timeStamp + ".html")), StandardCharsets.UTF_8);
			String passSearch = "<td class=\"pass\">PASS</td>";
			int passLastIndex = 0;
			int passCount = 0;

			while(passLastIndex != -1){
				passLastIndex = fileContent.indexOf(passSearch, passLastIndex);
			    if(passLastIndex != -1){
			    	passCount ++;
			        passLastIndex += fileContent.length();
			    }
			}
			int total = Reporting.currentTestScriptNo;
			float passPercentage = (float)passCount/(float)total;
			int passPercentageInt = (int)(passPercentage*100);
			System.out.println("Total:"+total);
			System.out.println("Passed:"+passCount+ "  "+passPercentageInt+"%");

			String content = "<tr><td colspan=\"4\">End Time: " + ed + "</td></tr>";
			content = content+"<tr><td colspan=\"4\">Duration:"+reportDuration+" min. Total Scripts:"+total+", Passed:"+passCount+" ("+passPercentageInt+"%)</td></tr>";
			fileContent = fileContent.replaceAll("<tr><td colspan=\"3\" style=\"text-align:center\">Generated by Selenium O2</td></tr>",
					content + "\n" + "<tr><td colspan=\"3\" style=\"text-align:center\">Generated by Selenium O2</td></tr>");
			Files.write(Paths.get(Environment.get("report_path") + "/TestScriptReport_" + TestRunner.timeStamp + ".html"), fileContent.getBytes(StandardCharsets.UTF_8));
		}catch(Exception e){
			Assert.error(e, "Exception occured while closing the report in html format");
		}
	}

	
	public static void updateTestScriptReport(String fileName, String content) throws IOException {
		String styleClass = "";
		String status = content.split("\\,", -1)[0];
		if (status.trim().equalsIgnoreCase("pass")) {
			styleClass = "pass";
		} else if (status.trim().equalsIgnoreCase("fail")) {
			styleClass = "fail";
		}
		if (status.trim().equalsIgnoreCase("warn") || status.trim().equalsIgnoreCase("warning")) {
			styleClass = "warning";
			try {
				if (!Environment.get("show_warning_as").trim().equalsIgnoreCase("")) {
					status = Environment.get("show_warning_as").trim().toUpperCase();
					styleClass = Environment.get("show_warning_as");
				}
			} catch (Exception e) {
			}
		}

		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
//		Date dt = new Date();
		String sd = df.format(Reporting.scriptStartTime);
		String ed = df.format(Reporting.scriptEndTime);
		long scriptDuration = (Reporting.scriptEndTime.getTime()-Reporting.scriptStartTime.getTime()) / (60 * 1000) % 60;

		Reporting.currentTestScriptNo = Reporting.currentTestScriptNo + 1;
		content = "<tr><td class=\"" + styleClass + "\">" + Reporting.currentTestScriptNo + " [" + sd + " to "+ed+" ("+scriptDuration+" min) ]</td><td class=\"" + styleClass + "\">" + content.split("\\,", -1)[1] + "</td><td class=\""
				+ styleClass + "\">" + status + "</td></tr>";
		String fileContent = new String(Files.readAllBytes(Paths.get(Environment.get("report_path") + "/TestScriptReport_" + TestRunner.timeStamp + ".html")), StandardCharsets.UTF_8);
		fileContent = fileContent.replaceAll("<tr><td colspan=\"3\" style=\"text-align:center\">Generated by Selenium O2</td></tr>",
				content + "\n" + "<tr><td colspan=\"3\" style=\"text-align:center\">Generated by Selenium O2</td></tr>");
		Files.write(Paths.get(Environment.get("report_path") + "/TestScriptReport_" + TestRunner.timeStamp + ".html"), fileContent.getBytes(StandardCharsets.UTF_8));
	}

	public static void updateTestScriptExcelReport(String content) throws IOException {
		String status = content.split("\\,", -1)[0];
		if (status.trim().equalsIgnoreCase("pass")) {
			status = "Pass";
		} else if (status.trim().equalsIgnoreCase("fail")) {
			status = "Fail";
		}
		if (status.trim().equalsIgnoreCase("warn") || status.trim().equalsIgnoreCase("warning")) {
			status = "Warning";
			try {
				if (!Environment.get("show_warning_as").trim().equalsIgnoreCase("")) {
					status = Environment.get("show_warning_as").trim().toUpperCase();
				}
			} catch (Exception e) {
			}
		}

		String reportFile = Environment.get("report_excel_filename") + "-" + Environment.get("execution_environment") + "-" + Environment.get("execution_build") + ".xls";
		ExcelReportUpdate.updateResult(Environment.get("report_path") + "/" + reportFile, Environment.get("TestScript"), status);
	}

	public static void updateTestCaseReport(String status, String description) {
		description = description.replace("[$]", "USD");
		String styleClass = "";
		if (status.trim().equalsIgnoreCase("pass")) {
			styleClass = "pass";
		} else if (status.trim().equalsIgnoreCase("fail")) {
			styleClass = "fail";
		}
		if (status.trim().equalsIgnoreCase("warn") || status.trim().equalsIgnoreCase("warning")) {
			styleClass = "warning";
			try {
				if (!Environment.get("show_warning_as").trim().equalsIgnoreCase("")) {
					status = Environment.get("show_warning_as").trim().toUpperCase();
					styleClass = Environment.get("show_warning_as");
				}
			} catch (Exception e) {
			}
		}

		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date dt = new Date();
		String d = df.format(dt);

		Reporting.currentTestCaseNo = Reporting.currentTestCaseNo + 1;
		String screenshot = Environment.get("report_path") + "/screenshots/" + Environment.get("TestScript") + "_" + Reporting.currentTestCaseNo + ".png";
		Path testCaseReportFile = Paths.get(Environment.get("report_path") + "/TestCaseReport_" + TestRunner.timeStamp + ".html");
		String content = "<tr><td class=\"" + styleClass + "\">" + Reporting.currentTestCaseNo + " [" + d + "]</td><td class=\"" + styleClass + "\"><a href='file:///" + screenshot + "'>"
				+ Environment.get("TestScript") + "-" + Environment.get("TestMethod") + "</a></td><td class=\"" + styleClass + "\">" + status + "</td><td class=\"" + styleClass + "\">" + description
				+ "</td></tr>";
		String fileContent;

		try {
			fileContent = new String(Files.readAllBytes(testCaseReportFile), StandardCharsets.UTF_8);
			fileContent = fileContent.replaceAll("<tr><td colspan=\"4\" style=\"text-align:center\">Generated by Selenium O2</td></tr>",
					content + "\n" + "<tr><td colspan=\"4\" style=\"text-align:center\">Generated by Selenium O2</td></tr>");
			Files.write(testCaseReportFile, fileContent.getBytes(StandardCharsets.UTF_8));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void createTestScriptReport() throws IOException {
		File f = new File(Environment.get("report_path") + "/TestScriptReport_" + TestRunner.timeStamp + ".html");
		if (!f.exists()) {
			f.createNewFile();
		}

		Reporting.reportStartTime = new Date();
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
//		Date dt = new Date();
		String d = df.format(Reporting.reportStartTime);

		String content = "";
		content = content + "<html><head><title>Test Script Report</title>";
		content = content + "<style>";
		content = content + "body {background-color: #EEEEEE; font-family:verdana;}";
		content = content + "#report table{width:95%; margin-left:2%; border-width:1px; border-style: solid; border-collapse: collapse; colspan: 0px;}";
		content = content + "#report table caption{font-size: 30px; color: black; }";
		content = content + "#report table th{ border-width:1px; border-style: solid; border-color: white; color: white; background-color: #3A5FCD; padding: 2px; font-size: 80%;}";
		content = content + "#report table td{ border-width:1px; border-style: solid; border-color: white; background-color: #F4F4F4;  padding: 2px; font-size: 80%;}";
		content = content + "#report table td.fail{ border-width:1px; border-style: solid; border-color: white;  color:red;  padding: 2px; font-size: 80%;}";
		content = content + "#report table td.pass{ border-width:1px; border-style: solid; border-color: white;  color:black; font-weight:normal; padding: 2px; font-size: 80%;}";
		content = content + "#report table td.warning{ border-width:1px; border-style: solid; border-color: white; color: orange; font-weight:normal; padding: 2px; font-size: 80%;}";
		content = content + "</style>";
		content = content + "</head>";
		content = content + "<body>";
		content = content + "<div id=\"report\">";
		content = content + "<table>";
		content = content + "<caption>Test Scripts Report</caption>";
		content = content + "<tr><td colspan=\"3\">Start Time: " + d + "</td></tr>";
		content = content + "<tr><th>S.No</th><th>Script Name</th><th>Status</th></tr>";
		content = content + "<tr><td colspan=\"3\" style=\"text-align:center\">Generated by Selenium O2</td></tr>";
		content = content + "</table><br/><br/>";
		content = content + "</div>";
		content = content + "</body>";
		content = content + "</html>";

		Files.write(Paths.get(Environment.get("report_path") + "/TestScriptReport_" + TestRunner.timeStamp + ".html"), content.getBytes(StandardCharsets.UTF_8));
	}

	public static void createTestScriptExcelReport() throws IOException {

		try {
			String src = "";
			String templateFile = "";
			if (Environment.get("report_excel_template").trim().contains("/")) {
				String[] tokens = Environment.get("report_excel_template").split("/", -1);
				templateFile = tokens[tokens.length - 1];
				src = Environment.get("report_excel_template");
			} else if (Environment.get("report_excel_template").trim().contains("\\")) {
				String[] tokens = Environment.get("report_excel_template").split("\\", -1);
				templateFile = tokens[tokens.length - 1];
				src = Environment.get("report_excel_template");
			} else {
				templateFile = Environment.get("report_excel_template");
				src = Environment.get("report_path") + "/" + Environment.get("report_excel_template");
			}

			String dest = Environment.get("report_path") + "/" + Environment.get("report_excel_filename") + "-" + Environment.get("execution_environment") + "-" + Environment.get("execution_build")
					+ ".xls";			

			File f = new File(dest);

			if (!f.exists()) {
				FileChannel sourceChannel = null;
				FileChannel destChannel = null;
				try {
					sourceChannel = new FileInputStream(src).getChannel();
					destChannel = new FileOutputStream(dest).getChannel();
					destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());

				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("Exception occured while updating excel report. Excel report file is not found:"+ExcelReportUpdate.reportFilename+"  Please check the the environment is properly configured for these: execution_environment , execution_build, report_excel_template, report_excel_filename");
					ScriptLogger.log.info("Exception occured while updating excel report. Excel report file is not found:"+ExcelReportUpdate.reportFilename+"  Please check the the environment is properly configured for these: execution_environment , execution_build, report_excel_template, report_excel_filename");
				} finally {
					sourceChannel.close();
					destChannel.close();
				}
				updateTestScriptExcelReportBuild(f);
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("Exception occured while updating excel report. Excel report file is not found:"+ExcelReportUpdate.reportFilename+"  Please check the the environment is properly configured for these: execution_environment , execution_build, report_excel_template, report_excel_filename");
			ScriptLogger.log.info("Exception occured while updating excel report. Excel report file is not found:"+ExcelReportUpdate.reportFilename+"  Please check the the environment is properly configured for these: execution_environment , execution_build, report_excel_template, report_excel_filename");
		}catch(Exception e){
			System.out.println("e5");
			e.printStackTrace();
			System.out.println("Exception occured while updating excel report. Excel report file:"+ExcelReportUpdate.reportFilename+"  Please check the the environment is properly configured for these: execution_environment , execution_build, report_excel_template, report_excel_filename");
			ScriptLogger.log.info("Exception occured while updating excel report. Excel report file:"+ExcelReportUpdate.reportFilename+"  Please check the the environment is properly configured for these: execution_environment , execution_build, report_excel_template, report_excel_filename");			
		}

	}

	private static void updateTestScriptExcelReportBuild(File reportXLSFile) throws IOException {
		// System.out.println("Calling updateTestScriptExcelReportBuild");
		FileInputStream file = new FileInputStream(reportXLSFile);
		HSSFWorkbook workbook = new HSSFWorkbook(file);
		HSSFSheet sheetSummary = workbook.getSheetAt(0);
		HSSFRow rowDate = sheetSummary.getRow(1);
		HSSFCell cellDate = rowDate.getCell(1);
		HSSFCell cellRanBy = rowDate.getCell(6);
		HSSFRow rowBuild = sheetSummary.getRow(2);
		HSSFCell cellBuild = rowBuild.getCell(1);
		HSSFRow rowBranch = sheetSummary.getRow(3);
		HSSFCell cellBranch = rowBranch.getCell(1);
		HSSFRow rowTotals = sheetSummary.getRow(9);
		HSSFCell cellStartTime = rowTotals.getCell(5);

		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		DateFormat df1 = new SimpleDateFormat("HH:mm:ss");
		Date dt = new Date();
		String currentDate = df.format(dt);

		cellDate.setCellValue(currentDate);
		cellRanBy.setCellValue(System.getProperty("user.name"));
		cellBuild.setCellValue(Environment.get("execution_build"));
		cellBranch.setCellValue(Environment.get("execution_environment"));
		cellStartTime.setCellValue(df1.format(dt));

		file.close();

		FileOutputStream fos = new FileOutputStream(reportXLSFile);
		workbook.write(fos);
		try {
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		fos.close();
	}

	public static void createTestCaseReport() throws IOException {
		File f = new File(Environment.get("report_path") + "/TestCaseReport_" + TestRunner.timeStamp + ".html");
		if (!f.exists()) {
			f.createNewFile();
		}

		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date dt = new Date();
		String d = df.format(dt);

		String content = "";
		content = content + "<html>\n<head>\n<title>Test Case Report</title>";
		content = content + "\n<style>\n";
		content = content + "body {background-color: #EEEEEE; font-family:verdana;}";
		content = content + "#report table{width:95%; margin-left:2%; border-width:1px; border-style: solid; border-collapse: collapse; colspan: 0px;}";
		content = content + "#report table caption{font-size: 30px; color: black; }";
		content = content + "#report table th{ border-width:1px; border-style: solid; border-color: white; color: white; background-color: #3A5FCD; padding: 2px; font-size: 80%;}";
		content = content + "#report table td{ border-width:1px; border-style: solid; border-color: white; background-color: #F4F4F4;  padding: 2px; font-size: 80%;}";
		content = content + "#report table td.fail{ border-width:1px; border-style: solid; border-color: white; color:red;  padding: 2px; font-size: 80%;}";
		content = content + "#report table td.pass{ border-width:1px; border-style: solid; border-color: white; color: black; font-weight:normal; padding: 2px; font-size: 80%;}";
		content = content + "#report table td.warning{ border-width:1px; border-style: solid; border-color: white; color: orange; font-weight:normal; padding: 2px; font-size: 80%;}";
		content = content + "</style>\n";

		content = content + "<script>\n\n";

		content = content + "function resultFilter() {";
		content = content + "\n  var criteria, filter, table, tr, td, i, c;";
		content = content + "\n  criteria = document.getElementById(\"filterCriteria\");";
		content = content + "\n  filter = criteria.value.toUpperCase();";
		content = content + "\n  table = document.getElementById(\"testCaseResultTable\");";
		content = content + "\n  tr = table.getElementsByTagName(\"tr\");";
		content = content + "\n  c = 0;";
		content = content + "\n  for (i = 0; i < tr.length; i++) {";
		content = content + "\n    td = tr[i].getElementsByTagName(\"td\")[2];";
		content = content + "\n    if (td) {";
		content = content + "\n      if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {";
		content = content + "\n        tr[i].style.display = \"\";";
		content = content + "\n		   c = c+1;";
		content = content + "\n      } else {";
		content = content + "\n        tr[i].style.display = \"none\";";
		content = content + "\n      }";
		content = content + "\n    } ";
		content = content + "\n  }";
		content = content + "\n";
		content = content + "\n  if(filter===\"\"){";
		content = content + "\n	   document.getElementById(\"statusCount\").innerHTML=0;";
		content = content + "\n	   c=0;";
		content = content + "\n  }else{";
		content = content + "\n	   document.getElementById(\"statusCount\").innerHTML=c;";
		content = content + "\n  }";
		content = content + "\n";
		content = content + "\n}\n\n";

		content = content + "function descriptionFilter() {";
		content = content + "\n  var criteria, filter, table, tr, td, i, c;";
		content = content + "\n  criteria = document.getElementById(\"descCriteria\");";
		content = content + "\n  filter = criteria.value.toUpperCase();";
		content = content + "\n  table = document.getElementById(\"testCaseResultTable\");";
		content = content + "\n  tr = table.getElementsByTagName(\"tr\");";
		content = content + "\n  c = 0;";
		content = content + "\n";
		content = content + "\n  for (i = 0; i < tr.length; i++) {";
		content = content + "\n    td = tr[i].getElementsByTagName(\"td\")[3];";
		content = content + "\n    if (td) {";
		content = content + "\n      if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {";
		content = content + "\n        tr[i].style.display = \"\";";
		content = content + "\n        c = c+1;";
		content = content + "\n      } else {";
		content = content + "\n        tr[i].style.display = \"none\";";
		content = content + "\n      }";
		content = content + "\n    }";
		content = content + "\n  }";
		content = content + "\n";
		content = content + "\n  if(filter===\"\"){";
		content = content + "\n     document.getElementById(\"descriptionCount\").innerHTML=0;";
		content = content + "\n    c=0;";
		content = content + "\n    }else{";
		content = content + "\n	   document.getElementById(\"descriptionCount\").innerHTML=c;";
		content = content + "\n    }";
		content = content + "\n";
		content = content + "\n}\n";

		content = content + "</script>\n";
		content = content + "</head>\n";
		content = content + "<body>\n";
		content = content + "<div id=\"report\">";
		
		content = content + "<table id=\"testCaseResultTable\">";
		content = content + "<caption>Test Case Report</caption>";
		content = content + "<tr><td colspan=\"4\">Start Time: " + d + "</td></tr>";
		content = content + "<tr>";
		content = content + "	<th>S.No</th>";
		content = content + "	<th>TC Id</th>";
		content = content + "	<th>Status<br/><input type=\"text\" id=\"filterCriteria\" onkeyup=\"resultFilter()\" placeholder=\"Search for result..\">(<small id=\"statusCount\">0</small>)</th>";
		content = content
				+ "	<th>Description<br/><input type=\"text\" id=\"descCriteria\" onkeyup=\"descriptionFilter()\" placeholder=\"Search for description..\">(<small id=\"descriptionCount\">0</small>)</th>";
		content = content + "</tr>";

		content = content + "<tr><td colspan=\"4\" style=\"text-align:center\">Generated by Selenium O2</td></tr>";
		content = content + "</table><br/><br/>";
		content = content + "</div>";
		content = content + "</body>";
		content = content + "</html>";

		Files.write(Paths.get(Environment.get("report_path") + "/TestCaseReport_" + TestRunner.timeStamp + ".html"), content.getBytes(StandardCharsets.UTF_8));
	}

	public static void cleanupScreenshots() {
		// Create report path if does not exist
		File reportPath = new File(Environment.get("report_path"));
		if (reportPath.exists()) {
			if (!reportPath.isDirectory()) {
				try {
					reportPath.mkdir();
				} catch (SecurityException se) {
					Reporting.report("FAIL", "Security exception while creating report path.  Please check if the user has valid rights!");
				}
			}
		} else {
			try {
				reportPath.mkdir();
			} catch (SecurityException se) {
				Reporting.report("FAIL", "Security exception while creating report path.  Please check if the user has valid rights!");
			}
		}

		// Create screenshots path if does not exist
		File screenshotsPath = new File(Environment.get("report_path") + "/screenshots");
		if (screenshotsPath.exists()) {
			if (!screenshotsPath.isDirectory()) {
				try {
					screenshotsPath.mkdir();
				} catch (SecurityException se) {
					Reporting.report("FAIL", "Security exception while creating reports/screenshots path.  Please check if the user has valid rights!");
				}
			}
		} else {
			try {
				screenshotsPath.mkdir();
			} catch (SecurityException se) {
				Reporting.report("FAIL", "Security exception while creating reports/screenshots path.  Please check if the user has valid rights!");
			}
		}

		if (screenshotsPath.listFiles().length > 0) {
			LocalDateTime now = LocalDateTime.now();
			String year = String.valueOf(now.getYear());
			String month = String.valueOf(now.getMonthValue());
			String day = String.valueOf(now.getDayOfMonth());
			String hour = String.valueOf(now.getHour());
			String minute = String.valueOf(now.getMinute());
			String second = String.valueOf(now.getSecond());

			if (Integer.valueOf(month) < 10) {
				month = "0" + month;
			}
			if (Integer.valueOf(day) < 10) {
				day = "0" + day;
			}
			if (Integer.valueOf(hour) < 10) {
				hour = "0" + hour;
			}
			if (Integer.valueOf(minute) < 10) {
				minute = "0" + minute;
			}
			if (Integer.valueOf(second) < 10) {
				second = "0" + second;
			}

			String screenShotsBackup = Environment.get("report_path") + "/screenshots/screenshots_" + year + month + day + "_" + hour + minute + second;
			File screenShotsBackupFile = new File(screenShotsBackup);
			screenShotsBackupFile.mkdir();
			File[] files = screenshotsPath.listFiles();
			for (File f : files) {
				// System.out.println(f.getName());
				try {
					if (!f.isDirectory()) {
						// FileUtils.moveFile(f, new
						// File(screenShotsBackupFile+"/"+f.getAbsolutePath()));
						// Files.move(f, screenShotsBackupFile,
						// StandardCopyOption.REPLACE_EXISTING);
						f.renameTo(new File(screenShotsBackupFile + "/" + f.getName()));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		// if(screenshotsPath.listFiles().length>0){
		// Date d = new Date();
		// String screenShotsBackup
		// =Environment.get("report_path")+"/screenshots/screenshots_"+d.getTime();
		// File screenShotsBackupPath = new File(screenShotsBackup);
		// screenShotsBackupPath.mkdir();
		// File[] files = screenshotsPath.listFiles();
		// for(File f:files){
		// try {
		// FileUtils.moveFile(f, new File(screenShotsBackup+"/"+f.getName()));
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// }
		// }

	}

	public static void main(String[] args) {

	}

	public static void createNewReportPath() {
		// Create report path if does not exist
		File reportPath = new File(Environment.get("report_path"));
		if (reportPath.exists()) {
			if (!reportPath.isDirectory()) {
				try {
					reportPath.mkdir();
				} catch (SecurityException se) {
					Reporting.report("FAIL", "Security exception while creating report path.  Please check if the user has valid rights!");
				}
			}
		} else {
			try {
				reportPath.mkdir();
			} catch (SecurityException se) {
				Reporting.report("FAIL", "Security exception while creating report path.  Please check if the user has valid rights!");
			}
		}

		createNewScreenPath();
	}

	public static void createNewScreenPath() {
		// Create report path if does not exist
		File reportPath = new File(Environment.get("report_path") + "/screenshots");
		if (reportPath.exists()) {
			if (!reportPath.isDirectory()) {
				try {
					reportPath.mkdir();
				} catch (SecurityException se) {
					Reporting.report("FAIL", "Security exception while creating report path.  Please check if the user has valid rights!");
				}
			}
		} else {
			try {
				reportPath.mkdir();
			} catch (SecurityException se) {
				Reporting.report("FAIL", "Security exception while creating report path.  Please check if the user has valid rights!");
			}
		}

	}
}
