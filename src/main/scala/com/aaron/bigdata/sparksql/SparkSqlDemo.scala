package com.aaron.bigdata.sparksql

import com.aaron.bigdata.DataBaseHelper
import org.apache.spark.sql.SparkSession

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-05-26
  */
object SparkSqlDemo
{

    //val PATH = "hdfs://192.168.2.175:25555/home/aaron/hadoopData/people.txt"
    val PATH = "spark-warehouse/people.json"

    System.setProperty("hadoop.home.dir", "D:\\develop\\大数据\\hadoop-2.7.3")


    def main(args: Array[String]): Unit =
    {

        fromJdbc()
    }


    def fromLocalFile(): Unit =
    {
        val session = SparkSession.builder().master("local[2]").appName("SparkSql Demo").getOrCreate()

        val sqlContext = session.sqlContext

        val dataFrame = sqlContext.read.json(PATH)


        dataFrame.createOrReplaceTempView("people")

        val frame = sqlContext.sql("select name,age,address from people where age > 20 and age < 30")

        frame.show()
    }


    def fromJdbc(): Unit =
    {
        val session = SparkSession.builder().master("local[2]").appName("SparkSql Demo").getOrCreate()

        val sqlContext = session.sqlContext

        val df = sqlContext.read.jdbc(DataBaseHelper.MYSQL_URL, "config", DataBaseHelper.getMySqlProperties)

        //创建一个视图，后面就可以像sql一样使用该视图
        df.createOrReplaceTempView("config")

        //按type列分组统计
        df.groupBy("type").sum("type").as("总和").show()
        val some = sqlContext.sql("select t.type, count(1) from config t group by t.type")

        some.show()

        //some.foreach(row => println(row.get(4)))
    }

}
