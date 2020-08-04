package org.clxmm.vueshopservice.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.jni.User;
import org.clxmm.vueshopservice.domian.Users;
import org.clxmm.vueshopservice.mapper.UsersMapper;
import org.clxmm.vueshopservice.vo.QueryInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

import java.util.Date;
import java.util.List;

/**
 * @author clx
 * @date 2020-08-02 16:40
 */
@Service
public class UsersService {

    @Autowired
    UsersMapper mapper;

    public PageInfo<Users> queryPaged(QueryInfo queryInfo) {


        PageHelper.startPage(queryInfo.getPageNumber().intValue(), queryInfo.getPageSize().intValue());


        List<Users> users = query(queryInfo);
        for (Users user : users) {
            user.setMgState();
        }

        return new PageInfo(users);
    }

    private List<Users> query(QueryInfo queryInfo) {

        Example.Builder builder = Example.builder(Users.class);
        WeekendSqls<Users> sqls = WeekendSqls.custom();

        if (StringUtils.isNoneBlank(queryInfo.getQuery())) {
            String query = "%" + queryInfo.getQuery() + "%";
            sqls.orLike(Users::getUsername, query)
                    .orLike(Users::getUsername, query);
            return mapper.selectByExample(builder.where(sqls).build());
        }

        return mapper.selectAll();

    }

    public void changeState(Long id, Integer state) {

        Users users = new Users();
        users.setId(id);
        users.setState(state);

        mapper.updateByPrimaryKeySelective(users);
    }

    public Users getById(Long id) {
        return mapper.selectByPrimaryKey(id);
    }

    public void saveOrUpdate(Users user) {
        if (user.getId() != null) {
            mapper.updateByPrimaryKey(user);
        } else {
            user.setCreateTime(new Date());
            mapper.insertSelective(user);
        }

    }

    public void delete(Long id) {
        mapper.deleteByPrimaryKey(id);
    }
}
