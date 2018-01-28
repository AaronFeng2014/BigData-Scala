package com.aaron.scala.implicits

import com.aaron.scala.implicits.ImplicitUtils._

import scala.language.implicitConversions

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-10-09
  */
final class ImplicitConvertSample[Int](a: Int)
{

    def c(): Unit =
    {

    }
}


class A
{

    def a(): Unit =
    {
        println("a")
    }
}


class B
{
    def b(): Unit =
    {
        println("b")
    }
}


class C
{
    def c(): Unit =
    {
        println("c")
    }

}


object ImplicitConvertSample
{


    implicit def convertToC(a: A): C =
    {
        new C
    }


    def main(args: Array[String]): Unit =
    {
        val a = new A

        a.a()

        /**
          * a类中并没有c()方法，因此此处有一个隐式转换忙把啊类型转换成c类型
          * 所以a才可以调用c类中方法
          */
        a.c()

        val b = new B()

        println(b --> a)


    }
}
