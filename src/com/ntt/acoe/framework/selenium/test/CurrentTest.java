package com.ntt.acoe.framework.selenium.test;

import com.ntt.acoe.framework.selenium.report.TestResult;
import com.ntt.acoe.framework.selenium.verify.Assert;

/*
 * @author Santosh Hariprasad (NTT Badge Id: 244583,
 *         Santhosh.Hariprasad@NTTDATA.com)
 * @version 1.0
 * @since 2015-01-01
 */

public class CurrentTest {
	public static String tcId = "";
	public static String tcTitle = "";
	public static String tcStatus = "";
	public static String stepId = "";
	public static String expected = "";
	public static String actual = "";
	public static String stepStatus = "";
	public static String comments = "";

	public static void reset() {
		tcId = "";
		tcTitle = "";
		tcStatus = "";
		stepId = "";
		expected = "";
		actual = "";
		stepStatus = "";
		comments = "";
	}

	// this will be used at the begining of teh etst cases
	public static void setupTest(String tcId, String tcTitle) {
		CurrentTest.tcId = tcId;
		CurrentTest.tcTitle = tcTitle;
		CurrentTest.tcStatus = "";
	}

	// mainly at the test step level
	public static void setupTest(String stepId, String expected, String actual, String comments) {
		CurrentTest.stepId = stepId;
		CurrentTest.expected = expected;
		CurrentTest.actual = actual;
		CurrentTest.stepStatus = "";
		CurrentTest.comments = comments;
	}

	public static void setupTest(String stepId, boolean expected, boolean actual, String comments) {
		CurrentTest.stepId = stepId;
		CurrentTest.expected = String.valueOf(expected);
		CurrentTest.actual = String.valueOf(actual);
		CurrentTest.stepStatus = "";
		CurrentTest.comments = comments;
	}

	public static void setupTest(String tcId, String tcTitle, String tcStatus, String stepId, String expected, String actual, String stepStatus, String comments) {
		CurrentTest.tcId = tcId;
		CurrentTest.tcTitle = tcTitle;
		CurrentTest.tcStatus = tcStatus;
		CurrentTest.stepId = stepId;
		CurrentTest.expected = expected;
		CurrentTest.actual = actual;
		CurrentTest.stepStatus = stepStatus;
		CurrentTest.comments = comments;
	}

	public static void setupTest(String tcId, String tcTitle, String tcStatus, String stepId, boolean expected, boolean actual, String stepStatus, String comments) {
		CurrentTest.tcId = tcId;
		CurrentTest.tcTitle = tcTitle;
		CurrentTest.tcStatus = tcStatus;
		CurrentTest.stepId = stepId;
		CurrentTest.expected = String.valueOf(expected);
		CurrentTest.actual = String.valueOf(actual);
		CurrentTest.stepStatus = stepStatus;
		CurrentTest.comments = comments;
	}

	public static void validate() {
		// try {
		// AWTScreenshot.captureScreenshot(Environment.get("report_path")
		// + "/screenshots/" + CurrentTest.tcId + "_"
		// + CurrentTest.stepId + ".png");
		// } catch (Exception e) {
		// try {
		// AWTScreenshot.captureScreenshot(Configuration.REPORT_PATH
		// + "/screenshots/" + CurrentTest.tcId + "_"
		// + CurrentTest.stepId + ".png");
		// } catch (Exception e1) {
		// //e1.printStackTrace();
		// }
		// }

		Assert.assertEquals(actual, expected);
		// if (CurrentTest.expected.equals(CurrentTest.actual)) {
		// TestResult.addResultRecord(CurrentTest.tcId, CurrentTest.tcTitle,
		// CurrentTest.tcStatus, CurrentTest.stepId,
		// CurrentTest.expected, CurrentTest.actual, "Pass",
		// CurrentTest.comments);
		// } else {
		// TestResult.addResultRecord(CurrentTest.tcId, CurrentTest.tcTitle,
		// CurrentTest.tcStatus, CurrentTest.stepId,
		// CurrentTest.expected, CurrentTest.actual, "Fail",
		// CurrentTest.comments);
		// }
	}

	public static void writeResult(String stepDescription, String expected, String stepStatus, String actual) {

		CurrentTest.expected = expected;
		CurrentTest.actual = actual;
		CurrentTest.stepStatus = stepStatus;
		CurrentTest.comments = stepDescription;
		TestResult.addResultRecord(CurrentTest.tcId, CurrentTest.tcTitle, CurrentTest.tcStatus, CurrentTest.stepId, CurrentTest.expected, CurrentTest.actual, CurrentTest.stepStatus,
				CurrentTest.comments);
	}
}
