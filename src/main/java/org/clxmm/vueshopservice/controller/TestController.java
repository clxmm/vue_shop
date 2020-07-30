package org.clxmm.vueshopservice.controller;

import org.clxmm.vueshopservice.domian.User;
import org.clxmm.vueshopservice.util.ResponseBean;
import org.clxmm.vueshopservice.util.ResponseCode;
import org.springframework.web.bind.annotation.*;

/**
 * @author clx
 * @date 2020-07-26 16:22
 */
@RestController
@RequestMapping("/login")
public class TestController {


    @PostMapping("/")
    public ResponseBean login(@RequestBody User user) {
        if (!"admin".equals(user.getUsername())) {
            return new ResponseBean(ResponseCode.FAIL,"用户名或密码错误");
        }
        return new ResponseBean(ResponseCode.SUCCESS, user);
    }







}
