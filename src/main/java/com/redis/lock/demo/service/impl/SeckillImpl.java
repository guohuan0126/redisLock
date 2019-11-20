package com.redis.lock.demo.service.impl;

import com.redis.lock.demo.common.exception.CacheLockException;
import com.redis.lock.demo.common.interf.SeckillInerface;
import com.redis.lock.demo.entity.Product;
import com.redis.lock.demo.entity.ProductOrder;
import com.redis.lock.demo.entity.User;
import com.redis.lock.demo.mapper.ProductMapper;
import com.redis.lock.demo.mapper.ProductOrderMapper;
import com.redis.lock.demo.mapper.UserMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * @program: demo
 * @description:
 * @author: Irving
 * @create: 2019-11-19
 **/
@Service
public class SeckillImpl implements SeckillInerface {

    @Resource
    UserMapper userMapper;
    @Resource
    ProductOrderMapper productOrderMapper;
    @Resource
    ProductMapper productMapper;

    @Override
    public void secKill(Long userId, Long commidityId) {
        User user = userMapper.selectByPrimaryKey(userId);
        Product product = productMapper.selectByPrimaryKey(commidityId);
        if (user==null || product==null){
            throw new CacheLockException("用户无效");
        }
        if (product.getCount()<=0){
            throw new CacheLockException("没有商品了下次再来吧");
        }
        if (user.getBalance()-product.getPrice()<0){
            throw new CacheLockException("余额不足");
        }
        user.setBalance(user.getBalance()-product.getPrice());
        product.setCount(product.getCount()-1);
        userMapper.updateByPrimaryKey(user);
        productMapper.updateByPrimaryKey(product);
        ProductOrder order = new ProductOrder();
        order.setAmount(product.getPrice());
        order.setOrderId("000001");
        order.setUserId(userId);
        productOrderMapper.insertSelective(order);
    }

}
