package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.ApiOperation;

public interface CmsPageControllerApi {
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest) ;
    public CmsPageResult add(CmsPage cmsPage);
    public CmsPage findById(String id);
    public CmsPageResult edit(String id,CmsPage cmsPage);
    public ResponseResult delete(String id);


    @ApiOperation("发布页面")
    public ResponseResult post(String pageId);
}
