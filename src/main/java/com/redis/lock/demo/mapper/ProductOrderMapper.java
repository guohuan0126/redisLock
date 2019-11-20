package com.redis.lock.demo.mapper;

import com.redis.lock.demo.entity.ProductOrder;
import com.redis.lock.demo.entity.ProductOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ProductOrderMapper {
    long countByExample(ProductOrderExample example);

    int deleteByExample(ProductOrderExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ProductOrder record);

    int insertSelective(ProductOrder record);

    List<ProductOrder> selectByExampleWithRowbounds(ProductOrderExample example, RowBounds rowBounds);

    List<ProductOrder> selectByExample(ProductOrderExample example);

    ProductOrder selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ProductOrder record, @Param("example") ProductOrderExample example);

    int updateByExample(@Param("record") ProductOrder record, @Param("example") ProductOrderExample example);

    int updateByPrimaryKeySelective(ProductOrder record);

    int updateByPrimaryKey(ProductOrder record);
}