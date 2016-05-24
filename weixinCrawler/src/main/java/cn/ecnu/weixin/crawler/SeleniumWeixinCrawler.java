package cn.ecnu.weixin.crawler;

import cn.ecnu.weixin.ZJExtractor.ZJExactorExample;
import cn.ecnu.weixin.search.SeleniumSearchOnWeb;
import cn.ecnu.weixin.util.CreateFloderFile;
import cn.ecnu.weixin.util.LoginClick;
import cn.ecnu.weixin.util.TheCrawlerUtil;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import java.io.*;

/**
 * Created by zhoujie on 2016/1/19.
 */
public class SeleniumWeixinCrawler {
    private final static Logger LOG = Logger.getLogger(weixinCrawler.class);
    public static String path = System.getProperty("user.dir");
    public final static String WEIXINID_FILE_PATH = System.getProperty("user.dir") + File.separator + "weixinID.txt";
    private static LoginClick loginClick=new LoginClick();
    private static String getPath(String path,String weixinID){
        CreateFloderFile.JudgeDicrectory(path, "datasave");
        String dataSavePath = path + File.separator + "datasave";
        CreateFloderFile.JudgeDicrectory(dataSavePath, weixinID);
        String folderPath = dataSavePath + File.separator + weixinID;
        CreateFloderFile.JudgeDicrectory(folderPath, TheCrawlerUtil.GetCurrentDate());
        String filePath = folderPath + File.separator + TheCrawlerUtil.GetCurrentDate();
        return filePath;
    }
    public static void main(String[] args) {
        new ZJExactorExample();
        InputStreamReader inputStreamReader;
        String weixinID;
        String weixinName;
        String[] name={"3177909907","2029374819","1503435862"};
        String[] pd={"zj19930818","zj19930818","zj19930818"};
        for(int i=0;i<name.length;i++) {
            WebDriver driver = loginClick.setUp(name[i],pd[i]);
            try {
                Thread.sleep(20000);
                inputStreamReader = new InputStreamReader(new FileInputStream(WEIXINID_FILE_PATH), "UTF-8");
                BufferedReader fReader = new BufferedReader(inputStreamReader);
                String data = "";
                while ((data = fReader.readLine()) != null) {
                    data = data.trim();
                    weixinName = data.split(" ")[0];
                    weixinID = data.split(" ")[1];
                    String filePath=getPath(path,weixinID);
                    SeleniumSearchOnWeb.searchSogou(weixinID, weixinName, filePath, driver);
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
            } catch (Exception e) {

            }
            System.out.println("Today's task is done! see ya");
            loginClick.tearDown();
        }
    }
}
