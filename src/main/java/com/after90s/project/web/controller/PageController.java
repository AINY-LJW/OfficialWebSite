/**============================================================
 * 版权： 久其软件 版权所有 (c) 2002 - 2012
 * 包： com.after90s.project.web.controller
 * 修改记录：
 * 日期                作者           内容
 * =============================================================
 * 2019年7月26日       lijiawen        
 * ============================================================*/

package com.after90s.project.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * <p>TODO 类描述</p>
 *
 * <p>Copyright: 版权所有 (c) 2002 - 2008<br>
 * Company: 久其</p>
 *
 * @author lijiawen
 * @version 2019年7月26日
 */
@Controller
public class PageController {
	@GetMapping(value= {"index","/","/index"})
	public String toHomePage(HttpServletRequest req) {
		return "index";
		
	}
	@GetMapping("login")
	public String toLoginPage() {
		return "login";
	}
}
