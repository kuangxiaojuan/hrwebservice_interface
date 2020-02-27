package dhr.provider.vo;

import java.util.List;
import java.util.Map;

public class UpdateVO {

	private String tableName;

	private String tableKey;
	
	private String[] tableKeys;

	private Map<String, Object> obj;

	private List<Map<String, Object>> list;

	public UpdateVO() {

	}

	public UpdateVO(String tableName, String tableKey, Map<String, Object> obj) {
		this.tableName = tableName;
		this.tableKey = tableKey;
		this.obj = obj;
	}
	
	public UpdateVO(String tableName, String[] tableKeys, Map<String, Object> obj) {
		this.tableName = tableName;
		this.tableKeys = tableKeys;
		this.obj = obj;
	}

	public UpdateVO(String tableName, String tableKey, List<Map<String, Object>> list) {
		this.tableName = tableName;
		this.tableKey = tableKey;
		this.list = list;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableKey() {
		return tableKey;
	}

	public void setTableKey(String tableKey) {
		this.tableKey = tableKey;
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

	public String[] getTableKeys() {
		return tableKeys;
	}

	public void setTableKeys(String[] tableKeys) {
		this.tableKeys = tableKeys;
	}
}
