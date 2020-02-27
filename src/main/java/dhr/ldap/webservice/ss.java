package dhr.ldap.webservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import dhr.utils.JsonUtil;
import dhr.utils.WriteIOstream;

public class ss {
	

//	public String writePersonel(String requestBody) {
//		// TODO Auto-generated method stub
//		requestBody="{\"chengyuan\":[{\"A7905\":\"李四\",\"A7910\":\"001\",\"A79AG\":\"23\",\"A7920\":\"美容\",\"A79AB\":\"4321234\"},{\"A7905\":\"李四wer\",\"A7910\":\"001\",\"A79AG\":\"23\",\"A7920\":\"美we容\",\"A79AB\":\"4321234\"}],\"work\":[{\"A1905\":\"2016-7-8\",\"A1910\":\"2017-8-8\",\"A1915\":\"花花\",\"A190D\":\"人事部\",\"A1920\":\"经理\",\"A19AE\":\"张三\",\"A19AF\":\"9876543\"},{\"A1905\":\"2016-7-8\",\"A1910\":\"2017-8-8\",\"A1915\":\"花花\",\"A190D\":\"人事eee\",\"A1920\":\"经理\",\"A19AE\":\"张三\",\"A19AF\":\"9876543\"}],\"TheContract\":[{\"AZ302\":\"24\",\"AZ301\":\"001\",\"AZ303\":\"2017-11-20\",\"AZ304\":\"2019-11-20\"},{\"AZ302\":\"24\",\"AZ301\":\"001\",\"AZ303\":\"2017-11-20\",\"AZ304\":\"2019-11-20\"}],\"positive\":[{\"A14AB\":\"1245\",\"A14AA\":\"1234\",\"C1401\":\"3\"},{\"A14AB\":\"1245\",\"A14AA\":\"1234\",\"C1401\":\"3\"}],\"Education\":[{\"A0415\":\"2011-9-1\",\"A0430\":\"2015-7-1\",\"A0435\":\"深圳大学\",\"A0410\":\"修仙飞升\",\"A0420\":\"001\",\"A0405\":\"01\",\"A0440\":\"2\"},{\"A0415\":\"2011-9-1\",\"A0430\":\"2015-7-1\",\"A0435\":\"深圳大学\",\"A0410\":\"修仙飞升\",\"A0420\":\"001\",\"A0405\":\"01\",\"A0440\":\"2\"}],\"requestContext\":[{\"E0127\":\"12345\",\"A01AH\":\"2017-11-20\",\"A0101\":\"张三\",\"A0107\":\"M\",\"A0199\":\"1000070D\",\"E0122\":\"C42AEDF1DEC1457682794DA8729686B9\",\"A0127\":\"001\",\"A0111\":\"1995-11-29\",\"A0121\":\"01\",\"A01AX\":\"01\",\"A010A\":\"180\",\"A010B\":\"75\",\"A0114\":\"广东深圳\",\"B0110\":\"E123AF2625A24DAEBEF94833C5B23CF3\",\"E0122\":\"C42AEDF1DEC1457682794DA8729686B9\",\"E01A1\":\"69EAA8076C884E15B027A62B083EFF01\",\"A01AP\":\"01\",\"A0124\":\"1\",\"A0177\":\"612501199511297310\",\"C0104\":\"123456\",\"A0171\":\"广东\",\"A0141\":\"2017-11-30\",\"A010C\":\"广东\",\"A0182\":\"2017-01-01\",\"A01AI\":\"广东深圳\",\"A01AL\":\"广东深圳\",\"A0184\":\"李四\",\"A010D\":\"1998-11-29\",\"A010E\":\"03\",\"A010F\":\"广西\",\"A010G\":\"美容\",\"A0194\":\"678908764\"}]}";
////		requestBody="{\"chengyuan\":[{\"A7905\":\"李四\",\"A7910\":\"001\",\"A79AG\":\"23\",\"A7920\":\"美容\",\"A79AB\":\"4321234\"},{\"A7905\":\"李四wer\",\"A7910\":\"001\",\"A79AG\":\"23\",\"A7920\":\"美we容\",\"A79AB\":\"4321234\"}],\"work\":[{\"A1905\":\"2016-7-8\",\"A1910\":\"2017-8-8\",\"A1915\":\"花花\",\"A190D\":\"人事部\",\"A1920\":\"经理\",\"A19AE\":\"张三\",\"A19AF\":\"9876543\"},{\"A1905\":\"2016-7-8\",\"A1910\":\"2017-8-8\",\"A1915\":\"花花\",\"A190D\":\"人事eee\",\"A1920\":\"经理\",\"A19AE\":\"张三\",\"A19AF\":\"9876543\"}],\"TheContract\":[{\"AZ302\":\"24\",\"AZ301\":\"001\",\"AZ303\":\"2017-11-20\",\"AZ304\":\"2019-11-20\"},{\"AZ302\":\"24\",\"AZ301\":\"001\",\"AZ303\":\"2017-11-20\",\"AZ304\":\"2019-11-20\"}],\"positive\":[{\"A14AB\":\"1245\",\"A14AA\":\"1234\",\"C1401\":\"3\"},{\"A14AB\":\"1245\",\"A14AA\":\"1234\",\"C1401\":\"3\"}],\"Education\":[{\"A0415\":\"2011-9-1\",\"A0430\":\"2015-7-1\",\"A0435\":\"深圳大学\",\"A0410\":\"修仙飞升\",\"A0420\":\"001\",\"A0405\":\"01\",\"A0440\":\"2\"},{\"A0415\":\"2011-9-1\",\"A0430\":\"2015-7-1\",\"A0435\":\"深圳大学\",\"A0410\":\"修仙飞升\",\"A0420\":\"001\",\"A0405\":\"01\",\"A0440\":\"2\"}],\"requestContext\":[{\"E0127\":\"12345\",\"A0101\":\"张三\",\"A0107\":\"M\",\"A0199\":\"1000070D\",\"E0122\":\"C42AEDF1DEC1457682794DA8729686B9\",\"A0127\":\"001\",\"A0121\":\"01\",\"A01AX\":\"01\",\"A010A\":\"180\",\"A010B\":\"75\",\"A0114\":\"广东深圳\",\"B0110\":\"E123AF2625A24DAEBEF94833C5B23CF3\",\"E0122\":\"C42AEDF1DEC1457682794DA8729686B9\",\"E01A1\":\"69EAA8076C884E15B027A62B083EFF01\",\"A01AP\":\"01\",\"A0124\":\"1\",\"A0177\":\"612501199511297310\",\"C0104\":\"123456\",\"A0171\":\"广东\",\"A010C\":\"广东\",\"A01AI\":\"广东深圳\",\"A01AL\":\"广东深圳\",\"A0184\":\"李四\",\"A010E\":\"03\",\"A010F\":\"广西\",\"A010G\":\"美容\",\"A0194\":\"678908764\"}]}";
//		System.out.println(requestBody+" 入职");
//		WriteIOstream.GetwritetoJson("入职信息",requestBody);
//		Map<String,Object> person = JsonUtil.json2Map(requestBody);
//		@SuppressWarnings("unchecked")
//		List<Map<String,Object>> list = (List<Map<String, Object>>)person.get("requestContext");  //A01
//		List<Map<String,Object>> list2 = (List<Map<String, Object>>)person.get("chengyuan");           //AZX
//		List<Map<String,Object>> list3 = (List<Map<String, Object>>)person.get("positive");
//		List<Map<String,Object>> list4 = (List<Map<String, Object>>)person.get("TheContract");
//		List<Map<String,Object>> list5 = (List<Map<String, Object>>)person.get("Education");
//		List<Map<String,Object>> list6 = (List<Map<String, Object>>)person.get("work");
////		Map<String, Object> B0110Map = baseDao.Queryzzjg(list.get(0).get("B0110").toString());
////		Map<String, Object> E0122Map = baseDao.Queryzzjg(list.get(0).get("E0122").toString());
////		Map<String, Object> E01A1Map = baseDao.Queryzzjg(list.get(0).get("E01A1").toString());
////		String A0000 = baseDao.QueryA0000();
////		String A0100 =baseDao.QUERYMAXA0100();
////		list.get(0).put("A0000", A0100);
////		list.get(0).put("A0100", A0100);
////		list.get(0).put("B0110", B0110Map.get("CODEITEMID"));
////		list.get(0).put("E0122", E0122Map.get("CODEITEMID"));
////		list.get(0).put("E01A1", E01A1Map.get("CODEITEMID"));
//		Map<String, Object> person2 = new HashMap<>();
////		baseDao.insertBatch("UsrA01",list);
////		baseDao.insertBatchReturnRows("UsrA01",list);
//		String filterFields = "";
//		String fields2 = "";
//		String values2 = "";
//		String str4 ="";
//		String str5 ="";
//		String sql="";
//		String values="";
//		String date = null;
//		String datetime;
//		for (int i = 0; i < list6.size(); i++) {
//			for (Map<String, Object> person1 : list6) {
//				for (String key : person1.keySet()) {
//					if (filterFields.indexOf(key) > -1 
//							|| person1.get(key) == null
//							|| "".equals(person1.get(key))
//							|| "null".equals(person1.get(key))
//							){
//						continue;
//					}
//					boolean str = person1.get(key).toString().indexOf("-")!=-1;
//					int str2 = person1.get(key).toString().length();
//					boolean str3 = person1.get(key).toString().indexOf(":")!=-1;
//					if((str && str2 <=10) && str2>=8){
//						str4 = "to_date('"+person1.get(key)+"','yyyy-mm-dd')";
//						person2.put("DATE", str4);
//					}
//					if((str3 && str) && str2 >10){
//						str5 = "to_date('"+person1.get(key)+"','yyyy-mm-dd hh24:mi:ss')";
//						person2.put("DATETIME", str5);
//					}
//					if(person2.get("DATE")!=null){
//						if(person2.get("DATE").toString().indexOf(person1.get(key).toString()) != -1 && str ){
//							date = person1.get(key).toString();
//						}
//					}
//					if(person2.get("DATETIME")!=null){
//						if( person2.get("DATETIME").toString().indexOf(person1.get(key).toString())!=-1 && str && str3){
//							datetime = person1.get(key).toString();
//						}
//					}
//					fields2 = fields2 + key + ",";
//					if(date != null && person1.get(key).equals(date)){
//						values2 = values2 + person2.get("DATE").toString() +",";
//					}else{
//						values2 = values2 +"'"+person1.get(key)+"',";
//					}
//				}
//			}
//			fields2 = fields2.substring(0, fields2.length()-1);
//			values2 = values2.substring(0, values2.length()-1) ;
//			String sql_2 = "INSERT INTO UsrA01("+fields2+") VALUES("+values2+")" ;
//			System.out.println(sql_2);
//		}
//		return "";
//			
//		}
//	public static void main(String[] args) {
//		ss ff = new ss();
//		ff.writePersonel("");
//	}
	
	
	
	
	public void writePersonel(String requestBody) {
		// TODO Auto-generated method stub
		requestBody="{\"chengyuan\":[{\"A7905\":\"李四\",\"A7910\":\"001\",\"A79AG\":\"23\",\"A7920\":\"美容\",\"A79AB\":\"4321234\"},{\"A7905\":\"李四wer\",\"A7910\":\"001\",\"A79AG\":\"23\",\"A7920\":\"美we容\",\"A79AB\":\"4321234\"}],\"work\":[{\"A1905\":\"2016-7-8\",\"A1910\":\"2017-8-8\",\"A1915\":\"花花\",\"A190D\":\"人事部\",\"A1920\":\"经理\",\"A19AE\":\"张三\",\"A19AF\":\"9876543\"},{\"A1905\":\"2016-7-8\",\"A1910\":\"2017-8-8\",\"A1915\":\"花花\",\"A190D\":\"人事eee\",\"A1920\":\"经理\",\"A19AE\":\"张三\",\"A19AF\":\"9876543\"}],\"TheContract\":[{\"AZ302\":\"24\",\"AZ301\":\"001\",\"AZ303\":\"2017-11-20\",\"AZ304\":\"2019-11-20\"},{\"AZ302\":\"24\",\"AZ301\":\"001\",\"AZ303\":\"2017-11-20\",\"AZ304\":\"2019-11-20\"}],\"positive\":[{\"A14AB\":\"1245\",\"A14AA\":\"1234\",\"C1401\":\"3\"},{\"A14AB\":\"1245\",\"A14AA\":\"1234\",\"C1401\":\"3\"}],\"Education\":[{\"A0415\":\"2011-9-1\",\"A0430\":\"2015-7-1\",\"A0435\":\"深圳大学\",\"A0410\":\"修仙飞升\",\"A0420\":\"001\",\"A0405\":\"01\",\"A0440\":\"2\"},{\"A0415\":\"2011-9-1\",\"A0430\":\"2015-7-1\",\"A0435\":\"深圳大学\",\"A0410\":\"修仙飞升\",\"A0420\":\"001\",\"A0405\":\"01\",\"A0440\":\"2\"}],\"requestContext\":[{\"E0127\":\"12345\",\"A01AH\":\"2017-11-20\",\"A0101\":\"张三\",\"A0107\":\"M\",\"A0199\":\"1000070D\",\"E0122\":\"C42AEDF1DEC1457682794DA8729686B9\",\"A0127\":\"001\",\"A0111\":\"1995-11-29\",\"A0121\":\"01\",\"A01AX\":\"01\",\"A010A\":\"180\",\"A010B\":\"75\",\"A0114\":\"广东深圳\",\"B0110\":\"E123AF2625A24DAEBEF94833C5B23CF3\",\"E0122\":\"C42AEDF1DEC1457682794DA8729686B9\",\"E01A1\":\"69EAA8076C884E15B027A62B083EFF01\",\"A01AP\":\"01\",\"A0124\":\"1\",\"A0177\":\"612501199511297310\",\"C0104\":\"123456\",\"A0171\":\"广东\",\"A0141\":\"2017-11-30\",\"A010C\":\"广东\",\"A0182\":\"2017-01-01\",\"A01AI\":\"广东深圳\",\"A01AL\":\"广东深圳\",\"A0184\":\"李四\",\"A010D\":\"1998-11-29\",\"A010E\":\"03\",\"A010F\":\"广西\",\"A010G\":\"美容\",\"A0194\":\"678908764\"}]}";
//		requestBody="{\"chengyuan\":[{\"A7905\":\"李四\",\"A7910\":\"001\",\"A79AG\":\"23\",\"A7920\":\"美容\",\"A79AB\":\"4321234\"},{\"A7905\":\"李四wer\",\"A7910\":\"001\",\"A79AG\":\"23\",\"A7920\":\"美we容\",\"A79AB\":\"4321234\"}],\"work\":[{\"A1905\":\"2016-7-8\",\"A1910\":\"2017-8-8\",\"A1915\":\"花花\",\"A190D\":\"人事部\",\"A1920\":\"经理\",\"A19AE\":\"张三\",\"A19AF\":\"9876543\"},{\"A1905\":\"2016-7-8\",\"A1910\":\"2017-8-8\",\"A1915\":\"花花\",\"A190D\":\"人事eee\",\"A1920\":\"经理\",\"A19AE\":\"张三\",\"A19AF\":\"9876543\"}],\"TheContract\":[{\"AZ302\":\"24\",\"AZ301\":\"001\",\"AZ303\":\"2017-11-20\",\"AZ304\":\"2019-11-20\"},{\"AZ302\":\"24\",\"AZ301\":\"001\",\"AZ303\":\"2017-11-20\",\"AZ304\":\"2019-11-20\"}],\"positive\":[{\"A14AB\":\"1245\",\"A14AA\":\"1234\",\"C1401\":\"3\"},{\"A14AB\":\"1245\",\"A14AA\":\"1234\",\"C1401\":\"3\"}],\"Education\":[{\"A0415\":\"2011-9-1\",\"A0430\":\"2015-7-1\",\"A0435\":\"深圳大学\",\"A0410\":\"修仙飞升\",\"A0420\":\"001\",\"A0405\":\"01\",\"A0440\":\"2\"},{\"A0415\":\"2011-9-1\",\"A0430\":\"2015-7-1\",\"A0435\":\"深圳大学\",\"A0410\":\"修仙飞升\",\"A0420\":\"001\",\"A0405\":\"01\",\"A0440\":\"2\"}],\"requestContext\":[{\"E0127\":\"12345\",\"A0101\":\"张三\",\"A0107\":\"M\",\"A0199\":\"1000070D\",\"E0122\":\"C42AEDF1DEC1457682794DA8729686B9\",\"A0127\":\"001\",\"A0121\":\"01\",\"A01AX\":\"01\",\"A010A\":\"180\",\"A010B\":\"75\",\"A0114\":\"广东深圳\",\"B0110\":\"E123AF2625A24DAEBEF94833C5B23CF3\",\"E0122\":\"C42AEDF1DEC1457682794DA8729686B9\",\"E01A1\":\"69EAA8076C884E15B027A62B083EFF01\",\"A01AP\":\"01\",\"A0124\":\"1\",\"A0177\":\"612501199511297310\",\"C0104\":\"123456\",\"A0171\":\"广东\",\"A010C\":\"广东\",\"A01AI\":\"广东深圳\",\"A01AL\":\"广东深圳\",\"A0184\":\"李四\",\"A010E\":\"03\",\"A010F\":\"广西\",\"A010G\":\"美容\",\"A0194\":\"678908764\"}]}";
		System.out.println(requestBody+" 入职");
		WriteIOstream.GetwritetoJson("入职信息",requestBody);
		Map<String,Object> person = JsonUtil.json2Map(requestBody);
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> list = (List<Map<String, Object>>)person.get("requestContext");  //A01
		List<Map<String,Object>> list2 = (List<Map<String, Object>>)person.get("chengyuan");           //AZX
		List<Map<String,Object>> list3 = (List<Map<String, Object>>)person.get("positive");
		List<Map<String,Object>> list4 = (List<Map<String, Object>>)person.get("TheContract");
		List<Map<String,Object>> list5 = (List<Map<String, Object>>)person.get("Education");
		List<Map<String,Object>> list6 = (List<Map<String, Object>>)person.get("work");
//		Map<String, Object> B0110Map = baseDao.Queryzzjg(list.get(0).get("B0110").toString());
//		Map<String, Object> E0122Map = baseDao.Queryzzjg(list.get(0).get("E0122").toString());
//		Map<String, Object> E01A1Map = baseDao.Queryzzjg(list.get(0).get("E01A1").toString());
//		String A0000 = baseDao.QueryA0000();
//		String A0100 =baseDao.QUERYMAXA0100();
//		list.get(0).put("A0000", A0100);
//		list.get(0).put("A0100", A0100);
//		list.get(0).put("B0110", B0110Map.get("CODEITEMID"));
//		list.get(0).put("E0122", E0122Map.get("CODEITEMID"));
//		list.get(0).put("E01A1", E01A1Map.get("CODEITEMID"));
		Map<String, Object> person2 = new HashMap<>();
//		baseDao.insertBatch("UsrA01",list);
//		baseDao.insertBatchReturnRows("UsrA01",list);
		String filterFields = "";
		String fields2 = "";
		String values2 = "";
		String str4 ="";
		String str5 ="";
		String sql="";
		String Values="";
		String date = "";
		String datetime;
		String keySet;
		String keyValues;
		String ww = null;
		Set<String> set = new HashSet<>();
		for (int i = 0; i < list6.size(); i++) {
				for (String key : list6.get(i).keySet()) {
					if (filterFields.indexOf(key) > -1 
							|| list6.get(i).get(key) == null
							|| "".equals(list6.get(i).get(key))
							|| "null".equals(list6.get(i).get(key))
							){
						continue;
					}
					boolean str = list6.get(i).get(key).toString().indexOf("-")!=-1;
					int str2 = list6.get(i).get(key).toString().length();
					boolean str3 = list6.get(i).get(key).toString().indexOf(":")!=-1;
					if((str && str2 <=10) && str2>=8){
						str4 = "to_date('"+list6.get(i).get(key)+"','yyyy-mm-dd')";
						person2.put("DATE", str4);
					}
					if((str3 && str) && str2 >10){
						str5 = "to_date('"+list6.get(i).get(key)+"','yyyy-mm-dd hh24:mi:ss')";
						person2.put("DATETIME", str5);
					}
					if(person2.get("DATE")!=null){
						if(person2.get("DATE").toString().indexOf(list6.get(i).get(key).toString()) != -1 && str ){
							date = list6.get(i).get(key).toString();
						}
					}
					if(person2.get("DATETIME")!=null){
						if( person2.get("DATETIME").toString().indexOf(list6.get(i).get(key).toString())!=-1 && str && str3){
							datetime = list6.get(i).get(key).toString();
						}
					}
					keySet = list6.get(0).keySet().toString();
					sql = keySet.replace("[", "").replace("]", "");
					for (Object map : list6.get(i).values()) {
						
					}
					keyValues = "'"+list6.get(i).values().toString()+"'";
					Values = keyValues.replace("[", "").replace("]", "");
					System.out.println(keyValues);
//					if(date != null && list6.get(i).get(key).equals(date)){
//						keyValues.replace(list6.get(i).values().toString(), person2.get("DATE").toString());
//					}else{
//						ww = "'"+Values+"'";
//					}
				}
				String sql_2 = "INSERT INTO UsrA01("+sql+") VALUES("+Values+")" ;
			System.out.println(sql_2);
		}
	}
	public static void main(String[] args) {
		ss ff = new ss();
		ff.writePersonel("");
	}
}
