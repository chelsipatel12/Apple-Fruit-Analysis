
import org.saddle._
import vegas._

import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Paths}

object ApplePriceAnalysis {
  case class Fruit(date: String, name: String, price: Double, quantity: Int)

  def main(args: Array[String]): Unit = {

    // Sample dataset
    val data = Seq(
      Fruit("2026-10-01", "Apple", 100, 10),
      Fruit("2026-10-02", "Apple", 110, 12),
      Fruit("2026-10-03", "Apple", 120, 8),
      Fruit("2026-10-01", "Banana", 40, 20),
      Fruit("2026-10-02", "Banana", 45, 15),
      Fruit("2026-10-03", "Mango", 90, 10)
    )

    // Filter apple records
    val apples = data.filter(_.name == "Apple")

    // Store apple prices in a Saddle Series
    val prices = Series(
      Vec(apples.map(_.price).toArray),
      Index(apples.map(_.date): _*)
    )

    // Calculate average price
    val average = apples.map(_.price).sum / apples.size

    println("Apple Price Data")
    apples.foreach(a =>
      println(a.date + "  " + a.name + "  ₹" + a.price)
    )

    println(f"\nAverage Apple Price: ₹$average%.2f")

    // Prepare data for Vegas
    val chartData = apples.map(a =>
      Map("Date" -> a.date, "Price" -> a.price)
    )

    val chart = Vegas("Apple Price Trend Over Time")
      .withData(chartData)
      .encodeX("Date", Nom)
      .encodeY("Price", Quant)
      .mark(Line)

    // Save chart as HTML
    Files.write(
      Paths.get("apple_price_trend.html"),
chart.html.pageHTML().getBytes(StandardCharsets.UTF_8)    )

    println("Chart saved as apple_price_trend.html")
  }
}
