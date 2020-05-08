package com.hopu.service.impl;

import com.hopu.domain.Role;
import com.hopu.mapper.RoleMapper;
import com.hopu.service.RoleServcie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServcieImpl implements RoleServcie {
    @Autowired
    private RoleMapper roleMapper;
    @Override
    public List<Role> findAll() {
        return roleMapper.findAll();
    }
}
