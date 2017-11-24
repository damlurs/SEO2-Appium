package com.ntt.acoe.framework.selenium.verify;

import com.ntt.acoe.framework.selenium.report.TestResult;
import com.ntt.acoe.framework.selenium.test.CurrentTest;

/*
 * @author Santosh Hariprasad (NTT Badge Id: 244583,
 *         Santhosh.Hariprasad@NTTDATA.com)
 * @version 1.0
 * @since 2015-01-01
 */
public class ValidatorForListValues {
	public static boolean validate() {
		boolean isExist = false;
		String[] valueTokens = CurrentTest.expected.split("\\#", -1);

		for (int i = 0; i < valueTokens.length; i++) {
			if (valueTokens[i].equalsIgnoreCase(CurrentTest.actual)) {
				isExist = true;
				break;
			}
		}

		if (isExist = true) {
			TestResult.addResultRecord(CurrentTest.tcId, CurrentTest.tcTitle, CurrentTest.tcStatus, CurrentTest.stepId, "'" + CurrentTest.expected + "'", "'" + CurrentTest.actual + "'", "Pass",
					CurrentTest.comments);
		} else {
			TestResult.addResultRecord(CurrentTest.tcId, CurrentTest.tcTitle, CurrentTest.tcStatus, CurrentTest.stepId, "'" + CurrentTest.expected + "'", "'" + CurrentTest.actual + "'", "Fail",
					CurrentTest.comments);
		}

		return isExist;
	}
}
