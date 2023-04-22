package cn.ianzhang.automation.selenium;

import cn.ianzhang.automation.common.PageTools;
import cn.ianzhang.automation.common.SearchResultAnalysis;
import cn.ianzhang.automation.common.UrlTool;
import cn.ianzhang.automation.pom.SearchEngineQueryPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.apache.tomcat.jni.Time.sleep;

@SpringBootTest
class QueryStatisticsTests {
	private  static Logger logger = Logger.getLogger("QueryStatisticsTests.class");

	static WebDriver driver;
	PageTools pageTools;
	SearchEngineQueryPage searchEngineQueryPage = new SearchEngineQueryPage();

	@BeforeAll
	static void setupClass(){
// TODO 自动选择driver进行浏览器兼容性测试。

//		String browserName = System.getenv("browser");
		String browserName="msedge";
// 		String browserName="chrome";


		if(("msedge").equals(browserName)){
			EdgeOptions  edgeOptions = new EdgeOptions();
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

	}

	@BeforeEach
	void setupTest(){
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
//		driver.get("https://www.ianzhang.cn/bing/");
		searchEngineQueryPage.SearchPage(driver,"https://cn.bing.com");


	}

	@AfterEach
	void teardown(){
		driver.quit();
	}

	@Test
	void test() throws InterruptedException{

		// search keyword "franklin" and print the result
		searchEngineQueryPage.getPageTitle();
		searchEngineQueryPage.enterSearch("franklin");
		searchEngineQueryPage.switchToSearchedSecondPage();
		searchEngineQueryPage.collectResults();
		SearchResultAnalysis.printResult(searchEngineQueryPage.collectResults());

		// search keyword "selenium" and print result.
		searchEngineQueryPage.enterSearch("Selenium");
		searchEngineQueryPage.switchToSearchedSecondPage();
		searchEngineQueryPage.collectResults();
		SearchResultAnalysis.printResult(searchEngineQueryPage.collectResults());

	}
}