package com.ntt.acoe.framework.run;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.ntt.acoe.framework.config.Configuration;
import com.ntt.acoe.framework.config.Environment;
import com.ntt.acoe.framework.loggers.ScriptLogger;
import com.ntt.acoe.framework.selenium.FrameworkDriver;
import com.ntt.acoe.framework.selenium.report.CustomReporting;
import com.ntt.acoe.framework.selenium.report.ExcelReportUpdate;
import com.ntt.acoe.framework.selenium.report.Reporting;
import com.ntt.acoe.framework.selenium.report.TestResult;
import com.ntt.acoe.framework.selenium.report.XMLReporting;
import com.ntt.acoe.framework.selenium.test.CurrentTest;
import com.ntt.acoe.framework.selenium.verify.Assert;

/*
 * @author Santosh Hariprasad (NTT Badge Id: 244583,
 *         Santhosh.Hariprasad@NTTDATA.com)
 * @version 1.0
 * @since 2015-01-01
 */
public class TestRunner {
	public static ArrayList<Test> tests = new ArrayList<Test>();
	public static ArrayList<Script> scripts = new ArrayList<Script>();
	public static ArrayList<Property> properties = new ArrayList<Property>();
	static DateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");
	static Date d = new Date();
	public static String timeStamp = df.format(d);
	static ExecutionThread executionThread = null;
	private static String currentStatus = "Yet to start";

	public static void load(String configFile) throws IOException {
		Environment.load(configFile, "Environment");
		tests = ConfigData.getTests(configFile, "TestPlan");
		scripts = ConfigData.getScripts(configFile, "Scripts");
		Environment.put("report_path", Environment.get("report_path") + "Report_" + Environment.get("execution_environment") + "_" + Environment.get("execution_build") + "_" + TestRunner.timeStamp);
		Configuration.REPORT_PATH = Environment.get("report_path");

		// Commenting deprecated code
		/*
		 * properties = ConfigData.getProperties(configFile, "Configuration");
		 * 
		 * for (int i = 0; i < properties.size(); i++) { if
		 * (properties.get(i).property.equalsIgnoreCase("REPORT_PATH")) {
		 * Configuration.REPORT_PATH = properties.get(i).value; } else if
		 * (properties.get(i).property.equalsIgnoreCase("objects_file")) { }
		 * else if
		 * (properties.get(i).property.equalsIgnoreCase("TEST_DATA_PATH")) { }
		 * else if (properties.get(i).property.equalsIgnoreCase("DRIVER")) { }
		 * else if
		 * (properties.get(i).property.equalsIgnoreCase("DRIVER_IE_PATH")) { }
		 * else if
		 * (properties.get(i).property.equalsIgnoreCase("DRIVER_CHROME_PATH")) {
		 * } }
		 */

	}

	public static void load1(String configFile) throws IOException {
		tests = ConfigData.getTests(configFile, "TestPlan");
		scripts = ConfigData.getScripts(configFile, "Scripts");
		properties = ConfigData.getProperties(configFile, "Configuration");

		for (int i = 0; i < properties.size(); i++) {
			if (properties.get(i).property.equalsIgnoreCase("REPORT_PATH")) {
				Configuration.REPORT_PATH = properties.get(i).value;
			} else if (properties.get(i).property.equalsIgnoreCase("objects_file")) {
			} else if (properties.get(i).property.equalsIgnoreCase("test_data_path")) {
			} else if (properties.get(i).property.equalsIgnoreCase("driver")) {
			} else if (properties.get(i).property.equalsIgnoreCase("driver_ie_path")) {
			} else if (properties.get(i).property.equalsIgnoreCase("driver_ie_path")) {
			}
		}

	}

	@SuppressWarnings({})
	public static void runTests(String configFile, String packageName, String className, String methods) {
		try {
			Date d1 = new Date();
			long startTime = System.currentTimeMillis();
			TestResult.start = d1;

			Environment.load(configFile, "Environment");
			Reporting.report("DONE", "Loading configuration file from:" + configFile + " completed");
			Reporting.report("DONE", "Running tests ...");

			// ---
			Reporting.report("DONE", "Running tests from TestRunner with this test...");
			TestResult.start = d1;
			TestResult.startTime = startTime;
			Reporting.createTestScriptReport();
			Reporting.createTestCaseReport();

			try {
				Reporting.report("DONE", "Loading test for execution:" + className + " ...");
				Reporting.setTestCase(className);
				Reporting.report("DONE", "Running test:" + className + " ");
				String[] scriptMethodTokens = methods.split("\\,", -1);
				Reporting.startScript(className);
				Environment.put("TestScript", className);

				for (String scriptMethodToken : scriptMethodTokens) {
					Reporting.report("DONE", "Running test:" + className + " - Executing method:" + scriptMethodToken.trim());
					Reporting.setTestCase(packageName + "." + className + " - " + scriptMethodToken.trim());
					// System.out.println("Executing: " +
					// scripts.get(i).scriptPackage + "." +
					// scripts.get(i).scriptClass + "." +
					// scriptMethodToken.trim());
					Reporting.startMethod(packageName + "." + className + "." + scriptMethodToken.trim());
					// Environment.put("TestMethod", packageName + "." +
					// className + "." + scriptMethodToken.trim());
					Environment.put("TestMethod", scriptMethodToken.trim());

					try {
						Class c = Class.forName(packageName + "." + className);
						CurrentTest.tcId = className;
						CurrentTest.tcTitle = className;
						CurrentTest.stepId = "Step 1";
						Method m = c.getDeclaredMethod(scriptMethodToken.trim(), null);
						try {
							m.invoke(null, null);
						} catch (Exception e1) {
							Writer result = new StringWriter();
							PrintWriter printWriter = new PrintWriter(result);
							e1.printStackTrace(printWriter);
							e1.printStackTrace();
							String err = result.toString();
							int errStart = 0;
							errStart = err.indexOf("Caused by:");
							int errEnd = errStart + 100;
							if (err.lastIndexOf("For documentation on this error") < 0) {
								errEnd = errStart + 100;
							} else {
								errEnd = err.lastIndexOf("For documentation on this error");
							}
							err = err.substring(errStart, errEnd);
							CurrentTest.expected = CurrentTest.tcTitle + " - executes without any exception";
							CurrentTest.actual = err;
							// CurrentTest.validate();
						}

						Reporting.closeMethod(packageName + "." + className + "." + scriptMethodToken.trim());

					} catch (ClassNotFoundException e) {
						Reporting.report("ERROR", "Running test:" + className + " - Executing method:" + scriptMethodToken.trim() + " - Given class not found, Exception:" + e.getMessage());
						System.err.println("ClassNotFoundException");
						CurrentTest.expected = CurrentTest.tcTitle + " - executes without any exception";
						CurrentTest.actual = e.getMessage();
						// CurrentTest.validate();
					} catch (NoSuchMethodException e) {
						Reporting.report("ERROR", "Running test:" + className + " - Executing method:" + scriptMethodToken.trim() + " - Given method not found, Exception:" + e.getMessage());
						System.err.println("NoSuchMethodException");
						CurrentTest.expected = CurrentTest.tcTitle + " - executes without any exception";
						CurrentTest.actual = e.getMessage();
						CurrentTest.validate();
					} catch (SecurityException e) {
						Reporting.report("ERROR",
								"Running test:" + className + " - Executing method:" + scriptMethodToken.trim() + " - Security is not allowed to execute, Exception:" + e.getMessage());
						System.err.println("SecurityException");
						CurrentTest.expected = CurrentTest.tcTitle + " - executes without any exception";
						CurrentTest.actual = e.getMessage();
						CurrentTest.validate();
					} catch (IllegalArgumentException e) {
						Reporting.report("ERROR", "Running test:" + className + " - Executing method:" + scriptMethodToken.trim() + " - Illegal arguments to method, Exception:" + e.getMessage());
						System.err.println("IllegalArgumentException");
						CurrentTest.expected = CurrentTest.tcTitle + " - executes without any exception";
						CurrentTest.actual = e.getMessage();
						CurrentTest.validate();
					}

					Reporting.closeScript(className + "," + packageName + "." + className + "." + scriptMethodToken.trim());
					Reporting.report("DONE", "Running test:" + className + " - Executing method:" + scriptMethodToken.trim() + " completed");
				}

			} catch (Exception e) {
				Reporting.report("ERROR", "Exception occured while running tests.  Exception:" + e.getMessage());
				Reporting.report("ERROR", "Exception occured while running tests.  Exception Stack Trace:" + e.getStackTrace());
			}
			// ---

			Reporting.report("DONE", "Running tests completed");

			Date d2 = new Date();
			long endTime = System.currentTimeMillis();
			TestResult.end = d2;
			TestResult.endTime = endTime;
			long difference = endTime - startTime;
			Reporting.report("DONE", "Elapsed minutes: " + difference / 60000);
			Reporting.report("DONE", "Started On: " + d1);
			Reporting.report("DONE", "Completed On: " + d2);
			Reporting.report("DONE", "Closing driver ...");
			FrameworkDriver.driver.close();
			FrameworkDriver.driver.quit();
			Reporting.report("DONE", "Closing driver completed");
			Reporting.report("DONE", "Completed execution");
		} catch (Exception e) {
			e.printStackTrace();
			Reporting.report("ERROR", "Exception found while executing. Exception:" + e.getMessage());
			Reporting.report("ERROR", "Exception found while executing. Exception Stack Trace:" + e.getStackTrace());

			try {
				Reporting.report("DONE", "Saving test report after exception ...");
				TestResult.endTime = new Date().getTime();
				Reporting.report("DONE", "Saving test report after exception completed");
			} catch (Exception e1) {
				Reporting.report("ERROR", "Exception found while saving report after exception. Exception:" + e1.getMessage());
				Reporting.report("ERROR", "Exception found while saving report after exception. . Exception Stack Trace:" + e1.getStackTrace());
			}
			try {
				FrameworkDriver.driver.close();
				FrameworkDriver.driver.quit();
			} catch (Exception e2) {
				e2.printStackTrace();
				Reporting.report("ERROR", "Exception found while closing driver. Exception:" + e2.getMessage());
				Reporting.report("ERROR", "Exception found while closing driver. Exception Stack Trace:" + e2.getStackTrace());
			}
		}
	}

	@SuppressWarnings({})
	public static void runTests(String configFile) {
		try {
			Date d1 = new Date();
			long startTime = System.currentTimeMillis();
			TestResult.start = d1;

			Environment.load(configFile, "Environment");
			tests = ConfigData.getTests(configFile, "TestPlan");
			scripts = ConfigData.getScripts(configFile, "Scripts");
			Environment.put("report_path",
			Environment.get("report_path") + "/Report_" + Environment.get("execution_environment") + "_" + Environment.get("execution_build") + "_" + TestRunner.timeStamp);
			Environment.put("log_path", Environment.get("log_path"));
			Configuration.REPORT_PATH = Environment.get("report_path");
			Reporting.createNewReportPath();

			Reporting.report("DONE", "Loading configuration file from:" + configFile + " completed");
			Reporting.report("DONE", "Running tests ...");
			runTests();
			Reporting.report("DONE", "Running tests completed");

			Date d2 = new Date();
			long endTime = System.currentTimeMillis();
			TestResult.end = d2;
			TestResult.endTime = endTime;
			long difference = endTime - startTime;
			Reporting.report("DONE", "Elapsed minutes: " + difference / 60000);
			Reporting.report("DONE", "Started On: " + d1);
			Reporting.report("DONE", "Completed On: " + d2);
			Reporting.report("DONE", "Generating custom test report ....");
			CustomReporting.generateCustomReports();
			Reporting.report("DONE", "Generating custom test report completed");
			Reporting.report("DONE", "Closing driver ...");
			try {
				if(FrameworkDriver.driver!=null){
					FrameworkDriver.driver.close();
					FrameworkDriver.driver.quit();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			Reporting.report("DONE", "Closing driver completed");
			Reporting.report("DONE", "Completed execution");
			//System.exit(0);
		} catch (Exception e) {
			StringWriter exceptions = new StringWriter();
			e.printStackTrace(new PrintWriter(exceptions));
			Reporting.report("ERROR", "San1 Exception found while executing. Exception:" + e.getMessage());
			Reporting.report("ERROR", "Exception found while executing. Exception Stack Trace:" + exceptions.toString());

			try {
				Reporting.report("DONE", "Saving test report after exception ...");
				TestResult.endTime = new Date().getTime();
				// JLLReport.saveReport();
				Reporting.report("DONE", "Saving test report after exception completed");
			} catch (Exception e1) {
				Reporting.report("ERROR", "Exception found while saving report after exception. Exception:" + e1.getMessage());
				Reporting.report("ERROR", "Exception found while saving report after exception. . Exception Stack Trace:" + e1.getStackTrace());
			}
			try {
				FrameworkDriver.driver.close();
				FrameworkDriver.driver.quit();
			} catch (Exception e2) {
				Reporting.report("ERROR", "Exception found while closing driver. Exception:" + e2.getMessage());
				Reporting.report("ERROR", "Exception found while closing driver. Exception Stack Trace:" + e2.getStackTrace());
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void runTests() throws IOException {		
		Reporting.report("DONE", "Running tests from TestRunner ...");
		long startTime = System.currentTimeMillis();
		Date d1 = new Date();
		TestResult.start = d1;
		TestResult.startTime = startTime;
		Reporting.createTestScriptReport();
		Reporting.createTestScriptExcelReport();
		Reporting.createTestCaseReport();
		XMLReporting.createNewXMLReport();
		Environment.put("timestamp", TestRunner.timeStamp);
		// Reporting.cleanupScreenshots();

		try {
			for (int i = 0; i < tests.size(); i++) {
				if(currentStatus.trim().equalsIgnoreCase("STOP ALL TESTS")){
					break;
				}
				
				Reporting.report("DONE", "Loading test(" + i + ") for execution:" + tests.get(i).testCase + " ...");
				Reporting.setTestCase(tests.get(i).testCase);
				if (tests.get(i).canExecute) {
					Reporting.report("DONE", "Running test:" + tests.get(i).testCase + " ");
					String[] scriptMethodTokens = scripts.get(i).scriptMethod.split("\\,", -1);
					Reporting.startScript(tests.get(i).testCase);
					Environment.put("TestScript", tests.get(i).testCase);
					ExcelReportUpdate.updateStartTime();

					for (String scriptMethodToken : scriptMethodTokens) {
						Reporting.report("DONE", "Running test:" + tests.get(i).testCase + " - Executing method:" + scriptMethodToken.trim());
						Reporting.setTestCase(tests.get(i).testCase + " - " + scriptMethodToken.trim());
						Reporting.startMethod(scripts.get(i).scriptPackage + "." + scripts.get(i).scriptClass + "." + scriptMethodToken.trim());
						Environment.put("TestMethod", scriptMethodToken.trim());

						try {
							Class c = Class.forName(scripts.get(i).scriptPackage + "." + scripts.get(i).scriptClass);
							CurrentTest.tcId = scripts.get(i).scriptClass;
							CurrentTest.tcTitle = scripts.get(i).scriptClass;
							CurrentTest.stepId = "Step 1";
							Method m = c.getDeclaredMethod(scriptMethodToken.trim(), null);							
							System.out.println("Executing method [" + scriptMethodToken.trim() + "]...");
							// ExecutionThread R2 = new ExecutionThread(m);
							executionThread = new ExecutionThread(m);
							executionThread.start();
							executionThread.join();							
							System.out.println("Executing method [" + scriptMethodToken.trim() + "]... completed");
						} catch (ClassNotFoundException e) {
							Reporting.report("ERROR",
									"Running test:" + tests.get(i).testCase + " - Executing method:" + scriptMethodToken.trim() + " - Given class not found, Exception:" + e.getMessage().toString());
							System.err.println("ClassNotFoundException");
							CurrentTest.expected = CurrentTest.tcTitle + " - executes without any exception";
							CurrentTest.actual = e.getMessage();
							// CurrentTest.validate();
						} catch (NoSuchMethodException e) {
							Reporting.report("ERROR",
									"Running test:" + tests.get(i).testCase + " - Executing method:" + scriptMethodToken.trim() + " - Given method not found, Exception:" + e.getMessage().toString());
							System.err.println("NoSuchMethodException");
							CurrentTest.expected = CurrentTest.tcTitle + " - executes without any exception";
							CurrentTest.actual = e.getMessage();
							CurrentTest.validate();
						} catch (SecurityException e) {
							Reporting.report("ERROR",
									"Running test:" + tests.get(i).testCase + " - Executing method:" + scriptMethodToken.trim() + " - Security is not allowed to execute, Exception:" + e.getMessage());
							System.err.println("SecurityException");
							CurrentTest.expected = CurrentTest.tcTitle + " - executes without any exception";
							CurrentTest.actual = e.getMessage();
							CurrentTest.validate();
						} catch (IllegalArgumentException e) {
							Reporting.report("ERROR", "Running test:" + tests.get(i).testCase + " - Executing method:" + scriptMethodToken.trim() + " - Illegal arguments to method, Exception:"
									+ e.getMessage().toString());
							System.err.println("IllegalArgumentException");
							CurrentTest.expected = CurrentTest.tcTitle + " - executes without any exception";
							CurrentTest.actual = e.getMessage();
							CurrentTest.validate();
						} catch (Exception e) {
							Reporting.closeMethod(scripts.get(i).scriptPackage + "." + scripts.get(i).scriptClass + "." + scriptMethodToken.trim());
							Reporting.report("FAIL",
									"Running test:" + tests.get(i).testCase + " - Executing method:" + scriptMethodToken.trim() + " - Exception occured, Exception:" + e.getMessage().toString());
							System.err.println("IllegalArgumentException");
							CurrentTest.expected = CurrentTest.tcTitle + " - executes without any exception";
							CurrentTest.actual = e.getMessage();
							CurrentTest.validate();
						}

						Reporting.closeMethod(scripts.get(i).scriptPackage + "." + scripts.get(i).scriptClass + "." + scriptMethodToken.trim());
						Reporting.closeScript(scripts.get(i).testCase + "," + scripts.get(i).scriptPackage + "." + scripts.get(i).scriptClass + "." + scriptMethodToken.trim());
						Reporting.report("DONE", "Running test:" + tests.get(i).testCase + " - Executing method:" + scriptMethodToken.trim() + " completed");
					}
				} else {
					Reporting.report("DONE", "Skipping test:" + tests.get(i).testCase + " ");
				}
			}
			
			// Close reporting
			Reporting.closeReport();
		} catch (Exception e) {
			Assert.fail("Exception occured while running tests.  Exception:" + e.getMessage());
			ScriptLogger.log.info("Exception occured while running tests.  Exception:" + e.getMessage().toString());
			ScriptLogger.log.info("Exception occured while running tests.  Exception:" + e.getStackTrace().toString());
			System.out.println("Exception occured while running tests.  Exception Stack Trace:");
			e.printStackTrace();
		}
		
	}

	public static String getProperty(String prop) {
		String value = "";
		for (int i = 0; i < properties.size(); i++) {
			if (properties.get(i).property.trim().equalsIgnoreCase(prop.trim())) {
				value = properties.get(i).value.trim();
			}
		}
		return value;
	}

	public static void stopThisTest(String message) {
		System.out.println("Stopping executing this test further due to: " + message);
		Assert.fail("Stopping executing this test further due to: " + message);
		executionThread.interrupt();

		executionThread = null;
	}

	public static void stopExecution(String message) {
		TestRunner.stopThisTest("Stopping executing this test further due to: " + message);
		Assert.fail("Stopping executing all tests further due to: " + message);
		currentStatus = "STOP ALL TESTS";
	}
	
	public static void main(String args[])
			throws IOException, ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if (args.length < 1) {
			System.out.println("Please provide configuration xls file");
			System.out.println("Usage: com.dell.acoe.framework.run.TestRunner <C:/temp/config.xls>");
			System.exit(-1);
		} else {
			try {
				TestRunner.load(args[0]);
				TestRunner.runTests();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

	}
}
