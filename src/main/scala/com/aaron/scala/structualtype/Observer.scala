package com.aaron.scala.structualtype

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-10-05
  */
object Observer
{

    def update(stat: Any): Unit =
    {
        println(stat)
    }


    val subject = new Subject()
    {
        //指定抽象类型的实际类型
        override type STATE = String


        def structualType(a: STATE): Unit =
        {
            addObserver(Observer)
            notifyObserver(a)
        }
    }


    def main(args: Array[String]): Unit =
    {
        subject.structualType("fsdf")
    }
}



