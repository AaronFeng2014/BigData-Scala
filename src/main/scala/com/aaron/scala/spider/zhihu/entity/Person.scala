package com.aaron.scala.spider.zhihu.entity

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2016-12-23
  */
class Person
{
    /**
      * 知乎id
      */
    var name: String = ""

    /**
      * 职业
      */
    var job: String = ""

    /**
      * 关注人员列表
      */
    var followingList: List[Person] = List()

    /**
      * 关注人员列表
      */
    var followerList: List[Person] = List()
}
