package com.taxholic.core.aspect;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import com.taxholic.core.annotation.Encrypt;
import com.taxholic.core.util.EncryptUtil;

/**
 * DAO Method의 Argument 및 return Object에 대하여 {@link Encrypt} 어노테이션이 붙은 필드를 찾아 암복호화를 수행한다.
 *
 */
@Configuration
@EnableAspectJAutoProxy
@Aspect
public class EncryptAspect {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Pointcut("execution(public * com.taxholic.core.dao..*Dao.*(..))")
	public void daoMethod() {}

	@Around("daoMethod()")
	public Object checkArgumentAndReturnObjectForEncryptAnnotationField(final ProceedingJoinPoint pjp) throws Throwable {

		Object[] args = pjp.getArgs();
		for (int i = 0; i < args.length; i++) {
			// DB 저장 전 암호화
			encrypt(args[i]);
		}

		Object returnObject = pjp.proceed(args);

		// 조회 시 복호화
		decrypt(returnObject);

		return returnObject;
	}
	
	/**
	 * @param model
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 */
	private void encrypt(Object model) throws IllegalAccessException, NoSuchFieldException {
		encryptOrDecrypt(model, true);
	}

	/**
	 * @param model
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 */
	private void decrypt(Object model) throws IllegalAccessException, NoSuchFieldException {
		encryptOrDecrypt(model, false);
	}
	
	/**
	 * @param model
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 */
	private void encryptOrDecrypt(Object model, boolean isEncrypt) throws IllegalAccessException, NoSuchFieldException {
		if (model == null || ClassUtils.isPrimitiveOrWrapper(model.getClass())) {
			return;
		}

		if (model instanceof List<?>) {
			List<?> list = (List<?>)model;

			for (Object o : list) {
				encryptOrDecrypt(o, isEncrypt);
			}

		} else {

			ArrayList<Field> encryptFieldList = toArrayList(model.getClass(), Encrypt.class);

			if (encryptFieldList != null && !encryptFieldList.isEmpty()) {
				logger.debug("/***************************************");
				logger.debug("@Encrypt : {}", isEncrypt ? "encrypt" : "decrypt");
				logger.debug("Target Model is [ {} ]", model);
				logger.debug("/***************************************");

				for (Field field : encryptFieldList) {

					field.setAccessible(true);
					String targetString = (String)field.get(model);

					if (targetString != null) {
						Encrypt encrypt = field.getAnnotation(Encrypt.class);
						logger.debug("@Encrypt Annotation Info : {}, {}, {}", encrypt.Algorithm(), encrypt.toString());

						try {
							if (isEncrypt) {
								field.set(model, EncryptUtil.encrypt(targetString));
							} else {
								field.set(model, EncryptUtil.decrypt(targetString));
							}
						} catch (EncryptionOperationNotPossibleException encryptionOperationNotPossibleException) {
							logger.debug("EncryptionOperationNotPossibleException : {}",
								encryptionOperationNotPossibleException.getMessage());
						}
					}
				}
			}
		}
	}


	/**
	 * 자신 및 상속받은 super class의 field에서 대상 어노테이션을 찾는다.
	 *
	 * @param targetClazz
	 * @param annotationClazz
	 * @return
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public ArrayList<Field> toArrayList(Class<?> targetClazz, Class annotationClazz) {
		Class<?> current = targetClazz;
		ArrayList<Field> targetFieldList = new ArrayList<Field>();
		targetFieldList.addAll(Arrays.asList(current.getDeclaredFields()));

		while (current.getSuperclass() != null) {
			targetFieldList.addAll(Arrays.asList(current.getSuperclass().getDeclaredFields()));
			current = current.getSuperclass();
		}

		ArrayList<Field> list = new ArrayList<Field>();

		for (Field field : targetFieldList) {
			if (field.getAnnotation(annotationClazz) != null)
				list.add(field);
		}

		return list;
	}
}
