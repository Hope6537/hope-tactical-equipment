package org.hope6537.scala

/**
 * Scala的继承、重写、抽象
 */
class ExtendScala {


  /**
   * 在继承中可能会出现子类篡改父类值的现象
   * 特别是这些值可能会在构造时被使用、例如开数组的等操作
   * 那么就应该
   * 将超类的val声明为final或将超类的val声明为lazy
   * 同时在子类中使用提前定义语法
   */
  class month {

    private var i: Int = 0
    val e = 0
    val t = 0
    /**
     * 不可变
     */
    final var password = 123

    def method() = {
      println("super")
    }

    /**
     * 方法不可变
     */
    final def getI = i

    def setI(newValue: Int) = {

      i = newValue
    }

  }

  /**
   * 1、子类能重写超类的成员
   * 2、单例对象一样能从类中继承
   * 3、继承了非私有成员
   *
   * 提前定义：在超类的构造器运行之前初始化子类的字段
   * 把需要提前定义的语句块放在extends与超类之间，并后接with保留字
   */
  class week(override val e: Int) extends {
    /*提前定义*/
    override val t = 1
  } with month {
    var k: Int = 2
    var n = super.getI

    /**
     * 重写规则
     * 重写def
     * 用val：利用val能重写超类用没有参数的方法(getter)
     * 用def：子类的方法与超类方法重名
     * 用var：同时重写getter、setter方法，只重写getter方法报错
     * 重写val
     * 用val：子类的一个私有字段与超类的字段重名，getter方法重写超类的getter方法
     * 重写var
     * 用var：且当超类的var是抽象的才能被重写，否则超类的var都会被继承
     */
    override def method() = {
      println("this")
    }

  }

  /**
   * 不能被实例的类叫做抽象类
   */
  abstract class abstractClass {

    var username: String
    var password: String

    def sign()
  }


}
