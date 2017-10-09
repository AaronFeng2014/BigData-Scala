package com.aaron.scala.evidence

import com.aaron.scala.implicits.api.People

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-10-09
  */
class ImplicitEvidenceSample extends Fly[ImplicitEvidenceSample]
{

    /**
      * 隐式证据，语法如下：
      *
      * implicit evidence: <:<[ImplicitEvidenceSample, api.People])
      *
      * 意思是：ImplicitEvidenceSample必须是一个People类型
      *
      * 隐式证据存在的意义：仅仅是为了约束参数，使参数是指定的类型
      *
      * @param evidence
      *
      * @return
      */
    override def fly(implicit evidence: <:<[ImplicitEvidenceSample, ImplicitEvidenceSample]) =
    {
        val map: Map[String, ImplicitEvidenceSample] = Map("str" -> new ImplicitEvidenceSample)

        map
    }
}


object ImplicitEvidenceSample
{

    def main(args: Array[String]): Unit =
    {
        val sample = new ImplicitEvidenceSample

        println(sample.fly.get("str").get)
    }
}


