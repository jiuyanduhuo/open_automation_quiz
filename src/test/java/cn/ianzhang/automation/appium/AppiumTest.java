package cn.ianzhang.automation.appium;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.logging.Logger;

import io.appium.java_client.android.options.UiAutomator2Options;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import io.appium.java_client.android.AndroidDriver;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static cn.ianzhang.automation.common.PageTools.sleep;

@SpringBootTest
public class AppiumTest {
    private AndroidDriver driver;
    private  static Logger logger = Logger.getLogger("AppiumTest.class");
    public static final String url ="http://127.0.0.1:4723";

    @BeforeEach
    public void setUp() throws MalformedURLException {

        UiAutomator2Options options = new UiAutomator2Options()
                .setUdid("7adc488c")
                .setPlatformVersion("10")
                .setAutomationName("uiautomator2")
                .setPlatformName("android")
                .setNoReset(true)
                .setFullReset(true)
                .printPageSourceOnFindFailure()
                .setApp("src/test/resources/apk/app-debug-1.0.0.apk");

        URL remoteUrl = new URL(url);
        logger.info("Starting Appium ... ");

        driver = new AndroidDriver(remoteUrl,options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));


    }


    @Test
    public void addAlarm() {

        logger.info("Starting test ... ");

        WebElement elText =  driver.findElement(By.id("cn.ianzhang.android:id/textview_first"));
        assertThat("Hello lan").isEqualTo(elText.getText());

        WebElement el3 =  driver.findElement(By.id("cn.ianzhang.android:id/button_first"));
        assertThat("NEXT").isEqualTo(el3.getText());
        el3.click();
        sleep(5);
        WebElement el4 =  driver.findElement(By.id("cn.ianzhang.android:id/button_second"));
        assertThat("PREVIOUS").isEqualTo(el4.getText());
        el4.click();
        sleep(5);

        WebElement el5 =  driver.findElement(By.id("cn.ianzhang.android:id/fab"));
        el5.click();
        WebElement el6 =  driver.findElement(By.id("cn.ianzhang.android:id/snackbar_text"));
        assertThat("This is a demo for Appium Inspector").isEqualTo(el6.getText());

        logger.info("Ended test! ");

    }

    @AfterEach
    public void tearDown() {
        if(driver != null){
            driver.quit();
        }
    }
}
