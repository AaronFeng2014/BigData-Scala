package com.aaron.scala

import java.io.{FileOutputStream, OutputStream}

import scala.io.Source


/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2017-06-14
  */
object Tools
{

    val dataPath: String = "spark-warehouse/hangban.txt"


    def main(args: Array[String]): Unit =
    {
        convertText22Sql()
    }


    def convertText22Sql(): Unit =
    {
        val sqlArray: StringBuilder = new StringBuilder
        Source.fromFile(dataPath, "UTF-8").getLines().map(_.split("\t")) foreach (line =>
        {
            sqlArray.append("MERGE INTO HTL_DELIVERY.T_MERCHANT_DISTRIBUTOR t USING (SELECT '"+line(0)+"' AS merchantCode, '"+line(1)+"' AS distributorCode FROM dual) d ON (t.MERCHANTCODE = d.merchantCode AND t.DISTRIBUTORCODE = d.distributorCode) WHEN NOT MATCHED THEN INSERT (t.MERCHANTCODE,t.DISTRIBUTORCODE,t.DISTRIBUTORNAME,t.CHANNELCODE,t.SHOPNAME,t.CREATETIME) VALUES ('"+line(0)+"','"+line(1)+"','"+line(2)+"','hangban','"+line(3)+"',sysdate);").append("\n")
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

}
