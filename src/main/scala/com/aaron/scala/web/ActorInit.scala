package com.aaron.scala.web

import akka.actor.{ActorSystem, Props}
import akka.stream.ActorMaterializer
import com.aaron.scala.web.dao.UsersDao

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2018-03-09
  */
class ActorInit
{


    implicit val system = ActorSystem("my-system")

    // needed for the future flatMap/onComplete in the end
    implicit val executionContext = system.dispatcher

    implicit val materializer = ActorMaterializer()


    system.actorOf(Props[UsersDao], "usersDao")

}
