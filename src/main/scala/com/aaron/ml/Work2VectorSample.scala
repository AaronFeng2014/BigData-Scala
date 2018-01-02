package com.aaron.ml

import com.aaron.bigdata.SparkContextHelper
import org.apache.spark.mllib.feature.{Word2Vec, Word2VecModel}
import org.apache.spark.rdd.RDD

/**
  * 词向量模型
  * 为什么需要该模型
  * 1.传统的文本特征，都是基于词频来生成的，无法表达语义
  * 2.文本较大，维度较高，造成维度灾难，计算耗时
  *
  *
  * 原理：
  * 根据文库中的词汇，训练联合概率
  *
  * 用途：
  * 传统的相似性计算方式是根据句子中词语的频率来计算的，因此无法计算语义相似，但单词不同的句子的像相似度
  * 而词向量模型可以解决该问题
  *
  * 例如： 圣诞节和苹果，这两个词在传统的相似性计算时，无论时余弦相似性还是雅克比相似性都是0，
  * 但是根据常识我们知道这两个词是有关系的，
  *
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-12-17
  */
object Work2VectorSample
{

    def main(args: Array[String]): Unit =
    {

        val session = SparkContextHelper.getSparkSession(SparkContextHelper.LOCAL_MODEL, "Work2VectorSample")

        val data: RDD[Seq[String]] = session.sparkContext.textFile("spark-warehouse/word2vec.txt").map(line => line.split(" "))

        val word2Vec = new Word2Vec()


        //词频， 过滤低于该频率的词汇
        word2Vec.setMinCount(1)

        //特征向量的维度
        word2Vec.setVectorSize(5)
        val word2VecModel: Word2VecModel = word2Vec.fit(data)


        for ((key, value) <- word2VecModel.getVectors)
        {
            println("单词：" + key + ",特征向量：" + value.toList)
            //value.foreach(println(_))
            //println("=================")
            //计算距离
            for ((innerKey, innerValue) <- word2VecModel.getVectors)
            {
                var sum: Double = 0


                for (i: Int <- value.indices)
                {
                    sum += math.pow(value(i) - innerValue(i), 2)
                }
                println("单词：" + key + " 和单词：" + innerKey + " 的欧氏距离：" + math.sqrt(sum))
                //println("单词：" + key + " 和单词：" + innerKey + " 的余弦相似性：" +)
            }
        }
        //session.sparkContext.makeRDD()
        //word2VecModel.getVectors.map((key: String, value: Array[Float]) => Vectors.dense(value.map(_.toDouble)))
    }

}
