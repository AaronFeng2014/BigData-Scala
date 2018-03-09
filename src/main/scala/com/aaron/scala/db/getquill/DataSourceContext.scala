package com.aaron.scala.db.getquill

import com.typesafe.config.{Config, ConfigFactory}
import io.getquill.{Literal, MysqlJdbcContext}

/**
  * 数据源
  *
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2018-03-07
  */
trait DataSourceContext
{
    private val config: Config = ConfigFactory.load().getConfig("mysql2")


    lazy val ctx = new MysqlJdbcContext(Literal, config)
}