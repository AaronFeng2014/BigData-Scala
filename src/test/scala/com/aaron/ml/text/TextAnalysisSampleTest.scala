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

}