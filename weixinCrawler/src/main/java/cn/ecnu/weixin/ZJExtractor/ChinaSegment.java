package cn.ecnu.weixin.ZJExtractor;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.WordDictionary;
import com.huaban.analysis.jieba.SegToken;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by zhoujie on 2016/1/16.
 */
public class ChinaSegment {
    public void testDemo() {
        JiebaSegmenter segmenter = new JiebaSegmenter();

        String[] sentences =
                new String[] {"这是一个伸手不见五指的黑夜。我叫孙悟空，我爱北京，我爱Python和C++。", "我不喜欢日本和服。", "雷猴回归人间。",
                        "工信处女干事每月经过下属科室都要亲口交代24口交换机等技术性器件的安装工作", "结果婚的和尚未结过婚的"};
        for (String sentence : sentences) {
            //System.out.println(segmenter.process(sentence, JiebaSegmenter.SegMode.INDEX).toString());
            List<SegToken> resultList = segmenter.process(sentence, JiebaSegmenter.SegMode.INDEX);
            Iterator<SegToken> it = resultList.iterator();
            if (!it.hasNext())
                return ;

            StringBuilder sb = new StringBuilder();
            while (it.hasNext()) {
                SegToken s = it.next();
                if(!" ".equals(s.word)){
                    sb.append(s.word).append(' ');
                }
            }
            System.out.println(sb.toString());
        }
    }
    public static String getWords(String str){
        String words="";
        words=getSpecialWords(str)+" "+getFenci(str);
        return words;
    }
    private static String getSpecialWords(String str){
        String result="";
        result=GetQuotationMarkAndSoOn.getValue(str);
        return result;
    }
    private static String getFenci(String str){
        JiebaSegmenter segmenter = new JiebaSegmenter();
        List<SegToken> resultList = segmenter.process(str, JiebaSegmenter.SegMode.INDEX);
        Iterator<SegToken> it = resultList.iterator();
        if (!it.hasNext())
            return "";
        StringBuilder sb = new StringBuilder();
        while (it.hasNext()) {
            SegToken s = it.next();
            if(!" ".equals(s.word)){
                sb.append(s.word).append(' ');
            }
        }
        return sb.toString();
    }

    public static void main(String[] args){
        (new ChinaSegment()).testDemo();
    }
}
