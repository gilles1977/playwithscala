package controllers

import javax.inject.Inject

import api.ApiHandler
import play.api.libs.json._
import play.api.mvc._

class ApiController @Inject()(cc: ControllerComponents, handler: ApiHandler) extends AbstractController(cc) {
  def product(ean: Long) = Action {
    implicit request =>
      handler.findByEan(ean).map(product =>
        Ok(Json.toJson(product))
      ).getOrElse(NotFound)
  }

  def products = Action {
    implicit request =>
      Ok(Json.toJson(handler.findAll))
  }
}
