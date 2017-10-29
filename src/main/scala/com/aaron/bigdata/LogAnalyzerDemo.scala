package com.aaron.bigdata

import java.util

import com.aaron.scala.kafka.KafkaProperty
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{SQLContext, SparkSession}
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.{Seconds, StreamingContext}

import scala.collection.mutable

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-05-23
  */
object LogAnalyzerDemo
{

    val LOG_PATH: String = "hdfs://192.168.2.175:25555/home/aaron/hadoopData/"

    val sparkSession: SparkSession = SparkContextHelper.getSparkSession("local[10]", "LogAnalyzer")


    val stream: StreamingContext = new StreamingContext(sparkSession.sparkContext, Seconds(10))


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
        paramMap.putIfAbsent(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArrayDeserializer")
        paramMap.putIfAbsent(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArrayDeserializer")
        /**
          * earliest
          * latest
          * none
          */
        paramMap.putIfAbsent(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest")
        paramMap.putIfAbsent(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, java.lang.Boolean.FALSE)


        val list: java.util.List[String] = new util.ArrayList[String]()
        list.add(KafkaProperty.kafkaTopic)
        val consumerStrategy = ConsumerStrategies.Subscribe[String, Array[Byte]](list, paramMap)

        val kafka = KafkaUtils.createDirectStream[String, Array[Byte]](stream, LocationStrategies.PreferConsistent, consumerStrategy)


        kafka.start()

        kafka.foreachRDD(rdd =>
        {
            if (!rdd.isEmpty())
            {

                val json = rdd.map[String](record =>
                {
                    val bytes: Array[Byte] = record.value()
                    new String(bytes, "UTF-8")

                })

                sql(json)
            }
        })

        /*  kafka.window(Seconds(10)).foreachRDD(rdd =>
          {
              println("10秒访问量====>" + rdd.count())
          })*/

        stream.start()
        stream.awaitTermination()
    }


    val sqlContext: SQLContext = sparkSession.sqlContext


    def sql(content: RDD[String]): Unit =
    {

        import sqlContext.implicits._

        val dataSet = sqlContext.createDataset[String](content)

        val dataFrame = sqlContext.read.json(dataSet)

        dataFrame.cache()

        dataFrame.createOrReplaceTempView("visitLog")

        val select = sqlContext.sql("select a.methodName, count(1) as total from visitLog a group by a.methodName")


        //聚合结果，否则结果是散的
        val result = select.collect()

        println("--------------> 输出结果<---------------------------")
        result.foreach(row => println(row))
    }

}
