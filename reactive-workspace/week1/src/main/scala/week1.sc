val f: String => String = { case "ping" => "pong" }

f("ping")
// f("abc") // throws MatchError
