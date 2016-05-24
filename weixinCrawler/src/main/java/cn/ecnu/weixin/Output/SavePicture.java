package cn.ecnu.weixin.Output;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by zhoujie on 2016/1/19.
 */
public class SavePicture {
    public static void main(String[] args) {
//        String url = "http://mmbiz.qpic.cn/mmbiz/aBTYg0zfgkcWjuNzKlKFWl9tPPXN3Zbo5iaMFo8TQklb68RAOjUplEyRssB1vRHt0TaJzSPl59OWib7GtZouLnmg/0?wx_fmt=jpeg";
//        byte[] btImg = getImageFromNetByUrl(url);
//        if(null != btImg && btImg.length > 0){
//            System.out.println("读取到：" + btImg.length + " 字节");
//            String fileName = "百度.jpg";
//            writeImage(btImg, path, fileName);
//        }else{
//            System.out.println("没有从该连接获得内容");
//        }
    }

    public static void savePicture(String path,String url,String name){
        byte[] btImg = getImageFromNetByUrl(url);
        if(null != btImg && btImg.length > 0){
            System.out.println("读取到：" + btImg.length + " 字节");
            String fileName = name + ".jpg";
            writeImage(btImg, path, fileName);
        }else{
            System.out.println("没有从该连接获得内容");
        }
    }
    /**
     * 将图片写入到磁盘
     * @param img 图片数据流
     * @param fileName 文件保存时的名称
     */
    public static void writeImage(byte[] img, String path,String fileName){
        try {
            File file = new File(path+ "\\" + fileName);
            FileOutputStream fops = new FileOutputStream(file);
            fops.write(img);
            fops.flush();
            fops.close();
            System.out.println("图片已经写入到C盘");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 根据地址获得数据的字节流
     * @param strUrl 网络连接地址
     * @return
     */
    public static byte[] getImageFromNetByUrl(String strUrl){
        try {
            URL url = new URL(strUrl);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            InputStream inStream = conn.getInputStream();//通过输入流获取图片数据
            byte[] btImg = readInputStream(inStream);//得到图片的二进制数据
            return btImg;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 从输入流中获取数据
     * @param inStream 输入流
     * @return
     * @throws Exception
     */
    public static byte[] readInputStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while( (len=inStream.read(buffer)) != -1 ){
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }
}
