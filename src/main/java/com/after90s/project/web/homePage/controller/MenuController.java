/**============================================================
 * 版权： 久其软件 版权所有 (c) 2002 - 2012
 * 包： com.after90s.project.web.homePage.controller
 * 修改记录：
 * 日期                作者           内容
 * =============================================================
 * 2019年7月30日       lijiawen        
 * ============================================================*/

package com.after90s.project.web.homePage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.after90s.common.annotation.Log;
import com.after90s.project.web.homePage.entity.MenuEntity;
import com.after90s.project.web.homePage.service.IMenuService;

/**
 * <p>TODO 菜单Controller</p>
 *
 * @author lijiawen
 * @version 2019年7月30日
 */
@Controller
@RequestMapping("/system/menu")
public class MenuController {
//    private String prefix = "system/menu";
    @Autowired
    private IMenuService menuService;
    
    @Log(title="目录",action="获取目录")
	@GetMapping()
	@ResponseBody
	public List<MenuEntity> getAllMenuList(){
		
		return menuService.getAllMenuList();
	}
}
