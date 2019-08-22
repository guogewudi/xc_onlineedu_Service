package com.xuecheng.manage_cms.dao;


import com.xuecheng.framework.domain.cms.CmsPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.List;
import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsPageRepositoryTest {
    @Autowired
    private CmsPageRepository cmsPageRepository;

     @Test
    public void testFindALL(){
         //mongodb的 MongoRepository接口自动映射数据
         List<CmsPage> all = cmsPageRepository.findAll();
         System.out.println(all);
     }

     @Test
    public void testFindByPage(){
        //mongodb的 MongoRepository接口自动映射数据
        Pageable pageable = (Pageable) PageRequest.of(1,10);
        Page<CmsPage> all = cmsPageRepository.findAll(pageable);
        System.out.println(all);
    }

    @Test
    public void findByExample(){
         CmsPage cmsPage = new CmsPage();
        //cmsPage.setSiteId("5a92141cb00ffc5a448ff1a0");
//模板ID
       // cmsPage.setTemplateId("5a751fab6abb5044e0d19ea1");

        cmsPage.setPageAliase("轮播图");

        ExampleMatcher exampleMatcher = ExampleMatcher.matching();
        exampleMatcher = exampleMatcher.withMatcher("pageAliase",
                ExampleMatcher.GenericPropertyMatchers.contains());


        Example<CmsPage> example = Example.of(cmsPage,exampleMatcher);

        Pageable pageable = PageRequest.of(0,10);
        Page<CmsPage> all = cmsPageRepository.findAll(example,pageable);
        List<CmsPage> content = all.getContent();
        System.out.println(content);

    }
    @Test
        public void updateTest(){
            Optional<CmsPage> op = cmsPageRepository.findById("5a7be667d019f14d90a1fb1c");
            if(op.isPresent()){
                CmsPage cmsPage = op.get();
                cmsPage.setPageAliase("葵花宝典");
                cmsPageRepository.save(cmsPage);
            }
        }

}
