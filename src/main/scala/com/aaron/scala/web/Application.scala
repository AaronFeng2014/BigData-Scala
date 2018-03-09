package com.aaron.scala.web

import akka.http.scaladsl.Http

import scala.util.Success

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2018-03-08
  */
object Application extends ActorInit
{


    def main(args: Array[String]): Unit =
    {
        val httpFuture = Http().bindAndHandle(ApplicationContextRouter.route, "", 9999)

        httpFuture.onComplete
        {

            case Success(b) =>
            {
                println("成功%s", b)
            }
        }
    }
}
