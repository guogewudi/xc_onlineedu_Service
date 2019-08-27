package com.xuecheng.manage_cms_client.service;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.manage_cms_client.dao.CmsPageRepository;
import com.xuecheng.manage_cms_client.dao.CmsSiteRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

/**
 * Copyright@http://github.com/guogewudi
 * Author:国宇航
 * Date:2019/8/27
 * Description:
 */
@Service
public class PageService {
    //页面路径+物理路径+文件名（页面名）

//将页面html保存到页面物理路径

    /*1 根据pageID取出 cmspage对象 然后取出cmssite对象
    2 得到了物理路径
    3.查询到了htmlfieldname
    4 根据这个name从girdfs中取出了文件
    *
    *
    *
    */
    @Autowired
    CmsPageRepository cmsPageRepository;
    @Autowired
    CmsSiteRepository cmsSiteRepository;

    @Autowired
    GridFsTemplate gridFsTemplate;
    @Autowired
    GridFSBucket gridFSBucket;

    public void  savePageToServerPath(String pageId) {
        Optional<CmsPage> optional = cmsPageRepository.findById(pageId);
        if (optional.isPresent() == false) {
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOTEXISTS);
        }
        CmsPage cmsPage = optional.get();
        //Optional<CmsSite> cmsSite = cmsSiteRepository.findById(cmsPage.getSiteId());
        CmsSite cmsSite = this.getCmsSiteById(cmsPage.getSiteId());
        String pagePath = cmsPage.getPagePhysicalPath()+cmsSite.getSitePhysicalPath()+cmsPage.getPageName();
        String htmlFileId = cmsPage.getHtmlFileId();
        InputStream inputStream = this.getFileById(htmlFileId);
        if(inputStream == null){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_HTMLISNULL);
        }
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(new File(pagePath));
//将文件内容保存到服务物理路径
            IOUtils.copy(inputStream,fileOutputStream);
        } catch (Exception e) {

            e.printStackTrace();
        }finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




    private CmsSite getCmsSiteById(String siteID) {
        Optional<CmsSite> op = cmsSiteRepository.findById(siteID);
        if (op.isPresent()) {
            CmsSite cmsSite = op.get();
            return cmsSite;
        } else {
            return null;
        }
    }
    public InputStream getFileById(String fileId){
            try {
                GridFSFile gridFSFile =
                        gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(fileId)));
                GridFSDownloadStream gridFSDownloadStream =
                        gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
                GridFsResource gridFsResource = new GridFsResource(gridFSFile,gridFSDownloadStream);
                return gridFsResource.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }




        
    
}
