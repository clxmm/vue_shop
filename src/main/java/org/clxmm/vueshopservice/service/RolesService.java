package org.clxmm.vueshopservice.service;

import org.clxmm.vueshopservice.domian.Roles;
import org.clxmm.vueshopservice.mapper.RolesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author clx
 * @date 2020-08-05 21:10
 */
@Service
public class RolesService {


    @Autowired
    RolesMapper mapper;


    public List<Roles> getAll() {

        return mapper.getAllRoles();
    }


    public List<Roles> getA() {
        return mapper.selectAll();
    }
}
