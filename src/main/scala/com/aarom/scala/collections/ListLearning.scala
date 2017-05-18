package com.aarom.scala.collections

/**
  * scala集合之list练习
  *
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-04-04
  */
object ListLearning
{

    /**
      * list遍历方式之一：foreach遍历
      *
      * @param list List[Int]：参数
      */
    def listIterater(list: List[Int]): Unit =
    {
        list.foreach(showNumber)
    }


    /**
      * list遍历方式之一：for循环
      *
      * @param list List[Int]：参数
      */
    def listIterater_2(list: List[Int]): Unit =
    {
        //for循环里面可以直接加条件
        for (num: Int <- list if num % 2 == 0)
        {
            showNumber(num)
        }
    }


    def flatMap(list: List[List[Int]]): List[Int] =
    {
        list.flatMap(_.map(_ * 2)).map(_ * 2)
    }


    /**
      *
      * @param any  Any：待添加到list中的元素
      * @param list List：list容器
      * @tparam Any
      *
      * @return
      */
    def addItemToList[Any](any: Any, list: List[Any]): List[Any] =
    {
        any :: list
    }


    private def showNumber(num: Int): Unit =
    {
        println(num)
    }
}
