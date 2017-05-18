package com.aarom.scala.companionobject

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-04-16
  */
class Students()
{
    var name: String = ""


    def study(): Unit =
    {
        println("student should study")
    }


    def introduce(): Unit =
    {
        println("my name is " + name + ", and my age is " + Students.age)
    }

}


/**
  * 该对象对类Students的伴生对象，伴生对象的名字必须和类名一致
  * 为什么要使用伴生对象？
  * 使用伴生对象后，获得对象的实例就可以不用使用new关键字
  */
object Students
{

    var name: String = ""
    var age: Int = 0


    def apply(name: String): Students =
    {
        apply(name, 0)
    }


    def apply(name: String, age: Int): Students =
    {
        val student = new Students()

        this.name = name

        this.age = age

        student
    }


    def apply(): Students =
    {
        apply("")
    }


    def unapply(arg: Students): Option[Students] =
    {
        Some(apply())
    }

}
