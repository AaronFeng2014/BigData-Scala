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
      * 该部分应用函数处理1-5这几个数字
      *
      * PartialFunction中的最后一个参数表示返回值的类型，其他的表示方法参数类型
      *
      * PartialFunction部分应用函数的参数都是逆变的，返回值是协变的
      *
      * 什么是逆变：如果A extends B，那么集合List[A]和List[B]满足List[B]是List[A]的子类，则叫逆变
      * 什么是协变：如果A extends B，那么集合List[A]和List[B]满足List[A]是List[B]的子类，则叫协变
      *
      * 为什么参数是逆变的，而返回值是协变的，个人理解
      *
      * 返回值协变，如果函数可以接收的返回值是A类型，那么他一定可以处理A的子类，因为父类中有的，子类一定共有
      * 参数逆变，
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

    /**
      * 该部分应用函数处理7-11这几个数字
      */
    val intToString_2 = new PartialFunction[Int, String]
    {
        val numbers: Array[Int] = Array(7, 8, 9, 10, 11)


        override def isDefinedAt(x: Int): Boolean =
        {
            x > numbers(0) && x < numbers(4)
        }


        override def apply(v1: Int): String = numbers(v1 - 7).toString
    }

    /**
      * 两个或多个部分应用函数组合使用，能够处理更多的数据
      */
    val str: PartialFunction[Int, String] = intToString orElse intToString_2


    def main(args: Array[String]): Unit =
    {
        println(str.apply(10))
        println(str.apply(3))
    }
}
