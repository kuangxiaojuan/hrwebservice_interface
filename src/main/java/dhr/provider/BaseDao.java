package dhr.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import dhr.provider.vo.InsertVO;
import dhr.provider.vo.SelectVO;
import dhr.provider.vo.UpdateVO;
import dhr.utils.ResultMessage;
import dhr.utils.UUIDUtil;


@Repository
public class BaseDao {

	private Configuration configuration;
	private LanguageDriver languageDriver;

	@Resource
	private SqlMapper sqlMapper;

	@Resource
	private SqlSession sqlSession;
	private String tableName;

	@SuppressWarnings("deprecation")
	@PostConstruct
	public void init() {
		configuration = sqlSession.getConfiguration();
		languageDriver = configuration.getDefaultScriptingLanuageInstance();
	}
	
	/**
	 * <p>Title: excute()</p>
	 * <p>Description: 插入一条sql语句</p>
	 * @return
	 */
	public Integer excute(String sql) {
		return sqlMapper.excute(new InsertVO(sql));
	}
	
	/**
	 * <p>Title: insert()</p>
	 * <p>Description: 插入一行数据</p>
	 * @return
	 */
	public Integer insert(String tableName, Map<String, Object> values) {
		return sqlMapper.insert(new InsertVO(tableName,values));
	}
	
	/**
	 * <p>Title: insertBatch()</p>
	 * <p>Description: 插入多行数据</p>
	 * @return
	 */
	public Integer insertBatch(String tableName, List<Map<String, Object>> listValues) {
		return sqlMapper.insertBatch(new InsertVO(tableName,listValues));
	}
	
	/**
	 * <p>Title: insertBatch()</p>
	 * <p>Description: 插入多行数据</p>
	 * @return
	 */
	public Integer insertBatchReturnRows(String tableName, List<Map<String, Object>> listValues) {
		return sqlMapper.insertBatchReturnRows(new InsertVO(tableName,listValues));
	}
	
	
	/**
	 * <p>Title: insertBatch()</p>
	 * <p>Description: 插入多行数据，并返回插入后的行</p>
	 * @return
	 */
	public List<Map<String, Object>> insertBatchReturnRows(String tableName,String key, List<Map<String, Object>> listValues) {
		List<Map<String, Object>> newValues = new ArrayList<>();
		for (Map<String, Object> map : listValues) {
			map.put(key, UUIDUtil.getUUID32());
			newValues.add(map);
		}
		if (sqlMapper.insertBatchReturnRows(new InsertVO(tableName,key,newValues)) > 0) {
			return newValues;
		}
		return null;
		
	}
	
	/**
	 * <p>Title: insertBatch()</p>
	 * <p>Description: 插入多行数据</p>
	 * @return
	 */
	public Integer insertBatch(String tableName,String key, List<Map<String, Object>> listValues) {
		return sqlMapper.insertBatch(new InsertVO(tableName,key,listValues));
	}
	
	/**
	 * <p>Title: update()</p>
	 * <p>Description: 更新一行数据</p>
	 * @return
	 */
	public Integer update(String tableName,String updateKey,Map<String, Object> values) {
		return sqlMapper.update(new UpdateVO(tableName,updateKey,values));
	}
	
	/**
	 * <p>Title: update()</p>
	 * <p>Description: 更新一行数据，多条件</p>
	 * @return
	 */
	public Integer update(String tableName,String[] updateKeys,Map<String, Object> values) {
		return sqlMapper.updateByConds(new UpdateVO(tableName,updateKeys,values));
	}
	
	/**
	 * <p>Title: update()</p>
	 * <p>Description: 删除一行数据</p>
	 * @return
	 */
	public Integer delete(String tableName,String updateKey,Map<String, Object> values) {
		return sqlMapper.delete(new UpdateVO(tableName,updateKey,values));
	}
	
	/**
	 * <p>Title: updateBatch()</p>
	 * <p>Description: 更新多行数据</p>
	 * @return
	 */
	public Integer updateBatch(String tableName,String updateKey,List<Map<String, Object>> listValues) {
		return sqlMapper.updateBatch(new UpdateVO(tableName,updateKey,listValues));
	}
	
	/**
	 * <p>Title: deleteBatch()</p>
	 * <p>Description: 删除多行数据</p>
	 * @return
	 */
	public Integer deleteBatch(String tableName,String updateKey,List<Map<String, Object>> listValues) {
		return sqlMapper.deleteBatch(new UpdateVO(tableName,updateKey,listValues));
	}

	/**
	 * <p>Title: selectList()</p>
	 * <p>Description: 查询数据，返回一个泛型集合（无参数类型）</p>
	 */
	public <T> List<T> selectList(String sql, Class<?> resultType) {
		String msId = selectStatic(sql, resultType);
		return sqlSession.selectList(msId);
	}
	
	/**
	 * <p>Title: selectList()</p>
	 * <p>Description: 查询数据，返回一个泛型集合（有参数类型）</p>
	 */
	public <T> List<T> selectList(String sql, Class<?> resultType,Object params) {
		Class<?> parameterType = params != null ? params.getClass() : null;
		String msId = selectDynamic(sql, parameterType, resultType , params);
		return sqlSession.selectList(msId, params);

	}
	
	/**
	 * <p>Title: getInfo</p>
	 * <p>Description: 查询一行，无参数</p>
	 * @return
	 */
	public <T> T selectOne(String sql, Class<?> resultType) {
		List<T> list = selectList(sql, resultType);
		return getOne(list);
	}
	
	/**
	 * <p>Title: getInfo</p>
	 * <p>Description: 查询一行，有参数</p>
	 * @return
	 */
	public <T> T selectOne(String sql, Class<?> resultType, Object params) {
		List<T> list = selectList(sql, resultType, params);
		return getOne(list);
	}

	/**
	 * 创建一个查询的MS
	 * @param msId
	 * @param sqlSource
	 * @param resultType
	 */
	private void newSelectMappedStatement(String msId, SqlSource sqlSource, final Class<?> resultType) {
		MappedStatement ms = new MappedStatement.Builder(configuration, msId, sqlSource, SqlCommandType.SELECT)
				.resultMaps(new ArrayList<ResultMap>() {
					private static final long serialVersionUID = 1L;

					{
						add(new ResultMap.Builder(configuration, "defaultResultMap", resultType,
								new ArrayList<ResultMapping>(0)).build());
					}
				}).build();
		// 缓存
		configuration.addMappedStatement(ms);
	}

	@SuppressWarnings("unchecked")
	private String selectDynamic(String sql, Class<?> parameterType, Class<?> resultType,Object params) {
		sql = new SelectVO(sql,(Map<String, Object> )params).getSql();
		//System.out.println(sql);
		String msId = newMsId(sql + parameterType, SqlCommandType.SELECT);
		if (hasMappedStatement(msId)) {
			return msId;
		}
		SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, parameterType);
		newSelectMappedStatement(msId, sqlSource, resultType);
		return msId;
	}

	private String selectStatic(String sql, Class<?> resultType) {
		//System.out.println("msql:"+sql);
		String msId = newMsId(resultType + sql, SqlCommandType.SELECT);
		if (hasMappedStatement(msId)) {
			System.out.println("msid:"+msId);
			return msId;
		}
		StaticSqlSource sqlSource = new StaticSqlSource(configuration, sql);
		newSelectMappedStatement(msId, sqlSource, resultType);
		return msId;
	}

	/** 创建MSID **/
	public String newMsId(String sql, SqlCommandType sqlCommandType) {
		StringBuilder msIdBuilder = new StringBuilder(sqlCommandType.toString());
		msIdBuilder.append(".").append(sql.hashCode());
		return msIdBuilder.toString();
	}

	/**
	 * <p>Title: getOne()</p>
	 * <p>Description: 获取List中最多只有一个的数据</p>
	 */
	private <T> T getOne(List<T> list) {
		if (list.size() == 1) {
			return list.get(0);
		} else if (list.size() > 1) {
			throw new TooManyResultsException(
					"Expected one result (or null) to be returned by selectOne(), but found: " + list.size());
		} else {
			return null;
		}
	}

	/**是否已经存在该ID**/
	private boolean hasMappedStatement(String msId) {
		return configuration.hasStatement(msId, false);
	}
	
	public String queryi9999(String tableName,String A0100) {
		return this.selectOne("select to_char(nvl(max(to_number(i9999)),0)+1) i9999 FROM "+tableName+" WHERE A0100 = '"+A0100+"'",String.class);
	}
	
	public String insertUsr(Map<String, Object> map) {
		return sqlMapper.insertUsr(map);
	}
	
	
	public Map<String, Object> queryUsrA01(String E0127) {
		return this.selectOne("select A0100,C01TC,B0110,A0101,A0127,A0107,C0101,E0122,E01A1,A01AH FROM UsrA01 where E0127='"+E0127+"'",Map.class);
	}
	
	public String queryE0127(String E0127) {
		return this.selectOne("select A0100 from UsrA01 where E0127='"+E0127+"'",String.class);
	}
	
	public String queryUsrA01(String tableName,String E0127) {
		return this.selectOne("select A0100,E0122,E01A1 from usra01 where E0127='"+E0127+"'", Map.class);
	}
	
	public String tableName_01(String tableName) {
		return this.selectOne("select substr('0000000000'||to_char(nvl(max(to_number("+tableName+"01)),0)+1),-10) from "+tableName+"", String.class);
	}
	
//	public String QUERYMAXA0100() {
//		return this.selectOne("select substr('0000000000'||to_char(nvl(max(to_number(A0100)),0)+1),-8) from AppA01", String.class);
//	}
	
	public String QUERYMAXA0100() {
		return this.selectOne("select substr('0000000000'||to_char(nvl(max(to_number(A0100)),0)+1),-8) from UsrA01", String.class);
	}
	
	public Map<String, Object> codeitemdesc(String E0127) {
		return this.selectOne("select (select CODEITEMDESC from organization where codeitemid =a.B0110) B0110,(select CODEITEMDESC from organization where codeitemid =a.E0122) E0122,"
				+ "(select CODEITEMDESC from organization where codeitemid =a.E01A1) E01A1,(select CODEITEMDESC from codeitem where codesetid='ZJ' and codeitemid =a.A010H) A010H,"
				+ "C0181,A0100 from UsrA01 a  where E0127 ='"+E0127+"'", Map.class);
	}
	
	//组织架构转换
	public Map<String, Object> QueryOrg(String E0122,String E01A1) {
		return this.selectOne("select CODEITEMDESC from organization where GUIDKEY ="+E0122+" OR GUIDKEY ="+E01A1+"",Map.class);
	}
	
    //判断数据是否有重复
	public int countQ11(String tableName,String A0100,String Q11Z1,String Q11Z3) {
		return this.selectOne("select count(*) from "+tableName+" where A0100 ="+A0100+" and Q11Z1 ="+Q11Z1+" and Q11Z3 ="+Q11Z3+"",int.class);
	}
	

    //判断数据是否有重复
	public int countQ13(String tableName,String A0100,String Q13Z1,String Q13Z3) {
		return this.selectOne("select count(*) from "+tableName+" where A0100 ="+A0100+" and Q13Z1 ="+Q13Z1+" and Q13Z3 ="+Q13Z3+"",int.class);
	}
	
	//判断数据是否有重复
	public int countkq_originality_data(String tableName,String A0100,String  Work_date,String Work_time) {
		return this.selectOne("select count(*) from "+tableName+" where A0100 ='"+A0100+"' and Work_date ='"+Work_date+"' and Work_time ='"+Work_time+"'",int.class);
	}
	
	//查询假期
	public List<Map<String, Object>> ForLeavetype(String type,String A0100) {
		return this.selectOne("SELECT Q1709 type,Q1707 dayCnt FROM Q17 WHERE A0100 ="+A0100+" AND Q1709 = "+type+"",List.class);
	}
	
	//传主键查询组织架构信息
	public Map<String, Object> Queryzzjg(String zzjg) {
		return this.selectOne("select CODEITEMDESC,CODEITEMID from organization where GUIDKEY='"+zzjg+"'",Map.class);
	}
	
//	//传主键查询组织架构信息
//	public Map<String, Object> QueryJobs(String jobs) {
//		return this.selectOne("select CODEITEMDESC,CODEITEMID from organization where GUIDKEY='"+jobs+"'",Map.class);
//	}

	//传主键查询组织架构信息
	public Map<String, Object> QueryA010H(String A17AI) {
		return this.selectOne("select CODEITEMDESC,CODEITEMID from codeitem where codesetid='ZJ' and codeitemid = '"+A17AI+"'",Map.class);
	}
	//传主键查询组织架构信息
//	public String QueryA0000() {
//		return this.selectOne("select Max(A0000)+1 A0000 from AppA01",String.class);
//	}
	
	public String QueryA0000() {
		return this.selectOne("select Max(A0000)+1 A0000 from usrA01",String.class);
	}
	
	
	//查询入职，转正日期
	public Map<String, Object> getDate(String E0127) {
		//view_personBydate
		return this.selectOne("select * from view_personBydate where E0127= '"+E0127+"'",Map.class);
	}
	
	//APP入职
	public Integer writePersonel(Map <String,Object> map) {
		return sqlMapper.insertWritePersonel(map);
	}
	
	public Integer writePersonelusr(Map <String,Object> map) {
		return sqlMapper.insertwritePersonelusr(map);
	}
	
	
	//APP入职
	public Integer App(Map <String,Object> map,String tableName,String A0100) {
		return sqlMapper.insertAPP(map,tableName,A0100);
	}
	
	//APP入职
		public Integer App2(Map <String,Object> map,String tableName,String A0100) {
			return sqlMapper.insertAPP2(map,tableName,A0100);
		}
	
	//APP主集入职
	public Integer insertUsrList(List<Map <String,Object>> list,String tableName,String A0100) {
		return sqlMapper.insertUsrList(list,tableName,A0100);
	}
	
//	public Integer QUERYApp() {
//		return this.selectOne("select count(*) from AppA01", int.class);
//	}

	public Integer QUERYApp() {
		return this.selectOne("select count(*) from usrA01", int.class);
	}
	
	
//	public int QUERYA0177(String A0177) {
//		return this.selectOne("select count(*) from AppA01 where A0177 ='"+A0177+"'", int.class);
//	}
	
	public int QUERYA0177(String A0177) {
		//select max(num) from (select count(*) num from usrA01 where A0177 ='421022198812240610'
//				union all select count(*) num from retA01 where A0177 ='421022198812240610')t
//		return this.selectOne("select count(*) from usrA01 where A0177 ='"+A0177+"'", Integer.class);
		return this.selectOne("select max(num) from (select count(*) num from usrA01 where A0177 ='"+A0177+"' "
				+ "union all select count(*) num from retA01 where A0177 ='"+A0177+"')t", Integer.class);
	}
	
	public Map<String,Object> Getgeneral(String E0127,String tableName1,String tableName2){
		synchronized (E0127) {
			String A0100 = this.selectOne("select A0100 from UsrA01 where E0127 = '"+E0127+"'",String.class);
			Map<String, Object> map2 = new HashMap<>();
			if(A0100 == null || A0100.equals("") || "null".equals(A0100)){
				map2.put("false", "找不到"+E0127+"对应的人员！");
				return map2;
			}
			Map<String, Object> map1 = new HashMap<>();
			if(A0100 == null || A0100.equals("")){
				map1.put("false", "没有对应的人员工号" +E0127+ "信息,请核实");
				return  map1;
			}
			map1.put("A0100", A0100);
			boolean tableName = tableName2 == "";
			String str =this.selectOne("select to_char(nvl(max(to_number(i9999)),0)+1) i9999 FROM "+tableName1+" WHERE A0100 = "+A0100+"",String.class);
			System.out.println("str"+str);
			if(str == null || "".equals(str) || str.equals("")){
				map1.put("stri9999", "1");
			}else{
				map1.put("stri9999",str);
			}
			if(tableName == false){
				String str2 = this.selectOne("select to_char(nvl(max(to_number(i9999)),0)+1) i9999 FROM "+tableName2+" WHERE A0100 = "+A0100+"",String.class);
				if(str2 == null || "".equals(str2) || str2.equals("")){
					map1.put("str2i9999", "1");
				}else{
					map1.put("str2i9999",str2);
					System.out.println(str2);
				}
			}
			return map1;
		}
	}
	
	public Map<String,Object> GetAttendance(String E0127,String tableName,String AttendanceName){
		Map<String,Object> map  = this.selectOne("select A0100,C01TC,B0110,A0101,A0127,A0199,A0107,C0101,E0122,A01AA,E01A1,A01AH FROM UsrA01 where E0127='"+E0127+"'",Map.class);
		Map<String,Object> map4 = new HashMap<>();
		if(map == null || map.size() == 0){
			map.put("false", "没有对应的人员工号" +map.get("E0127")+ "信息,请核实");
			return map;
		}
		String Q1501 = this.selectOne("select substr('0000000000'||to_char(nvl(max(to_number("+tableName+"01)),0)+1),-10) from "+tableName+"", String.class);
		map4.put("Q1501", Q1501);
		map4.put("nbase", "Usr");
		map4.put("A0100",map.get("A0100"));
		map4.put("E0122",map.get("E0122"));
		map4.put("E01A1",map.get("E01A1"));
		map4.put("A0101",map.get("A0101"));
		map4.put("B0110",map.get("B0110"));
		map4.put(AttendanceName+"Z0", "01");
		map4.put(AttendanceName+"Z5", "03");
		return null;
		
	}

	/**
	 * @param a0195
	 * @return  判断是否存在同名同姓  如果返回值大于等于1  表示有  返回0  表示无
	 */
	public int ifSameNameByUsr(String a0195) {
		return this.selectOne("select COUNT(*) from usrA01 where a0195 = '"+a0195+"'", int.class);
	}
	
	public int ifSameNameByRet(String a0195) {
		return this.selectOne("select COUNT(*) from retA01 where a0195 = '"+a0195+"'", int.class);
	}

	/**
	 * @param string
	 * @return  返回所有拥有域名子串的数据
	 */
	public List<LinkedHashMap<Object, Object>> containsA01(String a0195) {
		// TODO Auto-generated method stub
		return this.selectList("select a0195 from usrA01 where a0195 like '"+a0195+"%'",LinkedHashMap.class);
		
	}

	/**
	 * @param string
	 * @return
	 */
	public List<LinkedHashMap<Object, Object>> containsretA01(String a0195) {
		return this.selectList("select a0195 from retA01 where a0195 like '"+a0195+"%'",LinkedHashMap.class);
	}

	/**
	 * @return  获取当前最大工号加1
	 */
	public String getMaxE0127() {
		return this.selectOne("select lpad((x+y+abs(x-y))/2+1,6,'0') e0127 from "
				+ "(select max(e0127) x from usra01)t,(select max(e0127) y from Reta01)tt",String.class);
	}

	/**
	 * @param codeitemid
	 * @return
	 */
	public String getOU(String codeitemid) {
		return this.selectOne("select wm_concat(codeitemdesc) "
				+ "from (select 'OU='||codeitemdesc codeitemdesc from "
				+ "(select  codeitemid,case when  codeitemid='"+codeitemid+"' "
				+ "then null else parentid end parentid,codeitemdesc from testOUORG) "
				+ "start with o.codeitemid='10000K0204' connect by o.codeitemid =prior o.parentid)",String.class);
	}

	/**
	 * @param string
	 * @return 判断是否在离职库
	 */
	public String inRetA01(String e0127) {		
		return  this.selectOne("select case when exists(select * from RetA01 where e0127='"+e0127+"') "
				+ "then 'true' else 'false' end from dual",String.class);
	}

	/**
	 * @param string
	 * @return 查询离职库里人员信息
	 */
	public Map<String, Object> queryRetA01(String E0127) {
		return this.selectOne("select A0100,C01TC,B0110,A0101,A0127,A0107,C0101,E0122,E01A1,A01AH FROM RetA01 where E0127='"+E0127+"'",Map.class);
	}

	//返回B0110
	public String queryB0110ByE0127(String E0127) {
		return this.selectOne("select B0110 from UsrA01 where E0127='"+E0127+"'",String.class);
	}

	public String queryE0122ByE0127(String E0127) {
		return this.selectOne("select E0122 from UsrA01 where E0127='"+E0127+"'",String.class);
	}

	public String queryE01a1ByE0127(String E0127) {
		return this.selectOne("select E01a1 from UsrA01 where E0127='"+E0127+"'",String.class);
	}

	public String queryA010HByE0127(String E0127) {
		return this.selectOne("select A010H from UsrA01 where E0127='"+E0127+"'",String.class);
	}

	public String queryC0181ByE0127(String E0127) {
		return this.selectOne("select C0181 from UsrA01 where E0127='"+E0127+"'",String.class);
	}

	public String queryMaxI9999(String tableName, String A0100) {
		return this.selectOne("select to_char(nvl(max(to_number(i9999)),0)) i9999 FROM "+tableName+" WHERE A0100 = "+A0100+"",String.class);
	}

	public String getCodeitemidByGuidkey(String guidkey) {
		return this.selectOne("select codeitemid from organization where guidkey ='"+guidkey+"'",String.class);
	}

	public String ifA0100ExistA22(String A0100,String A22Z0) {
		return this.selectOne("select case when exists "
				+ "(select * from usra22 where A0100='"+A0100+"' and A22Z0=to_date('"+A22Z0+"','yy-MM-dd')) "
				+ "then 'true' else 'false' end from dual ",String.class);
	}

	public String queryParentid(String codeitemid) {
		return this.selectOne("select parentid from organization  where codeitemid='"+codeitemid+"'",String.class);
	}

	public String getCodeSetId(String codeitemid) {
		return this.selectOne("select codesetid from organization  where codeitemid='"+codeitemid+"'",String.class);
	}
}