package domain

import java.time.Instant

import domain.SandboxData.Department

abstract class Member {
  def userCode: String

  def userSource: String

  def department: Department

  def givenName: String

  def familyName: String

  def warwickPrimary: Boolean

  def mail: String

  def warwickTargetGroup: String

  def universityId: String
}

case class BasicMember(userCode: String,
  userSource: String,
  department: Department,
  givenName: String,
  familyName: String,
  warwickPrimary: Boolean,
  mail: String,
  warwickTargetGroup: String,
  universityId: String) extends Member

case class MemberAuthorityResponse(userCode: String,
  userSource: String,
  department: Department,
  givenName: String,
  familyName: String,
  warwickPrimary: Boolean,
  mail: String,
  warwickTargetGroup: String,
  universityId: String,
  ssc: String,
  pgt: String,
  fedMember: Boolean,
  athens: Boolean,
  lastPasswordChange: Instant,
  memberEduTypeAffiliation: MemberEduTypeAffiliation,
  ipAddress: String,
  itsClass: WarwickItsClass,
  targetGroup: String,
  title: String // todo; do students have a title?
) extends Member

sealed abstract class MemberEduTypeAffiliation(val value: String)

object MemberEduTypeAffiliation {

  case object Faculty extends MemberEduTypeAffiliation("faculty")

  case object Student extends MemberEduTypeAffiliation("student")

  case object Staff extends MemberEduTypeAffiliation("staff")

  case object Alumni extends MemberEduTypeAffiliation("alum")

  case object Member extends MemberEduTypeAffiliation("member")

  case object Affiliate extends MemberEduTypeAffiliation("affiliate")

  case object LibraryWalkIn extends MemberEduTypeAffiliation("library-walk-in")

}

sealed abstract class WarwickItsClass(val value: String)

object WarwickItsClass {

  case object Staff extends WarwickItsClass("Staff")

  case object UG extends WarwickItsClass("UG")

  case object PGT extends WarwickItsClass("PG(T)")

  case object PGR extends WarwickItsClass("PG(R)")

  case object Alumni extends WarwickItsClass("Alumni")

  case object Applicant extends WarwickItsClass("Applicant")

}

object AttributeConverter {
  def toAttributes(r: MemberAuthorityResponse): Map[String, String] = {
    return Map[String, String](
      "mail" -> r.mail,
      "urn:websignon:ipaddress" -> r.ipAddress,
      "urn:websignon:passwordlastchanged" -> r.lastPasswordChange.toString, // Must be iso 8601!
      "urn:websignon:ssc" -> r.ssc,
      "warwicktargetgroup" -> r.targetGroup,
      "warwickitsclass" -> r.itsClass.value,
      "mail" -> r.mail,
      "urn:websignon:loggedin" -> "true",
      "ou" -> r.department.name,
      "deptshort" -> r.department.name,
      "urn:websignon:usersource" -> r.userSource,
      "cn" -> r.userCode,
      "warwickuniid" -> r.universityId,
      "warwickdeptcode" -> r.department.code,
      "givenName" -> r.givenName,
      "sn" -> r.familyName,
      "dn" -> s"CN=${r.userCode},OU=Staff,OU=CU,OU=WARWICK,DC=ads,DC=warwick,DC=ac,DC=uk", // todo: OU
      "title" -> r.title,
      "passwordexpired" -> "FALSE"
    )
  }
}