package cn.ecnu.weixin.BaseInfo;

import java.io.*;
import java.sql.Blob;

/**
 * Created by zhoujie on 2016/1/15.
 */
public class StrBinaryTurn {
    public static InputStream FileToBool(String path){
        File file=new File(path);
        InputStream in=null;
        try {
            in = new BufferedInputStream(new FileInputStream(file));
        }catch (Exception e){

        }
        return in;
    }

    public static File BoolToFile(InputStream in ,String path){
        File file=new File(path);
        try {
            OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
            byte[] buff = new byte[1024];
            //System.out.println("###############################");
            for (int i = 0; (i = in.read(buff)) > 0; ) {
                out.write(buff, 0, i);
                //System.out.print(buff);
            }
            //System.out.println("###############################");
        }catch (Exception e){

        }
        return file;
    }

}
