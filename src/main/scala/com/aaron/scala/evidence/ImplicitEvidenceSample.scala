package com.aaron.scala.evidence

import com.aaron.scala.implicits.api

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
      * @param evidence
      *
      * @return
      */
    override def fly(implicit evidence: <:<[ImplicitEvidenceSample, api.People]) =
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

        sample.fly
    }
}


