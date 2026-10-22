case class Person(name: String, age: Int, city: String)

object PersonOperations {
  def filterOlderThan30(people: List[Person]): List[Person] = {
    people.filter(person => person.age > 30)
  }

  def groupByCity(people: List[Person]): Map[String, List[Person]] = {
    people.groupBy(_.city)
  }
  def personToString(people: List[Person]): List[String] = {
    people.map(person =>
      s"${person.name} is ${person.age} years old and lives in ${person.city}"
    )
  }
  def sortByAge(people: List[Person]): List[Person] = {
    people.sortBy(_.age)
  }

  def main(args: Array[String]): Unit = {
    val people = List(
      Person("Alice", 25, "Kolkata"),
      Person("Bob", 35, "Delhi"),
      Person("Charlie", 28, "Mumbai"),
      Person("David", 42, "Kolkata"),
      Person("Emma", 31, "Delhi")
    )

    println("Original List of People:")
    people.foreach(println)
    println("\nPeople Older Than 30:")
    filterOlderThan30(people).foreach(println)

    println("\nPeople Grouped by City:")
    groupByCity(people).foreach { case (city, persons) =>
      println(city + " -> " + persons.map(_.name).mkString(", "))
    }

    println("\nPerson Details:")
    personToString(people).foreach(println)

    println("\nPeople Sorted by Age (Ascending):")
    sortByAge(people).foreach(println)
  }
}
