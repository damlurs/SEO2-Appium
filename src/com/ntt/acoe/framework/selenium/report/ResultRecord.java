package com.ntt.acoe.framework.selenium.report;

/*
 * @author Santosh Hariprasad (NTT Badge Id: 244583,
 *         Santhosh.Hariprasad@NTTDATA.com)
 * @version 1.0
 * @since 2015-01-01
 */
public class ResultRecord {

	public String tcId;
	public String tcTitle;
	public String tcStatus;
	public String stepId;
	public String expected;
	public String actual;
	public String stepStatus;
	public String comments;

	public ResultRecord(String tcId, String tcTitle, String tcStatus, String stepId, String expected, String actual, String stepStatus, String comments) {
		this.tcId = tcId;
		this.tcTitle = tcTitle;
		this.tcStatus = tcStatus;
		this.stepId = stepId;
		this.expected = expected;
		this.actual = actual;
		this.stepStatus = stepStatus;
		this.comments = comments;
	}
}
