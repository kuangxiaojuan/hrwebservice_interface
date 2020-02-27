package dhr.utils;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.PartialResultException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import dhr.ldap.LdapConnect;

public class LdapUtils {
	
	public static String REALM = LdapConnect.getInstance().getPt().getProperty("realm");
	public static String DC = "dc="+REALM.replaceAll("\\.", ",dc=");
	public static String INIT_PASSWORD = LdapConnect.getInstance().getPt().getProperty("initPassword");
	public static String ROOTOU = "DC=fhsj,DC=com";
	
	
	public static BasicAttribute getBattr(){
		BasicAttribute battr = new BasicAttribute("objectClass");
		battr.add("top");                                  
		battr.add("person");                     
		battr.add("organizationalPerson");
		battr.add("user");	
		return battr;
	}
	
	/**
	 * 检查是否存在改组织 DN
	 * @param ldapGroupDN 组织ＤＮ
	 * @return　存在：true 不存在：false
	 */
	public static boolean isOrg(String ldapGroupDN,DirContext dc){   
	    try {   
	        /*  
	         * 查找是否已经存在指定的OU条目  
	         * 如果存在，则打印OU条目的属性信息  
	         * 如果不存在，则程序会抛出NamingException异常，进入异常处理  
	         */  
	        dc.getAttributes(ldapGroupDN);
	        return true;   
	    } catch (NamingException e) {   
	        return false;
	    }   
	}
	
	/**
	 * 创建组织的方法
	 * @param orgName         要创建的组织名称
	 * @param ldapGroupDN     路径（OU地址）
	 * @param dc
	 * @return
	 */
	public static boolean createOrg(String orgName,String ou,DirContext dc){ 
		orgName = orgName.trim(); //去掉空格、不然无法新增
		// 创建objectclass属性
		Attribute objclass = new BasicAttribute("objectclass");
		objclass.add("top");
		objclass.add("organizationalunit");
		// 创建cn属性
		Attribute cn = new BasicAttribute("ou", orgName);
		//创建Attributes，并添加objectclass和cn属性
		Attributes attrs = new BasicAttributes();
		attrs.put(objclass);
		attrs.put(cn);
		//将属性绑定到新的条目上，创建该条目
		try {
			dc.bind(ou.trim(), null, attrs);
			return true;
		} catch (NamingException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 创建组织的方法
	 * @param orgName         要创建的组织名称
	 * @param ldapGroupDN     路径（OU地址）
	 * @param dc
	 * @return
	 */
	public static boolean createOrg(Map<String, Object> attrMap,String ou,DirContext dc){ 
		// 创建objectclass属性
		Attribute objclass = new BasicAttribute("objectclass");
		objclass.add("top");
		objclass.add("organizationalunit");
		
		//创建Attributes，并添加objectclass和cn属性
		Attributes attrs = new BasicAttributes();
		attrs.put(objclass);

		//遍历Map集合将值添加到attrs对象中
		Set<Entry<String, Object>> set = attrMap.entrySet();
		for (Iterator<Map.Entry<String, Object>> it = set.iterator(); it.hasNext();) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it.next();
			//System.out.println(entry.getKey() + "--->" + entry.getValue());
			//例如attrs.put("cn","张三")
			attrs.put(entry.getKey(),entry.getValue());
		}
		//将属性绑定到新的条目上，创建该条目
		try {
			dc.bind(ou.trim(), null, attrs);
			return true;
		} catch (NamingException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 修改组织名称
	 * @param oleOu	AD域路径
	 * @param newOu	HR新路径
	 * @param dc
	 * @return
	 */
	public static boolean modifyName(String oleOu,String newOu,DirContext dc) {
		try {				
			dc.rename(oleOu, newOu);
			return true;
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 根据账号、根路径获取人员路径
	 * @param org 组织
	 * @param loginId 账号
	 * @param dc LDAP链接
	 * @return ou 人员DN
	 */
	public static Attributes getUserDNByLoginId(String loginId,DirContext dc){
		SearchControls sc = new SearchControls();
		sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
		NamingEnumeration<SearchResult> results = null;
		try {
			//results = dc.search(DC, "sAMAccountName=" + loginId,sc);
			results = dc.search(DC, "postalCode=" + loginId,sc);
			SearchResult sr = (SearchResult) results.next();
			return sr.getAttributes();
		} catch (Exception e) {
			System.out.println(loginId);
			e.printStackTrace();
			return null;
		} 
	}
	
	/**
	 * 根据账号、根路径获取人员路径
	 * @param org 组织
	 * @param loginId 账号
	 * @param dc LDAP链接
	 * @return ou 人员DN
	 */
	public static Attributes getUserDNByUserName(String userName,DirContext dc){
		SearchControls sc = new SearchControls();
		sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
		NamingEnumeration<SearchResult> results = null;
		try {
			results = dc.search(DC, "sAMAccountName=" + userName,sc);
			SearchResult sr = (SearchResult) results.next();
			return sr.getAttributes();
		} catch (Exception e) {
			//e.printStackTrace();
			return null;
		} 
	}
	
	/** 根据中文名、所在部门OU判断该用户是否存在
	 * @param dept
	 * @param Name
	 * @param dc
	 * @return
	 */
	public static boolean isUserExistByName(String dept,String Name,DirContext dc) {
		SearchControls sc = new SearchControls();
		sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
		NamingEnumeration<?> results;
		try {
			results = dc.search(dept, "CN=" + Name, sc);
			System.out.println("CN=" + Name + dept);
			if (results != null && results.hasMoreElements())
				return true;
		} catch (PartialResultException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		}	
		return false;
	}
	
	/**
	 * 创建用户的方法
	 * @param dept       OU=海印集团,OU=海印,DC=highsun-test,DC=com
	 * @param attrMap    用户信息
	 * @param dc         ldap链接
	 * @return
	 */
	public static boolean createUser(String UserDN,Map<String, Object> attrMap,DirContext dc) {
		try {
			//将objectclass加到属性集合中 
			Attributes attrs = new BasicAttributes();		
			attrs.put(getBattr());	
			
			//遍历Map集合将值添加到attrs对象中
			Set<Entry<String, Object>> set = attrMap.entrySet();
			for (Iterator<Map.Entry<String, Object>> it = set.iterator(); it.hasNext();) {

				Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it.next();
				//System.out.println(entry.getKey() + "--->" + entry.getValue());
				
				//例如attrs.put("cn","张三")
				attrs.put(entry.getKey(),entry.getValue());
			}		
			String newQuotedPassword = "\""+LdapUtils.INIT_PASSWORD+"\"";
			System.out.println(newQuotedPassword);
	 		byte[] newUnicodePassword;
			try {
				newUnicodePassword = newQuotedPassword.getBytes("UTF-16LE");
				attrs.put("unicodePwd", newUnicodePassword);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			//账户默认为启用：66048永不过期
			//attrs.put("userAccountControl","66048");	
			//账户默认为必须修改密码
			attrs.put("userAccountControl","544");
			//执行创建用户的方法
			dc.createSubcontext(UserDN,attrs);
			return true;
		} catch (NamingException e) {
			e.printStackTrace();
			return false;

		}
	}
	
	/**
	 * 创建用户的方法
	 * @param dept       OU=海印集团,OU=海印,DC=highsun-test,DC=com
	 * @param attrMap    用户信息
	 * @param dc         ldap链接
	 * @return
	 */
	public static boolean createUserNoPwd(String UserDN,Map<String, Object> attrMap,DirContext dc) {
		try {
			//将objectclass加到属性集合中 
			Attributes attrs = new BasicAttributes();		
			attrs.put(getBattr());	
			
			//遍历Map集合将值添加到attrs对象中
			Set<Entry<String, Object>> set = attrMap.entrySet();
			for (Iterator<Map.Entry<String, Object>> it = set.iterator(); it.hasNext();) {

				Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it.next();
				//System.out.println(entry.getKey() + "--->" + entry.getValue());
				
				//例如attrs.put("cn","张三")
				attrs.put(entry.getKey(),entry.getValue());
			}		
			
			//账户默认为启用：66048永不过期
			//attrs.put("userAccountControl","66048");	
			//账户默认为必须修改密码
			attrs.put("userAccountControl","544");
			//执行创建用户的方法
			dc.createSubcontext(UserDN,attrs);
			return true;
		} catch (NamingException e) {
			e.printStackTrace();
			return false;

		}
	}
	
	/**
	 * 修改用户
	 * @param UserDN	用户路径	
	 * @param attrMap	用户属性
	 * @param dc
	 * @return
	 */
	public static boolean modifyUser(String UserDN, Map<String, Object> attrMap, DirContext dc) {		
		try {	
			Attributes attrs = new BasicAttributes();		
			attrs.put(getBattr());									
			//遍历Map集合将值添加到attrs对象中
			Set<Entry<String, Object>> set = attrMap.entrySet();
			for (Iterator<Map.Entry<String, Object>> it = set.iterator(); it.hasNext();) {
				Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it.next();
				if ("cn".equals(entry.getKey())) {
					continue;
				}
				attrs.put(entry.getKey(),entry.getValue());
			}
			dc.modifyAttributes(UserDN,DirContext.REPLACE_ATTRIBUTE, attrs);
			return true;
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 修改用户
	 * @param UserDN	用户路径	
	 * @param attrMap	用户属性
	 * @param dc
	 * @return
	 */
	public static boolean modifyUserNoPwd(String UserDN, Map<String, Object> attrMap, DirContext dc) {		
		try {	
			Attributes attrs = new BasicAttributes();		
			attrs.put(getBattr());									
			//遍历Map集合将值添加到attrs对象中
			Set<Entry<String, Object>> set = attrMap.entrySet();
			for (Iterator<Map.Entry<String, Object>> it = set.iterator(); it.hasNext();) {
				Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it.next();
				if ("cn".equals(entry.getKey()) || "ou".equals(entry.getKey())) {
					continue;
				}
				attrs.put(entry.getKey(),entry.getValue());
			}
			dc.modifyAttributes(UserDN,DirContext.REPLACE_ATTRIBUTE, attrs);
			return true;
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 修改组织
	 * @param UserDN	组织路径	
	 * @param attrMap	组织属性
	 * @param dc
	 * @return
	 */
	public static boolean modifyOrg(String ou, Map<String, Object> attrMap, DirContext dc) {		
		try {	
			
			// 创建objectclass属性
			//BasicAttribute objclass = new BasicAttribute("objectclass");
			//objclass.add("top");
			//objclass.add("organizationalunit");
			
			//创建Attributes，并添加objectclass和cn属性
			Attributes attrs = new BasicAttributes();
			//attrs.put(objclass);
			
			//遍历Map集合将值添加到attrs对象中
			Set<Entry<String, Object>> set = attrMap.entrySet();
			for (Iterator<Map.Entry<String, Object>> it = set.iterator(); it.hasNext();) {
				Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it.next();
				attrs.put(entry.getKey(),entry.getValue());
			}
			dc.modifyAttributes(ou,DirContext.REPLACE_ATTRIBUTE, attrs);
			return true;
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return false;
	}
	
//	//禁用
//	public static boolean disableUser(String UserDN, Map<String, Object> attrMap, DirContext dc) {
//		System.out.println("disableUser");
//		try {	
//			Attributes attrs = new BasicAttributes();		
//			attrs.remove("CN");
//			attrs.put("description", "禁用");
//			attrs.put("userAccountControl", "546");
//			dc.modifyAttributes(UserDN,DirContext.REPLACE_ATTRIBUTE, attrs);
//			return true;
//		} catch (NamingException e) {
//			e.printStackTrace();
//		}
//		return false;
//	}
	
}
