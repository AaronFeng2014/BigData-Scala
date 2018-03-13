package com.aaron.scala.web.controller

import java.util.concurrent.TimeUnit
import javax.ws.rs.Path

import akka.actor.ActorSelection
import akka.http.scaladsl.server.Directives
import akka.util.Timeout
import com.aaron.scala.db.getquill.domian.AppUsers
import com.aaron.scala.web.Application._
import com.aaron.scala.web.json.JsonSupport
import io.swagger.annotations._
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
@Api("用户接口")
@Path("/")
trait UserController extends Directives with JsonSupport
{
    val usersDao: ActorSelection = system.actorSelection("/user/usersDao")

    implicit val timeout = Timeout(FiniteDuration(60, TimeUnit.SECONDS))

    import akka.pattern.ask

    private val LOGGER: slf4j.Logger = LoggerFactory.getLogger(this.getClass)


    @ApiOperation(value = "查询所有的用户", protocols = "http", httpMethod = "GET", produces = "application/json")
    @Path("/users")
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
                        case list: List[AppUsers] =>
                        {
                            LOGGER.info("controller收到的返回：{}", list)

                            //这里是要返回的内容
                            complete(list)
                        }
                    }
                }
            }
        }
    }

    @ApiOperation(value = "根据用户id查询用户", protocols = "http", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams({
        Array(new ApiImplicitParam(name = "userId", value = "用户id", required = true, paramType = "path", dataType = "long"))
    })
    @ApiResponses(Array(new ApiResponse(code = 200, message = "操作处理成功"), new ApiResponse(code = 400, message = "操作处理失败")))
    @Path("/user/{userId}")
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

                        result match
                        {

                            case user: AppUsers =>
                            {
                                LOGGER.warn("查询单个用户结束：{}", user)


                                complete(user)
                            }
                        }
                    }
                }
            }
        }
    }


    @ApiOperation(value = "新增用户信息到数据库", produces = "text/plain", httpMethod = "POST", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams(
        Array(new ApiImplicitParam(name = "appUsersId", value = "用户id", required = true, paramType = "form", dataType = "string"),
            new ApiImplicitParam(name = "accountNumber", value = "用户账号", required = true, paramType = "form", dataType = "string"),
            new ApiImplicitParam(name = "nickName", value = "用户昵称", required = true, paramType = "form", dataType = "string")))
    @Path("/user/")
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
