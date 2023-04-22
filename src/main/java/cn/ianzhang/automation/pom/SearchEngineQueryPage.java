package cn.ianzhang.automation.pom;
import cn.ianzhang.automation.common.PageTools;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;

public class SearchEngineQueryPage extends LoadableComponent<SearchEngineQueryPage> {

    private  static final Logger logger = LogManager.getLogger("SearchEngineQueryPage");

    public  WebDriver driver;

    public void SearchPage(WebDriver driver,String Url){
        this.driver = driver;
        driver.get(Url);
    }
    private WebElement elementBingInput;

    @Override
    protected void load(){

    }
    @Override
    protected void isLoaded() throws Error{
        logger.info("---------Check whether the page is loaded successfully.------------");
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class);
        wait.until(((new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driverObject){
                logger.info("---------Waiting Bing Dom loading complete.------------");
                return (Boolean) ((JavascriptExecutor) driverObject).executeScript("return document.readyState==='complete'");
            }
        })));
        if(!PageTools.myElementIsClickable(this.driver,By.xpath("//*[@id=\"sb_form_q\"]"))){
            throw new Error("Bing home page failed to load!");
        }
        logger.info("--------- Bing home page is loading complete.------------");
    }

    public String getPageTitle(){
        return driver.getTitle();
    }

    public void enterSearch(String searchText){
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(30));
        WebElement webElementBingInput = driver.findElement(By.xpath("//*[@id=\"sb_form_q\"]"));
        wait.until(ExpectedConditions.elementToBeClickable(webElementBingInput));
        webElementBingInput.clear();
        webElementBingInput.sendKeys(searchText + Keys.ENTER);
    }

    public void scrollToFoot(){
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0,document.body.scrollHeight);");
    }

    public void switchToSearchedSecondPage(){
        scrollToFoot();
        ((JavascriptExecutor) driver).executeScript("document.querySelector(\"#b_results > li.b_pag > nav > ul > li:nth-child(2) > a\").click();");

    }

    public List<WebElement> collectResults(){
        return driver.findElements(By.xpath("//ol[@id='b_results']//h2/a"));
    }
}
