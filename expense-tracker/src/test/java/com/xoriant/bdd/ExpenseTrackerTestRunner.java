package com.xoriant.bdd;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty"},
        glue = "com.xoriant.bdd.steps",
        features= {"src/test/resources/features"})
public class ExpenseTrackerTestRunner {

}
