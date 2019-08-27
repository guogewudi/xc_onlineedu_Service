package com.xuecheng.manage_cms.web.controller;

import com.xuecheng.api.cms.CmsConfigControllerApi;
import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.manage_cms.service.CmsConfigService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Copyright@http://github.com/guogewudi
 * Author:国宇航
 * Date:2019/8/23
 * Description:
 */
@RestController
@RequestMapping("/cms/config")
public class CmsConfigController implements CmsConfigControllerApi {

    @Autowired
    CmsConfigService cmsConfigService;

    @GetMapping("/getmodel/{id}")
    @ApiOperation("查询CMS配置")
    public CmsConfig getmodel(@PathVariable("id") String id) {
        CmsConfig configById = cmsConfigService.getConfigById(id);
        return configById;
    }
}
