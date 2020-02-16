package com.sps.data.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;

@Configuration
public class DBConfig {

	@Bean("sqliteDbConnection")
	public  Connection getConnection() {
	        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
	        dataSourceBuilder.driverClassName("org.sqlite.JDBC");
	        dataSourceBuilder.url("jdbc:sqlite:"+PropertyConfig.getStringProperties("sqlite.file.name"));
			DataSource dataSource = dataSourceBuilder.build();
			Connection con = null;
			try {
				con = dataSource.getConnection();
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			return con;
	}
}
