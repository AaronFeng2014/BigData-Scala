package com.aaron.scala.akka.actor

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props}
import akka.dispatch.{BoundedMessageQueueSemantics, RequiresMessageQueue}
import com.aaron.scala.akka.actor.ActorWithBoundedMailBoxSample.ActorWithBoundedMailBox

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2018-03-29
  */
object ActorWithBoundedMailBoxSample {

  def main(args: Array[String]): Unit = {

    val system = ActorSystem("ActorWithBoundedMailBoxSample")

    val actorWithBoundedMailBox: ActorRef = system.actorOf(Props[ActorWithBoundedMailBox], classOf[ActorWithBoundedMailBox].getSimpleName)

    (1 to 100) foreach (actorWithBoundedMailBox ! _)
  }

  class ActorWithBoundedMailBox extends Actor with ActorLogging with RequiresMessageQueue[BoundedMessageQueueSemantics] {

    override def receive: Receive = {

      case any: Any =>
        log.info("接收到指令：{}", any)
        Thread.sleep(500)
    }
  }

}
