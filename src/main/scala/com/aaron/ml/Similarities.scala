package com.aaron.ml

import com.aaron.bigdata.SparkContextHelper
import org.apache.spark.mllib.linalg.distributed.{CoordinateMatrix, MatrixEntry}

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-11-27
  */
object Similarities
{
    val session = SparkContextHelper.getSparkSession(SparkContextHelper.LOCAL_MODEL, "Similarities")

    val sparkContext = session.sparkContext


    def main(args: Array[String]): Unit =
    {

        columnSimilarities()
    }


    def columnSimilarities(): Double =
    {
        val parseData = sparkContext.textFile("spark-warehouse/similar.txt").map(line =>
        {
            val one = line.split(",")(0).toLong
            val two = line.split(",")(1).toLong
            val three = line.split(",")(2).toLong

            MatrixEntry(one, two, three)
        })

        val ratings = new CoordinateMatrix(parseData)


        val matrix = ratings.transpose().toRowMatrix()

        val result = matrix.columnSimilarities(0)

        result.entries.foreach(a => println("i=" + a.i + ",j=" + a.j + ",value=" + a.value))

        0d

    }
}
