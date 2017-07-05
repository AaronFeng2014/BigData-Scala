package com.aaron.bigdata.sparksql

import org.apache.spark.sql.{DataFrame, SQLContext, SparkSession}

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-07-03
  */
object NginxLogProcess
{

    val logPath: String = "spark-warehouse/ng.log"

    System.setProperty("hadoop.home.dir", "D:\\develop\\大数据\\hadoop-2.7.3")


    def main(args: Array[String]): Unit =
    {
        val session = SparkSession.builder().master("local[2]").appName("Nginx访问日志分析").getOrCreate()

        val sqlContext: SQLContext = session.sqlContext

        val df: DataFrame = sqlContext.read.json(logPath)

        df.cache()

        df.createOrReplaceTempView("visitLog")

        val select = sqlContext.sql("select a.clientip, count(a.clientip) from visitLog a group by clientip having(count(a.clientip)) > 10000")


        //聚合结果，否则结果是散的
        val result = select.collect()

        result.foreach(row => println(row))
    }
}
