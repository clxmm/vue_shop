package org.clxmm.vueshopservice.controller;

import com.sun.org.apache.regexp.internal.RE;
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
    public ResponseBean getAttrsByCatIdAndSel(String sel, Integer id) {

        List<Attrs> attrsList = attrsService.getAttrsByCatIdAndSel(sel, id);


        return new ResponseBean(ResponseCode.SUCCESS, attrsList);

    }


    @PostMapping("save")
    public ResponseBean save(@RequestBody Attrs attrs) {

        if (attrs.getId() == null) {
            attrsService.save(attrs);
        } else {
            attrsService.saveById(attrs);
        }

        return new ResponseBean(ResponseCode.SUCCESS, "添加成功");
    }


    /**
     * 根据id获取参数信息
     * @param id
     * @return
     */
    @GetMapping("getAttrById")
    public ResponseBean getAttrById(Long id) {
        Attrs attrs = attrsService.getAttrByid(id);
        return new ResponseBean(ResponseCode.SUCCESS, attrs);
    }

    /**
     *  根据iD 删除参数
     */
    @DeleteMapping("deleteById/{id}")
    public ResponseBean deleteById(@PathVariable("id") Long id) {
        attrsService.deleteById(id);
        return new ResponseBean(ResponseCode.SUCCESS,"删除成功");
    }






}
