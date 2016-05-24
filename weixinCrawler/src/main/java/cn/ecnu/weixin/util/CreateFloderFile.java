package cn.ecnu.weixin.util;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.apache.commons.io.comparator.DirectoryFileComparator;
import org.apache.log4j.Logger;
import org.omg.CosNaming.NamingContextPackage.AlreadyBound;

/**
 *@Author : baoquan Huang 
 *@Email  : baoquanhuang@gmail.com
 *@Date   : 2014年11月17日下午7:30:26
 *@Description : Create File Directory for storage data
 */
public class CreateFloderFile {
	private static final Logger LOG = Logger.getLogger(CreateFloderFile.class);
	

	public CreateFloderFile() {
		// TODO Auto-generated constructor stub
	}
	public static boolean CreateFolder(String path,String name)
	{
		File dirFile = new File(path + File.separator + name);
		if(dirFile.exists())
		{
			LOG.info(" Create directory " + path + File.separator + name + " Failure ");
			return false;
		}
		if(dirFile.mkdirs())
		{
			System.out.println(" Create File directory " + path +  File.separator +name+ " success");
			return true;
		}
		else 
		{
			System.out.println(" Create File directory " + path + File.separator + name +" Failure ");
			return false;
		}
	}
	
	public static void JudgeDicrectory(String path,String name)
	{
		try 
		{
			File dirFile = new File(path + File.separator + name);
			/*	Judge the Path directory exist or not
			 *  if the directory in not exist
			 *  then create the diectory
			 * */
			 if(!dirFile.isDirectory())
			 {
				 dirFile.mkdirs();
				 System.out.println(path + File.separator + name + " Make Directory");
			 }else
			 {
				 System.out.println(path + File.separator + name + " Directory already exists");
			 }
			 
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
	}
	
	//Create file's Name
	public static String GenerateRandomFilename()
	{
		try 
		{
			String fileName = TheCrawlerUtil.GetCurrentDate();
			Random random  =new Random();
			int randomNum = random.nextInt();
			fileName = fileName + randomNum +"";
			return fileName;
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return null;
	}
	
	//Create file and write contents 
	public static void CreateFileAndWrite(String path,String contents)
	{
		if(contents != null)
		{
			File file = new File(path);
			try 
			{
				file.createNewFile();
				write(file, contents);
			} catch (IOException e) {
				LOG.error(e.toString());
				e.printStackTrace();
			}
		}
	}
	/**
	 * write contents in file
	 * @param file
	 * @param contents
	 */
	public static void write(File file,String contents)
	{
		try {
			FileWriter fWriter =new FileWriter(file,true);
			fWriter.write(contents);
			fWriter.close();
		} catch (IOException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
	}
	
	/**
	 * Test the CreateFolderFile 
	 * @param args
	 */
	/*public static void main(String[] args) {
		String path = System.getProperty("user.dir");
		String name = "JD";
		if(CreateFloderFile.CreateFolder(path, name))
		{
			System.out.println("Yes");
		}
		else
		{
			System.out.println("No");
		}
		
		String path = System.getProperty("user.dir");
		String conString =path + File.separator + "面部护肤" + File.separator + "CPU";
		int flag = CreateFloderFile.JudgeDicrectory(conString);
		if(flag == 1)
		{
			System.out.println("yes");
		}
		else {
			System.out.println("no");
			flag = CreateFloderFile.JudgeDicrectory(conString);
			System.out.println(flag);
		}
	}*/
	
	/**
	 * traversa file path
	 * @param path
	 * @return
	 */
	public static ArrayList<String> TraversalFolds(String path,ArrayList<String> pathLists)
	{
		ArrayList<String> pathList = new ArrayList<>();
		File[] files;
		File rootFile = new File(path);
		files = rootFile.listFiles();
		if(files.length != 0)
		{
			for(File file:files)
			{
				if(file.isDirectory())
				{
					pathList = TraversalFolds(file.getAbsolutePath(),pathLists);
					if(pathList != null)
					{
						pathLists.addAll(pathList);
					}
				}
				else 
				{
					pathLists.add(file.getAbsolutePath());
				}
			}
			return pathLists;
		}
		else
		{
			return null;
		}
	}
}
