package com.aaron.scala.bound.contextbound

import java.util.Comparator

/**
  * 上线文边界,参见 http://hongjiang.info/scala-type-system-context-bounds/ 说明
  *
  * 需要一个隐式对象，来完成某种转化
  *
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-10-06
  */
class DataWrapper(var data: Int)


class ContextBoundSample[T]
{

    /**
      * T: Comparator 上下文边界的申明方式
      *
      * 表示存在一个Compare[T]类型的隐式值
      *
      * @param t1 T
      * @param t2 T
      * @tparam T 上下文边界参数
      *
      * @return
      */
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

        val max = contextBoundSample.max(new DataWrapper(734), new DataWrapper(45))

        println(max.data)
    }
}


