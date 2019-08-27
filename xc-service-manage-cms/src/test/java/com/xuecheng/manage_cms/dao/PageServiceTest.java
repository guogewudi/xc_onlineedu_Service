package com.xuecheng.manage_cms.dao;

import com.xuecheng.manage_cms.service.PageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Copyright@http://github.com/guogewudi
 * Author:国宇航
 * Date:2019/8/25
 * Description:
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class PageServiceTest {


    @Autowired
    PageService pageService;

    @Test
    public void testGetPageHtml(){
        pageService.getPageHtml("5d61730ab93e292358a03666");
    }
}
