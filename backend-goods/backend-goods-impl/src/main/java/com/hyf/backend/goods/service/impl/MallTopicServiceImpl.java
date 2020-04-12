package com.hyf.backend.goods.service.impl;

import com.hyf.backend.common.domain.PageListBO;
import com.hyf.backend.common.mybatis.mapper.MapperHelper;
import com.hyf.backend.common.vo.PageVO;
import com.hyf.backend.goods.admin.vo.AdminTopicVO;
import com.hyf.backend.goods.dataobject.MallGoods;
import com.hyf.backend.goods.dataobject.MallGoodsExample;
import com.hyf.backend.goods.dataobject.MallTopic;
import com.hyf.backend.goods.dataobject.MallTopicExample;
import com.hyf.backend.goods.dto.AdminTopicCreateDTO;
import com.hyf.backend.goods.dto.AdminTopicQueryDTO;
import com.hyf.backend.goods.dto.AdminTopicUpdateDTO;
import com.hyf.backend.goods.mapper.MallGoodsMapper;
import com.hyf.backend.goods.mapper.MallTopicMapper;
import com.hyf.backend.goods.service.MallTopicService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Elvis on 2020/4/7
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Service
public class MallTopicServiceImpl implements MallTopicService {

    @Autowired
    private MallTopicMapper topicMapper;
    @Autowired
    private MallGoodsMapper goodsMapper;

    @Override
    public PageVO<AdminTopicVO> findPageByQuery(AdminTopicQueryDTO queryDTO) {
        MallTopicExample example = new MallTopicExample();
        MallTopicExample.Criteria criteria = example.createCriteria();
        PageListBO<MallTopic> mallTopicPageListBO =
                MapperHelper.selectPageByQueryWithBlobs(topicMapper, example, criteria, queryDTO, queryDTO.toPage());
        List<AdminTopicVO> voList = new ArrayList<>(mallTopicPageListBO.getList().size());
        for (MallTopic mallTopic : mallTopicPageListBO.getList()) {
            AdminTopicVO vo = new AdminTopicVO();
            BeanUtils.copyProperties(mallTopic, vo);
            voList.add(vo);
        }
        return new PageVO<>(voList, mallTopicPageListBO.getPageSize(), mallTopicPageListBO.getPageNo(), mallTopicPageListBO.getTotal());
    }

    @Override
    public AdminTopicVO createTopic(AdminTopicCreateDTO createDTO) {
        MallTopic toCreate = new MallTopic();
        BeanUtils.copyProperties(createDTO, toCreate);
        topicMapper.insertSelective(toCreate);
        AdminTopicVO vo = new AdminTopicVO();
        BeanUtils.copyProperties(toCreate, vo);
        return vo;
    }

    @Override
    public AdminTopicVO updateTopic(AdminTopicUpdateDTO updateDTO) {
        MallTopic toUpdate = new MallTopic();
        BeanUtils.copyProperties(updateDTO, toUpdate);
        topicMapper.updateByPrimaryKeyWithBLOBs(toUpdate);
        AdminTopicVO vo = new AdminTopicVO();
        BeanUtils.copyProperties(topicMapper.selectByPrimaryKey(updateDTO.getId()), vo);
        return vo;
    }

    @Override
    public Map<String, Object> topicDetail(Integer id) {
        Map<String, Object> resultMap = new HashMap<>();
        MallTopic mallTopic = MapperHelper.selectByPrimaryKeyGuaranteed(topicMapper, id);
        List goodIdList = mallTopic.getGoods();
        if (CollectionUtils.isNotEmpty(goodIdList)) {
            MallGoodsExample goodsExample = new MallGoodsExample();
            goodsExample.createCriteria().andIdIn(goodIdList);
            List<MallGoods> mallGoods = goodsMapper.selectByExample(goodsExample);
            resultMap.put("goodsList", mallGoods);
        } else {
            resultMap.put("goodsList", new ArrayList<>());
        }
        resultMap.put("topic", mallTopic);
        return resultMap;
    }

    @Override
    public List<MallTopic> listTopicLimit() {
        return  topicMapper.selectLimit5();
    }
}
