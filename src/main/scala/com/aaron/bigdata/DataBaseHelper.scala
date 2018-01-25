package com.aaron.bigdata

import java.sql.{Connection, DriverManager}
import java.util.Properties

/**
  * 数据库连接属性配置
  *
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-05-27
  */
object DataBaseHelper
{
    val MYSQL_URL = "jdbc:mysql://192.168.2.175:3306/disconf"

    val MYSQL_DRIVER = "com.mysql.cj.jdbc.Driver"


    def getConnection(url: String, username: String, password: String): Connection =
    {
        Class.forName("com.mysql.jdbc.Driver")

        DriverManager.getConnection(url, username, password)
    }


    def getMySqlProperties: Properties =
    {
        val properties = new Properties()

        /**
          * 用户名的key是user，而不是username
          */
        properties.put("user", "root")
        properties.put("password", "fangcang")
        properties.put("driver", MYSQL_DRIVER)

        properties
    }


    object MySql
    {
        val URL = "jdbc:mysql://192.168.2.175:3306/disconf"

        val DRIVER_CLASS = "com.mysql.jdbc.Driver"


        def getConnectionProperties(): Properties =
        {
            val properties = new Properties()

            /**
              * 用户名的key是user，而不是username
              */
            properties.put("user", "root")
            properties.put("password", "fangcang")
            properties.put("driver", MYSQL_DRIVER)

            properties
        }
    }


    object MySql122
    {
        val URL = "jdbc:mysql://192.168.1.122:3306/hotelsupplier"

        val DRIVER_CLASS = "com.mysql.jdbc.Driver"


        def getConnectionProperties(): Properties =
        {
            val properties = new Properties()

            /**
              * 用户名的key是user，而不是username
              */
            properties.put("user", "hotel")
            properties.put("password", "hot4el")
            properties.put("driver", MYSQL_DRIVER)

            properties
        }
    }


    object Oracle
    {
        val URL = "jdbc:mysql://192.168.2.175:3306/disconf"

        val DRIVER_CLASS = "com.mysql.jdbc.Driver"


        def getConnectionProperties(): Properties =
        {
            val properties = new Properties()

            /**
              * 用户名的key是user，而不是username
              */
            properties.put("user", "root")
            properties.put("password", "fangcang")
            properties.put("driver", MYSQL_DRIVER)

            properties
        }
    }


}
