package com.aaron.ml

import com.aaron.bigdata.SparkContextHelper
import org.ansj.domain.Term
import org.ansj.splitWord.analysis.BaseAnalysis
import org.apache.spark.mllib.feature.{HashingTF, IDF, IDFModel}
import org.apache.spark.mllib.linalg.distributed.{CoordinateMatrix, MatrixEntry}
import org.apache.spark.rdd.RDD

import scala.collection.JavaConversions._

/**
  * 文本分类，关键词提取的IDF
  *
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-11-30
  */
object IDFSample
{

    val sparkSession = SparkContextHelper.getSparkSession(SparkContextHelper.LOCAL_MODEL, "IDF_demo")

    val sparkContext = sparkSession.sparkContext


    def main(args: Array[String]): Unit =
    {
        //转化为List，list中存放的是二元组.(id, content)
        val text: RDD[(Long, String)] = sparkContext.textFile("spark-warehouse/idf.txt").map(line => line.split("\t")).map(array => (array(0).toLong, array(1)))

        text.foreach(line => println(line))
        //每行内容转化为TF向量
        val pair_tf = text.map(line =>
        {
            val hashing = new HashingTF(20)
            //返回的是稀疏向量的表达形式(20,[4,7,8,9,10,16],[1.0,1.0,2.0,1.0,1.0,1.0])

            //第一个元素表示向量的长度，第二个元素是一个数组，表示非0元素的索引，第三个元素也是数组，表示非0元素的值
            //执行分词
            val result: java.util.List[Term] = BaseAnalysis.parse(line._2).getTerms()

            val doc: List[String] = result.toList.map(term => term.getName)

            (line._1, hashing.transform(doc))
        })

        println("TF向量结果=========")
        pair_tf.foreach(println)

        val idfModel: IDFModel = new IDF(1).fit(pair_tf.values)

        val pair_idf = pair_tf.map(line =>
        {
            //计算idf
            (line._1, idfModel.transform(line._2))
        })

        //转化为稠密向量的表现形式，即普通的展示形式
        println("IDF结果=========")
        pair_idf.foreach(idfTuple => println(idfTuple._1 + "\t" + idfTuple._2))


        //pair_idf计算的结果是行向量的形式，需要转化成列向量，这里比较麻烦，我是先转化成坐标矩阵，然后在求转置的
        val matrixEntries: Array[(Long, MatrixEntry)] = pair_idf.zipWithIndex().map(value =>
        {
            //value结构(content,index)
            //每次一个vector，vector转MatrixEntry
            value._1._2.toSparse.toArray.zipWithIndex.map(innerValue =>
            {
                (value._1._1, MatrixEntry(value._2, innerValue._2, innerValue._1))
            })
        }).reduce((a: Array[(Long, MatrixEntry)], b: Array[(Long, MatrixEntry)]) =>
        {
            a.union(b)
        })

        val ratings = new CoordinateMatrix(sparkContext.makeRDD(matrixEntries.map(_._2)))

        //这里只可以计算列的余弦相似性，因此数据的形式必须是按列存储的，否则应当对矩阵求转置再计算余弦相似性
        val similarities = ratings.transpose().toRowMatrix().columnSimilarities()

        //文章id列表
        val articleList: Array[Long] = pair_idf.map(idf => idf._1).collect()

        println("余弦相似性=========")
        similarities.entries.foreach(a => println("文章" + articleList.apply(a.i.toInt) + "和文章" + articleList.apply(a.j.toInt) + "的相似度为：" + a.value))
    }
}
