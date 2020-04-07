package com.hyf.backend.goods.service;

import com.hyf.backend.common.vo.PageVO;
import com.hyf.backend.goods.admin.vo.AdminTopicVO;
import com.hyf.backend.goods.dto.AdminTopicCreateDTO;
import com.hyf.backend.goods.dto.AdminTopicQueryDTO;
import com.hyf.backend.goods.dto.AdminTopicUpdateDTO;

import java.util.Map;

/**
 * @Author: Elvis on 2020/4/7
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public interface MallTopicService {
    PageVO<AdminTopicVO> findPageByQuery(AdminTopicQueryDTO queryDTO);

    AdminTopicVO createTopic(AdminTopicCreateDTO createDTO);

    AdminTopicVO updateTopic(AdminTopicUpdateDTO updateDTO);

    Map<String, Object> topicDetail(Integer id);
}
