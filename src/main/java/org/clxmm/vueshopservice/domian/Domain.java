package org.clxmm.vueshopservice.domian;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author clx
 * @date 2020-08-02 16:37
 */
@Data
public class Domain {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


}
