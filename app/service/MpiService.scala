package service

import models.{CH, EnrollmentRequest, EnrollmentResponse, VeRes}

class MpiService {
  def verifyEnrollment(request: EnrollmentRequest): EnrollmentResponse = {
    EnrollmentResponse(veRes = VeRes(request.id, request.version, CH = CH("Y", "12345"), "https://acsurl.com/auth", "ThreeDSecure"))
  }
}
