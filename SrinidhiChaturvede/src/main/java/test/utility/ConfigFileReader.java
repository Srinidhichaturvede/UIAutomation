/**
 * @author Srinidhi Chaturvede
 *
 */
package test.utility;

import java.util.Properties;

public class ConfigFileReader {
	
	
	public String getReportConfigPath(){
		
		Properties properties = new Properties();
		String reportConfigPath = properties.getProperty("reportConfigPath");
		if(reportConfigPath!= null) return reportConfigPath;
		else throw new RuntimeException("Report Config Path not specified in the Configuration.properties file for the Key:reportConfigPath");		
	}

}
