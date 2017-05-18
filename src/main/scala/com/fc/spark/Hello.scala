package com.fc.spark

import org.apache.spark.sql.{DataFrame, SparkSession}

/**
  * spark demo
  *
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-01-09
  */
object Hello
{

    //val filePath = "spark-warehouse\\test.csv"
//-Dspark.master=spark://192.168.2.175:7077

    def main(args: Array[String]): Unit =
    {
        System.setProperty("hadoop.home.dir", "D:\\develop\\大数据\\hadoop-2.7.3")

        val session: SparkSession = SparkSession.builder().master("spark://192.168.2.175:7077").appName("okay?").getOrCreate()

        //hadoop地址：hdfs://192.168.2.175:25555
        val dataFrame: DataFrame = session.sqlContext.read.csv("hdfs://192.168.2.175:25555/home/aaron/hadoop-2.7.3/tmp/dfs/data/people.txt")

        dataFrame.show()
    }

}
