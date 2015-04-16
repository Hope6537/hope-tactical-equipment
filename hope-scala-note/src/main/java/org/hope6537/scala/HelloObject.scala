package org.hope6537.scala

import scala.beans.BeanProperty

//主构造器写法
/**
 * 主构造器的参数一般有四种：
 * value：String
 * 生成对象私有字段，对象中没有方法使用value，则没有该字段
 * private val/var value：String
 * 私有字段，私有的getter/setter方法
 * val/var value：String
 * 私有字段，公有的getter/setter方法
 * @BeanProperty val/var value：String
 * 私有字段，共有的Scala和JavaBean的getter/setter方法
 */
class HelloObject /*private 此时主构造器私有，只能通过其他辅助（工厂）来创建对象*/
(val constructorValue1: String, private var _level: Int, @BeanProperty var test: Int) {

  pointer =>
  val value3: String = constructorValue1 + "->" + _level
  var value2: String = "World"
  private var privateValue: String = "Hello"

  /**
   * 辅助构造器
   */
  def this(m: String) {
    //调用主构造器
    this("test", 1, 2)
    this.privateValue = "yep"
  }

  def this() {
    //调用已经定义的辅助构造器
    this("3")
  }


  def add(): Unit = {
    println(privateValue + value2)
  }

  def plus(m: Char) = value2 + m

  //getter方法
  def value1 = privateValue

  //setter方法
  def value1_(value: String) = {
    if (value.length > privateValue.length) {
      privateValue = value
    }
  }

  def level = _level

  def level_(value: Int) = {
    _level = value
  }

  def getInformation = new HelloWorldFactory().getFather

  class HelloWorldFactory {

    val mySpace = "inner class"

    /**
     * 使用指针来调用父类方法
     */
    def getFather = privateValue + value1 + value2 + pointer.value3
  }

}

object HelloObject extends App {

  def testHelloWorld() = {
    //调用伴生方法
    val extenison = HelloObject('a')
    //调用构造方法
    val hello: HelloObject = new HelloObject("Hope6537", 1, 233)
    //无参方法可以不带括号
    hello.add()
    println(hello.plus('s'))
    println(hello.value2)
    hello.value1_("asgard")
    println(hello.value1)
    println(hello.value3)
    println(hello.constructorValue1)
    println(hello.getTest)
    println(hello.getInformation)
  }

  //伴生apply方法
  def apply(n: Char) = new HelloObject(n.toString, 0, 0);

  testHelloWorld()


}
