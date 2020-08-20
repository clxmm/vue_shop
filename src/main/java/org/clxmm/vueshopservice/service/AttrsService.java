package org.clxmm.vueshopservice.service;

import org.clxmm.vueshopservice.domian.Attrs;
import org.clxmm.vueshopservice.mapper.AttrsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

import java.util.List;

/**
 * @author clx
 * @date 2020-08-18 20:49
 */
@Service
public class AttrsService {

    @Autowired
    private AttrsMapper mapper;


    public List<Attrs> getAttrsByCatIdAndSel(String sel, Integer id) {
        Example.Builder builder = Example.builder(Attrs.class);
        WeekendSqls<Attrs> sqls = WeekendSqls.custom();
        sqls.andEqualTo(Attrs::getAttrSel, sel)
                .andEqualTo(Attrs::getCatId, id);
        return mapper.selectByExample(builder.where(sqls).build());
    }

    /**
     * 保存
     * @param attrs
     */
    public void save(Attrs attrs) {

        mapper.insertSelective(attrs);

    }
}
