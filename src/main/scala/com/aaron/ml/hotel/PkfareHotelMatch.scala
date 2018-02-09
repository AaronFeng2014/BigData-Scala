package com.aaron.ml.hotel

import java.sql.Connection
import java.util.concurrent.{Phaser, TimeUnit}

import com.aaron.bigdata.DataBaseHelper
import com.aaron.scala.FileUtils
import org.apache.spark.sql.{Row, SparkSession}

import scala.concurrent.forkjoin
import scala.concurrent.forkjoin.{ForkJoinPool, ForkJoinTask, RecursiveAction}
import scala.util.control.Breaks._

/**
  * 基于tf idf的文件匹配
  *
  * 或者基于余弦计算相似性
  *
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2018/1/29
  */
object PkfareHotelMatch
{

    val forkJoinPool: ForkJoinPool = new forkjoin.ForkJoinPool(20)


    val phaser = new Phaser(1)


    def main(args: Array[String]): Unit =
    {
        getHotelFromDb

        /*val addr = InetAddress.getLocalHost
        System.out.println("Local HostAddress:"+addr.getHostAddress)
        val hostname = addr.getHostName
        System.out.println("Local host name: "+hostname)*/
    }


    class Task(list: List[Row], matchSource: List[Row]) extends RecursiveAction
    {


        override def compute(): Unit =
        {

            //执行任务
            //phaser.register()
            if (list.lengthCompare(300) <= 0)
            {


                println("当前线程：" + Thread.currentThread().getName + "，当前处理大小：" + list.size + ", hash:" + list.hashCode())
                doMatch(list, matchSource)

            }
            else
            {
                //分解任务

                val left = list.slice(0, list.size / 2)
                val right = list.slice(list.size / 2, list.size)

                val leftTask = new Task(left, matchSource)
                val rightTask = new Task(right, matchSource)

                //leftTask.fork()
                //rightTask.fork()

                ForkJoinTask.invokeAll(leftTask, rightTask)
            }
            ///phaser.arriveAndAwaitAdvance()


        }
    }


    def getHotelFromDb(): Unit =
    {
        val connection = DataBaseHelper.getConnection("jdbc:mysql://192.168.1.122:3306/hotel?useUnicode=true&characterEncoding=utf8", "hotel", "hot4el")
        val session = SparkSession.builder().master("local[2]").appName("SparkSql Demo").getOrCreate()


        val standardCityDataFrame: List[Row] = getStandardCityInfo(connection, session)

        val xmdCityDataFrame: List[Row] = getXmdCityInfo(connection, session)


        //connection.close()

        val start = System.currentTimeMillis()

        forkJoinPool.submit(new Task(xmdCityDataFrame, standardCityDataFrame))


        forkJoinPool.awaitTermination(10, TimeUnit.MINUTES)
        //phaser.arriveAndAwaitAdvance()

        println("并发匹配耗时：" + (System.currentTimeMillis() - start))
    }


    private def doMatch(xmdCityDataFrame: List[Row], standardCityDataFrame: List[Row]) =
    {
        xmdCityDataFrame.foreach(xmdRow =>
        {

            try
            {
                val fullName = xmdRow.getString(1)

                val xmdFullNameArray = fullName.replaceAll("and", "").replaceAll("of", "").replaceAll("the", "").replaceAll("\\\\\'", "").replaceAll("\\'", "").replaceAll(",", "").split("[ ]+").toSet

                var score = (0D, Row(""))

                breakable
                {

                    standardCityDataFrame.foreach(standardRow =>
                    {
                        val standardFullNameArray = standardRow.getString(1).replaceAll("and", "").replaceAll("of", "").replaceAll("the", "").replaceAll("\\\\\'", "").replaceAll("\\'", "").replaceAll(",", "").split("[ ]+").toSet

                        val total = xmdFullNameArray.union(standardFullNameArray).size.toDouble

                        val same = (xmdFullNameArray & standardFullNameArray).size.toDouble

                        //雅克比相似性计算
                        val current = (same / total, standardRow)

                        if (current._1 > score._1)
                        {
                            score = current
                        }

                        if (current._1 == 1D)
                        {
                            break
                        }

                    })

                }

                val matchResult = xmdRow.getString(0) + "\t" + xmdRow.getString(1) + "\t" + score._1 + "\t" + score._2.getString(0) + "\t" + score._2.getString(1) + "\t" + Thread.currentThread().getName + "\r\n"
                FileUtils.write2File(matchResult, "matchResult.txt", true)
            }
            catch
            {
                case _ => println("错误：" + xmdRow)
            }
        })
    }


    def getStandardCityInfo(connection11: Connection, session: SparkSession): List[Row] =
    {

        val connection22 = DataBaseHelper.getConnection("jdbc:mysql://rr-bp1ig5ki4ov0e7lseo.mysql.rds.aliyuncs.com:3306/hotel?useUnicode=true&characterEncoding=utf8", "hotel_r", "PKfare2017")

        val sql = "SELECT max(c.region_id) regionId, c.region_name_long FROM hotel.cmm_region c WHERE c.language_code = 'en_US' AND c.region_type = 'City' GROUP BY c.region_name_long"

        val resultSet = connection22.createStatement().executeQuery(sql)

        var standardCityList: List[Row] = List()

        while (resultSet.next())
        {

            val innerArray: Row = Row(resultSet.getString(1), resultSet.getString(2))


            standardCityList = standardCityList.::(innerArray)
        }

        println("生产标准城市数据：" + standardCityList.size)

        standardCityList
    }


    def getXmdCityInfo(connection: Connection, session: SparkSession): List[Row] =
    {
        val xmdCitySql = "SELECT a.cityId, a.name_long_en FROM hotel.xmd_city_temp a"

        val resultSet = connection.createStatement().executeQuery(xmdCitySql)

        var standardCityList: List[Row] = List()

        while (resultSet.next())
        {

            val innerArray: Row = Row(resultSet.getString(1), resultSet.getString(2))


            standardCityList = standardCityList.::(innerArray)
        }

        standardCityList
    }
}