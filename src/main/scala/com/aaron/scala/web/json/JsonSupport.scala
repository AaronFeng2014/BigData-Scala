package com.aaron.scala.web.json

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.aaron.scala.db.getquill.domian.AppUsers
import spray.json.DefaultJsonProtocol

/**
  * 支持对象转换json格式
  *
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2018-03-12
  */
trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol
{
    //定义隐式转换对象
    implicit val jsonFormat = jsonFormat3(AppUsers)
}