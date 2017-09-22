package com.aaron.bigdata

import java.util

import com.aaron.scala.kafka.KafkaProperty
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.{Duration, StreamingContext}

import scala.collection.mutable

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-05-23
  */
object LogAnalyzerDemo
{

    val LOG_PATH: String = "hdfs://192.168.2.175:25555/home/aaron/hadoopData/"

    val sparkContext: SparkContext = SparkContextHelper.getSparkContext("local[10]", "LogAnalyzer")


    val stream: StreamingContext = new StreamingContext(sparkContext, Duration(3000))


    def main(args: Array[String]): Unit =
    {
        kafkaStreaming()
    }


    def socketStreaming(): Unit =
    {
        val lines: DStream[String] = stream.socketTextStream("192.168.2.175", 12345, StorageLevel.MEMORY_ONLY)

        val analyzer: LogAnalyzer[String] = new LogAnalyzerWithStreaming[String]()

        analyzer.analyzer(lines)

        stream.start()
        stream.awaitTermination()
    }


    /**
      * 监控一个目录中的文件变动，暂时发现只能用于hdfs文件系统
      */
    def fileSystemStreaming(): Unit =
    {
        val result: DStream[String] = stream.textFileStream(LOG_PATH)

        val analyzer: LogAnalyzer[String] = new LogAnalyzerWithStreaming[String]()

        analyzer.analyzer(result)
        stream.start()
        stream.awaitTermination()
    }


    /**
      * 从队列中获取数据
      */
    def queueStreaming(): Unit =
    {

        val queue = new scala.collection.mutable.SynchronizedQueue[RDD[Int]]()

        val result = stream.queueStream(queue)

        val analyzer: LogAnalyzer[Int] = new LogAnalyzerWithStreaming[Int]()


        analyzer.analyzer(result)

        stream.start()

        addContentToQueue(queue)

        stream.awaitTermination()
    }


    private[this] def addContentToQueue(queue: mutable.SynchronizedQueue[RDD[Int]]): Unit =
    {
        for (i <- 1 to 100)
        {
            queue += stream.sparkContext.makeRDD(0 to i)
        }

    }


    def kafkaStreaming(): Unit =
    {

        val paramMap = new util.HashMap[String, Object]()
        paramMap.putIfAbsent(ConsumerConfig.GROUP_ID_CONFIG, "1")
        paramMap.putIfAbsent(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaProperty.kafkaServiceAddress)
        paramMap.putIfAbsent(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
        paramMap.putIfAbsent(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
        paramMap.putIfAbsent(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")


        val list: java.util.List[String] = new util.ArrayList[String]()
        list.add(KafkaProperty.kafkaTopic)
        val consumerStrategy = ConsumerStrategies.Subscribe[String, String](list, paramMap)

        val kafka = KafkaUtils.createDirectStream(stream, LocationStrategies.PreferConsistent, consumerStrategy)

        /*kafka.foreachRDD(rdd =>
        {
            rdd.foreach(record =>
            {
                print(record.value())
            })
        })*/

        kafka.foreachRDD(rdd =>
        {
            rdd.foreachPartition(record =>
            {
                while (record.hasNext)
                {
                    println(record.next())
                }
            })
        })

        stream.start()
        stream.awaitTermination()
    }


}
