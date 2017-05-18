package com.aarom.scala.methodParameter

import org.scalatest.FunSuite

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-04-05
  */
class MethodParameterLearningTest extends FunSuite
{

    test("testShowName")
    {
        MethodParameterLearning.showName(MethodParameterLearning.tellName, 5)
    }

}
