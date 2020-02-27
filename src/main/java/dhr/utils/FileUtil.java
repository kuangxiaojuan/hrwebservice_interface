package dhr.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;


public class FileUtil {

	public static boolean deleteFile(String fileName){    
        File file = new File(fileName);    
        if(file.isFile() && file.exists()){    
            file.delete();    
            System.out.println("删除单个文件"+fileName+"成功！");    
            return true;    
        }else{    
            System.out.println("删除单个文件"+fileName+"失败！");    
            return false;    
        }    
    }
	
	/**
     * 功能：Java读取txt文件的内容
     * 步骤：1：先获得文件句柄
     * 2：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取
     * 3：读取到输入流后，需要读取生成字节流
     * 4：一行一行的输出。readline()。
     * 备注：需要考虑的是异常情况
     * @param filePath
     */
    public static String readTxtFile(String filePath){
        try {
    		String readTxtFile = "";
            String encoding="UTF-8";
            File file=new File(filePath);
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null){
                	readTxtFile = readTxtFile + lineTxt.trim();
                }
                read.close();
                return readTxtFile;
	        }else{
	           return null;
	        }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
     
    }
	
	
	
}
