package com.aaron.ml

import com.aaron.bigdata.SparkContextHelper
import org.apache.spark.SparkContext
import org.apache.spark.mllib.linalg.{Vector, Vectors}
import org.apache.spark.mllib.stat.{MultivariateStatisticalSummary, Statistics}
import org.apache.spark.rdd.RDD

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-05-29
  */
object VectorDemo
{

    def main(args: Array[String]): Unit =
    {
        //创建一个稠密向量
        val denseVector = Vectors.dense(5, 0, 0, 0, 1, 0, 0, 0, 9, 0, 0)

        /*
        * compressed方法作用于一个稠密型向量的表示法时，会压缩为稀疏表示法
        *
        */
        println(denseVector.compressed)

        /**
          * 创建一个稀疏向量(0,2,0,0,1,0,0,0,9,0,0)
          * 第一个参数表示向量的长度
          * 第二个参数表示向量中不为0的元素的索引，从0开始计算
          * 第三个参数表示向量中不为0的元素的值，与第二个参数中的索引是一一对应的
          **/
        val sparseVector = Vectors.sparse(120, Array(1, 4, 8), Array(2, 1, 9))

        val sparkContext: SparkContext = SparkContextHelper.getSparkContext("local[2]", "mlLibDemo")


        val rdd: RDD[Vector] = sparkContext.textFile("spark-warehouse/data.txt").map(_.split(",")).map(line => Vectors.dense(line.map(_.toDouble)))

        /**
          * 案列统计数据： 描述统计
          * MultivariateStatisticalSummary
          */
        val result: MultivariateStatisticalSummary = Statistics.colStats(rdd)

        println("均值：" + result.mean)
        println("最大值：" + result.max)
        println("最小值：" + result.min)
        println("方差：" + result.variance)
        println("非0个数：" + result.numNonzeros)

        println(sparseVector.asML)

        relationAnalyze()
    }


    def relationAnalyze(): Unit =
    {
        val sparkContext: SparkContext = SparkContextHelper.getSparkContext("local[2]", "mlLibDemo")

        val x: RDD[Double] = sparkContext.makeRDD((2 to 20 by 2).map(_.toDouble))

        val y: RDD[Double] = sparkContext.makeRDD((20 to 38 by 2).map(_.toDouble))

        /**
          * 皮尔森相关系数，这是求两个向量的线性相关性的方法，利用最小二乘
          */
        val relation: Double = Statistics.corr(x, y, "pearson")

        println("向量x和y的线性相关性：" + relation)
    }
}
