package system

import controllers.RequestContext
import javax.inject.Inject
import play.api.Configuration
import play.api.mvc.RequestHeader
import services.{FakeMemberService, NavigationService}

trait ImplicitRequestContext {

  @Inject
  private[this] var navigationService: NavigationService = _

  @Inject
  private[this] var csrfPageHelperFactory: CSRFPageHelperFactory = _

  @Inject
  private[this] var configuration: Configuration = _

  implicit def requestContext(implicit request: RequestHeader): RequestContext =
        RequestContext.anonymous(request, navigationService.getNavigation, csrfPageHelperFactory, configuration)


  implicit val requestContextBuilder: RequestHeader => RequestContext =
    request => requestContext(request)

}