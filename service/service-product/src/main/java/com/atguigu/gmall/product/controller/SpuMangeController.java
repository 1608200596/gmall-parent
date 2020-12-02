package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.BaseSaleAttr;
import com.atguigu.gmall.model.product.SpuInfo;
import com.atguigu.gmall.product.service.ManageService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author shkstart
 * @create 2020-12-01 20:02:52
 */
@RestController
@RequestMapping("admin/product")
public class SpuMangeController {
    @Autowired
    private ManageService manageService;
    /*http://api.gmall.com/admin/product/ {page}/{limit}?category3Id=61*/
    @GetMapping("{page}/{limit}")
    public Result getSpuList(@PathVariable Long page, @PathVariable Long limit, SpuInfo spuInfo){

        //处理分页
        Page<SpuInfo> spuInfoPage = new Page<>(page,limit);
        //调用服务层方法
        IPage spuInfoList = manageService.getSpuInfoPage(spuInfoPage, spuInfo);
        return Result.ok(spuInfoList);
    }
    /*http://api.gmall.com/admin/product/baseSaleAttrList*/
    @ApiOperation(value = "获取销售属性")
    @GetMapping("baseSaleAttrList")
    public Result baseSaleAttrList(){
        // 查询所有的销售属性集合
        List<BaseSaleAttr> baseSaleAttrList = manageService.getBaseSaleAttrList();
        return Result.ok(baseSaleAttrList);
    }
}
