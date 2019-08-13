/**============================================================
 * 版权： 
 * 包： com.after90s.frame.aspectj
 * 修改记录：
 * 日期                作者           内容
 * =============================================================
 * 2019年7月31日       lijiawen        
 * ============================================================*/

package com.after90s.frame.aspectj;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import com.after90s.common.annotation.Log;
import com.after90s.common.utils.ShiroUtils;
import com.after90s.project.web.user.entity.UserEntity;

/**
 * <p>
 * TODO Log注解切面操作相关 可以把重要的操作保存到数据库
 * </p>
 *
 *
 * @author AINY
 * @version 2019年7月31日
 */
@Aspect // 作用是把当前类标识为一个切面供容器读
@Component
@EnableAsync // 启用异步任务
public class LogAspectJ {
	private static final Logger log = LoggerFactory.getLogger(LogAspectJ.class);

	/*
	 * 配置织入点 也可以切入包、方法，本次切入的是自定义的注解
	 * 
	 * @Pointcut("execution(* com.after90s.Student.getName(..))")
	 * 
	 * @Pointcut("execution(* com.after90s.*.*(..))")
	 */
	@Pointcut("@annotation(com.after90s.common.annotation.Log)")
	/**
	 * signature
	 *  void
	 */
	public void logCutPoint() {
	}

	/**
	 * 执行前 void
	 */
	@Before("logCutPoint()")
	public void beforeAdvice() {
		System.out.println("======================方法执行前===================");
	}

	/**
	 * 执行后 void
	 */
	@After("logCutPoint()")
	public void afterAdvice() {
		System.out.println("======================方法执行中========");
	}

	/**
	 * 方法返回对象前 void
	 */
	@AfterReturning(pointcut = "logCutPoint()", returning = "joinPoint")
	public void afterReturningAdvice(JoinPoint joinPoint) {
		handleLog(joinPoint, null);
		System.out.println("======================方法执行后===================");
	}

	/**
	 * 出现异常时
	 * 
	 * @param joinPoint
	 * @param e         void
	 */
	@AfterThrowing(value = "logCutPoint()", throwing = "e")
	public void doAfter(JoinPoint joinPoint, Exception e) {
		handleLog(joinPoint, e);
		System.out.println("======================方法出现异常===================");
	}

	/**
	 * 异步操作记录日志
	 * 
	 * @param joinPoint
	 * @param e         void
	 */
	@Async
	private void handleLog(final JoinPoint joinPoint, final Exception e) {
		// 获得注解
		Log controllerLog = getMyAnnotationLog(joinPoint);
		if (controllerLog == null) {
			return;
		}
		// 获取当前的用户
		UserEntity currentUser = ShiroUtils.getUser();
//       OperLog operLog = new OperLog();
//       operLog.setStatus(UserConstants.NORMAL);
		// 请求的地址
		String ip = ShiroUtils.getIp();
		// 设置方法民名称和类明名称
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		if (currentUser != null) {
			log.info("==================" + currentUser.getLoginName() + "操作：" + className + "." + methodName);
			log.info("==================ip" + ip);
		}
		if (e != null) {
			log.info("===================" + e.getMessage() + "操作：" + className + "." + methodName);
			log.info("==================ip" + ip);
		}
	}

	/**
	 * 获取注解 没有则返回null
	 * 
	 * @param joinPoint
	 * @return Log
	 */
	private Log getMyAnnotationLog(JoinPoint joinPoint) {
		// 获取切点签名
		Signature signature = joinPoint.getSignature();
		// 方法签名
		MethodSignature methodSignature = (MethodSignature) signature;
		// 方法
		Method method = methodSignature.getMethod();
		if (method != null && method.getAnnotation(Log.class) != null) {
			return method.getAnnotation(Log.class);
		}
		return null;
	}
}
