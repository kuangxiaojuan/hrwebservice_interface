/**
 * 
 */
package dhr.ldap.webservice;

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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.landray.kmss.km.review.webservice.IKmReviewWebserviceService;
import com.landray.kmss.km.review.webservice.IKmReviewWebserviceServiceProxy;
import com.landray.kmss.km.review.webservice.JsonUtil;
import com.landray.kmss.km.review.webservice.KmReviewParamterForm;

import dhr.utils.WriteIOstream;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ZHONGYUAN
 *
 */
@Controller
@EnableScheduling
public class GetMessPer {

	// @Scheduled(cron = "0/5 * * * * ?")
	// @Scheduled(cron = "0 17 17 * * ?")
	 @Scheduled(cron = "0 30 8 * * ?")
	public void synOrg() {
		System.out.println("--------考勤异常提醒---------");
		// performanceMessage();
		// regularMessage();//转正提醒
		kqAbnormalMessage();// 考勤异常提醒
	}

	 @Scheduled(cron = "0 30 8 * * ?")//指定时间8点30分
	public void synRegularMessage() {
		System.out.println("--------转正提醒 ---------");
		regularMessage();// 转正提醒
	}

//	 @Scheduled(cron = "0 35 15 * * ?")
//	@Scheduled(cron = "0 0/10 17,18 * * ?")
	@Scheduled(cron = "0 0 8 1 * ?")//每月1号8点
	public void synPerformence() {
		System.out.println("--------绩效提醒---------");
		performanceMessage();
		// regularMessage();//转正提醒
		// kqAbnormalMessage();//考勤异常提醒
	}

	// 转正提醒
	public void regularMessage() {
		// 转正提醒 传工号和一句话到OA
		IKmReviewWebserviceService service = new IKmReviewWebserviceServiceProxy();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			java.sql.DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			// 测试：jdbc:oracle:thin:@172.30.30.20:1521:yksoft1919
			// 正式：jdbc:oracle:thin:@172.30.11.93:1521:orcl
//			 String url = "jdbc:oracle:thin:@172.30.30.20:1521:yksoft1919";
			String url = "jdbc:oracle:thin:@172.30.10.93:1521:orcl";
			// String url = "jdbc:oracle:thin:@172.30.10.113:1521:orcl";
			String username = "yksoft";
			String userpwd = "yksoft1919";
			Connection conn = java.sql.DriverManager.getConnection(url, username, userpwd);
			// 从数据库表 hrpwarn_result 中获取a0100 然后根据a0100获取工号
//			String sql = "select usra01.e0127,usra01.a0101,"
//					+ "(select codeitemdesc from organization where codeitemid=usra01.e0122) e0122,"
//					+ "hrpwarn_result.a0100 from hrpwarn_result "
//					+ "left join usra01 on hrpwarn_result.a0100 = usra01.a0100 where wid=25";
			
			String sql = "select a.e0127,a.a0101,b.fd_id nameID,"
					+ "(select codeitemdesc from organization where codeitemid=a.e0122) e0122,"
					+ "hrpwarn_result.a0100 from hrpwarn_result  "
					+ "left join usra01 a on hrpwarn_result.a0100 = a.a0100 "
					+ "left join Z_SYS_ORG_ELEMENT b on CONCAT('HR',a.guidkey) = b.fd_import_info where wid=25";

			// String sql = "select e0127,a0100 from usra01 where e0127='002472'
			// ";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery(sql);
			// System.out.println("rs" + rs);
			while (rs.next()) {
				String e0127 = rs.getString("e0127");
				String a0100 = rs.getString("a0100");
				String a0101 = rs.getString("a0101");
				String e0122 = rs.getString("e0122");
				String nameID = rs.getString("nameID");
//				String sentence = "您好！您的试用期将于10天内到期。在此，首先感谢您入职以来的努力工作，同时，我们郑重提醒您：您已进入转正评估期，请您在收到本通知后的3个工作日内，进入《试用期/试岗期考核表》流程，上传《转正申请书》，转正申请内容包括但不限于对您试用期间的整体工作表现与岗位能力胜任情况做出全面总结，以及未来您的工作改进目标和努力方向等。";
				String sentence = "您好！您的试用期将于10天内到期。感谢您入职以来的努力工作！";
				KmReviewParamterForm krpf = new KmReviewParamterForm();
				// 将必须要传的参数放进来
				// form.setDocCreator("{\"PersonNo\": \"000994\"}")
				krpf.setDocCreator("{\"PersonNo\": \"" + e0127 + "\"}");
				krpf.setDocSubject("docSubject");
//				 krpf.setFdTemplateId("1630be52c76773ab6c5f6714c87b4cc5");//动态写进
				// 测试
				krpf.setFdTemplateId("16464c5d9703f923d971d7547e9b4037");// 正式
				// 将两个参数设置到 krpf 中 传json格式
				Map<String, String> krpfMap = new HashMap<String, String>();
				krpfMap.put("fd_person_no", e0127);
				krpfMap.put("fd_person_name", a0101);
				krpfMap.put("fd_apply_dept", e0122);

				krpfMap.put("fd_apply_person", nameID);
				krpfMap.put("fd_remind", sentence);
				String jsonTemp = JsonUtil.map2json(krpfMap);
				krpf.setFormValues(jsonTemp);
				// jsonTemp 即为要传的参数
				System.out.println("jsonTemp:" + jsonTemp);
				try {
					String reset = service.addReview(krpf);
					System.out.println(reset);
					WriteIOstream.GetwritetoJson("转正数据:", "传的数据" + jsonTemp + "回复数据：" + reset);
//					printMessage("传的数据" + jsonTemp + "回复数据：" + reset);
				} catch (Exception e) {
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

	// 考勤异常提醒
	public void kqAbnormalMessage() {
		// 考勤异常提醒 传工号和一句话到OA
		IKmReviewWebserviceService service = new IKmReviewWebserviceServiceProxy();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			java.sql.DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			// 测试：jdbc:oracle:thin:@172.30.30.20:1521:yksoft1919
			// 正式：jdbc:oracle:thin:@172.30.11.93:1521:orcl
//			 String url = "jdbc:oracle:thin:@172.30.30.20:1521:yksoft1919";
			String url = "jdbc:oracle:thin:@172.30.10.93:1521:orcl";
			// String url = "jdbc:oracle:thin:@172.30.10.113:1521:orcl";
			String username = "yksoft";
			String userpwd = "yksoft1919";
			Connection conn = java.sql.DriverManager.getConnection(url, username, userpwd);
			// 从数据库表 获取数据
			String sql = "select a0100,Q03Z0,q03AA,q0341,Q0340,(select e0127 from usra01 where a0100=q.a0100) e0127,"
					+ "(select a0101 from usra01 where a0100=q.a0100) a0101, "
					+ "(select codeitemdesc from organization where codeitemid=(select e0122 from usra01 where a0100=q.a0100)) e0122 "
					+ "from q03 q where Q03Z0 > to_char(sysdate-2,'yyyy.mm.dd') and Q03Z0 < to_char(sysdate,'yyyy.mm.dd')"
					+ " and (q03AA='1' or q0341='1' or Q0340='1') and nbase='Usr'";
			// String sql = "select a0100,Q03Z0,q03AA,q0341,Q0340,(select e0127
			// from usra01 where a0100=q.a0100) e0127 "
			// + "from q03 q where a0100='00002441' and q03Z0 ='2019.03.27' ";

			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				StringBuffer sb = new StringBuffer();
				String a0100 = rs.getString("a0100");
				String e0127 = rs.getString("e0127");
				String e0122 = rs.getString("e0122");
				String a0101 = rs.getString("a0101");
				String Q03Z0 = rs.getString("Q03Z0");
				String q03AA = rs.getString("q03AA");
				String q0341 = rs.getString("q0341");
				String Q0340 = rs.getString("Q0340");
				sb.append("工号：" + e0127 + ",时间：" + Q03Z0 + ",缺打卡班次：");
				System.out.println(sb.toString());
				if (q03AA.equals("1")) {
					sb.append("上班缺刷卡  ");
				}
				if (q0341.equals("1")) {
					sb.append("中午没打卡  ");
				}
				if (Q0340.equals("1")) {
					sb.append("下班缺刷卡");
				}

				String sentence = sb.toString();
				System.out.println(sentence);
				KmReviewParamterForm krpf = new KmReviewParamterForm();
				// 将必须要传的参数放进来
				krpf.setDocCreator("{\"PersonNo\": \"" + e0127 + "\"}");
				krpf.setDocSubject("docSubject");
				// 测试
				krpf.setFdTemplateId("168187df32df188cc324aed4f918bcdc");// 正式
				// 将两个参数设置到 krpf 中 传json格式
				Map<String, String> krpfMap = new HashMap<String, String>();
				krpfMap.put("fd_person_no", e0127);
				krpfMap.put("fd_apply_dept", e0122);
				krpfMap.put("fd_apply_person", a0101);
				krpfMap.put("fd_remind", sentence);
				String jsonTemp = JsonUtil.map2json(krpfMap);
				krpf.setFormValues(jsonTemp);
				// jsonTemp 即为要传的参数
				System.out.println("jsonTemp:" + jsonTemp);
				try {
					String reset = service.addReview(krpf);
					System.out.println(reset);
					WriteIOstream.GetwritetoJson("考勤异常传的数据:", "传的数据" + jsonTemp + "回复数据：" + reset);
//					printMessage("考勤异常传的数据" + jsonTemp + "回复数据：" + reset);
				} catch (Exception e) {
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

	// 绩效提醒
	public void performanceMessage() {
		// 绩效提醒 传数据到OA
		IKmReviewWebserviceService service = new IKmReviewWebserviceServiceProxy();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			java.sql.DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			// 测试：jdbc:oracle:thin:@172.30.30.20:1521:yksoft1919
//			 正式：jdbc:oracle:thin:@172.30.11.93:1521:orcl
//			String url = "jdbc:oracle:thin:@172.30.30.20:1521:yksoft1919";
			String url = "jdbc:oracle:thin:@172.30.10.93:1521:orcl";
			// String url = "jdbc:oracle:thin:@172.30.10.113:1521:orcl";
			String username = "yksoft";
			String userpwd = "yksoft1919";
			Connection conn = java.sql.DriverManager.getConnection(url, username, userpwd);
			// 从数据库表 Z_SYS_ORG_ELEMENT 中获取工号，部门 ，姓名id
			String sql = "select a.e0127 e0127,a.a0101,b.fd_id nameID,d.fd_id deptID from usra01 a "
					+ "left join Z_SYS_ORG_ELEMENT b on  CONCAT('HR',a.guidkey) = b.fd_import_info "
					+ "left join organization c on c.codeitemid = a.e0122 "
					+ "left join Z_SYS_ORG_ELEMENT d on CONCAT('HR',c.guidkey) = d.fd_import_info "
					+ "where a.E0104='1' ";

			// String sql = "select e0127,a0100 from usra01 where e0127='002472' ";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery(sql);
			// System.out.println("rs" + rs);
			while (rs.next()) {
				String e0127 = rs.getString("e0127");
				String name = rs.getString("nameID");
				String dept = rs.getString("deptID");
				String a0101 = rs.getString("a0101");

				Calendar date = Calendar.getInstance();
				int year = date.get(Calendar.YEAR);
				int month = date.get(Calendar.MONTH) + 1;
				System.out.println("fd_period" + year + "." + month);

				String sentence = "1.员工须于每月第一个工作日填完绩效计划。\n"
						+ "2.评价人须在3个工作日内完成计划的审核与沟通，月末工作总结与绩效评分须在次月3号前完成，8号前最后一个工作日流程须结束，如未按以上时间要求进行处理，造成员工当月绩效无有效成绩，则由相应节点处理人负责。\n"
						+ "3.当月绩效结果将推送至您OA平台的待阅栏，请及时查阅。如有异议，须于推送之日起的3个工作日内提起绩效申诉，逾期则视为您认同此结果\n";
				KmReviewParamterForm krpf = new KmReviewParamterForm();
				// 将必须要传的参数放进来
				// form.setDocCreator("{\"PersonNo\": \"000994\"}")
				krpf.setDocCreator("{\"PersonNo\": \"" + e0127 + "\"}");
				krpf.setDocSubject(year + "年" + month + "月度绩效计划及评价-" + a0101 + "-" + e0127);
				// krpf.setFdTemplateId("1630be52c76773ab6c5f6714c87b4cc5");//动态写进
				// 测试
//				krpf.setFdTemplateId("16a3321e74947a149d9da8346d5925ff");// 测试
				
				krpf.setFdTemplateId("16a63559779e8cb2f93b0604d32857e2");// 正式
				// 将两个参数设置到 krpf 中 传json格式
				Map<String, String> krpfMap = new HashMap<String, String>();
				krpfMap.put("fd_apply_person", name);
				krpfMap.put("fd_person_no", e0127);
				krpfMap.put("fd_apply_dept", dept);
//				krpfMap.put("fd_warning", "考核评分完成后系统会将结果推送至您OA平台的待阅栏，请及时查阅。如有异议，须于推送之日起的3个工作日内提起绩效申诉，逾期则视为您认同此结果。");
				krpfMap.put("fd_remind", sentence);// fd_period
				krpfMap.put("fd_period", year + "年" + month + "月");
				String jsonTemp = JsonUtil.map2json(krpfMap);
				krpf.setFormValues(jsonTemp);
				// jsonTemp 即为要传的参数
				System.out.println("jsonTemp:" + jsonTemp);
				try {
					String reset = service.addReview(krpf);
					System.out.println(reset);
					WriteIOstream.GetwritetoJson("绩效提醒:", "传的数据" + jsonTemp + "回复数据：" + reset);
//					printMessage("传的数据" + jsonTemp + "回复数据：" + reset);
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("e0127:" + e0127);

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
		// FileOutputStream fs = new FileOutputStream(
		// new File("D:\\opt\\apache-tomcat-7.0.28\\logs\\" +
		// sdf.format(date.getTime()) + "text.txt"));
		// FileOutputStream fs = new FileOutputStream(
		// new File("D:\\apache-tomcat-7.0.73_1\\logs\\" +
		// sdf.format(date.getTime()) + "text.txt"));
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// String str ="写入时间："+ formatter.format(currentTime);
		String no = formatter.format(currentTime).substring(0, 7);
		String logPath = System.getProperty("catalina.home") + "\\logs\\" + no + "log.txt"; // 日志路径
		// File file = new File(logPath);// 指定要写入的文件
		FileOutputStream fs = new FileOutputStream(new File(logPath));
		PrintStream p = new PrintStream(fs);
		p.println(message);
		p.close();
	}
	//////////////////////////////////////////////////////
	//////////// cz代码
	//////////////////////////////////////////////////////
	@Scheduled(cron = "0 0 8 1 * ?")//每月1号8点
	public void synPerformenceJianCe() {
		System.out.println("--------绩效提醒---------");
		performanceMessageJianCe();
	}


	@ResponseBody
	@RequestMapping("/testRedirect1")
	public String testRedirect1(){
		System.out.println("testRedirect1");
		return "123";
	}
	// 绩效提醒
	public void performanceMessageJianCe() {
		// 绩效提醒 传数据到OA
		IKmReviewWebserviceService service = new IKmReviewWebserviceServiceProxy();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			java.sql.DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			// 测试：jdbc:oracle:thin:@172.30.30.20:1521:yksoft1919
//			 正式：jdbc:oracle:thin:@172.30.11.93:1521:orcl
//			String url = "jdbc:oracle:thin:@172.30.30.20:1521:yksoft1919";
			String url = "jdbc:oracle:thin:@172.30.10.93:1521:orcl";
			// String url = "jdbc:oracle:thin:@172.30.10.113:1521:orcl";
			String username = "yksoft";
			String userpwd = "yksoft1919";
			Connection conn = java.sql.DriverManager.getConnection(url, username, userpwd);
			// 从数据库表 Z_SYS_ORG_ELEMENT 中获取工号，部门 ，姓名id
			String sql = "select a.e0127 e0127,a.a0101,b.fd_id nameID,d.fd_id deptID from usra01 a "
					+ "left join Z_SYS_ORG_ELEMENT b on  CONCAT('HR',a.guidkey) = b.fd_import_info "
					+ "left join organization c on c.codeitemid = a.e0122 "
					+ "left join Z_SYS_ORG_ELEMENT d on CONCAT('HR',c.guidkey) = d.fd_import_info "
					+ "where a.E0104='1' ";

			// String sql = "select e0127,a0100 from usra01 where e0127='002472' ";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery(sql);
			// System.out.println("rs" + rs);
			while (rs.next()) {
				String e0127 = rs.getString("e0127");
				String name = rs.getString("nameID");
				String dept = rs.getString("deptID");
				String a0101 = rs.getString("a0101");

				Calendar date = Calendar.getInstance();
				int year = date.get(Calendar.YEAR);
				int month = date.get(Calendar.MONTH) + 1;
				System.out.println("fd_period" + year + "." + month);

				String sentence = "1.员工须于每月第一个工作日填完绩效计划。\n"
						+ "2.评价人须在3个工作日内完成计划的审核与沟通，月末工作总结与绩效评分须在次月3号前完成，8号前最后一个工作日流程须结束，如未按以上时间要求进行处理，造成员工当月绩效无有效成绩，则由相应节点处理人负责。\n"
						+ "3.当月绩效结果将推送至您OA平台的待阅栏，请及时查阅。如有异议，须于推送之日起的3个工作日内提起绩效申诉，逾期则视为您认同此结果\n";
				KmReviewParamterForm krpf = new KmReviewParamterForm();
				// 将必须要传的参数放进来
				// form.setDocCreator("{\"PersonNo\": \"000994\"}")
				krpf.setDocCreator("{\"PersonNo\": \"" + e0127 + "\"}");
				krpf.setDocSubject(year + "年" + month + "月度绩效计划及评价-" + a0101 + "-" + e0127);
				// krpf.setFdTemplateId("1630be52c76773ab6c5f6714c87b4cc5");//动态写进
				// 测试
//				krpf.setFdTemplateId("16a3321e74947a149d9da8346d5925ff");// 测试

				krpf.setFdTemplateId("16a63559779e8cb2f93b0604d32857e2");// 正式
				// 将两个参数设置到 krpf 中 传json格式
				Map<String, String> krpfMap = new HashMap<String, String>();
				krpfMap.put("fd_apply_person", name);
				krpfMap.put("fd_person_no", e0127);
				krpfMap.put("fd_apply_dept", dept);
//				krpfMap.put("fd_warning", "考核评分完成后系统会将结果推送至您OA平台的待阅栏，请及时查阅。如有异议，须于推送之日起的3个工作日内提起绩效申诉，逾期则视为您认同此结果。");
				krpfMap.put("fd_remind", sentence);// fd_period
				krpfMap.put("fd_period", year + "年" + month + "月");
				String jsonTemp = JsonUtil.map2json(krpfMap);
				krpf.setFormValues(jsonTemp);
				// jsonTemp 即为要传的参数
				System.out.println("jsonTemp:" + jsonTemp);
				try {
					String reset = service.addReview(krpf);
					System.out.println(reset);
					WriteIOstream.GetwritetoJson("绩效提醒:", "传的数据" + jsonTemp + "回复数据：" + reset);
//					printMessage("传的数据" + jsonTemp + "回复数据：" + reset);
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("e0127:" + e0127);

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
}
