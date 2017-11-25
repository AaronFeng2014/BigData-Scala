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

        val session = SparkContextHelper.getSparkSession(SparkContextHelper.remoteSparkMaster, "KMeansDemo-success")

        //如果提交到远程执行，则需要把相关的jar包也要提交上去，否则找不到相关的信息
        session.sparkContext.addJar("D:\\develop\\code\\personalCode\\BigData-Scala\\classes\\artifacts\\bigdata_scala_jar\\bigdata-scala.jar")

        val sparkContext = session.sparkContext

        //默认读取本地文件系统中的文件，如果是要提交到远程的spark中运行，这里的路径需要是远程系统中的路径或者是hdfs中的路径
        val data = sparkContext.textFile(SparkContextHelper.remoteHdfsPath + "/home/spark/spark-warehouse/kmeans.txt").map(line =>
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
