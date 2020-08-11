package org.clxmm.vueshopservice.controller;

import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.clxmm.vueshopservice.domian.Categories;
import org.clxmm.vueshopservice.service.CategoriesService;
import org.clxmm.vueshopservice.util.ParamUtil;
import org.clxmm.vueshopservice.util.ResponseBean;
import org.clxmm.vueshopservice.util.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 商品分类接口
 *
 * @author clx
 * @date 2020-08-11 20:40
 */
@RestController
@RequestMapping("categories")
public class CategoriesController {

    @Autowired
    private CategoriesService categoriesService;


    @GetMapping("queryPage")
    public ResponseBean querypage(HttpServletRequest request) {
        Map<String, String> parameterMap = ParamUtil.getParameterMap(request);

        String type = parameterMap.get("type");

        if (StringUtils.isBlank(type)) {
            type= "3";
        parameterMap.put("type","3");
        }


        PageInfo<Categories> pageInfo = categoriesService.queryPaged(parameterMap);
        List<Categories> list = pageInfo.getList();
        if ("3".equals(type)) {
            List<Categories> newList = new ArrayList<>();
            for (Categories categories : list) {
                if (1 == categories.getLevel()) {
                    newList.add(categories);
                }
            }

            list = categoriesService.getAll();
            for (Categories categories : newList) {
                List<Categories> list2 = new ArrayList<>();
                for (Categories c1 : list) {
                    if (categories.getId().intValue() == c1.getPid()) {
                        list2.add(c1);
                    }
                }

                for (Categories categories1 : list2) {
                    List<Categories> list3 = new ArrayList<>();
                    for (Categories c1 : list) {
                        if (c1.getPid().intValue() == categories1.getId()) {
                            list3.add(c1);
                        }
                    }
                    categories1.setCategoriesList(list3);
                }
                categories.setCategoriesList(list2);

            }


            pageInfo.setList(newList);





            return new ResponseBean(ResponseCode.SUCCESS, pageInfo);
        }



        return new ResponseBean(ResponseCode.SUCCESS, pageInfo);

    }

}
