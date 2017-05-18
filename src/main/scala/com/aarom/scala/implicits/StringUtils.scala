package com.aarom.scala.implicits

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-04-16
  */
object StringUtils
{

    implicit val a : String = "test"

    def append(implicit appendString: String): String =
    {
        "appendString" + appendString
    }


    def main(args: Array[String]): Unit =
    {
        val name :String = "feng haixin "

        println(append)
    }
}
