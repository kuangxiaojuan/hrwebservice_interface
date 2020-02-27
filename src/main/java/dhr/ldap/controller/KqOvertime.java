package dhr.ldap.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import dhr.provider.BaseDao;
import dhr.provider.SqlMapper;
import dhr.utils.WriteIOstream;

@Component
@Controller
public class KqOvertime {
	
	public BaseDao baseDao;

	public BaseDao getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
	public SqlMapper SqlMapper;

	public SqlMapper getSqlMapper() {
		return SqlMapper;
	}

	public void setSqlMapper(SqlMapper sqlMapper) {
		SqlMapper = sqlMapper;
	}
	
	
//	@Scheduled(cron = "0/5 * * * * ?")
	@RequestMapping(value = "performance", method = RequestMethod.GET)
	public @ResponseBody String kq_Overtime() {
		System.out.println(123);
		List<Map<String,Object>> list = baseDao.selectOne("select kq.* from overtime kq ,Usra01 person where kq.e0127 = person.e0127",Map.class);
		synchronized (this) {
			String sql="";
			String values="";
			Map<String,Object> map4 = new HashMap<>();
			try{
				if(list!=null || list.size()>0){
					for (Map<String, Object> map : list) {
						System.out.println(map.get("E0127").toString());
						Map<String,Object> map3 =baseDao.queryUsrA01(map.get("E0127").toString());
						map4.put("Q1103",map.get("Q1103"));
						map4.put("Q11Z0","01");
						map4.put("Q11Z5","03");
						map4.put("Q11AA",map.get("Q11AA"));
//					to_date('"+Q13Z1+"','yyyy-mm-dd hh24:mi:ss')
						map4.put("Q11Z1","to_date('"+map.get("Q11Z1")+"','yyyy-mm-dd hh24:mi:ss')");
						map4.put("Q11Z3","to_date('"+map.get("Q11Z3")+"','yyyy-mm-dd hh24:mi:ss')");
						map4.put("Q11AA",map.get("Q11AA"));
						map4.put("Q1107",map.get("Q1107"));
						map4.put("E0127",map.get("E0127"));
						map4.put("Q1101",baseDao.tableName_01("Q11").toString());
//					map4.put("nbase", "Usr");
						map4.put("A0100",map3.get("A0100"));
						map4.put("E0122",map3.get("E0122"));
						map4.put("E01A1",map3.get("E01A1"));
						map4.put("A0101",map3.get("A0101"));
						map4.put("B0110",map3.get("B0110"));
						if(baseDao.countQ11("Q11",map4.get("A0100").toString(), map4.get("Q11Z1").toString(), map4.get("Q11Z3").toString()) == 0){
							baseDao.excute("Insert into Q11(nbase,Q11AA,E01A1,B0110,Q11Z0,Q1107,Q11Z5,A0101,Q1103,A0100,Q1101,E0122,Q11Z1,Q11Z3) "
									+ "values('Usr','"+map4.get("Q11AA")+"','"+map4.get("E01A1")+"','"+map4.get("B0110")+"','"+map4.get("Q11Z0")+"',"
									+ "'"+map4.get("Q1107")+"','"+map4.get("Q11Z5")+"','"+map4.get("A0101")+"','"+map4.get("Q1103")+"','"+map4.get("A0100")+"',"
									+ "'"+map4.get("Q1101")+"','"+map4.get("E0122")+"',to_date('"+map.get("Q11Z1")+"','yyyy-mm-dd hh24:mi:ss'),"
									+ "to_date('"+map.get("Q11Z3")+"','yyyy-mm-dd hh24:mi:ss'))");
						}
					}
				}
			}catch(Exception e){
				String str  = e.getMessage();
				WriteIOstream.GetMessage("获取新入职人员的加班信息", "异常信息是: " + str);
				return  "{\"result\":\"false\",\"message\":\"失败"+e.getMessage()+"\"}";
			}
		}
		return null;
	}
}
