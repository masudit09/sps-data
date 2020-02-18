package com.sps.data.controller;

import com.sps.data.config.MysqlDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@RestController
@RequestMapping("/api/db/migrate")
public class DbMigrateController {

    @Autowired
    private Connection sqliteDbConnection;

    @RequestMapping
    public String migrate() {

        //sqliteDbConnection.get
        String result = MysqlDB.execute("chapters ");
        updateDatabase(result, "chapters");
        result = MysqlDB.execute("sections ");
        updateDatabase(result, "sections");
        result = MysqlDB.execute("paragraphs ");
        updateDatabase(result, "paragraphs");
        return result;

    }

    private void updateDatabase(String result, String tableName) {
        try {
            Statement statement = sqliteDbConnection.createStatement();
            statement.execute("Delete from "+tableName);
            statement.executeUpdate(result);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
