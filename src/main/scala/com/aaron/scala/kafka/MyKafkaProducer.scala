package com.aaron.scala.kafka

import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-05-19
  */
class MyKafkaProducer extends Runnable
{
    val topic: String = "logStreaming"

    val msgSendSuccessCallBack = new MessageSendSuccessListener


    val configMap: Properties = new Properties()

    val producer: KafkaProducer[String, String] = new KafkaProducer[String, String](configMap)


    override def run(): Unit =
    {
        sendMessage()
    }


    def initData(): Unit =
    {


        //必须有的属性配置
        configMap.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaProperty.kafkaServiceAddress)
        configMap.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
        configMap.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")

        //可选的属性配置
        configMap.put(ProducerConfig.SEND_BUFFER_CONFIG, KafkaProperty.kafkaProducerBufferSize.toString)
        configMap.put(ProducerConfig.RETRIES_CONFIG, KafkaProperty.reconnectionTimes.toString)
    }


    def sendMessage(): Unit =
    {
        var index: Long = 0
        while (true)
        {
            val msg = new ProducerRecord[String, String](topic, "Kafka 测试--" + (index += 1))

            producer.send(msg, msgSendSuccessCallBack)

            println(index + " success")
            Thread.sleep(100)
        }
    }

}
