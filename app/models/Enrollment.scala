package models

case class EnrollmentRequest(id: String, version: String, pan: String, merchant: Merchant, browser: Browser) {
  def toXml = {
    <?xml version="1.0" encoding="UTF-8"?>
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
    <?xml version="1.0" encoding="UTF-8"?>
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

case class CH(enrolled: String, acctID: String)
