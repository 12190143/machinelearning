package cn.ecnu.weixin.util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by zhoujie on 2016/1/19.
 */
public class SeleniumOperator {
    public static WebDriver SearchFisrt(WebDriver driver,String weixinId){
        driver.findElement(By.id("upquery")).clear();
        driver.findElement(By.id("upquery")).sendKeys(weixinId);
        driver.findElement(By.className("swz2")).click();
        return driver;
    }

    public static WebDriver SearchTwo(WebDriver driver){
        //driver.findElement(By.id("sogou_vr_11002301_box_0")).click();
        String url="http://weixin.sogou.com"+ driver.findElement(By.id("sogou_vr_11002301_box_0")).getAttribute("href").toString().trim();
        System.out.print(url);
        driver.get(url);
        //driver.findElement(By.xpath("//*[@id=\"wxmore\"]/a")).click();
        return driver;
    }

    public static WebDriver GetMore(WebDriver driver){
        System.out.println(driver.getPageSource());
        String flag=driver.findElement(By.id("wxmore")).getAttribute("style");
        while(!flag.equals("visibility: hidden;"))
        {
            driver.findElement(By.xpath("//*[@id=\"wxmore\"]/a")).click();
            flag=driver.findElement(By.id("wxmore")).getAttribute("style");
            System.out.println("#####"+flag);
        }
        return driver;
    }
}
