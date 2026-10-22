object CollectionOperations {
  def squareElements(numbers: List[Int]): List[Int] = {
    numbers.map(n => n * n)
  }
  def findIntersection(set1: Set[Char], set2: Set[Char]): Set[Char] = {
    set1.intersect(set2)
  }

  def main(args: Array[String]): Unit = {
    val primes = List(2, 3, 5, 7, 11)
    println("Prime numbers: " + primes)
    val squares = squareElements(primes)
    println("Squares of prime numbers: " + squares)

    val text = "hello world"
    val charSet = text.toSet
    println("Unique characters: " + charSet)
    val anotherSet = Set('h', 'e', 'a', 'b', 'o')
    val intersection = findIntersection(charSet, anotherSet)
    println("Intersection of sets: " + intersection)
    val details = (101, "Scala", true)

    println("Tuple elements:")
    println("Integer: " + details._1)
    println("String: " + details._2)
    println("Boolean: " + details._3)
  }
}
