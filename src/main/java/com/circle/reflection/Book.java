package com.circle.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author KeWeiYang
 */
public class Book {

    private String id;
    private String name;
    public Book() {

    }

    public Book(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public static void main(String[] args) {
        //viewClassInfo();
        createObjectByName();
    }

    private static void viewClassInfo() {
        Book book = new Book("1", "Thinking in Java");
        Class clz = book.getClass();

        System.out.println("包名：" + clz.getPackage().getName());
        System.out.println("超类：" + clz.getSuperclass());

        System.out.println("字段--------------------------------");
        // 获取所有级别的字段
        Field[] fields = clz.getDeclaredFields();
        // 获取public字段
       // Field[] fields = clz.getFields();
        for(int i =0;i<fields.length;i++) {
            Class type = fields[i].getType();
            System.out.println(type.getName()+" "+fields[i].getName());
        }

        System.out.println("构造器-------------------------------");
        Constructor[] constructors = clz.getDeclaredConstructors();
        for(int i =0;i<constructors.length;i++) {
            Class[] types = constructors[i].getParameterTypes();
            for(int j=0;j<types.length;j++) {
                System.out.println(types[j].getName());
            }
        }

        System.out.println("方法---------------------------------");
        Method[] methods = clz.getDeclaredMethods();
        for(int i = 0;i<methods.length;i++) {
            String name = methods[i].getName();
            Class returnType = methods[i].getReturnType();
            Class[] paramType = methods[i].getParameterTypes();
            StringBuffer sb = new StringBuffer();
            sb.append(returnType.getName()).append(" ").append(name).append("(");

            for(int j=0;j<paramType.length;j++) {
                sb.append(paramType[j].getName()).append(" ");
            }
            sb.append(")");

            System.out.println(sb.toString());
        }
    }

    private static void createObjectByName() {
        try {
            // 根据类名装载一个类
            Class clz = Class.forName("com.circle.reflection.Book");
            // 创建一个类的实例
            Object obj = clz.newInstance();
            // 得到一个方法的引用
            Method method = clz.getDeclaredMethod("setId", java.lang.String.class);
            // 调用该方法
            method.invoke(obj, "100");

            Book book = (Book) obj;
            System.out.println(book.getId());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
