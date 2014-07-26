package com.homeinns.web.controller;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Irving on 2014/7/26.
 */
@ContextConfiguration
        (
                {
                        "classpath:dispatcher-servlet.xml",
                        "classpath:/conf/spring-mybatis.xml",
                }
        )

//@ContextConfiguration(locations ="/dispatcher-servlet.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TestMyBatis {
    @Autowired
    private SqlSession sqlSession;

    @Test
    public void delete(){
        this.sqlSession.delete("bookMapper.deleteByPrimaryKey", 5);
    }
}
