package com.aaron.scala.evidence

import com.aaron.scala.implicits.api.People

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-10-09
  */
trait Fly[+A]
{
    def fly(implicit evidence: <:<[A, People]): Map[String, A]
}
