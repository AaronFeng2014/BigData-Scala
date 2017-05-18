package com.aarom.scala.methodParameter

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-04-05
  */
object MethodParameterLearning
{

    /**
      *
      * @param callback (String,String) => Unit：方法参数，以两个String作为参数，无返回类型
      * @param total    Int：表示要调用第一个方法参数的次数
      */
    def showName(callback: (String, String) => Unit, total: Int): Unit =
    {

        for (a <- 1 to total)
        {
            println("This is the " + a + " times")
            callback("fenghaixin" + a, "aaron" + a)
        }
    }


    def tellName(name: String, nickName: String): Unit =
    {
        println("name=" + name)

        println("nickName=" + nickName)
    }


    /**
      * 按名称调用
      *
      * @param now
      */
    def printTime(now: => Long): Unit =
    {
        println("now :" + now)
    }


    def openResource[ResourceManager <:
    {def close() : Unit}](resourceManager: => ResourceManager): Unit =
    {
        resourceManager.close()
    }


    def main(args: Array[String]): Unit =
    {
        // printTime(System.currentTimeMillis())

        //openResource(resourceManager)

        //val a = s(4)
        //println(s(9, 8))
        //println(sum2(a => a * a)(1, 4))
        println(f(2, 3l))
    }


    var f: (Int, Long) => String = new Function2[Int, Long, String]
    {
        override def apply(v1: Int, v2: Long): String = "gfvc666666666"
    }

    val ssss = (a: Int) => a * a


    /**
      * 函数的返回值是另一个函数
      *
      * @param a
      *
      * @return
      */
    def sum(a: Double): (Double, Int) => Double = (b: Double, c: Int) => a * b + c


    val s = (a: Int, b: Int) => a + b


    def sum2(f: Int => Int): (Int, Int) => Int =
    {
        def sumF(a: Int, b: Int): Int =
            if (a > b) 0
            else f(a) + sumF(a + 1, b)


        sumF
    }


    def sum3(callBack: (Int) => Int): (Int, Int) => Int =
    {
        (a: Int, b: Int) =>
        {
            if (a > b)
            {
                0
            }
            else
            {
                callBack(a)
            }
        }

    }
}
