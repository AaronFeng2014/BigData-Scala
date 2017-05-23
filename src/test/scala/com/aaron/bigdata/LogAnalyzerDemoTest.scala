package com.aaron.bigdata

import org.scalatest.{BeforeAndAfterEach, FunSuite}

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-05-23
  */
class LogAnalyzerDemoTest extends FunSuite with BeforeAndAfterEach
{

    override def beforeEach()
    {

    }


    override def afterEach()
    {

    }


    test("testSocketStreaming")
    {
        LogAnalyzerDemo.socketStreaming()
    }

    test("testFileSystemStreaming")
    {
        LogAnalyzerDemo.fileSystemStreaming()
    }

}
