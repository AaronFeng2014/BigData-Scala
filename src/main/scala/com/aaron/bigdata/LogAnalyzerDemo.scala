package com.aaron.bigdata

import org.apache.spark.sql.SparkSession
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.dstream.ReceiverInputDStream
import org.apache.spark.streaming.{Duration, StreamingContext}

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-05-23
  */
object LogAnalyzerDemo
{
    //hadoop地址：hdfs://192.168.2.175:25555

    val LOG_PATH: String = "hdfs://192.168.2.175:25555/home/aaron/hadoop-2.7.3/tmp/dfs/data/people.txt"


    def main(args: Array[String]): Unit =
    {
        System.setProperty("hadoop.home.dir", "D:\\develop\\大数据\\hadoop-2.7.3")

        val session: SparkSession = SparkSession.builder().master("spark://192.168.2.175:7077").appName("LogAnalyzer").getOrCreate()

        val stream = new StreamingContext(session.sparkContext, Duration(3000))

        val lines: ReceiverInputDStream[String] = stream.socketTextStream("192.168.2.175", 12345, StorageLevel.MEMORY_ONLY)

        //val result: Receiver[String] = lines.getReceiver()


        val analyzer: LogAnalyzer = new LogAnalyzerWithStreaming

        analyzer.analyzer(lines)

        stream.start()
        stream.awaitTermination()
    }
}
