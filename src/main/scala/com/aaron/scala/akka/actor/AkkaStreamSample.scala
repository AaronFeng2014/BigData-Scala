package com.aaron.scala.akka.actor

import java.nio.file.Paths

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.scaladsl.{Broadcast, FileIO, Flow, GraphDSL, RunnableGraph, Sink, Source}
import akka.stream.{ActorMaterializer, ClosedShape, ThrottleMode}
import akka.util.ByteString

import scala.concurrent.Future

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2018-03-31
  */
object AkkaStreamSample {

  implicit val system = ActorSystem()

  implicit val s = ActorMaterializer()

  def main(args: Array[String]): Unit = {
    normalStream
    timeBased()
  }

  //仅仅是保存文件的一个描述信息
  val fileSink = (fileName: String) => Flow[String].map(content => ByteString(content + "\n")).to(FileIO.toPath(Paths.get(fileName)))

  val sink: Sink[Int, Future[Int]] = Sink.fold(0)((total, next) => total + next)

  def normalStream {
    val source1: Source[Int, NotUsed] = Source(1 to 20)

    source1.scan(0)((sum, next) => sum + next).map(_.toString).runWith(fileSink("normalStream.txt"))
  }

  def timeBased() = {
    val source1: Source[Int, NotUsed] = Source(1 to 20)
    val source2: Source[Int, NotUsed] = Source(1 to 20)

    import scala.concurrent.duration._
    //基于时间的流，可以控制速度
    source1
      .zipWith(source2)((a, b) => a + b)
      .throttle(1, 1.seconds, 1, ThrottleMode.shaping)
      .map(_.toString)
      .runWith(fileSink("timeBased.txt"))
    //source1.scan(0)((sum, next) => sum + next).map(_.toString).runWith(fileSink("timeBased.txt"))
  }

  val source: Source[Int, NotUsed] = Source(1 to 20)

  /**
    * Graph 貌似是对流进行分流作用的，例如一个输入流进过Graph处理后变成2个或者更多的流
    *
    */
  val ss = RunnableGraph.fromGraph(GraphDSL.create() { implicit c =>
    {
      import GraphDSL.Implicits._
      val broadcast = c.add(Broadcast[Int](2))
      source ~> broadcast.in

      broadcast.out(0) ~> Flow[Int].map(str => str + "\n") ~> fileSink("graph0.txt")
      broadcast.out(1) ~> Flow[Int].map(str => str + "\n") ~> fileSink("graph1.txt")

      ClosedShape
    }

  })

  ss.run()

}
