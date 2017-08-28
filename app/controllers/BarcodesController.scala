package controllers

import javax.inject._

import play.api.mvc._

@Singleton
class BarcodesController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  val ImageResolution = 144

  def barcode(ean: Long) = Action {
    val mimeType = "image/png"
    try {
      val imageData = ean13BarCode(ean, mimeType)
      Ok(imageData).as(mimeType)
    }
    catch {
      case e: IllegalArgumentException =>
        BadRequest(s"Couldn't generate barcode. Error: ${e.getMessage}")
    }
  }

  def ean13BarCode(ean: Long, mimeType: String) : Array[Byte] = {
    import java.awt.image.BufferedImage
    import java.io.ByteArrayOutputStream
    import org.krysalis.barcode4j.impl.upcean.EAN13Bean
    import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider

    val output: ByteArrayOutputStream = new ByteArrayOutputStream
    val canvas: BitmapCanvasProvider = new BitmapCanvasProvider(output, mimeType, ImageResolution, BufferedImage.TYPE_BYTE_BINARY, false, 0)
    val barcode = new EAN13Bean
    barcode.generateBarcode(canvas, String valueOf ean)
    canvas.finish

    output.toByteArray
  }
}
