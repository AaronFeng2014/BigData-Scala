package com.aaron.scala.akka.actor

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props}
import akka.dispatch.MessageDispatcher

import scala.concurrent.Future

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2018-03-29
  */
object BlockingActorSample {

  val system = ActorSystem("BlockingActorSample")

  val blockingActorA: ActorRef = system.actorOf(Props[BlockingActorA], "blockingActorA")

  val blockingActorB: ActorRef = system.actorOf(Props[BlockingActorB], "blockingActorB")

  def main(args: Array[String]): Unit = {

    (1 to 10) foreach (blockingActorA ! _)
    println("A 所有消息发送结束")

    Thread.sleep(3000)
    (1 to 10) foreach (blockingActorB ! _)
    println("B 所有消息发送结束")

    (11 to 20) foreach (blockingActorB ! _)
    println("again B 所有消息发送结束")
  }

  class BlockingActorA extends Actor with ActorLogging {

    implicit val executionContext: MessageDispatcher = context.system.dispatchers.lookup("blocking-io-dispatcher")
    override def receive: Receive = {

      case any: Any =>
        Future {
          log.info(this.getClass + ",接收到指令：{}", any)
          Thread.sleep(600)
        }
    }
  }

  class BlockingActorB extends Actor with ActorLogging {

    //指定阻塞IO的操作在单独的线程中执行，做到线程隔离
    implicit val executionContext: MessageDispatcher = context.system.dispatchers.lookup("blocking-io-dispatcher")
    override def receive: Receive = {

      case any: Any =>
        Future {
          log.info(this.getClass + ",接收到指令：{}", any)
          Thread.sleep(100)
        }
    }
  }

}
