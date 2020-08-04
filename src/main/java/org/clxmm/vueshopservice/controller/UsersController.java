package org.clxmm.vueshopservice.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.clxmm.vueshopservice.domian.Users;
import org.clxmm.vueshopservice.service.UsersService;
import org.clxmm.vueshopservice.util.ResponseBean;
import org.clxmm.vueshopservice.util.ResponseCode;
import org.clxmm.vueshopservice.vo.QueryInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author clx
 * @date 2020-08-02 16:42
 */
@RestController
@RequestMapping("users")
public class UsersController {


    @Autowired
    private UsersService usersService;


    @GetMapping("/queryPaged")
    public ResponseBean queryPaged(QueryInfo queryInfo) {
        PageInfo<Users> page = usersService.queryPaged(queryInfo);
        return new ResponseBean(ResponseCode.SUCCESS, page);
    }


    @PutMapping("changeState")
    public ResponseBean changeState(@RequestBody Users users) {

        Long id = users.getId();
        Integer state = users.getState();
        if (state == 1) {
            state = 0;
        } else if (state == 0) {
            state = 1;
        } else {
            return new ResponseBean(ResponseCode.FAIL, "state 数据错误");
        }
        usersService.changeState(id, state);
        Users users1 = usersService.getById(id);
        return new ResponseBean(ResponseCode.SUCCESS, users1);
    }

    @PostMapping("/save")
    public ResponseBean save(@RequestBody Users users) {
        usersService.saveOrUpdate(users);
        return new ResponseBean(ResponseCode.SUCCESS,"");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseBean delete(@PathVariable("id") Long id ) {
        usersService.delete(id);
        return new ResponseBean(ResponseCode.SUCCESS,"删除成功");
    }


    @GetMapping("getById")
    public  ResponseBean getUserById(Long id) {
        Users users = usersService.getById(id);

        return new ResponseBean(ResponseCode.SUCCESS,users);
    }


}
