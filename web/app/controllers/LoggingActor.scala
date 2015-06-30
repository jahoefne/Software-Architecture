package controllers

import akka.actor.Actor
import com.mongodb.casbah.WriteConcern
import com.mongodb.casbah.MongoClient
import com.mongodb.casbah.commons.MongoDBObject
import org.joda.time.DateTime
import play.mvc.Http
import play.mvc.Http.Request



class LoggingActor extends Actor {
  val conn = MongoClient("127.0.0.1", 27017)
  val db = conn("Chess-Online")
  val log = db("Logs")

  def receive = {

    case LogMessage(msg, ip) =>
      println("Log")
      log.insert(
        MongoDBObject(
        "timestamp" -> new DateTime().toString(),
        "msg" -> msg.toString,
        "FromIp" -> ip.getOrElse("Unknown Ip")
        )
      )
  }
}

case class LogMessage(msg: Any, ip: Option[String] = None)
