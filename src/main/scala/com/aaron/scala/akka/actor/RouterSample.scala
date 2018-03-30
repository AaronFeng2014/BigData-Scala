package com.aaron.scala.akka.actor

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props}
import akka.routing.RoundRobinPool

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2018-03-29
  */
object RouterSample {

  def main(args: Array[String]): Unit = {

    val system = ActorSystem("BlockingActorSample")
    val routerActor = system.actorOf(Props[RouterActor], classOf[RouterActor].getSimpleName)

    (1 to 100) foreach (routerActor ! _)

    println("所有指令发送完成")
  }

  class RouterActor extends Actor with ActorLogging {

    val router: ActorRef = context.actorOf(RoundRobinPool(10).props(Props[WorkActor]), "WorkActorRouter")

    override def receive: Receive = {

      case any: Any => router ! any
    }
  }

  class WorkActor extends Actor with ActorLogging {

    override def receive: Receive = {

      case any: Any =>
        log.info("发送者：{}，接收到指令：{}", sender(), any)
        Thread.sleep(3000)
    }
  }

}
