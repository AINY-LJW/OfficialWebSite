/**============================================================
 * 版权： 久其软件 版权所有 (c) 2002 - 2012
 * 包： com.after90s.common.annotation
 * 修改记录：
 * 日期                作者           内容
 * =============================================================
 * 2019年8月6日       lijiawen        
 * ============================================================*/

package com.after90s.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>TODO 类描述</p>
 *
 * <p>Copyright: 版权所有 (c) 2002 - 2008<br>
 * Company: 久其</p>
 *
 * @author lijiawen
 * @version 2019年8月6日
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /** 模块 */
    String title() default "";

    /** 功能 */
    String action() default "";

    /** 渠道 */
//    String channel() default "web";

    /** 是否保存请求的参数 */
//    boolean isSaveRequestData() default true;


}
