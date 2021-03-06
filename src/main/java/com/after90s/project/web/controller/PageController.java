/**============================================================
 * 版权： 
 * 包： com.after90s.project.web.controller
 * 修改记录：
 * 日期                作者           内容
 * =============================================================
 * 2019年7月26日       lijiawen        
 * ============================================================*/

package com.after90s.project.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * <p>TODO 类描述</p>
 *
 * <p>
 * </p>
 *
 * @author lijiawen
 * @version 2019年7月26日
 */
@Controller
public class PageController {
	private static Logger logger = LoggerFactory.getLogger(PageController.class);
	@GetMapping(value= {"index","/","/index"})
	public String toHomePage(HttpServletRequest req) {
//		logger.info("进入首页");
//		logger.error("进入首页");
//		logger.debug("进入首页");
//		logger.warn("进入首页");
//		logger.trace("进入首页");
		return "index";
		
	}
	@GetMapping("login")
	public String toLoginPage() {
		return "login";
	}
}
