package org.hope6537.scala

/**
 * Scala的函数式编程
 */
class Functional {

  def getFrontFunction: FrontFunction = new FrontFunction

  def getControlAbstract: ControlAbstract = new ControlAbstract

  /**
   * 头等函数 函数是一个值
   * 下面是个计算Σa,b的故事
   */
  class FrontFunction {

    //f(n) = n
    def sum1(a: Int, b: Int): Int = {
      if (a > b) 0 else a + sum1(a + 1, b)
    }

    //f(n) = n*2
    /*
    def sum2(a: Int, b: Int) = {
      if (a > b) 0 else a * 2 + sum(a + 1, b)
    } */

    def f(a: Int) = a * 2

    def sum2(a: Int, b: Int): Int = {
      if (a > b) 0 else f(a) + sum2(a + 1, b)
    }

    //在Scala中函数能够作为参数进行传递，函数能够调用满足参数要求的不同函数作为参数
    def sum_functional(f: Int => Int, a: Int, b: Int): Int = {
      if (a > b) 0 else f(a) + sum_functional(f, a + 1, b);
    }

    def f1(a: Int) = a

    def f2(a: Int) = a * 2

    def sum1_functional(a: Int, b: Int): Int = sum_functional(f1, a, b)

    def sum2_functional(a: Int, b: Int): Int = sum_functional(f2, a, b)

  }

  /**
   * 高阶函数
   */
  class HighFunction {
    val f1 = (a: Int) => a
    val f2 = (a: Int) => a * 2
    val f3 = (a: Int) => a * 3
    val conn: Connection = new Connection()
    val t1 = mul(1)
    val t2 = mul(2)
    val t3 = mul(3)
    withClose(conn, conn =>
      println("do something with Connection"))

    def sum_functional(f: Int => Int, a: Int, b: Int): Int = {
      if (a > b) 0 else f(a) + sum_functional(f, a + 1, b)
    }

    def withClose(closeAble: {def close(): Unit}, op: {} => Unit) {
      try {
        op(closeAble)
      } finally {
        closeAble.close()
      }
    }

    /**
     * 函数柯里化
     * 柯里化是指将接受两个参数的函数变成新的接受一个参数的函数的过程
     * 例如
     * def mul(a:Int,b:Int)=a*b
     * 代表的是
     * mul : (Int,Int) => Int
     * 柯里化之后，定义两个参数函数，代表一个f1(a)和f2(b)将其组合
     * def mul(a:Int)(b:Int)=a*b
     * mul : (Int)(Int) => Int
     *
     * 对于这个函数
     * def A(a: T1, b: T2, c: T3, d: T4) = E
     * 等价于
     * def A(a: T1)(b: T2)(c: T3)(d: T4) = E
     * 即为
     * def A = (a:T1) => (b:T2) => (c:T3) => (d:T4) => E
     *
     */
    def mul(a: Int) = (b: Int) => b * a

    class Connection {
      def close() = println("close Connection")
    }

  }

  /**
   * 以求平方根迭代算法举例
   * X(n+1) = (X(n)+x/X(n))/2
   *
   * 柯里化带来的另一个好处是可以先预定义一些要传入的参数的值
   * 然后再一定步骤之后再传入剩下的
   * 将抽象步骤控制在一定的流程之中
   *
   */
  class ControlAbstract {

    //在重新计算
    val m = two(5)

    /**
     * @param a a是算法的初始值或称预测值
     * @param b b是需要求平方根的自变量
     */
    def sqrt(a: Double, b: Double): Double = {
      val next = (a + b / a) / 2
      if (b - next * next > 0.001 || next * next - b > 0.001) sqrt(next, b)
      next
    }

    def sqrt_currier(a: Double)(b: Double): Double = {
      val next = (a + b / a) / 2
      if (accuracy(b, next)) sqrt(next, b)
      next
    }

    def SQ(f: (Double, Double) => Double)(a: Double)(b: Double): Double = {
      val next = f(a, b)
      if (accuracy(b, next)) {
        SQ(f)(next)(b)
      }
      next
    }

    /**
     * 定义函数和参数
     */
    def two(b: Double): Double = SQ2(a => (a + b / a) / 2)(1)

    def SQ2(f: Double => Double)(a: Double) = {
      def inSQ(guess: Double): Double = {
        val next = f(guess)
        if (accuracy_SQ2(guess, next)) inSQ(next)
        next
      }
      //抖机灵后接入法
      inSQ(a)
    }

    def accuracy_SQ2(guess: Double, next: Double) = (guess - next) * (guess - next) > 0.00001

    def accuracy(b: Double, next: Double): Boolean = (b - next * next) * (b - next * next) > 0.00001
  }

}

object FunctionalClient extends App {

  val functional = new Functional
  val frontFunction = functional.getFrontFunction
  val controlAbstract = functional.getControlAbstract
  //使用匿名函数方法
  println(frontFunction.sum_functional((a: Int) => a * 3, 1, 3))
  //使用声明式函数
  println(frontFunction.sum1_functional(1, 3))
  println(frontFunction.sum2_functional(1, 3))

  /**
   * 这样的一个对称玩法，也是柯里化的一个小技巧
   */
  val one = controlAbstract.sqrt_currier(1) _
  println(one(2))
  println(controlAbstract.m)
}
