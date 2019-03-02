package com.zjyk.repair.modle.bean;


public enum RPYesOrNoEnum implements BaseEnum {

	NONE("none"),
	YES("yes"),
	NO("no");

    private final String key;

    RPYesOrNoEnum(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return key;
    }
}

