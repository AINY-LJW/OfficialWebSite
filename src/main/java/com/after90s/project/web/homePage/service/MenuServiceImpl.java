/**============================================================
 * 版权：
 * 包： com.after90s.project.web.homePage.service
 * 修改记录：
 * 日期                作者           内容
 * =============================================================
 * 2019年7月30日       ainy        
 * ============================================================*/

package com.after90s.project.web.homePage.service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.after90s.common.constant.SystemConstant;
import com.after90s.project.web.homePage.entity.MenuEntity;
import com.after90s.project.web.homePage.entity.MenuEntityExample;
import com.after90s.project.web.homePage.mapper.MenuMapper;
import com.alibaba.fastjson.JSON;

/**
 * <p>
 * TODO 菜单Service
 * </p>
 *
 * @author ainy
 * @version 2019年7月30日
 */
@Service("menuService")
public class MenuServiceImpl implements IMenuService {
	@Autowired
	private MenuMapper mapper;
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	/**
	 * 获取所有菜单
	 */
	@Override
	public List<MenuEntity> getAllMenuList() {
		ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
		boolean hasKey = redisTemplate.hasKey(SystemConstant.SYSTEM_REDISKEY_ALLMENU);
		if(hasKey) {
			long start = System.currentTimeMillis();
			 String string = opsForValue.get(SystemConstant.SYSTEM_REDISKEY_ALLMENU);
			long end = System.currentTimeMillis();
			 System.out.println("查询redis花费的时间是:" + (end - start)+"s");
			 List<MenuEntity> parseArray = JSON.parseArray(string, MenuEntity.class);
			 return parseArray;
			
		}else {
			MenuEntityExample example = new MenuEntityExample();
			long start = System.currentTimeMillis();
			example.setDistinct(false);
			List<MenuEntity> allMneuList = mapper.selectByExample(example);
			long end = System.currentTimeMillis();
			System.out.println("查询mysql花费的时间是:" + (end - start)+"s");
			String jsonString = JSON.toJSONString(allMneuList);
			opsForValue.set(SystemConstant.SYSTEM_REDISKEY_ALLMENU, jsonString, 5, TimeUnit.HOURS);
			return allMneuList;
			
		}
	}

}
