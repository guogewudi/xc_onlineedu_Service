package com.xuecheng.manage_cms_client.mq;

import com.alibaba.fastjson.JSON;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.manage_cms_client.dao.CmsPageRepository;
import com.xuecheng.manage_cms_client.service.PageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

/**监听mq 接收页面发送的消息
 * Copyright@http://github.com/guogewudi
 * Author:国宇航
 * Date:2019/8/27
 * Description:
 */
@Component
public class ConsumerPostPage {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerPostPage.class);
    @Autowired
    PageService pageService;
    @Autowired
    CmsPageRepository cmsPageRepository;
    @RabbitListener(queues = {"${xuecheng.mq.queue}"})
    public void postPage(String msg){
        //解析消息  fastjson
        Map map = JSON.parseObject(msg, Map.class);
        //得到消息中的页面ID
        String pageId= (String) map.get("pageId");
        //调用service方法准备将页面从gridfs中下载到服务器 注入service
        //可以做一个判断校验页面是否合法
        Optional<CmsPage> byId = cmsPageRepository.findById(pageId);
        if(byId.isPresent()==false){
            LOGGER.error("收到了cms请求页面，但是cmspage对象是空",msg.toString());
        }
        pageService.savePageToServerPath(pageId);
        }

}
