package com.yun.demo.config;

public enum DataSourceEnum {

    /**
     * 数据库1
     */
    DB1("db1"),
    /**
     * 数据库2
     */
    DB2("db2");

    private String value;

    DataSourceEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
