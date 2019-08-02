/**============================================================
 * 版权： 久其软件 版权所有 (c) 2002 - 2012
 * 包： com.after90s.project.web.user.mapper
 * 修改记录：
 * 日期                作者           内容
 * =============================================================
 * 2019年8月2日       lijiawen        
 * ============================================================*/

package com.after90s.project.web.user.mapper;

import com.after90s.project.web.user.entity.UserEntity;

/**
 * <p>TODO 用户Mapper</p>
 *
 * @author lijiawen
 * @version 2019年8月2日
 */

public interface UserMapper {
	 /**
     * 通过用户名查询用户
     * 
     * @param userName 用户名
     * @return UserEntity
     */
    public UserEntity selectUserByLoginName(String userName);
}
