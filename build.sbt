name := "BigData&Scala"

version := "1.0"

scalaVersion := "2.11.8"


libraryDependencies += "org.scalatest" % "scalatest-funsuite_2.11" % "3.0.0-SNAP13"

libraryDependencies += "org.apache.kafka" % "kafka_2.11" % "0.10.2.1"

libraryDependencies += "org.apache.kafka" % "kafka-clients" % "0.10.2.1"

libraryDependencies += "mysql" % "mysql-connector-java" % "6.0.6"

libraryDependencies += "org.apache.spark" % "spark-streaming-kafka-0-10_2.11" % "2.1.0"


resolvers += Resolver.mavenLocal
//spark相关依赖
libraryDependencies += "org.apache.spark" % "spark-core_2.11" % "2.1.0"

libraryDependencies += "org.apache.spark" % "spark-streaming_2.11" % "2.1.0"

libraryDependencies += "org.apache.spark" % "spark-sql_2.11" % "2.1.0"

libraryDependencies += "org.apache.spark" % "spark-mllib_2.11" % "2.1.0"

libraryDependencies += "org.jsoup" % "jsoup" % "1.10.1"

libraryDependencies += "redis.clients" % "jedis" % "2.9.0"

libraryDependencies += "com.alibaba" % "fastjson" % "1.2.35"

libraryDependencies += "org.json4s" % "json4s-jackson_2.11" % "3.5.2"