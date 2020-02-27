package dhr.ldap.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;

import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import dhr.ldap.LdapConnect;
import dhr.provider.BaseDao;
import dhr.utils.LdapUtils;
import dhr.utils.WriteIOstream;

@Controller
public class LdapController {
	
	@Resource BaseDao dao;
	
	/**
	 * 每天定时12点 19点 23点执行
	 * @throws ParseException 
	 */

//	@Scheduled(cron = "0/5 * * * * ?")
	
	@Scheduled(cron="0 0 12,19,23 * * ? ") 
	public void synOrg() throws ParseException{
		//同步组织架构
		try {
//			System.out.println("1004");
			test();
			test2();
			test3();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	@RequestMapping(value="test",method=RequestMethod.GET)
	@ResponseBody
	public String test(){
		//获取OU的值  
		//OU 更新为当前的codeitemid的OU
		//遍历orgid，得到OU值，更新对应orgid的OU值
//		List<Map<String,String>> codeitemidList = dao.selectList("SELECT CODEITEMID FROM ORGANIZATION WHERE CODESETID IN('UN','UM')", Map.class);
//		
//		for (Map<String,String> codeitemidMap : codeitemidList) {
//			String codeitemid = codeitemidMap.get("CODEITEMID");
//			String ou = dao.getOU(codeitemid);
//			dao.excute("UPDATE ORGANIZATION SET OU='"+ou+"' WHERE CODEITEMID='"+codeitemid+"'");
//		}
		String sql = "update organization set OU=NULL";
		dao.excute(sql);
		
		sql= "UPDATE organization SET OU = 'OU=' || CODEITEMDESC WHERE GRADE = 1";
		dao.excute(sql);
		
		sql= "UPDATE organization u "
				+ "SET OU =  'OU=' || CODEITEMDESC || ',' || (SELECT o.ou FROM ORGANIZATION o WHERE o.CODEITEMID = u.PARENTID) "
				+ "WHERE GRADE = 2 AND codesetid in ('UN','UM')";
		dao.excute(sql);
		sql= "UPDATE organization u "
				+ "SET OU =  'OU=' || CODEITEMDESC || ',' || (SELECT o.ou FROM ORGANIZATION o WHERE o.CODEITEMID = u.PARENTID) "
				+ "WHERE GRADE = 3 AND codesetid in ('UN','UM')";
		dao.excute(sql);
		sql="UPDATE organization u "
				+ "SET OU =  'OU=' || CODEITEMDESC || ',' || (SELECT o.ou FROM ORGANIZATION o WHERE o.CODEITEMID = u.PARENTID) "
				+ "WHERE GRADE = 4 AND codesetid in ('UN','UM')";
		dao.excute(sql);
		sql= "UPDATE organization u "
				+ "SET OU =  'OU=' || CODEITEMDESC || ',' || (SELECT o.ou FROM ORGANIZATION o WHERE o.CODEITEMID = u.PARENTID) "
				+ "WHERE GRADE = 5 AND codesetid in ('UN','UM')";
		dao.excute(sql);
		sql= "UPDATE organization u "
				+ "SET OU =  'OU=' || CODEITEMDESC || ',' || (SELECT o.ou FROM ORGANIZATION o WHERE o.CODEITEMID = u.PARENTID) "
				+ "WHERE GRADE = 6 AND codesetid in ('UN','UM')";
		dao.excute(sql);
		
		//查询组织架构信息
		List<Map<String,String>> list = dao.selectList(
			"SELECT OU,OLEOU,CODEITEMDESC,CODEITEMID,CORCODE DEPARTMENT,A0000,"
			+ "START_DATE,END_DATE FROM ORGANIZATION WHERE END_DATE>SYSDATE AND "
			+ "CODESETID IN('UN','UM') AND OU IS NOT NULL ORDER BY CODEITEMID", 
			Map.class
		);
		//该List用于记录旧的组织架构
		List<Map<String,Object>> oleOuList = new ArrayList<>();
		
		LdapConnect ss = LdapConnect.getInstance();
		DirContext dc = ss.getDirContext();
		for (Map<String,String> map : list) {
			Map<String,Object> attrMap = new HashMap<>();
			attrMap.put("ou", map.get("CODEITEMDESC"));	
			
			//当前OU
			String ou = map.get("OU");
			System.out.println(ou);
			//旧OU  ldat controller   同步人员       jdbc 
			String oleou = map.get("OLEOU");
			
			//如果oleou==null，执行新建组织单元的操作,这句话重点 
			if (oleou == null) {
				WriteIOstream.GetwritetoJson("AD组织新增:", map.get("CODEITEMDESC"));
				System.out.println("组织新增："+ map.get("CODEITEMDESC"));
				//OU=深圳市卓越物业管理股份有限公司,DC=fhsj,DC=com
				//OU=卓越物业总部,OU=深圳市卓越物业管理股份有限公司,DC=fhsj,DC=com
				//OU=管理层,OU=卓越物业总部,OU=深圳市卓越物业管理股份有限公司,DC=fhsj,DC=com
				//DN=刘俊宏,OU=管理层,OU=卓越物业总部,OU=深圳市卓越物业管理股份有限公司,DC=fhsj,DC=com
				
//				LdapUtils.createOrg(map.get("CODEITEMDESC"), map.get("OU")+","+LdapUtils.DC, dc);
				LdapUtils.createOrg(attrMap, map.get("OU")+","+LdapUtils.DC, dc);
				//LdapUtils.createOrgTest(map.get("CODEITEMDESC"),map.get("DEPARTMENT"), map.get("OU")+","+LdapUtils.DC, dc);
				//同步成功后，记录当前OU，用于更新OLEOU字段
				Map<String, Object> oleOu = new HashMap<>();
				oleOu.put("CODEITEMID", map.get("CODEITEMID"));
				oleOu.put("OLEOU", map.get("OU"));
				oleOuList.add(oleOu);
			}else if(ou.equals(oleou)){		//OU相等，更新其他属性信息
				continue;
//				LdapUtils.modifyOrg(map.get("OU")+","+LdapUtils.DC, attrMap, dc);	
//				System.out.println("执行成功！");
			}else{							//否则执行更新操作
				WriteIOstream.GetwritetoJson("AD组织更新:", map.get("CODEITEMDESC"));
				System.out.println("组织更新："+ map.get("CODEITEMDESC"));
				LdapUtils.modifyName(oleou, ou, dc);
				//同步成功后，记录当前OU，用于更新OLEOU字段
				Map<String, Object> oleOu = new HashMap<>();
				oleOu.put("CODEITEMID", map.get("CODEITEMID"));
				oleOu.put("OLEOU", map.get("OU"));
				oleOuList.add(oleOu);
			}
			
		}
		ss.dcClose(dc);
		
		if (oleOuList.size() > 0) {
			dao.updateBatch("ORGANIZATION", "CODEITEMID", oleOuList);
		}
		
		
		return "test";
	}
	
	@RequestMapping(value="test2",method=RequestMethod.GET)
	@ResponseBody
	public String test2() throws NamingException{
		
		//查询组织架构信息
		List<CaseInsensitiveMap> list = dao.selectList(
			"select o2.ou ou,o1.codeitemdesc company,o2.codeitemdesc department,o3.codeitemdesc title,A0195 sAMAccountName,a0101 cn,a0101 displayName,a0107 pager,C0104 mobile,A0195||'@fhsj.com' userPrincipalName,A0195||'@fhsj.com' mail,E0127 employeeNumber,to_char(A01AH,'YYYY-MM-DD') gidNumber,o3.corcode from yksoft.usra01 a left join yksoft.organization o1 on a.b0110=o1.codeitemid left join yksoft.organization o2 on a.e0122=o2.codeitemid left join yksoft.organization o3 on a.e01a1=o3.codeitemid order by a.a0000", 
			CaseInsensitiveMap.class
		);
		
		LdapConnect ss = LdapConnect.getInstance();
		DirContext dc = ss.getDirContext();
		for (CaseInsensitiveMap map : list) {
			if (map.get("ou") == null) {
				continue;
			}
			String username = map.get("sAMAccountName").toString();
			
			String userDN = "CN="+map.get("cn")+","+map.get("ou").toString()+","+LdapUtils.DC;
			System.out.println(userDN);
			Attributes attrs = LdapUtils.getUserDNByUserName(username, dc);
			
			Map<String, Object> attrMap = new HashMap<String, Object>();
			attrMap.put("company", map.get("company"));
			attrMap.put("department", map.get("department"));
			attrMap.put("title", map.get("title"));					//岗位
			attrMap.put("displayName", map.get("displayName"));		//姓名
			attrMap.put("mobile", map.get("mobile"));
			attrMap.put("userPrincipalName", map.get("userPrincipalName"));
			attrMap.put("mail", map.get("mail"));
			
			//判断用户是否存在
			if (attrs != null) {
				Attribute attr = attrs.get("distinguishedName");
				String adDN = attr.get().toString();
				if (userDN.equalsIgnoreCase(adDN)) {
					WriteIOstream.GetwritetoJson("AD人员更新:", userDN);
					System.out.println("用户DN一致"+userDN);
					//修改信息
					LdapUtils.modifyUser(userDN, attrMap, dc);
				}else {
					WriteIOstream.GetwritetoJson("AD人员更新:", userDN);
					System.out.println("用户DN不一致"+userDN);
					//移动用户
					LdapUtils.modifyName(adDN, userDN, dc);
					//修改信息
					LdapUtils.modifyUser(userDN, attrMap, dc);
				}
			}else{
				WriteIOstream.GetwritetoJson("AD人员新增:", userDN);
				System.out.println("用户新增"+userDN);
				LdapUtils.createUser(userDN, attrMap, dc);
			}
			
		}
		ss.dcClose(dc);
		
//		//--禁用离职人员
//		//查询组织架构信息
//		System.out.println("开始禁用人员---");
//		List<CaseInsensitiveMap> retlist = dao.selectList(
//				"select o2.ou ou,o1.codeitemdesc company,o2.codeitemdesc department,o3.codeitemdesc title,A0195 sAMAccountName,a0101 cn,a0101 displayName,a0107 pager,C0104 mobile,A0195||'@fhsj.com' userPrincipalName,A0195||'@fhsj.com' mail,E0127 employeeNumber,to_char(A01AH,'YYYY-MM-DD') gidNumber,o3.corcode from yksoft.reta01 a left join yksoft.organization o1 on a.b0110=o1.codeitemid left join yksoft.organization o2 on a.e0122=o2.codeitemid left join yksoft.organization o3 on a.e01a1=o3.codeitemid order by a.a0000", 
//				CaseInsensitiveMap.class
//		);
//		LdapConnect retss = LdapConnect.getInstance();
//		DirContext retdc = retss.getDirContext();
//		for (CaseInsensitiveMap map : list) {
//			if (map.get("ou") == null) {
//				continue;
//			}
//			String username = map.get("sAMAccountName").toString();
//			
//			String userDN = "CN="+map.get("cn")+","+map.get("ou").toString()+","+LdapUtils.DC;
//			System.out.println(userDN);
//			Attributes attrs = LdapUtils.getUserDNByUserName(username, retdc);
//			
//			Map<String, Object> attrMap = new HashMap<String, Object>();
//			attrMap.put("company", map.get("company"));
//			attrMap.put("department", map.get("department"));
//			attrMap.put("title", map.get("title"));					//岗位
//			attrMap.put("displayName", map.get("displayName"));		//姓名
//			attrMap.put("mobile", map.get("mobile"));
//			attrMap.put("userPrincipalName", map.get("userPrincipalName"));
//			attrMap.put("mail", map.get("mail"));
//			
//			//判断用户是否存在
//			if (attrs != null) {
//				Attribute attr = attrs.get("distinguishedName");
//				String adDN = attr.get().toString();
//				System.out.println("禁用：userDN="+userDN);
//				//直接禁用
//				LdapUtils.disableUser(userDN, attrMap, retdc);
//			}else{
////				LdapUtils.createUser(userDN, attrMap, retdc);
//			}
//			
//		}
//		
//		retss.dcClose(retdc);
		
		return "test2";
	}
	

	@RequestMapping(value="test3",method=RequestMethod.GET)
	@ResponseBody
	public String test3() throws NamingException{
		
		//查询组织架构信息
				List<CaseInsensitiveMap> list = dao.selectList(
					"select o2.ou ou,o1.codeitemdesc company,o2.codeitemdesc department,o3.codeitemdesc title,A0195 sAMAccountName,a0101 cn,a0101 displayName,a0107 pager,C0104 mobile,A0195||'@fhsj.com' userPrincipalName,A0195||'@fhsj.com' mail,E0127 employeeNumber,to_char(A01AH,'YYYY-MM-DD') gidNumber,o3.corcode from yksoft.reta01 a left join yksoft.organization o1 on a.b0110=o1.codeitemid left join yksoft.organization o2 on a.e0122=o2.codeitemid left join yksoft.organization o3 on a.e01a1=o3.codeitemid where A0195 is not null order by a.a0000", 
					CaseInsensitiveMap.class
				);
				
		
		LdapConnect ss = LdapConnect.getInstance();
		DirContext dc = ss.getDirContext();
		System.out.println("list"+list.size());
		for (CaseInsensitiveMap map : list) {
			if (map.get("ou") == null) {
				continue;
			}
			String username = map.get("sAMAccountName").toString();
			
			String userDN = "CN="+map.get("cn")+","+map.get("ou").toString()+","+LdapUtils.DC;
			System.out.println(userDN);
			Attributes attrs = LdapUtils.getUserDNByUserName(username, dc);
			Map<String, Object> attrMap = new HashMap<String, Object>();
			attrMap.put("company", map.get("company"));
			attrMap.put("department", map.get("department"));
			attrMap.put("title", map.get("title"));					//岗位
			attrMap.put("displayName", map.get("displayName"));		//姓名
			attrMap.put("mobile", map.get("mobile"));
			attrMap.put("userPrincipalName", map.get("userPrincipalName"));
			attrMap.put("mail", map.get("mail"));
			
			//判断用户是否存在
			if (attrs != null) {
				Attribute attr = attrs.get("distinguishedName");
				String adDN = attr.get().toString();
				if (userDN.equalsIgnoreCase(adDN)) {
					System.out.println("禁用：用户DN一致");
					//修改信息
					attrMap.put("description", "禁用");
					attrMap.put("userAccountControl", "546");
					LdapUtils.modifyUser(userDN, attrMap, dc);
				}else {
					System.out.println("禁用：用户DN不一致");
					attrMap.put("description", "禁用");
					attrMap.put("userAccountControl", "546");
					LdapUtils.modifyUser(userDN, attrMap, dc);
				}
			}else{
				System.out.println("没有禁用");//基本都是在这儿进行禁用的
				WriteIOstream.GetwritetoJson("AD人员禁用:", userDN);
				attrMap.put("description", "禁用");
				attrMap.put("userAccountControl", "546");
				LdapUtils.modifyUser(userDN, attrMap, dc);
			}
			
		}
		ss.dcClose(dc);
		
		return "test3";
	}
	
}
