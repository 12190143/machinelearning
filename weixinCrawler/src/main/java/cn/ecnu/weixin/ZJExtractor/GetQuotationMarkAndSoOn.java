package cn.ecnu.weixin.ZJExtractor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhoujie on 2016/1/15.
 */
public class GetQuotationMarkAndSoOn {
    private static String[] BagLeft= {"'","‘","“","「","\""};
    private static String[] BagRight = {"'","’","”","」","\""};

    private static String getOtherValue(String str){
        String result="";
        for(int i=0;i<BagLeft.length;i++) {
            String reg = BagLeft[i]+"(.*?)"+BagRight[i];//正则
            Pattern p1 = Pattern.compile(reg);
            Matcher m1 = p1.matcher(str);
            while (m1.find()) {
                String word=m1.group().substring(1,m1.group().length()-1);
                result += " "+word;
            }
        }
        return result.trim();
    }

    public static String getValue(String str){
        String result="";
        result = getOtherValue(str);
        return result;
    }
}
