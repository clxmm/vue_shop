package org.clxmm.vueshopservice.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.clxmm.vueshopservice.domian.Categories;
import org.clxmm.vueshopservice.service.CategoriesService;
import org.clxmm.vueshopservice.util.ParamUtil;
import org.clxmm.vueshopservice.util.ResponseBean;
import org.clxmm.vueshopservice.util.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


    /**
     * type = 3 获取全部分类
     * type = 2 获取前两级分类
     *
     * @param request
     * @return
     */
    @GetMapping("queryPage")
    public ResponseBean querypage(HttpServletRequest request) {
        Map<String, String> parameterMap = ParamUtil.getParameterMap(request);

        String type = parameterMap.get("type");

        if (StringUtils.isBlank(type)) {
            type = "3";
            parameterMap.put("type", "3");
        }


        PageInfo<Categories> pageInfo = categoriesService.queryPaged(parameterMap);
        List<Categories> list = pageInfo.getList();

        if ("2".equals(type)) {


            list = categoriesService.query(parameterMap);
            List<Categories> newList = new ArrayList<>();
            for (Categories categories : list) {
                if (1 == categories.getLevel()) {
                    newList.add(categories);
                }
            }
            for (Categories categories : newList) {
                List<Categories> list2 = new ArrayList<>();
                for (Categories c1 : list) {
                    if (categories.getId().intValue() == c1.getPid()) {
                        list2.add(c1);
                    }
                }
                categories.setChildren(list2);
            }
            pageInfo.setList(newList);
            return new ResponseBean(ResponseCode.SUCCESS, newList);

        }


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
                    categories1.setChildren(list3);
                }
                categories.setChildren(list2);

            }


            pageInfo.setList(newList);


            return new ResponseBean(ResponseCode.SUCCESS, pageInfo);
        }


        return new ResponseBean(ResponseCode.SUCCESS, pageInfo);

    }


    /**
     * 添加商品分类
     *
     * @return
     */
    @PostMapping("addCate")
    public ResponseBean addCate(@RequestBody  String s, HttpServletRequest request) {
        JSONObject object = JSONObject.parseObject(s);

        String level = object.getString("cat_level");
        String name = object.getString("name");
        String pid = object.getString("cat_pid");

        if (StringUtils.isBlank(level) || StringUtils.isBlank(name) || StringUtils.isBlank(pid)) {
            return new ResponseBean(ResponseCode.FAIL, "参数缺失");
        }

        Categories categories = new Categories();
        categories.setName(name);
        categories.setLevel(Integer.valueOf(level));
        categories.setPid(Integer.valueOf(pid));
        categoriesService.save(categories);

        return new ResponseBean(ResponseCode.SUCCESS, "添加成功");
    }

}
