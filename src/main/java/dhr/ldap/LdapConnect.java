package dhr.ldap;

import java.io.IOException;
import java.io.InputStream;
import java.security.Security;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import dhr.utils.LdapUtils;


/**
 * 类名称：Ldap链接
 * 类描述：单例类，用于创建Ldap链接
 **/
public class LdapConnect {
	
	public Properties getPt() {
		return pt;
	}

	public void setPt(Properties pt) {
		this.pt = pt;
	}

	private Properties pt;				/**类描述：初始化属性文件**/
	static private LdapConnect cl;		/**唯一实例**/
	
	/**加载LDAP链接属性文件**/
	private LdapConnect(){
		InputStream is = getClass().getResourceAsStream("/ldap.properties");
		pt = new Properties();
		try {
			pt.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	/**唯一实例**/
	static synchronized public LdapConnect getInstance() {
		if(cl == null)
			cl = new LdapConnect();
		return cl;
	}
	
	/**打开LDAP链接**/
	public DirContext getDirContext(){
		
		//设置证书
		System.setProperty("javax.net.ssl.trustStore", pt.getProperty("keystore"));
		//System.setProperty("javax.net.ssl.keyStorePassword", "changeit");
		
		DirContext dirContext = null;
		Hashtable<String, String> ht = new Hashtable<String, String>();
		ht.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
		ht.put(Context.PROVIDER_URL, pt.getProperty("ldapServer"));
		ht.put(Context.SECURITY_AUTHENTICATION, "simple");
		ht.put(Context.SECURITY_PRINCIPAL,pt.getProperty("user") + "@" + pt.getProperty("realm"));
		ht.put(Context.SECURITY_CREDENTIALS, pt.getProperty("password"));
		ht.put(Context.SECURITY_PROTOCOL, "ssl");
		try {
			dirContext = new InitialDirContext(ht);
			System.out.println("链接打开……");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return dirContext;
	}	
	
	/**关闭LDAP链接**/
	public void dcClose(DirContext dc){
		if (dc != null) {
			try {
				dc.close();
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}	
	}
		
	public static void main(String[] args) throws NamingException {
		LdapConnect ss = LdapConnect.getInstance();
		DirContext dc = ss.getDirContext();
		//LdapUtils.createOrg("部门1", "OU=部门1,OU=泛海三江,DC=szfhsjdz,DC=com", dc);
//		Map<String, Object> attrMap = new HashMap<>();
//		attrMap.put("cn", "测试人员1");
//		attrMap.put("sAMAccountName", "username01");
//		attrMap.put("displayName", "测试人员2");
//		attrMap.put("userPrincipalName", "username01@" + LdapUtils.REALM);
//		attrMap.put("company", "泛海三江");
//		attrMap.put("department", "部门1");
//		LdapUtils.createUserNoPwd("CN=测试人员1,OU=部门1,OU=泛海三江,DC=szfhsjdz,DC=com", attrMap, dc);
//		Attributes attrs = LdapUtils.getUserDNByUserName("huan.long", dc);
//		if (attrs != null) {
//			Attribute attr = attrs.get("distinguishedName");
//			System.out.println(attr.get());
//		}
		
//		boolean isOrg = LdapUtils.isOrg("OU=测试架构1,OU=HR_TEST,OU=Users,OU=SJDZ,DC=fhsj,DC=com", dc);
//		System.out.println(isOrg);
//		
//		//如果组织单元不存在，执行创建组织单元操作
//		if (isOrg == false) {
			//LdapUtils.createOrg("测试架构1-1", "OU=测试架构1,OU=HR_TEST,OU=Users,OU=SJDZ,DC=fhsj,DC=com", dc);
//		}
		
		
//		Map<String, Object> attrMap = new HashMap<>();
//		attrMap.put("cn", "测试人员2");	
//		attrMap.put("sAMAccountName", "test222");
//		attrMap.put("displayName", "测试人员2");
//		attrMap.put("userPrincipalName", "test222@" + LdapUtils.REALM);
//		attrMap.put("company", "测试架构1");
//		attrMap.put("department", "测试架构2");
//		LdapUtils.createUserNoPwd("CN=测试人员2,OU=测试架构1-1,OU=测试架构1,OU=HR_TEST,OU=Users,OU=SJDZ,DC=fhsj,DC=com", attrMap, dc);
//		
		ss.dcClose(dc);
	}
	
	
	
	
}
