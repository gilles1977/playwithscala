package controllers

import javax.inject.Inject

import play.api.mvc._
import models.{CH, EnrollmentRequest, VeRes}
import play.api.libs.json.Json
import service.MpiService

class MpiController @Inject()(cc: ControllerComponents, service: MpiService) extends AbstractController(cc) {

  def verifyEnrollment = Action {
    implicit request =>
      request.body.asJson.map(json => {
        Json.fromJson[EnrollmentRequest](json).map(enrollmentRequest =>
          Ok(service.verifyEnrollment(enrollmentRequest).veRes.toXml)
        ).getOrElse(BadRequest("Error in EnrollmentRequest JSON format"))
      }
    ).getOrElse(BadRequest("Expecting JSON data"))
  }

  def demoMastercard(pan: String) = Action {
    implicit request =>
      Ok(VeRes("66666", "1.0.2", CH = CH("Y", "66666"), "https://mastercard.com/auth", "ThreeDSecure").toXml)
  }
}
