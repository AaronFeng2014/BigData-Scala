package com.aaron.scala.implicits

import com.aaron.scala.implicits.api.{LazyPeople, Monkey, People}

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-04-16
  */
object ImplicitConvertUtils
{

    implicit def monkeyToPeople(monkey: Monkey): People =
    {
        new LazyPeople
    }


    def main(args: Array[String]): Unit =
    {
        val monkey : Monkey = new Monkey

        monkey.run()
    }
}
