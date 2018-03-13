package com.aaron.scala.web

import akka.http.scaladsl.model.{HttpEntity, MediaTypes}
import akka.http.scaladsl.server.Route
import com.aaron.scala.web.controller.UserController
import io.swagger.jaxrs.Reader
import io.swagger.jaxrs.config.DefaultReaderConfig
import io.swagger.models.auth.{ApiKeyAuthDefinition, In, SecuritySchemeDefinition}
import io.swagger.models.{Info, Scheme, Swagger}
import io.swagger.util.Json

import scala.collection.JavaConverters._

/**
  * @description 一句话描述该文件的用途
  * @author FengHaixin
  * @date 2018-03-09
  */
object ApplicationContextRouter extends UserController
{
    val unwantedDefinitions: Seq[String] = Seq.empty

    val route: Route =

    //这里设置了请求路径的前缀
        pathPrefix("akkatest")
        {

            //多个路由组合起来
            queryAllUsers() ~ queryUserById() ~ addUser() ~ path("swagger.json")
            {
                get
                {
                    complete(HttpEntity(MediaTypes.`application/json`, {

                        val info = new Info().version("1.0.0").description("基于swagger自动生成api文档").title("api文档")
                        val swagger = new Swagger().basePath("/akkatest/").scheme(Scheme.HTTP).info(info).host("127.0.0.1:9999")
                        swagger.setSecurityDefinitions(Map[String, SecuritySchemeDefinition]("apiKey" → new ApiKeyAuthDefinition("Authorization", In.HEADER)).asJava)
                        swagger.setVendorExtensions(Map[String, Object]().asJava)

                        new Reader(swagger, new DefaultReaderConfig()).read(classOf[UserController])

                        swagger.setDefinitions(swagger.getDefinitions.asScala.filterKeys(definitionName => !unwantedDefinitions.contains(definitionName)).asJava)

                        Json.pretty().writeValueAsString(swagger)
                    }))

                }
            } ~ path("swagger")
            {
                getFromResource("swagger/index.html")
            } ~
                    getFromResourceDirectory("swagger")
        }


    def main(args: Array[String]): Unit =
    {
        import spray.json._
        println(List(1, 3, 4, 32).toJson)
    }
}
