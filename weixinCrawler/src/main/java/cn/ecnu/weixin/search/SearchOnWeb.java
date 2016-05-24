package cn.ecnu.weixin.search;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.ecnu.weixin.BaseInfo.Article;
import cn.ecnu.weixin.BaseInfo.StrBinaryTurn;
import cn.ecnu.weixin.Output.OutputToMySqlOperator;
import cn.ecnu.weixin.ZJExtractor.ZJExactorExample;
import cn.ecnu.weixin.ZJExtractor.pictureExactor;
import cn.ecnu.weixin.util.*;
import com.gargoylesoftware.htmlunit.WebRequest;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 *@ClassName   : SearchOnWeb.java
 *@Package     : cn.ecnu.weixin.search
 *@Author      : baoquanhuang 
 *@Email       : baoquanhuang@gmail.com
 *@Date        : 2015年12月1日下午7:11:43
 *@Description : TODO
 */
public class SearchOnWeb {
	private static Logger log = Logger.getLogger(SearchOnWeb.class);
	public final static String WEIXIN = "http://weixin.sogou.com/weixin?type=1&query=";
	private static OutputToMySqlOperator outputToMySqlOperator = null;
	private static String getArticleContentPath(String path,String articleTitle){
		String titleAndTime=articleTitle + TheCrawlerUtil.GetCurrentDate();
		String articleFileName=TheCrawlerUtil.fileNameFilter(titleAndTime)+".txt";
		CreateFloderFile.JudgeDicrectory(path, "content");
		String filepath = path +File.separator + "content" + File.separator +articleFileName;
		return filepath;
	}
	private static String getArticleHtmlContentPath(String path,String articleTitle){
		String titleAndTime=articleTitle + TheCrawlerUtil.GetCurrentDate();
		String articleFileName=TheCrawlerUtil.fileNameFilter(titleAndTime)+".html";
		CreateFloderFile.JudgeDicrectory(path, "htmlcontent");
		String filepath = path +File.separator + "htmlcontent" + File.separator +articleFileName;
		return filepath;
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
	private static String GethomeUrl(HtmlPage page){
		Document doc = Jsoup.parse(page.asXml());
		Element div_weixinID = doc.getElementById("sogou_vr_11002301_box_0");
		String weixinhomeUrl = div_weixinID.attr("href").toString().trim();
		weixinhomeUrl = "http://weixin.sogou.com" + weixinhomeUrl;
		return weixinhomeUrl;
	}
	private static Elements getArticleUrlList(HtmlPage homepage){
		Document homeDoc = Jsoup.parse(homepage.asXml());
		Element  div_wxbox =  homeDoc.getElementById("wxbox");
		Elements articleLists = div_wxbox.select(".txt-box h4 a");
		return articleLists;
	}
	private static Elements getPictureUrlList(HtmlPage homepage){
		Document homeDoc = Jsoup.parse(homepage.asXml());
		Element  div_wxbox =  homeDoc.getElementById("wxbox");
		Elements picturesLists = div_wxbox.select(".img_box2 a img");
		return picturesLists;
	}
	private static String getPictureUrl(Element ele){
		String pictureurl=ele.attr("src").toString().trim();
		return pictureurl;
	}
	private static HtmlPage getArticlePage(Element articlelist,WebClient webClient) {
		String eachArticleUrl = articlelist.attr("href").toString().trim();
		//System.out.println("eachArticleUrl"+eachArticleUrl);
		HtmlPage articlePage=null;
		try{
			 articlePage  = webClient.getPage("http://weixin.sogou.com"+ eachArticleUrl);
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
	public static void searchSogou(String weixinID,String weixinName, String path)
	{
		WebClient webClient = WebConstructor.ConstructWebClient();
		outputToMySqlOperator=new OutputToMySqlOperator();
		outputToMySqlOperator.init();
		try {
			String articleContent;
			String articleTitle;
			String articleHtmlContent;

			HtmlPage page = getGongzhonghaoPage(weixinID,webClient);  //获取微信公众号列表页面
			String weixinhomeUrl = GethomeUrl(page);      //获取公众号文章列表界面的url
			System.out.println("weixinhomeUrl="+weixinhomeUrl);
			
//			Parse Each article URL
			int gongzhonghao_id=outputToMySqlOperator.GetGongzhonghaoIDByName(weixinName);
			if(gongzhonghao_id==-1){
				outputToMySqlOperator.InsertintoGongzhonghao(weixinName,weixinID);
				gongzhonghao_id=outputToMySqlOperator.GetGongzhonghaoIDByName(weixinName);
			}


			HtmlPage homepage = webClient.getPage(weixinhomeUrl); //获取文章列表的界面
			///checkLogin.LoginBtnClick(homepage,webClient);
			homepage= getMoreClick.GetMore(homepage,webClient);//触发点击加载更多

			Elements articleLists = getArticleUrlList(homepage);//获取文章的链接标签列表

			Elements pictureLists =getPictureUrlList(homepage);//获取图片列表


			int count = 1;
			for(int i=0;i<articleLists.size();i++)
			//for(Element articlelist : articleLists)
			{
				String pictureurl=getPictureUrl(pictureLists.get(i));
				System.out.println("#########pictureurl="+pictureurl);
				int idbyPictureurl=outputToMySqlOperator.GetArticleIDByPictureUrl(pictureurl);
				System.out.print(idbyPictureurl+" ");
				if(idbyPictureurl!=-1) break;


				HtmlPage articlepage = getArticlePage(articleLists.get(i),webClient);//获取文章页面

				String articleUrl=articlepage.getUrl().toString();    //获取文章显示时的正常页面
				System.out.println("articleurl="+articleUrl);
				Thread.sleep(300000);
				if(articlepage.getUrl().toString().length()>200){
					continue;   //如果大于200说明是验证码问题，不用保存
				}

				articleContent = articlepage.asText();   //文章内容
				articleHtmlContent =articlepage.asXml();//文章html
				articleTitle = articlepage.getTitleText().toString().trim();  //文章标题
				articleTitle=articleTitle.replaceAll("\\pP|\\pS", "");
				String time=Jsoup.parse(articleHtmlContent).select("#post-date").text();
				System.out.println("time="+time);
				int articleid=outputToMySqlOperator.GetArticleIDByName(articleTitle);
				if(articleid!=-1)continue;

				String contentpath = getArticleContentPath( path , articleTitle);//文章内容保存的路径
				//System.out.println("contentpath="+contentpath);
				CreateFloderFile.CreateFileAndWrite(contentpath, articleContent);

				String htmlcontentpath = getArticleHtmlContentPath( path , articleTitle);//文章内容保存的路径
				//System.out.println("htmlcontentpath="+htmlcontentpath);
				CreateFloderFile.CreateFileAndWrite(htmlcontentpath, articleHtmlContent);



				String words=ZJExactorExample.getAnswer(htmlcontentpath);
				Article article=new Article(articleTitle,articleUrl,articleContent,articleHtmlContent,contentpath,htmlcontentpath,pictureurl,time,words);
				article.gongzhonghao_id=gongzhonghao_id;
				System.out.println("gongzhonghao_id="+article.gongzhonghao_id);
				outputToMySqlOperator.InsertintoArticle(article);



				articleid=outputToMySqlOperator.GetArticleIDByName(articleTitle);
				InsertWord(words,articleid);

				String picturePath=getArticlePicturePath(path,articleTitle);
				pictureExactor.SlovePicture(htmlcontentpath,articleid,picturePath,outputToMySqlOperator);
				//System.out.println("Segmentword="+words);

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