package cn.ianzhang.automation.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SearchResultAnalysis {

    private  static Logger logger = LogManager.getLogger("SearchResultAnalysis");

    public static boolean printResult(List<WebElement> elements){

        List<String> listLink = new ArrayList<>();
        List<String> listTitle = new ArrayList<>();

//        List<WebElement> elements = driver.findElements(By.xpath("//ol[@id='b_results']//h2/a"));
        for(WebElement element:elements){
            listLink.add(element.getAttribute("href"));
            listTitle.add(element.getText());
        }
        System.out.println(listLink);
        System.out.println(listTitle);

        Map<String,String> map = IntStream.range(0,listTitle.size()).collect(HashMap::new,(m, i) ->m.put(listTitle.get(i),listLink.get(i)), (m, n) ->{});
        System.out.println("结果列表：----------------------------------------------------------------------------------");
        map.keySet().forEach(s -> {
            System.out.println(s+ "-->" + map.get(s));
        });

        List<String> domains = new ArrayList<>();
        for(String link:listLink){
            domains.add(UrlTool.handleUrl(link));
        }
        Map<String,Long> countDomain = domains.stream().collect(Collectors.groupingBy(a ->a,Collectors.counting()));
        System.out.println("结果统计：----------------------------------------------------------------------------------");

        countDomain.keySet().forEach(s -> {
            System.out.println(s +"-->"+ countDomain.get(s));
        });

        return true;
    }
}
