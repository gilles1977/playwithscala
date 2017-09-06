package service

import javax.inject.Inject

import models._
import play.api.libs.ws._

import scala.concurrent.duration.Duration

class SchemeService @Inject() (ws: WSClient){
  def getVeResFromMasterCard(veReq: VeReq): VeRes = {
    val response = ws.url("")
      .withHttpHeaders("Accept" -> "application/xml")
      .withRequestTimeout(Duration(10, "s"))
      .post(veReq.toXml)

    val result = response.map(response => response.xml \ "ThreeDSecure")
    result onComplete {
      case res => VeRes.fromXml(res.get.head)
    }
    VeRes(veReq.id, veReq.version, CH = CH("Y", "12345"), "https://acsurl.com/auth", "ThreeDSecure")
  }

  def getVeResFromVisa(veReq: VeReq): VeRes = {
    VeRes(veReq.id, veReq.version, CH = CH("Y", "12345"), "https://acsurl.com/auth", "ThreeDSecure")
  }
}
