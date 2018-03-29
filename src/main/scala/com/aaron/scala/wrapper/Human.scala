package com.aaron.scala.wrapper

import com.aaron.scala.wrapper.Humanish.HumanLiked

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2018-03-12
  */
trait Animal

final case class Cat(name: String) extends Animal

final case class Dog(name: String) extends Animal

object Humanish {

  trait HumanLiked[A] {

    def speak(speaker: A): Unit
  }

  object HumanLiked {

    implicit object DogIsHumanLiked extends HumanLiked[Dog] {

      override def speak(speaker: Dog): Unit = {

        println(s"${speaker.name} is speaking")
      }
    }

  }

}

object TypeClassSample {

  def main(args: Array[String]): Unit = {

    makeHumanLikedSpeak(Dog("dog"))
  }

  def makeHumanLikedSpeak[A](speak: A)(implicit humanLiked: HumanLiked[A]): Unit = {

    humanLiked.speak(speak)
  }
}
