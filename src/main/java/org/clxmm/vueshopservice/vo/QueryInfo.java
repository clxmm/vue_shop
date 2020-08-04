package org.clxmm.vueshopservice.vo;

import lombok.Data;

/**
 * @author clx
 * @date 2020-08-02 16:46
 */
@Data
public class QueryInfo {

    private String query;


    private Long pageSize = 10L;

    private Long pageNumber = 1L;


}
