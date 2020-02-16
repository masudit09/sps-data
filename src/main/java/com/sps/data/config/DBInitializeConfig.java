package com.sps.data.config;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;


public class DBInitializeConfig {


	private static DataSource dataSource;
	private static Connection connection;

	
	public static void init() {
		//dataSource = DBConfig.getDataSource();
		try {
			connection = dataSource.getConnection();
		}  catch (Exception ex) {
			ex.printStackTrace();
		}

	}


	public static void main1(String a[]) {
		String result = MysqlDB.execute("sections");
		System.out.print(result);

//		DBConfig.getDataSource();
//		try {
//			connection = dataSource.getConnection();
//		}  catch (Exception ex) {
//			ex.printStackTrace();
//		}

	}


}
