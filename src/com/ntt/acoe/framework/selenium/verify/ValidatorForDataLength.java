package com.ntt.acoe.framework.selenium.verify;

import com.ntt.acoe.framework.selenium.report.TestResult;
import com.ntt.acoe.framework.selenium.test.CurrentTest;

/*
 * @author Santosh Hariprasad (NTT Badge Id: 244583,
 *         Santhosh.Hariprasad@NTTDATA.com)
 * @version 1.0
 * @since 2015-01-01
 */
public class ValidatorForDataLength {
	public static void validate(int expLength) {
		System.out.println("validating data length rule: in validate");
		if (CurrentTest.actual.length() == expLength) {
			TestResult.addResultRecord(CurrentTest.tcId, CurrentTest.tcTitle, CurrentTest.tcStatus, CurrentTest.stepId + " - Verifying data length", "'" + CurrentTest.expected + "'",
					"'" + CurrentTest.actual + "'", "Pass", CurrentTest.comments);
		} else {
			TestResult.addResultRecord(CurrentTest.tcId, CurrentTest.tcTitle, CurrentTest.tcStatus, CurrentTest.stepId + " - Verifying data length", "'" + CurrentTest.expected + "'",
					"'" + CurrentTest.actual + "'", "Fail", CurrentTest.comments);
		}
	}
}
