package service

import models.{CH, EnrollmentRequest, EnrollmentResponse, VeRes}

class MpiService {
  def verifyEnrollment(request: EnrollmentRequest): EnrollmentResponse = {
    EnrollmentResponse(veRes = VeRes("999","1.0.2", CH = CH("Y", "12345"),"https://acsurl.com/auth", "ThreeDSecure"))
  }
}
