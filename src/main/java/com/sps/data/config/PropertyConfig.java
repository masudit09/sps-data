package com.sps.data.config;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Properties;


public class PropertyConfig {
	public static long lastModified = 0;
	public static Properties prop = null;

	public static void load() {
		try {
			File file = new File("db.properties");
			if(prop == null) {
				prop = new Properties();
			}
			if(file.lastModified() > lastModified) {
				System.out.println("Loading Properties ........");
				lastModified = file.lastModified();
				FileInputStream inputStream = new FileInputStream(file);
				prop.clear();
				prop.load(inputStream);
				inputStream.close();
				System.out.println("Loading Complete........");
			}

			//inputStream.
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	public static Object getProperties(String key) {
		load();
		return prop.get(key);
	}


	public static String getStringProperties(String key) {
		load();
		return prop.get(key).toString();
	}

	public static String getProperties(String key, String deString) {
		load();
		return prop.getProperty(key, deString);
	}
}
