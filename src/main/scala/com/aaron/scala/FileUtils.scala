package com.aaron.scala

import java.io.{FileOutputStream, OutputStream}

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-07-28
  */
object FileUtils
{
    val rootPath = "spark-warehouse/"


    /**
      * 把内容写到指定的文件中
      *
      * @param content     StringBuilder：待写入的内容
      * @param destination String：文件路径
      * @param append      Boolean：写入方式， true为追加到文件末尾
      */
    def write2File(content: StringBuilder, destination: String, append: Boolean): Unit =
    {
        val start = System.currentTimeMillis()
        val stream: OutputStream = new FileOutputStream(rootPath + destination, append)

        try
        {
            stream.write(content.toString().getBytes("UTF-8"))
        }
        catch
        {
            case e: Throwable => e.printStackTrace()
        }
        finally
        {
            stream.close()
            println("文件内容写入成功，耗时：" + (System.currentTimeMillis() - start) + "毫秒")
        }
    }
}
