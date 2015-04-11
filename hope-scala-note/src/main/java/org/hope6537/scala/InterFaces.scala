package org.hope6537.scala

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

/**
 * Scala的接口们
 * 在构造时的顺序为
 * 超类
 * 父特质
 * 第一个特质
 * 第二个特质(父特质不重复构造)
 * 类
 *
 * 如果class A extends B1 with B2 with B3....
 * 那么，串接B1、B2、B3...等特质，去掉重复项且右侧胜出
 * 当为类添加多个互相调用的特质时，从最后一个开始进行处理
 * 在类中super.foo()这样的方法调用是静态绑定的，明确是调用它的父类的foo()方法
 * 在特质中写下了super.foo()时，它的调用是动态绑定的。调用的实现讲在每一次特质被混入到具体类的时候才被决定
 * 因此，特质混入的次序的不同其执行效果也就不同
 */
class InterFaces {

  /**
   * 就是java里的接口
   */
  trait Reset {
    def reset(m: Int, n: Int) = if (m >= n) 1
  }

  trait Member {
    def id = Random.nextInt()
  }

  /**
   * 可以在接口组合接口
   */
  trait Excited extends Reset with Member {

  }

  class User1 extends Reset {

  }

  class User2 extends Reset with Member with Serializable {


    /**
     * 可以在对象构造时混入特质
     */
    val five = new User1 with Serializable

  }

}

/**
 * 一个抽象整形队列
 */
abstract class IntQueue {
  def get(): Int

  def put(x: Int)
}

/**
 * 具体实现
 */
class BasicIntQueue extends IntQueue {

  private val buf = new ArrayBuffer[Int]

  def get() = buf.remove(0)

  def put(x: Int) {
    buf += x
  }
}

/**
 * 同样的，定义特质，重写抽象类的方法
 */
trait Incrementing extends IntQueue {
  abstract override def put(x: Int) {
    super.put(x + 1)
  }
}

/**
 * 重写抽象类2
 */
trait Doubling extends IntQueue {
  abstract override def put(x: Int) {
    super.put(2 * x)
  }
}

object TestClient extends App {
  /**
   * 在创建的时候混入特质 但是会奔最右面的去
   */
  val queue1 = new BasicIntQueue with Incrementing with Doubling
  queue1.put(2)
  //实际上为2*2 然后再 2*2的结果+1　最后为5
  println(queue1.get())

  /**
   * 先2+1 在3*2
   */
  val queue2 = new BasicIntQueue with Doubling with Incrementing
  queue2.put(2)
  println(queue2.get())
}
