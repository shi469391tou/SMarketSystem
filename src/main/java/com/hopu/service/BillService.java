package com.hopu.service;

import com.github.pagehelper.PageInfo;
import com.hopu.domain.Bill;

public interface BillService {
    PageInfo<Bill> findAll(Integer pageNum, Integer pageSize, String productName, Integer provider_id, Integer isPayment);

    void add(Bill bill);
}
