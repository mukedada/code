package com.circle.reflection.dynamicproxy;

/**
 * @author KeWeiYang
 * @date 2018/2/28 14:03
 */
public class BookDaoTest {

    public static void main(String[] args) {
        /*BookDao dao = new BookDaoImpl();
        dao.addBook("1", "Thinking in Java!");*/

        BookDao dao = new BookDaoImpl();
        DaoProxy proxy = new DaoProxy();
        BookDao bookProxy = (BookDao) proxy.bind(dao);

        bookProxy.addBook("1", "Thinking in Java!");
    }
}
