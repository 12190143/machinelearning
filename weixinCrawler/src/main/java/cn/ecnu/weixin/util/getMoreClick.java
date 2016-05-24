package cn.ecnu.weixin.util;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * Created by zhoujie on 2016/1/15.
 */
public class getMoreClick {
    public static HtmlPage GetMore(HtmlPage homepage, WebClient webClient){
        HtmlAnchor btn = (HtmlAnchor )homepage.getByXPath("//*[@id=\"wxmore\"]/a").get(0);
        HtmlPage page=homepage;
        try{
            int i=0;
            while(null != btn){
                page=btn.click();
                System.out.println("1");
                webClient.waitForBackgroundJavaScript(1000);
                System.out.println("2");
                btn = (HtmlAnchor)homepage.getByXPath("//*[@id=\"wxmore\"]/a").get(0);
                i++;
                if(i>10)break;
            }
            //CreateFloderFile.CreateFileAndWrite("E:\\index.html", page.asXml());
        }catch (Exception e){

        }

        return page;
    }
}
