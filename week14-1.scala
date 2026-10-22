
 //> using scala 2.13.16
 //> using dep "org.scalanlp::breeze:2.1.0"

import breeze.linalg._
import java.nio.file.{Files, Paths}
import java.nio.charset.StandardCharsets

object FruitRevenue {
  def main(args: Array[String]): Unit = {

    val fruits = Seq("Apple", "Banana", "Mango", "Apple", "Banana", "Mango")
    val prices = DenseVector(100.0, 40.0, 80.0, 110.0, 45.0, 90.0)
    val quantities = DenseVector(10.0, 20.0, 15.0, 5.0, 10.0, 8.0)

    // Breeze: element-wise multiplication
    val revenues = prices *:* quantities

    // Calculate total revenue by fruit
    val totals = fruits.indices
      .groupBy(i => fruits(i))
      .map { case (fruit, indices) =>
        fruit -> indices.map(i => revenues(i)).foldLeft(0.0)(_ + _)
      }
      .toSeq
      .sortBy(_._1)

    println("Total Revenue by Fruit")
    totals.foreach { case (fruit, revenue) =>
      println(f"$fruit%-10s ₹$revenue%.2f")
    }

    // Create an interactive Plotly bar chart in HTML
    val xValues = totals.map(_._1).map(f => s""""$f"""").mkString("[", ",", "]")
    val yValues = totals.map(_._2).mkString("[", ",", "]")

    val html =
      s"""<!DOCTYPE html>
         |<html>
         |<head>
         |<meta charset="UTF-8">
         |<script src="https://cdn.plot.ly/plotly-2.35.2.min.js"></script>
         |</head>
         |<body>
         |<div id="chart" style="width:900px;height:550px;"></div>
         |<script>
         |Plotly.newPlot('chart', [{
         |  x: $xValues,
         |  y: $yValues,
         |  type: 'bar',
         |  text: $yValues.map(v => '₹' + v),
         |  textposition: 'auto'
         |}], {
         |  title: 'Total Revenue by Fruit',
         |  xaxis: {title: 'Fruit Type'},
         |  yaxis: {title: 'Total Revenue (₹)'},
         |  template: 'plotly_white'
         |});
         |</script>
         |</body>
         |</html>
         |""".stripMargin

    Files.write(
      Paths.get("fruit_revenue.html"),
      html.getBytes(StandardCharsets.UTF_8)
    )

    println("\nChart saved as fruit_revenue.html")
  }
}
