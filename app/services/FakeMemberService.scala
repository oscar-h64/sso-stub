package services

import com.google.inject.{ImplementedBy, Singleton}
import domain.{Gender, Member, SandboxData}
import domain.SandboxData.Department

@ImplementedBy(classOf[FakeMemberServiceImpl])
trait FakeMemberService {
  def getStaff: Seq[Member]

  def getStudentsFor(dept: Department): Seq[Member]
}

@Singleton
class FakeMemberServiceImpl extends FakeMemberService {
  override def getStaff: Seq[Member] = {
    val depts = SandboxData.Departments.values
    val mappedDepts: Iterable[Seq[(Department, Member)]] = depts.map(d => {
      val range: Seq[Int] = d.staffStartId to d.staffEndId
      range.map(ri => {
        val name = SandboxData.randomName(ri.longValue(), if (ri % 2 == 0) Gender.Male else Gender.Female)
        val userCode = "u" + ri
        (d, new domain.BasicMember(
          userCode,
          "WarwickADS",
          d,
          givenName = name.givenName,
          familyName = name.familyName,
          warwickPrimary = true,
          mail = "sso-stub-" + userCode + "@tempinbox.com",
          warwickTargetGroup = "University staff",
          universityId = ri.toString
        ))
      })
    })
    mappedDepts.flatten.map(t => t._2).toSeq
  }

  // TODO: iterate routes etc
  override def getStudentsFor(dept: Department): Seq[Member] = ???
}
