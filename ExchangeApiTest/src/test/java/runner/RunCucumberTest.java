package runner;

import org.junit.runner.RunWith;

import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.Cucumber;

@RunWith(Cucumber.class)

//Setting cucumber options
@CucumberOptions(plugin = {"pretty", "html:target/cucumber-html-report", 
							"json:target/cucumber-reports/cucumber.json",
							"junit:target/cucumber-reports/cucumber.xml",
							"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"},
				features = "src/test/resources/features", 
				glue = "stepDefinitions", 
				tags = "@testOnly", 
				monochrome = true)

public class RunCucumberTest {
}
