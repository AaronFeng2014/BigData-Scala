package com.aaron.bigdata

import com.aaron.bigdata.entity.InterfaceRecord
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-05-26
  */
object TextAnalyzer
{

    val logPath: String = "C:/Users/Aaron/Desktop/info.log"

    val interfaceName: String = "return success, process request"


    def splitLine(line: String): Unit =
    {
        line.split("INFO")
    }


    def main(args: Array[String]): Unit =
    {
        val sc: SparkContext = SparkContextHelper.getSparkContext(SparkContextHelper.LOCAL_MODEL, "logInfoTextAnalyzer")

        val rdd: RDD[String] = sc.textFile(logPath)

        val mappedRdd = rdd.filter(_.contains(interfaceName)).map(_.split("INFO"))

        mappedRdd.foreach(_.foreach(print(_)))


        println("总共统计数据：" + mappedRdd.count())
    }


    /**
      * 统计公司Hub系应用的info日志，分析出每分钟内各个接口的流量情况
      *
      * @param line
      */
    private[this] def handleLine(line: String): Unit =
    {
        var index: Int = line.lastIndexOf(":")

        val processTime: Long = line.substring(index + 1, line.length - 2).toLong

        index = line.lastIndexOf("INFO")

        val invokeTime: String = line.substring(0, index - 1)

        index = line.lastIndexOf("interface : ") + "interface : ".length

        val name: String = line.substring(index, line.lastIndexOf(", ip : "))
        val interfaceRecord: InterfaceRecord = InterfaceRecord(name, invokeTime, processTime)

        println(interfaceRecord)
    }
}
