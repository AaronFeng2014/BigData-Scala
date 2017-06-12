package com.aaron.scala.kafka

import org.apache.commons.logging.{Log, LogFactory}
import org.apache.kafka.clients.producer.{Callback, RecordMetadata}

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-05-19
  */
class MessageSendSuccessListener extends Callback
{
    val log: Log = LogFactory.getLog("MessageSendSuccessListener")


    override def onCompletion(metadata: RecordMetadata, exception: Exception): Unit =
    {
        try
        {
            //log.info("message has been send successfully!\npartition: " + metadata.partition() + "\ntopic:" + metadata.topic())
        }
        catch
        {
            case _ => log.error(exception.printStackTrace())
        }
    }
}
