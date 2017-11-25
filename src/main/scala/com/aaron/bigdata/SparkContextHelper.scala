package com.aaron.bigdata

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

    val remoteSparkMaster = "spark://192.168.1.103:7077"
    val remoteHdfsPath = "hdfs://192.168.1.103:9000"

    val LOCAL_MODEL: String = "local[2]"

    System.setProperty("hadoop.home.dir", "D:\\develop\\BigData\\hadoop-2.7.3")


    /**
      * 获取sparkContext，用于提交作业执行
      *
      * @param master   String：该参数决定运行模式是本地还是远程，如果是 local[n]表示在本地以n线程运行，如果是spark://192.168.1.103:7077表示远程提交执行
      * @param taskName String：任务名称
      *
      * @return SparkContext：sparkContext
      */
    def getSparkSession(master: String, taskName: String): SparkSession =
    {
        SparkSession.builder().master(master).appName(taskName).getOrCreate()
    }
}
