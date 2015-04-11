package org.hope6537.scala

import scala.collection.mutable.ArrayBuffer

/**
 * 数组操作
 */
object ArrayScala extends App {

  /**
   * 操作经典数组
   */
  def basicArray() = {
    //经典定义数组方法
    val basicArray = new Array[Int](10)
    val staticArray = Array(1, 10, 213123)
    val toArray = Array(1 to 10)
    //输出
    basicArray.foreach(println)
    staticArray.foreach(println)
    toArray.foreach(println)
    //数组初始化后方能赋值，或者对指定的数组成员赋值
    staticArray(0) = 12312
    staticArray(2) = 1111
    println(staticArray(0))
  }

  /**
   * 操作变长数组
   */
  def bufferArray() = {

    val bufferArray = new ArrayBuffer[Int]()
    //添加元素 打断点看不同~
    bufferArray += 1
    bufferArray +=(11123, 312312)
    //操作符重载
    bufferArray ++= Array(2, 3, 4, 5)
    //删除最后一个元素
    bufferArray.trimEnd(1)
    //在第二个元素后插入
    bufferArray.insert(2, 1001)
    //在第三个元素后插入元组
    bufferArray.insert(3, 1002, 1003, 1004)
    //移除第三个元素后的第一个元素
    bufferArray.remove(3)
    //移除第三个元素后的两个元素 即删掉index=3、4
    bufferArray.remove(3, 2)

    //定长数组和变长数组转换
    val D = bufferArray.toArray
    val E = D.toBuffer
    println(E)
  }

  /**
   * 数据遍历
   */
  def foreachArray() = {

    val toArray = Array(1, 2, 3, 4, 5, 6)
    //直接遍历
    for (i <- toArray) {
      println(i)
    }
    //读取下标法
    for (i <- 0 to toArray.length - 1) {
      println(toArray(i))
    }
    //下标跳跃法
    for (i <- 0 to(toArray.length, 2)) {
      println(i)
    }
    //倒序遍历法
    for (i <- (0 to toArray.length).reverse) {
      println(i)
    }

  }


  //basicArray()
  //bufferArray()
  foreachArray()
}
