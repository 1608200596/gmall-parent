package com.atguigu.gmall.product.service.imp;

import com.atguigu.gmall.model.product.BaseTrademark;
import com.atguigu.gmall.product.mapper.BaseTrademarkMapper;
import com.atguigu.gmall.product.service.BaseTrademarkService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author shkstart
 * @create 2020-12-01 21:18:27
 */
@Service
public class BaseTrademarkServiceImpl extends ServiceImpl<BaseTrademarkMapper, BaseTrademark> implements BaseTrademarkService{
    //引入mapper
    @Autowired
    private BaseTrademarkMapper baseTrademarkMapper;
    @Override
    public IPage<BaseTrademark> selectPage(Page<BaseTrademark> pageParam) {
        //创建一个条件选择器
        QueryWrapper<BaseTrademark> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("id");
        IPage<BaseTrademark> page = baseTrademarkMapper.selectPage(pageParam,queryWrapper);
        return page;
    }
}
