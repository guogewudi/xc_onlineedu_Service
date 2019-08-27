package com.xuecheng.test.freemarker.controller;

import com.xuecheng.test.freemarker.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * Copyright@http://github.com/guogewudi
 * Author:国宇航
 * Date:2019/8/23
 * Description:
 */
@RequestMapping("/freemarker")
@Controller
public class FreemarkerController {
    //@Autowired
   // RestTemplate restTemplate;

    @Autowired
    RestTemplate restTemplate;
    @RequestMapping("/banner")
    public String index_banner(Map<String, Object> map){
        //向url发送请求，并且通过调用服务器（我们写的cms/config/getmodel服务器程序）数据库查询来获得model并且响应以map方式返回
        ResponseEntity<Map> forEntity = restTemplate.getForEntity(
                  "http://localhost:31001/cms/config/getmodel/5a791725dd573c3574ee333f"
                ,Map.class);
        Map body = forEntity.getBody();
        map.putAll(body);
        return "index_banner";
    }
    @RequestMapping("/test1")
    public String freemarker(Map<String, Object> map){
        map.put("name","黑马程序员");
        Student stu1 = new Student();
        stu1.setName("小明");
        stu1.setAge(18);
        stu1.setMoney(1000.86f);
        stu1.setBirthday(new Date());
        Student stu2 = new Student();
        stu2.setName("小红");
        stu2.setMoney(200.1f);
        stu2.setAge(19);
// stu2.setBirthday(new Date());
        List<Student> friends = new ArrayList<>();
        friends.add(stu1);
        stu2.setFriends(friends);
        stu2.setBestFriend(stu1);
        List<Student> stus = new ArrayList<>();
        stus.add(stu1);
        stus.add(stu2);
//向数据模型放数据
        map.put("stus",stus);
//准备map数据
        HashMap<String,Student> stuMap = new HashMap<>();
        stuMap.put("stu1",stu1);
        stuMap.put("stu2",stu2);
//向数据模型放数据
        map.put("stu1",stu1);
//向数据模型放数据
        map.put("stuMap",stuMap);
//返回模板文件名称
        return "test1";
    }
}
