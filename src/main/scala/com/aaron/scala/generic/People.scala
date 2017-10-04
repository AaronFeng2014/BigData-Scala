package com.aaron.scala.generic

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-04-05
  */
class People extends RunAction[Animal, Animal]
{
    override def run(a: Animal): Animal =
    {

        new Cat
    }
}
