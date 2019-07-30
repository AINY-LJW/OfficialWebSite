/**============================================================
 * 版权： 
 * 包： com.after90s.project.web.Authorization.baidu.controller
 * 修改记录：
 * 日期                作者           内容
 * =============================================================
 * 2019年7月26日       LJW        
 * ============================================================*/

package com.after90s.project.web.Authorization.baidu.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.after90s.common.constant.BaiduAppConstant;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baidu.api.Baidu;
import com.baidu.api.BaiduApiClient;
import com.baidu.api.BaiduApiException;
import com.baidu.api.BaiduOAuthException;
import com.baidu.api.domain.Session;
import com.baidu.api.domain.User;
import com.baidu.api.service.IUserService;
import com.baidu.api.service.impl.UserServiceImpl;
import com.baidu.api.store.BaiduCookieStore;
import com.baidu.api.store.BaiduStore;

/**
 * <p>
 * TODO 百度第三方登陆授权相关
 * </p>
 *
 * @author LJW
 * @version 2019年7月26日
 */
@Controller
@RequestMapping("baidu/")
public class LoginBaiDuController {
	private String clientId = BaiduAppConstant.CLIENTID;

	private String redirectUri = BaiduAppConstant.REDIRECTURI;

	private String clientSecret = BaiduAppConstant.CLIENTSECRET;

	private Logger logger = LoggerFactory.getLogger(LoginBaiDuController.class);
	/**
	 * 授权回调地址
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException ModelAndView
	 */
	@PostMapping("authorize")
	public ModelAndView baiduniubi(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ModelAndView modelAndView = new ModelAndView();
		String code = request.getParameter("code")!=null?request.getParameter("code"):"page";
		String url1 = "https://openapi.baidu.com/oauth/2.0/token?grant_type=authorization_code&code=" + code
				+ "&client_id=" + clientId + "&client_secret=" + clientSecret + "&redirect_uri=" + redirectUri + "";
//		StringBuffer shouquan=new StringBuffer().append("http://openapi.baidu.com/oauth/2.0/authorize?" )
//				.append("response_type=code&" )
//				.append("client_id="+clientId+"&" )
//				.append("redirect_uri="+redirectUri+"&" )
//				.append("scope=basic&"  )
//				.append("display=popup");
//响应结果
		String content1 = "";

		try {
//			HttpResponse res = getResponse(shouquan.toString());
			HttpResponse res2 = getResponse(url1);
			
			HttpEntity entity = res2.getEntity();
			content1 = EntityUtils.toString(entity, "UTF-8");
//
		} catch (Exception e) {
			// TODO: handle exception
		}
//
		Map<String, Object> map = JSON.parseObject(content1, new TypeReference<Map<String, Object>>() {
		});
		String access_token = (String) map.get("access_token");
		Map<String, Object> print = print(access_token, request, response);
//		request.setAttribute("message", print);
		modelAndView.addObject("message", print);
		modelAndView.setViewName("redirect:result");
//		request.getRequestDispatcher(shouquan.toString()).forward(request, response);
//		return "redirect:"+shouquan.toString();
		return modelAndView;

	}

	/**
	 * 
	 * @param url1 void
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	private HttpResponse getResponse(String url1) throws ClientProtocolException, IOException {
		// 创建一个HttpClient对象
					HttpResponse res=null;
					try {
						CloseableHttpClient httpClient = HttpClients.createDefault();
//				//创建一个Get请求
						HttpGet getReq = new HttpGet(url1);
						getReq.addHeader("Accept",
								"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8    ");
						getReq.addHeader("Accept-Encoding", "gzip, deflate, sdch, br");
						getReq.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
						getReq.addHeader("Cache-Control", "max-age=0");
						getReq.addHeader("Connection", "keep-alive");
						getReq.addHeader("Host", "openapi.baidu.com");
						getReq.addHeader("User-Agent",
								"Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36");

						res = httpClient.execute(getReq);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return res;
		
	}

	public Map<String, Object> print(String access_token, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String content = "";
		String url = "https://openapi.baidu.com/rest/2.0/passport/users/getInfo?access_token=" + access_token
				+ access_token;
		try {

			// 创建一个HttpClient对象
			CloseableHttpClient httpClient = HttpClients.createDefault();
			// 创建一个Get请求
			HttpGet getReq = new HttpGet(url);

			getReq.addHeader("Accept",
					"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8    ");
			getReq.addHeader("Accept-Encoding", "gzip, deflate, sdch, br");
			getReq.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
			getReq.addHeader("Cache-Control", "max-age=0");
			getReq.addHeader("Connection", "keep-alive");
			getReq.addHeader("Host", "openapi.baidu.com");
			getReq.addHeader("User-Agent",
					"Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36");

			HttpEntity entity = httpClient.execute(getReq).getEntity();
			content = EntityUtils.toString(entity, "UTF-8");
			System.out.println(content);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> map = JSON.parseObject(content, new TypeReference<Map<String, Object>>() {
		});
		System.out.println(map);

		String baiduid = (String) map.get("userid");
		System.out.println(baiduid);
//List list = JdbcUtils.getList(User.class, "select * from user where baiduid=" + baiduid);

		//         if (list.size() == 0) {
		request.setAttribute("message", map);
//		request.getRequestDispatcher("/result.jsp").forward(request, response);
//		modelAndView.setViewName("redirect:result");
		return map;
		//         } else {
		//             User user = (User) list.get(0);
		//             req.getSession().setAttribute("UserInfo", user);
		//             req.getRequestDispatcher("/success.jsp").forward(req, res);
		//         }
	}

	/**
	 * 回调,从百度调取消息
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException      void
	 */
	@GetMapping("callback")
	public ModelAndView callBack(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ModelAndView mv = new ModelAndView();
		BaiduStore store = new BaiduCookieStore(clientId, request, response);
		Baidu baidu = null;
		String accessToken = "";
		String refreshToken = "";
		String sessionKey = "";
		String sessionSecret = "";
		User loggedInUser = null;
		try {
			baidu = new Baidu(clientId, clientSecret, redirectUri, store, request);
			accessToken = baidu.getAccessToken();
			refreshToken = baidu.getRefreshToken();
			sessionKey = baidu.getSessionKey();
			sessionSecret = baidu.getSessionSecret();
			loggedInUser = baidu.getLoggedInUser();
		} catch (BaiduApiException e) {
			logger.debug("BaiduApiException", e);
		} catch (BaiduOAuthException e) {
			logger.debug("BaiduOAuthException ", e);
		}
		mv.addObject("accessToken", accessToken);
		mv.addObject("refreshToken", refreshToken);
		mv.addObject("sessionKey", sessionKey);
		mv.addObject("sessionSecret", sessionSecret);
		if (loggedInUser != null) {
			request.setAttribute("user", loggedInUser);
		}
		// 转发
//		request.getRequestDispatcher("accesstoken.jsp").forward(request, response);
//		return "forward:baidu/accesstoken";
		mv.setViewName("baidu/accesstoken");
		return mv;
	}

	/**
	 * 获取百度登陆注销页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException      String
	 */
	@RequestMapping(value = { "iframeDemo" }, method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView IframeDemo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ModelAndView mv = new ModelAndView();
		// 创建BaiduStore 通常使用Cookie实现
		BaiduStore store = new BaiduCookieStore(clientId, request, response);
		try {
			Baidu baidu = new Baidu(clientId, clientSecret, redirectUri, store, request);
			User user = baidu.getLoggedInUser();
			String loginUrl = baidu.getLoginUrl();
			String next = "http://127.0.0.1:9999/baidu/iframeDemo/logout";
			// 注销页面
			String loginOutUrl = baidu.getLogOutUrl(next);
			mv.addObject("user", user);
			mv.addObject("loginUrl", loginUrl);
			mv.addObject("loginOutUrl", loginOutUrl);
			mv.setViewName("forward:/baidu/iframe");
//			mv.addObject("baidu/iframeDemo.html").forward(request, response);
//			return "baidu/iframeDemo";
			return mv;
		} catch (BaiduOAuthException e) {
			logger.debug("BaiduOAuthException ", e);
		} catch (BaiduApiException e) {
			logger.debug("BaiduApiException ", e);
		}
		mv.setViewName("index");
		return mv;
	}

	/**
	 * 获取验证地址
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException String
	 */
	@PostMapping("oauthCode")
	public String OAuthServlet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		BaiduStore store = new BaiduCookieStore(clientId, request, response);
		Baidu baidu = null;
		try {
			baidu = new Baidu(clientId, clientSecret, redirectUri, store, request);
			// 状态信息
			String state = baidu.getState();
			Map<String, String> params = new HashMap<String, String>();
			params.put("state", state);
			String authorizeUrl = baidu.getBaiduOAuth2Service().getAuthorizeUrl(params);
			response.sendRedirect(authorizeUrl);
		} catch (BaiduOAuthException e) {
			logger.debug("BaiduOAuthException ", e);
		} catch (BaiduApiException e) {
			logger.debug("BaiduApiException ", e);
		}
		return "baidu/index";

	}

	/**
	 * 获取用户信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException      String
	 */
	@PostMapping("userInfo")
	public ModelAndView UserInfoServlet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ModelAndView mvAndView = new ModelAndView();
//		获取AccessToken等信息
		String token = getAccessToken(response, request);
		IUserService userService = new UserServiceImpl(new BaiduApiClient(token));
		long uid = 0;
		String userParameter = request.getParameter("uid");
		if (userParameter != null && !userParameter.trim().equals("")) {
			uid = Long.valueOf(userParameter);
		}
		String fields = request.getParameter("fields");
		String[] split = fields.split("\\,");
		String info = "";
		try {
			info = userService.getInfo(uid, split);
		} catch (BaiduApiException e) {
			info = e.toString();
		}
		mvAndView.addObject("result", info);
//		request.getRequestDispatcher("result.jsp").forward(request, response);
		mvAndView.setViewName("baidu/result");
		return mvAndView;
	}

	/**
	 * 获取当前登录用户信息
	 * 
	 * @param response
	 * @param request
	 * @return
	 * @throws ServletException
	 * @throws IOException      String
	 */
	@RequestMapping(value = { "loggedInUser" }, method = { RequestMethod.POST })
	public String UserLoggedInServlet(HttpServletResponse response, HttpServletRequest request)
			throws ServletException, IOException {
		String token = getAccessToken(response, request);
		IUserService userService = new UserServiceImpl(new BaiduApiClient(token));
		User loggedInUser = null;
		try {
			// 获取当前登录用户的用户uid和用户名。
			loggedInUser = userService.getLoggedInUser();
		} catch (BaiduApiException e) {

		}
		request.setAttribute("result", loggedInUser);
//		request.getRequestDispatcher("result.jsp").forward(request, response);
		return "forward:result";

	}

	/**
	 * 
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException String
	 */
	@GetMapping("userService")
	public String UserServiceServlet(HttpServletRequest request, HttpServletResponse response) throws IOException {
//		response.sendRedirect("userinfo.jsp");
		return "baidu/userinfo";
	}

	/**
	 * 获取token
	 * 
	 * @param response
	 * @param request
	 * @return String
	 */
	private String getAccessToken(HttpServletResponse response, HttpServletRequest request) {
		BaiduStore store = new BaiduCookieStore(BaiduAppConstant.CLIENTID, request, response);
		Session session = store.getSession();
		if (session == null) {
			try {
				response.sendRedirect("/baidu/index");
//				return "index"
				return null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return session.getToken().getAccessToken();

	}
}
