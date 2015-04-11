package org.hope6537.scala

import scala.io.StdIn
import scala.util.Random

/**
 * 基本操作
 */
object BaseScala extends App {

  //if表达式
  def min(x: Int, y: Int): Int = {
    var a = x
    if (x > y) {
      a = y
    }
    //可以不用return
    a
  }

  //if表达式可以用来赋值
  def max(x: Int, y: Int) = if (x > y) x else y

  def foreach() = {
    //循环
    var m = 3
    while (m != 0) {
      println(m)
      m -= 1
    }
    //for for(i<-e)E(i)
    for (i <- 1 to 4) {
      println("NO" + i)
    }
    //可以使用if
    for (i <- 1 to 4) {
      if (i % 2 == 0) {
        println("No" + i)
      }
      //可以多个if语句一起联立
      if (i % 2 != 0) if (i % 2 != 0 && i != 1) if (i != 3) {
        println("Wow" + i)
      }
    }
    //也可以直接写在for循环里 以分号为分割
    for (i <- 1 to 4; if i % 2 == 0; if i != 2) {
      println("hehe" + i)
    }
    //也可以多个变量
    for (i <- 1 to 4; j <- 1 to 2) {
      println(i + "__" + j);
    }

    val numVector = for (i <- 10 to 40 if i % 2 == 0) yield i
    println(numVector)
  }

  //匹配，类似于Switch
  def matcher() = {
    val numVector = for (i <- 1 to 40) yield i
    val sign = numVector.apply(Random.nextInt(40)) match {
      case 10 => "hello"
      case 20 => "suck"
      case _ => "whatever"
    }
    println(sign)
  }

  def exception() = {
    try {
      println("try")
      val str: String = null
      str.charAt(0);
    }
  }

  def console() = {
    println("abc")
    val std = StdIn.readLine()
    println(std)
  }


  //foreach()
  //matcher()
  //exception()
  console()


}

