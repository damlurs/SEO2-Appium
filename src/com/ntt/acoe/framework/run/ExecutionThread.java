package com.ntt.acoe.framework.run;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.ntt.acoe.framework.loggers.ScriptLogger;
import com.ntt.acoe.framework.selenium.report.Reporting;
import com.ntt.acoe.framework.selenium.test.CurrentTest;
import com.ntt.acoe.framework.selenium.verify.Assert;

/*
 * @author Santosh Hariprasad (NTT Badge Id: 244583,
 *         Santhosh.Hariprasad@NTTDATA.com)
 * @version 1.0
 * @since 2015-01-01
 */
public class ExecutionThread extends Thread {
	Method m = null;

	public ExecutionThread(Method m) {
		this.m = m;
		System.out.println("Executing method from execution thread");
	}

	public void run() {
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
			Assert.fail("Exception occured in method: " + m.getName() + " while running tests.  Exception:" + e1.getMessage());
			ScriptLogger.log.info("Exception occured in method: " + m.getName() + " while running tests.  Exception:" + e1.getMessage().toString());
			ScriptLogger.log.info("Exception occured in method: " + m.getName() + " while running tests.  Exception:" + e1.getStackTrace().toString());
			System.out.println("Exception occured in method: " + m.getName() + " while running tests.  Exception:");
			e1.printStackTrace();
			// Reporting.report("FAIL", "Exception occured while running tests.
			// Exception:" + e1.getMessage());
		}
	}

}
