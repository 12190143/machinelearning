package cn.ecnu.weixin.ZJExtractor;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by zhoujie on 2016/1/16.
 */
public class ExcludeStopWord {
    private final static Logger LOG = Logger.getLogger(ExcludeStopWord.class);
    private static Set stopWordsSet=new HashSet<String>();
    public  static String StopWords = System.getProperty("user.dir") + File.separator + "stopWords.txt";
    public static void Init(){
        try{
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(StopWords), "UTF-8");
            BufferedReader fReader = new BufferedReader(inputStreamReader);
            String stopWord="";
            while ((stopWord = fReader.readLine()) != null){
                stopWord = stopWord.trim();
                stopWordsSet.add(stopWord);
                //System.out.println(stopWord);
            }
        }catch (UnsupportedEncodingException e) {
            LOG.error(e.toString());
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            LOG.error(e.toString());
            e.printStackTrace();
        } catch (IOException e) {
            LOG.error(e.toString());
            e.printStackTrace();
        }
    }
    public static String LeftStopWords(String str) {
        String result="";
        String[] words=str.split(" ");
        Set wordSet=new HashSet<String>();
        for(int i=0;i<words.length;i++)
        {
            if(stopWordsSet.contains(words[i])){
                //System.out.println(words[i]);
                continue;
            }
            wordSet.add(words[i]);
        }
        Iterator<String> it = wordSet.iterator();
        while (it.hasNext()) {
            String wd = it.next();
            result += wd + " ";
        }
        return result;
    }
    public static void main(String[] args){
        Init();
    }
}
