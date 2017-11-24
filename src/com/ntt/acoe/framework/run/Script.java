package com.ntt.acoe.framework.run;

/*
 *@author Santosh Hariprasad (NTT Badge Id: 244583,
 *         Santhosh.Hariprasad@NTTDATA.com)
 * @version 1.0
 * @since 2015-01-01
 */
public class Script {
	public int sno = 0;
	public String testCase = "";
	public String scriptPackage = "";
	public String scriptClass = "";
	public String scriptMethod = "";

	public Script(int sno, String testCase, String scriptPackage, String scriptClass, String scriptMethod) {
		this.sno = sno;
		this.testCase = testCase;
		this.scriptPackage = scriptPackage;
		this.scriptClass = scriptClass;
		this.scriptMethod = scriptMethod;
	}

}
