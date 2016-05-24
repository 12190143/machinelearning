package cn.ecnu.weixin.crawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import cn.ecnu.weixin.ZJExtractor.ZJExactorExample;
import org.apache.log4j.Logger;
import cn.ecnu.weixin.search.SearchOnWeb;
import cn.ecnu.weixin.util.CreateFloderFile;
import cn.ecnu.weixin.util.TheCrawlerUtil;

/**
 *@ClassName   : weixinCrawler.java
 *@Package     : cn.ecnu.weixin.crawler
 *@Author      : baoquanhuang 
 *@Email       : baoquanhuang@gmail.com
 *@Date        : 2015年12月2日上午10:14:54
 *@Description : This is weixin main function
 */
public class weixinCrawler {
	private final static Logger LOG = Logger.getLogger(weixinCrawler.class);
	public static String path = System.getProperty("user.dir");
	public final static String WEIXINID_FILE_PATH = System.getProperty("user.dir") + File.separator + "weixinID.txt";
	public static void main(String[] args) {
		new ZJExactorExample();
		InputStreamReader inputStreamReader;
		String weixinID;
		String weixinName;
		try {
			inputStreamReader = new InputStreamReader(new FileInputStream(WEIXINID_FILE_PATH), "UTF-8");
			BufferedReader fReader = new BufferedReader(inputStreamReader);
			String data;
			while ((data = fReader.readLine()) != null) {
				data=data.trim();
				weixinName=data.split(" ")[0];
				weixinID = data.split(" ")[1];
				CreateFloderFile.JudgeDicrectory(path, "datasave");
				String dataSavePath = path + File.separator+"datasave";
				CreateFloderFile.JudgeDicrectory(dataSavePath, weixinID);
				String folderPath = dataSavePath + File.separator + weixinID;
				CreateFloderFile.JudgeDicrectory(folderPath, TheCrawlerUtil.GetCurrentDate());
				String filePath = folderPath + File.separator + TheCrawlerUtil.GetCurrentDate();
				//Crawl each weixin of public acount
				System.out.println("filepath=" + filePath);
				SearchOnWeb.searchSogou(weixinID, weixinName,filePath);
			}
			fReader.close();
			inputStreamReader.close();
		} catch (UnsupportedEncodingException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		} catch (IOException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		System.out.println("Today's task is done! see ya");
	}
}