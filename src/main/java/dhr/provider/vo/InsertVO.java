package dhr.provider.vo;

import java.util.List;
import java.util.Map;

public class InsertVO {

	private String tableName;
	
	private String key;

	private Map<String, Object> obj;

	private List<Map<String, Object>> list;
	
	private String sql;
	

	public InsertVO() {

	}
	
	public InsertVO(String sql) {
		this.sql = sql;
	}

	public InsertVO(String tableName, Map<String, Object> obj) {
		this.tableName = tableName;
		this.obj = obj;
	}

	public InsertVO(String tableName, List<Map<String, Object>> list) {
		this.tableName = tableName;
		this.list = list;
	}
	
	public InsertVO(String tableName, String key, List<Map<String, Object>> list) {
		this.key = key;
		this.tableName = tableName;
		this.list = list;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Map<String, Object> getObj() {
		return obj;
	}

	public void setObj(Map<String, Object> obj) {
		this.obj = obj;
	}

	public List<Map<String, Object>> getList() {
		return list;
	}

	public void setList(List<Map<String, Object>> list) {
		this.list = list;
	}

	public String getSql() {
		
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
