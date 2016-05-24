package cn.ecnu.weixin.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;


/**
 *@Author : baoquan Huang 
 *@Email  : baoquanhuang@gmail.com
 *@Date   : 2014年12月23日下午2:36:09
 *@Description : TODO
 */
public class TheCrawlerUtil {
	private final static Logger LOGGER =Logger.getLogger(TheCrawlerUtil.class);
	private static Pattern FilePattern = Pattern.compile("[\\\\/:*?\"<>|]");  

	/**
	 * get system current time 
	 * @return time
	 */
	public static String GetCurrentDate()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String time = sdf.format(new Date());
		return time;
	}
	
	
	public static String GetTimeSpan(long startTime,long endTime)
	{
		long l = endTime - startTime;
		long day=l/(24*60*60*1000);
		long hour=(l/(60*60*1000)-day*24);
		long min=((l/(60*1000))-day*24*60-hour*60);
		long s=(l/1000-day*24*60*60-hour*60*60-min*60);
		System.out.println(""+day+"天"+hour+"小时"+min+"分"+s+"秒");  
		return (""+day+"天"+hour+"小时"+min+"分"+s+"秒");
	}
	
	/**
	 * log in error or warning informations into logger
	 * @param e
	 * @return error
	 */
	public static String LogErrorInfo(Exception e)
	{
		try 
		{
			String error = "";
			StringWriter writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter(writer);
			e.printStackTrace(printWriter);
			error = writer.toString();
			return error;
		} catch (Exception e1) {
			LOGGER.error(e1.toString());
			e1.printStackTrace();
		}
		return null;
	}
	public static String ToStopChar(String word)
	{
		String string = word;
		if(string.contains("|"))
		{
			string = string.replaceAll("|", "");
		}
		if(string.contains("\\"))
		{
			string = string.replaceAll("\\\\", "");
		}
		if(string.contains("/"))
		{
			string = string.replaceAll("/", "");
		}
		if(string.contains("*"))
		{
			string = string.replaceAll("\\*", "");
		}
		if(string.contains(":"))
		{
			string = string.replaceAll(":", "");
		}
		if(string.contains("-"))
		{
			string = string.replaceAll("-", "");
		}
		if(string.contains("?"))
		{
			string = string.replaceAll("\\?", "");
		}
		if(string.contains("\""))
		{
			string = string.replaceAll("\"", "");
		}
		if(string.contains("<"))
		{
			string = string.replaceAll("<", "");
		}
		if(string.contains(">"))
		{
			string = string.replaceAll(">", "");
		}
		
		return string;
	}
	
	public static String fileNameFilter(String str) {
		/// 去掉文件名中的无效字符,如 \ / : * ? " < > | 
	    return str==null?null:FilePattern.matcher(str).replaceAll("");  
	}
}