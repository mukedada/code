package com.circle.reflection.dynamicproxy;

import com.circle.reflection.Book;

/**
 * @author KeWeiYang
 */
public interface BookDao {
     /**
      *  增加Book
      * @param id
      * @param name
      */
     void addBook(String id, String name);

     /**
      * 根据Id获取Book
      * @param id
      * @return
      */
     Book getBook(String id);

}
