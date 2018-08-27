package com.aaron.scala.actor

import akka.actor.{Actor, ActorSystem, Props}

import scala.concurrent.{ExecutionContextExecutor, Future, Promise}

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2018-03-24
  */
class ActorSample extends Actor {
  override def receive = {

    case (_: Any) => println("any")
  }
}

object ActorSample {
  println(ActorSample.getClass.getSimpleName)
  val systemActor = ActorSystem("ActorSample")

  implicit val dispatcher: ExecutionContextExecutor = systemActor.dispatcher
  val ss = systemActor.actorOf(Props[ActorSample], "ActorSample")

  ss ! ""

  val future: Future[Int] = Future {

    2 / 0
    3 + 5
  }.recover {
      case e: Throwable =>
        println(e.getStackTrace)
        2 / 0
        -1

    }
    .recoverWith {
      case e: Throwable =>
        println(e.getStackTrace)
        Future(-3)
    }

  val promise: Promise[String] = Promise[String]()

  def main(args: Array[String]): Unit = {
    future.onComplete { re => println(re.get)
    }

    val promiseFuture = promise.future

    promiseFuture.onComplete { rr => println("promise  --->   " + rr)
    }
    Thread.sleep(2000)
    promise.success("5453")

  }
}
