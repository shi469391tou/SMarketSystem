package com.hopu.mapper;

import com.hopu.domain.Bill;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BillMapper {
    @Delete("delete from t_bill where providerId=#{pid}")
    void deleteByPid(String pid);

    List<Bill> findAll(@Param("productName") String productName,@Param("provider_id") Integer provider_id,@Param("isPayment") Integer isPayment);

    void save(Bill bill);
}
