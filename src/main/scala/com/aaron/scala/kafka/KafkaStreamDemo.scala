package com.aaron.scala.kafka

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-05-19
  */
object KafkaStreamDemo
{
    def main(args: Array[String]): Unit =
    {
        val producer: MyKafkaProducer = new MyKafkaProducer()
        new Thread(producer, "消息生产者线程").start()

        val consumer: MyKafkaConsumer = new MyKafkaConsumer()
        new Thread(consumer, "消息消费者线程").start()
    }

}
