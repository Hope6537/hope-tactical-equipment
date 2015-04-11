package org.hope6537.scala

/**
 * Scala的泛型
 */
object GenericScala extends App {

  abstract class Stack[T] {

    def push(x: T): Stack[T] = new NonEmptyStack[T](x, this)

    def isEmpty: Boolean

    def top: T

    def pop: Stack[T]

  }

  class EmptyStack[T] extends Stack[T] {

    override def isEmpty: Boolean = true

    override def top: T = sys.error("empty")

    override def pop: Stack[T] = sys.error("empty")
  }

  class NonEmptyStack[T](elem: T, rest: Stack[T]) extends Stack[T] {

    override def isEmpty: Boolean = false

    override def top: T = elem

    override def pop: Stack[T] = rest
  }

  def testStack(): Unit = {
    val m = new EmptyStack[Int]
    val n = new EmptyStack[Array[Char]]

    val q = m.push(1).push(2)
    val p = n.push(Array('1', '2', '3'))

    println(q.top)
    p.top.foreach(println)
  }

  testStack()

}

