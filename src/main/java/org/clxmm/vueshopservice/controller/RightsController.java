package org.clxmm.vueshopservice.controller;

import org.clxmm.vueshopservice.domian.Rights;
import org.clxmm.vueshopservice.service.RightsService;
import org.clxmm.vueshopservice.util.ResponseBean;
import org.clxmm.vueshopservice.util.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author clx
 * @date 2020-08-05 20:32
 */
@RestController
@RequestMapping("rights")
public class RightsController {


    @Autowired
    RightsService rightsService;


    @GetMapping("/rights")
    public ResponseBean rights(String type) {


        List<Rights> rights = null;
        if ("list".equals(type)) {
            rights = rightsService.getAll();

        }


        return new ResponseBean(ResponseCode.SUCCESS,rights);
    }


}
