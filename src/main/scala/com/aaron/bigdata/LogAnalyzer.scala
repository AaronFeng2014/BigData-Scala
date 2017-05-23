package com.aaron.bigdata

import org.apache.spark.streaming.dstream.ReceiverInputDStream

/**
  * 定义解析消息的特质，类似于Java的接口
  *
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-05-23
  */
trait LogAnalyzer
{
    def analyzer(lines: ReceiverInputDStream[String]): Unit
}
