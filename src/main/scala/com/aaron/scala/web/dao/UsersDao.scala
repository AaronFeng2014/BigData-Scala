package com.aaron.scala.web.dao

import akka.actor.Actor
import com.aaron.scala.db.getquill.domian.AppUsers
import org.slf4j
import org.slf4j.LoggerFactory

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2018-03-08
  */
class UsersDao extends Actor
{

    private val LOGGER: slf4j.Logger = LoggerFactory.getLogger(this.getClass)

    import com.aaron.scala.db.getquill.querytemplate.UsersTemplate._
    import ctx._


    def deleteUserById(userId: String) =
    {

        val deleteUser = quote
        {

            users.filter(user => user.appUsersId == lift(userId)).delete
        }

        val result = ctx.run(deleteUser)

        LOGGER.info("删除用户{}完成，删除结果：{}", userId, result)
    }


    override def receive: Receive =
    {

        case "queryAllUsers" =>
        {
            val allUsers: List[AppUsers] = queryUsers()

            LOGGER.info("开始查询全部用户，查询结果：{}", allUsers)

            sender() ! allUsers
        }

        case user: AppUsers =>
        {
            addUser(user)

            sender() ! "用户新增完成"
        }
        case userId: Long =>
        {
            val user: AppUsers = queryUserById(userId).get

            LOGGER.info("开始查询单个用户，查询结果：{}", user)

            sender() ! user
        }
    }


    def queryUsers(): List[AppUsers] =
    {
        val query = quote
        {

            users
        }

        ctx.run(query)
    }


    def queryUserById(userId: Long) =
    {

        val query = quote
        {
            //lift的作用是绑定运行时的值
            users.filter(_.appUsersId == lift(userId.toString))
        }

        Option(ctx.run(query).head)
    }


    def addUser(user: AppUsers) =
    {
        val insertUser = quote
        {

            users.insert(lift(user))
        }

        val result = ctx.run(insertUser)

        LOGGER.info("成功插入{}个用户", result)
    }
}
