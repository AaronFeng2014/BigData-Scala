package com.aaron.scala.kafka

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-05-19
  */
object KafkaProperty
{

    /**
      * zookeeper地址
      */
    val zookeeperAddress: String = "192.168.1.150:12181"

    /**
      * topic
      */
    val kafkaTopic: String = "hotel-api-log-data"

    /**
      * kafka单机伪集群
      */
    val kafkaServiceAddress = "192.168.1.150:9091,192.168.1.150:9092,192.168.1.150:9093"

    val kafkaProducerBufferSize: Int = 1024 * 64

    val groupId: String = "logConsumer-001"

    /**
      * 一分钟
      */
    val connectionTimeOut: Int = 1000 * 60

    val reconnectionTimes: Int = 10
}
