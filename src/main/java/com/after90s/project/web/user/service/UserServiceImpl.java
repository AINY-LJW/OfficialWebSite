/**============================================================
 * 版权： 
 * 包： com.after90s.project.web.user.service
 * 修改记录：
 * 日期                作者           内容
 * =============================================================
 * 2019年7月31日       lijiawen        
 * ============================================================*/

package com.after90s.project.web.user.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.after90s.project.web.user.entity.UserEntity;

/**
 * <p>TODO User Service</p>
 *
 *
 * @author lijiawen
 * @version 2019年7月31日
 */
@Service("userService")
public class UserServiceImpl implements IUserService{
	// 用户的集合
		private List<UserEntity> users = new ArrayList<>();
	 
		public UserServiceImpl() {
			// 从数据库查出来的用户名，密码，这是是静态数据(密码是123456)
			users.add(new UserEntity("admin", "038bdaf98f2037b31f1e75b5b4c9b26e"));
			users.add(new UserEntity("user", "098d2c478e9c11555ce2823231e02ec1"));
		}
	/* 
	 * 
	 */
	@Override
	public boolean selectUsername(String username) {
		for (UserEntity user : users) {
			if (user.getUsername().equals(username)) {
				return true;
			}
		}
		return false;

	}

	/* 
	 * 
	 */
	@Override
	public String selectPassword(String username) {
		for (UserEntity user : users) {
			if (user.getUsername().equals(username)) {
				return user.getPassword();
			}
		}
		return "";

	}

}
