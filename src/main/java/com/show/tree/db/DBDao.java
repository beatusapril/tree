package com.show.tree.db;

import com.show.tree.domain.File;

import java.sql.*;
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

    public void create(File file) {
        try {
            String sql = "INSERT INTO (record_id, parent_id, name) VALUES (?, ?, ?)";
            PreparedStatement statement = getPreparedStatement(file, sql);
            statement.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private PreparedStatement getPreparedStatement(File file, String sql) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, file.getId());
        statement.setInt(2, file.getParentId());
        statement.setString(3, file.getName());
        return statement;
    }

    public void update(File file) {
        try {
            String sql = "UPDATE files (record_id, parent_id, name) VALUES (?, ?, ?)";
            PreparedStatement statement = getPreparedStatement(file, sql);
            statement.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void delete(Integer parentId, String name) throws SQLException {
        String sql = "DELETE FROM files WHERE parent_id = ? AND name = ? ";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, parentId);
        statement.setString(2, name);
        statement.executeQuery(sql);
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
