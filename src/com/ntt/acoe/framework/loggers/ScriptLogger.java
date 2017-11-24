package com.ntt.acoe.framework.loggers;

import java.io.IOException;

import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;

import com.ntt.acoe.framework.config.Environment;

/**
 * @author Santosh Hariprasad (NTT Badge Id: 244583,
 *         Santhosh.Hariprasad@NTTDATA.com)
 * @version 1.0
 * @since 2015-01-01
 */
public class ScriptLogger {
	public static org.apache.log4j.Logger log = initialise();

	public static org.apache.log4j.Logger initialise() {
		org.apache.log4j.Logger log1 = org.apache.log4j.Logger.getLogger(ErrorLogger.class.getName());
		try {
			// String filePath = "C:/Temp/myscriptlog.log";
			PatternLayout layout = new PatternLayout("%-5p %d %m%n");
			RollingFileAppender appender;
			// appender = new RollingFileAppender(layout, filePath);
			// appender = new RollingFileAppender(layout,
			// Configuration.REPORT_PATH+"/Script.log");
			System.out.println("Log Path:" + Environment.get("log_path") + "/Execution.log");
			appender = new RollingFileAppender(layout, Environment.get("log_path") + "/Execution.log");
			appender.setName("myFirstLog");
			appender.setMaxFileSize("1MB");
			appender.activateOptions();
			log1.addAppender(appender);
			log1.setLevel(org.apache.log4j.Level.INFO);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return log1;
	}
}
