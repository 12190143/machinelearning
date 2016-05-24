package cn.ecnu.weixin.util;

import com.sun.jna.platform.mac.Carbon;
import com.thoughtworks.selenium.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

/**
 * Created by zhoujie on 2016/1/19.
 */
public class LoginClick {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();


    public WebDriver setUp(String name,String pd){
        try {
            driver = new FirefoxDriver();
            baseUrl = "http://weixin.sogou.com/";
            driver.get(baseUrl);
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            testSeleniumIde(name,pd);
        }catch (Exception e){

        }
        return driver;
    }

    public void testSeleniumIde(String name,String pd){
        driver.findElement(By.id("loginBtn")).click();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.switchTo().frame(driver.findElements(By.tagName("iframe")).get(0));
        driver.switchTo().frame(driver.findElement(By.id("ptlogin_iframe")));
        System.out.println(driver.getTitle());
        driver.findElement(By.id("switcher_plogin")).click();
        driver.findElement(By.id("u")).clear();
        driver.findElement(By.id("u")).sendKeys(name);
        //1503435862  zj19930818
        driver.findElement(By.id("p")).clear();
        driver.findElement(By.id("p")).sendKeys(pd);
        driver.findElement(By.id("switcher_plogin")).click();
        driver.findElement(By.id("login_button")).click();
    }

    public void getData(String weixinId){
        String url =driver.getCurrentUrl();
        System.out.println("url="+url);
        WebDriver d1=SeleniumOperator.SearchFisrt(driver,weixinId);
        SeleniumOperator.SearchTwo(d1);
    }

    public void tearDown(){
        driver.quit();
    }


    public static void main(String[] args){

    }
}
