package com.aaron.scala.kafka

import java.util
import java.util.Properties

import kafka.consumer.{ConsumerIterator, KafkaStream}
import kafka.javaapi.consumer.ConsumerConnector
import kafka.message.MessageAndMetadata
import org.apache.kafka.clients.consumer.ConsumerConfig


/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-05-19
  */
class MyKafkaConsumer extends Runnable
{
    val topic: String = "logStreaming"
    val zkAddress: String = "logStreaming"


    val msgSendSuccessCallBack = new MessageSendSuccessListener

    val props: Properties = new Properties()

    props.put("zookeeper.connect", KafkaProperty.zookeeperAddress)
    props.put("zookeeper.session.timeout.ms", "400")
    props.put("zookeeper.sync.time.ms", "200")
    props.put("auto.commit.interval.ms", "1000")

    props.put(ConsumerConfig.GROUP_ID_CONFIG, KafkaProperty.groupId)


    val consumer: ConsumerConnector = kafka.consumer.Consumer.createJavaConsumerConnector(new kafka.consumer.ConsumerConfig(props))


    override def run(): Unit =
    {
        receiveMessage()
    }


    def receiveMessage(): Unit =
    {
        while (true)
        {
            val topicCountMap: util.HashMap[String, Integer] = new util.HashMap[String, Integer]()
            topicCountMap.put(KafkaProperty.kafkaTopic, 2)

            val result: util.Map[String, util.List[KafkaStream[Array[Byte], Array[Byte]]]] = consumer.createMessageStreams(topicCountMap)

            val kafkaStream: KafkaStream[Array[Byte], Array[Byte]] = result.get(KafkaProperty.kafkaTopic).get(0)

            val consumerIterator: ConsumerIterator[Array[Byte], Array[Byte]] = kafkaStream.iterator()

            while (consumerIterator.hasNext())
            {
                val messageData: MessageAndMetadata[Array[Byte], Array[Byte]] = consumerIterator.next()

                val byteArray: Array[Byte] = messageData.message()

                println("接收到来自Kafka的消息，内容是：" + new String(byteArray))
            }
        }


    }
}
