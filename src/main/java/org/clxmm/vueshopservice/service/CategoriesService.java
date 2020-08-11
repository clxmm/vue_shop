package org.clxmm.vueshopservice.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.clxmm.vueshopservice.domian.Categories;
import org.clxmm.vueshopservice.mapper.CategoriesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

import java.util.List;
import java.util.Map;

/**
 * @author clx
 * @date 2020-08-11 20:39
 */
@Service
public class CategoriesService {


    @Autowired
    private CategoriesMapper mapper;


    public PageInfo<Categories> queryPaged(Map<String, String> parameterMap) {


        String pageNumber = parameterMap.get("pageNumber");


        if (StringUtils.isBlank(pageNumber)) {
            pageNumber = "1";
        }

        String pageSize = parameterMap.get("pageSize");

        if (StringUtils.isBlank(pageSize)) {
            pageSize = "5";
        }


        PageHelper.startPage(Integer.valueOf(pageNumber), Integer.valueOf(pageSize));

        List<Categories> categoriesList = query(parameterMap);

        return new PageInfo<>(categoriesList);

    }

    private List<Categories> query(Map<String, String> parameterMap) {
        Example.Builder builder = Example.builder(Categories.class);
        WeekendSqls<Categories> sqls = WeekendSqls.custom();
        String type = parameterMap.get("type");

        if (StringUtils.isNoneBlank(type) && type.equals("3")) {
            sqls.andEqualTo(Categories::getLevel,1);
        }
        return mapper.selectByExample(builder.where(sqls).build());
    }


    public List<Categories> getAll() {
        return mapper.selectAll();
    }












}
