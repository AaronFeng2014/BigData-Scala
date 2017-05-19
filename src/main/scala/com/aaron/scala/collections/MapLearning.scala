package com.aaron.scala.collections

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-04-05
  */
object MapLearning
{


    def mapIterater(map: Map[String, Int]): Unit =
    {
        map.keys.foreach
        {
            key => showInfo(key, map(key))
        }
    }


    def showInfo(key: String, value: Int): Unit =
    {
        println(key + " is " + value)
    }


}
