package service

import javax.inject.Inject

import models._
import play.api.libs.ws._

import scala.concurrent.{Await, ExecutionContext}
import scala.concurrent.duration._

class SchemeService @Inject() (ws: WSClient, ec: ExecutionContext){
  def getVeResFromMasterCard(veReq: VeReq): VeRes = {
    implicit val executionContext: ExecutionContext = ec
    Await.result(ws.url("http://localhost:9000/api/mastercard/" + veReq.pan)
      .withHttpHeaders("Accept" -> "application/xml")
      .withRequestTimeout(100 seconds)
      .post(veReq.toXml)
      .map(response => response.xml)
      .map(result => VeRes.fromXml(result.head)), 10 seconds
    )
  }

  def getVeResFromVisa(veReq: VeReq): VeRes = {
    VeRes(veReq.id, veReq.version, CH = CH("Y", "12345"), "https://acsurl.com/auth", "ThreeDSecure")
  }
}
