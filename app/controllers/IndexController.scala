package controllers

import javax.inject.Singleton
import play.api.mvc.{Action, AnyContent}

@Singleton
class IndexController extends BaseController {
  def home: Action[AnyContent] = Action { implicit request =>
    Ok(views.html.home())
  }

  def redirectToPath(path: String, status: Int = MOVED_PERMANENTLY): Action[AnyContent] = Action {
    Redirect(s"/${path.replaceFirst("^/","")}", status)
  }

  def hs(shire: String, providerId: String, target: String) = Action { implicit request =>
    Ok(views.html.hs(shire, providerId, target))
  }

  // TODO: Use form case class here
  def generateAcs(shire: String, providerId: String, target: String, uid : String) = Action { implicit request =>
    Ok(<message>It was okay</message>)
  }
}
