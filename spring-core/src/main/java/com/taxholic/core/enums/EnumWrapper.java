package com.taxholic.core.enums;

import java.util.List;


import com.google.common.collect.Lists;

public class EnumWrapper<T extends DisplayEnum> {

    @SuppressWarnings("rawtypes")
    Class enumType;

    @SuppressWarnings("rawtypes")
    public EnumWrapper(Class enumType) {
        this.enumType = enumType;
    }

    @SuppressWarnings("unchecked")
    public List<T> sort() {
        List<T> list = Lists.newArrayList();
        for (T e : (T[])enumType.getEnumConstants()) {
            list.add(e.getPriority(), e);
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    public T get(String name) {
        return (T)Enum.valueOf(enumType, name);
    }
}
