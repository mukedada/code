package com.circle.ood.factory.simpleFactory;

import java.util.Properties;

/**
 * @author:keweiyang
 * @date:Created in 下午9:05 2018/2/10
 * @description:
 */
public class ConnectionFactory {

    public static Connection create(String type, Properties info) {
/*        if ("mysql".equals(type)) {
            return new MySqlConnection(info);
        }
        if ("oracle".equals(type)) {
            return new OracleConnection(info);
        }
        throw new RuntimeException("unsupported db type=" + type);*/
        return null;
    }

}
