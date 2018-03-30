package com.aaron.scala.akka.actor.listener

import akka.actor.{Actor, ActorLogging, DeadLetter}

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2018-03-29
  */
class DeadLetterListener extends Actor with ActorLogging {

  override def receive: Receive = {

    case deadLetter: DeadLetter => {
      log.warning("死信通道接收到消息\n发送者：{}\n接收者：{}\n消息内容：{}", deadLetter.sender, deadLetter.sender, deadLetter.message)
    }
  }
}
