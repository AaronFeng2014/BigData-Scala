package com.aaron.scala.kafka

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-05-19
  */
object KafkaProperty
{

    val zookeeperAddress: String = "192.168.2.175:12181"

    val kafkaTopic: String = "logStreaming"

    val kafkaServiceAddress = "192.168.175:9029"

    val kafkaProducerBufferSize: Int = 1024 * 64

    val groupId: String = "logConsumer-001"

    /**
      * 一分钟
      */
    val connectionTimeOut: Int = 1000 * 60

    val reconnectionTimes: Int = 10
}
