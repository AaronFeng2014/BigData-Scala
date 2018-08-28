package com.aaron.ml.text

import com.aaron.ml.IDFSample.sparkContext
import org.apache.spark.rdd.RDD

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2018-08-12
  */
object TextAnalysisSample
{

    def main(args: Array[String]): Unit =
    {
        simHash()
    }


    def simHash(): Unit =
    {

        val text: RDD[Array[String]] = sparkContext.textFile("spark-warehouse/simhash.txt").map(line => line.split(" "))

        val hash = text.zipWithIndex().map(line =>
        {
            line._1.map(word =>
            {
                (line._2, word, word.hashCode() % 1000)
            })
        }).flatMap(s => s)

        hash.foreach(w => println("文本：" + w._1 + "" + w._2 + "的hash值是" + w._3 + ",二进制hash值是" + Integer.toBinaryString(w._3)))
    }


    /**
      * 基于余弦值计算相似性
      *
      * 利用坐标向量求余弦值，余弦值越小，相似性越大
      *
      * 当余弦值为1时，文本完全相同；当余弦值为0时，两文本完全不同
      *
      * 文本向量化过程：
      *
      * 文本a：我爱我的国家
      * 文本b：我的国家是中国
      *
      * 1. 分词
      *
      * 文本a：我 爱 我的 国家
      * 文本b：我的 国家 是 中国
      *
      * 2. 合并
      *
      * 结果： 我 爱 我的 国家 是 中国
      *
      * 3. 得到文本的向量表征
      *
      * 文本a：(1，1，1，1，0，0)
      * 文本b：(0，0，1，1，1，1)
      *
      * 4. 向量余弦公式
      *
      * cos ∂ = a * b /(|a| * |b|)
      * = (1 * 0 + 1 * 0 + 1 * 1 + 1 * 1 + 1 * 0 + 1 * 0) / (√4 * √4)
      * = 2 / 4
      * = 0.5
      **/
    def cosBased(word1: String, word2: String): Unit =
    {
        println("基于余弦值计算文本相似度")
    }


    /**
      * 基于汉明距离计算相似性
      *
      * 汉明距离定义：
      *
      * 1. 两个等长度字符串
      * 2. 相同索引位置上，字符串不相等的个数，即为汉明距离
      *
      * 汉明距离越小说明2个文本的相似性越大；反之，越小
      *
      * 用途：
      *
      * 在miniHash最后一步中，需要使用汉明距离来检测hash值的相似性，来确定文本的相似性
      */
    def hammingDistanceBased(word1: String, word2: String): Unit =
    {
        println("基于汉明距离计算文本相似度")

        if (word1.length != word2.length)
        {
            throw new IllegalArgumentException("两个参数需要长度一致")
        }

        val same = word1.zipWithIndex.filter(charWithIndex => !charWithIndex._1.equals(word2.charAt(charWithIndex._2))).map(_ => 1).sum

        println("汉明距离：" + same)
    }
}
