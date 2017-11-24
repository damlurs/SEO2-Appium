package com.ntt.acoe.framework.selenium.report;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.ntt.acoe.framework.config.Configuration;

/*
 * @author Santosh Hariprasad (NTT Badge Id: 244583,
 *         Santhosh.Hariprasad@NTTDATA.com)
 * @version 1.0
 * @since 2015-01-01
 */

public class ExcelReport {
	static ArrayList<ResultRecord> testResult1;
	static ArrayList<String> uniqueTCIds = new ArrayList<String>();
	static int passed = 0;
	static ArrayList<String> tcIds = new ArrayList<String>();

	private static String getHTMLHeader() {
		return "Company Name";
	}

	private static String getTableHeader() {
		return "S.No,TC Id,TC Name,Test Case Status,Step Name,Description,Expected,Actual,Step Status";
	}

	private static String getFilesTable() {
		return "\nTest Report,,,,,,,," + "\nTotal Steps:," + testResult1.size() + ",,,,,,," + "\nSteps Passed:," + getPassCount() + ",,,,,,," + "\nPass Percentage:," + getPercentage() + " % ,,,,,,,"
				+ "\nStarted On:," + TestResult.start + ",,,,,,," + "\nCompleted On:," + TestResult.end + ",,,,,,," + "\nDuration:," + ((TestResult.endTime - TestResult.startTime) / 60000)
				+ " minute(s) ,,,,,,," + "\n";
	}

	private static void copyResult() {
		for (int i = 0; i < TestResult.resultArray.size(); i++) {
			tcIds.add(TestResult.resultArray.get(i).tcId);
		}
	}

	private static int getPercentage() {
		int pc = 0;
		if (testResult1.size() > 0) {
			pc = (passed * 100 / testResult1.size());
		}
		return pc;
	}

	public static void updatedTestResult() {
		// copyResult();
		testResult1 = TestResult.resultArray;

		for (int i = 0; i < TestResult.resultArray.size(); i++) {
			ResultRecord r = TestResult.resultArray.get(i);
			// System.out.println("r.tcId-"+r.tcId);
			if (!checkIfExists(r.tcId)) {
				uniqueTCIds.add(r.tcId + "#" + r.tcTitle + "#Fail#" + i + "#" + i);
				// System.out.println("updatedTestResult-NewUnique-"+r.tcId+"#"+i+"#"+i);
			} else {
				String existingVal = uniqueTCIds.get(getIndex(r.tcId));
				String[] existingValTokens = existingVal.split("\\#");
				existingValTokens[4] = String.valueOf(i);
				uniqueTCIds.set(getIndex(r.tcId), existingValTokens[0] + "#" + existingValTokens[1] + "#" + existingValTokens[2] + "#" + existingValTokens[3] + "#" + existingValTokens[4]);
				// System.out.println("updatedTestResult-OldUnique-"+existingValTokens[0]+"#"+existingValTokens[1]+"#"+existingValTokens[2]);
			}
		}

		// Place Ignore
		for (int i = 0; i < testResult1.size(); i++) {
			testResult1.get(i).tcId = "IGNORE";
			testResult1.get(i).tcTitle = "IGNORE";
			testResult1.get(i).tcStatus = "IGNORE";
		}

		for (int i = 0; i < uniqueTCIds.size(); i++) {
			// System.out.println(uniqueTCIds.get(i));
			String[] existingValTokens = uniqueTCIds.get(i).split("\\#");
			boolean newRes = checkIfPass(Integer.valueOf(existingValTokens[3]), Integer.valueOf(existingValTokens[4]));
			if (newRes == true) {
				existingValTokens[2] = "Pass";
			} else {
				existingValTokens[2] = "Fail";
			}
			int p = Integer.valueOf(existingValTokens[3]);
			String commas = "";
			int commasCount = Integer.valueOf(existingValTokens[4]) - Integer.valueOf(existingValTokens[3]) + 1;
			for (int c = 0; c < commasCount; c++) {
				commas = commas + ",";
			}
			testResult1.get(p).tcId = commas + existingValTokens[0];
			testResult1.get(p).tcTitle = commas + existingValTokens[1];
			testResult1.get(p).tcStatus = commas + existingValTokens[2];
		}

		copyResult();
	}

	private static boolean checkIfPass(int startRow, int endRow) {
		boolean isTCPassed = true;

		for (int i = startRow; i <= endRow; i++) {
			ResultRecord currentResultRecord = TestResult.resultArray.get(i);

			if (currentResultRecord.stepStatus.equalsIgnoreCase("Fail")) {
				isTCPassed = false;
				break;
			}
		}
		return isTCPassed;
	}

	private static boolean checkIfExists(String tcId) {
		boolean isExists = false;
		String currentTCId = "";
		// System.out.println("uniqueTCIds.size()-"+uniqueTCIds.size());
		for (int i = 0; i < uniqueTCIds.size(); i++) {
			currentTCId = uniqueTCIds.get(i);
			// System.out.println("checkIfExists token
			// 0-"+currentTCId.split("\\#")[0]);
			if (tcId.equalsIgnoreCase(currentTCId.split("\\#")[0]) == true) {
				isExists = true;
			}
		}
		// System.out.println("checkIfExists-"+tcId+"-"+isExists);
		return isExists;
	}

	private static int getIndex(String tcId) {
		int position = -1;
		String currentTCId = "";
		for (int i = 0; i < uniqueTCIds.size(); i++) {
			currentTCId = uniqueTCIds.get(i);
			if (tcId.equalsIgnoreCase(currentTCId.split("\\#")[0]) == true) {
				position = i;
			}
		}

		// System.out.println("getIndex-"+tcId+"-"+position);
		return position;
	}

	private static int getPassCount() {
		for (int i = 0; i < testResult1.size(); i++) {
			ResultRecord r = testResult1.get(i);
			if (r.stepStatus.equalsIgnoreCase("Pass")) {
				passed = passed + 1;
			}
		}
		return passed;
	}

	private static String getTableData1() {
		String tdData = "";
		for (int i = 0; i < testResult1.size(); i++) {
			ResultRecord r = testResult1.get(i);
			tdData = tdData + (i + 1);

			// if(!r.tcId.equalsIgnoreCase("IGNORE")){tdData = tdData +","+
			// r.tcId; }
			// if(!r.tcId.equalsIgnoreCase("IGNORE")){tdData = tdData +","+
			// r.tcTitle; }
			// if(!r.tcId.equalsIgnoreCase("IGNORE")){tdData = tdData +","+
			// r.tcStatus; }

			if (r.stepStatus.equalsIgnoreCase("fail")) {
				tdData = tdData + "," + r.stepId + "," + r.comments + "," + r.expected + "," + r.actual + "," + r.stepStatus;
			} else {
				tdData = tdData + "," + r.stepId + "," + r.comments + "," + r.expected + "," + r.actual + "," + r.stepStatus;
			}
			tdData = tdData + "\n";
		}
		return tdData;
	}

	private static String getHTMLClosure() {
		return "\n";
	}

	public static String getHTML() {
		return getHTMLHeader() + "\n" + getFilesTable() + "\n" + getTableHeader() + "\n" + getTableData1() + "\n" + getHTMLClosure();
	}

	public static void saveReport(String fileName) throws IOException {
		copyResult();
		updatedTestResult();
		File f = new File(fileName);
		FileWriter fw = new FileWriter(f);
		fw.write(getHTML());
		fw.close();
	}

	public static void saveReport() throws IOException {
		DateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");
		Date d = new Date();
		saveReport(Configuration.REPORT_PATH + "/TestReport_" + df.format(d) + ".csv");
	}
}
