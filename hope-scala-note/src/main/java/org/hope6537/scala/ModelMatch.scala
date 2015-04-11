package org.hope6537.scala

/**
 * 模式匹配
 *
 * 模式匹配完成后需要确保所有情况皆被考虑
 * Scala编译器会检测match表达式所遗漏的模式组合
 * 但也可以使用@unchecked注解来解除警告
 */
class ModelMatch {

  /**
   * 经典匹配法
   * _代表任意
   * _*代表任意长度
   */
  def classicMatch() = {
    val hi = Array("hi", "hello", "world")
    hi match {
      case Array("hi", _*) => println("Ok")
      case Array("hi", "world", _) => println("Wrong")
      case _ => println(" None")
    }
  }

  /**
   * 类型匹配,无法匹配类似于List[String]的泛型类型
   * 以为JVM会擦除内部泛型来编译
   */
  def typeMatch(a: Any) {
    a match {
      case _: Array[Char] => println("Array[Char]")
      case _: Int => println("Int")
      case _: Char => println("Char")
      case _ => println("Error")
    }
  }

  /**
   * 模型匹配示例
   */
  def typeMatchTest() = {

    /**
     * sealed 使样本的超类被封闭，封闭类除了累定义文件之外不能添加子类
     */
    sealed abstract class Expr {
      def getType: String = this match {
        case Number(n) => "Number"
        case Sum(e1, e2) => " Sum"
      }
    }

    /**
     * Scala的case class就是在普通的类定义前加case这个关键字，然后你可以对这些类来模式匹配。
     * 首先，编译器为case class生成一个同名的对象构造器（Factory Method）
     * 也就是你可以使用 Var(“x”) 来创建一个类的实例，而无需使用new Var(“x”)
     * 例如这个的Number(n)
     */

    case class Number(n: Int) extends Expr {
      val name: String = n.toString
    }

    case class Sum(e1: Expr, e2: Expr) extends Expr

    case class /*object 被封闭后只能使用class来进行继承*/ Mul(e1: Expr, e2: Expr) extends Expr

    def typeMatch(a: Expr): String = a match {
      //根据样板进行匹配
      case Number(n) => "Number"
      case Sum(m, n) => "Sum"
      case _ => "Else"
    }

    /**
     * 这个构造器在嵌套使用时显得非常简洁明了，
     * 比如我们构建如下的表达式，这种写法避免了很多new的使用。
     */
    println(typeMatch(Number(1)))
    println(typeMatch(Sum(Number(1), Number(2))))
    println(typeMatch(Mul(Number(1), Number(2))))

    /**
     * Scala编译器为case class的构造函数的参数创建以参数名为名称的属性，
     * 比如Number的类的参数name:String可以直接通过.name访问
     */
    println(Number(1).name)

    val hi = new Number(4)
    val hello = new Sum(Number(3), Number(7))

    println(hi.getType)
    println(hello.getType)
  }
}


object ModelMatchClient extends App {

  var obj = new ModelMatch

  obj.classicMatch()
  obj.typeMatch(1)
  obj.typeMatchTest()

}
