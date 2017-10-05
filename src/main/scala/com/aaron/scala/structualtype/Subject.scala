package com.aaron.scala.structualtype

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-10-05
  */
trait Subject
{

    type STATE

    type Observer =
        {def update(state: Any): Unit}

    private var observers: List[Observer] = Nil

    def addObserver(observer: Observer): Unit =
    {
        observers ::= observer
    }

    //支持运行时反射查询方法，需引入下面包，开启功能
    import scala.language.reflectiveCalls

    def notifyObserver(state: STATE): Unit =
    {
        observers.foreach(_.update(state))
    }
}
