name := "BigData&Scala"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies += "org.scalatest" % "scalatest-funsuite_2.11" % "3.0.0-SNAP13"

//libraryDependencies += "org.apache.kafka" % "kafka_2.11" % "0.10.2.1"

libraryDependencies += "org.apache.kafka" % "kafka-clients" % "0.10.2.1"

libraryDependencies += "mysql" % "mysql-connector-java" % "6.0.6"

libraryDependencies += "org.apache.spark" % "spark-streaming-kafka_2.10" % "1.6.3"


//spark相关依赖
libraryDependencies += "org.apache.spark" % "spark-core_2.10" % "2.1.0"

libraryDependencies += "org.apache.spark" % "spark-streaming_2.10" % "2.1.0"

libraryDependencies += "org.apache.spark" % "spark-sql_2.10" % "2.1.0"

libraryDependencies += "org.apache.spark" % "spark-mllib_2.10" % "2.1.0"