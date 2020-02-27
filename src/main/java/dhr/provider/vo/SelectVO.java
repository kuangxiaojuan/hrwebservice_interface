package dhr.provider.vo;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class SelectVO {

	private String sql;

	private Map<String, Object> param;
	
	private Object[] values;

	public SelectVO() {

	}

	public SelectVO(String sql) {
		this.sql = sql;
	}
	
	public SelectVO(String sql, Object[] values) {
		this.sql = sql;
		this.values = values;
	}

	public SelectVO(String sql, Map<String, Object> param) {
		StringBuilder bulider = new StringBuilder(sql);
		Iterator<?> it = param.entrySet().iterator();
		while (it.hasNext()) {
			Entry<?, ?> entity = (Entry<?, ?>) it.next();
			int index = bulider.indexOf("?");
			bulider.replace(index, index + 1, "#{" + entity.getKey() + "}");
		}
		this.sql = bulider.toString();
		this.param = param;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public Map<String, Object> getParam() {
		return param;
	}

	public void setParam(Map<String, Object> param) {
		this.param = param;
	}

	public Object[] getValues() {
		return values;
	}

	public void setValues(Object[] values) {
		this.values = values;
	}

}
