/**
 * 
 */
package com.landray.kmss.km.review.webservice;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author ZHONGYUAN
 *
 */
public class GetMessPer {

//	@Scheduled(cron = "0/5 * * * * ?")
	public void synOrg() throws ParseException{
					System.out.println("1234");
		regularMessage();
	}
	
	
	public void regularMessage() {

		//转正提醒  传工号和一句话到OA
		IKmReviewWebserviceService service = new IKmReviewWebserviceServiceProxy();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			java.sql.DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			// 测试：jdbc:oracle:thin:@172.30.30.20:1521:yksoft1919
			// 正式：jdbc:oracle:thin:@172.30.11.93:1521:orcl
			// String url = "jdbc:oracle:thin:@172.30.30.20:1521:yksoft1919";
			String url = "jdbc:oracle:thin:@172.30.11.93:1521:orcl";
			String username = "yksoft";
			String userpwd = "yksoft1919";
			Connection conn = java.sql.DriverManager.getConnection(url, username, userpwd);
			// 从数据库表 hrpwarn_result 中获取a0100 然后根据a0100获取工号
			String sql = "select usra01.e0127,hrpwarn_result.a0100 from hrpwarn_result "
					+ "left join usra01 on hrpwarn_result.a0100 = usra01.a0100 where wid=25";

			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery(sql);
			// System.out.println("rs" + rs);
			while (rs.next()) {
				String e0127 = rs.getString("e0127");
				String a0100 = rs.getString("a0100");
				String sentence = "试用期即将结束，请提交转正申请！";
				KmReviewParamterForm krpf = new KmReviewParamterForm();
				// 将必须要传的参数放进来
				// form.setDocCreator("{\"PersonNo\": \"000994\"}")
				krpf.setDocCreator("{\"PersonNo\": \"" + e0127 + "\"}");
				krpf.setDocSubject("docSubject");
				// krpf.setFdTemplateId("1630be52c76773ab6c5f6714c87b4cc5");//动态写进
				// 测试
				krpf.setFdTemplateId("16464c5d9703f923d971d7547e9b4037");// 正式
				// 将两个参数设置到 krpf 中 传json格式
				Map<String, String> krpfMap = new HashMap<String, String>();
				krpfMap.put("fd_person_no", e0127);
				krpfMap.put("fd_remind", sentence);
				String jsonTemp = JsonUtil.map2json(krpfMap);
				krpf.setFormValues(jsonTemp);
				// jsonTemp 即为要传的参数
				 System.out.println("jsonTemp:"+jsonTemp);
				try {
					String reset = service.addReview(krpf);
					System.out.println(reset);
					printMessage("传的数据"+jsonTemp+"回复数据："+reset);
				} catch (Exception e) {
					e.printStackTrace();
				} catch (RemoteException e) {
					e.printStackTrace();
				}catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 System.out.println("e0127:" + e0127 + "-- a0100:" + a0100);
				
			}
			 System.out.println("exe sql");
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public static void printMessage(String message) throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		FileOutputStream fs = new FileOutputStream(
				new File("D:\\opt\\apache-tomcat-7.0.28\\logs\\" + sdf.format(date.getTime()) + "text.txt"));
		PrintStream p = new PrintStream(fs);
		p.println(message);
		p.close();

	}

}
