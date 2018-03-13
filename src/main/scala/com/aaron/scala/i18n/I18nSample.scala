package com.aaron.scala.i18n

import java.util.{Locale, ResourceBundle}

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2018-03-09
  */
object I18nSample
{
    def main(args: Array[String]): Unit =
    {

        val resourceBundle = ResourceBundle.getBundle("message", Locale.CHINESE)


        println(resourceBundle.getString("hello"))
    }
}