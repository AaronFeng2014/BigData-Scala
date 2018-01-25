package com.aaron.scala.file.excel

import java.io.{File, FileInputStream, InputStream}

import com.aaron.bigdata.DataBaseHelper
import com.aaron.scala.collections.HotelInfo
import org.apache.poi.ss.usermodel.{Row, Workbook, WorkbookFactory}

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2018/1/24
  */
object FileParse
{

    def main(args: Array[String]): Unit =
    {
        excelParse("spark-warehouse/new_hotel3.xlsx")

        //(0 to 20).filter(_ % 5 == 0).foreach(println)
    }


    def excelParse(excelPath: String): Unit =
    {
        List("spark-warehouse/new_hotel1.xlsx", "spark-warehouse/new_hotel3.xlsx", "spark-warehouse/new_hotel4.xlsx").foreach(name =>
        {

            var hotelList: List[HotelInfo] = List()
            var failList: List[Row] = List()


            val sqlBuilder = new StringBuilder()
            val inputStream: InputStream = new FileInputStream(new File(name))

            val workBook: Workbook = WorkbookFactory.create(inputStream)


            val sheet = workBook.getSheetAt(0)

            val blank = Row.MissingCellPolicy.CREATE_NULL_AS_BLANK
            (0 to sheet.getLastRowNum).filter(index =>
            {

                val row = sheet.getRow(index)

                if (row.getLastCellNum != 49)
                {

                }
                row.getLastCellNum == 49

            }).foreach(index =>
            {
                val row = sheet.getRow(index)

                var numberOfRoom = 0
                try
                {
                    numberOfRoom = row.getCell(10, blank).getNumericCellValue.toInt
                }
                catch
                {
                    case _: Throwable => numberOfRoom = 0
                }

                var latitude = 0D
                try
                {
                    latitude = row.getCell(47, blank).getNumericCellValue
                }
                catch
                {
                    case _: Throwable => latitude = 0
                }

                var longitude = 0D
                try
                {
                    longitude = row.getCell(48, blank).getNumericCellValue
                }
                catch
                {
                    case _: Throwable => longitude = 0
                }


                try
                {
                    val hotelInfo = HotelInfo(row.getCell(0, blank).getNumericCellValue.toInt, row.getCell(1, blank).getStringCellValue, row.getCell(2, blank).getNumericCellValue, numberOfRoom, row.getCell(42, blank).getStringCellValue, row.getCell(45, blank).getStringCellValue, row.getCell(39, blank).getStringCellValue, row.getCell(40, blank).getStringCellValue, row.getCell(41, blank).getStringCellValue, latitude, longitude)
                    hotelList = hotelList.::(hotelInfo)


                    sqlBuilder.append("INSERT INTO hotelsupplier.ezeego1_hotel (hotelId, hotelName, startRate, roomNumber, cityCode, countryCode, address, location, phone, latitude, longitude) VALUES (")
                    sqlBuilder.append(row.getCell(0, blank).getNumericCellValue.toInt).append(",\"").append(row.getCell(1).getStringCellValue).append("\",").append(row.getCell(2).getNumericCellValue).append(",").append(numberOfRoom)
                    sqlBuilder.append(",\"").append(row.getCell(42).getStringCellValue).append("\",\"").append(row.getCell(45).getStringCellValue).append("\",\"").append(row.getCell(39).getStringCellValue).append("\",\"").append(row.getCell(40).getStringCellValue).append("\",\"").append(row.getCell(41).getStringCellValue)
                    sqlBuilder.append("\",").append(latitude).append(",").append(longitude).append(")  ON DUPLICATE KEY UPDATE hotelName = values(hotelName), startRate = values(startRate), roomNumber = values(roomNumber), cityCode = values(cityCode), countryCode = values(countryCode), address = values(address), location = values(location), phone = values(phone), latitude = values(latitude), longitude = values(longitude), lastModifyTime = now();\n")
                }
                catch
                {
                    case _: Exception => failList = failList.::(row)
                }


            })

            println("处理成功：" + hotelList.size)
            println("处理失败：" + failList.size)

            write2DB(hotelList)

        })

        //FileUtils.write2File(sqlBuilder, "target.sql", false)


    }


    val sqlTemplate = "INSERT INTO hotelsupplier.ezeego1_hotel (hotelId, hotelName, startRate, roomNumber, cityCode, countryCode, address, location, phone, latitude, longitude) VALUES (?,?,?,?,?,?,?,?,?,?,?)  ON DUPLICATE KEY UPDATE hotelName = values(hotelName), startRate = values(startRate), roomNumber = values(roomNumber), cityCode = values(cityCode), countryCode = values(countryCode), address = values(address), location = values(location), phone = values(phone), latitude = values(latitude), longitude = values(longitude), lastModifyTime = now()"


    def write2DB(hotelList: List[HotelInfo]): Unit =
    {
        val start = System.currentTimeMillis()
        val connection = DataBaseHelper.getConnection("jdbc:mysql://192.168.1.122:3306/hotelsupplier", "hotel", "hot4el")

        connection.setAutoCommit(false)
        val prepareStatement = connection.prepareStatement(sqlTemplate)

        hotelList.zipWithIndex.foreach(hotelWithIndex =>
        {
            val hotelInfo = hotelWithIndex._1

            prepareStatement.setInt(1, hotelInfo.hotelId)
            prepareStatement.setString(2, hotelInfo.hotelName)
            prepareStatement.setDouble(3, hotelInfo.startRate)
            prepareStatement.setDouble(4, hotelInfo.numberOfRoom)
            prepareStatement.setString(5, hotelInfo.cityCode)
            prepareStatement.setString(6, hotelInfo.countryCode)
            prepareStatement.setString(7, hotelInfo.address)
            prepareStatement.setString(8, hotelInfo.location)
            prepareStatement.setString(9, hotelInfo.phone)
            prepareStatement.setDouble(10, hotelInfo.latitude)
            prepareStatement.setDouble(11, hotelInfo.longitude)

            prepareStatement.addBatch()
        }
        )

        prepareStatement.executeBatch()
        connection.commit()

        connection.close()

        println("插入数据库耗时：" + (System.currentTimeMillis() - start))
    }

}