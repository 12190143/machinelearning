package cn.ecnu.weixin.Output;

import cn.ecnu.weixin.BaseInfo.Article;
import cn.ecnu.weixin.BaseInfo.StrBinaryTurn;
import cn.ecnu.weixin.util.TheCrawlerUtil;
import com.mysql.jdbc.log.Log;

import java.io.File;
import java.io.InputStream;
import java.sql.*;

/**
 * Created by zhoujie on 2016/1/15.
 */
public class OutputToMySqlOperator {
    public Connection conn=null;
    public OutputToMySqlOperator(){

    }
    public void init(){
        try {
            Class.forName("com.mysql.jdbc.Driver");

        } catch (ClassNotFoundException e) {
            System.out.println("无法加载驱动"+e.getMessage());
        }
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/weixin_gongzhonghao", "root", "");
        } catch (SQLException e) {
            System.out.println("无法连接数据库");
        }
    }

    public  int GetArticleIDByArticle(int article_id){     //根据图片得到文章id
        int id=-1;
        String sql="select article_id from weixin_gongzhonghao.keywords where article_id="+article_id;
        System.out.println(sql);
        ResultSet rs=null;
        Statement stm=null;
        try{
            stm= conn.createStatement();
            //rs = stm.executeQuery(sql.replace("$GONGZHONGHAONAME", name));
            rs = stm.executeQuery(sql);
            while(rs.next()){
                id=rs.getInt("article_id");
            }
        }catch(SQLException e){
            System.out.print(e.getMessage());
        }finally{
            try{
                if(stm!=null)
                    if(!stm.isClosed())
                        stm.close();
                if(rs!=null)
                    rs.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        return id;
    }

    public  int GetArticleIDByContentPath(String path){     //根据图片得到文章id
        int id=-1;
        String sql="select article_id from weixin_gongzhonghao.article where content_path='"+path.replaceAll("\\\\","\\\\\\\\")+"'";
        //System.out.println(sql);
        ResultSet rs=null;
        Statement stm=null;
        try{
            stm= conn.createStatement();
            //rs = stm.executeQuery(sql.replace("$GONGZHONGHAONAME", name));
            rs = stm.executeQuery(sql);
            while(rs.next()){
                id=rs.getInt("article_id");
            }
        }catch(SQLException e){
            System.out.print(e.getMessage());
        }finally{
            try{
                if(stm!=null)
                    if(!stm.isClosed())
                        stm.close();
                if(rs!=null)
                    rs.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        return id;
    }
    public  void InsertintoKeyWords(String keywords,int article_id){    //插入关键词
        String sql="insert into weixin_gongzhonghao.keywords(keywords,article_id)values('"+keywords+"',"+article_id+");";
        //System.out.println("+++++++++++++++++++++++++++"+sql);
        ResultSet rs=null;
        Statement stm=null;
        try{
            stm= conn.createStatement();
            stm.executeUpdate(sql);
        }catch(SQLException e){
            System.out.print(e.getMessage());
        }finally{
            try{
                if(stm!=null)
                    if(!stm.isClosed())
                        stm.close();
                if(rs!=null)
                    rs.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }
    public  void InsertintoWordNeed(String word){    //插入公众号
        String sql="insert into weixin_gongzhonghao.wordneed(word)values('"+word+"');";
        //System.out.println("+++++++++++++++++++++++++++"+sql);
        ResultSet rs=null;
        Statement stm=null;
        try{
            stm= conn.createStatement();
            stm.executeUpdate(sql);
        }catch(SQLException e){
            System.out.print(e.getMessage());
        }finally{
            try{
                if(stm!=null)
                    if(!stm.isClosed())
                        stm.close();
                if(rs!=null)
                    rs.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }


    public  int GetGongzhonghaoIDByName(String name){  //根据公众号名称得到id
        int id=-1;
        String sql="select gongzhonghao_id from weixin_gongzhonghao.gongzhonghao where name='"+name+"'";
        //System.out.print(sql);
        ResultSet rs=null;
        Statement stm=null;
        try{
            stm= conn.createStatement();
            //rs = stm.executeQuery(sql.replace("$GONGZHONGHAONAME", name));
            rs = stm.executeQuery(sql);
            while(rs.next()){
                id=rs.getInt("gongzhonghao_id");
            }
        }catch(SQLException e){
            System.out.print(e.getMessage());
        }finally{
            try{
                if(stm!=null)
                    if(!stm.isClosed())
                        stm.close();
                if(rs!=null)
                    rs.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        return id;
    }

    public  void InsertintoGongzhonghao(String name,String id){    //插入公众号
        String sql="insert into weixin_gongzhonghao.gongzhonghao(name,weixinId)values('"+name+"','"+id+"');";
        //System.out.println("+++++++++++++++++++++++++++"+sql);
        ResultSet rs=null;
        Statement stm=null;
        try{
            stm= conn.createStatement();
            stm.executeUpdate(sql);
        }catch(SQLException e){
            System.out.print(e.getMessage());
        }finally{
            try{
                if(stm!=null)
                    if(!stm.isClosed())
                        stm.close();
                if(rs!=null)
                    rs.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }


    public  void InsertintoArticle(Article article){    //插入文章
        //InputStream content=StrBinaryTurn.FileToBool(article.contentPath);
        //InputStream htmlcontent=StrBinaryTurn.FileToBool(article.htmlContentPath);
        String downloadtime=TheCrawlerUtil.GetCurrentDate();
        String sql="insert into weixin_gongzhonghao.article(url,name,content_path,htmlcontent_path,pictureurl,gongzhonghao_id,time,downloadtime,keywords)values('"+article.url+"','"+article.name+"','"+article.contentPath.replaceAll("\\\\","\\\\\\\\")+"','"+article.htmlContentPath.replaceAll("\\\\","\\\\\\\\")+"','"+article.pictureUrl+"',"+article.gongzhonghao_id+",'"+article.time+"','"+downloadtime+"','"+article.keywords+"');";
        //System.out.println("+++++++++++++++++++++++++++"+sql);
        ResultSet rs=null;
        Statement stm=null;
        try{
            stm= conn.createStatement();
            stm.executeUpdate(sql);
        }catch(SQLException e){
            System.out.print(e.getMessage());
        }finally{
            try{
                if(stm!=null)
                    if(!stm.isClosed())
                        stm.close();
                if(rs!=null)
                    rs.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

    public  void InsertintoPicture(int id,String path){    //插入图片
        System.out.println("path="+path);
        String sql="insert into weixin_gongzhonghao.picture(article_id,picturepath)values("+id+",'"+path.replaceAll("\\\\","\\\\\\\\")+"');";
        System.out.println("+++++++++++++++++++++++++++"+sql);
        ResultSet rs=null;
        Statement stm=null;
        try{
            stm= conn.createStatement();
            stm.executeUpdate(sql);
        }catch(SQLException e){
            System.out.print(e.getMessage());
        }finally{
            try{
                if(stm!=null)
                    if(!stm.isClosed())
                        stm.close();
                if(rs!=null)
                    rs.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }


    public  void InsertintoWords(String word,int article_id){    //插入单词
        String sql="insert into weixin_gongzhonghao.words(word,article_id,update_time)values('"+word+"',"+article_id+",'"+ TheCrawlerUtil.GetCurrentDate()+"');";
        //System.out.println("+++++++++++++++++++++++++++"+sql);
        ResultSet rs=null;
        Statement stm=null;
        try{
            stm= conn.createStatement();
            stm.executeUpdate(sql);
        }catch(SQLException e){
            System.out.print(e.getMessage());
        }finally{
            try{
                if(stm!=null)
                    if(!stm.isClosed())
                        stm.close();
                if(rs!=null)
                    rs.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }


    public  int GetArticleIDByName(String name){     //根据文章名得到文章id
        int id=-1;
        String sql="select article_id from weixin_gongzhonghao.article where name='"+name+"'";
        //System.out.print(sql);
        ResultSet rs=null;
        Statement stm=null;
        try{
            stm= conn.createStatement();
            //rs = stm.executeQuery(sql.replace("$GONGZHONGHAONAME", name));
            rs = stm.executeQuery(sql);
            while(rs.next()){
                id=rs.getInt("article_id");
            }
        }catch(SQLException e){
            System.out.print(e.getMessage());
        }finally{
            try{
                if(stm!=null)
                    if(!stm.isClosed())
                        stm.close();
                if(rs!=null)
                    rs.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        return id;
    }

    public  int GetArticleIDByPictureUrl(String picture_url){     //根据图片得到文章id
        int id=-1;
        String sql="select article_id from weixin_gongzhonghao.article where pictureurl='"+picture_url+"'";
        System.out.println(sql);
        ResultSet rs=null;
        Statement stm=null;
        try{
            stm= conn.createStatement();
            //rs = stm.executeQuery(sql.replace("$GONGZHONGHAONAME", name));
            rs = stm.executeQuery(sql);
            while(rs.next()){
                id=rs.getInt("article_id");
            }
        }catch(SQLException e){
            System.out.print(e.getMessage());
        }finally{
            try{
                if(stm!=null)
                    if(!stm.isClosed())
                        stm.close();
                if(rs!=null)
                    rs.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        return id;
    }

    public int GetValByNameAndArticleId(String word,int article_id) { //根据word和文章id获得id
        int id=-1;
        String sql="select article_id from weixin_gongzhonghao.words where word='"+word+"' and article_id = "+article_id;
        //System.out.print(sql);
        ResultSet rs=null;
        Statement stm=null;
        try{
            stm= conn.createStatement();
            rs = stm.executeQuery(sql);
            while(rs.next()){
                id=rs.getInt("article_id");
            }
        }catch(SQLException e){
            System.out.print(e.getMessage());
        }finally{
            try{
                if(stm!=null)
                    if(!stm.isClosed())
                        stm.close();
                if(rs!=null)
                    rs.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        return id;
    }

    public  void free(){
        try {
            conn.close();
        }catch (Exception e){

        }
    }
}
