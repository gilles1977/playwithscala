package api

import models.Product

class ApiHandler {
  def findAll = Product.findAll

  def findByEan(ean: Long): Option[Product] = {
    Product.findByEan(ean)
  }
}
