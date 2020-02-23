package domain

// Most of this is nabbed from Tabula
// we could depend on it, but the build process would take bloody ages


/** Trait for a class that can be converted to a value for storing,
  * usually a string or jinteger.
  */
trait Convertible[A >: Null <: AnyRef] {
  def value: A
}

sealed abstract class CourseType(val code: String, val level: String, val description: String, val courseCodeChars: Set[Char]) extends Convertible[String] with Product with Serializable {
  def value: String = code
}

object CourseType {
  implicit val factory: String => CourseType = { code: String => CourseType(code) }

  case object PGR extends CourseType("PG(R)", "Postgraduate", "Postgraduate (Research)", Set('R'))

  case object PGT extends CourseType("PG(T)", "Postgraduate", "Postgraduate (Taught)", Set('T', 'E'))

  case object UG extends CourseType("UG", "Undergraduate", "Undergraduate", Set('U', 'D'))

  case object Foundation extends CourseType("F", "Foundation", "Foundation course", Set('F')) // pre-UG
  case object PreSessional extends CourseType("PS", "Pre-sessional", "Pre-sessional course", Set('N')) // pre-PG

  // these are used in narrowing departmental filters via Department.filterRule
  val all = Seq(UG, PGT, PGR, Foundation, PreSessional)
  val ugCourseTypes = Seq(UG, Foundation)
  val pgCourseTypes = Seq(PGT, PGR, PreSessional)

  def apply(code: String): CourseType = code match {
    case UG.code => UG
    case PGT.code => PGT
    case PGR.code => PGR
    case Foundation.code => Foundation
    case PreSessional.code => PreSessional
    case other => throw new IllegalArgumentException("Unexpected course code: %s".format(other))
  }

  def fromCourseCode(cc: String): CourseType = {
    if (cc == null || cc.isEmpty) {
      null
    } else {
      cc.charAt(0) match {
        case c if UG.courseCodeChars.contains(c) => UG
        case c if PGT.courseCodeChars.contains(c) => PGT
        case c if PGR.courseCodeChars.contains(c) => PGR
        case c if Foundation.courseCodeChars.contains(c) => Foundation
        case c if PreSessional.courseCodeChars.contains(c) => PreSessional
        case other => throw new IllegalArgumentException("Unexpected first character of course code: %s".format(other))
      }
    }
  }
}