package org.hope6537.scala

/**
 * Scala的映射，也就是Map
 */
object MapperScala extends App {

  /**
   * 构造不可变映射
   */
  def finalMap() = {
    val basicMap = ("name" -> "zhao", "age" -> "21", "num" -> "041240827")
    val tupleMap = (("country", "china"), ("nation", "chinese"), ("province", "jilin"))
    val name = basicMap._1._2
    println(name)
  }

  /**
   * 构造可变映射
   */
  def variableMap() = {
    //类似于Array.asList
    val collection1 = scala.collection.mutable.Map(("country", "china"), ("nation", "chinese"), ("province", "jilin"))
    //定义类型可以设定初值
    var collection2: Map[Int, Int] = Map(1 -> 2, 2 -> 3)
    //两种获取值的方法
    println(collection1.get("country"))
    println(collection1("nation"))
    println(collection2(2))
    //是否存在
    println(collection1.contains("asdasdasd"))
    //如果有前者的映射，那么返回映射的值，否则返回-1
    println(collection2.getOrElse(3, -1))

    //进行操作 增加键值对
    collection2 +=(3 -> 4, 4 -> 5)
    //删除键
    collection2 -= 1
  }

  def foreachMap() = {
    val collection1 = scala.collection.mutable.Map(("country", "china"), ("nation", "chinese"), ("province", "jilin"))
    for ((i, j) <- collection1) {
      println(i + ":" + j)
    }
    //翻转key和value
    val collectionReverse = for ((i, j) <- collection1) yield (j, i)
    val keySet = collection1.keySet
    val value = collection1.values

    collectionReverse.foreach(println)
    println(keySet)
    println(value)
  }

  //Scala的元组 元组将值绑定在一起
  def turples() = {
    val g = (1, 2321.21, "asdas")
    val num = g._1
    val doublenum = g._2
    val str = g._3

    printf("%d,%f,%s\n", num, doublenum, str)

    val one = Array('a', 'b', 'c')
    val two = Array(1, 2, 3)
    //生成一个二元数组
    val three = one.zip(two)
    val three_2 = one zip two
    println(three.toString)
    println(three_2.toString)
    //生成一个映射
    val four = one.zip(two).toMap
    val four_2 = one zip two toMap;
    println(four)
    println(four_2)
  }

  //  finalMap()
  //  variableMap()
  //  foreachMap()
  turples()
}
