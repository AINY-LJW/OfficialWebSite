package com.after90s.project.web.homePage.mapper;

import com.after90s.project.web.homePage.entity.MenuEntity;
import com.after90s.project.web.homePage.entity.MenuEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MenuMapper {
    int countByExample(MenuEntityExample example);

    int deleteByExample(MenuEntityExample example);

    int deleteByPrimaryKey(Integer menuId);

    int insert(MenuEntity record);

    int insertSelective(MenuEntity record);

    List<MenuEntity> selectByExample(MenuEntityExample example);

    MenuEntity selectByPrimaryKey(Integer menuId);

    int updateByExampleSelective(@Param("record") MenuEntity record, @Param("example") MenuEntityExample example);

    int updateByExample(@Param("record") MenuEntity record, @Param("example") MenuEntityExample example);

    int updateByPrimaryKeySelective(MenuEntity record);

    int updateByPrimaryKey(MenuEntity record);
}