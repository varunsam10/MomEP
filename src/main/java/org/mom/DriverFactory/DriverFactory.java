package org.mom.DriverFactory;


import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.mom.utils.Environment;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class DriverFactory {

    private static WebDriver webDriver;
    Properties prop;

    static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public WebDriver getDriver() {
        return driver.get();
    }

    public void setDriver(WebDriver driverRef) {
        driver.set(driverRef);
    }

    public void unload() {
        if (Objects.nonNull(webDriver)) {
            getDriver().close();
            getDriver().quit();
            driver.remove();
        }
    }

    public void initDriver(String browser) {
        if (Objects.isNull(webDriver)) {
            switch (browser) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    DesiredCapabilities caps = new DesiredCapabilities();
                    ChromeOptions chOptions = new ChromeOptions();
                    Map<String, Object> chromePrefs = new HashMap<String, Object>();
                    chromePrefs.put("credentials_enable_service", false);
                    chOptions.setExperimentalOption("prefs", chromePrefs);
                    chOptions.addArguments("--disable-plugins", "--disable-extensions",
                            "--disable-popup-blocking");
                    caps.setCapability(ChromeOptions.CAPABILITY, chOptions);
                    caps.setCapability("applicationCacheEnabled", false);
                    webDriver = new ChromeDriver();
                    setDriver(webDriver);
                    webDriver.get(prop.getProperty("url"));
                    break;

                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    webDriver = new FirefoxDriver();
                    setDriver(webDriver);
                    webDriver.get(prop.getProperty("url"));
                    break;

            }
        }
    }

    public Properties init_prop() {
        FileInputStream ip = null;
        prop = new Properties();

        // mvn command line arg:
        // mvn clean install -Denv="qa"

        String envName = System.getProperty("env");
        System.out.println("Running tests on environment: " + envName);

        if (envName == null) {
            System.out.println("No env is given ..... hence running it on local MAC");
            try {
                ip = new FileInputStream("src/main/resources/config.properties");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {

            try {
                switch (envName.toLowerCase()) {
                    case Environment.ENV_QA:
                        ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
                        break;
                    case Environment.ENV_DEV:
                        ip = new FileInputStream("./src/test/resources/config/dev.config.properties");
                        break;
                    case Environment.ENV_STAGE:
                        ip = new FileInputStream("./src/test/resources/config/stage.config.properties");
                        break;
                    case Environment.ENV_UAT:
                        ip = new FileInputStream("./src/test/resources/config/uat.config.properties");
                        break;
                    case Environment.ENV_PROD:
                        ip = new FileInputStream("./src/test/resources/config/config.properties");
                        break;

                    default:
                        System.out.println("please pass the right environment value..." + envName);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        try {
            prop.load(ip);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }
}
