package com.aarom.scala.companionobject

import org.scalatest.FunSuite

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-04-16
  */
class StudentsTest
        extends FunSuite
{

    test("testStudy")
    {
        var student: Students = new Students()

        student.study()

        student = Students("FengHaixin", 25)

        //student.name$eq()


        student.introduce()
    }

    test("testIntroduce")
    {

    }

}
