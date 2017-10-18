package com.aaron.scala.implicits

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-10-15
  */
object ImplicitUtils
{


    implicit final class Convert[A](var value: A)
    {
        /**
          * 自定义的隐式转换函数，功能主要是把任意的两个类型的数据转化成二元组，map结构的 key -> value就是利用的该特性做的
          *
          * @param that
          * @tparam B
          *
          * @return
          */
        def -->[B](that: B): (A, B) =
        {
            (value, that)
        }
    }


}
