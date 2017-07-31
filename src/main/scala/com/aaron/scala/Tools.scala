package com.aaron.scala

import java.io.{FileOutputStream, OutputStream}

import com.aaron.AreaData
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.serializer.SerializerFeature
import org.apache.commons.pool2.impl.GenericObjectPoolConfig
import redis.clients.jedis.{Jedis, JedisPool}

import scala.io.Source


/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-06-14
  */
object Tools
{

    val dataPath: String = "spark-warehouse/mapping.txt"


    def main(args: Array[String]): Unit =
    {
        //parseAreaData()
        //parseOverseaAreaData()
        //save2Redis()
        convertText2Sql()
    }


    def convertText22Sql(): Unit =
    {
        val sqlArray: StringBuilder = new StringBuilder
        Source.fromFile(dataPath, "UTF-8").getLines().map(_.split("\t")) foreach (line =>
        {
            sqlArray.append("MERGE INTO HTL_DELIVERY.T_MERCHANT_DISTRIBUTOR t USING (SELECT '" + line(0) + "' AS merchantCode, '" + line(1) + "' AS distributorCode FROM dual) d ON (t.MERCHANTCODE = d.merchantCode AND t.DISTRIBUTORCODE = d.distributorCode) WHEN NOT MATCHED THEN INSERT (t.MERCHANTCODE,t.DISTRIBUTORCODE,t.DISTRIBUTORNAME,t.CHANNELCODE,t.SHOPNAME,t.CREATETIME) VALUES ('" + line(0) + "','" + line(1) + "','" + line(2) + "','hangban','" + line(3) + "',sysdate);").append("\n")
        })


        save2File(sqlArray)
    }


    def convertText2Sql(): Unit =
    {
        val sqlArray: StringBuilder = new StringBuilder
        Source.fromFile(dataPath, "UTF-8").getLines().map(_.split("\t")) foreach (line =>
        {
            sqlArray.append("MERGE INTO HTL_PTR.T_HUB_MAP_HOTEL t USING (SELECT " + line(0) + " AS hotelId FROM dual) d ON (t.HOTELID = d.hotelId and t.channelCode = 'hub_xmd') WHEN MATCHED THEN UPDATE SET t.PARTNERHOTELID = " + line(1) + ", t.PARTNERHOTELNAME='" + line(2) + "', t.PARTNERCITYCODE='" + line(3) + "', t.PARTNERCITYNAME='" + line(4) + "', t.PARTNERADDR='" + line(5) + "';").append("\n")
        })


        save2File(sqlArray)
    }


    def save2File(sqlArray: StringBuilder): Unit =
    {
        val destFile: String = "spark-warehouse/target.sql"

        val stream: OutputStream = new FileOutputStream(destFile)

        try
        {
            stream.write(sqlArray.toString().getBytes("UTF-8"))
        }
        catch
        {
            case e: Throwable => e.printStackTrace()
        }
        finally
        {
            stream.close()
        }
    }


    def map(): Unit =
    {
        val sqlArray: StringBuilder = new StringBuilder
        Source.fromFile(dataPath, "UTF-8").getLines().foreach(line =>
        {
            sqlArray.append("'").append(line).append("',")
        })

        sqlArray.setLength(sqlArray.length - 1)
        save2File(sqlArray)
    }


    var province: String = ""
    var provinceCode: String = ""
    var city: String = ""
    var cityCode: String = ""

    var areaDataList: List[AreaData] = List()


    def parseAreaData(): Unit =
    {
        Source.fromFile(dataPath, "UTF-8").getLines().map(_.split("\t")) foreach (line =>
        {
            //省份
            if (line.length == 2)
            {
                provinceCode = line(0)
                province = line(1)
            }

            //城市
            if (line.length == 4)
            {
                cityCode = line(2)
                city = line(3)
            }

            //区域
            if (line.length == 6)
            {
                val areaData: AreaData = new AreaData("China", "中国", provinceCode, province, cityCode, city, line(4), line(5))
                areaDataList = areaDataList :+ areaData
            }
        })

        save2Redis(areaDataList)
    }


    def parseOverseaAreaData(): Unit =
    {
        Source.fromFile("spark-warehouse/oversea.txt", "UTF-8").getLines().map(_.split("\t")) foreach (line =>
        {
            val areaData: AreaData = new AreaData(line(6), line(5), "", "", line(0), line(1), "", "")
            areaDataList = areaDataList :+ areaData
        })

        save2Redis(areaDataList)
    }


    /**
      * redis数据格式保存为hash格式
      *
      * 国内：
      *     key => cn+":"+省份code
      *     field => 城市code
      *     value => 所有信息的json格式，包括国家城市地区的编码和名称
      * 海外：
      *     key => os+":"+国家英文名称
      *     field => 城市code
      *     value => 所有信息的json格式，包括国家城市地区的编码和名称
      *
      * @param areaDataList List：所有地区信息组合的list
      */
    def save2Redis(areaDataList: List[AreaData]): Unit =
    {


        val pool: JedisPool = new JedisPool(new GenericObjectPoolConfig(), "172.16.120.11", 6379, 10000, "fcpwd1305")

        val jedis: Jedis = pool.getResource
        jedis.select(2)

        val pipelined = jedis.pipelined()

        val stringBuilder : StringBuilder = new StringBuilder()
        areaDataList.foreach((areaData: AreaData) =>
        {
            val data = JSON.toJSONString(areaData, SerializerFeature.WriteNullBooleanAsFalse)
            stringBuilder.append(data).append("\n")
            pipelined.hset("cn:" + areaData.getProvinceCode, areaData.getCityCode, data)
        })

        pipelined.sync()
        jedis.close()
        save2File(stringBuilder)

    }


}

