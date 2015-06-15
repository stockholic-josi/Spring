package com.taxholic.core.enums;

import java.util.Comparator;

public class EnumPriorityComparator<T extends DisplayEnum> implements Comparator<T> {

    @Override
    public int compare(T o1, T o2) {
        return new Integer(o1.getPriority()).compareTo(new Integer(o2.getPriority()));
    }

}
