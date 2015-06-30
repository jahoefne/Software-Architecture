package config

import akka.actor.{Props, ActorRef}
import com.escalatesoft.subcut.inject.NewBindingModule
import controllers.{LoggingActor, DBUserService, User}
import persistence.{IGameDB, DB4OGameDB}
import play.libs.Akka
import securesocial.core.RuntimeEnvironment
import securesocial.core.providers.{UsernamePasswordProvider, GoogleProvider}

import scala.collection.{immutable, mutable}


/**  RuntimeEnv for UserService */
object MyRuntimeEnvironment extends RuntimeEnvironment.Default[User] {
  override lazy val userService = DBUserService

  override lazy val providers = immutable.ListMap(
    include(new GoogleProvider(routes, cacheService, oauth2ClientFor(GoogleProvider.Google))),
    include(new UsernamePasswordProvider[User](userService, avatarService, viewTemplates, passwordHashers))
  )
}


/**
 * Config For Subcut Injection
 */
object DefaultConfig extends NewBindingModule({ module =>
  import module._

  bind [RuntimeEnvironment[User]] toSingleInstance  MyRuntimeEnvironment
  bind [IGameDB] toSingleInstance new DB4OGameDB
  bind [ActorRef] toSingleInstance Akka.system().actorOf(Props(new LoggingActor()))

  // controllers.Application is itself injectable, so we need the toModuleSingle form to pay the config forward
  bind [controllers.Application] toModuleSingle { implicit module => new controllers.Application }

})