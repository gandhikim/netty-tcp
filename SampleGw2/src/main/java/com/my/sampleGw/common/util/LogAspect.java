package com.my.sampleGw.common.util;

import org.aspectj.lang.JoinPoint;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LogAspect {

	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	private long startTime;
	private long endTime;
	
	@Pointcut("execution(* com.my.sampleGw.*.*.*.*.*Impl.*(..))")
	private void targetMethod() {};

	@Before("targetMethod()")
	public void before() {
		//log.info("LogAspect - before");
		startTime = System.currentTimeMillis();
	}

	@AfterReturning(pointcut = "execution(* com.my.sampleGw.*.*.*.*.*Impl.*(..))", returning = "retn")
	public void afterReturning(JoinPoint joinPoint, Object retn) {
		//log.info("LogAspect - afterReturning - " + retn.toString());
	}
	
	@After("targetMethod()")
	public void after(JoinPoint point) {
		endTime = System.currentTimeMillis();
		log.info("DurationTime : " + (endTime - startTime));
		/*
		log.info("LogAspect - after - " + point.getTarget().getClass().getSimpleName());
		log.info("LogAspect - after - " + point.getSignature().getName());
		for (Object item : point.getArgs()) {
			log.info("LogAspect - after - " + item);
		}
		*/
	}

	@Around("targetMethod()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		Object obj = point.proceed();
		/*
		log.info("LogAspect - around - " + point.getTarget().getClass().getSimpleName());
		log.info("LogAspect - around - " + point.getSignature().getName());
		for (Object item : point.getArgs()) {
			log.info("LogAspect - around - " + item);
		}
		*/
		return obj;
	}

	@AfterThrowing(pointcut = "targetMethod()", throwing = "exception")
	public void afterThrowing(JoinPoint point, Exception exception) throws Exception {
		log.error("LogAspect - afterThrowing - " + exception.toString());
	}

}
