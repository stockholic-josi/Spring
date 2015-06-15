package com.taxholic.core.util;

import java.util.ArrayList;



import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Objects;
import com.taxholic.core.enums.DisplayEnum;
import com.taxholic.core.enums.EnumLabelComparator;
import com.taxholic.core.enums.EnumPriorityComparator;
import com.taxholic.core.enums.EnumWrapper;

public class EnumUtils {

    public static <E extends Enum<E> & DisplayEnum> String getLabel(E e) {
        return e.getLabel();
    }

    public static <T extends DisplayEnum> List<T> getEnumSortedByPriority(T[] params) {
        EnumPriorityComparator<T> c = new EnumPriorityComparator<T>();
        List<T> list = removeNonDisplayedValues(params);
        Collections.sort(list, c);
        return list;
    }

    public static <T extends DisplayEnum> List<T> getEnumSortedByLabel(T[] params) {
        EnumLabelComparator<T> c = new EnumLabelComparator<T>();
        List<T> list = removeNonDisplayedValues(params);
        Collections.sort(list, c);
        return list;
    }

    private static <T extends DisplayEnum> List<T> removeNonDisplayedValues(T[] params) {
        List<T> list = new ArrayList<T>();
        for (int i = 0; i < params.length; i++) {
            if (params[i].isDisplay()) {
                list.add(params[i]);
            }
        }
        return list;
    }

    /**
     * label to EnumValue
     * 
     * @param enumClass
     * @param targetLabel
     * @return
     */
    public static <E extends Enum<E> & DisplayEnum> E getValueByLabel(Class<?> enumClass, String targetLabel) {
        @SuppressWarnings("unchecked") E[] enums = (E[])enumClass.getEnumConstants();

        for (E e : enums) {
            DisplayEnum DisplayEnum = e;
            String label = DisplayEnum.getLabel();

            if (Objects.equal(targetLabel, label)) {
                return e;
            }
        }

        return null;
    }
    
    /**
     * value to EnumLabel
     * 
     * @param enumClass
     * @param targeValue
     * @return
     */
    public static <E extends Enum<E> & DisplayEnum> String getLabelByValue(Class<?> enumClass, String targeValue) {
        @SuppressWarnings("unchecked") E[] enums = (E[])enumClass.getEnumConstants();

        for (E e : enums) {
            DisplayEnum DisplayEnum = e;
            String value = (String) DisplayEnum.getValue();

            if (Objects.equal(targeValue, value)) {
                return e.getLabel();
            }
        }

        return null;
    }

    /**
     * EnumType.values() 를 파라미터로 받아 priority 순서로 정렬, isDisplay인 상태의 속성들을 Map<value, Label>으로 전달한다.
     *
     * @param EnumType.values()
     * @param optional 추가 옵션 생성 여부
     * @param optionLabel 추가 옵션 라벨명
     * @return Map<value, Label>
     */
    public static <E extends Enum<E> & DisplayEnum> Map<String, String> getOptions(E[] enumValues, boolean optional, String optionLabel) {

        List<E> list = getEnumSortedByPriority(enumValues);

        Map<String, String> optionMap = new HashMap<String, String>();

        if (optional) {
            optionMap.put("", optionLabel);
        }

        for (E type : list) {
            optionMap.put((String)type.getValue(), type.getLabel());
        }
        return optionMap;
    }

    /**
     * EnumType.values() 를 파라미터로 받아 priority 순서로 정렬, isDisplay인 상태의 속성들을 Map<value, Label>으로 전달한다.
     *
     * @param EnumType.values()
     * @return Map<
     */
    public static <E extends Enum<E> & DisplayEnum> Map<String, String> getOptions(E[] enumValues) {
        return getOptions(enumValues, false, null);
    }

    public static <T extends DisplayEnum> EnumWrapper<T> getWrapper(Class<T> enumType) {
        return new EnumWrapper<T>(enumType);
    }
}