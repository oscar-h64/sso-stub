package helpers

import play.api.libs.json.{Writes, Json => PlayJson}

object Json {
  case class JsonOperationResult(success: Boolean = false)
  implicit val writesJsonOperationResult: Writes[JsonOperationResult] = PlayJson.writes[JsonOperationResult]

  case class JsonClientError(status: String, errors: Seq[String] = Nil, success: Boolean = false)
  implicit val writesJsonClientError: Writes[JsonClientError] = PlayJson.writes[JsonClientError]

}
