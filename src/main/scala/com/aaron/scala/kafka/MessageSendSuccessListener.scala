package com.aaron.scala.kafka

import org.apache.kafka.clients.producer.{Callback, RecordMetadata}

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-05-19
  */
class MessageSendSuccessListener extends Callback
{
    override def onCompletion(metadata: RecordMetadata, exception: Exception): Unit =
    {
        println("message send success! and the message send to")
    }
}
