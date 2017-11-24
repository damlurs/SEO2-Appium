package com.ntt.acoe.framework.config;

import java.io.IOException;

import com.ntt.acoe.framework.run.ConfigData;

/**
 *
  * @author Santosh Hariprasad (NTT Badge Id: 244583,
 *         Santhosh.Hariprasad@NTTDATA.com)
 * @version 1.0
 * @since 2015-01-01
 */
public class Configuration1 {
	public static String CONFIG_FILE = "";
	public static String TESTDATA_PATH = "";
	public static String OBJECTS_FILE = "";
	public static String REPORT_PATH = "";
	public static String TEST_PLAN_FILE = "";
	public static String DRIVER = "firefox";
	public static String DRIVER_IE_PATH = "";
	public static String DRIVER_CHROME_PATH = "";
	public static boolean IS_RUNNING_FROM_TESTRUNNER = false;

	public static void updateReportPath() {
		REPORT_PATH = "";
	}

	public static String getValue(String property) throws IOException {
		return ConfigData.getValue(CONFIG_FILE, "Environment", property);
	}
}
