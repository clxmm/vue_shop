package org.clxmm.vueshopservice.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonAlias;
import org.clxmm.vueshopservice.domian.Rights;
import org.clxmm.vueshopservice.service.RightsService;
import org.clxmm.vueshopservice.util.ResponseBean;
import org.clxmm.vueshopservice.util.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
        List<Rights> rights1 = new ArrayList<>();

        if ("list".equals(type)) {
            rights = rightsService.getAll();

        } else if ("tree".equals(type)){
            rights = rightsService.getAll();
            JSONArray jsonArrayByPid = getJSONArrayByPid(rights, 0L);

            return new ResponseBean(ResponseCode.SUCCESS,jsonArrayByPid);
        }



        return new ResponseBean(ResponseCode.SUCCESS,rights);
    }




    private JSONArray getJSONArrayByPid(List<Rights> rights, Long pid) {
        JSONArray array = new JSONArray();
        for (Rights right : rights) {
                if (right.getPid().longValue() == pid) {
                    JSONObject object = new JSONObject();
                    object.put("name",right.getName());
                    object.put("id",right.getId());
                    object.put("rgihts",right);
                    object.put("menus",getJSONArrayByPid(rights,right.getId()));
                    array.add(object);
                }
        }
        return array;

    }

}
