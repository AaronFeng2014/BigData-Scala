package com.aarom.scala.matchcase

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-04-16
  */
object MatchCase
{

    def matchValue(age: Int): Unit =
    {
        age match
        {
            case 18 => println("input parameter is 18")

            case _ => println("you are not 18")
        }

    }


    /**
      * match匹配Boolean表达式，可以改写成if else条件表达式
      *
      * @param age Int：年龄入参
      */
    def matchBoolean(age: Int): Unit =
    {
        /*age > 18 match
        {
            case true => println("you are adult！")

            case false => println("you are not 18")
        }*/


        age match
        {
            case `age` if age > 18 => println("you are adult！")

            case _ => println("you are not 18")
        }
    }


    def matchType(any: Any): Unit =
    {
        any match
        {
            case _: Int => println("input parameter is Int")

            case _: Long => println("input parameter is Long")

            case _: Double => println("input parameter is Double")

            case _ => println("input parameter is unknown")
        }
    }


    def matchConstructor(any: Any): Unit =
    {
        any match
        {
            //case Students.unapply() => println("input parameter is Int")

            //case Students("fenghaixin") => println("input parameter is Long")

            //case Students("fenghaixin", 24) => println("input parameter is Double")

            case _ => println("input parameter is unknown")
        }
    }
}
