/**============================================================
 * 版权： 
 * 包： com.after90s.frame.exception.exceptionCatch
 * 修改记录：
 * 日期                作者           内容
 * =============================================================
 * 2019年7月31日       lijiawen        
 * ============================================================*/

package com.after90s.frame.exception.exceptionCatch;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * TODO 异常捕获
 * </p>
 *
 *
 * @author lijiawen
 * @version 2019年7月31日
 */

@ControllerAdvice
public class AllExceptionCatch {
	/// 角色权限异常捕捉
	@ExceptionHandler(value = UnauthorizedException.class)
	@ResponseBody // 在返回自定义相应类的情况下必须有，这是@ControllerAdvice注解的规定
	public String roleException(UnauthorizedException e) {
		System.out.println("---------------------->" + e);
		return "角色权限不够！！！";
	}

	// 其它异常异常捕捉
	@ExceptionHandler(value = RedisConnectionFailureException.class)
	@ResponseBody // 在返回自定义相应类的情况下必须有，这是@ControllerAdvice注解的规定
	public String redisConnectionFailureException(Exception e) {
		System.out.println("---------------------->" + e);
		return "Redis连接出现异常！！！";
	}

	// 其它异常异常捕捉
	@ExceptionHandler(value = Exception.class)
	@ResponseBody // 在返回自定义相应类的情况下必须有，这是@ControllerAdvice注解的规定
	public String allException(Exception e) {
		System.out.println("---------------------->" + e);
		return "系統出现异常！！！";
	}

}
