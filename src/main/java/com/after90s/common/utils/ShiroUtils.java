/**============================================================
 * 包： com.after90s.common.utils
 * 修改记录：
 * 日期                作者           内容
 * =============================================================
 * 2019年7月19日       LJW        
 * ============================================================*/

package com.after90s.common.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;

import com.after90s.frame.shiro.realm.MyShiroRealm;
import com.after90s.project.web.user.entity.UserEntity;

/**
 * <p>
 * TODO Shiro工具类
 * </p>
 *
 * @author LJW
 * @version 2019年7月19日
 */

public class ShiroUtils {
	/**
	 * 获取加密后的密码
	 * 
	 * @param password
	 * @param loginname
	 * @return String
	 */
	public static String getMd5Salt(String password,String loginname) {
		return new SimpleHash("MD5", password, ByteSource.Util.bytes(loginname),1024).toHex();
	}

	public static Subject getSubjct() {
		return SecurityUtils.getSubject();
	}

	public static Session getSession() {
		return SecurityUtils.getSubject().getSession();
	}

	public static void logout() {
		getSubjct().logout();
	}
	/**
	 * 
	 * 返回当前登录用户  没有则为null
	 * @return UserEntity
	 */
	public static UserEntity getUser() {
		return (UserEntity) getSubjct().getPrincipal();
	}

	public static void setUser(UserEntity user) {
		Subject subject = getSubjct();
		PrincipalCollection principalCollection = subject.getPrincipals();
		String realmName = principalCollection.getRealmNames().iterator().next();
		PrincipalCollection newPrincipalCollection = new SimplePrincipalCollection(user, realmName);
		// 重新加载Principal
		subject.runAs(newPrincipalCollection);
	}

	public static void clearCachedAuthorizationInfo() {
		RealmSecurityManager rsm = (RealmSecurityManager) SecurityUtils.getSecurityManager();
		MyShiroRealm realm = (MyShiroRealm) rsm.getRealms().iterator().next();
		realm.clearCachedAuthorizationInfo();
	}

	/**
	 * 
	 * 获取用户ID
	 * @return Long
	 */
	public static Long getUserId() {
		return getUser().getUserId().longValue();
	}

	public static String getLoginName() {
		return getUser().getLoginName();
	}

	public static String getIp() {
		return getSubjct().getSession().getHost();
	}

	public static String getSessionId() {
		return String.valueOf(getSubjct().getSession().getId());
	}

}
