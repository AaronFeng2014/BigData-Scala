package com.aaron.scala.kafka

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-06-14
  */
object KafkaProducerStart extends App
{
    start()


    def start(): Unit =
    {
        val producer: MyKafkaProducer = new MyKafkaProducer()

        new Thread(producer, "消息生产者线程").start()
    }
}
