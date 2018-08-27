package com.aaron.ml.text
import com.aaron.ml.IDFSample.sparkContext
import org.apache.spark.rdd.RDD

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2018-08-12
  */
object TextAnalysisSample {

  def main(args: Array[String]): Unit = {
    simHash()
  }

  def simHash(): Unit = {

    val text: RDD[Array[String]] = sparkContext.textFile("spark-warehouse/simhash.txt").map(line => line.split(" "))

    val hash = text
      .zipWithIndex()
      .map(line => {
        line._1.map(word => {
          (line._2, word, word.hashCode() % 1000)
        })
      })
      .flatMap(s => s)

    hash.foreach(w => println("文本：" + w._1 + "" + w._2 + "的hash值是" + w._3 + ",二进制hash值是" + Integer.toBinaryString(w._3)))
  }
}
