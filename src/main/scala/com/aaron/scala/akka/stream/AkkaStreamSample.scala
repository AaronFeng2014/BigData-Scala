package com.aaron.scala.akka.stream

import java.nio.file.Paths

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{FileIO, Flow, Keep, Source}
import akka.util.ByteString
import akka.{Done, NotUsed}
import org.apache.commons.lang3.StringUtils

import scala.concurrent.{ExecutionContextExecutor, Future}

/**
  * akka stream相关的概念
  * 1. Source
  * 2. Flow
  * 3. Sink
  *
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2018-03-30
  */
object AkkaStreamSample {

  implicit val system: ActorSystem = ActorSystem("AkkaStreamSample")

  //官方文档说明：该参数是让Stream运行的参数，我们无需关心
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  implicit val dispatcher: ExecutionContextExecutor = system.dispatcher


  def main(args: Array[String]): Unit = {

    sourceInAction().onComplete(_ => system.terminate())
  }


  def sourceInAction(): Future[Done] = {

    /**
      * 定义一个Source，该对象有2个类型参数
      * 第一个类型参数表示Source容器中存放数据的类型
      *
      * 该定义只是对一个容器的描述，是可以重复使用的
      */
    val source: Source[Int, NotUsed] = Source(1 to 100)

    val f = source.scan(10)((total, next) => total + next)

    f.runWith(fileSink("test.txt"))

    /**
      * 带有run开始的方法才是对上面描述的执行过程
      */
    f.runForeach(println)
  }


  def fileSink[T](fileName: String) = {

    require(!StringUtils.isEmpty(fileName),"文件不可为空")
    /**
      * Sink
      * 该概念表示
      */
    Flow[T].map(str => ByteString(s"By Flow: $str\n")).toMat(FileIO.toPath(Paths.get(fileName)))(Keep.right)
  }
}
