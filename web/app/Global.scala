import akka.actor.{Props, ActorSystem, ActorRef}
import com.escalatesoft.subcut.inject.Injectable
import config.DefaultConfig
import controllers._
import java.lang.reflect.Constructor
import persistence.{DB4OGameDB, IGameDB, CouchGameDB}
import play.api.mvc.WithFilters
import play.filters.gzip.GzipFilter
import play.libs.Akka
import securesocial.core.RuntimeEnvironment
import securesocial.core.providers.{UsernamePasswordProvider, GoogleProvider}

import play.Application
import scala.collection.immutable.ListMap


/**
 * Global Settings used for SecureSocial
 */
object Global extends WithFilters(
  new GzipFilter(shouldGzip = (request, response) =>
    response.headers.get("Content-Type").exists(_.startsWith("text/html")) ||
      response.headers.get("Content-Type").exists(_.startsWith("text/css"))
  )
) with play.api.GlobalSettings {

  object Context extends Injectable {
    implicit val bindingModule = DefaultConfig

    val application = inject[controllers.Application]
    val applicationClass = classOf[controllers.Application]
  }

  /** Inject Dependencies in Controller Instances */
  override def getControllerInstance[A](controllerClass: Class[A]): A = {
    controllerClass match {
      case Context.applicationClass => Context.application.asInstanceOf[A]
      case _ => throw new IllegalArgumentException
    }
  }
}
