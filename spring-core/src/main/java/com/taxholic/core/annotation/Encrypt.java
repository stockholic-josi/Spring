package com.taxholic.core.annotation;

import java.lang.annotation.ElementType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 암복호화 대상 Field에 어노테이션을 추가한다.
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Encrypt{

	public static String NULL = "_NULL";

	String Algorithm() default NULL;
}
