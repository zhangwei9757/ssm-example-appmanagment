package cn.appsys.tools;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class ErrorLogger {
	private static final Logger logger = Logger.getLogger(ErrorLogger.class);

	@Pointcut("execution(* *cn.appsys.controller..*.*(..))")
	public void pointcut() {
	}

	@Around("pointcut()")
	public Object aroundLogger(ProceedingJoinPoint jp) throws Throwable {
		logger.info("=====>>>调用" + jp.getTarget().getClass().getName() + "的"
				+ jp.getSignature().getName() + "方法, 方法入参的参数是："
				+ Arrays.asList(jp.getArgs()));
		try {
			Object proceed = jp.proceed();
			logger.info("=====>>>调用" + jp.getTarget().getClass().getName()
					+ "的" + jp.getSignature().getName() + "方法, 方法返回值是："
					+ proceed);
			return proceed;
		} catch (Exception e) {
			logger.error("=====>>>" + jp.getSignature().getName() + "方法发生异常"
					+ e);
			throw e;
		} finally {
			logger.info("=====>>>" + jp.getSignature().getName() + "方法发生执行结束");
		}
	}
}
