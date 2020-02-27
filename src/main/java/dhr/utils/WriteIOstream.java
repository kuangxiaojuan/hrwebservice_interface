package dhr.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WriteIOstream {
	
	public static void GetwritetoJson(String method,String json){
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str ="写入时间："+ formatter.format(currentTime)+" 接口方法： "+method;
		String no = formatter.format(currentTime).substring(0, 7);
//		File file = new File("D:\\tomcat-7.0.68_me(接口程序)\\log.txt");// 指定要写入的文件  
		String logPath=System.getProperty("catalina.home")+"\\logs\\"+no+"log.txt";  //日志路径
		File file = new File(logPath);// 指定要写入的文件  
		try {
			 if (!file.exists()) {
				 // 如果文件不存在则创建  
				 file.createNewFile();  
			 }
			FileOutputStream fs=new FileOutputStream(logPath,true);
//			 FileOutputStream fs=new FileOutputStream("D:\\tomcat-7.0.68_me\\log.txt",true);
			fs.write(str.getBytes());
			fs.write("\r\n".getBytes());//把\r\n写入fs文件中，\r\n作为一个隔位附
			fs.write(json.getBytes());
			fs.write("\r\n".getBytes());
			fs.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void GetMessage(String method, String printStackTrace){
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str ="写入时间："+ formatter.format(currentTime)+" 接口方法： "+method;
		String no = formatter.format(currentTime).substring(0, 7);
//		File file = new File("D:\\tomcat-7.0.68_me(接口程序)\\log.txt");// 指定要写入的文件  
		String logPath=System.getProperty("catalina.home")+"\\logs\\"+no+"log.txt";  //日志路径
		
		File file = new File(logPath);// 指定要写入的文件  
//		File file = new File("D:\\tomcat-7.0.68_me\\log.txt");// 指定要写入的文件  
		 try {
			 if (!file.exists()) {
				 // 如果文件不存在则创建  
				 file.createNewFile();  
			 }
			 FileOutputStream fs=new FileOutputStream(logPath,true);
//			 FileOutputStream fs=new FileOutputStream("D:\\tomcat-7.0.68_me\\log.txt",true);
			fs.write(str.getBytes());
			fs.write("\r\n".getBytes());
			fs.write(printStackTrace.getBytes());
			fs.write("\r\n".getBytes());
			fs.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void ErrorPersonnel(String method,String json){
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str ="写入时间："+ formatter.format(currentTime)+" 接口方法： "+method;
		String no = formatter.format(currentTime).substring(0, 7);
//		File file = new File("D:\\tomcat-7.0.68_me(接口程序)\\log.txt");// 指定要写入的文件  
		String logPath=System.getProperty("catalina.home")+"\\logs\\"+no+"log.txt";  //日志路径
		File file = new File(logPath);// 指定要写入的文件  
//		File file = new File("D:\\tomcat-7.0.68_me\\error.txt");// 指定要写入的文件  
		try {
			 if (!file.exists()) {
				 // 如果文件不存在则创建  
				 file.createNewFile();  
			 }
			FileOutputStream fs=new FileOutputStream(logPath,true);
//			 FileOutputStream fs=new FileOutputStream("D:\\tomcat-7.0.68_me\\error.txt",true);
			fs.write(str.getBytes());
			fs.write("\r\n".getBytes());
			fs.write(json.getBytes());
			fs.write("\r\n".getBytes());
			fs.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
