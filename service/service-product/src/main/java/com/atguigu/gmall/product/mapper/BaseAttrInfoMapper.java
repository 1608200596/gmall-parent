package com.atguigu.gmall.product.mapper;

import com.atguigu.gmall.model.product.BaseAttrInfo;
import com.atguigu.gmall.model.product.BaseAttrValue;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author shkstart
 * @create 2020-11-30 18:24:27
 */
@Mapper
public interface BaseAttrInfoMapper extends BaseMapper<BaseAttrInfo> {
    //根据分类id查询属性+平台属性值
    //在mybatis中需要传递多个值时， 需要注解才能在后台获取到
    List<BaseAttrInfo> selectBaseAttrInfoList(@Param("category1Id")Long category1Id,
                                            @Param("category2Id")Long category2Id,
                                            @Param("category3Id")Long category3Id);
}
