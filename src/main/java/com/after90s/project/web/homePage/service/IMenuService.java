/**============================================================
 * 版权： 久其软件 版权所有 (c) 2002 - 2012
 * 包： com.after90s.project.web.homePage.service
 * 修改记录：
 * 日期                作者           内容
 * =============================================================
 * 2019年7月30日       lijiawen        
 * ============================================================*/

package com.after90s.project.web.homePage.service;

import java.util.List;

import com.after90s.project.web.homePage.entity.MenuEntity;

/**
 * <p>TODO 菜单Service接口</p>
 *
 * @author lijiawen
 * @version 2019年7月30日
 */

public interface IMenuService {
	/**
	 * 获取所有菜单列表
	 * 
	 * @return List<MenuEntity>
	 */
	public List<MenuEntity> getAllMenuList();
}
