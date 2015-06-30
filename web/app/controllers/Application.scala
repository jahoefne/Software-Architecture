package controllers

import _root_.java.util
import _root_.java.util.{UUID, Comparator}

import akka.actor.ActorRef
import com.escalatesoft.subcut.inject.{BindingModule, Injectable}
import controller.{IPlugin, GameController}
import persistence._
import play.api.libs.json.JsValue
import play.api.mvc._
import play.api.Play.current
import model._
import play.libs.Json
import plugin.FrenchRevolutionPlugin
import securesocial.core._
import scala.util.Random
import scala.concurrent.ExecutionContext.Implicits.global

object ShortUUID{ def uuid = (Random.alphanumeric take  8).mkString }



class Application(implicit val bindingModule: BindingModule) extends securesocial.core.SecureSocial[User] with Injectable {

  /** Injected Dependencies **/
  override implicit val env:RuntimeEnvironment[User] = inject [RuntimeEnvironment[User]]
  val loggingActor = inject [ActorRef]
  val gameDB = inject [IGameDB]

  /** Landing Page */
  def index = UserAwareAction{ implicit request =>
    loggingActor ! LogMessage("Accessing Index", Some(request.remoteAddress))
    Ok(views.html.index(request.user))
  }

  /** Create a new game instance */
  def newGame =  Action { implicit request =>
    loggingActor ! LogMessage("Creating new Game", Some(request.remoteAddress))
    val gameUUID = UUID.randomUUID.toString
    val playerId =  ShortUUID.uuid

    var plugins = new util.ArrayList[IPlugin]()
    plugins.add(new FrenchRevolutionPlugin())
    gameDB.saveGame( new GameController(gameUUID, playerId, plugins))
    Redirect(routes.Application.game(gameUUID))
  }

  /** Delete existing game **/
  def deleteGame(uuid: String) = SecuredAction { implicit request =>
    loggingActor ! LogMessage("Deleting Game", Some(request.remoteAddress))
    gameDB.deleteGameWithUUID(uuid)
    Ok
  }

  /** Access existing game instance
    * Check if the connection comes from a registered user, if so use
    * it's uuid as playerId, otherwise use a random id */
  def game(uuid: String) = UserAwareAction {
    implicit request =>
      loggingActor ! LogMessage("Accessing Game with id: "+uuid, Some(request.remoteAddress))
      gameDB.doeGameExistWithUUID(uuid) match {
        case true =>
          val playerId = request.user match {
            case Some(user) => user.uuid
            case None => ShortUUID.uuid
          }
          Ok(views.html.game( uuid, playerId, request.user))
        case false =>
          Ok(views.html.error("The requested game does not exist, please create a:"))
      }
  }

  /** Create websocket for game: uuid */
  def socket (uuid: String, playerID: String) = WebSocket.acceptWithActor[JsValue, JsValue] {
    request => out =>
      ChessWebSocketActor.props(out = out, logActor = loggingActor, playerID = playerID , gameID = uuid, gameDB)
  }

  /** Return a list of all game instances */
  def gameList =  SecuredAction { implicit request =>
    loggingActor ! LogMessage("Listing Games of user "+request.user.uuid, Some(request.remoteAddress))
    Ok(views.html.gameList(null, Some(request.user)))
  }

  /** Render Login Container */
  def login = UserAwareAction { implicit request =>
    loggingActor ! LogMessage("Login "+request.user.toString)
    Ok(views.html.loginContainer(request.user))
  }
}