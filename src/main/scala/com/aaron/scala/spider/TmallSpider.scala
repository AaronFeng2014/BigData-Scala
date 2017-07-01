package com.aaron.scala.spider

import java.util

import com.aaron.scala.spider.zhihu.entity.Goods
import org.jsoup.Jsoup
import org.jsoup.nodes.{Document, Element}
import org.jsoup.select.Elements

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2016-11-29
  */
object TmallSpider
{
    val url: String = "https://list.tmall.com/search_product.htm?cat=50072114&sort=s&acm=lb-zebra-25393-317332.1003.8.445137&style=g&search_condition=7&industryCatId=50072114&active=1&spm=a220m.1000858.1000721.2.cRzPZ0&from=sn_1_cat&scm=1003.8.lb-zebra-25393-317332.ITEM_14460674816291_445137#J_crumbs"


    def main(args: Array[String])
    {


        getPage(url)
    }


    /**
      * 递归抓取商品的每一页数据
      *
      * @param url String：商品地址
      */
    def getPage(url: String): Unit =
    {
        val document: Document = Jsoup.connect(url).get()

        val element: Element = document.body()
        val elements: Elements = element.getElementsByClass("product  ")

        val iterator: util.Iterator[Element] = elements.iterator()

        while (iterator.hasNext)
        {
            val ele: Element = iterator.next()
            val name = ele.getElementsByClass("productTitle").first().getElementsByTag("a").first().text()

            println(name)
        }

        getPage(url)
    }


    private[this] def write2File(goodsList: List[Goods]): Unit =
    {
        val stringBuffer: StringBuilder = new StringBuilder()
        goodsList.foreach((goods: Goods) =>
        {
            stringBuffer.append(goods.name).append("\t")
            stringBuffer.append(goods.price).append("\r\n")
        })

        write(stringBuffer)
    }


    def write(stringBuffer: StringBuilder): Unit =
    {

    }

}
