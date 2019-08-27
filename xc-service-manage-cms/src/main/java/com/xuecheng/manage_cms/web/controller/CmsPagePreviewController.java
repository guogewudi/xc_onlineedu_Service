package com.xuecheng.manage_cms.web.controller;

import com.xuecheng.framework.web.BaseController;
import com.xuecheng.manage_cms.service.PageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.ServletOutputStream;
import java.io.IOException;

/**
 * Copyright@http://github.com/guogewudi
 * Author:国宇航
 * Date:2019/8/25
 * Description:
 */

@Controller
public class CmsPagePreviewController extends BaseController{
    @Autowired
    PageService pageService;

    //测试用例http://localhost:31001//cms/preview/5d61730ab93e292358a03666
    //工作原理：根据pageID找到templateID  根据templateID获取到tempaltefiledID，用gridFS
    //查找文件技术根据tempaltefiledID找到相应的模板文件。模板文件中的相应的freemaker语言部分会被

    /**
     *
     * 测试用例http://localhost:31001//cms/preview/5d61730ab93e292358a03666
     *工作原理：根据pageID找到templateID  根据templateID获取到tempaltefiledID，用gridFS
     *查找文件技术根据tempaltefiledID找到相应的模板文件。模板文件中的相应的freemaker语言部分会被
     *  ResponseEntity<Map> forEntity = restTemplate.getForEntity(
     *"http://localhost:31001/cms/config/getmodel/5a791725dd573c3574ee333f"
     *,Map.class);
     *Map body = forEntity.getBody();
     *map.putAll(body);
     *
     * 注意 这里的map里有四个字段 id class name model
     * 我们需要的是model   model内部里的内容本身又是一个map 里面还有三个个字段 我们只需要其中的value字段
     *

     *这里的map中内容替代

     *然后freemarker将此map内容整合到模板之中，替换模板里的内容
     *
     */
    @GetMapping("/cms/preview/{pageId}")
    public void preview(
            @PathVariable("pageId")
            String pageID){

        String pageHtml = pageService.getPageHtml(pageID);

        if(StringUtils.isNotEmpty(pageHtml)){
            try {
                ServletOutputStream outputStream = response.getOutputStream();
                outputStream.write(pageHtml.getBytes("utf-8"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }


}
