/**============================================================
 * 版权： 
 * 包： com.after90s.project.web.controller
 * 修改记录：
 * 日期                作者           内容
 * =============================================================
 * 2019年7月31日       lijiawen        
 * ============================================================*/

package com.after90s.project.web.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * <p>TODO 类描述</p>
 *
 *
 * @author lijiawen
 * @version 2019年7月31日
 */
@Controller
public class LoginController {
//	@Autowired
//	private UserServiceImpl userService;
	
	@PostMapping("login")
	public String login(String username, String password, Map<String, Object> map, HttpSession session) {
		System.out.println(username + "---" + password);
		// 获得当前Subject
		Subject currentUser = SecurityUtils.getSubject();
		// 验证用户是否验证，即是否登录
		if (!currentUser.isAuthenticated()) {
			String msg = "";
			// 把用户名和密码封装为 UsernamePasswordToken 对象
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
 
			// remembermMe记住密码    根据前段传过来的参数来决定
			token.setRememberMe(false);
			try {
				// 执行登录.
				currentUser.login(token);
 
				// 登录成功...
				return "redirect:/index";
			} catch (IncorrectCredentialsException e) {
				msg = "登录密码错误";
				System.out.println("登录密码错误!!!" + e);
			} catch (ExcessiveAttemptsException e) {
				msg = "登录失败次数过多";
				System.out.println("登录失败次数过多!!!" + e);
			} catch (LockedAccountException e) {
				msg = "帐号已被锁定";
				System.out.println("帐号已被锁定!!!" + e);
			} catch (DisabledAccountException e) {
				msg = "帐号已被禁用";
				System.out.println("帐号已被禁用!!!" + e);
			} catch (ExpiredCredentialsException e) {
				msg = "帐号已过期";
				System.out.println("帐号已过期!!!" + e);
			} catch (UnknownAccountException e) {
				msg = "帐号不存在";
				System.out.println("帐号不存在!!!" + e);
			} catch (UnauthorizedException e) {
				msg = "您没有得到相应的授权！";
				System.out.println("您没有得到相应的授权！" + e);
			} catch (Exception e) {
				System.out.println("出错！！！" + e);
			}
			map.put("msg", msg);
			return "/loginFial";
		}else {
			currentUser.logout();
		}
		// 已经登录，重定向到LoginSuccess.action
		return "redirect:/index";
 
	}
	/**
	 * 注销用户
	 * 
	 * @return String
	 */
	@GetMapping("loginOut")
	public String loginOut() {
		Subject currUser = SecurityUtils.getSubject();
		if(currUser!=null) {
			currUser.logout();
		}
	return "redirect:index";
	
}
}
