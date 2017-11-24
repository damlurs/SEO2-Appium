package com.ntt.acoe.framework.loggers;

import java.io.IOException;

import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;

/**
 * @author Santosh Hariprasad (NTT Badge Id: 244583,
 *         Santhosh.Hariprasad@NTTDATA.com)
 * @version 1.0
 * @since 2015-01-01
 */
public class Logger {
	static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(Logger.class.getName());

	public static void main(String[] args) throws IOException {
		try {
			String filePath = "C:/Temp/mylog.log";
			PatternLayout layout = new PatternLayout("%-5p %d %m%n");
			RollingFileAppender appender = new RollingFileAppender(layout, filePath);
			appender.setName("myFirstLog");
			appender.setMaxFileSize("1MB");
			appender.activateOptions();
			org.apache.log4j.Logger.getRootLogger().addAppender(appender);
		} catch (Exception e) {

			System.out.println("Message:\n" + e.getMessage());
			System.out.println("Class:" + e.getClass());
			System.out.println("Cause:" + e.getCause());
			System.out.println("Strace:\n");
			e.printStackTrace();

			StackTraceElement[] stes = e.getStackTrace();
			System.out.println("stes len:" + stes.length);
			for (StackTraceElement el : stes) {
				System.out.println("el: Class:" + el.getClassName() + "  File:" + el.getFileName() + "  Line:" + el.getLineNumber() + "  Method:" + el.getMethodName());
			}
		}

		log.setLevel(org.apache.log4j.Level.INFO);
		log.debug("Hello this is a debug message");
		log.info("Hello this is an info message");
	}
}
