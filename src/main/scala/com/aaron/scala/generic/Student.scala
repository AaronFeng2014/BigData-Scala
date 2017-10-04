package com.aaron.scala.generic

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-10-04
  */
class Student extends People
{
    override def run(a: Animal) =
    {
        println("student study")
        new Animal
        {
            override def sayName(): Unit =
            {}
        }
    }
}
