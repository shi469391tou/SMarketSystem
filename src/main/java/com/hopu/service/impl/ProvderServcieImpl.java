package com.hopu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hopu.domain.Provider;
import com.hopu.mapper.BillMapper;
import com.hopu.service.ProvderServcie;
import com.hopu.mapper.ProviderMapper;
import com.hopu.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProvderServcieImpl implements ProvderServcie {
    @Autowired
    private ProviderMapper providerMapper;
    @Autowired
    private BillMapper billMapper;

    @Override
    public PageInfo<Provider> findAllByPage(int num, int size, String proCode, String proName) {
        // 设置分页数据
        PageHelper.startPage(num,size);
        List<Provider> providerList=providerMapper.findAll(proCode,proName);
        //用PageInfo对结果进行包装
        PageInfo<Provider> pageInfo = new PageInfo(providerList);
        return pageInfo;
    }

    @Override
    public void add(Provider provider) {
        providerMapper.save(provider);
    }

    @Override
    public Provider findById(String id) {
        return providerMapper.findById(id);
    }

    @Override
    public void update(Provider provider) {
        providerMapper.update(provider);
    }

    @Override
    public void delete(String id) {
        // 先删除订单表
        billMapper.deleteByPid(id);
        // 再删除供应商表
        providerMapper.deleteById(id);
    }

    @Override
    public List<Provider> findAllProvider() {
        return  providerMapper.findAllProvider();
    }

    @Override
    public void saveList(List<Provider> providerList) {
        for (Provider provider : providerList) {
            this.add(provider);
        }
    }
}
