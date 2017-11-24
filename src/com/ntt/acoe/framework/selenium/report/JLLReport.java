package com.ntt.acoe.framework.selenium.report;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.ntt.acoe.framework.config.Configuration;
import com.ntt.acoe.framework.config.Environment;

/*
 * @author Vijaya Bhaskar Devireddy (DELL Badge Id: 614269,
 *         Vijaya_Bhaskar_Devir@dell.com)
 * @version 1.0
 * @since 2015-01-01
 */
public class JLLReport {
	static ArrayList<ResultRecord> testResult1;
	static ArrayList<String> uniqueTCIds = new ArrayList<String>();
	static int passed = 0;
	static ArrayList<String> tcIds = new ArrayList<String>();

	private static String getHTMLHeader() {
		return "<html><head><title>Test Case Report</title>" + "<style>" + "body {background-color: lightgray; font-family:verdana;}"
				+ "#report table{width:95%; margin-left:2%; border-width:1px; border-style: solid; border-collapse: collapse; colspan: 0px;}" + "#report table caption{font-size: 30px; color: white; }"
				+ "#report table th{ border-width:1px; border-style: solid; border-color: white; color: white; background-color: #771219; padding: 2px; font-size: 80%;}"
				+ "#report table td{ border-width:1px; border-style: solid; border-color: white; background-color: #f5f5f5;  padding: 2px; font-size: 80%;}"
				+ "#report table td.fail{ border-width:1px; border-style: solid; border-color: white;  color:red;  padding: 2px; font-size: 80%;}"
				+ "#report table td.failstatus{ border-width:1px; border-style: solid; border-color: white;  background-color:red; font-weight:bold; padding: 2px; font-size: 80%;}"
				+ "#report table td.passstatus{ border-width:1px; border-style: solid; border-color: white;  background-color: #00CC00; font-weight:bold; padding: 2px; font-size: 80%;}" + "</style>"
				+ "</head><body><div id=\"report\">";
	}

	private static String getTableHeader() {
		return "<table><tr><th>S.No</th><th>TC Id</th><th width=\"\5%\">TC Name</th><th>Test Case Status</th><th>Step&nbsp;Name</th><th>Description</th><th>Expected</th><th>Actual</th><th>Step Status</th></tr>";
	}

	private static String getFilesTable() {
		return "<table>" + "<caption>Test Report</caption>" + "<tr><td>Total Steps:</td><td>" + testResult1.size() + "</td></tr>" + "<tr><td>Steps Passed:</td><td>" + getPassCount() + "</td></tr>"
				+ "<tr><td>Pass Percentage:</td><td>" + getPercentage() + " % </td></tr>" + "<tr><td>Started On:</td><td>" + TestResult.start + "</td></tr>" + "<tr><td>Completed On:</td><td>"
				+ TestResult.end + "</td></tr>" + "<tr><td>Duration:</td><td>" + ((TestResult.endTime - TestResult.startTime) / 60000) + " minute(s) </td></tr>" + "</table><br></br>";
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
			testResult1.get(p).tcId = "<td rowspan=\"" + (Integer.valueOf(existingValTokens[4]) - Integer.valueOf(existingValTokens[3]) + 1) + "\">" + existingValTokens[0] + "</td>";
			testResult1.get(p).tcTitle = "<td rowspan=\"" + (Integer.valueOf(existingValTokens[4]) - Integer.valueOf(existingValTokens[3]) + 1) + "\">" + existingValTokens[1] + "</td>";
			testResult1.get(p).tcStatus = "<td rowspan=\"" + (Integer.valueOf(existingValTokens[4]) - Integer.valueOf(existingValTokens[3]) + 1) + "\">" + existingValTokens[2] + "</td>";
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
			tdData = tdData + "<tr>" + "<td>" + (i + 1) + "</td>";

			if (!r.tcId.equalsIgnoreCase("IGNORE")) {
				tdData = tdData + r.tcId;
			}
			if (!r.tcId.equalsIgnoreCase("IGNORE")) {
				tdData = tdData + r.tcTitle;
			}
			if (!r.tcId.equalsIgnoreCase("IGNORE")) {
				tdData = tdData + r.tcStatus;
			}

			if (r.stepStatus.equalsIgnoreCase("fail")) {
				// System.out.println("<a href=\"screenshot_"+tcIds.get(i)+"_"
				// +r.stepId +".png\">");
				tdData = tdData + "<td class=\"fail\"><a href=\"screenshots/" + tcIds.get(i) + "_" + r.stepId + ".png\">" + r.stepId + "</a></td><td class=\"fail\">" + r.comments + "</td>"
						+ "<td class=\"fail\">" + r.expected + "</td><td class=\"fail\">" + r.actual + "<td class=\"failstatus\">" + r.stepStatus + "</td></tr>";
			} else {
				tdData = tdData + "<td><a href=\"screenshots/" + tcIds.get(i) + "_" + r.stepId + ".png\">" + r.stepId + "</a></td><td>" + r.comments + "</td><td>" + r.expected + "</td><td>" + r.actual
						+ "</td>" + "<td  class=\"passstatus\">" + r.stepStatus + "</td></tr>";
			}
		}
		return tdData;
	}

	private static String getHTMLClosure() {
		return "</div></body></html>";
	}

	private static String getCurrentTime() {
		return new Date().toString();
	}

	public static String getHTML() {
		return getHTMLHeader() + getFilesTable() + getTableHeader() + getTableData1() + "</table><br/><br/>" + getHTMLClosure();
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
		saveReport(Environment.get("report_path") + "/TestCaseReport_" + df.format(d) + ".html");
		// saveReport(Configuration.REPORT_PATH+"/TestReport_"+df.format(d)+".html");
		// updatedTestResult();
		// File f = new File(Configuration.REPORT_PATH+"/TestReport.html");
		// FileWriter fw = new FileWriter(f);
		// fw.write(getHTML());
		// fw.close();
	}
}
