package com.aaron.ml.text

import org.scalatest.FunSuite

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2018/8/16
  */
class TextAnalysisSampleTest
        extends FunSuite
{

    test("testCosBased")
    {
        TextAnalysisSample.cosBased("今天的天气真的很好呢","我们明天去做什么呢")
    }


    test("testHammingDistanceBased")
    {
        TextAnalysisSample.hammingDistanceBased("我今天想吃苹果","我今天想吃香蕉")
    }

    test("testSimHash") {
      val text_a = "北京 北京 天安门 中国 广场 广场 清晨 升旗仪式"
      val text_b = "成都 天府 广场 广场 大妈 大妈 广场舞"
      val text_c = "北京 天安门 中国 广场 清晨 游客 升旗仪式"

      TextAnalysisSample.simHash(text_a, text_b)

      TextAnalysisSample.simHash(text_a, text_c)
    }

}