package com.sps.data.config;


import com.sps.data.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MysqlDB {

	static Connection conn = null;
	public static void connect() {
		try {
			// create a connection to the database
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(PropertyConfig.getStringProperties("mysql.url"),
					PropertyConfig.getStringProperties("mysql.user"), PropertyConfig.getStringProperties("mysql.password"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static String execute(String tableName) {
		if (conn == null) {
			connect();
		}
		try {
			Statement statement = conn.createStatement();
			//resultSet = statement.executeQuery("select LastName from employees limit 3;");
			statement.execute("use " + PropertyConfig.getStringProperties("mysql.database"));
			String query = "select * from "+tableName;
			ResultSet resultSet = statement.executeQuery(query + ";");
			String results = getMapFromResultSet(resultSet, tableName);
			statement.close();
			resultSet.close();
			return results;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static String getMapFromResultSet(ResultSet resultSet, String tableName) {
		String result = "INSERT INTO "+ tableName +"\n(";
		try {
			ResultSetMetaData rsmd = resultSet.getMetaData();
			int colCount = rsmd.getColumnCount();

			for (int i = 1; i <= colCount; i++) {
				result+=rsmd.getColumnName(i)+",";
			}
			result = result.substring(0, result.length()-1);
			result+= ") \n VALUES ";
			String columnValue="";
			while (resultSet.next()) {
				String[] values = new String[colCount];

				for (int i = 1; i <= colCount; i++) {
					if(resultSet.getObject(i) != null){
						columnValue = resultSet.getObject(i).toString();
					} else {
						columnValue = "";
					}
					if (Util.isNumeric(columnValue)) {
						values[i - 1] = columnValue;
					} else {
						values[i - 1] = "\"" + columnValue + "\"";
					}
				}
				result+= "("+String.join(",",values)+"),\n";
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		result = result.substring(0, result.length()-2);
		return result;
	}

}
