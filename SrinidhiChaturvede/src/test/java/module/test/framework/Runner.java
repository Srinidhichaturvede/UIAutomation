package module.test.framework;

import java.io.File;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import com.cucumber.listener.ExtentProperties;
import com.cucumber.listener.Reporter;



@RunWith(Cucumber.class)
@CucumberOptions( 
		features = {"src/test/resources/features"},
        glue = {"test.framework.glue"},
        tags = {"@smokeTesting"},
        plugin = {"com.cucumber.listener.ExtentCucumberFormatter:"},
        strict = true,
    	dryRun = false 
)

//public class Runner extends AbstractTestNGCucumberTests
public class Runner {

	@BeforeClass
	public static void setup() {
		ExtentProperties extentProperties = ExtentProperties.INSTANCE;
		extentProperties.setReportPath("output/TestAutomationReport.html");
	}

	@AfterClass
	public static void teardown() {
		Reporter.loadXMLConfig(new File("src/main/resources/config/extent-config.xml"));
		Reporter.setSystemInfo("user", System.getProperty("user.name"));
		Reporter.setTestRunnerOutput("Sample test runner output message");
	}

}
