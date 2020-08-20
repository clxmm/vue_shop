package org.clxmm.vueshopservice.controller;

import org.clxmm.vueshopservice.domian.Attrs;
import org.clxmm.vueshopservice.service.AttrsService;
import org.clxmm.vueshopservice.util.ResponseBean;
import org.clxmm.vueshopservice.util.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author clx
 * @date 2020-08-18 20:49
 */
@RestController
@RequestMapping("attrs")
public class AttrsController {


    @Autowired
    private AttrsService attrsService;





    @GetMapping("getAttrsByCatIdAndSel")
    public ResponseBean getAttrsByCatIdAndSel(String sel,Integer id) {

        List<Attrs> attrsList = attrsService.getAttrsByCatIdAndSel(sel,id);


        return new ResponseBean(ResponseCode.SUCCESS,attrsList);

    }



    @PostMapping("save")
    public ResponseBean save(@RequestBody Attrs attrs) {

        if (attrs.getId()==null) {
            attrsService.save(attrs);
        }

        return new ResponseBean(ResponseCode.SUCCESS,"添加成功");
    }






}
