package com.aaron.ml

import com.aaron.bigdata.SparkContextHelper
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

/**
  * 分层抽样
  *
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-07-05
  */
object ExactSample
{

    def main(args: Array[String]): Unit =
    {
        val ageTuple = 1 to 50 map ((key: Int) => ("age", key.toDouble))
        val weekTuple = 51 to 100 map ((key: Int) => ("week", key.toDouble))
        val monthTuple = 101 to 150 map ((key: Int) => ("month", key.toDouble))
        val yearTuple = 151 to 200 map ((key: Int) => ("year", key.toDouble))


        val sc: SparkContext = SparkContextHelper.getSparkContext("local[2]", "test")

        val data: RDD[(String, Double)] = sc.makeRDD(ageTuple.++(weekTuple).++(monthTuple).++(yearTuple))

        /**
          * 分层抽样：每个特征抽取的比例
          * key是待抽取的特征
          * value是对应特征抽取的比例
          */
        val partitions: Map[String, Double] = Map("age" -> 0.2, "week" -> 0.2, "month" -> 0.1, "year" -> 0.25)

        /**
          * 简单的分层抽样
          */
        var result: RDD[(String, Double)] = data.sampleByKey(withReplacement = false, partitions)

        println("==============简单的分层抽样===============")
        result.collect().foreach(println)
        /**
          * 精确抽样： 可以提供高达99.99的置信度
          */
        result = data.sampleByKeyExact(withReplacement = false, partitions)
        println("==============精确的分层抽样===============")
        result.collect().foreach(println)
    }
}
