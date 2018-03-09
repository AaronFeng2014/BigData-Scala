package com.aaron.scala.web

import akka.http.scaladsl.server.Route
import com.aaron.scala.web.controller.UserController

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2018-03-09
  */
object ApplicationContextRouter extends UserController
{


    val route: Route =

    //这里设置了请求路径的前缀
        pathPrefix("akkatest")
        {

            //多个路由组合起来
            queryAllUsers() ~ queryUserById() ~ addUser()
        }
}
