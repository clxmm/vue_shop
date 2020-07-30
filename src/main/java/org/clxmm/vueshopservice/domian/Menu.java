package org.clxmm.vueshopservice.domian;

import lombok.Data;

import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

/**
 * @author clx
 * @date 2020-07-30 19:46
 */
@Data

public class Menu {


    private Long id;


    private String authName;

    private String path;

    private Long pid;

    private String icon;

    @Transient
    private List<Menu> children = new ArrayList<>();


}
