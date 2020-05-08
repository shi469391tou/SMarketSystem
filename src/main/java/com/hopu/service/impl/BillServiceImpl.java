package com.hopu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hopu.domain.Bill;
import com.hopu.mapper.BillMapper;
import com.hopu.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillServiceImpl implements BillService {
    @Autowired
    private BillMapper billMapper;

    @Override
    public PageInfo<Bill> findAll(Integer pageNum, Integer pageSize, String productName, Integer provider_id, Integer isPayment) {
        // 先设置分页条件
        PageHelper.startPage(pageNum,pageSize);
        // 进行分页查询
        List<Bill> billList=billMapper.findAll(productName,provider_id,isPayment);
        return new PageInfo<>(billList);
    }

    @Override
    public void add(Bill bill) {
        billMapper.save(bill);
    }


}
