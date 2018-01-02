package com.aaron.ml

import com.aaron.bigdata.SparkContextHelper
import org.apache.spark.mllib.fpm.{FPGrowth, FPGrowthModel}
import org.apache.spark.rdd.RDD

/**
  * 关联规则
  *
  * 条件模式基
  * 频繁项分析，最简单的例子就是超市物品的摆放，主要利用到了频繁模式树，又称前缀树
  *
  * 分析每个用户购买的物品，因此数据格式是，array，每个array表示每个用户购买的商品列表
  *
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2018-01-01
  */
object FPTreeSample
{
    val sparkSession = SparkContextHelper.getSparkSession(SparkContextHelper.LOCAL_MODEL, "fpGrowthModel")


    def main(args: Array[String]): Unit =
    {

        fpTree()
    }


    def fpTree(): Unit =
    {

        val iterms: RDD[Array[String]] = sparkSession.sparkContext.textFile("spark-warehouse/fpgrowth.txt").map(line => line.split(" "))

        val fPGrowth: FPGrowth = new FPGrowth()

        fPGrowth.setMinSupport(0.6)

        val fpGrowthModel: FPGrowthModel[String] = fPGrowth.run(iterms)

        println("关联规则分析结果")
        fpGrowthModel.freqItemsets.foreach(f => println(f))
    }

}
