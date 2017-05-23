package com.aaron.bigdata

import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.dstream.ReceiverInputDStream

/**
  * 基于spark以及spark stream的日志分析
  *
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-05-23
  */
class LogAnalyzerWithStreaming extends LogAnalyzer
{

    override def analyzer(lines: ReceiverInputDStream[String]): Unit =
    {
        println("开始处理来自spark streaming的消息")

        lines.foreachRDD(handleDStream _)

        lines.flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _).print()
    }


    private[this] def handleDStream(rdd: RDD[String]): Unit =
    {
        rdd.foreach(print(_))
    }
}
