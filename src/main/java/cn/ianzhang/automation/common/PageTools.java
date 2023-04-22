package cn.ianzhang.automation.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class PageTools {

    private  static Logger logger = LogManager.getLogger("PageTools");

    public WebElement waitElementReady(WebDriver driver, By by,int time){

//        WebElement webElement=null;
//        try{
          WebElement   webElement = new WebDriverWait(driver, Duration.ofSeconds(time))
                    .until(ExpectedConditions.visibilityOfElementLocated(by));
//        }catch(Exception e){
//            logger.error("[" + by +"]: no such element!!!" );
//        }
        return webElement;
    }

    public static boolean myElementIsClickable(WebDriver driver,By by){
        try{
            new WebDriverWait(driver,Duration.ofSeconds(30)).until(ExpectedConditions.elementToBeClickable(by));

        }catch (WebDriverException ex){
            return false;
        }
        return true;
    }

    public static void sleep(long timeout){
        try{
            Thread.sleep(1000*timeout);
        }catch (Exception e){
            logger.info("Failed to sleep");
        }
    }

}
