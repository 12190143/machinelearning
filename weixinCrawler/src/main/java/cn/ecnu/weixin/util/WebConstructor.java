package cn.ecnu.weixin.util;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;

/**
 *@ClassName   : ConstructWebClient.java
 *@Package     : cn.ecnu.weixin.util
 *@Author      : baoquanhuang 
 *@Email       : baoquanhuang@gmail.com
 *@Date        : 2015年12月1日下午7:46:02
 *@Description : Configure browser
 */
public class WebConstructor {
	
	/**
	 * 配置模拟浏览器的属性
	 * @return
	 */
	public static WebClient ConstructWebClient()
	{
		// 设置模拟浏览器的版本型号
		WebClient webClient = new WebClient(BrowserVersion.INTERNET_EXPLORER_11);
		// 设置支持浏览器是否解析javascript和 css
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setCssEnabled(false);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		// 设置浏览器链接超时时间
		webClient.getOptions().setTimeout(120000);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		webClient.getOptions().setAppletEnabled(false);
		return webClient;
	}
}
