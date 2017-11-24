package com.ntt.acoe.framework.selenium.report;

import java.util.ArrayList;
import java.util.Date;

/*
 * @author Santosh Hariprasad (NTT Badge Id: 244583,
 *         Santhosh.Hariprasad@NTTDATA.com)
 * @version 1.0
 * @since 2015-01-01
 */
public class TestResult {
	public static Date start = new Date();
	public static Date end = new Date();
	public static long startTime = System.currentTimeMillis();
	public static long endTime = System.currentTimeMillis();
	public static int totalTestSteps = 0;
	public static int passedTestSteps = 0;
	public static int failedTestSteps = 0;

	public static ArrayList<ResultRecord> resultArray = new ArrayList<ResultRecord>();

	public static void addResultRecord(String tcId, String tcTitle, String tcStatus, String stepId, String expected, String actual, String stepStatus, String comments) {
		TestResult.resultArray.add(new ResultRecord(tcId, tcTitle, tcStatus, stepId, expected, actual, stepStatus, comments));

		totalTestSteps = totalTestSteps + 1;
		if (stepStatus.equalsIgnoreCase("PASS")) {
			passedTestSteps = passedTestSteps + 1;
		} else if (stepStatus.equalsIgnoreCase("FAIL")) {
			passedTestSteps = failedTestSteps + 1;
		}
	}
}
