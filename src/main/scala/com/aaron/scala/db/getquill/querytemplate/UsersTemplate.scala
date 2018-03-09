package com.aaron.scala.db.getquill.querytemplate

import com.aaron.scala.db.getquill.DataSourceContext
import com.aaron.scala.db.getquill.domian.AppUsers

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2018-03-07
  */
object UsersTemplate extends DataSourceContext
{

    import ctx._

    //val mapping: AppUsers => (Any, String) = (user: AppUsers) => (user.name, "nickName")

    val users = quote
    {
        querySchema[AppUsers]("appUsers")
    }
}
