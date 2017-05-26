package com.aaron.bigdata

import org.apache.spark.streaming.dstream.DStream

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-05-23
  */
class LogAnalyzerWithKafka[A] extends LogAnalyzer[A]
{
    override def analyzer(lines: DStream[A]): Unit =
    {
        println("接收到来自Kafka的消息，内容是：")

        //lines.print(3)(_, Time(1000))
        lines.foreachRDD((rdd) => println(rdd.take(3)))
    }
}
