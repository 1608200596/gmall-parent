package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.SkuInfo;
import com.atguigu.gmall.model.product.SpuImage;
import com.atguigu.gmall.model.product.SpuSaleAttr;
import com.atguigu.gmall.product.service.ManageService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author shkstart
 * @create 2020-12-02 16:40:28
 */
@Api(tags = "sku接口")
@RestController
@RequestMapping("admin/product")
public class SkuManageController {
    //引入service
    @Autowired
    private ManageService manageService;
    /*http://api.gmall.com/admin/product/spuImageList/{spuId}*/
    //根据spuId获取图片列表
    @ApiOperation(value = "根据spuId获取图片列表")
    @GetMapping("spuImageList/{spuId}")
    public Result<List<SpuImage>> spuImageList(@PathVariable Long spuId){
        List<SpuImage> spuImageList = manageService.spuImageList(spuId);
        return Result.ok(spuImageList);
    }

    /*http://api.gmall.com/admin/product/spuSaleAttrList/{spuId}*/
    //根据spuId获取销售属性
    @ApiOperation(value = "根据spuId获取销售属性")
    @GetMapping("spuSaleAttrList/{spuId}")
    public Result spuSaleAttrList(@PathVariable Long spuId){
        List<SpuSaleAttr> spusaleAttrList = manageService.spuSaleAttrList(spuId);
        return Result.ok(spusaleAttrList);
    }

    /*http://api.gmall.com/admin/product/list/{page}/{limit}*/
    //sku的分页查询
    @GetMapping("list/{page}/{limit}")
    public Result list(@PathVariable Long page,
                       @PathVariable Long limit){
        //创建一个page
        Page<SkuInfo> pageparam = new Page<>(page,limit);
        IPage<SkuInfo> pageModel= manageService.getpage(pageparam);
        return Result.ok(pageModel);
    }

    /*http://api.gmall.com/admin/product/saveSkuInfo*/
    //添加sku
    @PostMapping("saveSkuInfo")
    public Result saveSkuInfo(@RequestBody SkuInfo skuIn){
        manageService.saveSkuInfo(skuIn);
        return Result.ok();
    }
    /*http://api.gmall.com/admin/product/onSale/{skuId}*/
    //上架
    @GetMapping("onSale/{skuId}")
    public Result onSale (@PathVariable Long skuId){
        manageService.onSale(skuId);
        return Result.ok();
    }
    /*http://api.gmall.com/admin/product/cancelSale/{skuId}*/
    @GetMapping("cancelSale/{skuId}")
    public Result cancelSale (@PathVariable Long skuId){
        manageService.cancelSale(skuId);
        return Result.ok();
    }
}
