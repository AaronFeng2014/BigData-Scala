package com.aaron.ml

import com.aaron.bigdata.SparkContextHelper
import org.apache.spark.mllib.classification.{NaiveBayes, NaiveBayesModel}
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.rdd.RDD


/**
  * 贝叶斯分类算法
  *
  * 这里以二分类为例子，阐述朴素贝叶斯算法
  *
  * 根据训练的数据分类，这里假设有A和B两个类别，分别对应的数据集是D(A)和D(B),
  * 第一步是分别计算出两个类别中各自自变量的均值，标准差（这里是假设了各自类中的数据服从正态分布）
  *
  *
  * 第二步，利用测试集来检测分类的正确率等等，利用这些指标来判断分类器的准确性
  *
  * 第三步，用训练出来的模型，来分类的数据
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
