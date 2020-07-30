package org.clxmm.vueshopservice.controller;

import org.clxmm.vueshopservice.domian.Menu;
import org.clxmm.vueshopservice.service.MenuService;
import org.clxmm.vueshopservice.util.ResponseBean;
import org.clxmm.vueshopservice.util.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author clx
 * @date 2020-07-30 19:39
 */
@RestController
@RequestMapping("/menu")
public class MenuController {


    @Autowired
    private MenuService menuService;


    @GetMapping("/menus")
    public ResponseBean menus() {

        List<Menu> menuList = menuService.getAll();

        List<Menu> menus = checkMenus(menuList);


        return new ResponseBean(ResponseCode.SUCCESS, menus);
    }

    private List<Menu> checkMenus(List<Menu> menuList) {
        List<Menu> menus = new ArrayList<>();


        for (int i = menuList.size() - 1; i >= 0; i--) {
            Menu menu = menuList.get(i);

            if (menu.getPid().longValue() == 0) {
                menus.add(menu);
                menuList.remove(i);
            }

        }


        for (Menu menu : menus) {
            for (Menu menu1 : menuList) {

                if (menu1.getPid().longValue() == menu.getId()) {
                    menu.getChildren().add(menu1);
                }

            }


        }


        return menus;


    }


}
