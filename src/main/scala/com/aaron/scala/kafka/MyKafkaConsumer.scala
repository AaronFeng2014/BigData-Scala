package com.aaron.scala.kafka

import java.util
import java.util.Properties

import kafka.consumer.{ConsumerIterator, KafkaStream}
import kafka.javaapi.consumer.ConsumerConnector
import kafka.message.MessageAndMetadata
import org.apache.kafka.clients.consumer.ConsumerConfig

import scala.collection.JavaConversions._


/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-05-19
  */
class MyKafkaConsumer extends Runnable
{
    val msgSendSuccessCallBack = new MessageSendSuccessListener

    val props: Properties = new Properties()

    props.put("zookeeper.connect", KafkaProperty.zookeeperAddress)
    props.put("zookeeper.session.timeout.ms", "10000")
    props.put("zookeeper.sync.time.ms", "2000")
    props.put("auto.commit.interval.ms", "1000")

    props.put(ConsumerConfig.GROUP_ID_CONFIG, KafkaProperty.groupId)


    val consumer: ConsumerConnector = kafka.consumer.Consumer.createJavaConsumerConnector(new kafka.consumer.ConsumerConfig(props))


    override def run(): Unit =
    {
        receiveMessage()
    }


    def receiveMessage(): Unit =
    {

        val topicCountMap: util.HashMap[String, Integer] = new util.HashMap[String, Integer]()

        /**
          * 该map对象的value表示消费者的线程数量，key表示消费的主题
          */
        topicCountMap.put(KafkaProperty.kafkaTopic, 1)

        val result: util.Map[String, util.List[KafkaStream[Array[Byte], Array[Byte]]]] = consumer.createMessageStreams(topicCountMap)

        val kafkaStreamList: util.List[KafkaStream[Array[Byte], Array[Byte]]] = result.get(KafkaProperty.kafkaTopic)

        for (kafkaStream: KafkaStream[Array[Byte], Array[Byte]] <- kafkaStreamList)
        {
            new Thread()
            {
                override def run(): Unit =
                {
                    val consumerIterator: ConsumerIterator[Array[Byte], Array[Byte]] = kafkaStream.iterator()

                    while (consumerIterator.hasNext())
                    {
                        val messageData: MessageAndMetadata[Array[Byte], Array[Byte]] = consumerIterator.next()

                        val byteArray: Array[Byte] = messageData.message()

                        println("接收到来自Kafka的消息，内容是：" + new String(byteArray))
                    }
                }
            }.start()

        }
    }
}
