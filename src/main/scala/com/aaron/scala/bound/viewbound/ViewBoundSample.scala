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


    def viewBound[A <% Apple](t: A): Int =
    {
        println(t.getClass)

        t.showColor()

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

