package com.taxholic.core.aspect;

import org.apache.commons.logging.Log;

import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import com.taxholic.core.authority.AuthDto;
import com.taxholic.core.authority.AuthUtil;

/*@Aspect를 이용하여 특정 클래스가 Aspect임을 나타낸다.*/
@Aspect
public class AccessLogging {
	
	public  Log log = LogFactory.getLog("accessLogging");
	
	/**
	 * @Pointcut을 이용하여 해당 Aspect를 적용할 부분을 정의한다
	 */
	@Pointcut("within(@org.springframework.stereotype.Controller *)")
//	@Pointcut("execution(* com.taxholic..controller.*Controller.*(..))")
    public void controllerBean() {}
	
	@Pointcut("execution(* *List(..))")
	public void listPointcut() {}
	
	@Pointcut("execution(* *Json(..))")
	public void jsonPointcut() {}
		
	@Pointcut("execution(* *Read(..))")
	public void readPointcut() {}
	
	@Pointcut("execution(* *Form(..))")
	public void formPointcut() {}
	

    /**
     * @Before를 이용하여 Before Advice를 정의한다. 
     * Before Advice인 beforeExecuteGetMethod()는 앞서 정의한 controllerBean()라는 Pointcut 전에 실행
     * @param joinPoint
     */
//    @Before("controllerBean() && (listPointcut() || jsonPointcut() || readPointcut() || formPointcut())")
    @Before("controllerBean()")
    public void accessLog(JoinPoint joinPoint) {
    	
    	StringBuffer sb = new StringBuffer();
    	AuthDto auth = AuthUtil.getUser();
    	
    	String id =(AuthUtil.getPrincipal().equals("GUS")) ? "noLogin" : auth.getUserId() ;
    	
	   	sb.append("ID : " + id + "\t");
	   	sb.append("Class : " + joinPoint.getTarget().getClass().toString().replace("class", "").trim() + "\t");
	   	sb.append("Method : " + joinPoint.getSignature().getName());
    	
    	if(log.isInfoEnabled()) { log.info(sb); }
    }
    
    /**
     * @AfterReturning을 이용하여 AfterReturning Advice를 정의
     *  Pointcut 후 실행
     * @param joinPoint
     */
//    @AfterReturning("controllerBean()")
//    public void afterExecuteGetMethod(JoinPoint joinPoint) {
//    	Class targetClass = joinPoint.getTarget().getClass();
//    	Signature signature = joinPoint.getSignature();
//    	String opName = signature.getName();
//
//    	System.out	.println("After(finally) Advice of PrintStringUsingAnnotation");
//    	System.out.println("***" + targetClass + "." + opName + "()" + "***");
//    }
}
