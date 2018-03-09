name := "BigData&Scala"

version := "1.0.0"

scalaVersion := "2.11.8"

resolvers += Resolver.mavenLocal

libraryDependencies += "org.scalatest" % "scalatest-funsuite_2.11" % "3.0.0-SNAP13"

libraryDependencies += "org.apache.kafka" % "kafka_2.11" % "0.11.0.1"

libraryDependencies += "org.apache.kafka" % "kafka-clients" % "0.11.0.1"

libraryDependencies += "mysql" % "mysql-connector-java" % "6.0.6"

libraryDependencies += "org.apache.spark" % "spark-streaming-kafka-0-10_2.11" % "2.2.0"

//spark相关依赖
/*libraryDependencies += "org.apache.spark" % "spark-core_2.11" % "2.2.0"

libraryDependencies += "org.apache.spark" % "spark-streaming_2.11" % "2.2.0"

libraryDependencies += "org.apache.spark" % "spark-sql_2.11" % "2.2.0"

libraryDependencies += "org.apache.spark" % "spark-mllib_2.11" % "2.2.0"*/

libraryDependencies += "org.jsoup" % "jsoup" % "1.10.1"

libraryDependencies += "redis.clients" % "jedis" % "2.9.0"

libraryDependencies += "com.alibaba" % "fastjson" % "1.2.35"

//libraryDependencies += "org.json4s" % "json4s-jackson_2.11" % "3.5.3"

libraryDependencies += "com.fasterxml.jackson.core" % "jackson-core" % "2.9.1"

//添加hbase依赖
libraryDependencies += "org.apache.hbase" % "hbase-client" % "1.2.6"

//中文分词
libraryDependencies += "org.ansj" % "ansj_seg" % "5.1.3"

libraryDependencies += "org.apache.poi" % "poi" % "3.17"

libraryDependencies += "org.apache.poi" % "poi-ooxml" % "3.17"


//操作数据库的一个框架
libraryDependencies += "io.getquill" %% "quill-core" % "2.3.2"
libraryDependencies += "io.getquill" %% "quill-jdbc" % "2.3.2"
libraryDependencies += "io.getquill" %% "quill-sql" % "2.3.2"
libraryDependencies += "io.getquill" %% "quill-async" % "2.3.2"
libraryDependencies += "io.getquill" %% "quill-async-mysql" % "2.3.2"


libraryDependencies += "com.typesafe.akka" %% "akka-http" % "10.0.11"
libraryDependencies += "com.typesafe.akka" %% "akka-http-spray-json" % "10.0.11"

libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.5.11"
libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.5.11"
libraryDependencies += "ch.megard" %% "akka-http-cors" % "0.2.2"

//logback日志组件
libraryDependencies += "ch.qos.logback" % "logback-core" % "1.2.3"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"