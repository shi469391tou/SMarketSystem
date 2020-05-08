package com.hopu.web.controller;

import com.hopu.domain.Role;
import com.hopu.service.RoleServcie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleServcie roleServcie;

    // 用户退出
    @RequestMapping("/findAll")
    @ResponseBody
    public List<Role> findAllAjax(){
       // 查询数据
        List<Role> roleList =roleServcie.findAll();
        // 返回数据
        return roleList;
    }
}
