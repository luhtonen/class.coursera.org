package quickcheck

import common._

import org.scalacheck._
import Arbitrary._
import Gen._
import Prop._

abstract class QuickCheckHeap extends Properties("Heap") with IntHeap {

  property("min1") = forAll { a: Int =>
    val h = insert(a, empty)
    findMin(h) == a
  }

  property("gen1") = forAll {(h: H) =>
    val m = if(isEmpty(h)) 0 else findMin(h)
    findMin(insert(m, h)) == m
  }

  property("min2") = forAll { (a: Int, b: Int) =>
    val h = insert(a, empty)
    findMin(insert(b, h)) == math.min(a, b)
  }

  property("del1") = forAll { a: Int =>
    deleteMin(insert(a, empty)) == empty
  }

  property("meld1") = forAll { (h1: H, h2: H) =>
    findMin(meld(h1, h2)) == math.min(findMin(h1), findMin(h2))
  }

  property("meld2") = forAll { (h1: H, h2: H) =>
    val xs1 = deleteMinima(h1)
    val xs2 = deleteMinima(h2)
    val xs = xs1 ::: xs2
    deleteMinima(meld(h1, h2)) == xs.sorted
  }

  def deleteMinima(h: H): List[Int] = {
    if (isEmpty(h)) return List()
    val v = findMin(h)
    return v.toInt :: deleteMinima(deleteMin(h))
  }

  property("del2") = forAll { (h: H) =>
    val xs = deleteMinima(h)
    xs == xs.sorted
  }

  lazy val genHeap: Gen[H] = for {
    v <- arbitrary[Int]
    h <- oneOf(const(empty), genHeap)
  } yield insert(v, h)

  implicit lazy val arbHeap: Arbitrary[H] = Arbitrary(genHeap)

}
