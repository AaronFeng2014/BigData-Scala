package com.aaron.ml.text

import com.aaron.ml.IDFSample.sparkContext

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2018-08-12
  */
object TextAnalysisSample
{

    def main(args: Array[String]): Unit =
    {
        simHash("", "")
    }


    /**
      * SimHash算法
      *
      * 示例文本：
      * a: 北京的天安门是中国最大的广场，那里每天清晨都会举行隆重的升旗仪式 --> 北京 天安门 中国 广场 清晨 升旗仪式
      *
      * b: 成都的天府广场是成都最大的广场，那里每天都有很多大妈跳广场舞 --> 成都 天府 广场 大妈 广场舞
      *
      * c: 北京的天安门是中国最大的广场，那里每天清晨都有很多游客观看升旗仪式 --> 北京 天安门 中国 广场 清晨 游客 升旗仪式
      *
      *
    */
    def simHash(text1: String, text2: String): Unit =
    {

      val text1Rdd = sparkContext.makeRDD(text1.split(" "))

      //这里取1000个特征，计算hash值
      val hash1 = text1Rdd.map(word => (word, word.hashCode % 1000))
      hash1.foreach(result => println("原始hash： 词，" + result._1 + ", hash，%s", Integer.toBinaryString(result._2)))
      //统计权重
      val weigh1 = hash1.countByKey()

      weigh1.foreach(word => println("文本1权重计算: 词，" + word._1 + "，权重，" + word._2))

      val text2Rdd = sparkContext.makeRDD(text2.split(" "))

      val hash2 = text2Rdd.map(word => (word, word.hashCode % 1000))
      val weigh2 = hash2.countByKey()

      weigh2.foreach(word => println("文本2权重计算: 词，" + word._1 + "，权重：" + word._2))

      //加权
      val weighHash1 = hash1.map(word =>
        {
          val weigh: Long = weigh1.getOrElse(word._1, 1)

          (word._1,
           Integer
             .toBinaryString(word._2)
             .map(c => {
             if (c >= 0) weigh else weigh * -1
           }))
      })

      weighHash1.foreach(result => println("加权hash： 词，" + result._1 + ", hash，%s", result._2))

      val weighHash2 = hash2.map(word => {
        val weigh: Long = weigh1.getOrElse(word._1, 1)

        (word._1,
         Integer.toBinaryString(word._2)
           .map(c => {
             if (c >= 0) weigh else weigh * -1
           }))
      })

      weighHash2.foreach(result => println("加权hash： 词，" + result._1 + ", hash，%s", result._2))
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
