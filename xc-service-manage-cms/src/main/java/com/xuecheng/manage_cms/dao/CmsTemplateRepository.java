package com.xuecheng.manage_cms.dao;


import com.xuecheng.framework.domain.cms.CmsTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Copyright@http://github.com/guogewudi
 * Author:国宇航
 * Date:2019/8/25
 * Description:
 */
public interface CmsTemplateRepository extends MongoRepository<CmsTemplate,String> {
}
