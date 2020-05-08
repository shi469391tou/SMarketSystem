package com.hopu.mapper;

import com.hopu.domain.Provider;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProviderMapper {

    int findTotlCount();

    List<Provider> findAll(@Param("proCode") String proCode,@Param("proName") String proName);

    void save(Provider provider);

    @Select("select * from t_provider where id=#{id}")
    Provider findById(String id);

    void update(Provider provider);

    @Delete("delete from t_provider where id=#{id}")
    void deleteById(String id);

    @Select("select * from t_provider")
    List<Provider> findAllProvider();
}
