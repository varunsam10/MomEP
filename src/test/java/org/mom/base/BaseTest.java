package org.mom.base;

import org.mom.DriverFactory.DriverFactory;
import org.mom.PageObjects.LandingPage;
import org.openqa.selenium.By;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.asserts.SoftAssert;

import java.util.Properties;

public class BaseTest {

    DriverFactory df;
    protected Properties prop;
    protected LandingPage landingPage;

    @BeforeTest
    public void setup() {
        df = new DriverFactory();
        prop = df.init_prop();
        df.initDriver(prop.getProperty("browser"));
        landingPage = new LandingPage();
    }

    @AfterTest
    public void tearDown() {
        df.unload();
    }
}
