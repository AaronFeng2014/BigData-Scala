package com.aaron.scala.matchcase

import org.scalatest.FunSuite

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-04-16
  */
class MatchCaseTest
        extends FunSuite
{

    test("testMatchConstructor")
    {
        MatchCase.matchConstructor(20)
    }

    test("testMatchType")
    {
        MatchCase.matchType(Long.MaxValue)
        MatchCase.matchType(Int.MinValue)
    }

    test("testMatchValue")
    {
        MatchCase.matchValue(23)
        MatchCase.matchValue(18)
    }

    test("testMatchBoolean")
    {
        MatchCase.matchBoolean(20)
        MatchCase.matchBoolean(10)


    }

}
