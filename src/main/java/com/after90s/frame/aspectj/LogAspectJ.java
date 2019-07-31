/**============================================================
 * 版权： 
 * 包： com.after90s.frame.aspectj
 * 修改记录：
 * 日期                作者           内容
 * =============================================================
 * 2019年7月31日       lijiawen        
 * ============================================================*/

package com.after90s.frame.aspectj;

import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

/**
 * <p>TODO Log注解切面操作相关</p>
 *
 *
 * @author lijiawen
 * @version 2019年7月31日
 */
@Aspect
@Component
@EnableAsync
public class LogAspectJ {
	private static final Logger log = LoggerFactory.getLogger(LogAspectJ.class);
}
