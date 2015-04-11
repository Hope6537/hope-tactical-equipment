package org.hope6537.scala

import java.text.SimpleDateFormat

/**
 * Scala的泛型
 *
 * 多重界定
 * T >: lower <:upper //同时有上下界，但不能同时有多个上界或下界
 * T <: Ordered[T] with Cloneable//能要求同时混入多个特质
 * T <% Ordered[T] <% String//能同时多个视图限定
 * T : Ordering : Manifest//能同时多个上下文界定
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

  /**
   * 类型参数界限
   */
  def genericOuter() = {
    /**
     * T <: Ordered[T]
     * 实际上就是Java中的<T extends Orderer<? super T>>
     * 可以通过自行构建的类混入（实现）Ordered特质（接口）使之符合参数界限(能比较)
     */
    abstract class Stack[T <: Ordered[T]]
    class EmptyStack[T <: Ordered[T]] extends Stack[T]
    class NonEmptyStack[T <: Ordered[T]](elem: T, rest: Stack[T]) extends Stack[T]

    /**
     * 视界限定，T可以被隐式的转换为Ordered[T]
     *
     */
    abstract class CustomList[T <% Ordered[T]]

    /**
     * 关于隐式转换还有implicit关键字
     * 这个例子可以将String自动转换为Date类型。隐式转换时实现DSL的重要工具。
     */
    implicit def strToDate(str: String) = new SimpleDateFormat("yyyy-MM-dd").parse(str)

    /**
     * 当调用和Date类有关的方法时，就会自动调用该转换
     */
    println("2013-01-01 unix time: " + "2013-01-01".getTime / 1000l)


    /**
     * 类似的，便有类型参数下界
     * R >: S //S是R的子类，R是S的超类
     * 在这里K和V都是指
     *
     * INT是K的子类，而K是INT的超类
     */
    class CustomMap[K >: Int, V >: Int]

    /**
     * 所以只有INT和INT的超类能够使用它
     * 当使用String的时候会出现编译错误
     */

    val obj = new CustomMap[Int, AnyVal]
  }

  /**
   * 协变和逆变
   *
   * 逆变指的是向上转型
   * 协变指的是向下转型
   */
  def castType() = {

    trait Function1[-T, +R] extends AnyRef {
      def getSound(t: T): String
    }

    class Animal {
      val sound: String = " rustle"
    }

    class Bird extends Animal {

      override val sound: String = "Bird"
    }
    class Chicken extends Bird {

      override val sound: String = "Chicken"

    }

    val getTweet: (Bird => String) = (a: Animal) => a.sound
    println(getTweet(new Bird()))
    val hatch: () => Bird = () => new Chicken
    println(getTweet(hatch()))

  }

  /**
   * Scala中的闭包
   */
  def closePackage() = {

    class ClosureClass {
      def printResult[T](f: => T) = {
        println(f)
      }

      def printResult[T](f: String => T) = {
        println(f("HI THERE"))
      }
    }

    val cc = new ClosureClass
    //任意类型
    cc.printResult("sadasd")
    //闭包用法
    //val f: String => Char = (a: String) => a.charAt(0)
    //cc.printResult(f)
  }

  //testStack()
  //genericOuter()
  castType()
  closePackage()

}

