package com.aaron.scala.db.getquill

import org.slf4j
import org.slf4j.LoggerFactory


/**
  *
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2018-03-06
  */
object GetQuillSample
{

    private val LOGGER: slf4j.Logger = LoggerFactory.getLogger(this.getClass)

    import com.aaron.scala.db.getquill.querytemplate.UsersTemplate._

    /**
      * 尤其注意：
      * 以下一句import是引入上一句import中的ctx对象，这里不能单独再次引入该对象，否则编译报错
      * 也不能混入带有ctx对象的特质，容易引起歧义导致编译失败
      */

    import ctx._

    def main(args: Array[String]): Unit =
    {
        val q = quote
        {
            users
        }

        val result = ctx.run(q)

        result.foreach(LOGGER.info("数据库查询结果：{}", _))
    }
}
