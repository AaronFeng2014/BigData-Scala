package com.aaron.ml

import com.aaron.bigdata.SparkContextHelper
import org.apache.spark.ml.regression.{LinearRegression, LinearRegressionModel}

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

        SparkContextHelper.getSparkSession(SparkContextHelper.LOCAL_MODEL, "LinearRegressionSample")

        val model: LinearRegression = new LinearRegression()

        // LinearRegression.

        LinearRegressionModel.load("spark-warehouse/linearRegression.txt")

    }

}
