package com.hjsj.dao;

import java.lang.reflect.Field;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.Date;

/**
 * 数据库资源类
 * @version 1.0
 * @author LiuJH 2015-08
 */
public class JDBCResource {

	/**
	 * 打开数据库链接，默认连接池
	 * 
	 * @return Connection
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			return DriverManager.getConnection("jdbc:oracle:thin:@172.30.10.113:1521:orcl","yksoft","yksoft1919");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} 
	}

	/**
	 * 打开数据库链接，动态连接池
	 * 
	 * @param poolName
	 *            连接池名称
	 * @return Connection
	 * @throws SQLException
	 */
	public Connection getConnection(String poolName) throws SQLException {
		return DriverManager.getConnection("proxool."+poolName);
	}

	/**
	 * 释放数据库资源，增、删、改时用
	 * 
	 * @param ps
	 *            语句执行对象
	 * @param rs
	 *            结果集对象
	 * @throws SQLException
	 */
	public void releaseResource(PreparedStatement ps) throws SQLException {
		if (ps != null) {
			ps.close();
		}
	}
	
	/**
	 * 释放数据库资源，增、删、改时用
	 * @throws SQLException
	 */
	public void releaseResource(CallableStatement cs) throws SQLException {
		if (cs != null) {
			cs.close();
		}
	}
	
	/**
	 * 释放数据库资源，查询时用
	 * 
	 * @param ps
	 *            语句执行对象
	 * @param rs
	 *            结果集对象
	 * @throws SQLException
	 */
	public void releaseResource(Statement st)
			throws SQLException {
		if (st != null) {
			st.close();
		}
	}

	/**
	 * 释放数据库资源，查询时用
	 * 
	 * @param ps
	 *            语句执行对象
	 * @param rs
	 *            结果集对象
	 * @throws SQLException
	 */
	public void releaseResource(PreparedStatement ps, ResultSet rs)
			throws SQLException {
		if (ps != null) {
			ps.close();
		}
		if (rs != null) {
			rs.close();
		}
	}
	
	/**
	 * 释放数据库资源，查询时用
	 * @throws SQLException
	 */
	public void releaseResource(CallableStatement cs, ResultSet rs)
			throws SQLException {
		if (cs != null) {
			cs.close();
		}
		if (rs != null) {
			rs.close();
		}
	}
	
	/**
	 * 释放数据库资源，查询时用
	 * 
	 * @param ps
	 *            语句执行对象
	 * @param rs
	 *            结果集对象
	 * @throws SQLException
	 */
	public void releaseResource(Statement st, ResultSet rs)
			throws SQLException {
		if (st != null) {
			st.close();
		}
		if (rs != null) {
			rs.close();
		}
	}

	/**
	 * 关闭数据库链接
	 * 
	 * @param conn
	 *            数据连接对象
	 */
	public void closeConnection(Connection conn) {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public <T> void setValue(T t, Field f, Object value) throws IllegalAccessException {
		if (null == value)
			return;
		String v = value.toString();
		String n = f.getType().getName();
		if ("java.lang.Byte".equals(n) || "byte".equals(n)) {
			f.set(t, Byte.parseByte(v));
		} else if ("java.lang.Short".equals(n) || "short".equals(n)) {
			f.set(t, Short.parseShort(v));
		} else if ("java.lang.Integer".equals(n) || "int".equals(n)) {
			f.set(t, Integer.parseInt(v));
		} else if ("java.lang.Long".equals(n) || "long".equals(n)) {
			f.set(t, Long.parseLong(v));
		} else if ("java.lang.Float".equals(n) || "float".equals(n)) {
			f.set(t, Float.parseFloat(v));
		} else if ("java.lang.Double".equals(n) || "double".equals(n)) {
			f.set(t, Double.parseDouble(v));
		} else if ("java.lang.String".equals(n)) {
			f.set(t, value.toString());
		} else if ("java.lang.Character".equals(n) || "char".equals(n)) {
			f.set(t, (Character) value);
		} else if ("java.lang.Date".equals(n)) {
			f.set(t, new Date(((java.sql.Date) value).getTime()));
		} else if ("java.lang.Timer".equals(n)) {
			f.set(t, new Time(((java.sql.Time) value).getTime()));
		} else if ("java.sql.Timestamp".equals(n)) {
			f.set(t, (java.sql.Timestamp) value);
		} else {
			System.out.println("SqlError：暂时不支持此数据类型，请使用其他类型代替此类型！");
		}
	}

}
