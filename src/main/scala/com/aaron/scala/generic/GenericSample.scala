package com.aaron.scala.generic

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-10-04
  */
object GenericSample
{

    def main(args: Array[String]): Unit =
    {

        generic(new Dog, new Student)

        test(new RunAction[Cat, Animal]
        {
            override def run(a: Cat) =
            {
                new Cat
            }
        })
    }


    var generic: (Dog, Student) => Animal = (animal: Animal, student: Student) =>
    {
        animal.sayName()

        student.run(animal)
        animal
    }


    def test[Animal <: AnyRef](people: RunAction[Cat, Animal]): Unit =
    {
        people.run(new Cat)
    }
}
