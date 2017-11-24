package com.ntt.acoe.framework.selenium.report;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.ntt.acoe.framework.config.Environment;
import com.ntt.acoe.framework.run.TestRunner;

/*
 * @author Santosh Hariprasad (NTT Badge Id: 244583,
 *         Santhosh.Hariprasad@NTTDATA.com)
 * @version 1.0
 * @since 2015-01-01
 */
public class TestCaseReport {

	public static void generate() {
		// DateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");
		// Date d = new Date();
		// File f = new
		// File(Configuration.REPORT_PATH+"/TestCaseReport_"+df.format(d)+".html");
		// File f = new
		// File(Environment.get("report_path")+"/TestCaseReport_"+df.format(d)+".html");
		File f = new File(Environment.get("report_path") + "/TestCaseReport_" + TestRunner.timeStamp + ".html");
		FileWriter fw;
		try {
			fw = new FileWriter(f);
			fw.write(getHTML());
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static String getHTML() {
		return getHTMLHeader() + getFilesTable() + getTableHeader() + getTableData() + "</table><br/><br/>" + getHTMLClosure();
	}

	private static String getHTMLHeader() {
		return "<html><head><title>Test Report</title>" + "<style>" + "body {background-color: lightgray; font-family:verdana;}"
				+ "#report table{width:95%; margin-left:2%; border-width:1px; border-style: solid; border-collapse: collapse; colspan: 0px;}" + "#report table caption{font-size: 30px; color: white; }"
				+ "#report table th{ border-width:1px; border-style: solid; border-color: white; color: white; background-color: #771219; padding: 2px; font-size: 80%;}"
				+ "#report table td{ border-width:1px; border-style: solid; border-color: white; background-color: #f5f5f5;  padding: 2px; font-size: 80%;}"
				+ "#report table td.fail{ border-width:1px; border-style: solid; border-color: white;  color:red;  padding: 2px; font-size: 80%;}"
				+ "#report table td.failstatus{ border-width:1px; border-style: solid; border-color: white;  background-color:red; font-weight:bold; padding: 2px; font-size: 80%;}"
				+ "#report table td.passstatus{ border-width:1px; border-style: solid; border-color: white;  background-color: #00CC00; font-weight:bold; padding: 2px; font-size: 80%;}" + "</style>"
				+ "</head><body><div id=\"report\">";
	}

	private static String getTableHeader() {
		return "<table><tr><th>S.No</th><th>TC Id</th><th>Status</th><th>Description</th></tr>";
	}

	private static String getFilesTable() {
		return "<table>" + "<caption>Test Report</caption>" + "<tr><td>Total Steps:</td><td>" + Reporting.testCaseResult.size() + "</td></tr>" + "<tr><td>Steps Passed:</td><td>" + 100 + "</td></tr>"
				+ "<tr><td>Pass Percentage:</td><td>" + 100 + " % </td></tr>" + "<tr><td>Started On:</td><td>" + TestResult.start + "</td></tr>" + "<tr><td>Completed On:</td><td>" + TestResult.end
				+ "</td></tr>" + "<tr><td>Duration:</td><td>" + ((TestResult.endTime - TestResult.startTime) / 60000) + " minute(s) </td></tr>" + "</table><br></br>";
	}

	private static String getTableData() {
		String tdData = "";
		for (int i = 0; i < Reporting.testCaseResult.size(); i++) {
			String reportingLine = Reporting.testCaseResult.get(i);
			String[] reportingLineTokens = reportingLine.split("\\:", -1);

			tdData = tdData + "<tr>" + "<td>" + (i + 1) + "</td>";

			if (reportingLineTokens[2].trim().equalsIgnoreCase("fail")) {
				tdData = tdData + "<td class=\"fail\"><a href=\"screenshots/" + reportingLineTokens[1].trim() + ".png\">" + reportingLineTokens[1].trim() + "</td><td class=\"fail\">"
						+ reportingLineTokens[2].trim() + "</td><td class=\"fail\">" + reportingLineTokens[3].trim() + "</td></tr>";
			} else if (reportingLineTokens[2].trim().equalsIgnoreCase("pass")) {
				tdData = tdData + "<td class=\"pass\"><a href=\"screenshots/" + reportingLineTokens[1].trim() + ".png\">" + reportingLineTokens[1].trim() + "</td><td class=\"pass\">"
						+ reportingLineTokens[2].trim() + "</td><td pass=\"fail\">" + reportingLineTokens[3].trim() + "</td></tr>";
			} else if (reportingLineTokens[2].trim().equalsIgnoreCase("error")) {
				tdData = tdData + "<td class=\"error\"><a href=\"screenshots/" + reportingLineTokens[1].trim() + ".png\">" + reportingLineTokens[1].trim() + "</td><td class=\"error\">"
						+ reportingLineTokens[2].trim() + "</td><td class=\"error\">" + reportingLineTokens[3].trim() + "</td></tr>";
			}
		}
		return tdData;
	}

	private static String getHTMLClosure() {
		return "</div></body></html>";
	}

}
