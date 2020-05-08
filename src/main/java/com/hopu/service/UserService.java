package com.hopu.service;

import com.hopu.domain.User;
import com.hopu.utils.PageBean;

public interface UserService {
    User login(String userCode, String userPassword);

    PageBean<User> findByPage(String pageNum, String pageSize, String userName, String roleId);

    void add(User user);
}
