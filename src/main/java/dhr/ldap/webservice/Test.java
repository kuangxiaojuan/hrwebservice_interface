package dhr.ldap.webservice;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.xml.ws.Endpoint;

import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import dhr.utils.JsonUtil;
import dhr.utils.WriteIOstream;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.text.DateFormat;
import javax.xml.namespace.QName;
import java.lang.Integer;
import javax.xml.rpc.ParameterMode;

@SuppressWarnings("unchecked")
@Controller
public class Test {
	
//		
//		@RequestMapping(value = "getUnitBasiciIformation",method = RequestMethod.GET)
//		public @ResponseBody void UnitBasiciIformation(){
//		System.out.println(123);
//		 Endpoint.publish("http://127.0.0.1:8080/hrwebservice/services?wsdl", new HrWebServiceImpl().PersonnelBasic("000914"));  
//		 System.out.println("发布成功！");
//		}
	
//	public static void main(String [] args){  
//        Endpoint.publish("http://127.0.0.1:8080/hrwebservice/ws/services?wsdl", new HrWebServiceImpl());
////		Endpoint.publish("http://172.30.30.20:8080/hrwebservice/ws/services?wsdl", new HrWebServiceImpl());
////		http://172.30.30.20:8080/hrwebservice/ws/services?wsdl
//		System.out.println("发布成功！");
//    }  
	public static void main(String[] args) {
		Test test = new Test();
		test.writePersonel();
	}
	
	
	
	public String writePersonel(){
		String sql = "";
		String str4 ="";
		String str5 ="";
		String requestBody="{\"requestContext\":{\"E0127\":\"12345\",\"A01AH\":\"2017-11-20\",\"A0101\":\"张三\",\"A0107\":\"M\",\"E0122\":\"C42AEDF1DEC1457682794DA8729686B9\",\"A0127\":\"001\",\"A0111\":\"1995-11-29\",\"A0121\":\"01\",\"A01AX\":\"01\",\"A010A\":\"180\",\"A010B\":\"75\",\"A0114\":\"广东深圳\",\"B0110\":\"E123AF2625A24DAEBEF94833C5B23CF3\",\"E0122\":\"C42AEDF1DEC1457682794DA8729686B9\",\"E01A1\":\"69EAA8076C884E15B027A62B083EFF01\",\"A01AP\":\"01\",\"A0124\":\"1\",\"A0177\":\"612501199511297310\",\"C0104\":\"123456\",\"A0171\":\"广东\",\"A0141\":\"2017-11-30\",\"A010C\":\"广东\",\"A0182\":\"2017-01-01\",\"A01AI\":\"广东深圳\",\"A01AL\":\"广东深圳\",\"A0184\":\"李四\",\"A010D\":\"1998-11-29\",\"A010E\":\"03\",\"A010F\":\"广西\",\"A010G\":\"美容\",\"A0194\":\"678908764\",\"chengyuan\":[{\"A7905\":\"李四\",\"A7910\":\"001\",\"A79AG\":\"23\",\"A7920\":\"美容\",\"A79AB\":\"4321234\"},{\"A7905\":\"李四wer\",\"A7910\":\"001\",\"A79AG\":\"23\",\"A7920\":\"美we容\",\"A79AB\":\"4321234\"}],\"work\":[{\"A1905\":\"2016-7-8\",\"A1910\":\"2017-8-8\",\"A1915\":\"花花\",\"A190D\":\"人事部\",\"A1920\":\"经理\",\"A19AE\":\"张三\",\"A19AF\":\"9876543\"},{\"A1905\":\"2016-10-8\",\"A1910\":\"2017-11-8\",\"A1915\":\"花花\",\"A190D\":\"人事eee\",\"A1920\":\"经理\",\"A19AE\":\"张三\",\"A19AF\":\"9876543\"}],\"TheContract\":[{\"AZ302\":\"24\",\"AZ301\":\"001\",\"AZ303\":\"2017-11-20\",\"AZ304\":\"2019-11-20\"},{\"AZ302\":\"24\",\"AZ301\":\"001\",\"AZ303\":\"2017-11-20\",\"AZ304\":\"2019-11-20\"}],\"positive\":[{\"A14AB\":\"1245\",\"A14AA\":\"1234\",\"C1401\":\"3\"},{\"A14AB\":\"1245\",\"A14AA\":\"1234\",\"C1401\":\"3\"}],\"Education\":[{\"A0415\":\"2011-9-1\",\"A0430\":\"2015-7-1\",\"A0435\":\"深圳大学\",\"A0410\":\"修仙飞升\",\"A0420\":\"001\",\"A0405\":\"01\",\"A0440\":\"2\"},{\"A0415\":\"2011-9-1\",\"A0430\":\"2015-7-1\",\"A0435\":\"深圳大学\",\"A0410\":\"修仙飞升\",\"A0420\":\"001\",\"A0405\":\"01\",\"A0440\":\"2\"}]}}";
		Map<String,Object> person = JsonUtil.json2Map(requestBody);
		@SuppressWarnings("unchecked")
		Map<String,Object> map = (Map<String, Object>)person.get("requestContext");  //A01
//		List<Map<String,Object>> list2 = (List<Map<String, Object>>)map.get("chengyuan");           //AZX
//		List<Map<String,Object>> list3 = (List<Map<String, Object>>)map.get("positive");
//		List<Map<String,Object>> list4 = (List<Map<String, Object>>)map.get("TheContract");
//		List<Map<String,Object>> list5 = (List<Map<String, Object>>)map.get("Education");
		List<Map<String,Object>> list = (List<Map<String, Object>>)map.get("work");
		if(list==null||list.size()<1){
			return null;
		}
//		if(list.size()<1){
//			return null;
//		}
		Map<String, Object> mapson = list.get(0);
		List<String> listStr = new ArrayList<>();
		Set<String> set = mapson.keySet();
		for (String str : set) {
			listStr.add(str);
		}
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("INSERT INTO "+map.get("tableName")+" (A0100,I9999,");
		for (int i=0;i<listStr.size();i++) {
			sqlBuilder.append(listStr.get(i));
			if(i<(listStr.size()-1)){
				sqlBuilder.append(",");
			}else{
				sqlBuilder.append(")  select "); 
			}
		}
		//MessageFormat mf = new MessageFormat(str);
//		for(int i=0;i<list.size();i++){
//			String str = "";
//			for (int j=0; j<listStr.size();j++) {
//				boolean verdict = true;
//				String values = "#{list["+i+"]."+listStr.get(j)+"}";
//				boolean str1 = values.toString().indexOf("-")!=-1;
//				int str2 = values.toString().length();
//				boolean str3 = values.toString().indexOf(":")!=-1;
//				if((str1 && str2 <=10) && str2>=8){
//					str4 = "to_date('"+values+"','yyyy-mm-dd')";
//					
//				}
//				if((str3 && str1) && str2 >10){
//					str5 = "to_date('"+values+"','yyyy-mm-dd hh24:mi:ss')";
//				}
//				if(str4 != null ){
//					if(str4.toString().indexOf(values)!=-1){
//						str = str + str4+",";
//						verdict = false;
//					}
//				}
//				if(str5 != null ){
//					if(str5.toString().indexOf(values)!=-1){
//						str = str + str5+",";
//						verdict = false;
//					}
//				}
//				if(verdict=false){
//					continue;
//				}
//				
//				str = str + "#{list["+i+"]."+listStr.get(j)+"} ,";
//			}
		
		for(int i=0;i<list.size();i++){
			String str = "";
			for (String key : set) {
				boolean verdict = true;
				String values = list.get(i).get(key).toString();
				boolean str1 = values.indexOf("-")!=-1;
				int str2 = values.length();
				boolean str3 = values.indexOf(":")!=-1;
				if((str1 && str2 <=10) && str2>=8){
					values = str4 = "to_date('"+values+"','yyyy-mm-dd')";
					str = str + values +",";
				}else if((str3 && str1) && str2 >10){
					values = str5 = "to_date('"+values+"','yyyy-mm-dd hh24:mi:ss')";
					str = str +values+",";
				}else{
					str = str + "'"+values +"',";
				}
			}
//			str = str + "#{list["+i+"]."+listStr.get(j)+"} ,";
			str = str.substring(0,str.length()-1);
			sqlBuilder.append("'"+map.get("A0100")+"',"+(i+1)+","+str+" from dual union all select");
		}
        
//		sqlBuilder.setLength(sqlBuilder.length()-17);
		sql = sqlBuilder.toString();
		sql = sqlBuilder.substring(0, sql.lastIndexOf("union"));
		System.out.println(sql);
		return sql;
	}
}
