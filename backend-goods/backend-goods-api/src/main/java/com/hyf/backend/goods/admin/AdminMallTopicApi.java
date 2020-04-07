package com.hyf.backend.goods.admin;

import com.hyf.backend.common.vo.PageVO;
import com.hyf.backend.goods.admin.vo.AdminTopicVO;
import com.hyf.backend.goods.dto.AdminTopicCreateDTO;
import com.hyf.backend.goods.dto.AdminTopicQueryDTO;
import com.hyf.backend.goods.dto.AdminTopicUpdateDTO;
import com.hyf.backend.utils.common.vo.ResponseVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @Author: Elvis on 2020/4/6
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@RequestMapping("/admin/topic")
public interface AdminMallTopicApi {

    @PostMapping("/create")
    ResponseVO<AdminTopicVO> createTopic(@RequestBody AdminTopicCreateDTO createDTO);


    @GetMapping("/detail")
    ResponseVO<Map<String, Object>> topicDetail(@RequestParam("id") Integer id);

    @PostMapping("/list")
    ResponseVO<PageVO<AdminTopicVO>> findPageByQuery(@RequestBody AdminTopicQueryDTO queryDTO);

    @PostMapping("/update")
    ResponseVO<AdminTopicVO> updateTopic(@RequestBody AdminTopicUpdateDTO updateDTO);


}
