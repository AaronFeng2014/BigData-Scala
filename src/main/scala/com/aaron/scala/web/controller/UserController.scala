package com.aaron.scala.web.controller

import java.util.concurrent.TimeUnit

import akka.actor.ActorSelection
import akka.http.scaladsl.server.Directives
import akka.util.Timeout
import com.aaron.scala.db.getquill.domian.AppUsers
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.serializer.SerializerFeature
import org.slf4j
import org.slf4j.LoggerFactory

import scala.concurrent.duration.FiniteDuration
import scala.util.{Failure, Success}

/**
  * 用户接口
  *
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2018-03-09
  */
trait UserController extends Directives
{
    val usersDao: ActorSelection = system.actorSelection("/user/usersDao")

    implicit val timeout = Timeout(FiniteDuration(60, TimeUnit.SECONDS))

    import akka.pattern.ask
    import com.aaron.scala.web.Application._

    private val LOGGER: slf4j.Logger = LoggerFactory.getLogger(this.getClass)


    def queryAllUsers() =
    {
        (path("users") & get)
        {


            onComplete(usersDao ? "queryAllUsers")
            {
                case Success(result) =>
                {
                    result match
                    {
                        case _ =>
                        {
                            LOGGER.info("controller收到的返回：{}", result)

                            //这里是要返回的内容
                            complete(JSON.toJSONString(result, SerializerFeature.PrettyFormat))
                        }
                    }
                }
            }
        }
    }


    def queryUserById() =
    {

        //路径匹配，可以从路径中拿取指定的参数，该参数必须是long型才会匹配到，否者匹配不到
        path("user" / LongNumber)
        {
            userId =>
            {

                LOGGER.warn("接收到用户id：{}", userId)
                onComplete(usersDao ? userId)
                {

                    case Success(result) =>
                    {

                        LOGGER.warn("查询单个用户结束：{}", result)

                        complete(result.toString)
                    }
                }
            }
        }
    }


    def addUser() =
    {
        //parameter用于映射request中的请求参数
        (path("user") & post & parameter("appUsersId", "accountNumber", "nickName"))
        {

            (appUsersId: String, accountNumber: String, nickName: String) =>
            {
                LOGGER.warn("接收到用户请求参数：appUsersId->{}, accountNumber->{}, nickName->{}", appUsersId, accountNumber, nickName)

                try
                {
                    onComplete(usersDao ? AppUsers(appUsersId, accountNumber, nickName))
                    {
                        case Success(result) =>
                        {

                            complete(result.toString)
                        }

                        case Failure(exception) =>
                        {

                            LOGGER.error("程序异常：{}", exception)
                            complete("程序异常")
                        }
                    }
                }
                catch
                {

                    case e: Exception =>
                    {
                        LOGGER.error("程序异常：{}", e)
                        complete("程序异常")
                    }
                }


            }
        }

    }
}
