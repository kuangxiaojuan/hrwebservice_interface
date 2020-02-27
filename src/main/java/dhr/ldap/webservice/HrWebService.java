package dhr.ldap.webservice;

import java.sql.SQLException;
import java.util.Map;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.xml.ws.Endpoint;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@WebService
public interface HrWebService {
	
	//入职申请
	@WebMethod
	String writePersonel(String requestBody);
	
	//调动申请
	@WebMethod
	String requestForTransfer(String B0110,String E0127,String A17AK,String A17AV,String A17AG,String A17AH,String A5551, String A5540,String E5501,String E5504,String E5507,String A17AJ,String A17AL,String A5545);
	
	//离职申请
	@WebMethod
	String requestForDimission(String E0127,String AA307,String AA309,String AA305,String AA314) throws SQLException;
	
	//请休假申请
	@WebMethod
	String requestForLeave(String requestBody);
	
	//加班申请
	@WebMethod
	String requestForOvertime(String requestBody);
	
	//试用考核
	@WebMethod
	String requestForOut(String E0127,String E1403,String E1405,String A1403,String C1402,String A5540,String E5501,String E5504,String E5507,String A5545);
	
	//续签合同
	@WebMethod
	String requestForTrip(String E0127,String AZ3AH,String AZ3AC,String AZ3AA,String AZ303,String AZ304,String AZ302,String AZ3AE);
	
	
	//打卡异常
	@WebMethod
	String requestForTimeCard(String E0127,String type,String Q13Z1,String Q13Z3,String Q1307,String Work_date,String Work_time,String Oper_cause);
	
	//人员任免审
//	@WebMethod
//	String toAppointorRemove(String E0127,String A5540,String E5501,String E5504,String E5507,String AAHAC,String AAHAE,String AAHAB,String AAHAA,String A17AH, String A17AA,String A17AG,String A17AK,String A17AV);
	
	//查询年假调休假天数
	String getnumberDays(String A0177,String type) throws Exception;
	
	//表头信息
	Map<String, Object> PersonnelBasic(String E0127);
	
	//获取人员转正，入职日期
	Map<String, Object> personBydate(String E0127);

	/**
	 * @return 人员任免审
	 */
	@WebMethod
	String toAppointorRemove(String toAppointorRemoveInfo);

	/**
	 * @return 销假流程
	 */
	@WebMethod
	String salesLeave(String salesLeaveInfo);

	/**
	 * @return 绩效添加流程
	 */
	@WebMethod
	String insertPerformence(String performanceInfo);
	
	/**
	 * @return 绩效添加流程
	 */
	@WebMethod
	String updatePerformence(String performanceInfo);
	
}
