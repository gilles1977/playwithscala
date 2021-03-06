package api
import javax.inject._

import controllers.{ApiController, MpiController}
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

class ApiRouter @Inject() (controller : ApiController, mpi: MpiController) extends SimpleRouter{
  override def routes: Routes = {
    case GET(p"/product/${long(ean)}") => controller.product(ean)
    case GET(p"/products") => controller.products
    case POST(p"/enrollment") => mpi.verifyEnrollment
    case POST(p"/mastercard/${pan}") => mpi.demoMastercard(pan)
  }
}
