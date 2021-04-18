package com.show.tree.domain;

public class File {
    private Integer id;
    private Integer recordId;
    private Integer parentId;
    private String name;

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public void setName(String name) {
        this.name = name;
    }
}
