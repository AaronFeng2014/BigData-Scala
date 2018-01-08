package com.aaron.scala.bound.viewbound

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-10-06
  */
class ViewBoundSample[A]
{

    def count[A <% Apple](t: A)(implicit view: A => Apple): Int =
    {
        println(t.getClass)

        view.apply(t).showColor()

        3
    }


    /**
      * 试图边界
      * 申明方式：A <% Apple
      * 含义：在作用域内，必须存在一个隐式转换，把A转换成Apple
      *
      * @param t
      * @tparam A
      *
      * @return
      */
    def viewBound[A <% Apple](t: A): Int =
    {
        println(t.getClass)

        t.showColor()

        t.showName()

        3
    }
}


object ViewBoundSample
{
    def main(args: Array[String]): Unit =
    {
        val viewBoundSample: ViewBoundSample[GreenApple] = new ViewBoundSample[GreenApple]


        implicit def convert(a: Fruit): Apple = new Apple


        //println(viewBoundSample.count(new Fruit))

        val newViewBoundSample: ViewBoundSample[GreenApple] = new ViewBoundSample[GreenApple]
        println(newViewBoundSample.viewBound(new Fruit))
    }
}

