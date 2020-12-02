package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.BaseTrademark;
import com.atguigu.gmall.product.service.BaseTrademarkService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author shkstart
 * @create 2020-12-01 22:07:11
 */
@RestController
@RequestMapping("admin/product/baseTrademark")
public class BaseTrademarkController{

    //引入service
    @Autowired
    private BaseTrademarkService baseTrademarkService;

    /*http://api.gmall.com/admin/product/baseTrademark/{page}/{limit}*/
    @ApiOperation(value = "分页列表")
    @GetMapping("{page}/{limit}")
    public Result index(@PathVariable Long page,
                        @PathVariable Long limit){
        Page<BaseTrademark> page1 = new Page<>(page,limit);
        IPage<BaseTrademark> pageModel = baseTrademarkService.selectPage(page1);
        return Result.ok(pageModel);
    }
    /*http://api.gmall.com/admin/product/baseTrademark/save*/
    @ApiOperation(value = "增加品牌")
    @PostMapping("save")
    public Result save(@RequestBody BaseTrademark baseTrademark){
        baseTrademarkService.save(baseTrademark);
        return Result.ok();
    }
    /*http://api.gmall.com/admin/product/baseTrademark/update*/
    @ApiOperation(value = "修改品牌")
    @PutMapping("update")
    public Result update(@RequestBody BaseTrademark baseTrademark){
        baseTrademarkService.updateById(baseTrademark);
        return Result.ok();
    }
    /*http://api.gmall.com/admin/product/baseTrademark/remove/{id}*/
    @ApiOperation(value = "删除")
    @DeleteMapping("remove/{id}")
    public Result delete(@PathVariable Long id){
        baseTrademarkService.removeById(id);
        return Result.ok();
    }
    /*http://api.gmall.com/admin/product/baseTrademark/get/{id}*/
    @ApiOperation(value = "查询品牌")
    @GetMapping("get/{id}")
    public Result select(@PathVariable String id){
        BaseTrademark baseTrademark = baseTrademarkService.getById(id);
        return Result.ok(baseTrademark);
    }

    /*http://api.gmall.com/admin/product/baseTrademark/getTrademarkList*/
    @ApiOperation(value = "获取品牌属性")
    @GetMapping("getTrademarkList")
    public Result<List<BaseTrademark>> getTrademarkList(){
        List<BaseTrademark> trademarkList = baseTrademarkService.list(null);
        return Result.ok(trademarkList);
    }



}
