package com.aaron.scala.spider.douban

import java.util.concurrent.Executors

import com.aaron.scala.spider.HTMLParse
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-07-27
  */
object FetchBooks
{
    //书籍信息爬取的起点
    val beginUrl = "https://book.douban.com/subject/26696099/"

    val executor = Executors.newFixedThreadPool(30)


    def main(args: Array[String]): Unit =
    {
        val start: Long = System.currentTimeMillis()

        println("正在获取数据，请稍后")

        fetchBookInfo(beginUrl)

        //executor.shutdown()

        println("抓取结束，耗时：" + (System.currentTimeMillis() - start) + "毫秒")
    }


    def fetchBookInfo(url: String): Unit =
    {
        executor.execute(new Runnable
        {
            override def run(): Unit =
            {
                try
                {
                    val document: Document = Jsoup.connect(url).get()

                    val element = document.getElementById("wrapper")

                    val parser: HTMLParse = new DouBanBookParse()

                    parser.parse(element)
                }
                catch
                {
                    case e: Throwable => println("抓取失败：" + url + "，错误原因：" + e.getMessage)
                }
            }
        })
    }
}
