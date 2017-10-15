package com.aaron.scala.evidence.typeerasure

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-10-09
  */
object TypeErasureSample
{


    implicit object IntMaker


    implicit object DoubleMaker


    implicit object DoubleMakers


    def typeErasure(param: List[Int])(implicit p: IntMaker.type): Unit =
    {
        println("========Int========")
        param.foreach(println(_))
    }


    def typeErasure(param: List[Double])(implicit p: DoubleMaker.type): Unit =
    {
        println("========Double========")
        param.foreach(println(_))
    }


    def typeErasure(param: List[String])(implicit p: DoubleMakers.type): Unit =
    {
        println("========Double========")
        param.foreach(println(_))
    }


    /**
      * 隐式参数出现的限制：
      * 1.只有最后一个参数列表才允许使用隐式参数
      * 2.隐式参数只能出现在参数列表的最左边
      * 3.如果方法是以 implicit 开始，那么所有的参数都是隐式参数
      *
      * @param param List[Double]
      * @param a     String
      */
    def typeErasure(implicit param: List[Double], a: String): Unit =
    {
        println("========Double========")
        param.foreach(println(_))
    }


    def main(args: Array[String]): Unit =
    {

        typeErasure(List(1, 2, 4, 2, 6))

        typeErasure(List("hello", "world"))

        typeErasure(List(1.toDouble, 2.toDouble, 4.toDouble, 2.toDouble, 6.toDouble))
    }
}
