package com.circle.reflection.dynamicproxy;

import com.circle.reflection.Book;

/**
 * @author KeWeiYang
 * @date 2018/2/28 14:03
 */
public class BookDaoImpl implements BookDao {

    @Override
    public void addBook(String id, String name) {
        System.out.println("增加一本书，name= " + name);
    }

    @Override
    public Book getBook(String id) {
        return new Book(id, "Thinking in Java");
    }
}
