package com.aaron.scala.spider.douban

import com.aaron.scala.spider.HTMLParse
import org.jsoup.nodes.Element

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-07-28
  */
class DouBanBookParse extends HTMLParse
{
    var hasFetchedUrlList: List[String] = List(FetchBooks.beginUrl)

    var repeatUtlList: List[String] = List(FetchBooks.beginUrl)


    override def parse(element: Element): Unit =
    {
        val content = element.getElementById("content")
        val info = content.getElementById("info")

        val titleElement = element.getElementById("dale_book_subject_top_icon")
        val title = titleElement.nextElementSibling().getElementsByAttribute("property").first().text()

        val bookInfo = info.getElementsByClass("pl")

        val bookIterator = bookInfo.toArray(new Array[Element](4))

        var bookInfoMap: scala.collection.mutable.Map[String, String] = scala.collection.mutable.Map()
        bookIterator.foreach((b) =>
        {
            bookInfoMap = bookInfoMap.updated(b.text(), b.nextSibling().toString)
        })

        bookInfoMap = bookInfoMap.updated("作者", info.getElementsByTag("a").first().text())
        var score = content.getElementById("interest_sectl").getElementsByTag("strong").first().text()

        if (score == null || "".equals(score))
        {
            score = "0"
        }
        val book: BookInfo = BookInfo(title, bookInfoMap("作者"), bookInfoMap("ISBN:"), bookInfoMap("出版社:"), score.toFloat)


        println(book)

        fetchLikeBook(element.getElementById("db-rec-section"))
    }


    def fetchLikeBook(element: Element): Unit =
    {
        val like = element.getElementById("db-rec-section")

        val elements = like.getElementsByTag("dd")

        val iterator = elements.iterator()

        import scala.util.control.Breaks._

        breakable
        {
            while (iterator.hasNext)
            {
                if (repeatUtlList.size % hasFetchedUrlList.size.toFloat > 4)
                {
                    break
                }

                val book = iterator.next().getElementsByTag("a").first()

                val url = book.attr("href")

                if (hasFetchedUrlList.contains(url))
                {
                    println("已经抓取过" + url)
                    repeatUtlList.++(url)
                }
                else
                {
                    FetchBooks.fetchBookInfo(url)
                    hasFetchedUrlList.++(url)
                }
            }
        }
    }
}
