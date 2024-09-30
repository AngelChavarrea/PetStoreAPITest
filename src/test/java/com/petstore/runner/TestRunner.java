package com.petstore.runner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.petstore.steps"},
        plugin = {"pretty", "html:target/cucumber.html"},
        monochrome = true
)
public class TestRunner {
}
