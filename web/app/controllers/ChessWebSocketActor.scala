package controllers

import java.awt.Point
import akka.actor.{Actor, Props, ActorRef}
import akka.contrib.pattern.DistributedPubSubExtension
import akka.contrib.pattern.DistributedPubSubMediator.{SubscribeAck, Publish, Subscribe}
import controller.GameController
import persistence.IGameDB
import play.api.Logger
import play.api.libs.json._

/** Defines the WebSocketActor + Companion */
class ChessWebSocketActor(out: ActorRef, playerID: String, gameID: String, gameDB: IGameDB) extends Actor {

  val log = Logger(this getClass() getName())
  val mediator = DistributedPubSubExtension(context.system).mediator
  mediator ! Subscribe(gameID, self)

  def gameToJson(gameController: GameController) : JsValue = Json.obj(
    "type" -> "ActiveGame",
    "uuid" -> gameController.get_id(),
    "field" -> gameController.getField.getField,
    "check" -> gameController.getCheck,
    "white" -> gameController.getWhitePlayerID,
    "whiteName" ->  "Unknown", //DBUserService.findNameByUuid(this.players._1),
    "whitePic" -> "/assets/images/white.png", //DBUserService.findPicByUuid(this.players._1).getOrElse("/assets/images/white.png").toString,
    "black" -> gameController.getBlackPlayerID, //.players._2.getOrElse("").toString,
    "blackName" ->  "Unknown",//DBUserService.findNameByUuid(this.players._2),
    "blackPic" -> "/assets/images/black.png",//DBUserService.findPicByUuid(this.players._2).getOrElse("/assets/images/black.png").toString,
    "whiteOrBlack" -> gameController.getField.getWhiteOrBlack.toInt,
    "gameOver" -> gameController.isGameOver
  )

  def receive = {
    case msg: JsValue => (msg \ "type").as[String] match {

      /**
       * Messages that do not have to be routed to other players
       **/
      case "GetGame" => out ! gameToJson(gameDB.loadGameWithUUID(gameID))

      case "PossibleMoves" =>
        val src = new Point((msg \ "x").as[Int], (msg \ "y").as[Int])
        val moves = Array[Array[Int]](Array[Int](src.x, src.y)) ++
          (for (p: Point <- gameDB.loadGameWithUUID(gameID).getPossibleMoves(src))
                yield Array[Int](p.x, p.y))
        out ! Json.obj( "type" -> "PossibleMoves", "moves" -> moves)

      case "ActiveGame" =>  out ! msg

      case "chatMessage" => out ! msg //GameDB.appendChatMessage(gameID, msg)

      case "getAllChatMessages"  => out !  Json.obj("type"-> "getAllChatMessages", "msgs" -> Array[JsValue]())

      /**
       *  Mediator routed messages below
       **/
      case "Move" =>
        val src = new Point((msg \ "srcX").as[Int], (msg \ "srcY").as[Int])
        val dst = new Point((msg \ "dstX").as[Int],  (msg \ "dstY").as[Int])
        val game =  gameDB.loadGameWithUUID(gameID)
        game.move(src,dst)
        gameDB.saveGame(game)
        mediator ! Publish(gameID, gameToJson(game))

      case "WhitePlayer" =>
        val game = gameDB.loadGameWithUUID(gameID)
        game.setWhitePlayerID(playerID)
        gameDB.saveGame(game)
        mediator ! Publish(gameID, gameToJson(game))

      case "BlackPlayer" =>
        val game = gameDB.loadGameWithUUID(gameID)
        game.setBlackPlayerID(playerID)
        gameDB.saveGame(game)
        mediator ! Publish(gameID, gameToJson(game))

      case "Spectator" =>
        val game = gameDB.loadGameWithUUID(gameID)
        game.movePlayerToSpec(playerID)
        gameDB.saveGame(game)
        mediator ! Publish(gameID, gameToJson(game))

      case "chatMessageClient" =>  mediator ! Publish(gameID, msg.as[JsObject] ++ Json.obj("type" -> "chatMessage"))

      case _ => log.error("Unknown Json")
    }

    case mediatorAck: SubscribeAck => log.debug("Subscribed to messages for game instance")

    case x =>
      log.error(x.toString)
      log.error("No type supplied")
  }

  /** Socket was closed from the client */
  override def postStop() = {
    val game = gameDB.loadGameWithUUID(gameID)
    game.movePlayerToSpec(playerID)
    gameDB.saveGame(game)
    mediator ! Publish(gameID, game)
  }
}

/** WS-Companion - for props */
object ChessWebSocketActor {
  def props(out: ActorRef, playerID: String, gameID: String, gameDB: IGameDB) =
    Props(new ChessWebSocketActor(out, playerID, gameID, gameDB))
}
