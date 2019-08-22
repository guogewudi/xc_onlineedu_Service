package com.xuecheng.manage_cms.web.controller;

import com.xuecheng.api.cms.CmsPageControllerApi;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.manage_cms.service.PageService;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("cms/page")

public class CmsPageController implements CmsPageControllerApi {


@Autowired
    private PageService pageService;
    @Override
    @GetMapping("list/{page}/{size}")
    @ApiOperation("查询")
    public QueryResponseResult findList(
            @ApiParam(name = "page",value = "页面")
            @PathVariable("page") int page,
            @ApiParam(name = "size",value = "尺寸")
            @PathVariable("size") int size,
            QueryPageRequest queryPageRequest) {

//
//        QueryResult<CmsPage>queryResult = new QueryResult();
//        List<CmsPage > list = new ArrayList<>();
//        CmsPage cmsPage = new CmsPage();
//        cmsPage.setPageName("测试页面");
//        list.add(cmsPage);
//
//        queryResult.setList(list);
//        queryResult.setTotal(1);
//        QueryResponseResult queryResponseResult = new QueryResponseResult(CommonCode.SUCCESS,queryResult);
//
//        return queryResponseResult;
        return pageService.findList(page,size,queryPageRequest);
    }

    @Override
    @PostMapping("/add")
    @ApiOperation("添加")
    //post一般和requestbody注解一起用
    //从浏览器的post请求的数据转换成json
    public CmsPageResult add(@RequestBody
                                 @ApiParam(name = "cmsPage",value = "页面信息")
                                         CmsPage cmsPage) {
        return pageService.add(cmsPage);
    }
}
