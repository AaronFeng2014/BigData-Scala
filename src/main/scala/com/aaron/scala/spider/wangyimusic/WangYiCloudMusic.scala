package com.aaron.scala.spider.wangyimusic

import java.io.{FileOutputStream, OutputStream}
import java.util

import com.google.common.collect.ImmutableMap
import org.jsoup.Jsoup
import org.jsoup.nodes.{Document, Element}
import org.jsoup.select.Elements

import scala.collection.immutable.HashMap.HashTrieMap
import scala.util.parsing.json.JSON

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-07-01
  */
object WangYiCloudMusic
{
    val domain = "http://music.163.com"

    val beginUrl = "http://music.163.com/discover/artist/cat?id=1001"

    val result: String = "spark-warehouse/songComment.txt"


    def main(args: Array[String]): Unit =
    {

        val document: Document = Jsoup.connect(beginUrl).get()

        //歌手列表
        val element: Element = document.body().getElementById("m-artist-box")

        findSinger(element)
    }


    def findSinger(singerElement: Element): Unit =
    {
        //热门歌手
        val singerList: Elements = singerElement.getElementsByClass("u-cover-5")

        val iterator: util.Iterator[Element] = singerList.iterator()

        val outputStream: OutputStream = new FileOutputStream(result, true)

        while (iterator.hasNext)
        {
            val singer: Element = iterator.next()

            val musicListPath = domain + singer.getElementsByTag("a").first().attr("href")
            val singerName = singer.getElementsByTag("a").first().attr("title")

            val stringBuilder: StringBuilder = new StringBuilder()

            stringBuilder.append("歌手名称：" + singerName + "\r")

            findMusic(musicListPath, stringBuilder)

            write2File(outputStream, stringBuilder)
        }
    }


    def write2File(outputStream: OutputStream, content: StringBuilder): Unit =
    {
        outputStream.write(content.toString().getBytes())
        outputStream.flush()

    }


    def findMusic(musicPath: String, resultBuilder: StringBuilder): Unit =
    {
        val musicPage: Document = Jsoup.connect(musicPath).get()
        val allMusicTable = musicPage.body().getElementById("artist-top50").getElementById("song-list-pre-cache")

        val musicTr: Elements = allMusicTable.getElementsByTag("li")

        val musicIterator: util.Iterator[Element] = musicTr.iterator()

        while (musicIterator.hasNext)
        {
            val musicElement: Element = musicIterator.next()

            val oneMusic: Element = musicElement.getElementsByTag("a").first()

            val musicId = oneMusic.attr("href").substring(oneMusic.attr("href").lastIndexOf("=") + 1)
            val commentUrl: String = commentUrlPrefix + musicId
            val musicName: String = oneMusic.getElementsByTag("a").first().text()
            resultBuilder.append("\t歌曲名称：" + musicName + "\r")
            findComment(commentUrl, resultBuilder)
        }
    }


    val commentUrlPrefix: String = "http://music.163.com/weapi/v1/resource/comments/R_SO_4_"


    val header: java.util.Map[String, String] = ImmutableMap.of("Content-Type", "application/x-www-form-urlencoded",
        "Cookie", "vjuids=29d2eb511.15982a265f2.0.f8ea7aa6dbfd3; _ntes_nnid=122d697362fbc028fdb13717a70418eb,1483955398136; usertrack=c+5+hlh2+hQTpSHMB5UyAg==; vjlast=1492591266.1492591266.30; vinfo_n_f_l_n3=0f3f473295dcef56.1.0.1492591266516.0.1492592108034; JSESSIONID-WYYY=0Pw1lW1kAEgUXATGPIhx4pi%2FabC0CqniHYpPIg%2FzUmaKlOr5NB4U1xvATMN3rc2nSDuE3uXkMk6lrHChTS%2BTFIDkoeWRwMVIDFFkhvSe2P%5CGdz88keQHCK%5Cmyy8Ahpkg%2Fy5PrDxdg9b7JG5kW58fZI76FYRzKUgPIQRfG1jsjVZ48DHE%3A1498892075556; _iuqxldmzr_=32; __utma=94650624.1655168919.1492787288.1498878090.1498888872.6; __utmb=94650624.32.10.1498888872; __utmc=94650624; __utmz=94650624.1495088026.3.3.utmcsr=baidu|utmccn=(organic)|utmcmd=organic",
        "User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36",
        "Connection", "keep-alive",
        "Accept-Language", "zh-CN,zh;q=0.8")

    val formData: java.util.Map[String, String] =
        ImmutableMap.of("encSecKey", "47244311f072bf263b0d8ae0a529d91dbeebe0360f46ee2a7540b6f61784bfb57ea5a8cb2f1804b11c37c56bb56def8526caa120b32bed27bb3e74d715946d8f34d730d9905d2077202d8a59d8c9d982dc910c8b6af5559ba3574d533425bd21352ca04a03a397078f4fe3bc2d486d2b33175b920400d3c4b36bdbec5ffad6a8",
            "params", "W6nI+mGm+KASF/Xb+xZu/yBNmo7NVa0hMcW1zrYV/L3oVKsuG+jGwpdA0D0HC/p3DPgLSfR9szjcY0iggiEjG0vtSdr3pcQ1gmmrLBGp2iQo3K0CUehrvZk7m2sbnoorQFXGpfoZMSr9kPvAYzDK/LCA2CW3Qw9L5KANv8ghqiMwIDl3wW93HOkLPA3rfHI5")


    def findComment(musicUrl: String, resultBuilder: StringBuilder): Unit =
    {
        val musicDetailPage: Document = Jsoup.connect(musicUrl).headers(header).data(formData).post()

        val content: Option[Any] = JSON.parseFull(musicDetailPage.text())

        content.get match
        {
            case commentDetails: HashTrieMap[Any, Any] =>
            {
                // commentDetails.foreach[Unit]((tuple2: (Any, Any)) => println(tuple2._2))

                for (i <- commentDetails if "comments".equals(i._1) && i._2.isInstanceOf[scala.collection.immutable.$colon$colon[HashTrieMap[Any, Any]]])
                {
                    val c: scala.collection.immutable.$colon$colon[HashTrieMap[Any, Any]] = i._2.asInstanceOf[scala.collection.immutable.$colon$colon[HashTrieMap[Any, Any]]]

                    for (lastComment <- c if lastComment.isInstanceOf[HashTrieMap[Any, Any]])
                    {
                        for (cc <- lastComment if "content".equals(cc._1))
                        {
                            resultBuilder.append("\t\t" + cc._2 + "\r")
                        }
                    }
                }
            }

            case _ =>
        }
    }
}
