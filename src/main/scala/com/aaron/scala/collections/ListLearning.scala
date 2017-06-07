package com.aaron.scala.collections

import scala.io.Source

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


    def findMaxInList(list: List[Int]): Int =
    {
        list.reduce(Math.max)
    }


    def ttt =
    {
        val textPah = "spark-warehouse/data01.txt"


        var index = 17690
        val sql: StringBuffer = new StringBuffer()
        Source.fromFile(textPah).getLines().foreach(code =>
        {
            val array: Array[String] = code.split(",")
            index += 1
            sql.append("INSERT INTO HTL_DELIVERY.T_MERCHANT_DISTRIBUTOR (ID, MERCHANTCODE, DISTRIBUTORCODE, DISTRIBUTORNAME, CHANNELCODE, SHOPNAME, CREATETIME) VALUES (" + index + ", '" + array(0) + "', '" + array(1) + "', 'TTM', 'ttm', '" + array(2) + "', sysdate);\n")
        })

        println(sql.toString)
    }


    def main(args: Array[String]): Unit =
    {
        ttt
    }
}
