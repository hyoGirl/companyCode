package com.spring.transaction.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AccountMapper {

    void updateAccount(@Param("money") double money,@Param("id") int id);


}
