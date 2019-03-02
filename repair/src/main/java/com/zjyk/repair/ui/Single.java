package com.zjyk.repair.ui;

public enum Single {
    INSTANCE;
    TestChild test;

    Single() {
        test = new TestChild();
    }

    public TestChild getInstance() {
        return test;
    }
}
