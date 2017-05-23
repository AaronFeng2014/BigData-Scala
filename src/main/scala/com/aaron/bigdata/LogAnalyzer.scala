package com.aaron.bigdata

import org.apache.hadoop.hdfs.protocol.datatransfer.Receiver
import org.apache.spark.sql.SparkSession
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.dstream.ReceiverInputDStream
import org.apache.spark.streaming.receiver.Receiver
import org.apache.spark.streaming.scheduler._
import org.apache.spark.streaming.{Duration, StreamingContext}

/**
  * 基于spark以及spark stream的日志分析
  *
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-05-23
  */
object LogAnalyzer
{
    //hadoop地址：hdfs://192.168.2.175:25555

    val LOG_PATH: String = "hdfs://192.168.2.175:25555/home/aaron/hadoop-2.7.3/tmp/dfs/data/people.txt"


    def main(args: Array[String]): Unit =
    {
        System.setProperty("hadoop.home.dir", "D:\\develop\\大数据\\hadoop-2.7.3")

        val session: SparkSession = SparkSession.builder().master("spark://192.168.2.175:7077").appName("LogAnalyzer").getOrCreate()

        val stream = new StreamingContext(session.sparkContext, Duration(3000))

        //val inputStream: DStream[String] = stream.textFileStream("C:\\Users\\Aaron\\Desktop")

        val lines: ReceiverInputDStream[String] = stream.socketTextStream("192.168.2.175", 12345, StorageLevel.MEMORY_ONLY)
         val result : Receiver[String] =lines.getReceiver()
        println(lines.getReceiver())
        //val words = lines.flatMap(_.split(" "))

        //val pairs = words.map((_,1))

        //pairs.reduceByKey(_ + _).print()
        //stream.addStreamingListener(new StatsReportListener(20))

        stream.start()
        stream.awaitTermination()
    }


    class LogStreamListener extends StreamingListener
    {
        override def onReceiverStarted(receiverStarted: StreamingListenerReceiverStarted): Unit =
        {
            println(receiverStarted.receiverInfo)
        }


        override def onReceiverError(receiverError: StreamingListenerReceiverError): Unit =
        {

        }


        override def onReceiverStopped(receiverStopped: StreamingListenerReceiverStopped): Unit =
        {

        }


        override def onBatchSubmitted(batchSubmitted: StreamingListenerBatchSubmitted): Unit =
        {

        }


        override def onBatchStarted(batchStarted: StreamingListenerBatchStarted): Unit =
        {

        }


        override def onBatchCompleted(batchCompleted: StreamingListenerBatchCompleted): Unit =
        {

        }


        override def onOutputOperationStarted(outputOperationStarted: StreamingListenerOutputOperationStarted): Unit =
        {

        }


        override def onOutputOperationCompleted(outputOperationCompleted: StreamingListenerOutputOperationCompleted): Unit =
        {

        }
    }


}
