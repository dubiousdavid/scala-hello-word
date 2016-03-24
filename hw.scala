package com.example

import scala.util.Random

object Predef {
  // Implicit number expansion
  val n1 = 1 + 1.0
  val n2 = 1L + 1
  // Not implemented
  def notDoneYet = ???
  // Print
  def greeting = println("Hello World!")
}

object Variables {
  // def vs val
  def r1 = Random.nextInt() // Different value every time
  val r2 = Random.nextInt() // Value doesn't change

  val `a long variable name` = 1
  val x = 3
  val nameEquality = `x` == x

  //Lazy vals
  def lazyTest = {
    println("Called lazyTest")
    3
  }
  lazy val lazyVal = lazyTest // println called first time

  // Mutation (don't do this!)
  var num = 3
  num += 1
}

object IfExpressions {
  val x = 3
  val isThree = if (x == 3) "It's three" else "It's not three"
  val mathTest =
    if (2 + 2 == 5) "Math is broken"
    else if (2 + 2 == 4) "Math is working okay"
    else "I got nothing"
}

object Strings {
  val x = 3
  val y = 2.0

  // Interpolation
  val string = s"1 + $x = ${1 + x}"
  val formatted = f"${y}%.2f"
  val rawString = raw"x is: $x\n"
  val json = s"""{"name": "Billy", "age": $x}"""

  val multiLineStr = """
    Name: Billy
    Age: 36
  """
}

object Numbers {
  val anInt = 500
  val aLong = 500L
  val aFloat = 500F
  val aDouble = 500D
}

object Symbols {
  val sym = 'hello
  val symName = sym.name
  val equality = sym == 'hello
}

object Options {
  val some: Option[Int] = Some(1).map(_ + 1)
  val none = None.getOrElse(2)
  val some2 = Option(1) // Some(1)
  val some3 = Option(null) // None
  val equality = Some(1) == Some(1)
}

object RegExes {
  val numRegex = """([0-9]+)"""
  val re = s"""$numRegex\\.$numRegex""".r //Must double escape for s""
  val number = "99.05"
  // Decompose string with regex
  val re(integerPart, fractionalPart) = number // Will throw if match fails

  def getFractionalPart(number: String) = number match {
    case re(_, fractionalPart) => fractionalPart.toInt
    case _ => 0
  }

  val fractionalPart2 = getFractionalPart(number)
  val fractionalPart3 = getFractionalPart("100")

  val re2 = """(S|s)cala""".r
  val str = "Scala is scalable and cool"
  val firstMatch = re2.findFirstIn(str)
  val allMatches = re2.findAllIn(str)
  val replaceFirst = re2.replaceFirstIn(str, "Clojure")
}

// vals are implied if left off (best practice)
case class Calculator(brand: String, model: String = "")

case class Person(val name: String, val age: Int)

case class Greeting(val greeting: String)

object CaseClasses {
  // Named parameters
  val calcNamed = Calculator(brand = "HP", model = "TI-85")

  val calc = Calculator("HP", "TI-85")
  // Deconstruct case class
  val Calculator(brand, model) = calc
  // Modify a single value of an existing case class
  val calc2 = calc.copy(model = "TI-82")
}

object Functions {
  // Inferred return type
  def addOne(x: Int) = x + 1

  implicit val standardGreeing = Greeting("You look great")
  // Curried function
  def greetMe(p: Person)(g: Greeting) = s"${p.name}: ${g.greeting}"
  // Implicit argument
  def greetMe2(p: Person)(implicit g: Greeting) = s"${p.name}: ${g.greeting}"
  // Default argument
  def greetMe3(p: Person, g: Greeting = Greeting("You look fabulous")) = s"${p.name}: ${g.greeting}"

  val bob = Person("Bob", 32)
  val bill = Person("Bill", 34)
  val frank = Person("Frank", 33)

  val bobGreeting = greetMe(bob)(Greeting("Hey there"))
  val billGreeting = greetMe2(bill)
  val frankGreeting = greetMe3(frank)
  // Partial application
  val greetBob = greetMe(bob)_
  // Variadic
  def capitalizeAll(args: String*) = {
    args.map { arg =>
      arg.capitalize
    }
  }
  val capitalNames = capitalizeAll("billy", "bobby", "frank")
}

object Collections {
  // Lists
  val numbers = List(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
  val negNumbers = -2 :: -1 :: Nil
  val combinedNumbers = negNumbers ++ numbers
  val head :: tail = numbers // Deconstruct
  // map, fold, filter
  val zipped = List(1, 2, 3).zip(List("a", "b", "c"))
  val partitioned = numbers.partition(_ % 2 == 0)
  val find = numbers.find(_ > 5)
  val leftFolded = numbers.foldLeft(0) { (m, n) => m + n }
  val flattened = List(List(1, 2), List(3, 4)).flatten
  // Seqs
  val seq1 = Seq("Clojure", "Scala")
  val seq2 = "Haskell" +: seq1
  val seq3 = "Scala" +: Seq.empty
  val sHead +: sTail = seq1 // Deconstruct
  // Vector
  val veq1 = Vector("Clojure", "Scala")
  val veq2 = veq1 :+ "Haskell"
  val vHead :+ vTail = veq1 // Deconstruct
  val veq3 = veq1.updated(0, "Haskell") // Update a value
  // Sets
  val s1 = Set(1, 1, 2)
  val s2 = s1 + 3
  val s3 = s1 - 1
  val s4 = s1 ++ List(3, 4)
  val s5 = s1 -- List(1, 2)
  val s6 = s1 + (3, 4)
  // Maps
  val extensions = Map("steve" -> 100, "bob" -> 101, "joe" -> 201)
  val filteredMap = extensions.filter(_._2 < 200)
  val filteredMap2 = extensions.filter { case (name, extension) => extension < 200 }
  // Code below won't compile
  // val filteredMap3 = extensions.filter { case 3 => true }
  val moreExtensions = extensions + ("frank" -> 302)
  val noSteve = extensions - "steve"
  val noBobJoe = extensions -- List("bob", "joe")
  val maybeSteve = extensions.get("steve")
  val noFrank = extensions.getOrElse("frank", 0)
  val steveUpdated = extensions.updated("steve", 102)
  // Ranges
  val range = (1 to 3).map { i => i }
  val charRange = 'a' to 'g'
}

object Tuples {
  val t1 = ("localhost", 80)
  val firstItem = t1._1
  val (host, port) = t1 // Destructure
}

object Blocks {
  // Evaluates all expressions returning the value of the last one
  val codeBlock = {
    println()
    3
  }
  // Defines a function
  val functionBlock = { x: Int => x + 1 }
  // Defines a partial function (i.e., one that can't necessarily handle every input for a given type)
  val partialFunctionBlock: PartialFunction[Int, String] = { case 3 => "3" }
}

object WeekDay extends Enumeration {
  type WeekDay = Value
  val Mon, Tue, Wed, Thu, Fri, Sat, Sun = Value
}

object Enumeration {
  import WeekDay._

  def isWorkingDay(d: WeekDay) = !(d == Sat || d == Sun)
  def workDays = WeekDay.values filter isWorkingDay
}

object DiscriminatedUnion {
  // Use a sealed trait to limit inheritance to the current file
  sealed trait HttpMethod

  case class DELETE() extends HttpMethod
  case class GET() extends HttpMethod
  case class POST(body: String) extends HttpMethod
  case class PUT(body: String) extends HttpMethod

  def reqBody(req: HttpMethod) = req match {
    case POST(body) => Some(body)
    case PUT(body) => Some(body)
    case _ => None
  }

  val postBody = reqBody(POST("name=Bobby"))
  val getBody = reqBody(GET())
}

object ForComprehensions {
  val comprehension = for (i <- List.range(0, 20) if i % 2 == 0) yield i
  val multiComprehension =
    for (
      i <- 0 until 20;
      j <- i until 20 if i + j == 32
    ) yield (i, j)
  def printNums = for (i <- 0 to 10) println(i)

  val dogBreeds = List(Some("Doberman"), None, Some("Yorkshire Terrier"),
    Some("Dachshund"), None, Some("Scottish Terrier"),
    None, Some("Great Dane"), Some("Portuguese Water Dog"))

  val filteredBreeds = for {
    breedOption <- dogBreeds
    breed <- breedOption
    if breed.contains("Terrier") && !breed.startsWith("Yorkshire") // guard
    upcaseBreed = breed.toUpperCase()
  } yield upcaseBreed

  val pattmatchedFor = for {
    Some(breed) <- dogBreeds
    upcasedBreed = breed.toUpperCase()
  } yield upcasedBreed
}

object PatternMatching {
  def shine(c: Car with Shiny) = c match {
    case b: BMW => s"BMW shine: ${b.shineRefraction}"
    case m: Mercedes => s"Mercedes shine: ${m.shineRefraction}"
  }
  def personName(p: Person) = p match {
    case Person("Bob", _) => "Hey Bob!"
    case Person("Bill", _) => "Hey Bill!"
    case Person(_, age) if age > 30 => "You're over 30." // guard
    case x @ Person(_, age) => s"${x.toString} your age is $age."
  }
  // Regexes
  val BookExtractorRE = """Book: title=([^,]+),\s+author=(.+)""".r
  val MagazineExtractorRE = """Magazine: title=([^,]+),\s+issue=(.+)""".r

  def regexMatcher(item: String) =
    item match {
      case BookExtractorRE(title, author) => title
      case MagazineExtractorRE(title, issue) => title
      case entry => s"Unrecognized entry: $entry"
    }
  val book = regexMatcher("Book: title=Programming Scala Second Edition, author=Dean Wampler")
  val magazine = regexMatcher("Magazine: title=The New Yorker, issue=January 2014")

  def checkY(y: Int) = {
    for (x <- Seq(99, 100, 101)) yield {
      x match {
        // Use the current value of y instead of assigning a new value
        case `y` => s"found $y"
        case i: Int => "int: " + i
      }
    }
  }
}

object Exceptions {
  def divide(x: Int, y: Int) =
    try { x / y }
    catch {
      case e: Exception => 0
    }
}

/* Advanced Section */

object CallByName {
  // println should NOT be called
  def a = Some(3).getOrElse { println("nothing from a"); 1 }
  // println should be called
  def b = None.getOrElse { println("nothing from b"); 1 }

  def run = println(s"a: $a, b: $b")
}

object ValueClasses {
  // Rules: Must extend AnyVal and have only one public val argument
  // Can provide a performance increase as values are stored on the stack instead of the heap

  // Add method to String class
  implicit class StringStuff(val s: String) extends AnyVal {
    def first: String = s.take(1)
  }

  val firstLetter = "Bobby".first

  class Dollar(val value: Float) extends AnyVal {
    override def toString = "$%.2f".format(value)
  }
  val benjamin = new Dollar(100)
}

object TailRecursion {
  import scala.annotation.tailrec

  def gcd(x: Int, y: Int): Int =
    if (y == 0) x else gcd(y, x % y)

  def factorial(i: BigInt): BigInt = {
    @tailrec
    def fact(i: BigInt, accumulator: BigInt): BigInt =
      if (i == 1) accumulator
      else fact(i - 1, i * accumulator)

    fact(i, 1)
  }
}

object TypeClasses {
  case class Address(street: String, city: String)
  case class Person(name: String, address: Address)

  trait ToJSON {
    def toJSON(level: Int = 0): String

    val INDENTATION = "  "
    def indentation(level: Int = 0): (String, String) =
      (INDENTATION * level, INDENTATION * (level + 1))
  }

  implicit class AddressToJSON(address: Address) extends ToJSON {
    def toJSON(level: Int = 0): String = {
      val (outdent, indent) = indentation(level)
      s"""{
      |${indent}"street": "${address.street}",
      |${indent}"city": "${address.city}"
      |$outdent}""".stripMargin
    }
  }

  implicit class PersonToJSON(person: Person) extends ToJSON {
    def toJSON(level: Int = 0): String = {
      val (outdent, indent) = indentation(level)
      s"""{
      |${indent}"name": "${person.name}",
      |${indent}"address": ${person.address.toJSON(level + 1)}
      |$outdent}""".stripMargin
    }
  }

  val a = Address("1 Scala Lane", "Anytown")
  val p = Person("Buck Trends", a)
  val json = p.toJSON()
}

object Linearization {
  class C1 {
    def m = print("C1 ")
    val n = 1
  }

  trait T1 extends C1 {
    override def m = { print("T1 "); super.m }
  }

  trait T2 extends C1 {
    override def m = { print("T2 "); super.m }
    override val n = 2
    // This will cause a compile error
    // val o = 'b'
  }

  trait T3 extends C1 {
    override def m = { print("T3 "); super.m }
    override val n = 3
    val o = 'a'
  }

  class C2 extends T1 with T2 with T3 {
    override def m = { print("C2 "); super.m }
  }

  val c2 = new C2
  // c2.m
  // Output: C2 T3 T2 T1 C1
}

object Variance {
  class CSuper { def msuper() = "CSuper" }
  class C extends CSuper { def m() = "C" }
  class CSub extends C { def msub() = "CSub" }

  val f1: C => C = (c: CSuper) => new CSub
  val f2: C => String = (c) => s"${c.msuper()} ${c.m()}"
  val f3: C => String = (c: CSuper) => c.msuper()

  // Won't compile
  // f1(new CSuper)
  // val f4: C => C = (c: CSub) => new CSub
  // val f5: C => C = (c: C) => new CSuper
  // val f6: C => String = (c) => c.msub()

  // Will compile
  f1(new C)
  f1(new CSub)
}

trait Car {
  val brand: String
}

trait Shiny {
  val shineRefraction: Int
}

class BMW extends Car with Shiny {
  val brand = "BMW"
  val shineRefraction = 12
}

class Mercedes extends Car with Shiny {
  val brand = "Mercedes"
  val shineRefraction = 14
}

object Traits {
  val bmw = new BMW
  val mercedes = new Mercedes
  val cars: List[Car with Shiny] = List(bmw, mercedes)
  val carShine = cars.map(PatternMatching.shine)
}
