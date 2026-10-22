import scala.collection.mutable

object HashMapOperations {
  def mergeMaps(
      map1: mutable.HashMap[String, Int],
      map2: mutable.HashMap[String, Int]
  ): mutable.HashMap[String, Int] = {

    val merged = mutable.HashMap[String, Int]()

    merged ++= map1

    for ((name, grade) <- map2) {
      if (merged.contains(name)) {
        merged(name) = math.max(merged(name), grade)
      } else {
        merged(name) = grade
      }
    }

    merged
  }

  def addWithChaining(
      hashTable: mutable.HashMap[String, List[Int]],
      key: String,
      value: Int
  ): Unit = {

    val existingValues = hashTable.getOrElse(key, List())
    hashTable(key) = existingValues :+ value
  }

  def main(args: Array[String]): Unit = {
    val students = mutable.HashMap[String, Int]()

    students("Alice") = 85
    students("Bob") = 90
    students("Charlie") = 78
    students("Alice") = 95 // Duplicate key: updates Alice's grade

    println("Student HashMap: " + students)
    val otherStudents = mutable.HashMap[String, Int](
      "Alice" -> 92,
      "David" -> 88,
      "Bob" -> 84
    )

    println("Second HashMap: " + otherStudents)
    val merged = mergeMaps(students, otherStudents)
    println("Merged HashMap: " + merged)
    val hashTable = mutable.HashMap[String, List[Int]]()
    addWithChaining(hashTable, "Key1", 100)
    addWithChaining(hashTable, "Key1", 200)
    addWithChaining(hashTable, "Key1", 300)
    addWithChaining(hashTable, "Key2", 400)

    println("Hash table with chaining: " + hashTable)
  }
}

