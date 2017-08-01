package com.lance.mongo.test;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.CommandResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

/**
 * Author Lance.
 * Date: 2017-07-19 15:50
 * Desc:
 */
@ContextConfiguration("classpath:spring/applicationContext-mongo.xml")
public class MongoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private MongoTemplate mongoTemplate;


    @Test
    public void find() {
        String command = "{'distinct':'users','key':'_id'}";
        command = "{'find':'users'},{'name':'fff'}";
        CommandResult commandResult = this.mongoTemplate.executeCommand(command);
        System.out.println(commandResult);
    }

}
