package controllers

import javax.inject._

import play.api.mvc._
import models.Product
import play.api.data.Form
import play.api.data.Forms.{longNumber, mapping, nonEmptyText}
import play.api.mvc.Flash
import play.api.i18n.{I18nSupport, Messages}

@Singleton
class ProductsController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with I18nSupport {

  def list = Action {
    implicit request =>
      val products = Product.findAll
      Ok(views.html.products.list(products))
  }

  def show(ean: Long) = Action {
    implicit request =>
      Product.findByEan(ean).map(product =>
        Ok(views.html.products.details(product))
      ).getOrElse(NotFound)
  }

  def newProduct = Action {
    implicit request =>
      val form = if (request.flash.get("error").isDefined) {
        val errorForm = productForm.bind(request.flash.data)
        errorForm
      }
      else
        productForm
      Ok(views.html.products.editProduct(form))
  }

  def save = Action {
    implicit request =>
      val newProductForm = productForm.bindFromRequest()
      newProductForm.fold(
        hasErrors = { form =>
          Redirect(routes.ProductsController.newProduct()).
            flashing(Flash(form.data) + ("error" -> Messages("validation.errors")))
        },
        success = { newProduct =>
          Product.add(newProduct)
          val successMessage = "success" -> Messages("products.new.success", newProduct.name)
          Redirect(routes.ProductsController.show(newProduct.ean)).
            flashing(successMessage)
        }
      )
  }

  private val productForm: Form[Product] = Form(
    mapping(
      "ean" -> longNumber.verifying("validation.ean.duplicate", Product.findByEan(_).isEmpty),
      "name" -> nonEmptyText,
      "description" -> nonEmptyText
    )(Product.apply)(Product.unapply)
  )
}
