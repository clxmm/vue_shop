package org.clxmm.vueshopservice.domian;

import lombok.Data;

import javax.persistence.Table;

/**
 * @author clx
 * @date 2020-08-05 20:27
 */
@Table(name = "rights")
@Data
public class Rights extends Domain {



    private Long pid;

    private String name;


    private String path;


    private String level;









}
