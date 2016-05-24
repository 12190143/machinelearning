package cn.ecnu.weixin.ZJExtractor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by zhoujie on 2016/1/14.
 */
public class ExactorBag {
    public static String Exactor(String filePath){
        Set wordsSet=new HashSet<String>();
        //File thisfile=new File(filePath);
        //String filename=thisfile.getName().substring(0,thisfile.getName().length()-5);
        //System.out.println("filename="+filename);
        File file = new File(filePath);
        Pattern p = Pattern.compile("\\s*|\t|\r|\n");
        String result="";
        try {
            Document doc = Jsoup.parse(file, "UTF-8");
            Elements elesList=doc.select("strong");
            for(Element ele : elesList){

                Matcher m = p.matcher(ele.text());
                String word = m.replaceAll("");
                wordsSet.add(word);
            }
            elesList=doc.select("span[style*=color]");
            for(Element ele : elesList){
                Matcher m = p.matcher(ele.text());
                String word = m.replaceAll("");
                wordsSet.add(word);
            }
            Iterator<String> it = wordsSet.iterator();
            while (it.hasNext()) {
                String str = it.next();
                result += str + " ";
            }
            result=result.trim();
        }catch (Exception e){

        }
        return result;
    }
}
