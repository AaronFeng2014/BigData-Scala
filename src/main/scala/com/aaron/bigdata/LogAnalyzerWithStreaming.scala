package com.aaron.bigdata

import org.apache.spark.streaming.dstream.DStream

/**
  * 基于spark以及spark stream的日志分析
  *
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-05-23
  */
class LogAnalyzerWithStreaming[A] extends LogAnalyzer[A]
{

    override def analyzer(lines: DStream[A]): Unit =
    {
        println("开始处理来自spark streaming的消息")

        lines.foreachRDD(handleDStream _)

        //lines.foreachRDD(rdd => rdd.foreach(println))

        //lines.print()

        //lines.flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _).print()
    }
}
