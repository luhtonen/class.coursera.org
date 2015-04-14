val f: String => String = { case "ping" => "pong" }

f("ping")
// result: pong
// f("abc") // throws MatchError

val f1: PartialFunction[String, String] = { case "ping" => "pong" }
f1.isDefinedAt("ping")
// result: true
f1.isDefinedAt("abc")
// result: false

// Exercises
val f2: PartialFunction[List[Int], String] = {
  case Nil => "one"
  case x :: y :: rest => "two"
}

f2.isDefinedAt(List(1,2,3))
// result: true

val g: PartialFunction[List[Int], String] = {
  case Nil => "one"
  case x :: rest =>
    rest match {
      case Nil => "two"
    }
}

g.isDefinedAt(List(1,2,3))
// result: true