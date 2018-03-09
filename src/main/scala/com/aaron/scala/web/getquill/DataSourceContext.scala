package com.aaron.scala.web.getquill

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
    lazy val ctx = new MysqlJdbcContext(Literal, config)
    private val config: Config = ConfigFactory.load().getConfig("mysql2")
}