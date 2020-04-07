package com.hyf.backend.goods.controller.admin;

import com.hyf.backend.common.vo.PageVO;
import com.hyf.backend.goods.admin.AdminMallTopicApi;
import com.hyf.backend.goods.admin.vo.AdminTopicVO;
import com.hyf.backend.goods.dto.AdminTopicCreateDTO;
import com.hyf.backend.goods.dto.AdminTopicQueryDTO;
import com.hyf.backend.goods.dto.AdminTopicUpdateDTO;
import com.hyf.backend.goods.service.MallTopicService;
import com.hyf.backend.utils.common.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author: Elvis on 2020/4/7
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@RestController
public class AdminMallTopicController implements AdminMallTopicApi {
    @Autowired
    private MallTopicService topicService;

    @Override
    public ResponseVO<AdminTopicVO> createTopic(AdminTopicCreateDTO createDTO) {
        return ResponseVO.ok(topicService.createTopic(createDTO));
    }

    @Override
    public ResponseVO<Map<String, Object>> topicDetail(Integer id) {
        return ResponseVO.ok(topicService.topicDetail(id));
    }

    @Override
    public ResponseVO<PageVO<AdminTopicVO>> findPageByQuery(AdminTopicQueryDTO queryDTO) {
        return ResponseVO.ok(topicService.findPageByQuery(queryDTO));
    }

    @Override
    public ResponseVO<AdminTopicVO> updateTopic(AdminTopicUpdateDTO updateDTO) {
        return ResponseVO.ok(topicService.updateTopic(updateDTO));
    }
}
