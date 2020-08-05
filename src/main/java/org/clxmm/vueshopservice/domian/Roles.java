package org.clxmm.vueshopservice.domian;

import lombok.Data;

import javax.persistence.Table;

/**
 * @author clx
 * @date 2020-08-05 21:04
 */
@Table(name = "roles")
@Data
public class Roles extends Domain{

    private String name;

    private String remark;


}
