package com.aaron.scala.collections

import org.scalatest.{BeforeAndAfterEach, FunSuite}

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-04-05
  */
class ListLearningTest extends FunSuite with BeforeAndAfterEach
{

    val list: List[Int] = List(3, 8, 2, 9, 12, 32)


    override def beforeEach()
    {

    }


    override def afterEach()
    {

    }


    test("testListIterater")
    {
        ListLearning.listIterater(list)
    }

    test("testListIterater_2")
    {
        ListLearning.listIterater_2(list)
    }

    test("addItemToList")
    {
        val result: List[Int] = ListLearning.addItemToList(-927, list)

        println(result)

        println(result.take(2))

        println(result.drop(2))

        println(result.product)
    }



    test("flatMap")
    {
        val list1: List[Int] = List(3,5,9,12,20)
        val list2: List[Int] = List(4,7,2)
        val list3: List[Int] = List(3,5,1)
        
        val list:List[List[Int]] = List(list1,list2,list3)

        println(ListLearning.flatMap(list))

    }

}
