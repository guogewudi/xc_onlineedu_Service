package com.xuecheng.manage_cms;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.test.context.junit4.SpringRunner;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Copyright@http://github.com/guogewudi
 * Author:国宇航
 * Date:2019/8/23
 * Description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class GridFsTest {
    @Autowired
    GridFsTemplate gridFsTemplate;

    @Autowired
    GridFSBucket gridFSBucket;

    //存文件
    @Test
    public void test() throws FileNotFoundException {
        //要存储的文件
        File file = new File("d:/index_banner.ftl");
//定义输入流
        FileInputStream inputStram = new FileInputStream(file);
//向GridFS存储文件
        ObjectId objectId = gridFsTemplate.store(inputStram, "轮test08", "");
//得到文件ID
        String fileId = objectId.toString();
        System.out.println(fileId);

    }




    //取文件
    @Test
    public void queryFile() throws IOException {
        //根据文件ID查文件
        GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is("5d5f8a6db93e292fe8b0e9ef")));
            //下载流对象
        GridFSDownloadStream gridFSDownloadStream =
                gridFSBucket.openDownloadStream(gridFSFile.getObjectId());

        //创建resource对象获取流
        GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);
        //从流中获取数据
        String s = org.apache.commons.io.IOUtils.toString(gridFsResource.getInputStream(), "UTF-8");
        System.out.println(s);


    }


}
