package com.atguigu.gmall.product.service;

import com.atguigu.gmall.model.product.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * @author shkstart
 * @create 2020-11-30 18:30:12
 */
public interface ManageService {
    //查询所有一级分类数据
    List<BaseCategory1> finfAll();

    //根据一级分类id查询二级分类数据
    List<BaseCategory2> getCategory2(Long category1Id);

    //根据二级分类id查询三级分类数据
    List<BaseCategory3> getCategory3(Long category2Id);

    //根据分类id 查询平台属性
    List<BaseAttrInfo> getAttrInfoList(Long category1Id,Long category2Id,Long category3Id);

    //保存
    void saveAttrInfo(BaseAttrInfo baseAttrInfo);

    //根据平台属性ID获取平台属性对象数据
    List<BaseAttrValue> getAttrValueList(Long attrId);

    //根据平台属性ID获取平台属性对象数据
    BaseAttrInfo getBaseAttrInfo(Long attrId);

    IPage getSpuInfoPage(Page<SpuInfo> spuInfoPage, SpuInfo spuInfo);

    // 查询所有的销售属性集合
    List<BaseSaleAttr> getBaseSaleAttrList();

    //保存商品数据
    void saveSpuInfo(SpuInfo spuInfo);

    //根据spuid获取图片
    List<SpuImage> spuImageList(Long spuId);

    //根据spuId获取销售属性
    List<SpuSaleAttr> spuSaleAttrList(Long spuId);

    //sku的分页查询
    IPage<SkuInfo> getpage(Page<SkuInfo> pageparam);

    //添加sku
    void saveSkuInfo(SkuInfo skuIn);

    //上架
    void onSale(Long skuId);

    //下架
    void cancelSale(Long skuId);
}
