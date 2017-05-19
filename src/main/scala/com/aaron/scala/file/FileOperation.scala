package com.aaron.scala.file

import scala.io.{BufferedSource, Source}

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-04-04
  */
object FileOperation
{
    val filePath: String = "C:\\Users\\Aaron\\Desktop\\毕业设计-冯海鑫0505-最新.txt"


    def main(args: Array[String]): Unit =
    {
        readFile(filePath)
    }


    def readFile(filePath: String): Unit =
    {
        val bufferedSource: BufferedSource = Source.fromFile(filePath, "UTF-8")

        val iterable: Iterator[String] = bufferedSource.getLines()

        iterable.foreach(println)

    }
}
