package com.aaron.scala.bound.contextbound

import java.util.Comparator

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-10-06
  */
class DataWrapper(var data: Int)


class ContextBoundSample[T]
{

    def max[T: Comparator](t1: T, t2: T): T =
    {
        val comparator = implicitly[Comparator[T]]

        if (comparator.compare(t1, t2) > 0) t1
        else t2
    }
}


object ContextBoundSample
{
    implicit val comparator = new Comparator[DataWrapper]()
    {
        override def compare(o1: DataWrapper, o2: DataWrapper): Int = o1.data - o2.data
    }


    def main(args: Array[String]): Unit =
    {
        val contextBoundSample: ContextBoundSample[DataWrapper] = new ContextBoundSample[DataWrapper]

        val max = contextBoundSample.max(new DataWrapper(34), new DataWrapper(45))

        println(max)
    }
}


