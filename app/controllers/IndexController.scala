package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc.{Action, AnyContent}
import services.FakeMemberService

@Singleton
class IndexController extends BaseController {
  @Inject
  private[this] var fakeMemberService: FakeMemberService = _

  def home: Action[AnyContent] = Action { implicit request =>
    Ok(views.html.home(fakeMemberService.getStaff))
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
