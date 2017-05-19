package com.aaron.scala.partialfun

/**
  * 偏函数详解
  *
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-04-16
  */
object PartialFunLearning
{

    /**
      * PartialFunction中的最后一个参数表示返回值的类型，其他的表示方法参数类型
      */
    val intToString = new PartialFunction[Int, String]
    {
        val numbers: Array[Int] = Array(1, 2, 3, 4, 5)


        override def isDefinedAt(x: Int): Boolean =
        {
            x > numbers(0) && x < numbers(4)
        }


        override def apply(v1: Int): String = numbers(v1 - 1).toString
    }

    val intToString_2 = new PartialFunction[Int, String]
    {
        val numbers: Array[Int] = Array(7, 8, 9, 10, 11)


        override def isDefinedAt(x: Int): Boolean =
        {
            x > numbers(0) && x < numbers(4)
        }


        override def apply(v1: Int): String = numbers(v1 - 7).toString
    }

    val str = intToString orElse intToString_2


    def main(args: Array[String]): Unit =
    {
        println(str.isDefinedAt(40))
    }
}
