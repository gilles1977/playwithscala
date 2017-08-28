package controllers

import javax.inject._
import play.api.mvc._
import play.api.i18n.I18nSupport

@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with I18nSupport {
  def index = Action {
    Ok("Hello")
  }
}
