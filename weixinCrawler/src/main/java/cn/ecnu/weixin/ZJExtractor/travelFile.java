package cn.ecnu.weixin.ZJExtractor;

import cn.ecnu.weixin.Output.OutputToMySqlOperator;
import com.gargoylesoftware.htmlunit.javascript.host.file.FileList;

import java.io.File;

/**
 * Created by zhoujie on 2016/1/22.
 */
public class travelFile {
    private static String basepath= System.getProperty("user.dir")+ File.separator+"datasave";
    private static OutputToMySqlOperator outputToMySqlOperator=new OutputToMySqlOperator();
    public static void allFile(){
        File file=new File(basepath);
        File[] file1=file.listFiles();//公众号
        for (int i=0;i<file1.length;i++)
        {
            File[] file2=file1[i].listFiles(); //日期
            for(int j=0;j<file2.length;j++){
                System.out.println(file1[i].getName()+file2[j].getName());
                File[] file3=file2[j].listFiles();
                if(file3.length==0)continue;
                File[] file4=file3[0].listFiles();
                for(int k=0;k<file4.length;k++){
                    String path=file4[k].getAbsolutePath();
                    System.out.println(path);
                    String ansower=ExtractorFromFile.getAnswer(path);
                    int id=outputToMySqlOperator.GetArticleIDByContentPath(path);
                    if(id!=-1){
                        id=outputToMySqlOperator.GetArticleIDByArticle(id);
                        if(id == -1) {
                            outputToMySqlOperator.InsertintoKeyWords(ansower, id);
                        }
                    }
                    System.out.println(ansower);
                }
            }
        }
    }
    public static void main(String[] args){
        ExtractorFromFile.Init();
        outputToMySqlOperator.init();
        allFile();
    }
}
