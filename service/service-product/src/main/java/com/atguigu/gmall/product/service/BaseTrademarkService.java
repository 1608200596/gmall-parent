package com.atguigu.gmall.product.service;

import com.atguigu.gmall.model.product.BaseTrademark;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import org.springframework.stereotype.Service;

/**
 * @author shkstart
 * @create 2020-12-01 21:14:59
 */
public interface BaseTrademarkService extends IService<BaseTrademark> {
    //分页列表

    IPage<BaseTrademark> selectPage(Page<BaseTrademark> pageParam);
}
