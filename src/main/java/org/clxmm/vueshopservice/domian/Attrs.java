package org.clxmm.vueshopservice.domian;

import lombok.Data;

import javax.persistence.Table;

/**
 * 属性表实体类
 *
 * @author clx
 * @date 2020-08-18 20:45
 */
@Table(name = "attrs")
@Data
public class Attrs extends Domain {


    private String attrName;

    private Integer catId;

    // only 输入框（唯一） many:后台下拉列表/前台单选框
    private  String attrSel;

    /**
     * manual:手工录入 list: 从列表选择
     */
    private String attrWriter;


    /**
     * attr_writer:list 那么有值，该值以逗号分隔
     */
    private String attrVals;



}
