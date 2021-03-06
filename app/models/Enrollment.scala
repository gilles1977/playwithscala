package models

import play.api.libs.json._
import play.api.libs.functional.syntax._

case class EnrollmentRequest(id: String, version: String, pan: String, merchant: Merchant, browser: Browser, scheme: String) {
  def veReq : VeReq = {
    VeReq(id, version, pan, merchant, browser)
  }
}

case class VeReq(id: String, version: String, pan: String, merchant: Merchant, browser: Browser) {
  def toXml = {
      <ThreeDSecure>
        <Message id={id}>
          <VEReq>
            <version>{version}</version>
            <pan>{pan}</pan>
            <Merchant>
              <acqBIN>{merchant.acqBin}</acqBIN>
              <merID>{merchant.merID}</merID>
              <password>{merchant.password}</password>
            </Merchant>
            <Browser>
              <deviceCategory>{browser.deviceCategory}</deviceCategory>
              <accept>{browser.accept}</accept>
              <userAgent>{browser.userAgent}</userAgent>
            </Browser>
          </VEReq>
        </Message>
      </ThreeDSecure>
  }
}

case class EnrollmentResponse(veRes: VeRes)

case class Merchant(acqBin: String, merID: String, password: String)

case class Browser(deviceCategory: String, accept: String, userAgent: String)

case class VeRes(id: String, version: String, CH: CH, url: String, protocol: String) {
  def toXml = {
      <ThreeDSecure>
        <Message id={id}>
          <VERes>
            <version>{version}</version>
            <CH>
              <enrolled>{CH.enrolled}</enrolled>
              <acctID>{CH.acctID}</acctID>
            </CH>
            <url>{url}</url>
            <protocol>{protocol}</protocol>
          </VERes>
        </Message>
      </ThreeDSecure>
  }
}

object VeRes {
  def fromXml(node: scala.xml.Node) : VeRes = {
    val id = (node \ "Message" \ "@id").text
    val version = (node \ "Message" \ "VERes" \ "version").text
    val ch = (node \ "Message" \ "VERes" \ "CH").headOption.map(node => CH.fromXml(node)).orNull
    val url = (node \ "Message" \ "VERes" \ "url").text
    val protocol = (node \ "Message" \ "VERes" \ "protocol").text
    VeRes(id, version, ch, url, protocol)
  }
}

case class CH(enrolled: String, acctID: String)

object CH {
  def fromXml(node: scala.xml.Node) : CH = {
    val enrolled = (node \ "enrolled").text
    val acctId = (node \ "acctID").text
    CH(enrolled, acctId)
  }
}

object EnrollmentRequest {
  implicit val merchantRead : Reads[Merchant] = (
    (JsPath \ "acqBin").read[String] and
      (JsPath \ "merID").read[String] and
      (JsPath \ "password").read[String]
    )(Merchant.apply _)

  implicit val browserRead : Reads[Browser] = (
    (JsPath \ "deviceCategory").read[String] and
      (JsPath \ "accept").read[String] and
      (JsPath \ "userAgent").read[String]
    )(Browser.apply _)

  implicit val enrollmentRequestRead : Reads[EnrollmentRequest] = (
    (JsPath \ "id").read[String] and
      (JsPath \ "version").read[String] and
      (JsPath \ "pan").read[String] and
      (JsPath \ "merchant").read[Merchant] and
      (JsPath \ "browser").read[Browser] and
      (JsPath \ "scheme").read[String]
    )(EnrollmentRequest.apply _)

  implicit val implicitWrite = new Writes[EnrollmentRequest] {
    def writes(e: EnrollmentRequest): JsValue = {
      Json.obj(
        "id" -> e.id,
        "version" -> e.version,
        "pan" -> e.pan,
        "merchant" -> Json.obj(
          "acqBin" -> e.merchant.acqBin,
          "merID" -> e.merchant.merID,
          "password" -> e.merchant.password
        ),
        "browser" -> Json.obj(
          "deviceCategory" -> e.browser.deviceCategory,
          "accept" -> e.browser.accept,
          "userAgent" -> e.browser.userAgent
        )
      )
    }
  }
}