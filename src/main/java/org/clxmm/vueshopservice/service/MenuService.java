package org.clxmm.vueshopservice.service;

import org.clxmm.vueshopservice.domian.Menu;
import org.clxmm.vueshopservice.mapper.MenuMapper;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.Weekend;
import tk.mybatis.mapper.weekend.WeekendSqls;

import java.util.List;

/**
 * @author clx
 * @date 2020-07-30 19:56
 */
@Service
public class MenuService {

    private final MenuMapper menuMapper;

    public MenuService(MenuMapper menuMapper) {
        this.menuMapper = menuMapper;
    }


    public List<Menu> getAll() {

        Example.Builder builder = Example.builder(Menu.class);

        WeekendSqls<Menu> sqls = WeekendSqls.custom();



        return menuMapper.selectByExample(builder.where(sqls).orderByDesc("id").build());
    }
}
