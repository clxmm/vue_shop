package org.clxmm.vueshopservice.controller;

import org.clxmm.vueshopservice.domian.Roles;
import org.clxmm.vueshopservice.service.RolesService;
import org.clxmm.vueshopservice.util.ResponseBean;
import org.clxmm.vueshopservice.util.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author clx
 * @date 2020-08-05 21:11
 */
@RestController
@RequestMapping("roles")
public class RolesController {


    @Autowired
    RolesService rolesService;


    @GetMapping("getAllRoles")
    public ResponseBean getAllRoles() {
        List<Roles> roles = rolesService.getAll();

        return new ResponseBean(ResponseCode.SUCCESS,roles);

    }




}
