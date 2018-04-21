package com.circle.crawler;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;


import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestCnkiNew {
    // 业务流程
    // 1： 访问
    // http://epub.cnki.net/kns/brief/result.aspx?dbprefix=scdb&action=scdbsearch&db_opt=SCDB
    // 获取cookie 及生成userKey
    // 2:
    public static final String encoding = "UTF-8";
    public static String cookie = "";

    // 程序入口
    public static void main(String[] args) {
        String keyword = "DBA";
        String url = "http://epub.cnki.net/KNS/request/SearchHandler.ashx?action=&NaviCode=*&ua=1.21&PageName=ASP.brief_result_aspx&DbPrefix=SCDB&DbCatalog=%e4%b8%ad%e5%9b%bd%e5%ad%a6%e6%9c%af%e6%96%87%e7%8c%ae%e7%bd%91%e7%bb%9c%e5%87%ba%e7%89%88%e6%80%bb%e5%ba%93&ConfigFile=SCDB.xml&db_opt=CJFQ%2CCJFN%2CCDFD%2CCMFD%2CCPFD%2CIPFD%2CCCND%2CCCJD%2CHBRD&base_special1=%25&magazine_special1=%25&au_1_sel=AU&au_1_sel2=AF&au_1_value2="
                + keyword
                + "&au_1_special1=%3D&au_1_special2=%25&his=0&__=Tue%20Mar%2025%202014%2022%3A19%3A36%20GMT%2B0800";
        cookie = getAllCookie(url);
        System.out.println("cookie:    "+cookie);
        String detailUrl = "http://epub.cnki.net/kns/brief/brief.aspx?pagename=ASP.brief_result_aspx&dbPrefix=SCDB&dbCatalog=%e4%b8%ad%e5%9b%bd%e5%ad%a6%e6%9c%af%e6%96%87%e7%8c%ae%e7%bd%91%e7%bb%9c%e5%87%ba%e7%89%88%e6%80%bb%e5%ba%93&ConfigFile=SCDB.xml&research=off&t=1395757176794&keyValue=&S=1";
        String fileNames = getFileNames(detailUrl, cookie);

        String innerUrl = "http://epub.cnki.net/kns/ViewPage/viewsave.aspx?TablePre=SCDB&displayMode=selfDefine";
        String overHtml = downloadPost(new DefaultHttpClient(), innerUrl,
                fileNames);

        String regex = "(SrcDatabase-来源库: .*?)<br>(Title-题名:.*?)<br>(Author-作者:.*?)<br>";
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(overHtml);
        int i = 0;
        while (m.find()) {
            i++;
            System.out.println(i);
            System.out.println(" [ " + m.group(1).trim() + " ]");
            System.out.println(" [ " + m.group(2).trim() + " ]");
            System.out.println(" [ " + m.group(3).trim() + " ]");
        }

    }

    // 业务流
    public static String getFileNames(String url, String cookie) {
        StringBuffer sb = new StringBuffer();
        // 新建客户端
        DefaultHttpClient client = new DefaultHttpClient();
        // 请求超时
        client.getParams().setParameter(
                CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
        // 读取超时
        client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10000);

        System.out.println("开始处理==>" + url);
        String html = downloadGet(new DefaultHttpClient(), url, cookie,
                encoding);
        String regex = "value=\"(.*?)\"  onclick=\"che";
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(html);
        int i = 0;
        while (m.find()) {
            i++;
            System.out.println(i + " : [ " + m.group(1).trim() + " ]");
            sb.append(m.group(1).trim() + ",");
        }
        client.getConnectionManager().shutdown();
        return sb.toString().substring(0, sb.length() - 1);
        // 关闭客户端
    }

    /**
     * get方式下载
     *
     * @param url
     */
    public static String downloadGet(DefaultHttpClient client, String url,
                                     String cookie, String encoding) {
        String htmls = null;
        // 新建get请求
        HttpGet httpGet = new HttpGet(url);
        // 封装请求头
        HashMap<String, String> headerMap = new LinkedHashMap<String, String>();
        headerMap.put("Host", "epub.cnki.net");
        headerMap.put("Cookie", cookie);
        headerMap
                .put("User-Agent",
                        "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36");
        // 封装请求头 判断封装
        if (null != headerMap && headerMap.size() > 0) {
            for (Map.Entry<String, String> header : headerMap.entrySet()) {
                httpGet.setHeader(header.getKey(), header.getValue());
            }
        }
        // 声明响应
        HttpResponse response = null;
        // 响应实体
        HttpEntity entity = null;
        try {
            response = client.execute(httpGet);
            System.out.println("响应码： "
                    + response.getStatusLine().getStatusCode());
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                entity = response.getEntity();
                // List<Cookie> cookies = client.getCookieStore().getCookies();
                byte[] content = EntityUtils.toByteArray(entity);
                htmls = new String(content, encoding);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            sleep(1000);
        }
        return htmls;
    }

    /**
     * 得到全cookie
     *
     * @param url
     * @return
     */
    public static String getAllCookie(String url) {
        String allCookie = "";
        DefaultHttpClient client = new DefaultHttpClient();
        allCookie = "cnkiUserKey=" + getNewGuid() + "; RsPerPage=20; "
                + getCookie(client, url, encoding);
        return allCookie.trim();
    }

    /**
     * get方式下载
     *
     * @param url
     */
    private static String getCookie(DefaultHttpClient client, String url,
                                    String encoding) {
        String currentCookie = "";
        // 新建get请求
        HttpGet httpGet = new HttpGet(url);
        // 封装请求头
        HashMap<String, String> headerMap = new LinkedHashMap<String, String>();
        headerMap.put("Host", "epub.cnki.net");
        headerMap
                .put("Referer",
                        "http://epub.cnki.net/kns/brief/result.aspx?dbprefix=scdb&action=scdbsearch&db_opt=SCDB");
        headerMap
                .put("User-Agent",
                        "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36");
        // 封装请求头 判断封装
        if (null != headerMap && headerMap.size() > 0) {
            for (Map.Entry<String, String> header : headerMap.entrySet()) {
                httpGet.setHeader(header.getKey(), header.getValue());
            }
        }
        // 声明响应
        HttpResponse response = null;
        // 响应实体
        try {
            response = client.execute(httpGet);
            System.out.println("响应码： "
                    + response.getStatusLine().getStatusCode());
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                List<Cookie> cookies = client.getCookieStore().getCookies();
                for (Cookie cookie : cookies) {
                    currentCookie = currentCookie + cookie.getName() + "="
                            + cookie.getValue() + "; ";
                }
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            sleep(1000);
        }
        return currentCookie;
    }

    /**
     * 生成cnkiUserKey
     *
     * @return
     */
    private static String getNewGuid() {
        String guid = "";
        for (int i = 1; i <= 32; i++) {
            String n = Integer
                    .toHexString(((int) Math.floor(Math.random() * 16)));
            ;
            guid += n;
            if ((i == 8) || (i == 12) || (i == 16) || (i == 20))
                guid += "-";
        }
        return guid;
    }

    /**
     * get方式下载
     *
     * @param url
     */
    public static String downloadPost(DefaultHttpClient client, String url,
                                      String fileNames) {
        String htmls = null;

        HttpPost httpPost = new HttpPost(url);
        // 封装请求头
        packRequestHeader(httpPost);
        // 封装请求实体
        String requestBody = "formfilenames=" + fileNames;
        Charset charset = Charset.forName(encoding);
        httpPost.setEntity(new StringEntity(requestBody, charset));
        // 声明响应
        HttpResponse response = null;
        // 响应实体
        HttpEntity entity = null;
        try {
            response = client.execute(httpPost);
            System.out.println("响应码： "
                    + response.getStatusLine().getStatusCode());
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                entity = response.getEntity();
                byte[] content = EntityUtils.toByteArray(entity);
                htmls = new String(content, encoding);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            sleep(1000);
        }
        return htmls;
    }

    /**
     * @declare 封装请求头
     * @param httpPost
     * @author cphmvp
     */
    private static void packRequestHeader(HttpPost httpPost) {
        HashMap<String, String> headerMap = new LinkedHashMap<String, String>();
        headerMap.put("Cookie", cookie);
        headerMap.put("Host", "epub.cnki.net");
        // 此参数必须
        headerMap.put("Content-Type", "application/x-www-form-urlencoded");
        headerMap
                .put("Referer",
                        "http://epub.cnki.net/kns/brief/brief.aspx?pagename=ASP.brief_result_aspx&dbPrefix=SCDB&dbCatalog=%e4%b8%ad%e5%9b%bd%e5%ad%a6%e6%9c%af%e6%96%87%e7%8c%ae%e7%bd%91%e7%bb%9c%e5%87%ba%e7%89%88%e6%80%bb%e5%ba%93&ConfigFile=SCDB.xml&research=off&t=1395760230280&keyValue=&S=1");
        headerMap
                .put("User-Agent",
                        "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36");
        // 封装请求头 判断封装
        if (null != headerMap && headerMap.size() > 0) {
            for (Map.Entry<String, String> header : headerMap.entrySet()) {
                httpPost.setHeader(header.getKey(), header.getValue());
            }
        }
    }

    /**
     * @declare 休眠
     * @param time
     * @author cphmvp
     */
    private static void sleep(long time) {
        try {
            System.out.println("-------线线程休眠：" + time + " 毫秒-----------");
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
