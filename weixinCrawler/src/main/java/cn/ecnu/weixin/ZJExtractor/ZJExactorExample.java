package cn.ecnu.weixin.ZJExtractor;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;

/**
 * Created by zhoujie on 2016/1/14.
 */
public class ZJExactorExample {
    public ZJExactorExample(){
        ExcludeStopWord.Init();
    }
    public static String getAnswer(String path){
        String result="";
        result=ExactorBag.Exactor(path);
        result=ChinaSegment.getWords(result);
        result=result.replaceAll("\\pP|\\pS", "").replaceAll("丨","").replaceAll("[\\d]","").trim();
        result=ExcludeStopWord.LeftStopWords(result);
        return result;
    }
    public  static void main(String[] args)
    {
        new ZJExactorExample();
        String path="E:\\华师大\\资料\\搜索\\微信搜索\\weixinCrawler\\datasave\\feekr_trip\\20160116\\htmlcontent\\国内转机城市大起底，一次玩多地还更便宜，分分钟值回路费20160116.html";
        System.out.print(getAnswer(path));
        for(int i=4;i<43;i++){
            System.out.println("delete from weixin_gongzhonghao.article where article_id = "+i+";");
        }
    }
}
