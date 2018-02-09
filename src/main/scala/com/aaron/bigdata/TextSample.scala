package com.aaron.bigdata

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2018/1/17
  */
object TextSample
{

    val PATH = "spark-warehouse/ezeegocity.txt"

    System.setProperty("hadoop.home.dir", "/Users/albert/pkfare/develop/大数据/hadoop-2.7.3")


    val sc: SparkContext = SparkContextHelper.getSparkSession("local[2]", "test").sparkContext


    def main(args: Array[String]): Unit =
    {

        val rdd: RDD[(String, Int)] = sc.textFile(PATH).map(line => (line.split("\t")(1), 1)).reduceByKey(_ + _)


        println("===================")

        println("未重后的大小---->" + rdd.keys.count())
        println("去重后的大小---->" + rdd.keys.distinct().count())
        rdd.filter((a) => a._2 > 1).foreach(a => println(a._1 + "--->" + a._2))
        println("===================")
    }

}
