/**============================================================
 * 版权： 
 * 包： com.after90s.project.web.Authorization.baidu.controller
 * 修改记录：
 * 日期                作者           内容
 * =============================================================
 * 2019年7月26日       LJW        
 * ============================================================*/

package com.after90s.project.web.Authorization.baidu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * <p>
 * TODO 页面跳转
 * </p>
 *
 * @author LJW
 * @version 2019年7月26日
 */
@Controller
@RequestMapping("baidu/")
public class PageBaiduController {
	@GetMapping("iframe")
	public String getdemoPage() {
		return "baidu/iframeDemo";
	}

	@GetMapping("index")
	public String getBaiduIndexPage() {
		return "baidu/index";
	}

	@GetMapping("accesstoken")
	public String getaccesstokenPage() {
		return "baidu/accesstoken";
	}

	@GetMapping("result")
	public ModelAndView getresultPage() {
		ModelAndView mvAndView=new ModelAndView();
		mvAndView.setViewName("baidu/result");
		mvAndView.addObject("result", "我是测试数据");
		return mvAndView;
	}

}
