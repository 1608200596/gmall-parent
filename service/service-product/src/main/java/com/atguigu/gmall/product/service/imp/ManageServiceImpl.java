package com.atguigu.gmall.product.service.imp;

import com.atguigu.gmall.model.product.*;
import com.atguigu.gmall.product.mapper.*;
import com.atguigu.gmall.product.service.ManageService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jdk.nashorn.internal.runtime.arrays.IteratorAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.index.IndexDefinitionRegistry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.lang.management.BufferPoolMXBean;
import java.util.List;

/**
 * @author shkstart
 * @create 2020-11-30 18:39:03
 */
@Service
public class ManageServiceImpl implements ManageService {

    //因为需要查询数据库，所以注入mapper
    @Autowired
    private BaseCategory1Mapper baseCategory1Mapper;
    @Autowired
    private BaseCategory2Mapper baseCategory2Mapper;
    @Autowired
    private BaseCategory3Mapper baseCategory3Mapper;
    @Autowired
    private BaseAttrInfoMapper baseAttrInfoMapper;
    @Autowired
    private BaseAttrValueMapper baseAttrValueMapper;
    @Autowired
    private SpuInfoMapper spuInfoMapper;
    @Autowired
    private baseSaleAttrMapper baseSaleAttrMapper;
    @Autowired
    private SpuImageMapper spuImageMapper;
    @Autowired
    private SpuSaleAttrMapper spuSaleAttrMapper;
    @Autowired
    private SpuSaleAttrValueMapper spuSaleAttrValueMapper;
    @Autowired
    private SkuInfoMapper skuInfoMapper;
    @Autowired
    private SkuAttrValueMapper skuAttrValueMapper;
    @Autowired
    private SkuImageMapper skuImageMapper;
    @Autowired
    private SkuSaleAttrValueListMapper skuSaleAttrValueListMapper;


    @Override
    public List<BaseCategory1> finfAll() {
        return baseCategory1Mapper.selectList(null);
    }

    @Override
    public List<BaseCategory2> getCategory2(Long category1Id) {
        //select * fom base_category2 where  category1_id=category1Id;
        //创建一个条件选择器
        QueryWrapper<BaseCategory2> baseCategory2QueryWrapper = new QueryWrapper<>();
        //添加查询条件
        baseCategory2QueryWrapper.eq("category1_id",category1Id);
        //执行查询方法并输出
        return  baseCategory2Mapper.selectList(baseCategory2QueryWrapper);
    }

    @Override
    public List<BaseCategory3> getCategory3(Long category2Id) {
        //select * from base_category3 where category2Id = category2_id;
        //创建条件选择器
//        QueryWrapper<BaseCategory3> baseCategory3QueryWrapper = new QueryWrapper<>();
//        baseCategory3QueryWrapper.eq("category2_id",category2Id);
       return baseCategory3Mapper.selectList(new QueryWrapper<BaseCategory3>().eq("category2_id",category2Id));
    }

    /*根据分类id查询平台属性
    *后面需要查询商品详情页面的销售属性，又要查询属性值
    * */
    @Override
    public List<BaseAttrInfo> getAttrInfoList(Long category1Id, Long category2Id, Long category3Id) {
        //需要用到多表查询，使用xml查询
        return baseAttrInfoMapper.selectBaseAttrInfoList(category1Id,category2Id,category3Id);
    }

    //需要保存baseAttrInfo 和 baseAttrValue
    @Override
    //加事务
    @Transactional
    //在这个控制器中既有保存又有修改
    public void saveAttrInfo(BaseAttrInfo baseAttrInfo) {
        // baseAttrInfo
        if (baseAttrInfo.getId() == null){
            //就是保存
            baseAttrInfoMapper.insert(baseAttrInfo);
        }else {
            //修改
            baseAttrInfoMapper.updateById(baseAttrInfo);

        }


        //baseAttrValue
        //以下操作的是整张表，不知道用户操作的是哪条数据,采用先删除再添加的方法
        //创建条件选择器
        QueryWrapper<BaseAttrValue> baseAttrValueQueryWrapper = new QueryWrapper<>();
        baseAttrValueQueryWrapper.eq("attr_id",baseAttrInfo.getId());
        baseAttrValueMapper.delete(baseAttrValueQueryWrapper);
        //删除之后原始id不见了，导致新增的id顺序错误，但是这里用不到

        List<BaseAttrValue> attrValueList = baseAttrInfo.getAttrValueList();
        if (!CollectionUtils.isEmpty(attrValueList)){
            //循环遍历，判断
            for (BaseAttrValue baseAttrValue : attrValueList) {

                //给attrId赋值 baseAttrInfo.id = baseAttrvalue.attrId
                baseAttrValue.setAttrId(baseAttrInfo.getId());
                baseAttrValueMapper.insert(baseAttrValue);
            }
        }

    }

    //根据平台属性ID获取平台属性对象数据
    @Override
    public List<BaseAttrValue> getAttrValueList(Long attrId) {
        //delect * from base_value where attr_id = attrId

        return baseAttrValueMapper.selectList(new QueryWrapper<BaseAttrValue>().eq("attr_id",attrId));
    }

    //根据平台属性ID获取平台属性对象数据
    @Override
    public BaseAttrInfo getBaseAttrInfo(Long attrId) {
        // select * from base_attr_info where attrId = id
        BaseAttrInfo baseAttrInfo = baseAttrInfoMapper.selectById(attrId);
        if (baseAttrInfo != null){

            //获取平台属性值集合，讲属性值集合放到该对象
            baseAttrInfo.setAttrValueList(getAttrValueList(attrId));

        }
        return baseAttrInfo;
    }

    @Override
    public IPage getSpuInfoPage(Page<SpuInfo> spuInfoPage, SpuInfo spuInfo) {
        //构造查询条件
        /*http://api.gmall.com/admin/product/ {page}/{limit}?category3Id=61*/
        QueryWrapper<SpuInfo> spuInfoQueryWrapper = new QueryWrapper<>();
        spuInfoQueryWrapper.eq("category3_id",spuInfo.getCategory3Id());
        //设置一个排序规则
        spuInfoQueryWrapper.orderByDesc("id");
        return spuInfoMapper.selectPage(spuInfoPage,spuInfoQueryWrapper);

    }

    // 查询所有的销售属性集合
    @Override
    public List<BaseSaleAttr> getBaseSaleAttrList() {
        return baseSaleAttrMapper.selectList(null);
    }

    //保存商品数据
    @Override
    //添加事务
    @Transactional
    public void saveSpuInfo(SpuInfo spuInfo) {
        //保存商品信息到商品表
        spuInfoMapper.insert(spuInfo);

        //保存照片到image表
        //先获取照片集合
        List<SpuImage> spuImageList = spuInfo.getSpuImageList();
        //遍历保存
        if (!CollectionUtils.isEmpty(spuImageList)){
            for (SpuImage spuImage : spuImageList) {
                spuImage.setSpuId(spuInfo.getId());
                spuImageMapper.insert(spuImage);
            }
        }

        //保存销售属性到表spuSaleAttr和spuSaleAttrList
        //先获取spuSaleAttrList
        List<SpuSaleAttr> spuSaleAttrList = spuInfo.getSpuSaleAttrList();
        //遍历
        if (!CollectionUtils.isEmpty(spuSaleAttrList)){
            for (SpuSaleAttr spuSaleAttr : spuSaleAttrList) {
                //把销售属性存到表spu_sale_attr里面
                spuSaleAttr.setSpuId(spuInfo.getId());
                spuSaleAttrMapper.insert(spuSaleAttr);
                //获取销售属性值spuSaleAttrList
                List<SpuSaleAttrValue> spuSaleAttrValueList = spuSaleAttr.getSpuSaleAttrValueList();
                //循环存储
                if (!CollectionUtils.isEmpty(spuSaleAttrValueList)){
                    for (SpuSaleAttrValue spuSaleAttrValue : spuSaleAttrValueList) {
                        spuSaleAttrValue.setSpuId(spuInfo.getId());
                        spuSaleAttrValueMapper.insert(spuSaleAttrValue);
                    }
                }
            }
        }


    }

    //根据spuid获取图片
    @Override
    public List<SpuImage> spuImageList(Long spuId) {
        QueryWrapper<SpuImage> spuImageQueryWrapper = new QueryWrapper<>();
        spuImageQueryWrapper.eq("spu_id",spuId);
        List<SpuImage> spuImages = spuImageMapper.selectList(spuImageQueryWrapper);
        return spuImages;

    }

    //根据spuId获取销售属性

    @Override
    public List<SpuSaleAttr> spuSaleAttrList(Long spuId) {
        return spuSaleAttrMapper.selectSpuSaleAttrList(spuId);
    }

    //sku的分页查询
    @Override
    public IPage<SkuInfo> getpage(Page<SkuInfo> pageparam) {
        QueryWrapper<SkuInfo> skuInfoQueryWrapper = new QueryWrapper<>();
        skuInfoQueryWrapper.orderByDesc("id");
        return skuInfoMapper.selectPage(pageparam,skuInfoQueryWrapper);
    }

    //添加sku
    @Override
    //添加事务
    @Transactional
    public void saveSkuInfo(SkuInfo skuIn) {
        /*
        * sku_info
        * sku_attr_value
        * sku_sale_attr_value
        * sku_image
        * */
        //添加sku属性
        skuInfoMapper.insert(skuIn);

        //得到skuAttrValueList
        List<SkuAttrValue> skuAttrValueList = skuIn.getSkuAttrValueList();
        //遍历添加
        if (!CollectionUtils.isEmpty(skuAttrValueList)){
            for (SkuAttrValue skuAttrValue : skuAttrValueList) {
                skuAttrValue.setSkuId(skuIn.getId());
                skuAttrValueMapper.insert(skuAttrValue);
            }
        }

        //得到skuSaleAttrValueList
        List<SkuSaleAttrValue> skuSaleAttrValueList = skuIn.getSkuSaleAttrValueList();
        //遍历添加
        if (!CollectionUtils.isEmpty(skuSaleAttrValueList)){
            for (SkuSaleAttrValue skuSaleAttrValue : skuSaleAttrValueList) {
                skuSaleAttrValue.setSpuId(skuIn.getSpuId());
                skuSaleAttrValue.setSkuId(skuIn.getId());
                skuSaleAttrValueListMapper.insert(skuSaleAttrValue);
            }
        }
        //得到skuImageList
        List<SkuImage> skuImageList = skuIn.getSkuImageList();
        if (!CollectionUtils.isEmpty(skuImageList)){
            for (SkuImage image : skuImageList) {
                image.setSkuId(skuIn.getId());
                skuImageMapper.insert(image);
            }
        }

    }

    //上架
    @Override
    public void onSale(Long skuId) {
        SkuInfo skuInfo1 = new SkuInfo();
        skuInfo1.setIsSale(1);
        skuInfo1.setId(skuId);
        skuInfoMapper.updateById(skuInfo1);

    }

    //下架
    @Override
    public void cancelSale(Long skuId) {
        SkuInfo skuInfo2 = new SkuInfo();
        skuInfo2.setIsSale(0);
        skuInfo2.setId(skuId);
        skuInfoMapper.updateById(skuInfo2);
    }

}
