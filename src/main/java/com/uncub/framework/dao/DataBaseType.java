package com.uncub.framework.dao;

public enum DataBaseType {
    MYSQL("mysql"),
    ORACLE("oracle"),
    DB2("db2");

    private String name;
    @Override
    public String toString() {
        return name;
    }

    DataBaseType(String name){
        this.name = name;
    }
}
