package com.aaron.ml

import com.aaron.bigdata.SparkContextHelper
import org.apache.spark.ml.regression.{LinearRegression, LinearRegressionModel}
import org.apache.spark.rdd.RDD

/**
  * 线性回归模型： y = ax + b，向量表现形式，a为行向量，x为列向量，b为残差
  *
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-12-07
  */
object LinearRegressionSample
{

    def main(args: Array[String]): Unit =
    {

        val sparkSession = SparkContextHelper.getSparkSession(SparkContextHelper.LOCAL_MODEL, "LinearRegressionSample")


        val rdd: RDD[Array[Double]] = sparkSession.sparkContext.textFile("spark-warehouse/linearRegression.txt").map(line => line.split(" ").map(_.toDouble))


        //implicit  val encoder: Encoder = new ExpressionEncoder()

        val dataSet = sparkSession.createDataset(rdd)

        val model: LinearRegression = new LinearRegression()

        model.setMaxIter(10)


        val regressionModel: LinearRegressionModel = model.fit(dataSet)

        println(regressionModel.coefficients)
    }

}
