package com.homeinns.web.dao.impl;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2014/7/23.
 */
@Repository
public   class BaseDaoImpl <T>extends SqlSessionDaoSupport {
    @Resource
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }
    public void save(String key, Object object) {
        getSqlSession().insert(key, object);
    }

    public void delete(String key, Serializable id) {
        getSqlSession().delete(key, id);
    }

    public void delete(String key, Object object) {
        getSqlSession().delete(key, object);
    }

    public <T> T get(String key, Object params) {
        return getSqlSession().selectOne(key, params);
    }

    public <T> List<T> getList(String key) {
        return getSqlSession().selectList(key);
    }

    public <T> List<T> getList(String key, Object params) {
        return getSqlSession().selectList(key, params);
    }
}