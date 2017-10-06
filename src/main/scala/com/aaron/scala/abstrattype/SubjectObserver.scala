package com.aaron.scala.abstrattype

import com.aaron.scala.abstrattype.PlaySubjectObserver.{PlayObserver, PlaySubject}
import com.aaron.scala.generic.Student

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-10-05
  */
abstract class SubjectObserver
{
    //抽象类型
    type S <: Subject
    type O <: Observer


    trait Subject
    {
        //自类型标记，无该申明，31行编译不通过
        self: S =>

        var observers: List[O] = List()

        def addObserver(observer: O): Unit =
        {
            observers = observers.+:(observer)
        }

        def notifyObserver(): Unit =
        {
            observers.foreach(_.update(self))
        }
    }


    trait Observer
    {
        def update(s: S): Unit
    }


}


object PlaySubjectObserver extends SubjectObserver
{
    //抽象类型
    type S = PlaySubject
    type O = PlayObserver


    class PlaySubject extends Subject
    {

        //存在类型
        val s: List[_] forSome
        {type T} = List()

        var name = "feg"


        def test(): Unit =
        {
            notifyObserver()
        }
    }


    class PlayObserver extends Observer
    {
        override def update(s: S): Unit =
        {
            println(s.getClass)
            println("=========")
        }
    }


}


object AbstractType
{
    def main(args: Array[String]): Unit =
    {
        val playSubject: PlaySubject = new PlaySubject
        playSubject.addObserver(new PlayObserver)

        playSubject.notifyObserver()

        ppp(t)
    }


    def ppp(callBack: () => Unit): Unit =
    {
        println("before callBack")
        callBack
        println("after callBack")
    }


    def t(): Unit =
    {
        println("test")
    }
}

