package com.aaron.bigdata.sparksql

import com.aaron.bigdata.DataBaseHelper
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.spark.sql.{Row, SparkSession}

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-05-26
  */
object SparkSqlDemo
{

    //val PATH = "hdfs://192.168.2.175:25555/home/aaron/hadoopData/people.txt"
    val PATH = "spark-warehouse/people.json"

    System.setProperty("hadoop.home.dir", "D:\\develop\\大数据\\hadoop-2.7.3")


    def main(args: Array[String]): Unit =
    {

        write2Db()
    }


    def fromLocalFile(): Unit =
    {
        val session = SparkSession.builder().master("local[2]").appName("SparkSql Demo").getOrCreate()

        val sqlContext = session.sqlContext

        val dataFrame = sqlContext.read.json(PATH)


        dataFrame.createOrReplaceTempView("people")

        val frame = sqlContext.sql("select a.name,a.age,a.address from people  a where age > 20 and age < 30")

        frame.show()
    }


    def fromJdbc(): Unit =
    {
        val session = SparkSession.builder().master("local[2]").appName("SparkSql Demo").getOrCreate()

        val sqlContext = session.sqlContext

        val df = sqlContext.read.jdbc(DataBaseHelper.MySql.URL, "config", DataBaseHelper.MySql.getConnectionProperties())

        //创建一个视图，后面就可以像sql一样使用该视图
        df.createOrReplaceTempView("config")

        //按type列分组统计
        df.groupBy("type").sum("type").as("总和").show()
        val some = sqlContext.sql("select t.type, count(1) from config t group by t.type")

        some.show()

        //some.foreach(row => println(row.get(4)))
    }


    def write2Db() =
    {
        val session = SparkSession.builder().master("local[2]").appName("SparkSql Demo").getOrCreate()

        val sqlContext = session.sqlContext


        val rdd: RDD[Row] = session.sparkContext.textFile("spark-warehouse/ezeegocity.txt").map(line => line.split("\t")).map[Row](line =>
        {

            val row: Row = Row(line(0), line(1), line(2), line(3))

            row
        })

        val schema = StructType(
            List(
                StructField("cityName", StringType, false),
                StructField("cityCode", StringType, true),
                StructField("countryCode", StringType, true),
                StructField("countryName", StringType, false)
            )
        )

        val cityData = session.createDataFrame(rdd, schema)

        cityData.createOrReplaceTempView("cityInfo")


        //val newFrame = sqlContext.sql("select a.countryName, count(1) as totalCity from cityInfo a group by a.countryName order by totalCity DESC")

        //newFrame.show(500)


        val total = sqlContext.sql("select count(1) from cityInfo")

        total.show()

        val newFrame = sqlContext.sql("select a.cityCode, count(1) as totalCity from cityInfo a group by a.cityCode order by totalCity DESC")

        newFrame.show(500)


        //cityData.write.mode(SaveMode.Append).jdbc(DataBaseHelper.MySql122.URL, "ezeego1_city", DataBaseHelper.MySql122.getConnectionProperties())


    }
}
