package com.aarom.bigdata

import org.apache.spark.sql.{DataFrame, SparkSession}

/**
  * spark作业提交
  *
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-04-04
  */
object SparkLearning
{

    val jsonPath: String = "C:/Users/Aaron/Desktop/people.csv"


    def main(args: Array[String]): Unit =
    {
        System.setProperty("hadoop.home.dir", "D:\\Develop\\BigData\\hadoop-2.7.3")

        val session: SparkSession = org.apache.spark.sql.SparkSession.builder().appName("Spark Learning Test").getOrCreate()

        val dataFrame: DataFrame = session.sqlContext.read.csv(jsonPath)

        dataFrame.show()
    }
}
