package dhr.sap.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

//import cn.fhsj.ConnSAP;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;

import dhr.utils.WriteIOstream;


/**
 * @author Administrator
 * 传入参数 ： 
 * EMPLOYEE - 员工号
 * EMPLOYEE_NAME - 员工姓名
 * BUKRS - 公司代码
 * BUTXT - 公司代码或公司的名称
 * TEL_NUMBER - 电话
 * IDNUMBER - 证件号码
 * BANKL - 银行代码
 * BANKL_DES - 银行代码描述
 * ACCOUNT - 银行账号
 */

@Controller
public class GetFico005 {

//	@Scheduled(cron="0 40 15 * * ? ") 
//	@Scheduled(cron = "0/100 * * * * ?")
	@Scheduled(cron = "0 55 23 * * ?")
	public void synOrg() throws ParseException{
		System.out.println("开始同步数据到SAP");
		SAPConnect();
	}
	public static void SAPConnect() {
		
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			Date date=new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			date = calendar.getTime();
		
			String SYNDate = sdf.format(date);
			System.out.println("SYNDate:"+SYNDate);
			
			String e0127 = null;
			String a0101 = null;
			String b0110 = null;
			String b0110DESC = null;
			String c0104 = null;  //联系电话
			String a0177 = null;
			String a01AN = null;  //开户行
			String a01AM = null;  //银行卡号
			String flag = null;        //离职和非离职
			String e0122Parent = null; //区域
			String e0122 = null;  //商务中心
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			java.sql.DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			// 测试：jdbc:oracle:thin:@172.30.30.20:1521:yksoft1919
			// 正式：jdbc:oracle:thin:@172.30.11.93:1521:orcl
//			String url = "jdbc:oracle:thin:@172.30.30.20:1521:yksoft1919";
			String url = "jdbc:oracle:thin:@172.30.10.93:1521:orcl";
			String username = "yksoft";
			String userpwd = "yksoft1919";
			Connection conn = java.sql.DriverManager.getConnection(url, username, userpwd);
			
			// 从数据库表 hrpwarn_result 中获取a0100 然后根据a0100获取工号    view_syn_sap
			String sql ="SELECT * FROM VIEW_SYN_SAP "
					+ "WHERE CREATETIME > TO_DATE('"+SYNDate+" 23:55:00','yyyy-mm-dd hh24:mi:ss') "
							+ " OR MODTIME > TO_DATE('"+SYNDate+" 23:55:00','yyyy-mm-dd hh24:mi:ss') ";
			System.out.println("sql:"+sql);
			WriteIOstream.GetwritetoJson("SAP同步数据:", "传的数据sql：" + sql);
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				e0127 = rs.getString("E0127");
				a0101 = rs.getString("A0101");
				b0110 = rs.getString("B0110");
				b0110DESC = rs.getString("B0110DESC");
				c0104 = rs.getString("C0104");
				a0177 = rs.getString("A0177");
				a01AN = rs.getString("A01AN");
				a01AM = rs.getString("A01AM");
				flag = rs.getString("FLAG");
				e0122Parent = rs.getString("E0122PARENT");
				e0122 = rs.getString("E0122");
			
				JCoFunction function = null;    //		ZFICO005
				//连接sap，其实就类似于连接数据库
				JCoDestination destination = ConnSAP.connect();
				//调用FICO005函数
				function = destination.getRepository().getFunction("ZFICO005");
				if(function == null ){
					WriteIOstream.GetwritetoJson("SAP同步数据异常:工号："+e0127, "BAPI_COMPANYCODE_GETDETAIL not found in SAP.");
					continue;
//					 throw new RuntimeException(
//					         "BAPI_COMPANYCODE_GETDETAIL not found in SAP.");
				}
				
				function.getImportParameterList().setValue("IV_FLAG", "C");
				//赋值
				JCoTable coTable = function.getTableParameterList().getTable("IT_ZFICO005_TAB");
				coTable.appendRow();//增加1行
				coTable.setValue("EMPLOYEE", e0127);
				coTable.setValue("EMPLOYEE_NAME", a0101);
				coTable.setValue("BUKRS", b0110);
				coTable.setValue("BUTXT", b0110DESC);
				coTable.setValue("TEL_NUMBER", c0104);
				coTable.setValue("IDNUMBER", a0177);
				coTable.setValue("BANKL", "");   //银行代码
				coTable.setValue("BANKL_DES", a01AN);  //银行代码描述
				coTable.setValue("ACCOUNT", a01AM);         //银行账号
				coTable.setValue("ZFLAG", flag);          //离职和非离职
				coTable.setValue("ZREGION", e0122Parent);    //区域
				coTable.setValue("ZBUSSINESS_CTR", e0122);   //商务中心
				
				function.execute(destination);
				
//				JCoTable table = function.getTableParameterList().getTable("ET_RETURN");
//				for (int i = 0; i < table.getMetaData().getFieldCount(); i++) {
//					System.out.println("tableName:"+table.getMetaData().getName(i));
//				}
//				System.out.println("MESSAGE:"+table.getString("MESSAGE")+"TYPE:"+table.getString("TYPE"));
				WriteIOstream.GetwritetoJson("SAP同步返回数据:工号："+e0127,"工号为  "+ e0127+" 的员工信息已同步至SAP");
//				for (int i = 0; i < table.getNumRows(); i++) {
//					for (int j = 0; j < table.getMetaData().getFieldCount(); j++) {
//						System.out.println("name:"+j+" --value:"+table.getString(j));
//					}
//				}		
				
			}
			stmt.close();
			conn.close();
			System.out.println("over");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void SAPConnectAll() {
//		JCoFunction function = null;    //		ZFICO005
//		//连接sap，其实就类似于连接数据库
//		JCoDestination destination = ConnSAP.connect();
		try {
			String e0127 = null;
			String a0101 = null;
			String b0110 = null;
			String b0110DESC = null;
			String c0104 = null;  //联系电话
			String a0177 = null;
			String a01AN = null;  //开户行
			String a01AM = null;  //银行卡号
			String flag = null;        //离职和非离职
			String e0122Parent = null; //区域
			String e0122 = null;  //商务中心
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			java.sql.DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			// 测试：jdbc:oracle:thin:@172.30.30.20:1521:yksoft1919
			// 正式：jdbc:oracle:thin:@172.30.11.93:1521:orcl
//			String url = "jdbc:oracle:thin:@172.30.30.20:1521:yksoft1919";
			String url = "jdbc:oracle:thin:@172.30.10.93:1521:orcl";
			String username = "yksoft";
			String userpwd = "yksoft1919";
			Connection conn = java.sql.DriverManager.getConnection(url, username, userpwd);
			
			// 从数据库表 hrpwarn_result 中获取a0100 然后根据a0100获取工号    view_syn_sap
			String sql ="SELECT * FROM VIEW_SYN_SAP_All ";
			System.out.println("sql:"+sql);
			WriteIOstream.GetwritetoJson("SAP同步数据:", "传的数据sql：" + sql);
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				e0127 = rs.getString("E0127");
				a0101 = rs.getString("A0101");
				b0110 = rs.getString("B0110");
				b0110DESC = rs.getString("B0110DESC");
				c0104 = rs.getString("C0104");
				a0177 = rs.getString("A0177");
				a01AN = rs.getString("A01AN");
				a01AM = rs.getString("A01AM");
				flag = rs.getString("FLAG");
				e0122Parent = rs.getString("E0122PARENT");
				e0122 = rs.getString("E0122");
			
				JCoFunction function = null;    //		ZFICO005
				//连接sap，其实就类似于连接数据库
				JCoDestination destination = ConnSAP.connect();
				//调用FICO005函数
				function = destination.getRepository().getFunction("ZFICO005");
				if(function == null ){
					WriteIOstream.GetwritetoJson("SAP同步数据异常:工号："+e0127, "BAPI_COMPANYCODE_GETDETAIL not found in SAP.");
					continue;
//					 throw new RuntimeException(
//					         "BAPI_COMPANYCODE_GETDETAIL not found in SAP.");
				}
				
				function.getImportParameterList().setValue("IV_FLAG", "C");
				//赋值
				JCoTable coTable = function.getTableParameterList().getTable("IT_ZFICO005_TAB");
				coTable.appendRow();//增加1行
				coTable.setValue("EMPLOYEE", e0127);
				coTable.setValue("EMPLOYEE_NAME", a0101);
				coTable.setValue("BUKRS", b0110);
				coTable.setValue("BUTXT", b0110DESC);
				coTable.setValue("TEL_NUMBER", c0104);
				coTable.setValue("IDNUMBER", a0177);
				coTable.setValue("BANKL", "");   //银行代码
				coTable.setValue("BANKL_DES", a01AN);  //银行代码描述
				coTable.setValue("ACCOUNT", a01AM);         //银行账号
				coTable.setValue("ZFLAG", flag);          //离职和非离职
				coTable.setValue("ZREGION", e0122Parent);    //区域
				coTable.setValue("ZBUSSINESS_CTR", e0122);   //商务中心
				
				function.execute(destination);
				
//				JCoTable table = function.getTableParameterList().getTable("ET_RETURN");
				WriteIOstream.GetwritetoJson("SAP同步返回数据:工号："+e0127,"工号为  "+ e0127+" 的员工信息已同步至SAP");
//				for (int i = 0; i < table.getMetaData().getFieldCount(); i++) {
//					System.out.println("tableName:"+table.getMetaData().getName(i));
//				}
//				System.out.println("MESSAGE:"+table.getString("MESSAGE")+"TYPE:"+table.getString("TYPE"));
//				WriteIOstream.GetwritetoJson("SAP同步返回数据:工号："+e0127, "MESSAGE:"+table.getString("MESSAGE")+"TYPE:"+table.getString("TYPE"));
//				for (int i = 0; i < table.getNumRows(); i++) {
//					for (int j = 0; j < table.getMetaData().getFieldCount(); j++) {
//						System.out.println("name:"+j+" --value:"+table.getString(j));
//					}
//				}		
				
			}
			stmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		SAPConnect();
	}
	
}
