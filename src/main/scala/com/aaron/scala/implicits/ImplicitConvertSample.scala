package com.aaron.scala.implicits

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
    def b(): Unit =
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

        a.b()


    }
}
