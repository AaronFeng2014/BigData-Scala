package com.aaron.scala.implicits.api

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-04-16
  */
class LazyPeople extends People
{
    override def run(): Unit =
    {
        println("lazy people run slowly")
    }

}
