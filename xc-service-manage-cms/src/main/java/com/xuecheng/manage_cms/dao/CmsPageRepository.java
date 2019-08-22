package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.springframework.data.mongodb.repository.MongoRepository;
//主键是String
public interface CmsPageRepository extends MongoRepository<CmsPage,String> {
    CmsPage findByPageNameAndSiteIdAndPageWebPath(String pageName,String siteID,String pathWebPath);
}
