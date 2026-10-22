Program	
object ListOperations {

  def main(args: Array[String]): Unit = {
    val numbers = (1 to 10).toList
    println("Original List: " + numbers)

    val evenNumbers = numbers.filter(n => n % 2 == 0)
    println("Even Numbers: " + evenNumbers)

    val multiplied = numbers.map(n => n * 3)
    println("Elements Multiplied by 3: " + multiplied)

    val tuples = numbers.flatMap(n => List((n, n * n)))
    println("Number and Square Tuples: " + tuples)

    val product = numbers.foldLeft(1)((acc, n) => acc * n)
    println("Product of All Elements: " + product)
  }
}
