package org.clxmm.vueshopservice.domian;

import lombok.Data;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * 商品分类实体类
 *
 * @author clx
 * @date 2020-08-11 20:37
 */
@Data
@Table(name = "categories")
public class Categories extends Domain {


    private Integer pid;

    private Integer level;

    private String name;


    @Transient
    private List<Categories> categoriesList;

}
