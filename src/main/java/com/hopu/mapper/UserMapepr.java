package com.hopu.mapper;

import com.hopu.domain.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapepr {
    @Select("select * from t_user where userCode=#{userCode} and userPassword=#{userPassword}")
    User findByCodeAndPwd(@Param("userCode") String userCode, @Param("userPassword")String userPassword);

//    @Select("select count(*) from t_user")
    int findTotlCount(@Param("userName") String userName,@Param("roleId") Integer role_id);
//
//    @Select("select * from t_user limit #{index},#{size}")
//    List<User> findAll(@Param("index")int index,@Param("size") int size);
    // 多表关联查询，查询对应的用户角色信息
    List<User> findAll(@Param("index")int index,@Param("size") int size,@Param("userName") String userName,@Param("roleId")Integer role_id);

    void add(User user);
}
