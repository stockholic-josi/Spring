package com.taxholic.enums;

import com.taxholic.core.enums.DisplayEnum;


public enum TestEnum  implements  DisplayEnum {

    ABC("ABC", "Test1", 1, true),
    DEF("DEF", "Test2", 2, true),
    MSG("MSG", "board.fileMax", 3, true);

    private final String value;
    private final String label;
    private final int priority;
    private final boolean isDisplay;

    TestEnum(String value, String label, int priority, boolean isDisplay) {
        this.value = value;
        this.label = label;
        this.priority = priority;
        this.isDisplay = isDisplay;
    }
    
    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public int getPriority() {
        return this.priority;
    }

    @Override
    public boolean isDisplay() {
        return this.isDisplay;
    }
}