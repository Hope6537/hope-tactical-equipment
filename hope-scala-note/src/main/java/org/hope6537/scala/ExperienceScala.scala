package org.hope6537.scala

import java.util.Date

import scala.collection.mutable

/**
 * Scala其他玩意
 */
class ExperienceScala {

  /**
   * 抽取器
   */
  def regex() = {

    import scala.util.matching.Regex

    object Email {
      //定义正则表达式
      def unapply(str: String) = {
        new Regex( """(.*)@(.*)""").unapplySeq(str).get match {
          //将字符串按照规则抽取 Some是Case匹配类
          case user :: domain :: Nil => Some(user, domain)
          case _ => None
        }
      }
    }

    //示例
    "user@domain.com" match {
      case Email(user, domain) => println(user + "@" + domain)
    }

  }

  /**
   * 记忆模式
   */
  def memory() = {

    //增加缓存
    val cache = new mutable.WeakHashMap[Int, Int]
    //memo可以将一个不含cache函数，包装成一个含有cache功能的 简而言之 包装器
    //构造为要一个能产生下一个Int值得函数，同时柯里化之再来一个新的值，那么就更新添加，如果有当前值，那么就获取
    //就是有则取之，无则加之、正好符合缓存的思想
    def memo(f: Int => Int) = (x: Int) => {
      cache.getOrElseUpdate(x, f(x))
    }
    //斐波那契数列，这是一个函数
    def fibonacci_(in: Int): Int = in match {
      case 0 => 0;
      case 1 => 1;
      case n: Int => fibonacci_(n - 1) + fibonacci_(n - 2)
    }
    /**
     * 尝试将fibonacci_(n - 1) + fibonacci_(n - 2)修改为memo(fibonacci_)(n - 1) + memo(fibonacci_)(n - 2)效率会更高
     * 之后我们将这个函数传进到memo中去，因为memo正需要一个(Int=>Int)形态的函数
     */
    val fibonacci: Int => Int = memo(fibonacci_)

    /**
     * 这样函数就包装好了，开干!
     */
    val t1 = System.currentTimeMillis()
    println(fibonacci(40))
    println("it takes " + (System.currentTimeMillis() - t1) + "ms")

    val t2 = System.currentTimeMillis()
    //在这里会直接调用上面刚刚缓存玩的东西
    println(fibonacci(40))
    println("it takes " + (System.currentTimeMillis() - t2) + "ms")
  }


  def dsl() = {

    case class Twitter(id: Long, text: String, publishedAt: Option[java.util.Date])

    val twitters = Twitter(1, "hello scala", Some(new Date())) ::
      Twitter(2, "I like scala tour", None) :: Nil

    /*val json = ("twitters"
       -> twitters.map(
       t => ("id" -> t.id)
         ~ ("text" -> t.text)
         ~ ("published_at" -> t.publishedAt.toString)))
          println(json)
         */


  }
}

object ExperienceScalaClient extends App {
  val obj = new ExperienceScala
  obj.regex()
  obj.memory()
  obj.dsl()

}
