Program	
 //> using scala 3.8.4
//> using dep "io.github.pityka::saddle-core:4.0.0-M13"
//> using dep "org.typelevel::spire:0.18.0"

import org.saddle.Series
import _root_.spire.math.Rational
import java.math.{BigDecimal => JBigDecimal, MathContext}
import java.nio.file.Files
import java.nio.file.Paths

object FruitPriceAnalysis {

  def main(args: Array[String]): Unit = {
    val dates = Vector(
      "2026-10-01", "2026-10-02", "2026-10-03", "2026-10-04",
      "2026-10-05", "2026-10-06", "2026-10-07", "2026-10-08"
    )

    val fruits = Vector(
      "Apple", "Banana", "Apple", "Orange",
      "Banana", "Apple", "Orange", "Apple"
    )

    val prices = Vector(
      "120.25", "40.50", "125.75", "80.00",
      "42.25", "130.50", "85.25", "128.75"
    )

    val quantities = Vector(10, 20, 15, 12, 25, 8, 10, 18)
    val dateSeries: Series[Int, String] = Series(
      0 -> dates(0), 1 -> dates(1), 2 -> dates(2), 3 -> dates(3),
      4 -> dates(4), 5 -> dates(5), 6 -> dates(6), 7 -> dates(7)
    )

    val fruitSeries: Series[Int, String] = Series(
      0 -> fruits(0), 1 -> fruits(1), 2 -> fruits(2), 3 -> fruits(3),
      4 -> fruits(4), 5 -> fruits(5), 6 -> fruits(6), 7 -> fruits(7)
    )

    val priceSeries: Series[Int, Double] = Series(
      0 -> prices(0).toDouble, 1 -> prices(1).toDouble,
      2 -> prices(2).toDouble, 3 -> prices(3).toDouble,
      4 -> prices(4).toDouble, 5 -> prices(5).toDouble,
      6 -> prices(6).toDouble, 7 -> prices(7).toDouble
    )

    val quantitySeries: Series[Int, Int] = Series(
      0 -> quantities(0), 1 -> quantities(1),
      2 -> quantities(2), 3 -> quantities(3),
      4 -> quantities(4), 5 -> quantities(5),
      6 -> quantities(6), 7 -> quantities(7)
    )

    println("COMPLETE FRUIT DATASET")
    println("Date | Fruit | Price | Quantity")
    println("----------------------------------------")

    dates.indices.foreach { i =>
      println(
        s"${dateSeries(i)} | ${fruitSeries(i)} | " +
        s"${priceSeries(i)} | ${quantitySeries(i)}"
      )
    }

    val fruitTypes = fruits.distinct.sorted
    val mc = MathContext.DECIMAL128

    def decimal(r: Rational): String = {
      val numerator = new JBigDecimal(r.numerator.toString)
      val denominator = new JBigDecimal(r.denominator.toString)
      numerator.divide(denominator, mc).toPlainString
    }

    println("\nMEAN AND POPULATION VARIANCE")
    println("----------------------------------------")

    fruitTypes.foreach { fruit =>
      val selected = fruits.indices
        .filter(i => fruits(i) == fruit)
        .map { i =>
          val cents = new JBigDecimal(prices(i))
            .movePointRight(2)
            .longValueExact()

          Rational(BigInt(cents), BigInt(100))
        }

      val count = Rational(selected.size)
      val mean = selected.foldLeft(Rational(0))(_ + _) / count

      val variance = selected
        .map(p => (p - mean) * (p - mean))
        .foldLeft(Rational(0))(_ + _) / count

      println(s"\nFruit: $fruit")
      println(s"Mean price: ₹${decimal(mean)}")
      println(s"Population variance: ${decimal(variance)}")
    }
    val traces = fruitTypes.map { fruit =>
      val indices = fruits.indices.filter(i => fruits(i) == fruit)

      val x = indices.map(i => "\"" + dates(i) + "\"")
        .mkString("[", ",", "]")

      val y = indices.map(i => prices(i))
        .mkString("[", ",", "]")

      s"""{
         |  x: $x,
         |  y: $y,
         |  type: 'scatter',
         |  mode: 'lines+markers',
         |  name: '$fruit'
         |}""".stripMargin
    }.mkString("[", ",", "]")

    val html =
      s"""<!DOCTYPE html>
         |<html>
         |<head>
         |  <meta charset="UTF-8">
         |  <title>Fruit Price Analysis</title>
         |  <script src="https://cdn.plot.ly/plotly-2.35.2.min.js"></script>
         |</head>
         |<body>
         |  <h2>Fruit Price Trends Over Time</h2>
         |  <div id="chart" style="width:90%;height:550px;"></div>
         |  <script>
         |    const traces = $traces;
         |    const layout = {
         |      title: 'Fruit Prices Over Time',
         |      xaxis: { title: 'Date' },
         |      yaxis: { title: 'Price (₹)' },
         |      template: 'plotly_white'
         |    };
         |    Plotly.newPlot('chart', traces, layout,
         |      { responsive: true });
         |  </script>
         |</body>
         |</html>
         |""".stripMargin

    Files.writeString(Paths.get("fruit_price_trend.html"), html)

    println("\nChart generated: fruit_price_trend.html")
    println("Open the HTML file in a browser to view the chart.")
  }
}
