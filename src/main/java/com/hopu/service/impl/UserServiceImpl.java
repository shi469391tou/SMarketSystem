package com.hopu.service.impl;

import com.hopu.domain.User;
import com.hopu.mapper.UserMapepr;
import com.hopu.service.UserService;
import com.hopu.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapepr userMapepr;
    @Override
    public User login(String userCode, String userPassword) {
        return userMapepr.findByCodeAndPwd(userCode,userPassword);
    }

    @Override
    public PageBean<User> findByPage(String pageNum, String pageSize,String userName,String roleId) {
        int num = Integer.parseInt(pageNum);
        int size = Integer.parseInt(pageSize);
        Integer role_id=null;
        if(roleId!=null && roleId!=""){
            role_id= Integer.parseInt(roleId);
            if(role_id==0){
                role_id=null;
            }
        }

        // 查询总记录数
        int totalCount=userMapepr.findTotlCount(userName,role_id);
        // 计算总页数
        Integer totalPage= totalCount%size==0 ? totalCount/size:totalCount/size+1;
        // 查询具体的分页数据
        int index = (num - 1)*size;
        List<User> userList=userMapepr.findAll(index,size,userName,role_id);

        PageBean<User> pageBean = new PageBean<>();
        pageBean.setPageNum(num);
        pageBean.setPageSize(size);
        pageBean.setTotalCount(totalCount);
        pageBean.setList(userList);
        pageBean.setTotalPage(totalPage);

        return pageBean;
    }

    @Override
    public void add(User user) {
        userMapepr.add(user);
    }
}
