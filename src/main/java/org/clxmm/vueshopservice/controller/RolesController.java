package org.clxmm.vueshopservice.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.clxmm.vueshopservice.domian.Rights;
import org.clxmm.vueshopservice.domian.Roles;
import org.clxmm.vueshopservice.service.RolesService;
import org.clxmm.vueshopservice.util.ResponseBean;
import org.clxmm.vueshopservice.util.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
    
    
    
    @GetMapping("getAll")
    public ResponseBean getAll() {
        List<Roles> all = rolesService.getA();

        return new ResponseBean(ResponseCode.SUCCESS,all);
    }
    
    @GetMapping("getAllRoles")
    public ResponseBean getAllRoles() {
        List<Roles> roles = rolesService.getAll();

        for (Roles role : roles) {

            JSONArray list1 = new JSONArray();
            JSONArray list2 = new JSONArray();
            JSONArray list3 = new JSONArray();


            for (long i = 0; i < 4; i++) {
                Rights rights = new Rights();
                rights.setName("权限" + i);
                rights.setPid((i - 1L));
                rights.setLevel(i + "");

                List<Rights> list4 = new ArrayList<>();
                for (int i1 = 0; i1 < 2; i1++) {
                    Rights rights1 = new Rights();
                    rights1.setName("权限" + i1);
                    rights1.setPid((i1 - 1L));
                    rights1.setLevel(i1 + "");
                    list4.add(rights1);


                    List<Rights> list5 = new ArrayList<>();
                    for (int i2 = 0; i2 < 3; i2++) {
                        Rights rights2 = new Rights();
                        rights2.setName("权限" + i1);
                        rights2.setPid((i1 - 1L));
                        rights2.setLevel(i1 + "");
                        list5.add(rights2);
                    }
                    rights1.setRightsList(list5);




                }

                rights.setRightsList(list4);





                list1.add(rights);
                list2.add(rights);
                list3.add(rights);
            }

            role.setRightsList(list1);
        }


        return new ResponseBean(ResponseCode.SUCCESS,roles);

    }


}
