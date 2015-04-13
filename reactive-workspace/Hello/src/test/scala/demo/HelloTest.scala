package demo

import org.scalatest.FunSuite

/**
 * Created by eduard.luhtonen on 13.04.2015.
 */
class HelloTest extends FunSuite {
  test("sayHello method works correctly") {
    val hello = new Hello
    assert(hello.sayHello("Scala") == "Hello, Scala!")
  }
}
