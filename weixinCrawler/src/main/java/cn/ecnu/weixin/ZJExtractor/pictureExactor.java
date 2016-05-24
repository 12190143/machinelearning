package cn.ecnu.weixin.ZJExtractor;

import cn.ecnu.weixin.Output.OutputToMySqlOperator;
import cn.ecnu.weixin.Output.SavePicture;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhoujie on 2016/1/19.
 */
public class pictureExactor {
    public static void SlovePicture(String filePath,int article_id,String path,OutputToMySqlOperator outputToMySqlOperator){
        File file = new File(filePath);
        Set urlSet=new HashSet<String>();
        try {
            Document doc = Jsoup.parse(file, "UTF-8");
            Elements elesList=doc.select("#js_content p  img");
            System.out.println("geshu="+elesList.size());
            for(Element ele : elesList){
                urlSet.add(ele.attr("data-src").toString().trim());
            }
            Iterator<String> it = urlSet.iterator();
            int i=0;
            while (it.hasNext()) {
                String str = it.next();
                i++;
                SavePicture.savePicture(path,str, String.valueOf(i));
                String pictureName=String.valueOf(i)+".jpg";
                outputToMySqlOperator.InsertintoPicture(article_id,path+ File.separator + pictureName);
                System.out.println("url="+str);
            }
            System.out.println("+++"+urlSet.size());
        }catch (Exception e){

        }
    }
    public static void main(String[] args){
        //String path="";
        //SlovePicture("E:\\华师大\\资料\\搜索\\微信搜索\\weixinCrawler\\datasave\\ribenlvyou1\\20160119\\htmlcontent\\\uD83D\uDD25你知道日本的民宿有多美吗？20160119.html",1,path);
    }
}
