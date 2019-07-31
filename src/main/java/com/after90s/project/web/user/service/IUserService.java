/**============================================================
 * 版权： 
 * 包： com.after90s.project.web.user.service
 * 修改记录：
 * 日期                作者           内容
 * =============================================================
 * 2019年7月31日       lijiawen        
 * ============================================================*/

package com.after90s.project.web.user.service;

/**
 * <p>TODO 用户Service接口</p>
 *
 *
 * @author lijiawen
 * @version 2019年7月31日
 */

public interface IUserService {
	/**
	 * 判断是否用户名是否存在
	 * 
	 * @param username
	 * @return boolean
	 */
	public boolean selectUsername(String username);
	/**
	 * 根据用户返回查询的密码
	 * 
	 * @param username
	 * @return String
	 */
	public String selectPassword(String username);
}
