package com.show.tree.db;

import com.show.tree.domain.File;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBDao {

    private Connection connection;

    public List<File> getAllFiles()  {
        List<File> files = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM files");
            while (resultSet.next()) {
                File file = getFile(resultSet);
                files.add(file);
            }
        } catch (SQLException ex){

        }
        return files;
    }

    private File getFile(ResultSet resultSet) throws SQLException {
        File file = new File();
        file.setRecordId((Integer) resultSet.getObject("record_id"));
        file.setParentId((Integer) resultSet.getObject("parent_id"));
        file.setName(resultSet.getString("name"));
        return file;
    }
}
