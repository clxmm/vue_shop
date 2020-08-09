package org.clxmm.vueshopservice.domian;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;


/**
 * @author clx
 * @date 2020-08-05 21:04
 */
@Table(name = "roles")
@Data
public class Roles extends Domain{

    private String name;

    private String remark;


    @Transient
    JSONArray rightsList;


}
