package com.aaron.ml

import com.aaron.bigdata.SparkContextHelper
import org.apache.spark.mllib.clustering.{KMeans, KMeansModel}
import org.apache.spark.mllib.linalg.Vectors

/**
  * k阶均值聚类算法
  *
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

        /**
          * 参数说明：
          * 第一个参数表示数据源
          * 第二个参数表示聚类中心的个数
          * 第三个参数表示最大迭代次数
          */
        //根据原始数据，训练模型
        val model: KMeansModel = KMeans.train(data, 5, 10)

        model.clusterCenters.foreach(println)

        //在训练出的模型上面做预测
        println(model.predict(Vectors.dense(Array(5.toDouble, 6.toDouble))))
        println(model.predict(Vectors.dense(Array(12.toDouble, 10.toDouble))))
        println(model.predict(Vectors.dense(Array(2.toDouble, 9.toDouble))))
        println(model.predict(Vectors.dense(Array(9.toDouble, 3.toDouble))))
        println(model.predict(Vectors.dense(Array(1.toDouble, 1.toDouble))))
    }

}
