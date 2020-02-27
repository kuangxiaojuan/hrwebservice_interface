package dhr.sap.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.ext.DestinationDataProvider;
/**
 * 与sap的连接配置
 *  * @author Administrator
 *
 */
public class ConnSAP {
	private static Logger log = Logger.getLogger(ConnSAP.class); // 初始化日志对象
	
	private static final String ABAP_AS_POOLED = "ABAP_AS_WITH_POOL";
	static{
		Properties connectProperties = new Properties();
//		connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST, "172.30.18.100");//服务器
//		connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR,  "00");        //系统编号
//		connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT, "800");       //SAP集团-正式
//		connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST, "172.30.18.94");//服务器
//		connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR,  "00");        //系统编号
//		connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT, "102");       //SAP集团
//		connectProperties.setProperty(DestinationDataProvider.JCO_USER,   "sap");  //SAP用户名
//		connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD, "fhsj@123");     //密码
//		connectProperties.setProperty(DestinationDataProvider.JCO_USER,   "sap_integ");  //SAP用户名
//		connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD, "FHSJSAP8");     //密码
//		connectProperties.setProperty(DestinationDataProvider.JCO_LANG,   "zh");        //登录语言
//		connectProperties.setProperty(DestinationDataProvider.JCO_POOL_CAPACITY, "3");  //最大连接数  
//		connectProperties.setProperty(DestinationDataProvider.JCO_PEAK_LIMIT, "10"); //最大连接线程
		connectProperties.setProperty(DestinationDataProvider.JCO_MSHOST, "172.30.18.100");
		connectProperties.setProperty(DestinationDataProvider.JCO_MSSERV, "sapmsS4P");
		connectProperties.setProperty(DestinationDataProvider.JCO_R3NAME,  "S4P");
        connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT, "800");
        connectProperties.setProperty(DestinationDataProvider.JCO_USER,   "sap_integ");
        connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD, "Fhsj@123");
        connectProperties.setProperty(DestinationDataProvider.JCO_GROUP, "LGRP");
        connectProperties.setProperty(DestinationDataProvider.JCO_LANG,   "zh");
		
		createDataFile(ABAP_AS_POOLED, "jcoDestination", connectProperties);
	}
	private static void createDataFile(String name, String suffix,
			Properties properties) {
		
		File cfg = new File(name+"."+suffix);
		if(cfg.exists()){
			cfg.deleteOnExit();
		}
		try {
			FileOutputStream fos = new FileOutputStream(cfg,false);
			properties.store(fos, "for tests only!");
			fos.close();
			
		} catch (Exception e) {
			
			log.error("Create Data file fault, error msg: " + e.toString());
			throw new RuntimeException("Unable to create the destination " + cfg.getName(), e);
			
		}	
	}
	/**
	 * 获取与SAP的连接
	 * @return SAP连接对象
	 */
	public static JCoDestination connect(){
		JCoDestination destination =null;
		try {
			destination = JCoDestinationManager.getDestination(ABAP_AS_POOLED);
//			System.out.println(destination.getAttributes());
		} catch (Exception e) {
			log.error("Connect SAP fault, error msg: " + e.toString());
		}
		return destination;
	}
	

}
