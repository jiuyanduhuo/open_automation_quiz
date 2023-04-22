package cn.ianzhang.automation.cucumber;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;


import java.time.Duration;
import java.util.*;
import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class StepDefinitions {

    private  static Logger logger = LogManager.getLogger("StepDefinitions");

    private WebDriver driver;

    @Given("打开浏览器")
    public void openDriver() {
//		String browserName = System.getenv("browser");
        String browserName="msedge";
// 		String browserName="chrome";


        if(("msedge").equals(browserName)){
            EdgeOptions edgeOptions = new EdgeOptions();
            edgeOptions.addArguments("--remote-allow-origins=*");
            driver = new EdgeDriver(edgeOptions);
            logger.info("EdgeDriver is created!");

        }else if(("chrome").equals(browserName)){
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--remote-allow-origins=*");
            driver = new ChromeDriver(chromeOptions);
            logger.info("ChromeDriver is created!");

        }else {

            logger.info("None is created!");

        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
    }

    @When("用户打开企业复工申请资料提交表{string}")
    public void openUrl(String arg0) {
        System.out.println("打开的网址为： "+ arg0);
        driver.get(arg0);
    }

    @When("用户在第一页选择{}")
    public void userChoose(String arg0) {
        logger.info("用户在第一页选择 "+ arg0);
        ((JavascriptExecutor) driver).executeScript("document.querySelector(\"#root > div > form > div.published-form__body > div > div.ant-col.field-container.field.ant-col-xs-24.ant-col-sm-24.ant-col-md-24 > div > div > div.ant-col.ant-form-item-control > div.ant-form-item-control-input > div > div > div > div > div:nth-child(2) > div > div > div > label > span:nth-child(2) > span.other-choice-option-name\").click();");

    }

    @And("用户截图{}")
    public void pngFirst(String arg0) {
        getScreenShot(arg0);
    }

    @And("用户点击下一页按钮")
    public void clickNext() {
        driver.findElement(By.xpath("//*[@id=\"root\"]//button/span[text()='下一页']/..")).click();
    }

    @Then("选中了{}")
    public void isCheck(String arg0) {
        logger.info("选中了 "+ arg0);

        boolean selected = driver.findElement(By.cssSelector("#root > div > form > div.published-form__body > div > div.ant-col.field-container.field.ant-col-xs-24.ant-col-sm-24.ant-col-md-24 > div > div > div.ant-col.ant-form-item-control > div.ant-form-item-control-input > div > div > div > div > div.choice-wrapper.choice-wrapper--vertical.choice-wrapper--selected > div > div > div > label > span.ant-radio.ant-radio-checked > input"))
                .isSelected();
        logger.info("打印是否选中 "+ selected);

        assertThat(selected).isEqualTo(true);


    }


    public void getScreenShot(String name) {
        try {
            File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(file, new File(String.format("src/main/resources/png/%s.png", name)));
        } catch (IOException e) {
            logger.error(e.toString());
        }
    }

    public String getData() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
        return simpleDateFormat.format(date);
    }

    @Then("关闭浏览器")
    public void close() {
        driver.close();
    }

    @And("填写申请日期运行脚本的当年元旦日期")
    public void addDataInfo() {
        String data = getData();
        driver.findElement(By.xpath("//*[@id=\"root\"]/div/form/div[2]/div/div[4]/div/div/div[2]/div[1]/div/div/div/div/span/input")).sendKeys(data);
    }

    @And("填写申请人 {string}")
    public void addUser(String arg0) {
        driver.findElement(By.xpath("//*[@id=\"root\"]/div/form/div[2]/div/div[6]/div/div/div[2]/div[1]/div/div/div/span/input")).sendKeys(arg0);
    }

    @And("填写联系方式 {string}")
    public void addPhone(String arg0) {
        driver.findElement(By.xpath("//*[@id=\"root\"]/div/form/div[2]/div/div[8]/div/div/div[2]/div[1]/div/div/div/div/span/input")).sendKeys(arg0);
    }

    @When("滑动到底部")
    public void scrollToFoot() {
        driver.switchTo().defaultContent();
        By frame = By.xpath("//*[@class='iframe iframe']");
        // 切换到 iframe
        WebElement iframe = driver.findElement(frame);
        driver.switchTo().frame(iframe);
        // 执行滚动操作
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    @Then("等待 {int} 秒")
    public void waitTime(int arg0) throws InterruptedException {
        Thread.sleep(arg0 * 1000L);
    }



    @And("填写单位 {string}")
    public void addUnit(String arg0) {
        driver.findElement(By.xpath(
                "//*[@id=\"root\"]/div/form/div[2]/div/div[4]/div/div/div[2]/div[1]/div/div/div/span/input"
        )).sendKeys(arg0);
    }

    @And("填写人数 {int}")
    public void addPersonNumber(int arg0) {
        driver.findElement(By.xpath(
                "//*[@id=\"root\"]/div/form/div[2]/div/div[6]/div/div/div[2]/div[1]/div/div/div/div/div/div/div[2]/input"
        )).sendKeys(String.valueOf(arg0));
    }

    @And("填写湖北籍员工、前往湖北以及与湖北人员密切接触的员工（人数） {int}")
    public void addSpecialNmber(int arg0) {
        driver.findElement(By.xpath(
                "//*[@id=\"root\"]/div/form/div[2]/div/div[10]/div/div/div[2]/div[1]/div/div/div/div/div/div/div[2]/input"
        )).sendKeys(String.valueOf(arg0));
    }

    @And("填写时间测试时间")
    public void addTestTime() {
        driver.findElement(By.xpath(
                "//*[@id=\"root\"]/div/form/div[2]/div/div[8]/div/div/div[2]/div[1]/div/div/div/div/span/input"
        )).sendKeys(getData());
    }

    @And("填写单位负责人 {string}")
    public void addManageName(String arg0) {
        driver.findElement(By.xpath(
                "//*[@id=\"root\"]/div/form/div[2]/div/div[12]/div/div/div[2]/div[1]/div/div/div/span/input"
        )).sendKeys(arg0);
    }

    @And("填写疫情防控方案 {string}")
    public void addText(String arg0) {
        driver.findElement(By.xpath(
                "//*[@id=\"root\"]/div/form/div[2]/div/div[16]/div/div/div[2]/div[1]/div/div[2]/div/span/textarea"
        )).sendKeys(arg0);
    }

    @And("填写新联系方式 {string}")
    public void addPhoneNumber(String arg0) {
        driver.findElement(By.xpath(
                " //*[@id=\"root\"]/div/form/div[2]/div/div[14]/div/div/div[2]/div[1]/div/div/div/div/span/input"
        )).sendKeys(arg0);
    }

    @And("点击提交")
    public void addCommit() {
        driver.findElement(By.xpath("//*[@id=\"root\"]/div/form/div[3]/div[1]/button[2]")).click();
    }

    @Then("提交成功验证")
    public void addSuccess() {
        String text = driver.findElement(By.xpath("/html/body/div/div[2]/div[2]/div/div[1]")).getText();
        boolean flag = text.contains("提交成功");
        assertThat(flag).isEqualTo(true);

    }
}
