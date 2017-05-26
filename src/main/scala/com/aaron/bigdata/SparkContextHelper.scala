package com.aaron.bigdata

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

/**
  * 获取SparkContext对象，这个对象是和Spark交互的唯一入口
  *
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-05-25
  */
object SparkContextHelper
{

    //hadoop地址：hdfs://192.168.2.175:25555

    //spark master地址：spark://192.168.2.175:7077

    //Standalone集群模式提交的作业需要上传jar文件，Local模式不需要

    //session.sparkContext.addJar("D:\\Code\\GitHub\\BigData-Scala\\classes\\artifacts\\bigdata_scala_jar\\bigdata-scala.jar")

    val LOCAL_MODEL: String = "local[2]"

    System.setProperty("hadoop.home.dir", "D:\\develop\\大数据\\hadoop-2.7.3")


    def getSparkContext(master: String, taskName: String): SparkContext =
    {
        SparkSession.builder().master(master).appName(taskName).getOrCreate().sparkContext
    }
}
