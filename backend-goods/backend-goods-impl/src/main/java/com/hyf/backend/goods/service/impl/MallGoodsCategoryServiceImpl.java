package com.hyf.backend.goods.service.impl;

import com.hyf.backend.common.domain.PageListBO;
import com.hyf.backend.common.mybatis.mapper.MapperHelper;
import com.hyf.backend.goods.bo.AdminGoodsCategoryBO;
import com.hyf.backend.goods.dataobject.MallGoodsCategory;
import com.hyf.backend.goods.dataobject.MallGoodsCategoryExample;
import com.hyf.backend.goods.dto.AdminCreateGoodsCategoryDTO;
import com.hyf.backend.goods.dto.AdminQueryGoodsCategoryDTO;
import com.hyf.backend.goods.dto.AdminUpdateGoodsCategoryDTO;
import com.hyf.backend.goods.mapper.MallGoodsCategoryMapper;
import com.hyf.backend.goods.service.MallGoodsCategoryService;
import com.hyf.backend.utils.exception.BizException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Elvis on 2020/4/5
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Service
public class MallGoodsCategoryServiceImpl implements MallGoodsCategoryService {
    @Autowired
    private MallGoodsCategoryMapper categoryMapper;

    private boolean validate(AdminCreateGoodsCategoryDTO createGoodsCategoryDTO) {
        String level = createGoodsCategoryDTO.getLevel();
        if (!level.equals("L1") && !level.equals("L2")) {
            throw new BizException("目前支持一级和二级类目");
        }
        Integer pid = createGoodsCategoryDTO.getPid();
        if (level.equals("L2") && pid == null) {
            throw new BizException("二级类目必须有父类目");
        }
        return true;
    }

    @Override
    public AdminGoodsCategoryBO createGoodsCategory(AdminCreateGoodsCategoryDTO createGoodsCategoryDTO) {
        validate(createGoodsCategoryDTO);
        MallGoodsCategory goodsCategory = new MallGoodsCategory();
        BeanUtils.copyProperties(createGoodsCategoryDTO, goodsCategory);
        categoryMapper.insertSelective(goodsCategory);
        return new AdminGoodsCategoryBO(goodsCategory);
    }

    @Override
    public AdminGoodsCategoryBO updateGoodsCategory(AdminUpdateGoodsCategoryDTO updateGoodsCategoryDTO) {
        validate(updateGoodsCategoryDTO);
        MallGoodsCategory goodsCategory = categoryMapper.selectByPrimaryKey(updateGoodsCategoryDTO.getId());
        if (goodsCategory == null) {
            throw new BizException("该记录不存在");
        }

        MallGoodsCategory toUpdate = new MallGoodsCategory();
        BeanUtils.copyProperties(updateGoodsCategoryDTO, toUpdate);
        categoryMapper.updateByPrimaryKeySelective(toUpdate);
        return new AdminGoodsCategoryBO(categoryMapper.selectByPrimaryKey(updateGoodsCategoryDTO.getId()));
    }

    @Override
    public PageListBO<AdminGoodsCategoryBO> findPageByQuery(AdminQueryGoodsCategoryDTO queryGoodsCategoryDTO) {
        MallGoodsCategoryExample example = new MallGoodsCategoryExample();
        MallGoodsCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andPidEqualTo(0);
        PageListBO<MallGoodsCategory> mallGoodsCategoryPageListBO = MapperHelper.selectPageByQuery(categoryMapper, example, criteria, queryGoodsCategoryDTO, queryGoodsCategoryDTO.toPage());
        List<MallGoodsCategory> list = mallGoodsCategoryPageListBO.getList();
        List<AdminGoodsCategoryBO> adminGoodsCategoryBOList = list.stream().map(AdminGoodsCategoryBO::new).collect(Collectors.toList());
        PageListBO<AdminGoodsCategoryBO> result = new PageListBO<>(adminGoodsCategoryBOList, mallGoodsCategoryPageListBO.getPageNo(), mallGoodsCategoryPageListBO.getPageSize(), mallGoodsCategoryPageListBO.getTotal());

        for (AdminGoodsCategoryBO categoryBO : adminGoodsCategoryBOList) {
            Integer id = categoryBO.getId();

            MallGoodsCategoryExample example2 = new MallGoodsCategoryExample();
            example2.createCriteria().andPidEqualTo(id);
            List<MallGoodsCategory> mallGoodsCategories = categoryMapper.selectByExample(example2);
            List<AdminGoodsCategoryBO> childList = mallGoodsCategories.stream().map(AdminGoodsCategoryBO::new).collect(Collectors.toList());
            categoryBO.setChildren(childList);
        }
        return result;
    }

    @Override
    public List<AdminGoodsCategoryBO> listL1() {
        MallGoodsCategoryExample example = new MallGoodsCategoryExample();
        example.createCriteria().andLevelEqualTo("L1");
        return categoryMapper.selectByExample(example).stream().map(AdminGoodsCategoryBO::new).collect(Collectors.toList());
    }

    @Override
    public List<AdminGoodsCategoryBO> listL2() {
        MallGoodsCategoryExample example = new MallGoodsCategoryExample();
        example.createCriteria().andLevelEqualTo("L2");
        return categoryMapper.selectByExample(example).stream().map(AdminGoodsCategoryBO::new).collect(Collectors.toList());
    }

    @Override
    public List<MallGoodsCategory> listL1Simple() {
        MallGoodsCategoryExample example = new MallGoodsCategoryExample();
        example.createCriteria().andLevelEqualTo("L1");
        return categoryMapper.selectByExample(example);
    }

    @Override
    public List<MallGoodsCategory> listNaBarByL1(Integer l1id) {
        MallGoodsCategoryExample example = new MallGoodsCategoryExample();
        example.createCriteria().andLevelEqualTo("L2").andPidEqualTo(l1id);
        return categoryMapper.selectByExample(example);
    }

    @Override
    public List<MallGoodsCategory> listNaBarByL2(Integer l2id) {
        MallGoodsCategoryExample example = new MallGoodsCategoryExample();
        example.createCriteria().andLevelEqualTo("L2").andIdEqualTo(l2id);
        List<MallGoodsCategory> mallGoodsCategories = categoryMapper.selectByExample(example);
        if (mallGoodsCategories.size() > 1) {
            throw new BizException("出现两个同名的二级分类");
        }
        MallGoodsCategory category = mallGoodsCategories.get(0);
        return listNaBarByL1(category.getPid());
    }

    @Override
    public MallGoodsCategory findById(Integer id) {
        return MapperHelper.selectByPrimaryKeyGuaranteed(categoryMapper, id);
    }

    @Override
    public MallGoodsCategory findByIdL1(Integer id) {
        MallGoodsCategoryExample example = new MallGoodsCategoryExample();
        example.createCriteria().andIdEqualTo(id).andLevelEqualTo("L1");
        List<MallGoodsCategory> mallGoodsCategories = categoryMapper.selectByExample(example);
        assert mallGoodsCategories.size() == 1;
        return mallGoodsCategories.get(0);
    }

    @Override
    public List<MallGoodsCategory> findByPidAndLevel(Integer pid, String l2) {
        MallGoodsCategoryExample example = new MallGoodsCategoryExample();
        example.createCriteria().andPidEqualTo(pid).andLevelEqualTo(l2);
        return categoryMapper.selectByExample(example);
    }
}
