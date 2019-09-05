package controllers

import play.api.Configuration
import play.api.mvc.{Flash, RequestHeader}
import services.Navigation
import system.{CSRFPageHelper, CSRFPageHelperFactory}
import warwick.core.timing.{ServerTimingFilter, TimingContext}

import scala.util.Try

case class RequestContext(
  path: String,
  navigation: Seq[Navigation],
  flash: Flash,
  csrfHelper: CSRFPageHelper,
  userAgent: Option[String],
  ipAddress: String,
  timingData: TimingContext.Data
) extends TimingContext

object RequestContext {

  def anonymous(request: RequestHeader, navigation: Seq[Navigation], csrfHelperFactory: CSRFPageHelperFactory, configuration: Configuration): RequestContext =
    RequestContext(request, navigation, csrfHelperFactory, configuration)

  def apply(request: RequestHeader, navigation: Seq[Navigation], csrfHelperFactory: CSRFPageHelperFactory, configuration: Configuration): RequestContext = {
    RequestContext(
      path = request.path,
      navigation = navigation,
      flash = Try(request.flash).getOrElse(Flash()),
      csrfHelper = transformCsrfHelper(csrfHelperFactory, request),
      userAgent = request.headers.get("User-Agent"),
      ipAddress = request.remoteAddress,
      timingData = request.attrs.get(ServerTimingFilter.TimingData).getOrElse(new TimingContext.Data)
    )
  }

  private[this] def transformCsrfHelper(helperFactory: CSRFPageHelperFactory, req: RequestHeader): CSRFPageHelper = {
    val token = play.filters.csrf.CSRF.getToken(req)

    val helper = helperFactory.getInstance(token)
    helper
  }

}
