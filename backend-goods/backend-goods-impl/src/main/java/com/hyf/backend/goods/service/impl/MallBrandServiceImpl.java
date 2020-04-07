package com.hyf.backend.goods.service.impl;

import com.hyf.backend.common.domain.PageListBO;
import com.hyf.backend.common.mybatis.mapper.MapperHelper;
import com.hyf.backend.goods.bo.AdminBrandBO;
import com.hyf.backend.goods.dataobject.MallBrand;
import com.hyf.backend.goods.dataobject.MallBrandExample;
import com.hyf.backend.goods.dto.AdminBrandCreateDTO;
import com.hyf.backend.goods.dto.AdminBrandQueryDTO;
import com.hyf.backend.goods.dto.AdminBrandUpdateDTO;
import com.hyf.backend.goods.mapper.MallBrandMapper;
import com.hyf.backend.goods.service.MallBrandService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Elvis on 2020/4/5
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Service
public class MallBrandServiceImpl implements MallBrandService {
    @Autowired
    private MallBrandMapper mallBrandMapper;

    @Override
    public PageListBO<AdminBrandBO> queryByPage(AdminBrandQueryDTO brandQueryDTO) {
        MallBrandExample example = new MallBrandExample();
        MallBrandExample.Criteria criteria = example.createCriteria();
        PageListBO<MallBrand> mallBrandPageListBO =
                MapperHelper.selectPageByQuery(mallBrandMapper, example, criteria, brandQueryDTO, brandQueryDTO.toPage());

        return new PageListBO<>(mallBrandPageListBO, AdminBrandBO::new);
    }

    @Override
    public AdminBrandBO createBrand(AdminBrandCreateDTO brandCreateDTO) {
        MallBrand mallBrand = new MallBrand();
        BeanUtils.copyProperties(brandCreateDTO, mallBrand);
        mallBrandMapper.insertSelective(mallBrand);
        return new AdminBrandBO(mallBrand);
    }

    @Override
    public AdminBrandBO updateBrand(AdminBrandUpdateDTO brandUpdateDTO) {
        Integer id = brandUpdateDTO.getId();
        MapperHelper.selectByPrimaryKeyGuaranteed(mallBrandMapper, id);
        MallBrand toUpdate = new MallBrand();
        BeanUtils.copyProperties(brandUpdateDTO, toUpdate);
        mallBrandMapper.updateByPrimaryKeySelective(toUpdate);
        return new AdminBrandBO(mallBrandMapper.selectByPrimaryKey(id));
    }
}
