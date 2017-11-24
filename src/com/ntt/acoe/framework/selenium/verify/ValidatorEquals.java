package com.ntt.acoe.framework.selenium.verify;

import com.ntt.acoe.framework.selenium.report.TestResult;
import com.ntt.acoe.framework.selenium.test.CurrentTest;

/*
 * @author Santosh Hariprasad (NTT Badge Id: 244583,
 *         Santhosh.Hariprasad@NTTDATA.com)
 * @version 1.0
 * @since 2015-01-01
 */
public class ValidatorEquals {
	public static void validate() {
		// System.out.println("TestResult.resultArray.size()
		// Before:"+TestResult.resultArray.size());
		if (CurrentTest.expected.equals(CurrentTest.actual)) {
			TestResult.addResultRecord(CurrentTest.tcId, CurrentTest.tcTitle, CurrentTest.tcStatus, CurrentTest.stepId, CurrentTest.expected, CurrentTest.actual, "Pass", CurrentTest.comments);
		} else {
			TestResult.addResultRecord(CurrentTest.tcId, CurrentTest.tcTitle, CurrentTest.tcStatus, CurrentTest.stepId, CurrentTest.expected, CurrentTest.actual, "Fail", CurrentTest.comments);
		}
		// System.out.println("TestResult.resultArray.size()
		// After:"+TestResult.resultArray.size());
	}
}
