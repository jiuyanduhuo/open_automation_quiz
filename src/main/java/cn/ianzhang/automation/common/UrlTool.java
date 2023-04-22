package cn.ianzhang.automation.common;

import java.net.URL;
import com.google.common.net.InternetDomainName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UrlTool {
    private  static Logger logger = LogManager.getLogger("Net");


    public static String handleUrl(String strUrl){
        try{
            URL url = new URL(strUrl);
            String host = url.getHost();
            InternetDomainName domainName = InternetDomainName.from(host);
            String topDomainName = domainName.topPrivateDomain().toString();
            logger.info("Top Domain Name is : "+topDomainName);

            return topDomainName;
        }catch (Exception e){
            logger.error("Url:"+ strUrl+ "extraction failed");
        }
        return "";
    }


}
