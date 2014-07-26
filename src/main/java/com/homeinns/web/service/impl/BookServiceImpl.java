package com.homeinns.web.service.impl;

import com.homeinns.web.dao.impl.BaseDaoImpl;
import com.homeinns.web.entity.Book;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2014/7/23.
 */
@Service
@Transactional
public class BookServiceImpl {

    @Autowired
    public BaseDaoImpl baseDao;
    @Autowired
    private SqlSession sqlSession;
    /*保存数据对象 */
    @Transactional
    public void save(Book books) {
        if(books.getBookId()== null){
            baseDao.save("bookMapper.insert", books);
        }
        else{
            baseDao.save("bookMapper.update", books);
        }
    }


    /*根据主键删除对象 */
    public void delete(Serializable id){
        //使用SqlSession
        this.sqlSession.delete("bookMapper.deleteByPrimaryKey", id);
      //  baseDao.delete("bookMapper.deleteByPrimaryKey", id);
    }


    public List<Book> getBooks(){
        return baseDao.getList("bookMapper.selectByEntity");
    }


    /*根据条件查询获取数据对象列表 */
    public List<Book> queryResult(Book books){
        return baseDao.getList("bookMapper.selectByQuery",books);
    }


    /*根据主键获取对象 */
    public Book getBooks(Serializable id) {
        return (Book) baseDao.get("bookMapper.selectByPrimaryKey", id);
    }
}
