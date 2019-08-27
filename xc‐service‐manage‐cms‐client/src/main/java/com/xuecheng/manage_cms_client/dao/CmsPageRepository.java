package com.xuecheng.manage_cms_client.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

//主键是String
@Repository
public interface CmsPageRepository extends MongoRepository<CmsPage,String> {

}
