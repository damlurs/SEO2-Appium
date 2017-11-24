package com.ntt.acoe.framework.run;

/*
 * @author Santosh Hariprasad (NTT Badge Id: 244583,
 *         Santhosh.Hariprasad@NTTDATA.com)
 * @version 1.0
 * @since 2015-01-01
 */
public class Test {
	public int sno = 0;
	public String testCase = "";
	public boolean canExecute = false;

	public Test(int sno, String testCase, String canExecute) {
		this.sno = sno;
		this.testCase = testCase;
		if (canExecute.equalsIgnoreCase("Yes") || canExecute.equalsIgnoreCase("TRUE") || canExecute.equalsIgnoreCase("1")) {
			this.canExecute = true;
		} else {
			this.canExecute = false;
		}
	}

	public Test(int sno, String testCase, boolean canExecute) {
		this.sno = sno;
		this.testCase = testCase;
		this.canExecute = canExecute;
	}
}
