package com.aaron.scala.collections

import org.scalatest.FunSuite

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-04-05
  */
class MapLearningTest
        extends FunSuite
{
    val map: Map[String, Int] = Map("fenghaixin" -> 23, "ranran" -> 20)

    test("testMapIterater")
    {
        MapLearning.mapIterater(map)
    }

}
