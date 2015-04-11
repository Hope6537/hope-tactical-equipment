package org.hope6537.scala

/**
 * 偏函数
 * 限定输入参数的值的函数
 * 把函数应用到其不支持的值时，产生运行异常
 */
class PartialFunctional {

  def classic(): Unit = {
    //PartialFunction[A,B]，Scala包中给出的部分函数类型 输入是A类型，输出时B类型
    val getNum: PartialFunction[Int, String] = {
      case 1 => "one"
      case 2 => "two"
    }

    val getSize: PartialFunction[Int, String] = {
      case 3 => "wow"
      case 4 => "hey"
    }
    println(getNum.isDefinedAt(1))
    println(getNum.isDefinedAt(3))
    /**
     * PartialFunction可以使用orElse组成新的函数
     */
    val part = getNum orElse getSize

    //组合使用
    println(part(1))
    println(part(3))
  }

  /**
   * 对参数模式匹配
   * 对应不同的参数会进行不同的操作
   */
  ???


}

object PartialFunctionalClient extends App {

  val obj = new PartialFunctional
  obj.classic()
}
