package dhr.provider;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.ibatis.jdbc.SQL;

/*import com.sun.tools.javac.resources.compiler;*/

import dhr.provider.vo.InsertVO;
import dhr.provider.vo.UpdateVO;
import dhr.utils.ResultMessage;
import dhr.utils.WriteIOstream;

public class SqlMapperProvider {
	
	
	/**
	 * <p>Title: 执行sql语句</p>
	 * <p>Description: 单行插入</p>
	 * @param vo
	 * @return
	 */
	public String excute(final InsertVO vo) {
		return vo.getSql();
	}
	
	/**
	 * <p>Title: 单行插入</p>
	 * <p>Description: 单行插入</p>
	 * @param vo
	 * @return
	 */
	public String insert(final InsertVO vo) {
		return new SQL() {
			{
				INSERT_INTO(vo.getTableName());
				for (String key : vo.getObj().keySet()) {
					if (vo.getObj().get(key) == null) {
						continue;
					}
					System.out.println(key+"  "+"#{obj." + key + "}"  +" vo.getObj().keySet() ");
					VALUES(key, "#{obj." + key + "}");
				}
			}
		}.toString();
	}
	
	/**
	 * <p>Title: 动态单行插入</p>
	 * <p>Description: 单行插入</p>
	 * @param vo
	 * @return
	 */
	public String insertDynamic(final InsertVO vo) {
		return new SQL() {
			{
				INSERT_INTO(vo.getTableName());
				for (String key : vo.getObj().keySet()) {
					VALUES(key, "#{obj." + key + "}");
				}
			}
		}.toString();
	}
	
	/**
	 * <p>Title: 单行更新</p>
	 * <p>Description: 单行更新</p>
	 * @param vo
	 * @return
	 */
	public String update(final UpdateVO vo) {
		return new SQL() {
			{
				UPDATE(vo.getTableName());
				for (String key : vo.getObj().keySet()) {
					if (key.equals(vo.getTableKey())) {
						continue;
					}
					if (key.equals("PERSONELTYPE")){
						SET("TYPE = #{obj." + key + "}");
					}else{
						SET(key + " = #{obj." + key + "}");
					}
					
				}
				WHERE(vo.getTableKey() + " = #{obj." + vo.getTableKey() + "}");
			}
		}.toString();
	}
	
	/**
	 * <p>Title: 单行更新</p>
	 * <p>Description: 单行更新</p>
	 * @param vo
	 * @return
	 */
	public String updateByConds(final UpdateVO vo) {
		String sql = new SQL() {
			{
				StringBuffer sb = new StringBuffer();
				for(int i = 0; i < vo.getTableKeys().length; i++){
				 sb. append(vo.getTableKeys()[i]);
				}
				UPDATE(vo.getTableName());
				for (String key : vo.getObj().keySet()) {
					if (sb.indexOf(key)>-1) {
						continue;
					}
					if (key.equals("PERSONELTYPE")){
						SET("TYPE = #{obj." + key + "}");
					}else{
						SET(key + " = #{obj." + key + "}");
					}
					
				}
				for (String key : vo.getTableKeys()) {
					WHERE(key + " = #{obj." + key + "}");
				}
				
			}
		}.toString();
		
		
		return new SQL() {
			{
				StringBuffer sb = new StringBuffer();
				for(int i = 0; i < vo.getTableKeys().length; i++){
				 sb. append(vo.getTableKeys()[i]);
				}
				UPDATE(vo.getTableName());
				for (String key : vo.getObj().keySet()) {
					if (sb.indexOf(key)>-1) {
						continue;
					}
					if (key.equals("PERSONELTYPE")){
						SET("TYPE = #{obj." + key + "}");
					}else{
						SET(key + " = #{obj." + key + "}");
					}
					
				}
				for (String key : vo.getTableKeys()) {
					WHERE(key + " = #{obj." + key + "}");
				}
				
			}
		}.toString();
	}
	
	/**
	 * <p>Title: 单行删除</p>
	 * <p>Description: 单行更新</p>
	 * @param vo
	 * @return
	 */
	public String delete(final UpdateVO vo) {
		return new SQL() {
			{
				DELETE_FROM(vo.getTableName());
				WHERE(vo.getTableKey() + " = #{obj." + vo.getTableKey() + "}");
			}
		}.toString();
	}
	
	/**
	 * <p>Title: 批量插入</p>
	 * <p>Description: 批量插入</p>
	 * @param vo
	 * @return
	 */
	public String insertBatchStatic(InsertVO vo) {

		List<Map<String, Object>> list = vo.getList();
		Set<String> set = new HashSet<>();
		for (Map<String, Object> map : list) {
			for (String key : map.keySet()) {
				if (key == null || key.equals("_state") || key.equals("_id") || key.equals("_uid") || key.equals("UUID")) {
					continue;
				}
				set.add(key);
			}
			
		}
		
		String primaryKey = "";
		if (vo.getKey() == null) {
			primaryKey = "UUID";
			System.out.println( primaryKey);
		}else{
			primaryKey = vo.getKey();
		}
		StringBuilder insertBuilder = new StringBuilder("INSERT INTO " + vo.getTableName() + " (primaryKey,");
		for (Iterator<String> it = set.iterator(); it.hasNext();) {
			String value = it.next();
			System.out.println(insertBuilder);
		}

		insertBuilder.setLength(insertBuilder.length() - 1);
		insertBuilder.append(") VALUES  ");


		String sql = insertBuilder.toString();

		StringBuilder valueBuilder = new StringBuilder();

		for (int i = 0; i < list.size(); i++) {
//			String uuid = UUID.randomUUID().toString().replace("-", "");
			valueBuilder.append("(");
//			valueBuilder.append("'" + uuid + "',");

			for (Iterator<String> it = set.iterator(); it.hasNext();) {
				String value = it.next();
//				boolean str = list.get(i).values().toString().indexOf("-")!=-1;
//				int str2 = list.get(i).values().toString().length();
//				boolean str3 = list.get(i).values().toString().indexOf(":")!=-1;
//				if(str && str2 <=10 && str2>=8){
//					value = "to_date('"+list.get(i).values()+"','yyyy-mm-dd')";
//				}
//				if(str3 && str && ){
//					
//				}
				valueBuilder.append("#{list[" + i + "]." + value + "},");
			}
			valueBuilder.setLength(valueBuilder.length() - 1);

			valueBuilder.append("),");
		}
		valueBuilder.setLength(valueBuilder.length() - 1);
		sql = sql + valueBuilder.toString();
//		System.out.println(sql);
		return sql;

	}
	
	/**
	 * <p>Title: 批量插入</p>
	 * <p>Description: 批量插入</p>
	 * @param vo
	 * @return
	 */
	public String insertBatchDynamic(InsertVO vo) {
		List<Map<String, Object>> list = vo.getList();

		Set<String> set = new HashSet<>();
		for (Map<String, Object> map : list) {
			for (String key : map.keySet()) {
				if (key == null || key.equals("_state") || key.equals("_id") || key.equals("_uid")) {
					continue;
				}
				set.add(key);
			}
			
		}
		StringBuilder insertBuilder = new StringBuilder("INSERT INTO " + vo.getTableName() + " (");
		for (Iterator<String> it = set.iterator(); it.hasNext();) {
			String value = it.next();
			insertBuilder.append(value + ",");
		}

		insertBuilder.setLength(insertBuilder.length() - 1);
		insertBuilder.append(") VALUES  ");


		String sql = insertBuilder.toString();

		StringBuilder valueBuilder = new StringBuilder();

		for (int i = 0; i < list.size(); i++) {
			valueBuilder.append("(");
			for (Iterator<String> it = set.iterator(); it.hasNext();) {
				String value = it.next();
				boolean str = list.get(i).get(value).toString().indexOf("-")!=-1;
				int str2 = list.get(i).get(value).toString().length();
				boolean str3 = list.get(i).get(value).toString().indexOf(":")!=-1;
				if((str && str2 <=10) && str2>=8){
					String str4 = "to_date('"+list.get(i).get(value)+"','yyyy-mm-dd')";
					list.get(i).put(value, str4);
				}
				if((str3 && str) && str2 >10){
					String str5 = "to_date('"+list.get(i).get(value)+"','yyyy-mm-dd hh24:mi:ss')";
					list.get(i).put(value, str5);
				}
				
				valueBuilder.append("#{list[" + i + "]." + value + "},");
			}
			valueBuilder.setLength(valueBuilder.length() - 1);

			valueBuilder.append("),");
		}
		valueBuilder.setLength(valueBuilder.length() - 1);
		sql = sql + valueBuilder.toString();
		return sql;

	}
	
	/**
	 * <p>Title: 批量更新</p>
	 * <p>Description: 批量更新</p>
	 * @param vo
	 * @return
	 */
	public String updateBatch(final UpdateVO vo) {
		StringBuilder sqlBulider = new StringBuilder();
		List<Map<String, Object>> list = vo.getList();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> params = list.get(i);
			sqlBulider.append("UPDATE " + vo.getTableName() + " SET " );
			for (String key : params.keySet()) {
				if (key.equals(vo.getTableKey()) || key.equals("B0110_NAME") || key.equals("E01A1_NAME") || key.equals("E0122_NAME") || key == null || key.equals("_state") || key.equals("_id") || key.equals("_uid") || key.equals("UUID")) {
					continue;
				}
				
				sqlBulider.append(key + "= #{list[" + i + "]." + key + "},");
			}
			sqlBulider.setLength(sqlBulider.length() - 1);
			sqlBulider.append(" WHERE " + vo.getTableKey() + "= '"+params.get(vo.getTableKey())+"';");
		}
		//System.out.println( sqlBulider.toString());
		return "BEGIN "+sqlBulider.toString() + "END;";
	}
	
	/**
	 * <p>Title: 批量删除</p>
	 * <p>Description: 批量更新</p>
	 * @param vo
	 * @return
	 */
	public String deleteBatch(final UpdateVO vo) {
		StringBuilder sqlBulider = new StringBuilder();
		List<Map<String, Object>> list = vo.getList();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> params = list.get(i);
			sqlBulider.append("DELETE FROM " + vo.getTableName() + " ");
			sqlBulider.append("WHERE " + vo.getTableKey() + "= '"+params.get(vo.getTableKey())+"';");
		}
		
		return sqlBulider.toString();
	}
	
	
	/*
	 * 入职A01
	 */
	@SuppressWarnings("unchecked")
	public String writePersonel(Map<String, Object> personMap){
		Map<String, Object> map = (Map<String, Object>) personMap.get("personMap");
//		System.out.println(map);
		//移除不需要插入的字段
		map.remove("AZ302");
		map.remove("AZ301");
		map.remove("AZ303");
		map.remove("AZ304");
//		map.remove("A14AA");
//		map.remove("A14AB");
		map.remove("education");
		map.remove("chengyuan");
		map.remove("work");
		map.remove("A10AA");
		map.remove("C1401");
		map.remove("A5551");
		map.remove("A5540");
		map.remove("E5501");
		map.remove("E5504");
		map.remove("E5507");
		map.remove("C1405");
		map.remove("E1405");
		map.remove("A17AJ");
		
		String fields2 = "";
		String values2 = "";
		String str4 ="";
		String str5 ="";
		String sql="";
		String values="";
			for (String key : map.keySet()) {
				int i = 0;
				String filterFields = "chengyuan,education,work,AZ302,AZ301,AZ303,AZ304,C1401";
				if (filterFields.indexOf(key) > -1 
						|| map.get(key) == null
						|| "".equals(map.get(key))
						|| "null".equals(map.get(key))
						){
					continue;
				}
				boolean str = map.get(key).toString().indexOf("-")!=-1;
				int str2 = map.get(key).toString().length();
				boolean str3 = map.get(key).toString().indexOf(":")!=-1;
				if((str && str2 <=10) && str2>=8){
					str4 = "to_date('"+map.get(key)+"','yyyy-mm-dd')";
					map.put(key, str4);
					
				}
				if((str3 && str) && str2 >10){
					str5 = "to_date('"+map.get(key)+"','yyyy-mm-dd hh24:mi:ss')";
					map.put(key, str5);
				}
			}
			for (String key : map.keySet()) {
				int i = 0;
				String filterFields = "chengyuan,positive,TheContract,Education,work";
				if (filterFields.indexOf(key) > -1 
						|| map.get(key) == null
						|| "".equals(map.get(key))
						|| "null".equals(map.get(key))
						){
					continue;
				}
				if(map.get(key).toString().indexOf("to_date(")!= -1){
					fields2 = fields2 + key + ",";
					values2 = values2 +""+map.get(key)+",";
				}else{
					fields2 = fields2 + key + ",";
					values2 = values2 +"'"+map.get(key)+"',";
				}
			}
			fields2 = fields2.substring(0, fields2.length()-1);
			values2 = values2.substring(0, values2.length()-1);
			String sql_2 = "INSERT INTO APPA01("+fields2+") VALUES("+values2+")" ;
			System.out.println(sql_2);
			return sql_2;
	}
	
	
	/*
	 * 入职AZ3 和A14
	 */
	@SuppressWarnings("unchecked")
	public String AppAZ3(Map<String, Object> personMap){
		Map<String, Object> map = (Map<String, Object>) personMap.get("personMap");
		System.out.println(map);
		String fields2 = "";
		String values2 = "";
		String str4 ="";
		String str5 ="";
		String sql="";
		String values="";
			for (String key : map.keySet()) {
				int i = 0;
				String filterFields = "chengyuan,positive,TheContract,Education,work";
				if (filterFields.indexOf(key) > -1 
						|| map.get(key) == null
						|| "".equals(map.get(key))
						|| "null".equals(map.get(key))
						){
					continue;
				}
				boolean str = map.get(key).toString().indexOf("-")!=-1;
				int str2 = map.get(key).toString().length();
				boolean str3 = map.get(key).toString().indexOf(":")!=-1;
				if((str && str2 <=10) && str2>=8){
					str4 = "to_date('"+map.get(key)+"','yyyy-mm-dd')";
					map.put(key, str4);
					
				}
				if((str3 && str) && str2 >10){
					str5 = "to_date('"+map.get(key)+"','yyyy-mm-dd hh24:mi:ss')";
					map.put(key, str5);
				}
			}
			for (String key : map.keySet()) {
				int i = 0;
				String filterFields = "chengyuan,positive,TheContract,Education,work";
				if (filterFields.indexOf(key) > -1 
						|| map.get(key) == null
						|| "".equals(map.get(key))
						|| "null".equals(map.get(key))
						){
					continue;
				}
				if(map.get(key).toString().indexOf("to_date(")!= -1){
					fields2 = fields2 + key + ",";
					values2 = values2 +""+map.get(key)+",";
				}else{
					fields2 = fields2 + key + ",";
					values2 = values2 +"'"+map.get(key)+"',";
				}
			}
//			fields2 = fields2.substring(0, fields2.length()-1);
//			values2 = values2.substring(0, values2.length()-1);
			fields2 = fields2+"A0100,I9999";
			values2 = values2+"'"+personMap.get("A0100").toString()+"',1";
			String sql_2 = "INSERT INTO "+personMap.get("tableName")+"("+fields2+") VALUES("+values2+")" ;
			System.out.println(sql_2);
			return sql_2;
	}
	
	/*
	 * 入职AZ3 和A14
	 */
	@SuppressWarnings("unchecked")
	public String AppAZ3T(Map<String, Object> personMap){
		Map<String, Object> map = (Map<String, Object>) personMap.get("personMap");
		System.out.println(map);
		String fields2 = "";
		String values2 = "";
		String str4 ="";
		String str5 ="";
		String sql="";
		String values="";
			for (String key : map.keySet()) {
				int i = 0;
				String filterFields = "chengyuan,positive,TheContract,Education,work";
				if (filterFields.indexOf(key) > -1 
						|| map.get(key) == null
						|| "".equals(map.get(key))
						|| "null".equals(map.get(key))
						){
					continue;
				}
				boolean str = map.get(key).toString().indexOf("-")!=-1;
				int str2 = map.get(key).toString().length();
				boolean str3 = map.get(key).toString().indexOf(":")!=-1;
				if((str && str2 <=10) && str2>=8){
					str4 = "to_date('"+map.get(key)+"','yyyy-mm-dd')";
					map.put(key, str4);
					
				}
				if((str3 && str) && str2 >10){
					str5 = "to_date('"+map.get(key)+"','yyyy-mm-dd hh24:mi:ss')";
					map.put(key, str5);
				}
			}
			for (String key : map.keySet()) {
				int i = 0;
				String filterFields = "chengyuan,positive,TheContract,Education,work";
				if (filterFields.indexOf(key) > -1 
						|| map.get(key) == null
						|| "".equals(map.get(key))
						|| "null".equals(map.get(key))
						){
					continue;
				}
				if(map.get(key).toString().indexOf("to_date(")!= -1){
					fields2 = fields2 + key + ",";
					values2 = values2 +""+map.get(key)+",";
				}else{
					fields2 = fields2 + key + ",";
					values2 = values2 +"'"+map.get(key)+"',";
				}
			}
//			fields2 = fields2.substring(0, fields2.length()-1);
//			values2 = values2.substring(0, values2.length()-1);
			fields2 = fields2+"A0100";
			values2 = values2+"'"+personMap.get("A0100").toString()+"'";
			String sql_2 = "INSERT INTO "+personMap.get("tableName")+"("+fields2+") VALUES("+values2+")" ;
			System.out.println(sql_2);
			return sql_2;
	}
	
	
	/*
	 * 入职子集
	 */
	
	public String insertUsrList(Map<String, Object> map){
		String sql = "";
		String str4 ="";
		String str5 ="";
		List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("list");
		if(list==null||list.size()<1){
//			return null;
			return "select null";
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
			sqlBuilder.append("'"+map.get("A0100")+"',"+(i+1)+","+str+" from dual union all select ");
		}
        
//		sqlBuilder.setLength(sqlBuilder.length()-17);
		sql = sqlBuilder.toString();
		sql = sqlBuilder.substring(0, sql.lastIndexOf("union"));
		System.out.println(sql);
		return sql;
	}
	
	/*
	 * AA3
	 */
	public String insertUsr(Map<String, Object> personMap){
		List<Map<String, Object>> list = (List<Map<String, Object>>) personMap.get("map");
		System.out.println(list);
		String filterFields = "";
		String fields2 = "";
		String values2 = "";
		for (Map<String, Object> person : list) {
			for (String key : person.keySet()) {
				if (filterFields.indexOf(key) > -1 
						|| person.get(key) == null
						|| "".equals(person.get(key))
						|| "null".equals(person.get(key))
						){
					continue;
				}
				fields2 = fields2 + key + ",";
				values2 = values2 +"'"+person.get(key)+"',";
			}
		}
		fields2 = fields2 + "i9999";
		values2 = values2 + ""+personMap.get("i9999")+"";
		String sql_2 = "INSERT INTO UsrA17("+fields2+") VALUES("+values2+")" ;
		System.out.println(sql_2);
		return sql_2;
	}
	
	public String insetrList(Map<String, Object> personMap){
		List<Map<String, Object>> list = (List<Map<String, Object>>) personMap.get("map");
		System.out.println(list);
		String filterFields = "";
		String fields2 = "";
		String values2 = "";
		for (Map<String, Object> person : list) {
			for (String key : person.keySet()) {
				if (filterFields.indexOf(key) > -1 
						|| person.get(key) == null
						|| "".equals(person.get(key))
						|| "null".equals(person.get(key))
						){
					continue;
				}
				fields2 = fields2 + key + ",";
				values2 = values2 +"'"+person.get(key)+"',";
			}
		}
		fields2 = fields2.substring(0, fields2.length()-1);
		values2 = values2.substring(0, values2.length()-1);
		String sql_2 = "INSERT INTO Q11("+fields2+") VALUES("+values2+")" ;
		System.out.println(sql_2);
		return sql_2;
	}
	
	/*
	 * 入职usrA01
	 */
	@SuppressWarnings("unchecked")
	public String writePersonelusr(Map<String, Object> personMap){
		Map<String, Object> map = (Map<String, Object>) personMap.get("personMap");
//		System.out.println(map);
		//移除不需要插入的字段
		map.remove("AZ302");
		map.remove("AZ301");
		map.remove("AZ303");
		map.remove("AZ304");
//		map.remove("A14AA");
//		map.remove("A14AB");
		map.remove("education");
		map.remove("chengyuan");
		map.remove("work");
		map.remove("A10AA");
		map.remove("C1401");
		map.remove("A5551");
		map.remove("A5540");
		map.remove("E5501");
		map.remove("E5504");
		map.remove("E5507");
		map.remove("C1405");
		map.remove("E1405");
		map.remove("A17AJ");
		map.remove("A5545");
		
		String fields2 = "";
		String values2 = "";
		String str4 ="";
		String str5 ="";
		String sql="";
		String values="";
			for (String key : map.keySet()) {
				int i = 0;
				String filterFields = "chengyuan,education,work,AZ302,AZ301,AZ303,AZ304,C1401";
				if (filterFields.indexOf(key) > -1 
						|| map.get(key) == null
						|| "".equals(map.get(key))
						|| "null".equals(map.get(key))
						){
					continue;
				}
				boolean str = map.get(key).toString().indexOf("-")!=-1;
				int str2 = map.get(key).toString().length();
				boolean str3 = map.get(key).toString().indexOf(":")!=-1;
				if((str && str2 <=10) && str2>=8){
					str4 = "to_date('"+map.get(key)+"','yyyy-mm-dd')";
					map.put(key, str4);
					
				}
				if((str3 && str) && str2 >10){
					str5 = "to_date('"+map.get(key)+"','yyyy-mm-dd hh24:mi:ss')";
					map.put(key, str5);
				}
			}
			for (String key : map.keySet()) {
				int i = 0;
				String filterFields = "chengyuan,positive,TheContract,Education,work";
				if (filterFields.indexOf(key) > -1 
						|| map.get(key) == null
						|| "".equals(map.get(key))
						|| "null".equals(map.get(key))
						){
					continue;
				}
				if(map.get(key).toString().indexOf("to_date(")!= -1){
					fields2 = fields2 + key + ",";
					values2 = values2 +""+map.get(key)+",";
				}else{
					fields2 = fields2 + key + ",";
					values2 = values2 +"'"+map.get(key)+"',";
				}
			}
			fields2 = fields2.substring(0, fields2.length()-1);
			values2 = values2.substring(0, values2.length()-1);
			String sql_2 = "INSERT INTO usrA01("+fields2+") VALUES("+values2+")" ;
			System.out.println(sql_2);
			return sql_2;
	}
	
	
}
