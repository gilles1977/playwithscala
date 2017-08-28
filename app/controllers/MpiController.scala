package controllers

import javax.inject.Inject

import play.api.mvc._
import models.EnrollmentRequest
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
}
