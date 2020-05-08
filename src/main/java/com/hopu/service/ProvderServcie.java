package com.hopu.service;

import com.github.pagehelper.PageInfo;
import com.hopu.domain.Provider;
import com.hopu.utils.PageBean;

import java.util.List;

public interface ProvderServcie {
    PageInfo<Provider> findAllByPage(int num, int size, String proCode, String pageNum);

    void add(Provider provider);

    Provider findById(String id);

    void update(Provider provider);

    void delete(String id);

    List<Provider> findAllProvider();

    void saveList(List<Provider> providerList);
}
