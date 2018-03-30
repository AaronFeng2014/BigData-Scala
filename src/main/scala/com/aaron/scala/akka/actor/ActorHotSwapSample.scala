package com.aaron.scala.akka.actor

import akka.actor.{Actor, ActorLogging, ActorSystem, DeadLetter, Props}
import akka.event.DeadLetterListener

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2018-03-29
  */
object ActorHotSwapSample {

  def main(args: Array[String]): Unit = {

    val system = ActorSystem("ActorHotSwapSample")

    val deadLetterListener = system.actorOf(Props[DeadLetterListener](), "deadLetterListener")

    /**
      * 注册死信监听器
      * 当接收者actor不存在时和网络故障会监听到
      *
      * 不监听接收者无法处理的消息，即case没有匹配上的情况
      */
    system.eventStream.subscribe(deadLetterListener, classOf[DeadLetter])

    val hotSwapActor = system.actorOf(Props[HotSwapActor](), "hotSwapActor")

    system.stop(hotSwapActor)
    Thread.sleep(2000)

    hotSwapActor ! "open before"

    hotSwapActor ! Open()

    hotSwapActor ! "open"

    hotSwapActor ! Close()

    Thread.sleep(2000)
    println("关闭连接后再次发送请求")
    hotSwapActor ! "closed "
  }

  class HotSwapActor extends Actor with ActorLogging {

    override def receive: Receive = {

      case Open() =>
        context.become(isOpen())
        log.info("连接已经打开了，可以正常处理请求")
    }

    def isOpen(): Receive = {

      case Close() =>
        context.unbecome()
        log.warning("连接已经关闭了，无法再继续处理请求")
      case any: Any => log.info("接收到请求{}", any)
    }

    def isClose(): Receive = {

      case _ => log.error("当前状态是close，不接收任何请求")
    }
  }

  case class Open()

  case class Close()

}
