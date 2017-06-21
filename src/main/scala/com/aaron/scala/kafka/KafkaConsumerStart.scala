package com.aaron.scala.kafka

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-06-14
  */
object KafkaConsumerStart extends App
{

    start()


    def start(): Unit =
    {
        val consumer: MyKafkaConsumer = new MyKafkaConsumer()

        new Thread(consumer, "消息消费者线程").start()
    }
}
