package com.aaron.ml

import com.aaron.bigdata.SparkContextHelper
import org.apache.spark.mllib.clustering.{KMeans, KMeansModel}
import org.apache.spark.mllib.linalg.Vectors

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-07-27
  */
object KMeansDemo
{
    def main(args: Array[String]): Unit =
    {

        val sparkContext = SparkContextHelper.getSparkContext("local[2]", "test")

        val data = sparkContext.textFile("spark-warehouse/kmeans.txt").map(line =>
        {
            Vectors.dense(line.split(",").map(_.toDouble))
        })

        val model: KMeansModel = KMeans.train(data, 5, 10)

        model.clusterCenters.foreach(println)

        println(model.predict(Vectors.dense(Array(5.toDouble, 6.toDouble))))
        println(model.predict(Vectors.dense(Array(12.toDouble, 10.toDouble))))
        println(model.predict(Vectors.dense(Array(2.toDouble, 9.toDouble))))
        println(model.predict(Vectors.dense(Array(9.toDouble, 3.toDouble))))
        println(model.predict(Vectors.dense(Array(1.toDouble, 1.toDouble))))
    }

}
