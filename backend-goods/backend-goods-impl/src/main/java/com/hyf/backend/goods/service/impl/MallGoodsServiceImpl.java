package com.hyf.backend.goods.service.impl;

import com.hyf.backend.common.domain.Page;
import com.hyf.backend.common.domain.PageListBO;
import com.hyf.backend.common.mybatis.mapper.MapperHelper;
import com.hyf.backend.goods.admin.vo.AdminGoodsAttrVO;
import com.hyf.backend.goods.admin.vo.AdminGoodsDetailVO;
import com.hyf.backend.goods.admin.vo.AdminGoodsSkuVO;
import com.hyf.backend.goods.admin.vo.AdminGoodsSpecVO;
import com.hyf.backend.goods.admin.vo.AdminGoodsVO;
import com.hyf.backend.goods.api.vo.ApiGoodsSkuVO;
import com.hyf.backend.goods.api.vo.ApiGoodsVO;
import com.hyf.backend.goods.api.vo.ApiUserCartVO;
import com.hyf.backend.goods.bo.AdminGoodsBrandOptionBO;
import com.hyf.backend.goods.bo.AdminGoodsCategoryOptionBO;
import com.hyf.backend.goods.bo.GoodsBO;
import com.hyf.backend.goods.dataobject.MallBrand;
import com.hyf.backend.goods.dataobject.MallGoods;
import com.hyf.backend.goods.dataobject.MallGoodsAttr;
import com.hyf.backend.goods.dataobject.MallGoodsAttrExample;
import com.hyf.backend.goods.dataobject.MallGoodsCategory;
import com.hyf.backend.goods.dataobject.MallGoodsExample;
import com.hyf.backend.goods.dataobject.MallGoodsSku;
import com.hyf.backend.goods.dataobject.MallGoodsSkuExample;
import com.hyf.backend.goods.dataobject.MallGoodsSpec;
import com.hyf.backend.goods.dataobject.MallGoodsSpecExample;
import com.hyf.backend.goods.dto.AdminGoodsAggregationTO;
import com.hyf.backend.goods.dto.AdminGoodsAttrCreateDTO;
import com.hyf.backend.goods.dto.AdminGoodsCreateDTO;
import com.hyf.backend.goods.dto.AdminGoodsSkuCreateDTO;
import com.hyf.backend.goods.dto.AdminGoodsSpecCreateDTO;
import com.hyf.backend.goods.dto.ApiAddCartDTO;
import com.hyf.backend.goods.dto.GoodsQueryDTO;
import com.hyf.backend.goods.mapper.MallBrandMapper;
import com.hyf.backend.goods.mapper.MallGoodsAttrMapper;
import com.hyf.backend.goods.mapper.MallGoodsCategoryMapper;
import com.hyf.backend.goods.mapper.MallGoodsMapper;
import com.hyf.backend.goods.mapper.MallGoodsSkuMapper;
import com.hyf.backend.goods.mapper.MallGoodsSpecMapper;
import com.hyf.backend.goods.service.MallGoodsService;
import com.hyf.backend.utils.exception.BizException;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: Elvis on 2020/4/6
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Service
public class MallGoodsServiceImpl implements MallGoodsService {
    @Autowired
    private MallGoodsMapper mallGoodsMapper;
    @Autowired
    private MallGoodsSpecMapper mallGoodsSpecMapper;
    @Autowired
    private MallGoodsSkuMapper mallGoodsSkuMapper;
    @Autowired
    private MallGoodsAttrMapper mallGoodsAttrMapper;
    @Autowired
    private MallGoodsCategoryMapper categoryMapper;
    @Autowired
    private MallBrandMapper brandMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public GoodsBO createGoods(AdminGoodsAggregationTO adminGoodsAggregationTO) {
        List<AdminGoodsAttrCreateDTO> attributes = adminGoodsAggregationTO.getAttributes();
        List<AdminGoodsSkuCreateDTO> skus = adminGoodsAggregationTO.getSkus();
        AdminGoodsCreateDTO goods = adminGoodsAggregationTO.getGoods();
        List<AdminGoodsSpecCreateDTO> specifications = adminGoodsAggregationTO.getSpecifications();

        String name = goods.getName();
        MallGoods mallGoods = mallGoodsMapper.selectByName(name);
        if (null != mallGoods) {
            throw new BizException("该商品已经存在");
        }
//        计算商品最低价
        BigDecimal retailPrice = new BigDecimal(Integer.MAX_VALUE);
        for (AdminGoodsSkuCreateDTO skuCreateDTO : skus) {
            BigDecimal price = skuCreateDTO.getPrice();
            if (price.compareTo(retailPrice) < 0) {
                retailPrice = price;
            }
        }

        MallGoods toCreateGoods = new MallGoods();
        BeanUtils.copyProperties(goods, toCreateGoods);
        toCreateGoods.setRetailPrice(retailPrice);
        mallGoodsMapper.insertSelective(toCreateGoods);

        //商品规格表
        for (AdminGoodsSpecCreateDTO specCreateDTO : specifications) {
            MallGoodsSpec toCreateSpec = new MallGoodsSpec();
            BeanUtils.copyProperties(specCreateDTO, toCreateSpec);
            toCreateSpec.setGoodsId(toCreateGoods.getId());
            mallGoodsSpecMapper.insertSelective(toCreateSpec);
        }

        for (AdminGoodsSkuCreateDTO skuCreateDTO : skus) {
            MallGoodsSku toCreateSku = new MallGoodsSku();
            BeanUtils.copyProperties(skuCreateDTO, toCreateSku);
            toCreateSku.setGoodsId(toCreateGoods.getId());
            mallGoodsSkuMapper.insertSelective(toCreateSku);
        }

        for (AdminGoodsAttrCreateDTO attrCreateDTO : attributes) {
            MallGoodsAttr toCreateAttr = new MallGoodsAttr();
            BeanUtils.copyProperties(attrCreateDTO, toCreateAttr);
            toCreateAttr.setGoodsId(toCreateGoods.getId());
            mallGoodsAttrMapper.insertSelective(toCreateAttr);
        }

        return new GoodsBO(toCreateGoods);

    }

    @Override
    public PageListBO<GoodsBO> findPageByQuery(GoodsQueryDTO goodsQueryDTO) {
        MallGoodsExample example = new MallGoodsExample();
        MallGoodsExample.Criteria criteria = example.createCriteria();
        PageListBO<MallGoods> mallGoodsPageListBO = MapperHelper.selectPageByQueryWithBlobs(mallGoodsMapper, example, criteria, goodsQueryDTO, goodsQueryDTO.toPage());
        return new PageListBO<>(mallGoodsPageListBO, GoodsBO::new);
    }

    @Override
    public PageListBO<GoodsBO> findPageByQuerySimple(GoodsQueryDTO goodsQueryDTO) {
        MallGoodsExample example = new MallGoodsExample();
        MallGoodsExample.Criteria criteria = example.createCriteria();
        PageListBO<MallGoods> mallGoodsPageListBO = MapperHelper.selectPageByQuery(mallGoodsMapper, example, criteria, goodsQueryDTO, goodsQueryDTO.toPage());
        return new PageListBO<>(mallGoodsPageListBO, GoodsBO::new);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public GoodsBO updateGoods(AdminGoodsAggregationTO adminGoodsAggregationTO) {
        AdminGoodsCreateDTO goods = adminGoodsAggregationTO.getGoods();
        List<AdminGoodsSkuCreateDTO> skus = adminGoodsAggregationTO.getSkus();
        List<AdminGoodsAttrCreateDTO> attributes = adminGoodsAggregationTO.getAttributes();
        List<AdminGoodsSpecCreateDTO> specifications = adminGoodsAggregationTO.getSpecifications();

        BigDecimal retailPrice = new BigDecimal(Integer.MAX_VALUE);
        for (AdminGoodsSkuCreateDTO skuCreateDTO : skus) {
            BigDecimal price = skuCreateDTO.getPrice();
            if (price.compareTo(retailPrice) < 0) {
                retailPrice = price;
            }
        }
        MallGoods toUpdate = new MallGoods();
        BeanUtils.copyProperties(goods, toUpdate);
        toUpdate.setRetailPrice(retailPrice);
        mallGoodsMapper.updateByPrimaryKeySelective(toUpdate);

        for (AdminGoodsSkuCreateDTO skuCreateDTO : skus) {
            MallGoodsSku toUpdateSku = new MallGoodsSku();
            toUpdateSku.setSpecifications(null);
            BeanUtils.copyProperties(skuCreateDTO, toUpdateSku);
            mallGoodsSkuMapper.updateByPrimaryKeySelective(toUpdateSku);
        }

        for (AdminGoodsSpecCreateDTO specCreateDTO : specifications) {
            MallGoodsSpec toUpdateSpec = new MallGoodsSpec();
            BeanUtils.copyProperties(specCreateDTO, toUpdateSpec);
            toUpdateSpec.setSpecification(null);
            toUpdateSpec.setValue(null);
            mallGoodsSpecMapper.updateByPrimaryKeySelective(toUpdateSpec);
        }

        //既有更新又有删除的，先删除全部再增加
        mallGoodsAttrMapper.deleteByGoodsId(goods.getId());
        for (AdminGoodsAttrCreateDTO attrCreateDTO : attributes) {
            MallGoodsAttr toCreateAttr = new MallGoodsAttr();
            BeanUtils.copyProperties(attrCreateDTO, toCreateAttr);
            toCreateAttr.setGoodsId(goods.getId());
            mallGoodsAttrMapper.insertSelective(toCreateAttr);
        }
        return new GoodsBO(mallGoodsMapper.selectByPrimaryKey(goods.getId()));

    }

    @Override
    public AdminGoodsDetailVO goodsDetail(Integer id) {
        AdminGoodsDetailVO detailVO = new AdminGoodsDetailVO();

        MallGoods mallGoods = MapperHelper.selectByPrimaryKeyGuaranteed(mallGoodsMapper, id);
        AdminGoodsVO adminGoodsVO = new AdminGoodsVO();
        BeanUtils.copyProperties(mallGoods, adminGoodsVO);
        detailVO.setGoods(adminGoodsVO);

        //spec
        MallGoodsSpecExample specExample = new MallGoodsSpecExample();
        specExample.createCriteria().andGoodsIdEqualTo(id);
        List<MallGoodsSpec> mallGoodsSpecs = mallGoodsSpecMapper.selectByExample(specExample);
        List<AdminGoodsSpecVO> specVOList = new ArrayList<>(mallGoodsSpecs.size());
        for (MallGoodsSpec mallGoodsSpec : mallGoodsSpecs) {
            AdminGoodsSpecVO specVO = new AdminGoodsSpecVO();
            BeanUtils.copyProperties(mallGoodsSpec, specVO);
            specVOList.add(specVO);
        }
        detailVO.setSpecifications(specVOList);
        //skus
        MallGoodsSkuExample skuExample = new MallGoodsSkuExample();
        skuExample.createCriteria().andGoodsIdEqualTo(id);
        List<MallGoodsSku> mallGoodsSkus = mallGoodsSkuMapper.selectByExample(skuExample);
        List<AdminGoodsSkuVO> skuVOList = new ArrayList<>(mallGoodsSkus.size());
        for (MallGoodsSku sku : mallGoodsSkus) {
            AdminGoodsSkuVO skuVO = new AdminGoodsSkuVO();
            BeanUtils.copyProperties(sku, skuVO);
            skuVOList.add(skuVO);
        }
        detailVO.setSkus(skuVOList);

        //attr
        MallGoodsAttrExample attrExample = new MallGoodsAttrExample();
        attrExample.createCriteria().andGoodsIdEqualTo(id);
        List<MallGoodsAttr> mallGoodsAttrs = mallGoodsAttrMapper.selectByExample(attrExample);
        List<AdminGoodsAttrVO> attrVOList = new ArrayList<>(mallGoodsAttrs.size());
        for (MallGoodsAttr attr : mallGoodsAttrs) {
            AdminGoodsAttrVO attrVO = new AdminGoodsAttrVO();
            BeanUtils.copyProperties(attr, attrVO);
            attrVOList.add(attrVO);
        }
        detailVO.setAttributes(attrVOList);

        Integer categoryId = mallGoods.getCategoryId();
        MallGoodsCategory goodsCategory = MapperHelper.selectByPrimaryKeyGuaranteed(categoryMapper, categoryId);
        Integer pid = goodsCategory.getPid();
        detailVO.setCategoryIds(Arrays.asList(pid, categoryId));
        return detailVO;
    }

    @Override
    public Map<String, Object> listBrandAndCategory() {
        Map<String, Object> resultMap = new HashMap<>();

        //1.找到所有的category
//        MallGoodsCategoryExample categoryExample = new MallGoodsCategoryExample();
//        categoryExample.createCriteria().andLevelEqualTo("L1");
        List<MallGoodsCategory> mallGoodsCategories = categoryMapper.selectByLevelAndPid("L1", 0);
        List<AdminGoodsCategoryOptionBO> categoryList = new ArrayList<>();
        for (MallGoodsCategory l1category : mallGoodsCategories) {
            AdminGoodsCategoryOptionBO optionBO = new AdminGoodsCategoryOptionBO();
            optionBO.setLabel(l1category.getName());
            optionBO.setValue(l1category.getId());
            List<AdminGoodsCategoryOptionBO> children = new ArrayList<>();

            //找到所有的L2分类
//            MallGoodsCategoryExample categoryExamplel2 = new MallGoodsCategoryExample();
//            categoryExample.createCriteria().andLevelEqualTo("L2").andPidEqualTo(l1category.getId());
            List<MallGoodsCategory> l2MallGoodsCategories = categoryMapper.selectByLevelAndPid("L2", l1category.getId());
            for (MallGoodsCategory l2category : l2MallGoodsCategories) {
                AdminGoodsCategoryOptionBO optionBOl2 = new AdminGoodsCategoryOptionBO();
                optionBOl2.setValue(l2category.getId());
                optionBOl2.setLabel(l2category.getName());
                children.add(optionBOl2);
            }
            optionBO.setChildren(children);
            categoryList.add(optionBO);
        }
        resultMap.put("categoryList", categoryList);

        //2. 找到所有的brand
        List<MallBrand> mallBrands = brandMapper.selectAll();
        List<AdminGoodsBrandOptionBO> brandList = new ArrayList<>(mallBrands.size());
        for (MallBrand brand : mallBrands) {
            AdminGoodsBrandOptionBO optionBO = new AdminGoodsBrandOptionBO();
            optionBO.setLabel(brand.getName());
            optionBO.setValue(brand.getId());
            brandList.add(optionBO);
        }
        resultMap.put("brandList", brandList);
        return resultMap;
    }

    @Override
    public List<MallGoods> listByNew() {
        MallGoodsExample example = new MallGoodsExample();
        example.createCriteria().andIsNewEqualTo(true);
        PageListBO<MallGoods> mallGoodsPageListBO = MapperHelper.selectPageByExample(mallGoodsMapper, example, new Page().setPageNo(1).setPageSize(4));
        return mallGoodsPageListBO.getList();
    }

    @Override
    public List<MallGoods> listByHot() {
        MallGoodsExample example = new MallGoodsExample();
        example.createCriteria().andIsHotEqualTo(true);
        PageListBO<MallGoods> mallGoodsPageListBO = MapperHelper.selectPageByExample(mallGoodsMapper, example, new Page().setPageNo(1).setPageSize(4));
        return mallGoodsPageListBO.getList();
    }

    @Override
    public void goodsDetailPortal(Integer id) {

    }

    @Override
    public MallGoods findById(Integer id) {
        return MapperHelper.selectByPrimaryKeyGuaranteed(mallGoodsMapper, id);
    }

    @Override
    public List<MallGoodsSpec> findSpecByGoodsId(Integer goodsId) {
        MallGoodsSpecExample example = new MallGoodsSpecExample();
        example.createCriteria().andGoodsIdEqualTo(goodsId);
        return mallGoodsSpecMapper.selectByExample(example);
    }

    @Override
    public List<MallGoodsSku> findSkuByGoodsId(Integer id) {
        MallGoodsSkuExample example = new MallGoodsSkuExample();
        example.createCriteria().andGoodsIdEqualTo(id);
        return mallGoodsSkuMapper.selectByExample(example);
    }

    @Override
    public List<MallGoodsAttr> findAttrByGoodsId(Integer id) {
        MallGoodsAttrExample example = new MallGoodsAttrExample();
        example.createCriteria().andGoodsIdEqualTo(id);
        return mallGoodsAttrMapper.selectByExample(example);
    }

    @Override
    public List<ApiGoodsSkuVO> findBySkuList(Collection<Integer> skuIdList) {
        if (skuIdList.isEmpty()) {
            return Collections.emptyList();
        }
        MallGoodsSkuExample skuExample = new MallGoodsSkuExample();
        skuExample.createCriteria().andIdIn(new ArrayList<>(skuIdList));
        List<MallGoodsSku> mallGoodsSkuList = mallGoodsSkuMapper.selectByExample(skuExample);
        List<Integer> goodsIdList = mallGoodsSkuList.stream().map(MallGoodsSku::getGoodsId).collect(Collectors.toList());
        Map<Integer, MallGoods> goodsIdToMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(goodsIdList)) {
            MallGoodsExample goodsExample = new MallGoodsExample();
            goodsExample.createCriteria().andIdIn(goodsIdList);
            List<MallGoods> mallGoods = mallGoodsMapper.selectByExample(goodsExample);
            goodsIdToMap = mallGoods.stream().collect(Collectors.toMap(MallGoods::getId, goods -> goods));
        }
        List<ApiGoodsSkuVO> voList = new ArrayList<>();
        for (MallGoodsSku goodsSku : mallGoodsSkuList) {
            ApiGoodsSkuVO skuVO = new ApiGoodsSkuVO();
            BeanUtils.copyProperties(goodsSku, skuVO);
            if (goodsIdToMap.containsKey(goodsSku.getGoodsId())) {
                MallGoods mallGoods1 = goodsIdToMap.get(goodsSku.getGoodsId());
                ApiGoodsVO goodsVO = new ApiGoodsVO();
                BeanUtils.copyProperties(mallGoods1, goodsVO);
                skuVO.setGoods(goodsVO);
            }
            voList.add(skuVO);
        }

        return voList;
    }

    @Override
    public ApiUserCartVO addCart(Integer uid, ApiAddCartDTO apiAddCartDTO) {
        return null;
    }

    @Override
    public MallGoodsSku findSkuById(Integer skuId) {
        return MapperHelper.selectByPrimaryKeyGuaranteed(mallGoodsSkuMapper, skuId);
    }
}
