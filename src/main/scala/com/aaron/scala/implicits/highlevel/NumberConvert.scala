package com.aaron.scala.implicits.highlevel

import scala.language.implicitConversions

/**
  * 隐式转换的高级用例
  *
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2018-03-13
  */
trait NumberConvert
{
    val doubleTimes = convert(2)
    val threeTimes = convert(3)
    val fiveTimes = convert(5)
    val tenTimes = convert(10)


    protected def convert(times: Int): Number
}


object Sample
{


    implicit final class IntConvert(origin: Int) extends NumberConvert
    {
        override protected def convert(times: Int): Number =
        {
            origin * times
        }
    }


    implicit final class DoubleConvert(origin: Double) extends NumberConvert
    {
        override protected def convert(times: Int): Number =
        {
            origin * times
        }
    }


    def main(args: Array[String]): Unit =
    {

        println(3 doubleTimes)
        println(3 threeTimes)
        println(3 fiveTimes)
        println(100 tenTimes)
    }
}
