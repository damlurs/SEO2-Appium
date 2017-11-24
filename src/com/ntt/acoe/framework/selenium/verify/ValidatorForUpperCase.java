package com.ntt.acoe.framework.selenium.verify;

import com.ntt.acoe.framework.selenium.report.TestResult;
import com.ntt.acoe.framework.selenium.test.CurrentTest;

/*
 * @author Santosh Hariprasad (NTT Badge Id: 244583,
 *         Santhosh.Hariprasad@NTTDATA.com)
 * @version 1.0
 * @since 2015-01-01
 */
public class ValidatorForUpperCase {
	public static void validate(boolean expected) {
		if (isUpperCase(CurrentTest.actual) == expected) {
			TestResult.addResultRecord(CurrentTest.tcId, CurrentTest.tcTitle, CurrentTest.tcStatus, CurrentTest.stepId + " - Verifying upper case", "'Value should be in Upper case'",
					"'" + CurrentTest.actual + "'", "Pass", CurrentTest.comments);
		} else {
			TestResult.addResultRecord(CurrentTest.tcId, CurrentTest.tcTitle, CurrentTest.tcStatus, CurrentTest.stepId + " - Verifying upper case", "'Value should be in Upper case'",
					"'" + CurrentTest.actual + "'", "Fail", CurrentTest.comments);
		}
	}

	private static boolean isUpperCase(String actual) {
		boolean isUpperCase = true;
		char[] actualChars = actual.toCharArray();
		for (int i = 0; i < actualChars.length; i++) {
			if (actualChars[i] > 96 && actualChars[i] < 123) {
				isUpperCase = false;
				break;
			}
		}
		return isUpperCase;
	}
}
