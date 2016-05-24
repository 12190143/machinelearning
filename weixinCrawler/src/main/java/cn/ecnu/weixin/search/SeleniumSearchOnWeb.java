package cn.ecnu.weixin.search;

import cn.ecnu.weixin.BaseInfo.Article;
import cn.ecnu.weixin.Output.OutputToMySqlOperator;
import cn.ecnu.weixin.ZJExtractor.ZJExactorExample;
import cn.ecnu.weixin.ZJExtractor.pictureExactor;
import cn.ecnu.weixin.util.*;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhoujie on 2016/1/19.
 */
public class SeleniumSearchOnWeb {
    private static Logger log = Logger.getLogger(SearchOnWeb.class);
    public final static String WEIXIN = "http://weixin.sogou.com/weixin?type=1&query=";
    private static OutputToMySqlOperator outputToMySqlOperator = null;
    private static String getArticleContentPath(String path,String articleTitle){
        String titleAndTime=articleTitle + TheCrawlerUtil.GetCurrentDate();
        String articleFileName=TheCrawlerUtil.fileNameFilter(titleAndTime)+".txt";
        CreateFloderFile.JudgeDicrectory(path, "content");
        String filepath = path + File.separator + "content" + File.separator +articleFileName;
        return filepath;
    }
    private static String getArticleHtmlContentPath(String path,String articleTitle){
        String titleAndTime=articleTitle + TheCrawlerUtil.GetCurrentDate();
        String articleFileName=TheCrawlerUtil.fileNameFilter(titleAndTime)+".html";
        CreateFloderFile.JudgeDicrectory(path, "htmlcontent");
        String filepath = path +File.separator + "htmlcontent" + File.separator +articleFileName;
        return filepath;
    }

    private static HtmlPage getGongzhonghaoPage(String weixinID,WebClient webClient){
        Pattern p = Pattern.compile("\\s*|\t|\r|\n");
        Matcher m = p.matcher(weixinID);
        weixinID = m.replaceAll("");
        HtmlPage page=null;
        try {
            page = webClient.getPage(WEIXIN + weixinID);
        }catch (Exception e){

        }
        return page;
    }
    private static String getArticlePicturePath(String path,String articleTitle){
        String titleAndTime=articleTitle + TheCrawlerUtil.GetCurrentDate();
        String pictureFile=TheCrawlerUtil.fileNameFilter(titleAndTime);
        CreateFloderFile.JudgeDicrectory(path, "htmlcontent");
        String filepath = path +File.separator + "htmlcontent";
        CreateFloderFile.JudgeDicrectory(filepath, pictureFile);
        filepath =filepath + File.separator + pictureFile;
        return filepath;
    }

    private static Elements getArticleUrlList(WebDriver driver){
        Document homeDoc = Jsoup.parse(driver.getPageSource());
        Element  div_wxbox =  homeDoc.getElementById("wxbox");
        Elements articleLists = div_wxbox.select(".txt-box h4 a");
        return articleLists;
    }
    private static Elements getPictureUrlList(WebDriver driver){
        Document homeDoc = Jsoup.parse(driver.getPageSource());
        Element  div_wxbox =  homeDoc.getElementById("wxbox");
        Elements picturesLists = div_wxbox.select(".img_box2 a img");
        return picturesLists;
    }
    private static String getPictureUrl(Element ele){
        String pictureurl=ele.attr("src").toString().trim();
        return pictureurl;
    }
    private static WebDriver getArticlePage(Element articlelist,WebDriver driver) {
        String eachArticleUrl = articlelist.attr("href").toString().trim();
        //System.out.println("eachArticleUrl"+eachArticleUrl);
        WebDriver articlePage=driver;
        try{
            articlePage.get("http://weixin.sogou.com"+ eachArticleUrl);
        }catch (Exception e){

        }
        return articlePage;
    }
    private static void InsertWord(String str,int artile_id){
        String[] words=str.split(" ");
        for(int i=0;i<words.length;i++){
            int id=outputToMySqlOperator.GetValByNameAndArticleId(words[i],artile_id);
            if(id==-1){
                outputToMySqlOperator.InsertintoWords(words[i],artile_id);
            }
        }
    }
    public static void searchSogou(String weixinID,String weixinName, String path, WebDriver driver){

        outputToMySqlOperator=new OutputToMySqlOperator();
        outputToMySqlOperator.init();
        try {
            String articleContent;
            String articleTitle;
            String articleHtmlContent;

            driver= SeleniumOperator.SearchFisrt(driver,weixinID);
            driver=SeleniumOperator.SearchTwo(driver);
            driver=SeleniumOperator.GetMore(driver);
            Elements articleLists = getArticleUrlList(driver);//获取文章的链接标签列表

            Elements pictureLists =getPictureUrlList(driver);//获取图片列表
            int gongzhonghao_id=outputToMySqlOperator.GetGongzhonghaoIDByName(weixinName);
            if(gongzhonghao_id==-1){
                outputToMySqlOperator.InsertintoGongzhonghao(weixinName,weixinID);
                gongzhonghao_id=outputToMySqlOperator.GetGongzhonghaoIDByName(weixinName);
            }

            int count = 1;
            for(int i=0;i<articleLists.size();i++)
            //for(Element articlelist : articleLists)
            {
                String pictureurl=getPictureUrl(pictureLists.get(i));
                System.out.println("#########pictureurl="+pictureurl);
                int idbyPictureurl=outputToMySqlOperator.GetArticleIDByPictureUrl(pictureurl);
                System.out.print(idbyPictureurl+" ");
                if(idbyPictureurl!=-1) continue;


                WebDriver articlepage = getArticlePage(articleLists.get(i),driver);//获取文章页面

                String articleUrl=articlepage.getCurrentUrl().toString();    //获取文章显示时的正常页面
                System.out.println("articleurl="+articleUrl);
                Thread.sleep(300000);
                if(articleUrl.length()>200){
                    continue;   //如果大于200说明是验证码问题，不用保存
                }


                articleHtmlContent =articlepage.getPageSource();//文章html
                articleContent = Jsoup.parse(articleHtmlContent).body().text();   //文章内容
                articleTitle = articlepage.getTitle().toString().trim();  //文章标题
                articleTitle=articleTitle.replaceAll("\\pP|\\pS", "");
                int articleid=outputToMySqlOperator.GetArticleIDByName(articleTitle);

                if(articleid!=-1)continue;

                String contentpath = getArticleContentPath( path , articleTitle);//文章内容保存的路径
                //System.out.println("contentpath="+contentpath);
                CreateFloderFile.CreateFileAndWrite(contentpath, articleContent);

                String htmlcontentpath = getArticleHtmlContentPath( path , articleTitle);//文章内容保存的路径
                //System.out.println("htmlcontentpath="+htmlcontentpath);
                CreateFloderFile.CreateFileAndWrite(htmlcontentpath, articleHtmlContent);

                String time=Jsoup.parse(articleHtmlContent).select("#post-date").text();
                System.out.println("time="+time);
                String words= ZJExactorExample.getAnswer(htmlcontentpath);
                Article article=new Article(articleTitle,articleUrl,articleContent,articleHtmlContent,contentpath,htmlcontentpath,pictureurl,time,words);
                article.gongzhonghao_id=gongzhonghao_id;
                System.out.println("gongzhonghao_id="+article.gongzhonghao_id);
                outputToMySqlOperator.InsertintoArticle(article);


                articleid=outputToMySqlOperator.GetArticleIDByName(articleTitle);
                InsertWord(words,articleid);
                //System.out.println("Segmentword="+words);

                String picturePath=getArticlePicturePath(path,articleTitle);
                pictureExactor.SlovePicture(htmlcontentpath,articleid,picturePath,outputToMySqlOperator);

                System.out.println(weixinID +" : "+ count + " Task Done.");
                count++;


            }
        } catch (Exception e) {
            log.error(e.toString());
            e.printStackTrace();
        }
        outputToMySqlOperator.free();
    }
}
