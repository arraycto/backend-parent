package com.hyf.backend.admin.controller;

import com.hyf.backend.admin.annotation.RequiresPermissionsDesc;
import com.hyf.backend.admin.feign.AdminTopicClient;
import com.hyf.backend.common.vo.PageVO;
import com.hyf.backend.goods.admin.vo.AdminTopicVO;
import com.hyf.backend.goods.dto.AdminTopicCreateDTO;
import com.hyf.backend.goods.dto.AdminTopicQueryDTO;
import com.hyf.backend.goods.dto.AdminTopicUpdateDTO;
import com.hyf.backend.utils.common.vo.ResponseVO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author: Elvis on 2020/4/7
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@RestController
@RequestMapping("/admin/backend/topic")
public class AdminTopicController {

    @Autowired
    private AdminTopicClient topicClient;

    @PostMapping("/create")
    @RequiresPermissions("admin:topic:create")
    @RequiresPermissionsDesc(menu = {"推广管理", "专题管理"}, button = "创建")
    public ResponseVO<AdminTopicVO> createTopic(@RequestBody AdminTopicCreateDTO createDTO) {
        return topicClient.createTopic(createDTO);
    }

    @GetMapping("/list")
    @RequiresPermissions("admin:topic:list")
    @RequiresPermissionsDesc(menu = {"推广管理", "专题管理"}, button = "列表")
    public ResponseVO<PageVO<AdminTopicVO>> listTopic(AdminTopicQueryDTO queryDTO) {
        return topicClient.findPageByQuery(queryDTO);
    }

    @GetMapping("/detail")
    public ResponseVO<Map<String, Object>> topicDetail(@RequestParam("id") Integer id) {
        return topicClient.topicDetail(id);
    }

    @PostMapping("/update")
    @RequiresPermissions("admin:topic:update")
    @RequiresPermissionsDesc(menu = {"推广管理", "专题管理"}, button = "更新")
    public ResponseVO<AdminTopicVO> updateTopic(@RequestBody AdminTopicUpdateDTO updateDTO) {
        return topicClient.updateTopic(updateDTO);
    }
}
