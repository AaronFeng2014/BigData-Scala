package com.aaron.bigdata

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
        println("接收到来自spark streaming的消息，内容是")

        lines.foreachRDD((rdd) => println(rdd.take(10).take(10).foreach(println(_)).toString))
    }

}
