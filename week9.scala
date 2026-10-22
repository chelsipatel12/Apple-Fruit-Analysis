
object ArrayOperations {
  def calculateSum(arr: Array[Int]): Int = {
    arr.sum
  }
  def concatenateFruits(fruits: Array[String]): String = {
    fruits.mkString(", ")
  }

  def main(args: Array[String]): Unit = {
    val numbers = Array(2, 5, 9, 14, 20)
    println("Sum of array elements: " + calculateSum(numbers))
    val fruits = Array("Apple", "Banana", "Mango", "Orange", "Grapes")
    println("Concatenated fruits: " + concatenateFruits(fruits))
    println("Elements of the integer array:")
    for (num <- numbers) {
      println(num)
    }
  }
}
