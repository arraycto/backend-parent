package com.hyf.backend.coupon.template.service.impl;

import com.hyf.backend.common.domain.PageListBO;
import com.hyf.backend.common.mybatis.mapper.MapperHelper;
import com.hyf.backend.common.vo.PageVO;
import com.hyf.backend.coupon.template.admin.dto.AdminBannerCreateDTO;
import com.hyf.backend.coupon.template.admin.dto.AdminBannerQueryDTO;
import com.hyf.backend.coupon.template.admin.dto.AdminBannerUpdateDTO;
import com.hyf.backend.coupon.template.admin.vo.AdminBannerVO;
import com.hyf.backend.coupon.template.dataobject.MallBanner;
import com.hyf.backend.coupon.template.dataobject.MallBannerExample;
import com.hyf.backend.coupon.template.mapper.MallBannerMapper;
import com.hyf.backend.coupon.template.service.BannerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Elvis on 2020/4/7
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Service
@Validated
public class BannerServiceImpl implements BannerService {
    @Autowired
    private MallBannerMapper bannerMapper;

    @Override
    public AdminBannerVO createBanner(@Valid AdminBannerCreateDTO bannerCreateDTO) {
        MallBanner toCreate = new MallBanner();
        BeanUtils.copyProperties(bannerCreateDTO, toCreate);
        bannerMapper.insertSelective(toCreate);
        AdminBannerVO vo = new AdminBannerVO();
        BeanUtils.copyProperties(toCreate, vo);
        return vo;
    }

    @Override
    public AdminBannerVO updateBanner(@Valid AdminBannerUpdateDTO bannerUpdateDTO) {
        MallBanner toUpdate = new MallBanner();
        BeanUtils.copyProperties(bannerUpdateDTO, toUpdate);
        bannerMapper.updateByPrimaryKeySelective(toUpdate);
        AdminBannerVO vo = new AdminBannerVO();
        BeanUtils.copyProperties(bannerMapper.selectByPrimaryKey(bannerUpdateDTO.getId()), vo);
        return vo;
    }

    @Override
    public PageVO<AdminBannerVO> findPageByQuery(@Valid AdminBannerQueryDTO queryDTO) {
        MallBannerExample example = new MallBannerExample();
        MallBannerExample.Criteria criteria = example.createCriteria();
        PageListBO<MallBanner> mallBannerPageListBO = MapperHelper.selectPageByQuery(bannerMapper, example, criteria, queryDTO, queryDTO.toPage());
        List<AdminBannerVO> voList = new ArrayList<>();
        for (MallBanner mallBanner : mallBannerPageListBO.getList()) {
            AdminBannerVO vo = new AdminBannerVO();
            BeanUtils.copyProperties(mallBanner, vo);
            voList.add(vo);
        }
        return new PageVO<>(voList, mallBannerPageListBO.getPageSize(), mallBannerPageListBO.getPageNo(), mallBannerPageListBO.getTotal());
    }
}
