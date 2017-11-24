package com.ntt.acoe.framework.selenium.report;

import java.lang.reflect.Method;

import com.ntt.acoe.framework.config.Environment;
import com.ntt.acoe.framework.run.TestRunner;
import com.ntt.acoe.framework.selenium.verify.Assert;

/*
* @author Santosh Hariprasad (NTT Badge Id: 244583,
*         Santhosh.Hariprasad@NTTDATA.com)
* @version 1.0
* @since 2015-01-01
*/

public class CustomReporting {

	public static void generateCustomReports() {

		String className = "";
		String methodName = "";
		try {
			String customReportGenerator = Environment.get("custom_reporter").trim();
			//Assert.done("Custom Report Generator is configured as: "+customReportGenerator);
			if(!customReportGenerator.contains(".")){
				//Assert.done(customReportGenerator+" is not a valid CustomReportGenerator, it needs package.Class.method format and with two String args");
				return;
			}else{
				String[] tokens = customReportGenerator.split("\\.", -1);
				System.out.println(tokens.length);
				System.out.println(tokens);
				if(tokens.length<2){
					//Assert.done(customReportGenerator+" is not a valid CustomReportGenerator, it needs package.Class.method format after splitting on .");
					return;
				}else{
					try{
						methodName = tokens[tokens.length-1];
						//Assert.done(customReportGenerator+" Method:"+methodName);
						
						for(int i=0; i<tokens.length-1; i++){
							className = className+tokens[i]+".";
						}
						if(className.endsWith(".")){
							className = className.substring(0, className.length()-1);
						}
						
						//Assert.done(customReportGenerator+" Class:"+className);
						Class<?> c = Class.forName(className);
						Method m = c.getDeclaredMethod(methodName.trim(), new Class[]{String.class, String.class});
						try {
							String dest = Environment.get("report_path") + "/Custom_TestScriptReport_" + TestRunner.timeStamp + ".html"; 
							if(Environment.get("custom_report_filename").trim()!=null){
								if(!Environment.get("custom_report_filename").trim().equals("")){
									dest = Environment.get("custom_report_filename").trim();
								}
							}
							
							m.invoke(null, Environment.get("report_path") + "/TestScriptReport_" + TestRunner.timeStamp + ".xml", dest);
						} catch (SecurityException e) {
							//Assert.error(e, "SecurityException while generating Custom report");
						} catch (IllegalArgumentException e) {
							//Assert.error(e, "IllegalArgumentException while generating Custom report");
						} catch (Exception e) {
							//Assert.error(e, "Exception while generating Custom report");
						}
				}catch(Exception e){
					//Assert.error(e, "There is an exception while generating custom reports.  Please check the value for the property 'custom_reporter' and 'custom_report_filename' in Environment sheet in config file. Also please check if the custom report generation classes or jars added to build path or classpaths");
				}
				}
			}
			
			
		} catch (Exception e) {
			//Assert.done("There is an exception while generating custom reports.  Please check the value for the property 'custom_reporter' and 'custom_report_filename' in Environment sheet in config file. Also please check if the custom report generation classes or jars added to build path or classpaths");
		}
	}
}
