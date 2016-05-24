package cn.ecnu.weixin.BaseInfo;

/**
 * Created by zhoujie on 2016/1/15.
 */
public class Article {
    public int article_id;
    public int gongzhonghao_id;
    public String gongzhonghao_Name;
    public String name;
    public String url;
    public String content;
    public String html_content;
    public String contentPath;
    public String htmlContentPath;
    public String pictureUrl;
    public String time;
    public String keywords;
    public Article(String name ,String url, String content, String html_content, String contentPath, String htmlContentPath,String pictureUrl,String time,String keywords){
        //this.gongzhonghao_id=gongzhonghao_id;
        this.name=name;
        this.url=url;
        this.content=content;
        this.html_content=html_content;
        this.contentPath=contentPath;
        this.htmlContentPath=htmlContentPath;
        this.pictureUrl=pictureUrl;
        this.time=time;
        this.keywords=keywords;
    }
}
