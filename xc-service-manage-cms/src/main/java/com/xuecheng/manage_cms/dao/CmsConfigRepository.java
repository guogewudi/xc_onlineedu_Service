package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsConfig;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Copyright@http://github.com/guogewudi
 * Author:国宇航
 * Date:2019/8/23
 * Description:
 */
public interface CmsConfigRepository extends MongoRepository<CmsConfig,String> {
}
