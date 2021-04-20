package com.show.tree.db;

import com.show.tree.domain.File;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DBDao {

    private Connection connection;
    private Statement statement;

    public DBDao() {
        this.connection = DBConnection.getConnection();
        try {
            this.statement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<File> getAllFiles() {
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM files");
            return mapFiles(resultSet);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }

    private List<File> mapFiles(ResultSet resultSet) throws SQLException {
        List<File> files = new ArrayList<>();
        while (resultSet.next()) {
            File file = getFile(resultSet);
            files.add(file);
        }
        return files;
    }

    private File getFile(ResultSet resultSet) throws SQLException {
        File file = new File();
        file.setId((Integer) resultSet.getObject("record_id"));
        file.setParentId((Integer) resultSet.getObject("parent_id"));
        file.setName(resultSet.getString("name"));
        return file;
    }
}
