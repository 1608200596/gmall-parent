package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.*;
import com.atguigu.gmall.product.service.ManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author shkstart
 * @create 2020-11-30 20:08:50
 */
@Api(tags = "后台数据接口")
@RestController
@RequestMapping("admin/product/")
//@CrossOrigin
public class BaseManageController {
    //引入services文件
    @Autowired
    private ManageService manageService;
    //下面写各种控制器
    /*获取一级分类数据*/
    /*http://api.gmall.com/admin/product/getCategory1*/
    @GetMapping("getCategory1")
    public Result getCategory1(){
        List<BaseCategory1> category1List = manageService.finfAll();
        //结果放到result里面返回
        return Result.ok(category1List);
    }
    //获取二级分类数据
    /*http://api.gmall.com/admin/product/getCategory2/{category1Id}*/
    @GetMapping("getCategory2/{category1Id}")
    public Result getCategory2(@PathVariable Long category1Id){
        List<BaseCategory2> category1List = manageService.getCategory2(category1Id);
        //结果放到result里面返回
        return Result.ok(category1List);
    }


    //获取三级分类数据
    /*http://api.gmall.com/admin/product/getCategory3/{category2Id}*/
    @GetMapping("getCategory3/{category2Id}")
    public Result getCategory3(@PathVariable Long category2Id){
        List<BaseCategory3> category3List = manageService.getCategory3(category2Id);
        //结果放到result里面返回
        return Result.ok(category3List);
    }


    //获取分类id获取平台属性
    /*http://api.gmall.com/admin/product/attrInfoList/{category1Id}/{category2Id}/{category3Id}*/
    @GetMapping("attrInfoList/{category1Id}/{category2Id}/{category3Id}")
    public Result attrInfoList(@PathVariable Long category1Id,
                               @PathVariable Long category2Id,
                               @PathVariable Long category3Id){
        List<BaseAttrInfo> attrInfoList = manageService.getAttrInfoList(category1Id,category2Id,category3Id);
        //结果放到result里面返回
        return Result.ok(attrInfoList);
    }
//    添加平台属性
    /*http://api.gmall.com/admin/product/saveAttrInfo*/
    @PostMapping("saveAttrInfo")
    public Result saveAttrInfo(@RequestBody BaseAttrInfo baseAttrInfo){
        manageService.saveAttrInfo(baseAttrInfo);
        return Result.ok();
    }

    //根据平台属性ID获取平台属性对象数据
    /*http://api.gmall.com/admin/product/getAttrValueList/{attrId}*/

    //查询平台属性值集合
//        //delect * from base_value where attr_id = attrId
//        List<BaseAttrValue> baseAttrValueList = manageService.getAttrValueList(attrId);
    @GetMapping("getAttrValueList/{attrId}")
    public Result getAttrValueList(@PathVariable Long attrId){
        //可以先通过attrid获取平台属性baseattrInfo，如果属性存在，则获取对其应的属性值 getattrValueList
        BaseAttrInfo baseAttrInfo = manageService.getBaseAttrInfo(attrId);
        //结果放到result里面返回
        return Result.ok(baseAttrInfo.getAttrValueList());
    }

    /*http://api.gmall.com/admin/product/saveSpuInfo*/
    @ApiOperation(value = "保存spu商品")
    @PostMapping("saveSpuInfo")
    public Result saveSpuInfo (@RequestBody SpuInfo spuInfo){
        manageService.saveSpuInfo(spuInfo);
        return Result.ok();
    }

}
