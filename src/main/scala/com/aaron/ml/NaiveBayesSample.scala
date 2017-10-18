package com.aaron.ml

import com.aaron.bigdata.SparkContextHelper
import org.apache.spark.mllib.classification.{NaiveBayes, NaiveBayesModel}
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.rdd.RDD


/**
  * 贝叶斯分类算法
  *
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-10-17
  */
object NaiveBayesSample
{

    def main(args: Array[String]): Unit =
    {
        val sparkContext = SparkContextHelper.getSparkContext("local[2]", "test")

        val rdd = sparkContext.textFile("spark-warehouse/bayes.txt").map(line =>
        {
            val parts = line.split(",")
            LabeledPoint(parts(0).toDouble, Vectors.dense(parts(1).split(" ") map (_.toDouble)))
        })

        /**
          * 把数据分为训练数据和测试数据
          * 训练数据用于训练模型，测试数据用于测试训练出来的模型的正确性
          */
        val res: Array[RDD[LabeledPoint]] = rdd.randomSplit(Array(0.7, 0.3), seed = 11L)

        val model: NaiveBayesModel = NaiveBayes.train(res(0), lambda = 0.7)

        val predict = res(1).map(p =>
        {
            println(p.label + "--->" + p.features)
            (model.predict(p.features), p.label)
        })


        val accuracy = 1.0 * predict.filter(f => f._2 == f._1).count() / res(1).count()

        predict.foreach(f =>
        {
            println("模型预测分类：" + f._1 + " ===> 实际分类：" + f._2)
        })

        println("召回率====>" + accuracy)
    }
}
