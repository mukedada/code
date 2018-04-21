package com.circle.regular;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author KeWeiYang
 * @date 2018/3/10 下午12:35
 * 学习正则表达式
 * 正则表达式匹配流程：
 * 1. 对匹配规则生成Pattern类；2. 将Pattern类匹配字符串。这是典型的NFA匹配模型，而不是DFA匹配模型
 * 注意：Java中的正则表达式在默认情况下单行模式，即不管字符串中有没有换行，Java中只认为是一行的
 * 同时，Java中提供参数Pattern.MULTILINE来修改为多行模式。
 *
 * Java默认情况下是区分大小写的,如果需要修改，则修改标志位Pattern.CASE_INSENSITIVE
 *
 */
public class RegularExpressionText {

    private static final String TEXT = "abc\ndef\nDEF";

    public static void main(String[] args) {
        System.out.println(TEXT);
        Pattern pattern = Pattern.compile("^def$",Pattern.MULTILINE);

        Matcher matcher = pattern.matcher(TEXT);

        if (matcher.find()) {
            System.out.println("匹配正确！");

        } else {
            System.out.println("没有匹配到！");

        }

        // 分组测试
        System.out.println("------------分组测试------------");
        Pattern p = Pattern.compile("([a-z]+)-(\\d)");
        Matcher matcher1 = p.matcher("type x-235,type y-3,type zw-465");
        if (matcher1.find()) {
            for(int i=0;i<matcher1.groupCount()+1;i++) {
                System.out.println(matcher1.group(i));

            }
        }

        // 字符串分隔测试
        System.out.println("------------字符串分隔测试------------");
        // 分隔后的包含空白字符
        String s[] = "a////b/c".split("/");
        System.out.println(s.length);
        for (String str : s) {
            System.out.println(str);
        }
        System.out.println("-----------------");
        String s2[] = "a///b/c".split("/+");
        for (String str : s2) {
            System.out.println(str);

        }

        System.out.println("------------字符串替换测试------------");
        Pattern replacePattern = Pattern.compile("(\\d+)\\s*(yuan)");
        Matcher replaceMacher = replacePattern.matcher("Orange is 100yuan,Apple is 100 yuan");
        if (replaceMacher.find()) {
            System.out.println("匹配正确");

            for(int i=0;i<replaceMacher.groupCount()+1;i++) {
                System.out.println(replaceMacher.group(i));

            }
            String result = replaceMacher.replaceAll("$2 $1");
            System.out.println(result);
        }

    }
}

