package controllers

import akka.actor.Actor
import com.mongodb.casbah.MongoClient
import com.mongodb.casbah.commons.MongoDBObject
import org.joda.time.DateTime

class LoggingActor extends Actor {
  val conn = MongoClient("127.0.0.1", 27017)
  val db = conn("Chess-Online")
  val log = db("Logs")

  import com.mongodb.casbah.commons.conversions.scala._
  com.mongodb.casbah.commons.conversions.scala.RegisterJodaTimeConversionHelpers()

  def receive = {
    case LogMessage(x) => log.insert( MongoDBObject("timestamp" -> new DateTime(), "msg" -> x.toString))
  }
}

case class LogMessage(msg : Any)
