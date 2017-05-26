package com.aaron.bigdata.sparksql

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
        val session = SparkSession.builder().master("local[2]").appName("SparkSql Demo").getOrCreate()

        val sqlContext = session.sqlContext

        val dataFrame = sqlContext.read.json(PATH)


        dataFrame.show()

        dataFrame.groupBy("age").count().show()

        dataFrame.filter(dataFrame.col("age").lt(25)).show()

        dataFrame.printSchema()

    }
}
