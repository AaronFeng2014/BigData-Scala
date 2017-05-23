package com.aaron.bigdata

import org.apache.spark.streaming.dstream.ReceiverInputDStream

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-05-23
  */
class LogAnalyzerWithKafka extends LogAnalyzer
{
    override def analyzer(lines: ReceiverInputDStream[String]): Unit =
    {
        println("接收到来自Kafka的消息，内容是：")

        //lines.print(3)(_, Time(1000))
        lines.foreachRDD((rdd) => println(rdd.take(3)))
    }
}
