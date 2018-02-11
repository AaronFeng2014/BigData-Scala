package com.aaron.bigdata

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2018/2/11
  */
object WordCountSample
{

    def main(args: Array[String]): Unit =
    {

        val sparkSession = SparkContextHelper.getSparkSession("local[2]", "word count")

        val rdd = sparkSession.sparkContext.textFile("spark-warehouse/hangban.txt", 4).map(_.split("\t")).flatMap(wordList =>
        {

            wordList.map(_ + "000")

        }).map((_, 1)).reduceByKey(_ + _)


        rdd.foreach(array =>
        {
            println(array)
        })

    }
}
