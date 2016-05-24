package cn.ecnu.weixin.ZJExtractor;

import cn.ecnu.weixin.Output.OutputToMySqlOperator;
import cn.ecnu.weixin.search.SeleniumSearchOnWeb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by zhoujie on 2016/1/22.
 */
public class ExtractorFromFile {
    public static Set<String> st=new HashSet<String>();
    private static String basepath=System.getProperty("user.dir");
    public static void Insert(){
        OutputToMySqlOperator outputToMySqlOperator=new OutputToMySqlOperator();
        outputToMySqlOperator.init();
        String path= basepath+ File.separator + "dat1.txt";
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(path), "UTF-8");
            BufferedReader fReader = new BufferedReader(inputStreamReader);
            String data = "";
            while ((data = fReader.readLine()) != null) {
                data = data.trim();
                System.out.println(data);
                outputToMySqlOperator.InsertintoWordNeed(data);
            }
        }catch(Exception e){

        }
    }

    public static void Init(){
        String path= basepath + File.separator + "dat1.txt";
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(path), "UTF-8");
            BufferedReader fReader = new BufferedReader(inputStreamReader);
            String data = "";
            while ((data = fReader.readLine()) != null) {
                data = data.trim();
                st.add(data);
            }
        }catch(Exception e){

        }
    }

    public static String getArticleFromFile(String path){
        String result="";
        String[] titleArr=path.split("\\\\");
        String title=titleArr[titleArr.length-1];
        //System.out.println(title);
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(path), "UTF-8");
            BufferedReader fReader = new BufferedReader(inputStreamReader);
            String data = "";
            while ((data = fReader.readLine()) != null) {
                data.replaceAll("\r\n","");
                result += data;
            }
        }catch(Exception e){

        }
        result += title;
        return result;
    }

    public static String keyWords(String content){
        String result="";
        Iterator<String> it = st.iterator();
        while (it.hasNext()) {
            String str = it.next();
            if (content.contains(str)) result += str + " ";
        }
        return result;
    }
    public static String getAnswer(String path){
        String content=getArticleFromFile(path);
        String result=keyWords(content);
        return result;
    }
    public static void main(String[] args){
        Init();
        String result=getArticleFromFile("E:\\华师大\\资料\\搜索\\微信搜索\\weixinCrawler\\datasave\\gousacn\\20160121\\content\\此生必览亚利桑那州8大自然奇观20160121.txt");
        System.out.println(result);
        String keyWords=keyWords(result);
        System.out.println(keyWords);
    }
}
