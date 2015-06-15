package com.taxholic.core.enums;

import java.util.Comparator;

public class EnumLabelComparator<T extends DisplayEnum> implements Comparator<T> {

    @Override
    public int compare(T o1, T o2) {
        return o1.getLabel().compareTo(o2.getLabel());
    }

}
