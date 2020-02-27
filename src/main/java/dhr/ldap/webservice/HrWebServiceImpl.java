package dhr.ldap.webservice;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.jws.WebService;
import org.springframework.transaction.annotation.Transactional;
import dhr.provider.BaseDao;
import dhr.provider.SqlMapper;
import dhr.utils.JsonUtil;
import dhr.utils.ResultMessage;
import dhr.utils.WriteIOstream;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.springframework.stereotype.Component;

@WebService
@Component
public class HrWebServiceImpl implements HrWebService {

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

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

	// 入职
	// 添加 域账号，A0195 50 字符型 20180404
	// 单位部门岗位 传入32的guidkey
	@Override
	@SuppressWarnings({ "unchecked", "null" }) // 用于抑制编译器产生警告信息:信息为null的
	@Transactional(rollbackFor = Exception.class)
	public String writePersonel(String requestBody) {
		// requestBody="{\"requestContext\":{\"E0127\":\"001234\",\"A0101\":\"111\",\"A0107\":\"M\",\"B0110\":\"E123AF2625A24DAEBEF94833C5B23CF3\",\"E01A1\":\"0A61ECCC21E243EF99D4F882FA51A693\",\"E0122\":\"9C54E2B828F24734B4F545BBCE2B40D9\",\"A010L\":\"001\",\"A01AH\":\"2018-03-19\",\"A14AB\":\"2000.00\",\"A14AA\":\"3000.00\",\"C1401\":\"\",\"AZ302\":1,\"AZ301\":2,\"AZ303\":\"2018-03-19\",\"AZ304\":\"2018-03-19\",\"A10AA\":\"1\",\"A0127\":\"01\",\"A0111\":\"2018-03-19\",\"A0121\":\"01\",\"A01AX
		// \":\"01\",\"A010A\":\"1\",\"A010B\":\"1\",\"A0114\":\"111\",\"A01AP\":\"01\",\"A0124\":\"01\",\"A0177\":\"1\",\"C0104\":\"1\",\"A0171\":\"1\",\"A0141\":\"2018-03-19\",\"A010C\":\"1\",\"A0182\":\"2018-03-19\",\"A01AI\":\"1\",\"A01AL\":\"1\",\"education\":[{\"A0415\":\"2018-03-19\",\"A0430\":\"2018-03-19\",\"A0435\":\"1\",\"A0410\":\"1\",\"A0420\":\"1\",\"A0405\":\"01\",\"A0440\":\"1\"}],\"work\":[{\"A1905\":\"2018-03-19\",\"A1910\":\"2018-03-19\",\"A1915\":\"1\",\"A1920\":\"1\",\"A19AE\":\"1\",\"A19AF\":\"1\"}],\"chengyuan\":[{\"A7905\":\"1\",\"A7910\":\"1\",\"A79AG\":\"1\",\"A79AB\":\"1\"}],\"A0184\":\"1\",\"A010D\":\"2018-03-19\",\"A010E\":\"01\",\"A010F\":\"1\",\"A010G\":\"1\",\"A0194\":\"1\"}}";
		// +
		// "{\"requestContext\":{\"E0127\":\"12345\",\"A01AH\":\"2017-11-20\",\"A0101\":\"张三\",\"A0107\":\"M\",\"E0122\":\"C42AEDF1DEC1457682794DA8729686B9\",\"A0127\":\"001\",\"A0111\":\"1995-11-29\",\"A0121\":\"01\",\"A01AX\":\"01\",\"A010A\":\"180\",\"A010B\":\"75\",\"A0114\":\"广东深圳\",\"B0110\":\"E123AF2625A24DAEBEF94833C5B23CF3\",\"E0122\":\"C42AEDF1DEC1457682794DA8729686B9\",\"E01A1\":\"69EAA8076C884E15B027A62B083EFF01\",\"A01AP\":\"01\",\"A0124\":\"1\",\"A0177\":\"612501199511297200\",\"C0104\":\"123456\",\"A0171\":\"广东\",\"A0141\":\"2017-11-30\",\"A010C\":\"广东\",\"A0182\":\"2017-01-01\",\"A01AI\":\"广东深圳\",\"A01AL\":\"广东深圳\",\"A0184\":\"李四\",\"A010D\":\"1998-11-29\",\"A010E\":\"03\",\"A010F\":\"广西\",\"A010G\":\"美容\",\"A0194\":\"678908764\",\"chengyuan\":[{\"A7905\":\"李四\",\"A7910\":\"001\",\"A79AG\":\"23\",\"A7920\":\"美容\",\"A79AB\":\"4321234\"},{\"A7905\":\"李四wer\",\"A7910\":\"001\",\"A79AG\":\"23\",\"A7920\":\"美we容\",\"A79AB\":\"4321234\"}],\"work\":[{\"A1905\":\"2016-7-8\",\"A1910\":\"2017-8-8\",\"A1915\":\"花花\",\"A190D\":\"人事部\",\"A1920\":\"经理\",\"A19AE\":\"张三\",\"A19AF\":\"9876543\"},{\"A1905\":\"2016-10-8\",\"A1910\":\"2017-10-08\",\"A1915\":\"花花\",\"A190D\":\"人事eee\",\"A1920\":\"经理\",\"A19AE\":\"张三\",\"A19AF\":\"9876543\"}],\"AZ302\":\"24\",\"AZ301\":\"001\",\"AZ303\":\"2017-11-20\",\"AZ304\":\"2019-11-20\",\"Education\":[{\"A0415\":\"2011-9-1\",\"A0430\":\"2015-7-1\",\"A0435\":\"深圳大学\",\"A0410\":\"修仙飞升\",\"A0420\":\"001\",\"A0405\":\"01\",\"A0440\":\"2\"},{\"A0415\":\"2011-9-1\",\"A0430\":\"2015-7-1\",\"A0435\":\"深圳大学\",\"A0410\":\"修仙飞升\",\"A0420\":\"001\",\"A0405\":\"01\",\"A0440\":\"2\"}]}}";
		// requestBody="{\"requestContext\":{\"E0127\":\"0009578\",\"A0101\":\"唐三\",\"A0107\":\"M\",\"B0110\":\"\",\"E01A1\":\"1622a26d0fe3c4293b6124a41c4b754b\",\"A010L\":\"004\",\"A01AH\":\"2018-03-19\",\"A14AB\":\"\",\"A14AA\":\"\",\"C1401\":\"\",\"AZ301\":\"\",\"A10AA\":\"123\",\"A0127\":\"已婚\",\"A0111\":\"2018-03-19\",\"A0121\":\"01\",\"A01AX
		// \":\"博士\",\"A010A\":\"188\",\"A010B\":\"66\",\"A0114\":\"广东深圳四季\",\"A01AP\":\"共产党员\",\"A0124\":\"健康\",\"A0177\":\"3625212522222222\",\"C0104\":\"18666554455\",\"A0171\":\"1\",\"A0141\":\"2018-03-19\",\"A010C\":\"2\",\"A0182\":\"2018-03-19\",\"A01AI\":\"3\",\"A01AL\":\"1\",\"education\":[{\"A0415\":\"2018-03-19\",\"A0430\":\"2018-03-19\",\"A0435\":\"123\",\"A0410\":\"123\",\"A0420\":\"123\",\"A0405\":\"本科\",\"A0440\":\"12\"}],\"work\":[{\"A1905\":\"2018-03-23\",\"A1910\":\"2018-03-27\",\"A1915\":\"1\",\"A1920\":\"1\",\"A19AE\":\"1\",\"A19AF\":\"1\"}],\"chengyuan\":[{\"A7905\":\"1\",\"A7910\":\"1\",\"A79AG\":\"1\",\"A79AB\":\"1\"}],\"A0184\":\"手动阀\",\"A010D\":\"2018-03-19\",\"A010E\":\"博士\",\"A010F\":\"1\",\"A010G\":\"1\",\"A0194\":\"1\"}}";
		synchronized (this) {// synchronized :同步块
			WriteIOstream.GetwritetoJson("入职信息", requestBody);// requestBody:是相关的数据
			String A0100 = null;
			String A0000 = null;
			try {
				Map<String, Object> person = JsonUtil.json2Map(requestBody);
				Map<String, Object> listA13 = new HashMap<>();
				Map<String, Object> listA14 = new HashMap<>();
				Map<String, Object> listA55 = new HashMap<>();
				Map<String, Object> listA17 = new HashMap<>();
				@SuppressWarnings("unchecked")
				Map<String, Object> map = (Map<String, Object>) person.get("requestContext"); // A01
				map.remove("E1405");
				int cardCnt = baseDao.QUERYA0177(map.get("A0177").toString());
				System.out.println("test:" + cardCnt);
				if (cardCnt != 0) {
					return map.get("A0177").toString() + " 身份证不允许重复入职";
				}
				// 查重 添加判断 如果域名已经存在 就返回对应最大值 确定一下表名，插入哪个表
				// 获取 域账号，A0195 从数据库返回包含A0195子串的数据
				String A0195 = map.get("A0195").toString();
				System.out.println(A0195);
				// 查到有数据 判断最大值 若无数据 直接跳过
				int countSameNameByUsr = baseDao.ifSameNameByUsr(A0195);
				int countSameNameByRet = baseDao.ifSameNameByRet(A0195);
				if (countSameNameByUsr >= 1 || countSameNameByRet >= 1) {
					// 两个表 usra01 和reta01
					List<LinkedHashMap<Object, Object>> A01list = baseDao.containsA01(map.get("A0195").toString());

					List<LinkedHashMap<Object, Object>> retA01list = baseDao
							.containsretA01(map.get("A0195").toString());
					// System.out.println(A01list+"---ret"+retA01list);
					// 循环判断最大值
					int a01max = getA0195EndNum(A01list, 0, A0195);
					int addReta01max = getA0195EndNum(retA01list, a01max, A0195) + 1;
					// 返回域名最大数
					return map.get("A0195").toString() + " 域名不允许重复入职 ,请在域名后添加数字" + addReta01max;
				}

				// 添加年月日时分秒 createTime 添加createUsernName
				// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				// String createTime=
				// sdf.format(Calendar.getInstance().getTime());
				// Calendar c = Calendar.getInstance();

				// map.put("CREATETIME", createTime);
				// String oa = "OA";
				// map.put("CREATEUSERNAME", oa);

				// 添加工号
				// 获取到对应的工号的最大值+1
				String e0127 = baseDao.getMaxE0127();
				map.put("e0127", e0127);
				System.out.println("get e0127:" + e0127);
				// 更新对应的表单数据
				// update id_factory set currentid='002172' where
				// sequence_name='A01.E0127'
				baseDao.excute("update id_factory set currentid='" + e0127 + "' where sequence_name='A01.E0127'");

				// list3.get(0).put("AZ302",map.get("AZ302").toString());
				// list3.get(0).put("AZ301",map.get("AZ301").toString());
				// list3.get(0).put("AZ303",map.get("AZ303").toString());
				// list3.get(0).put("AZ304",map.get("AZ304").toString());
				listA55.put("A5551", map.get("A5551").toString());
				listA55.put("A5540", map.get("A5540").toString());
				listA55.put("E5501", map.get("E5501").toString());
				listA55.put("E5504", map.get("E5504").toString());
				listA55.put("E5507", map.get("E5507").toString());
				listA55.put("A5545", map.get("A5545").toString()); // 20190301
				listA55.put("C5579", "01"); // 20190301

				// listA13.put("AZ302",map.get("AZ302").toString());
				listA13.put("AZ301", map.get("AZ301").toString());
				listA13.put("AZ303", map.get("AZ303").toString());
				listA13.put("AZ304", map.get("AZ304").toString());
				// A14AB 转正后薪金不传 -20180410
				// listA14.put("A14AB",map.get("A14AB").toString());
				// listA14.put("A14AA",map.get("A14AA").toString());
				listA14.put("C1401", map.get("C1401").toString());
				listA14.put("C1405", map.get("C1405").toString());

				// 试用期结束时间=试用期开始时间+试用时间（C1405） 2018-03-19
				// 試用期開始時間 E1403

				String C1405 = map.get("C1405").toString();
				String E1403 = map.get("A01AH").toString();
				String E1405 = "";
				if (C1405.equals("01")) {
					// 1
					E1405 = calE1405(E1403, 1);
				} else if (C1405.equals("02")) {
					// 2
					E1405 = calE1405(E1403, 2);
				} else if (C1405.equals("03")) {
					// 3
					E1405 = calE1405(E1403, 3);
				} else if (C1405.equals("04")) {
					// 6
					E1405 = calE1405(E1403, 6);
				}
				// 添加试用期开始和结束时间
				if (C1405.equals("05")) {
					listA14.put("E1403", "");
				} else {
					listA14.put("E1403", map.get("A01AH").toString());
				}
				listA14.put("E1405", E1405);

				// 添加四个字段 ：试用期薪金基本工资 A5540 试用期薪金岗位工资 E5501
				// 试用期薪金绩效奖金E5504 试用期浮动技术工资E5507
				//
				List<Map<String, Object>> list1 = (List<Map<String, Object>>) map.get("chengyuan"); // AZX
				// List<Map<String,Object>> list2 = (List<Map<String,
				// Object>>)map.get("positive");
				// List<Map<String,Object>> list3 =str3;//listA13;
				List<Map<String, Object>> list4 = (List<Map<String, Object>>) map.get("education");
				List<Map<String, Object>> list5 = (List<Map<String, Object>>) map.get("work");
				Map<String, Object> B0110Map = baseDao.Queryzzjg(map.get("B0110").toString());
				Map<String, Object> E0122Map = baseDao.Queryzzjg(map.get("E0122").toString());
				Map<String, Object> E01A1Map = baseDao.Queryzzjg(map.get("E01A1").toString());
				Integer count = baseDao.QUERYApp();
				if (count == 0) {
					A0000 = "10000";
					A0100 = "00010000";
				} else {
					A0000 = baseDao.QueryA0000();
					A0100 = baseDao.QUERYMAXA0100();
				}
				map.put("A0000", A0000);
				map.put("A0100", A0100);
				map.put("B0110", B0110Map.get("CODEITEMID"));
				map.put("E0122", E0122Map.get("CODEITEMID"));
				map.put("E01A1", E01A1Map.get("CODEITEMID"));
				Map<String, Object> person2 = new HashMap<>();
				// baseDao.insertBatch("UsrA01",list);
				// baseDao.writePersonel(map);
				baseDao.writePersonelusr(map);
				// baseDao.insertUsrList(list1,"AppA79",A0100);

				// 如果表单没有数据 不操作
				if (list1.size() < 1) {
				} else {
					baseDao.insertUsrList(list1, "usrA79", A0100);
				}

				// *本就不用 * baseDao.insertUsrList(list2,"AppA14",A0100);

				// baseDao.App(listA13,"AppAZ3",A0100);
				// baseDao.App(listA14,"AppA14",A0100);
				// baseDao.insertUsrList(list4,"AppA04",A0100);
				// baseDao.insertUsrList(list5,"AppA19",A0100);
				// baseDao.App(listA55,"AppA55",A0100);

				baseDao.App(listA13, "usrAZ3", A0100);
				baseDao.App(listA14, "usrA14", A0100);
				if (list4.size() < 1) {
				} else {
					baseDao.insertUsrList(list4, "usrA04", A0100);
				}
				if (list5.size() < 1) {
				} else {
					baseDao.insertUsrList(list5, "usrA19", A0100);
				}
				baseDao.App(listA55, "usrA55", A0100);
				baseDao.excute(
						"Insert into usrA10(A0100,i9999,A10AA) values('" + A0100 + "',1," + map.get("A10AA") + ")");
				baseDao.excute("update usra01 set modTime=SYSDATE,CREATETIME=SYSDATE where A0100='" + A0100 + "'");
				return new ResultMessage("true", "入职成功").toString();
			} catch (Exception e) {
				String str = e.getMessage();
				e.printStackTrace();
				// 添加删除信息功能
				// baseDao.delete("", updateKey, values)
				baseDao.excute("delete usra01 where A0100='" + A0100 + "'");
				baseDao.excute("delete usrA79 where A0100='" + A0100 + "'");
				baseDao.excute("delete usrAZ3 where A0100='" + A0100 + "'");
				baseDao.excute("delete usrA14 where A0100='" + A0100 + "'");
				baseDao.excute("delete usrA04 where A0100='" + A0100 + "'");
				baseDao.excute("delete usrA19 where A0100='" + A0100 + "'");
				baseDao.excute("delete usrA55 where A0100='" + A0100 + "'");
				baseDao.excute("delete usrA10 where A0100='" + A0100 + "'");
				WriteIOstream.GetMessage("入职失败", "入职信息失败，请联系管理员：" + str);
				// throw new RuntimeException(new ResultMessage("false",
				// "入职信息失败，请联系管理员："+str).toString());
				return new ResultMessage("false", "入职信息失败，请联系管理员：" + str).toString();
			}
		}
	}

	// 调动
	// 减少A17AI字段
	@Override
	@SuppressWarnings({ "unchecked", "null" })
	@Transactional(rollbackFor = Exception.class)
	public String requestForTransfer(String B0110, String E0127, String A17AK, String A17AV, String E0122, String E01A1,
			String A5551, String A5540, String E5501, String E5504, String E5507, String A17AJ, String A17AL,
			String A5545) {
		synchronized (E0127) {
			WriteIOstream.GetwritetoJson("岗位调动申请",
					"工号：" + E0127 + " 调动类型: " + A17AK + " 异动时间 : " + A17AV + " 新部门 : " + E0122 + " 新岗位： " + E01A1
							+ " 基本工资 :" + A5540 + " 岗位工资: " + E5501 + " 绩效奖金 :" + E5504 + " 浮动技术工资 :" + E5507
							+ " 新职务序列 :" + A17AJ + " 调动原因 " + A17AL);
			System.out.println("岗位调动申请" + "工号：" + E0127 + " 调动类型: " + A17AK + " 异动时间 : " + A17AV + " 新部门 : " + E0122
					+ " 新岗位： " + E01A1 + " 基本工资 :" + A5540 + " 岗位工资: " + E5501 + " 绩效奖金 :" + E5504 + " 浮动技术工资 :" + E5507
					+ " 新职务序列 :" + A17AJ + " 调动原因 " + A17AL);
			try {
				Map<String, Object> map = baseDao.codeitemdesc(E0127);
				System.out.println(map);
				if (map == null || map.size() == 0 || map.get("A0100") == null) {
					return new ResultMessage("false", "找不到" + E0127 + "对应的人员！").toString();
				}
				Map<String, Object> map1 = baseDao.Getgeneral(E0127, "UsrA17", "UsrA55");
				Map<String, Object> map3 = baseDao.Queryzzjg(E0122);
				Map<String, Object> map4 = baseDao.Queryzzjg(E01A1);
				String A17AGstr = map3.get("CODEITEMDESC").toString();
				String A17AHstr2 = map4.get("CODEITEMDESC").toString();
				// 插入调动子集
				Map<String, Object> a17Map = new HashMap<>();
				a17Map.put("A0100", map.get("A0100"));
				a17Map.put("i9999", map1.get("stri9999"));
				a17Map.put("A17AA", map.get("B0110"));  //中文
				a17Map.put("A17AB", map.get("E0122"));
				a17Map.put("A17AC", map.get("E01A1"));
				a17Map.put("A17AD", map.get("A010H"));
				a17Map.put("A17AE", map.get("C0181"));
				a17Map.put("A17AK", A17AK);
				a17Map.put("A17AV", sdf.parse(A17AV));
				a17Map.put("A17AF", map.get("B0110")); //中文
				a17Map.put("A17AG", A17AGstr);
				a17Map.put("A17AH", A17AHstr2);
				// a17Map.put("A17AI", A17AI);
				a17Map.put("A17AJ", A17AJ);
				a17Map.put("A17AL", A17AL);

				// baseDao.insert("UsrA17", a17Map);

				// baseDao.excute("Insert into
				// UsrA17(A0100,i9999,A17AA,A17AB,A17AC,A17AD,A17AE,A17AK,A17AV,A17AG,A17AH,A17AI,A17AJ,A17AL)"
				// + "
				// values('"+map.get("A0100")+"','"+map1.get("stri9999")+"','"+map.get("B0110")+"','"+map.get("E0122")+"','"+map.get("E01A1")+"'"
				// +
				// ",'"+map.get("A010H")+"','"+map.get("C0181")+"','"+A17AK+"',to_date('"+A17AV+"','yyyy-mm-dd'),'"+A17AGstr+"','"+A17AHstr2+"'"
				// + ",'"+A17AI+"','"+A17AJ+"','"+A17AL+"')");

				// 插入薪资信息
				// baseDao.excute("Insert into
				// UsrA55(A0100,i9999,A5540,E5501,E5504,E5507)
				// values('"+map.get("A0100")+"','"+map1.get("str2i9999")+"','"+A5540+"','"+E5501+"','"+E5504+"','"+E5507+"')");
				Map<String, Object> a58InsMap = new HashMap<>();
				// a58InsMap.put("A0100", map.get("A0100"));
				a58InsMap.put("i9999", map1.get("str2i9999"));
				a58InsMap.put("A5540", A5540);
				a58InsMap.put("E5501", E5501);
				a58InsMap.put("E5504", E5504);
				a58InsMap.put("E5507", E5507);
				a58InsMap.put("C5579", "03");// 20190301
				a58InsMap.put("A5545", A5545);// 20190301 日期
				baseDao.App2(a58InsMap, "usrA55", map.get("A0100").toString());
				// baseDao.insert("UsrA55", a58InsMap);

				// A17AI改代码
				// baseDao.excute("update UsrA01 set E0122 =
				// '"+map3.get("CODEITEMID")+"',E01A1 =
				// '"+map4.get("CODEITEMID")+"' , A010H ='"+A17AI+"' ,C0181
				// ='"+A17AJ+"' WHERE A0100 ='"+map.get("A0100")+"'");
				// 更新调动组织架构
				Map<String, Object> a01UpdMap = new HashMap<>();
				a01UpdMap.put("A0100", map.get("A0100"));
				// a01UpdMap.put("B0110", map.get("B0110"));
				// 新单位
				String B0110Code = baseDao.queryParentid(map3.get("CODEITEMID").toString());
				while(baseDao.getCodeSetId(B0110Code)!=null && baseDao.getCodeSetId(B0110Code).equals("UM")){
					B0110Code = baseDao.queryParentid(B0110Code);
				}
				
				a01UpdMap.put("B0110", B0110Code);//----
				a01UpdMap.put("E0122", map3.get("CODEITEMID"));
				a01UpdMap.put("E01A1", map4.get("CODEITEMID"));
				// a01UpdMap.put("A010H", A17AI);
				a01UpdMap.put("C0181", A17AJ);
				// baseDao.update("UsrA01", "A0100", a01UpdMap);
				// baseDao.excute("update UsrA01 set E0122 =
				// '"+map3.get("CODEITEMID")+"',E01A1 = '"
				// +map4.get("CODEITEMID")+"' ,C0181 ='"+A17AJ+"' WHERE A0100
				// ='"+map.get("A0100")+"'");
				// 部门：a1707 岗位：a1708 单位：a1706
				a17Map.put("a1706", B0110Code);//---
				a17Map.put("a1707", map3.get("CODEITEMID"));
				a17Map.put("a1708", map4.get("CODEITEMID"));
				baseDao.insert("UsrA17", a17Map);
				baseDao.excute("update usra01 set modTime=SYSDATE where E0127='" + E0127 + "'");
				return new ResultMessage("true", "岗位调动成功").toString();
			} catch (Exception e) {
				String str = e.getMessage();
				e.printStackTrace();
				WriteIOstream.GetMessage("岗位调动失败", str);
				// throw new RuntimeException(new ResultMessage("false",
				// str).toString());
				return new ResultMessage("false", "岗位调动失败，请联系管理员 " + str).toString();
			}
		}
	}

	// 离职申请
	// 2018-04-04 添加一个字段 其他原因（备注） AA314 20180919-新增更新usra01表modTime
	@SuppressWarnings("unchecked")
	@Override
	public String requestForDimission(String E0127, String AA307, String AA309, String AA305, String AA314) {
		WriteIOstream.GetwritetoJson("离职申请",
				"工号：" + E0127 + " 离职类型： " + AA307 + " 离职原因 ： " + AA309 + "：离职日期 ： " + AA305 + "备注：" + AA314);
		synchronized (E0127) {
			try {
				Map<String, Object> map = baseDao.queryUsrA01(E0127);
				if (map == null || map.size() == 0 || map.get("A0100") == null) {
					return new ResultMessage("false", "找不到" + E0127 + "对应的人员！").toString();
				}
				Map<String, Object> map1 = baseDao.Getgeneral(E0127, "UsrAA3", "");
				System.out.println(AA314);
				System.out.println("Insert into UsrAA3(A0100,i9999,AA301,AA303,AA307,AA309,AA305,AA314) values('"
						+ map.get("A0100") + "','" + map1.get("stri9999") + "','" + map.get("E0122") + "','"
						+ map.get("E01A1") + "','" + AA307 + "','" + AA309 + "',to_date('" + AA305 + "','yyyy-mm-dd'),'"
						+ AA314 + "')");
				baseDao.excute("Insert into UsrAA3(A0100,i9999,AA301,AA303,AA307,AA309,AA305,AA314) values('"
						+ map.get("A0100") + "','" + map1.get("stri9999") + "','" + map.get("E0122") + "','"
						+ map.get("E01A1") + "','" + AA307 + "','" + AA309 + "',to_date('" + AA305 + "','yyyy-mm-dd'),'"
						+ AA314 + "')");
				baseDao.excute("update usra01 set modTime=SYSDATE where e0127='" + E0127 + "'");
				return new ResultMessage("true", "离职申请成功").toString();
			} catch (Exception e) {
				String str2 = e.getMessage();
				e.printStackTrace();
				WriteIOstream.GetMessage("离职申请", str2);
				return new ResultMessage("false", "离职申请失败，请联系管理员 " + str2).toString();
			}
		}
	}

	// 请休假申请 要去掉 Q1519
	@Override
	public String requestForLeave(String requestBody) {
		synchronized (this) {
			WriteIOstream.GetwritetoJson("请休假申请", requestBody);
			String str = "{ForLeave:" + requestBody + "}";
			Map<String, Object> map = JsonUtil.json2Map(str);
			List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("ForLeave"); // A01
			Map<String, Object> map4 = new HashMap<>();
			Map<String, Object> map3 = new HashMap<>();
			try {
				for (Map<String, Object> map2 : list) {
					if (baseDao.inRetA01(map2.get("E0127").toString()).equals("true")) {
						map3 = baseDao.queryRetA01(map2.get("E0127").toString());
					} else {
						map3 = baseDao.queryUsrA01(map2.get("E0127").toString());
					}
					// Map<String,Object> map3
					// =baseDao.queryUsrA01(map2.get("E0127").toString());
					if (map3 == null || map3.size() == 0) {
						return new ResultMessage("false", "没有对应的人员工号" + map3.get("E0127") + "信息,请核实").toString();
					}
					String Q1501 = baseDao.tableName_01("Q15").toString();
					map4.put("Q1501", Q1501);
					map4.put("nbase", "Usr");
					map4.put("A0100", map3.get("A0100"));
					map4.put("E0122", map3.get("E0122"));
					map4.put("E01A1", map3.get("E01A1"));
					map4.put("A0101", map3.get("A0101"));
					map4.put("B0110", map3.get("B0110"));
					map4.put("Q15Z0", "01");
					map4.put("Q15Z5", "03");
					map4.put("Q1503", map2.get("Q1503")); // 请假类型
					map4.put("Q1520", map2.get("Q15AA")); // 请假天数 Q15AA
					map4.put("Q1507", map2.get("Q1507")); // 请假原因 Q1507
					map4.put("Q1505", map2.get("Q1505")); // 申请日期 Q1505
					// map4.put("Q1519", Q1501);
					baseDao.excute(
							"Insert into Q15(nbase,Q1501,A0100,E0122,E01A1,A0101,B0110,Q15Z0,Q15Z5,Q1503,Q1520,Q1507,Q1505,Q15Z1,Q15Z3) "
									+ "values('" + map4.get("nbase") + "','" + map4.get("Q1501") + "','"
									+ map4.get("A0100") + "','" + map4.get("E0122") + "','" + map4.get("E01A1") + "',"
									+ "'" + map4.get("A0101") + "','" + map4.get("B0110") + "','" + map4.get("Q15Z0")
									+ "','" + map4.get("Q15Z5") + "','" + map4.get("Q1503") + "'," + "'"
									+ map4.get("Q1520") + "','" + map4.get("Q1507") + "',sysdate,to_date('"
									+ map2.get("Q15Z1") + "','yyyy-mm-dd hh24:mi:ss')," + "to_date('"
									+ map2.get("Q15Z3") + "','yyyy-mm-dd hh24:mi:ss'))");
					// baseDao.excute("Insert into
					// Q15(nbase,Q1501,A0100,E0122,E01A1,A0101,B0110,Q15Z0,Q15Z5,Q1503,Q1520,Q1507,Q1505,Q15Z1,Q15Z3,Q1519)
					// "
					// +
					// "values('"+map4.get("nbase")+"','"+map4.get("Q1501")+"','"+map4.get("A0100")+"','"+map4.get("E0122")+"','"+map4.get("E01A1")+"',"
					// +
					// "'"+map4.get("A0101")+"','"+map4.get("B0110")+"','"+map4.get("Q15Z0")+"','"+map4.get("Q15Z5")+"','"+map4.get("Q1503")+"',"
					// +
					// "'"+map4.get("Q1520")+"','"+map4.get("Q1507")+"',sysdate,to_date('"+map2.get("Q15Z1")+"','yyyy-mm-dd
					// hh24:mi:ss'),"
					// + "to_date('"+map2.get("Q15Z3")+"','yyyy-mm-dd
					// hh24:mi:ss'),'"+map4.get("Q1519")+"')");
					baseDao.excute(
							"update usra01 set modTime=SYSDATE where A0100='" + map3.get("A0100").toString() + "'");
				}
				return new ResultMessage("true", "请休假成功").toString();
			} catch (Exception e) {
				e.printStackTrace();
				String str2 = e.getMessage();
				WriteIOstream.GetMessage("请休假申请失败", "异常信息是: " + str2);
				return new ResultMessage("false", "请休假申请失败，请联系管理员  " + str2).toString();
			}
		}
	}

	// 加班申请 加班流程
	@Override
	public String requestForOvertime(String requestBody) {
		synchronized (this) {
			WriteIOstream.GetwritetoJson("加班申请", requestBody);
			System.out.println(requestBody);
			// 传数组
			// requestBody ="[{\"E0127\":\"11080803\",\"Q11Z1\":\"2018-01-23
			// 00:00\",\"Q11Z3\":\"2018-01-23
			// 19:37\",\"Q11AA\":0,\"Q1103\":1,\"Q1107\":\"没事干\"},{\"E0127\":\"000914\",\"Q11Z1\":\"2018-01-23
			// 00:00\",\"Q11Z3\":\"2018-01-23
			// 19:37\",\"Q11AA\":0,\"Q1103\":1,\"Q1107\":\"没事干\"},{\"E0127\":\"1108080000\",\"Q11Z1\":\"2018-01-23
			// 00:00\",\"Q11Z3\":\"2018-01-23
			// 19:37\",\"Q11AA\":0,\"Q1103\":1,\"Q1107\":\"没事干\"},{\"E0127\":\"491613\",\"Q11Z1\":\"2018-01-23
			// 00:00\",\"Q11Z3\":\"2018-01-23
			// 19:37\",\"Q11AA\":0,\"Q1103\":1,\"Q1107\":\"没事干\"}]";
			String str = "{ForOvertime:" + requestBody + "}";
			Map<String, Object> map = JsonUtil.json2Map(str);
			List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("ForOvertime"); // A01
			String sql = "";
			String values = "";
			Map<String, Object> map3 = new HashMap<>();
			Map<String, Object> map4 = new HashMap<>();
			try {
				for (Map<String, Object> map2 : list) {
					// 区分是在在职库还是离职库
					if (baseDao.inRetA01(map2.get("E0127").toString()).equals("true")) {
						map3 = baseDao.queryRetA01(map2.get("E0127").toString());
					} else {
						map3 = baseDao.queryUsrA01(map2.get("E0127").toString());
					}
					map4.put("Q1103", map2.get("Q1103"));
					map4.put("Q11Z0", "01");
					map4.put("Q11Z5", "03");
					map4.put("Q11AA", map2.get("Q11AA"));
					// to_date('"+Q13Z1+"','yyyy-mm-dd hh24:mi:ss')
					map4.put("Q11Z1", "to_date('" + map2.get("Q11Z1") + "','yyyy-mm-dd hh24:mi:ss')");
					map4.put("Q11Z3", "to_date('" + map2.get("Q11Z3") + "','yyyy-mm-dd hh24:mi:ss')");
					map4.put("Q11AA", map2.get("Q11AA"));
					map4.put("Q1107", map2.get("Q1107"));
					map4.put("E0127", map2.get("E0127"));
					if (map3 == null || map3.size() == 0) {
						// WriteIOstream.ErrorPersonnel("加班人员异常信息：",map2.toString());
						baseDao.excute("Insert into overtime(nbase,Q11AA,Q11Z0,Q1107,Q11Z5,Q1103,Q11Z1,Q11Z3,E0127) "
								+ "values('Usr','" + map4.get("Q11AA") + "','" + map4.get("Q11Z0") + "','"
								+ map4.get("Q1107") + "'," + "'" + map4.get("Q11Z5") + "','" + map4.get("Q1103")
								+ "',to_date('" + map2.get("Q11Z1") + "','yyyy-mm-dd hh24:mi:ss')," + "to_date('"
								+ map2.get("Q11Z3") + "','yyyy-mm-dd hh24:mi:ss'),'" + map4.get("E0127") + "')");
						continue;
						// return new ResultMessage("false", "没有对应的人员工号"
						// +map3.get("E0127")+ "信息,请核实").toString();
					}
					map4.put("Q1101", baseDao.tableName_01("Q11").toString());
					// map4.put("nbase", "Usr");
					map4.put("A0100", map3.get("A0100"));
					map4.put("E0122", map3.get("E0122"));
					map4.put("E01A1", map3.get("E01A1"));
					map4.put("A0101", map3.get("A0101"));
					map4.put("B0110", map3.get("B0110"));
					if (baseDao.countQ11("Q11", map4.get("A0100").toString(), map4.get("Q11Z1").toString(),
							map4.get("Q11Z3").toString()) == 0) {
						baseDao.excute(
								"Insert into Q11(nbase,Q11AA,E01A1,B0110,Q11Z0,Q1107,Q11Z5,A0101,Q1103,A0100,Q1101,E0122,Q11Z1,Q11Z3) "
										+ "values('Usr','" + map4.get("Q11AA") + "','" + map4.get("E01A1") + "','"
										+ map4.get("B0110") + "','" + map4.get("Q11Z0") + "'," + "'" + map4.get("Q1107")
										+ "','" + map4.get("Q11Z5") + "','" + map4.get("A0101") + "','"
										+ map4.get("Q1103") + "','" + map4.get("A0100") + "'," + "'" + map4.get("Q1101")
										+ "','" + map4.get("E0122") + "',to_date('" + map2.get("Q11Z1")
										+ "','yyyy-mm-dd hh24:mi:ss')," + "to_date('" + map2.get("Q11Z3")
										+ "','yyyy-mm-dd hh24:mi:ss'))");
					}
					baseDao.excute(
							"update usra01 set modTime=SYSDATE where A0100='" + map3.get("A0100").toString() + "'");
				}
				return new ResultMessage("true", "在职人员加班申请成功").toString();
			} catch (Exception e) {
				e.printStackTrace();
				String str2 = e.getMessage();
				WriteIOstream.GetMessage("在职人员加班信息", "异常信息是: " + str2);
				return new ResultMessage("false", "加班申请失败，请联系管理员  " + str2).toString();
			}
		}
	}

	// 续签合同 XQAZ3
	// 添加AZ302 --20180410 方法名不改
	@Override
	public String requestForTrip(String E0127, String AZ3AH, String AZ3AC, String AZ3AA, String AZ303, String AZ304,
			String AZ302, String AZ3AE) {
		// WriteIOstream.GetwritetoJson("续签合同test","工号 :"+E0127+" 是否续签 :
		// "+AZ3AH+" 合同属性: "+AZ3AC+" 合同次数: "+ AZ3AA+" 合同起始日期： "+ AZ303+" 合同终止日期：
		// "+ AZ304 + "签订期限"+AZ302);
		synchronized (E0127) {
			WriteIOstream.GetwritetoJson("续签合同", "工号 :" + E0127 + " 是否续签 : " + AZ3AH + " 合同属性: " + AZ3AC + " 合同次数: "
					+ AZ3AA + " 合同起始日期： " + AZ303 + " 合同终止日期： " + AZ304 + "签订期限" + AZ302 + "不续签原因：" + AZ3AE);// 20190301
			// 是否续签 AZ3AH
			// 合同属性 AZ3AC
			// 合同次数 AZ3AA 传过来的时候是6.0
			// 合同起始日期 AZ303
			// 合同终止日期 AZ304
			try {
				Map<String, Object> map1 = baseDao.Getgeneral(E0127, "UsrAZ3", "");
				if (map1.get("false") != null) {
					return map1.toString();
				}
				AZ3AA = String.valueOf(Double.valueOf(AZ3AA) + 1);
				double AZ3AATemp = Double.valueOf(AZ3AA);
				// String str2 =
				// values+"to_date('"+Work_date+"','yyyy.mm.dd'),'"+Work_time+"'";
				baseDao.excute("Insert into UsrAZ3(A0100,i9999,AZ3AH,AZ3AC,AZ3AA,AZ303,AZ304,AZ302,AZ3AE) values('"
						+ map1.get("A0100") + "','" + map1.get("stri9999") + "','" + AZ3AH + "','" + AZ3AC + "','"
						+ AZ3AATemp + "',to_date('" + AZ303 + "','yyyy-mm-dd'),to_date('" + AZ304 + "','yyyy-mm-dd'),'"
						+ AZ302 + "','" + AZ3AE + "')");
				baseDao.excute("update usra01 set modTime=SYSDATE where E0127='" + E0127 + "'");
				return new ResultMessage("true", "合同续签成功").toString();
			} catch (Exception e) {
				String str = e.getMessage();
				e.printStackTrace();
				WriteIOstream.GetMessage("合同续签", str);
				return new ResultMessage("false", "合同续签失败，请联系管理员  " + str).toString();
			}
		}
	}

	// 外出/打卡异常 //员工出差流程 // 员工本地出差流程
	@Override
	@SuppressWarnings({ "unchecked", "null" })
	@Transactional(rollbackFor = Exception.class)
	public String requestForTimeCard(String E0127, String type, String Q13Z1, String Q13Z3, String Q1307,
			String Work_date, String Work_time, String Oper_cause) {
		synchronized (E0127) {
			WriteIOstream.GetMessage("外出/打卡异常", " 工号： " + E0127 + " 类型： " + type + " 外出开始日期：" + Q13Z1 + " 外出结束日期 ："
					+ Q13Z3 + " 外出原因：" + Q1307 + " 忘记打卡日期： " + Work_date + " 忘记打卡时间： " + Work_time);
			// 类型 type
			// 外出开始日期 Q13Z1
			// 外出结束日期 Q13Z3
			// 外出原因 Q1307
			// 忘记打卡日期 Work_date
			// 忘记打卡时 Work_time
			Map<String, Object> map = new HashMap<>();
			if (baseDao.inRetA01(E0127).equals("true")) {
				map = baseDao.queryRetA01(E0127);
			} else {
				map = baseDao.queryUsrA01(E0127);
			}
			// Map<String,Object> map =baseDao.queryUsrA01(E0127);
			String sql = "";
			String values = "";
			Map<String, Object> map1 = new HashMap<>();
			if (map == null || map.size() == 0) {
				return new ResultMessage("false", "没有对应的人员工号" + E0127 + "信息,请核实").toString();
			}
			try {
				map1.put("A0100", map.get("A0100"));
				map1.put("nbase", "Usr");
				map1.put("E0122", map.get("E0122"));
				map1.put("E01A1", map.get("E01A1"));
				map1.put("A0101", map.get("A0101"));
				map1.put("B0110", map.get("B0110"));
				if (type.equals("32")) {
					map1.put("card_no", map.get("C01TC"));
					map1.put("Oper_cause", Oper_cause); // 类型为32 有值时 默认为漏打卡
					map1.put("datafrom", "1");
					map1.put("inout_flag", "0");
					map1.put("sp_flag", "03");
					map1.put("Work_date", Work_date);
					map1.put("Work_time", Work_time);
					for (String KEY : map1.keySet()) {
						sql = sql + KEY + ",";
						values = values + "'" + map1.get(KEY) + "',";
					}
					String str = sql.substring(0, sql.length() - 1);
					String str2 = values.substring(0, values.length() - 1);
					if (baseDao.countkq_originality_data("kq_originality_data", map.get("A0100").toString(), Work_date,
							Work_time) == 0) {
						baseDao.excute("Insert into kq_originality_data(" + str + ") values(" + str2 + ")");
					}
					// PolySyncDao.insertPerson_Usrfhsj("kq_originality_data",str,
					// str2);
					return new ResultMessage("true", "补卡成功").toString();
				} else if (type.equals("31")) {
					map1.put("Q1301", baseDao.tableName_01("Q13"));
					map1.put("Q13Z0", "01");
					map1.put("Q13Z5", "03");
					map1.put("Q1303", "31");
					map1.put("Q1307", Q1307);
					for (String KEY : map1.keySet()) {
						sql = sql + KEY + ",";
						values = values + "'" + map1.get(KEY) + "',";
					}
					String str = sql + "Q13Z1,Q13Z3,Q1305";
					String str2 = values + "to_date('" + Q13Z1 + "','yyyy-mm-dd hh24:mi:ss'),to_date('" + Q13Z3
							+ "','yyyy-mm-dd hh24:mi:ss'),sysdate";
					if (baseDao.countQ13("Q13", map.get("A0100").toString(),
							"to_date('" + Q13Z1 + "','yyyy-mm-dd hh24:mi:ss')",
							"to_date('" + Q13Z3 + "','yyyy-mm-dd hh24:mi:ss')") == 0) {
						baseDao.excute("Insert into Q13(" + str + ") values(" + str2 + ")");
					}
					// PolySyncDao.insertPerson_Usrfhsj("Q13",str, str2);
					return new ResultMessage("true", "外地出差申请成功").toString();
				} else {
					map1.put("Q1301", baseDao.tableName_01("Q13"));
					map1.put("Q13Z0", "01");
					map1.put("Q13Z5", "03");
					map1.put("Q1303", "30");
					map1.put("Q1307", Q1307);
					for (String KEY : map1.keySet()) {
						sql = sql + KEY + ",";
						values = values + "'" + map1.get(KEY) + "',";
					}
					String str = sql + "Q13Z1,Q13Z3,Q1305";
					String str2 = values + "to_date('" + Q13Z1 + "','yyyy-mm-dd hh24:mi:ss'),to_date('" + Q13Z3
							+ "','yyyy-mm-dd hh24:mi:ss'),sysdate";
					if (baseDao.countQ13("Q13", map.get("A0100").toString(),
							"to_date('" + Q13Z1 + "','yyyy-mm-dd hh24:mi:ss')",
							"to_date('" + Q13Z3 + "','yyyy-mm-dd hh24:mi:ss')") == 0) {
						baseDao.excute("Insert into Q13(" + str + ") values(" + str2 + ")");
					}
					baseDao.excute("update usra01 set modTime=SYSDATE where E0127='" + E0127 + "'");
					return new ResultMessage("true", "本地外出申请成功").toString();
				}
			} catch (Exception e) {
				e.printStackTrace();
				String str = e.getMessage();
				WriteIOstream.GetMessage("打卡/出差失败", str);
				throw new RuntimeException(new ResultMessage("false", "打卡/出差失败，请联系管理员  " + str).toString());
			}

		}
	}

	// 试用考核
	@Override
	@SuppressWarnings({ "unchecked", "null" })
	@Transactional(rollbackFor = Exception.class)
	public String requestForOut(String E0127, String E1403, String E1405, String A1403, String C1402, String A5540,
			String E5501, String E5504, String E5507, String A5545) {
		synchronized (E0127) {
			WriteIOstream.GetMessage("试用考核",
					"工号：" + E0127 + " 试用开始日期： " + E1403 + " 试用终止日期：" + E1405 + " 试用期考核分数： " + A1403 + " 转正日期： " + C1402
							+ " 基本工资： " + A5540 + " 岗位工资： " + E5501 + " 绩效奖金：" + E5504 + " 浮动技术工资： " + E5507);
			try {
				// 试用开始日期 E1403
				// 试用终止日期 E1405
				// 试用期考核分数 A1403
				// 转正日期 C1402
				// 基本工资 A5540
				// 岗位工资 E5501
				// 绩效奖金 E5504
				// 浮动技术工资 E5507
				// String str2 =
				// values+"to_date('"+Work_date+"','yyyy.mm.dd'),'"+Work_time+"'";
				Map<String, Object> map1 = baseDao.Getgeneral(E0127, "UsrA14", "UsrA55");
				if (map1.get("false") != null) {
					return map1.toString();
				}
				baseDao.excute(
						"Insert into UsrA14(A0100,i9999,E1403,E1405,A1403,C1402) values" + "('" + map1.get("A0100")
								+ "','" + map1.get("stri9999") + "',to_date('" + E1403 + "','yyyy-mm-dd'),to_date('"
								+ E1405 + "','yyyy-mm-dd')," + A1403 + ",to_date('" + C1402 + "','yyyy-mm-dd'))");
				// baseDao.excute("Insert into
				// UsrA55(A0100,i9999,A5540,E5501,E5504,E5507)
				// values('"+map1.get("A0100")+"','"+map1.get("str2i9999")+"',"+A5540+","+E5501+","+E5504+","+E5507+")");
				Map<String, Object> a58InsMap = new HashMap<>();
				// a58InsMap.put("A0100", map1.get("A0100"));
				a58InsMap.put("i9999", map1.get("str2i9999"));
				a58InsMap.put("A5540", A5540);
				a58InsMap.put("E5501", E5501);
				a58InsMap.put("E5504", E5504);
				a58InsMap.put("E5507", E5507);
				a58InsMap.put("C5579", "02");// 20190301
				a58InsMap.put("A5545", A5545);// 20190301
				baseDao.App2(a58InsMap, "usrA55", map1.get("A0100").toString());// 20190301
				// baseDao.insert("UsrA55", a58InsMap);
				baseDao.excute("update usra01 set modTime=SYSDATE where E0127='" + E0127 + "'");
				return new ResultMessage("true", "试用考核成功").toString();
			} catch (Exception e) {
				String str = e.getMessage();
				e.printStackTrace();
				WriteIOstream.GetMessage("试用考核异常", str);
				// return new ResultMessage("false", "试用考核失败，请联系管理员
				// "+str).toString();
				throw new RuntimeException(new ResultMessage("false", "试用考核失败，请联系管理员  " + str).toString());
			}
		}
	}

	// 请假异常流程
	@Override
	@SuppressWarnings({ "unchecked", "null" })
	@Transactional(rollbackFor = Exception.class)
	public String salesLeave(String salesLeaveInfo) {
		System.out.println(salesLeaveInfo);
		WriteIOstream.GetwritetoJson("请假异常流程", salesLeaveInfo);// requestBody:是相关的数据
		try {
			Map<String, Object> salesLeaveS = JsonUtil.json2Map(salesLeaveInfo);
			// String E0127 = salesLeaveS.get("E0127").toString();
			String type = salesLeaveS.get("type").toString();
			String Q1501 = salesLeaveS.get("Q1501").toString();
			String Q15z1 = salesLeaveS.get("Q15z1").toString();
			String Q15z3 = salesLeaveS.get("Q15z3").toString();
			String Q1520 = salesLeaveS.get("Q1520").toString();

			// 1为更新
			if (type.equals("1")) {
				baseDao.excute("update Q15 set Q15z1= to_date('" + Q15z1 + "','yyyy-MM-dd HH24:mi:ss'),"
						+ "Q15z3=to_date('" + Q15z3 + "','yyyy-MM-dd HH24:mi:ss'),Q1520='" + Q1520 + "' where Q1501='"
						+ Q1501 + "'");
			} else if (type.equals("2")) { // 2为删除
				baseDao.excute("delete Q15 where Q1501='" + Q1501 + "'");
			} else {
				return "类型传值为：" + type + " 不符合要求！";
			}
		} catch (Exception e) {
			String str = e.getMessage();
			e.printStackTrace();
			WriteIOstream.GetMessage("请假异常修改出错：", str);
			throw new RuntimeException(new ResultMessage("false", "请假异常修改出错，请联系管理员  " + str).toString());
		}
		return new ResultMessage("true", "请假异常修改成功").toString();
	}

	// 管理人员任免 20190103
	@Override
	@SuppressWarnings({ "unchecked", "null" })
	@Transactional(rollbackFor = Exception.class)
	public String toAppointorRemove(String toAppointorRemoveInfo) {
		System.out.println(toAppointorRemoveInfo);
		WriteIOstream.GetwritetoJson("管理人员任免:", toAppointorRemoveInfo);// requestBody:是相关的数据
		try {
			Map<String, Object> toAppointorRemoveInfoS = JsonUtil.json2Map(toAppointorRemoveInfo);
			String E0127 = toAppointorRemoveInfoS.get("E0127").toString();
			String type = toAppointorRemoveInfoS.get("type").toString();
			String A0100 = baseDao.queryE0127(E0127);

			if (type.equals("1")) {// 主职
				String i9999 = baseDao.queryi9999("usra17", A0100); // A17表中的I9999
				String A17AA = baseDao.queryB0110ByE0127(E0127); // 原公司
				String A17AB = baseDao.queryE0122ByE0127(E0127); // 原部门
				String A17AC = baseDao.queryE01a1ByE0127(E0127); // 原岗位
				String A17AD = baseDao.queryA010HByE0127(E0127); // 原职务序列
				String A17AE = baseDao.queryC0181ByE0127(E0127); // 原人员类别

				String A17AF = baseDao.getCodeitemidByGuidkey(toAppointorRemoveInfoS.get("A17AF").toString()); // 新公司
				String A17AG = baseDao.getCodeitemidByGuidkey(toAppointorRemoveInfoS.get("A17AG").toString()); // 新部门
				String A17AH = baseDao.getCodeitemidByGuidkey(toAppointorRemoveInfoS.get("A17AH").toString()); // 新岗位
				String A17AI = toAppointorRemoveInfoS.get("A17AI").toString(); // 新职务序列
				String A17AJ = "002"; // 新员工类别 默认非操作类
				String A17AK = toAppointorRemoveInfoS.get("A17AK").toString(); // 调动类型
				String A17AV = toAppointorRemoveInfoS.get("A17AV").toString(); // 异动时间

				String E1403 = toAppointorRemoveInfoS.get("E1403").toString(); // 试用开始日期
				String E1405 = toAppointorRemoveInfoS.get("E1405").toString(); // 试用终止日期

				String A5545 = toAppointorRemoveInfoS.get("A5545").toString(); // 起薪日期
				String A5551 = toAppointorRemoveInfoS.get("A5551").toString(); // 工资合计标准
				String A5540 = toAppointorRemoveInfoS.get("A5540").toString(); // 基本工资标准
				String E5501 = toAppointorRemoveInfoS.get("E5501").toString(); // 岗位津贴标准
				String E5504 = toAppointorRemoveInfoS.get("E5504").toString(); // 绩效奖金标准

				System.out.println("insert into usrA17(A0100,i9999,A17AA,A17AB,A17AC,A17AD,A17AE,"
						+ "A17AF,A17AG,A17AH,A17AI,A17AJ,A17AK,A17AV) values('" + A0100 + "','" + i9999 + "'," + "'"
						+ A17AA + "','" + A17AB + "','" + A17AC + "','" + A17AD + "','" + A17AE + "','" + A17AF + "',"
						+ "'" + A17AG + "','" + A17AH + "','" + A17AI + "','" + A17AJ + "','" + A17AK + "',to_date('"
						+ A17AV + "','yyyy-mm-dd'))");

				// 新增A17
				baseDao.excute("insert into usrA17(A0100,i9999,A17AA,A17AB,A17AC,A17AD,A17AE,"
						+ "A17AF,A17AG,A17AH,A17AI,A17AJ,A17AK,A17AV) values('" + A0100 + "','" + i9999 + "'," + "'"
						+ A17AA + "','" + A17AB + "','" + A17AC + "','" + A17AD + "','" + A17AE + "','" + A17AF + "',"
						+ "'" + A17AG + "','" + A17AH + "','" + A17AI + "','" + A17AJ + "','" + A17AK + "',to_date('"
						+ A17AV + "','yyyy-mm-dd'))");

				// A14的i9999
				i9999 = baseDao.queryi9999("usra14", A0100);

				System.out.println("insert into usrA14(A0100,i9999,E1403,E1405) values('" + A0100 + "','" + i9999
						+ "',to_date('" + E1403 + "','yyyy-mm-dd')" + ",to_date('" + E1405 + "','yyyy-mm-dd'))");
				// 新增A14
				baseDao.excute("insert into usrA14(A0100,i9999,E1403,E1405) values('" + A0100 + "','" + i9999
						+ "',to_date('" + E1403 + "','yyyy-mm-dd')" + ",to_date('" + E1405 + "','yyyy-mm-dd'))");

				// A55的i9999
				i9999 = baseDao.queryi9999("usra55", A0100);
				// 新增A55
				System.out.println("insert into usrA55(A0100,i9999,A5545,A5551,A5540,E5501,E5504,C5579) values('"
						+ A0100 + "','" + i9999 + "',to_date('" + A5545 + "','yyyy-mm-dd'),'" + A5551 + "','" + A5540
						+ "','" + E5501 + "','" + E5504 + "','03')");
				baseDao.excute("insert into usrA55(A0100,i9999,A5545,A5551,A5540,E5501,E5504,C5579) values('" + A0100
						+ "','" + i9999 + "',to_date('" + A5545 + "','yyyy-mm-dd'),'" + A5551 + "','" + A5540 + "','"
						+ E5501 + "','" + E5504 + "','03')");

			} else if (type.equals("2")) {// 兼职
				String C0901 = toAppointorRemoveInfoS.get("C0901").toString(); // 任免标识
				String i9999 = baseDao.queryi9999("usra09", A0100);
				if (C0901.equals("0")) { // 任 新增数据
					String E0911 = baseDao.getCodeitemidByGuidkey(toAppointorRemoveInfoS.get("A17AF").toString()); // 兼职单位
					String E0902 = baseDao.getCodeitemidByGuidkey(toAppointorRemoveInfoS.get("A17AG").toString()); // 兼职部门
					String E0914 = baseDao.getCodeitemidByGuidkey(toAppointorRemoveInfoS.get("A17AH").toString()); // 兼职岗位
					String E0907 = toAppointorRemoveInfoS.get("A17AV").toString(); // 兼职时间
					// 新增A09
					baseDao.excute("insert into usrA09(A0100,i9999,C0901,E0911,E0902,E0914,E0907) values('" + A0100
							+ "','" + i9999 + "','" + C0901 + "','" + E0911 + "','" + E0902 + "','" + E0914
							+ "',to_date('" + E0907 + "','yyyy-mm-dd'))");
				} else if (C0901.equals("1")) { // 免 更新数据
					String E0934 = toAppointorRemoveInfoS.get("A17AV").toString(); // 免职时间
					i9999 = baseDao.queryMaxI9999("usra09", A0100);
					baseDao.excute("update usra09 set C0901='" + C0901 + "', E0934 ='" + E0934 + "' " + "where A0100='"
							+ A0100 + "' and i9999='" + i9999 + "'");
				} else {
					return new ResultMessage("false", "兼职任免标识值不符合规范!").toString();
				}
			} else {
				return new ResultMessage("false", "type值不符合规范!").toString();
			}

		} catch (Exception e) {
			String str = e.getMessage();
			e.printStackTrace();
			WriteIOstream.GetMessage("管理人员任免异常：", str);
			throw new RuntimeException(new ResultMessage("false", "管理人员任免异常，请联系管理员  " + str).toString());
		}
		return new ResultMessage("true", "管理人员任免成功").toString();
	}

	// 绩效数据插入 20190426
	@Override
	@SuppressWarnings({ "unchecked", "null" })
	@Transactional(rollbackFor = Exception.class)
	public String insertPerformence(String performanceInfo) {
		System.out.println(performanceInfo);
		WriteIOstream.GetwritetoJson("绩效数据插入:", performanceInfo);// requestBody:是相关的数据
		try {
			Map<String, Object> performanceInfoMap = JsonUtil.json2Map(performanceInfo);
			String E0127 = performanceInfoMap.get("E0127").toString();
			String A2210 = performanceInfoMap.get("A2210").toString();
			String A2215 = performanceInfoMap.get("A2215").toString();
			String A2225 = performanceInfoMap.get("A2225").toString();
			String A2230 = performanceInfoMap.get("A2230").toString();
			String A2235 = performanceInfoMap.get("A2235").toString();
			String A22AA = performanceInfoMap.get("A22AA").toString();
			String A22Z0 = performanceInfoMap.get("A22Z0").toString()+ "-01";

			String A0100 = baseDao.queryE0127(E0127);
			// A22的i9999
			String i9999 = baseDao.queryi9999("usra22", A0100);

			// 获取当月时间
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");// 设置日期格式

//			String A22Z0 = df.format(new Date()).toString() + "-01";
			System.out.println(A22Z0);
			String flag = baseDao.ifA0100ExistA22(A22Z0,A0100);
			//如果当月已经存在数据    就不插入   提醒已存在数据
			if(flag.equals("true") || flag== "true"){
				WriteIOstream.GetwritetoJson("绩效数据插入异常:", "该人员该月已存在数据:"+A22Z0+"--A0100："+A0100);// 
				return new ResultMessage("false", "绩效数据插入异常，该人员该月已存在数据！").toString();
			}
			// 新增A22
			String sql = "insert into usrA22(A0100,i9999,A22Z0,A2210,A2215,A2225,A2230,A2235,A22AA) values('" + A0100
					+ "','" + i9999 + "',to_date('" + A22Z0 + "','yyyy-mm-dd'),'" + A2210 + "','" + A2215 + "','"
					+ A2225 + "','" + A2230 + "','" + A2235 + "','" + A22AA + "')";
			baseDao.excute(sql);
			WriteIOstream.GetwritetoJson("绩效数据插入执行sql:", sql);

		} catch (Exception e) {
			String str = e.getMessage();
			e.printStackTrace();
			WriteIOstream.GetMessage("绩效数据插入异常：", str);
			throw new RuntimeException(new ResultMessage("false", "绩效数据插入异常，请联系管理员  " + str).toString());
		}

		return new ResultMessage("true", "绩效数据插入成功").toString();
	}

	// 绩效数据更新 20190426
	@Override
	@SuppressWarnings({ "unchecked", "null" })
	@Transactional(rollbackFor = Exception.class)
	public String updatePerformence(String performanceInfo) {
		System.out.println(performanceInfo);
		WriteIOstream.GetwritetoJson("绩效数据更新:", performanceInfo);// requestBody:是相关的数据
		try {
			Map<String, Object> performanceInfoMap = JsonUtil.json2Map(performanceInfo);
			String A0100 = performanceInfoMap.get("A0100").toString();
			String A2210 = performanceInfoMap.get("A2210").toString();
			String A2215 = performanceInfoMap.get("A2215").toString();
			String A2225 = performanceInfoMap.get("A2225").toString();
			String A2230 = performanceInfoMap.get("A2230").toString();
			String A2235 = performanceInfoMap.get("A2235").toString();
			String A22AA = performanceInfoMap.get("A22AA").toString();
			String A22Z0 = performanceInfoMap.get("A22Z0").toString();

			// 更新A22
			String sql = "update usra22 set A2210='" + A2210 + "',A2215='" + A2215 + "',A2225='" + A2225 + "',"
					+ "A2230='" + A2230 + "',A2235='" + A2235 + "',A22AA='" + A22AA + "' " + "where a0100='" + A0100
					+ "' and A22Z0=to_date('" + A22Z0 + "','yy-MM-dd')";
			baseDao.excute(sql);
			WriteIOstream.GetwritetoJson("绩效数据更新执行sql:", sql);

		} catch (Exception e) {
			String str = e.getMessage();
			e.printStackTrace();
			WriteIOstream.GetMessage("绩效数据更新异常：", str);
			throw new RuntimeException(new ResultMessage("false", "绩效数据更新异常，请联系管理员  " + str).toString());
		}

		return new ResultMessage("true", "绩效数据更新成功").toString();
	}
	
	// 管理人员任免 20181221 聘任日期 -A5545 yy-mm-dd，工资标准- A5551 ，基本工资 - A5540 int，岗位工资-
	// E5501 int，绩效工资- E5504 int ，C5579 --默认05
	// @Override
	// @SuppressWarnings({ "unchecked", "null" })
	// @Transactional(rollbackFor = Exception.class)
	// public String toAppointorRemove(String E0127,String A5545,String
	// A5551,String A5540,String E5501,String E5504){
	// synchronized (E0127) {
	// WriteIOstream.GetMessage("管理人员任免:" ,"工号："+E0127+" 基本工资："+A5540+"
	// 岗位工资："+E5501+" 绩效奖金："+E5504+" 浮动技术工资：");
	// System.out.println("管理人员任免:" +"工号："+E0127+" 基本工资："+A5540+" 岗位工资："+E5501+"
	// 绩效奖金："+E5504);
	// String C5579 ="05";
	// try{
	// String A0100 = baseDao.queryE0127(E0127);
	// if(A0100==null || A0100.equals("null")){
	// return "工号为："+E0127+" 的人员不存在于人员库中";
	// }else{
	// String I9999 =baseDao.queryi9999("UsrA55", A0100);
	//// System.out.println("Insert into
	// UsrA55(A0100,i9999,A5545,A5551,A5540,E5501,E5504,C5579)
	// values('"+A0100+"','"+I9999+"',to_date('"+A5545+"','yyyy-mm-dd'),"+A5551+","+A5540+","+E5501+","+E5504+",'"+C5579+"')");
	// baseDao.excute("Insert into
	// UsrA55(A0100,i9999,A5545,A5551,A5540,E5501,E5504,C5579)
	// values('"+A0100+"','"+I9999+"',to_date('"+A5545+"','yyyy-mm-dd'),"+A5551+","+A5540+","+E5501+","+E5504+",'"+C5579+"')");
	// return new ResultMessage("true", "管理人员任免成功").toString();
	// }
	//// return new ResultMessage("true", "管理人员任免成功").toString();
	// }catch(Exception e){
	// String str = e.getMessage();
	// e.printStackTrace();
	// WriteIOstream.GetMessage("管理人员任免 异常", str);
	// throw new RuntimeException(new ResultMessage("false", "管理人员任免异常，请联系管理员
	// "+str).toString());
	// }
	// }
	//
	// }

	// //管理人员任免
	// @Override
	// @SuppressWarnings({ "unchecked", "null" })
	// @Transactional(rollbackFor = Exception.class)
	/// * public String toAppointorRemove(String E0127, String A5540, String
	// E5501, String E5504, String E5507, String AAHAC,
	// String AAHAE, String AAHAB, String AAHAA,String A17AH, String
	// A17AA,String A17AG,String A17AK,String A17AV) {*/
	//
	// public String toAppointorRemove(String E0127, String A5540, String E5501,
	// String E5504, String E5507, String AAHAC,
	// String AAHAE, String AAHAB, String AAHAA,String E01A1,String E0122,String
	// A17AK,String A17AV,String A5551) {
	// System.out.println(E0127);
	// synchronized (E0127) {
	// WriteIOstream.GetMessage("管理人员任免:" ,"工号："+E0127+" 基本工资："+A5540+"
	// 岗位工资："+E5501+" 绩效奖金："+E5504+" 浮动技术工资："+E5507+" 拟任职务："+AAHAC+"
	// 聘任开始日期："+AAHAE+" 拟聘任部门： "+AAHAB+" 聘任类型 ："+AAHAA);
	// System.out.println("管理人员任免:" +"工号："+E0127+" 基本工资："+A5540+" 岗位工资："+E5501+"
	// 绩效奖金："+
	// E5504+" 浮动技术工资："+E5507+" 拟任职务："+AAHAC+" 聘任开始日期："+AAHAE+" 拟聘任部门： "+AAHAB+"
	// 聘任类型 ："+AAHAA+
	// A17AK+ A17AV+ A5551+"--");
	// try{
	// Map<String, Object> map1 = baseDao.Getgeneral(E0127,"UsrA55","UsrAAH");
	// if(map1.get("false")!=null){
	// return map1.toString();
	// }
	//
	// Map<String, Object> E0122Map = baseDao.Queryzzjg(E0122.toString());
	// Map<String, Object> E01A1Map = baseDao.Queryzzjg(E01A1.toString());
	//// baseDao.excute("Insert into UsrA55(A0100,i9999,A5540,E5501,E5504,E5507)
	// values('"+map1.get("A0100")+"','"+map1.get("stri9999")+"',"+A5540+","+E5501+","+E5504+","+E5507+")");
	//// System.out.println( map1.get("A0100") +" // "+map1.get("str2i9999")+"
	// // "+A5540+" // "+E5501+" // "+E5504+" // "+E5507);
	// Map<String,Object> a55InsMap = new HashMap<>();
	// a55InsMap.put("A0100", map1.get("A0100"));
	// a55InsMap.put("i9999", map1.get("stri9999"));
	// a55InsMap.put("A5540", A5540);
	// a55InsMap.put("E5501", E5501);
	// a55InsMap.put("E5504", E5504);
	// a55InsMap.put("E5507", E5507);
	// a55InsMap.put("A5551", A5551);
	//
	// System.out.println("hhh");
	// baseDao.insert("UsrA55", a55InsMap);
	// System.out.println("www");
	// //A17AH, String A17AA,String A17AG,String A17AK,String A17AV
	// Map<String,Object> a17InsMap = new HashMap<>();
	// a17InsMap.put("A0100", map1.get("A0100"));
	// a17InsMap.put("i9999",
	// baseDao.queryi9999("UsrA17",map1.get("A0100").toString()));
	// a17InsMap.put("A17AH", E01A1Map.get("CODEITEMDESC"));
	//// a17InsMap.put("A17AA", A17AA);
	// a17InsMap.put("A17AG", E0122Map.get("CODEITEMDESC"));
	// a17InsMap.put("A17AK", A17AK);
	// a17InsMap.put("A17AV", A17AV);
	// baseDao.excute("Insert into UsrA17(A0100,i9999,A17AH,A17AG,A17AK,A17AV)
	// values"
	// +
	// "('"+map1.get("A0100")+"','"+baseDao.queryi9999("UsrA17",map1.get("A0100").toString())
	// +"','" + E01A1Map.get("CODEITEMDESC")
	// +"','"+E0122Map.get("CODEITEMDESC")+"','"+A17AK+"',to_date('"+A17AV+"','yyyy-mm-dd'))");
	//// baseDao.insert("UsrA17", a17InsMap);
	//
	// Map<String,Object> a01UpdMap = new HashMap<>();
	// a01UpdMap.put("A0100", map1.get("A0100"));
	// a01UpdMap.put("i9999",
	// baseDao.queryi9999("UsrA17",map1.get("A0100").toString()));
	// a01UpdMap.put("E01A1", E01A1Map.get("CODEITEMID"));
	//// a01UpdMap.put("B0110", A17AA);
	// a01UpdMap.put("E0122", E0122Map.get("CODEITEMID"));
	//// baseDao.update("UsrA01", "A0100", a01UpdMap);
	// baseDao.excute("update UsrA01 set E0122 =
	// '"+E0122Map.get("CODEITEMID")+"',E01A1 = '"
	// +E01A1Map.get("CODEITEMID")+"' WHERE A0100 ='"+map1.get("A0100")+"'");
	// baseDao.excute("Insert into UsrAAH(A0100,i9999,AAHAC,AAHAE,AAHAB,AAHAA)
	// VALUES('"
	// +map1.get("A0100")+"','"+map1.get("str2i9999")+"','"+AAHAC+"',to_date('"+AAHAE+"','yyyy-mm-dd'),'"
	// +AAHAB+"','"+AAHAA+"')");
	// baseDao.excute("update usra01 set modTime=SYSDATE where
	// E0127='"+E0127+"'");
	// return new ResultMessage("true", "管理人员任免成功").toString();
	// }catch(Exception e){
	// String str = e.getMessage();
	// e.printStackTrace();
	// WriteIOstream.GetMessage("管理人员任免 异常", str);
	// throw new RuntimeException(new ResultMessage("false", "管理人员任免异常，请联系管理员
	// "+str).toString());
	// }
	// }
	// }
	//

	// 查询年假调休假天数
	public String getnumberDays(String E0127, String type) throws Exception {
		synchronized (E0127) {
			WriteIOstream.GetwritetoJson("获取假期信息" + E0127 + "", "类型为：" + type + "");
			try {
				Map<String, Object> map = baseDao.queryUsrA01(E0127);
				if (map != null || map.get("A0100") != null) {
					// List<Map<String, Object>> list =
					// baseDao.ForLeavetype(type,map.get("A0100").toString());
					List<Map<String, Object>> list = baseDao
							.selectList("SELECT Q1709 type,Q1707 dayCnt FROM Q17 WHERE A0100 =" + map.get("A0100")
									+ " AND Q1709 = " + type + "", Map.class);
					String json = JsonUtil.obj2json(list);
					return "{\"result\":\"true\",\"data\":" + json + "}";
				} else {
					return new ResultMessage("false", "找不到" + E0127 + "对应的人员！").toString();
				}
			} catch (Exception e) {
				String str = e.getMessage();
				WriteIOstream.GetMessage("获取假期信息", "异常信息是: " + str);
				return "{\"result\":\"false\",\"message\":\"失败" + e.getMessage() + "\"}";
			}
		}
	}

	// 公共
	@Override
	public Map<String, Object> PersonnelBasic(String E0127) {
		Map<String, Object> map = baseDao.selectOne(
				"select a.A0100,A0101,A0127,A0199,A0107,C0101,E0122,E01A1,A01AH,E0127,b.A0405,b.A0410,b.A0435,(SELECT AZ304 FROM yksoft.UsrAz3 where a.a0100 =a0100) AZ304 from yksoft.UsrA01 a,yksoft.usra04 b where a.A0100= b.A0100(+) and a.E0127=000914 where E0127='"
						+ E0127 + "'",
				Map.class);
		return map;
	}

	@Override
	public Map<String, Object> personBydate(String E0127) {
		return baseDao.getDate(E0127);
	}
	// 公共

	public String calE1405(String E1403, int addMoth) {
		String E1405 = "";
		// String strDate = "2018-10-01";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(sdf.parse(E1403));
			// 增加對應月份
			c.add(Calendar.MONTH, addMoth);
			date = c.getTime();
			// System.out.println("Date结束日期+1 " +sdf.format(date));

			c.add(Calendar.DAY_OF_MONTH, -1);
			date = c.getTime();
			E1405 = sdf.format(date);
			// System.out.println("Date结束日期+1 " +sdf.format(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return E1405;
	}

	/**
	 * @param A0195List
	 *            包含A0195的子串
	 * @param max
	 *            目前已经A0195后缀最大值,若还没有判断 赋值为0
	 */
	private int getA0195EndNum(List<LinkedHashMap<Object, Object>> A0195List, int max, String A0195) {
		for (int i = 0; i < A0195List.size(); i++) {
			// System.out.println(A0195List.get(i).get("A0195"));
			// 获取单个子串 如 san.zhang
			String A0195temp = (String) A0195List.get(i).get("A0195");
			int A0195Length = A0195.length();
			// 获取同子串后边的数字或字母
			String A0195EndString = A0195temp.substring(A0195Length);
			// 如果子串与A0195不相等
			if (A0195EndString.length() > 0) {
				// 判断A0195后第一个字符是否为数字
				char firstChar = A0195EndString.charAt(0);
				int numTemp = (int) firstChar - 48;
				if (numTemp >= 0 && numTemp <= 9) {
					// A0195后第一个字符为数字,获取后边数字取最大值
					int endNum = Integer.valueOf(A0195EndString);
					if (max < endNum) {
						max = endNum;
					}
				}
			}

		}
		System.out.println(max + "max");
		return max;
	}
}