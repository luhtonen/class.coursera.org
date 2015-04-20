object Generators {
  trait Generator[+T] {
    self =>
    def generate: T
    def foreach[U] (f: T => U): Unit = {
      f(generate)
    }
    def map[S](f: T => S): Generator[S] = new Generator[S] {
      def generate = f(self.generate)
    }
    def flatMap[S](f: T => Generator[S]): Generator[S] = new Generator[S] {
      def generate = f(self.generate).generate
    }
  }

  val integers = new Generator[Int] {
    def generate = scala.util.Random.nextInt()
  }

  val booleans = integers.map(_ >= 0)

  def single[T](x: T) = new Generator[T] {
    def generate = x
  }

  def choose(from: Int, to: Int) = new Generator[Int] {
    def generate = if (from == to) from else scala.util.Random.nextInt(to - from) + from
  }

  def pairs[T, U](t: Generator[T], u: Generator[U]): Generator[(T, U)] = for {
    x <- t
    y <- u
  } yield (x, y)

  def emptyLists = single(Nil)

  def nonEmptyLists = for {
    head <- integers
    tail <- lists
  } yield head :: tail

  def lists: Generator[List[Int]] = for {
    cutoff <- booleans
    list <- if (cutoff) emptyLists else nonEmptyLists
  } yield list

  trait Tree
  case class Inner(left: Tree, right: Tree) extends Tree
  case class Leaf(x: Int) extends Tree

  def leafs: Generator[Leaf] = for {
    x <- integers
  } yield Leaf(x)

  def inners: Generator[Inner] = for {
    l <- trees
    r <- trees
  } yield Inner(l, r)

  def trees: Generator[Tree] = for {
    isLeaf <- booleans
    tree <- if(isLeaf) leafs else inners
  } yield tree

  def test[T](g: Generator[T], numTimes: Int = 100)
             (test: T => Boolean): Unit = {
    for (i <- 0 until numTimes) {
      val value = g.generate
      assert(test(value), "test failed for " + value)
    }
    println("passed " + numTimes + " tests")
  }

  test(pairs(lists, lists)) {
    case (xs, ys) => (xs ++ ys).length > xs.length
  }
  // test will fail when second list is empty
}