package com.ntt.acoe.framework.selenium.report;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

/*
 * @author Santosh Hariprasad (NTT Badge Id: 244583,
 *         Santhosh.Hariprasad@NTTDATA.com)
 * @version 1.0
 * @since 2015-01-01
 */
public class HTMLReportGenerator {
	private static String getHTMLHeader() {
		return "<html><head><title>Test Report</title><style>#report table{width:95%; margin-left:2%; border-size:1px; border-style: solid; border-collapse: collapse; colspan: 0px;}#report table caption{font-size: 30px;}#report table th{ border-size:1px; border-style: solid; background-color: lightgray;}#report table td{ border-size:1px; border-style: solid;}</style></head><body><div id=\"report\">";
	}

	private static String getTableHeader() {
		return "<table><caption>Test Report</caption><tr><th>S.No</th><th>TC Id</th><th>TC Name</th><th>Test Case Status</th><th>Step</th><th>Expected</th><th>Actual</th><th>Step Status</th><th>Level</th><th>Description</th></tr>";
	}

	private static String getTableData() {
		String tdData = "";
		for (int i = 0; i < TestResult.resultArray.size(); i++) {
			ResultRecord r = TestResult.resultArray.get(i);
			tdData = tdData + "<tr><td>" + (i + 1) + "</td><td>" + r.tcId + "</td><td>" + r.tcTitle + "</td><td>" + r.tcStatus + "</td><td>" + r.stepId + "</td><td>" + r.expected + "</td><td>"
					+ r.actual + "</td><td>" + r.stepStatus + "</td><td>" + r.comments + "</td><td>" + r.comments + "</td></tr>";
		}
		return tdData;
		// return "<tr><td rowspan=\"2\">1</td><td rowspan=\"2\">TC001</td><td
		// rowspan=\"2\">Test Case1</td><td
		// rowspan=\"2\">Fail</td><td>Step1</td><td>100</td><td>30</td><td>Fail</td><td>Field</td><td>None</td></tr><tr><td>Step2</td><td>30</td><td>30</td><td>Pass</td><td>Field</td><td>None</td></tr>";
	}

	private static String getHTMLClosure() {
		return "</div></body></html>";
	}

	private static String getCurrentTime() {
		return new Date().toString();
	}

	public static String getHTML() {
		return getHTMLHeader() + getTableHeader() + getTableData() + "</table><br/><br/>" + getCurrentTime() + getHTMLClosure();
	}

	public static void saveReport(String fileName) throws IOException {
		File f = new File(fileName);
		FileWriter fw = new FileWriter(f);
		fw.write(getHTML());
		fw.close();
	}
}
