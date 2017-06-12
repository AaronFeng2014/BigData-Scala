package com.aaron.scala.kafka

import java.util.Properties

import org.apache.commons.logging.{Log, LogFactory}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-05-19
  */
class MyKafkaProducer extends Runnable
{
    val log: Log = LogFactory.getLog("MyKafkaProducer")

    val msgSendSuccessCallBack = new MessageSendSuccessListener

    val configMap: Properties = initData()

    val producer: KafkaProducer[String, String] = new KafkaProducer[String, String](configMap)


    override def run(): Unit =
    {
        sendMessage()
    }


    def initData(): Properties =
    {

        val config: Properties = new Properties()

        //必须有的属性配置
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaProperty.kafkaServiceAddress)

        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")

        //可选的属性配置
        config.put(ProducerConfig.SEND_BUFFER_CONFIG, KafkaProperty.kafkaProducerBufferSize.toString)
        config.put(ProducerConfig.RETRIES_CONFIG, KafkaProperty.reconnectionTimes.toString)
        config.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, "3000")

        config
    }


    def sendMessage(): Unit =
    {
        var index: Long = 0
        while (true)
        {
            index = index + 1
            val value = "Kafka 测试--test" + index
            val msg = new ProducerRecord[String, String](KafkaProperty.kafkaTopic,  value)

            producer.send(msg, msgSendSuccessCallBack)

            //log.info("发送消息为：" + value)
        }
    }

}
