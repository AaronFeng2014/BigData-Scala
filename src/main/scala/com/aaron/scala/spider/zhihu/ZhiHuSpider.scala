package com.aaron.scala.spider.zhihu

import java.util

import com.aaron.scala.spider.zhihu.entity.Person
import org.jsoup.Jsoup
import org.jsoup.nodes.{Document, Element}
import org.jsoup.select.Elements

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2016-12-23
  */
object ZhiHuSpider
{
    /**
      * 爬虫起点
      */
    var beginUrl: String = "https://www.zhihu.com/people/sun-jing-7-14/followers"

    val userAgent: String = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36"

    val person: Person = new Person()


    def main(args: Array[String]): Unit =
    {
        getZhiHuData()
    }


    def getZhiHuData(): Unit =
    {
        val headers: util.Map[String, String] = new util.HashMap[String, String]()
        headers.put("Accept-Language", "zh-CN,zh;q=0.8")
        headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
        headers.put("Cookie", "d_c0=\"AJBCs8jk9QqPThnB2tiQdk9qi8gsLlrUD7s=|1481080337\"; q_c1=a2d4484c7c9b4f338946be86b865f83d|1481080337000|1481080337000; l_cap_id=\"ZmUzZGQ1NDg1MzZkNGM0Njk2NzI3MjU4YjhiZGRlNjk=|1482456498|0c6efd3342589ab73282411082bfa5076a9aaa20\"; cap_id=\"ODVmYTkxYjE3ZjNhNDYyZDgxMGNlYTE1ZTZiNjE1Yjk=|1482456498|d3b8daf6dfaa4a9f9911f400447d9423d4d41bbb\"; r_cap_id=\"NGNjZjQ3M2UxNDZjNDBlYmJiNmEzMGUxYTc5MDk4MGM=|1482456499|9e4adb4e6b7008040c848fd3f8eff1173e9a23a4\"; _zap=2f77d22b-62ec-4073-92ec-19589243fa06; __utma=51854390.1832026682.1481080339.1482197209.1482456496.9; __utmz=51854390.1482456496.9.9.utmcsr=baidu|utmccn=(organic)|utmcmd=organic; __utmv=51854390.100--|2=registration_date=20161223=1^3=entry_date=20161207=1; z_c0=Mi4wQUFCQ01LQm1DZ3NBa0VLenlPVDFDaGNBQUFCaEFsVk42QXFFV0FCZ1hhak5PYVdBa2ZHeC1rejNTaHFyN0RfOU1R|1482456674|40bad6bec9b8f6a972575bb6475c07992e3d2bca")
        val document: Document = Jsoup.connect(beginUrl).headers(headers).userAgent(userAgent).execute().parse()
        val body: Element = document.body()

        //getUserName(body)

        getFollowing(body)
    }


    def getUserName(body: Element): Unit =
    {
        val profile: Element = body.getElementById("ProfileHeader")

        val nameElements: Elements = profile.getElementsByClass("ProfileHeader-name")

        val nameIterator: util.Iterator[Element] = nameElements.iterator()


        while (nameIterator.hasNext)
        {
            val ele: Element = nameIterator.next()
            val name = ele.text()

            println(name)
        }
    }


    def getFollowing(body: Element): Unit =
    {
        //val followingElements: Elements = body.getElementById("Profile-following").getElementsByClass("DataList-pager").first().getElementsByClass("List-item")


        val name: Elements = body.getElementsByClass("UserLink-link")

        val followingIterator: util.Iterator[Element] = name.iterator()

        while (followingIterator.hasNext)
        {


            val ele: Element = followingIterator.next()

            val followingName: String = ele.text()

            println(followingName)

        }
    }
}
