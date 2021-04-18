package com.show.tree.service;

import com.show.tree.db.DBDao;
import com.show.tree.domain.File;

import java.util.List;

public class DataService {

    private DBDao dbDao;

    public DataService() {
        dbDao = new DBDao();
    }

    public List<File> getAllFiles() {
        return dbDao.getAllFiles();
    }
}
