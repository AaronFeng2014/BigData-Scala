package com.aarom.scala.generic

/**
  * 泛型使用的相关练习
  *
  * 1.[+T]表示只可以使用T本身以及其子类
  *
  * 2.[-T]表示只可以使用T本身以及其父类
  *
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-04-05
  */
trait RunAction[+Animal]
{
    def run()
}
