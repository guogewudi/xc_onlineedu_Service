package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import io.swagger.annotations.ApiParam;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@Service
public class PageService {
    @Autowired
    CmsPageRepository cmsPageRepository;

    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest) {
        //自定义条件查询
        ExampleMatcher pageAliase = ExampleMatcher.matching()
                .withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());
        CmsPage cmsPage = new CmsPage();
        //站点ID
        if(StringUtils.isNotEmpty(queryPageRequest.getSiteId())){
            cmsPage.setSiteId(queryPageRequest.getSiteId());
        }

        if(StringUtils.isNotEmpty(queryPageRequest.getPageId())){
            cmsPage.setPageAliase(queryPageRequest.getPageId());
        }
        if(StringUtils.isNotEmpty(queryPageRequest.getPageName())){
            cmsPage.setPageAliase(queryPageRequest.getPageName());
        }
        if(StringUtils.isNotEmpty(queryPageRequest.getTemplateId())){
            cmsPage.setPageAliase(queryPageRequest.getTemplateId());
        }

        if(StringUtils.isNotEmpty(queryPageRequest.getPageAliase())){
            cmsPage.setPageAliase(queryPageRequest.getPageAliase());

        }
        Example<CmsPage> example = Example.of(cmsPage, pageAliase);

        if(page<=0){
          page=1;
      }
      page=page-1;//spring data mongodb默认页码从零开始
      if(size<=10){
          size=10;
      }
      Pageable pageable =  PageRequest.of(page,size);
      Page<CmsPage> all = cmsPageRepository.findAll(example,pageable);


      QueryResult queryResult = new QueryResult();
      queryResult.setList(all.getContent());
      queryResult.setTotal(all.getTotalElements());
        List lister = queryResult.getList();

        if(lister.size()!=0) {//判断是否存在记录
          QueryResponseResult queryResponseResult = new QueryResponseResult(CommonCode.SUCCESS, queryResult);
          return queryResponseResult;

      }else {
          QueryResponseResult queryResponseResult = new QueryResponseResult(CommonCode.FILE_NULL, queryResult);
          return queryResponseResult;
      }

    }

    public CmsPageResult add(CmsPage cmsPage){
        //根据索引校验唯一性 name id webpath
        CmsPage cmsPage1 = cmsPageRepository
                .findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());
        if(cmsPage1==null){
            cmsPage.setPageId(null);
            cmsPageRepository.save(cmsPage);
            return new CmsPageResult(CommonCode.SUCCESS,cmsPage);
        }
        //添加失败
        return new CmsPageResult(CommonCode.FAIL,null);
    }


}
