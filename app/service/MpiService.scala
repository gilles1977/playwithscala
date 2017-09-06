package service

import javax.inject.Inject

import models._

class MpiService @Inject() (schemeService: SchemeService) {

  def verifyEnrollment(request: EnrollmentRequest): EnrollmentResponse = {
    request match {
      case EnrollmentRequest(_, "1.0.2", _, _, _, "visa") => EnrollmentResponse(schemeService.getVeResFromVisa(request.veReq))
      case EnrollmentRequest(_, "1.0.2", _, _, _, "mastercard") => EnrollmentResponse(schemeService.getVeResFromMasterCard(request.veReq))
      case _ => EnrollmentResponse(veRes = VeRes(request.id, request.version, CH = CH("U", "12345"), "https://acsurl.com/auth", "ThreeDSecure"))
    }
  }

}
