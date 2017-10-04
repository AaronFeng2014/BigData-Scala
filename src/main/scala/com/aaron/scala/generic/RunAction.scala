package com.aaron.scala.generic

/**
  * 泛型使用的相关练习
  *
  * 这里定义了参数是逆变类型，返回值是协变类型
  *
  * 1.[+T]表示只可以使用T本身以及其子类，即逆变
  *
  * 2.[-T]表示只可以使用T本身以及其父类，即协变
  *
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-04-05
  */
trait RunAction[-A, +B]
{
    def run(a: A): B
}
