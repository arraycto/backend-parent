package com.hyf.backend.goods.controller.api;

import com.hyf.backend.common.vo.ListVO;
import com.hyf.backend.goods.api.ApiTopic;
import com.hyf.backend.goods.api.vo.ApiTopicVO;
import com.hyf.backend.goods.dataobject.MallTopic;
import com.hyf.backend.goods.service.MallTopicService;
import com.hyf.backend.utils.common.vo.ResponseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Elvis on 2020/4/8
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@RestController
public class ApiTopicController implements ApiTopic {
    @Autowired
    private MallTopicService topicService;

    @Override
    public ResponseVO<ListVO<ApiTopicVO>> listTopicLimit() {
        List<MallTopic> mallTopics = topicService.listTopicLimit();
        List<ApiTopicVO> voList = new ArrayList<>();
        for (MallTopic mallTopic : mallTopics) {
            ApiTopicVO vo = new ApiTopicVO();
            BeanUtils.copyProperties(mallTopic, vo);
            voList.add(vo);
        }
        return ResponseVO.ok(new ListVO<>(voList));
    }
}
