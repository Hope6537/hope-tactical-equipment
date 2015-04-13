package org.hope6537.scala

import java.util.concurrent.{BlockingQueue, ConcurrentMap, Executors, LinkedBlockingQueue}

import scala.collection.mutable
import scala.io.Source

/**
 * 构造搜索引擎 -> 实验Scala多线程
 */
class SearchEngine {

  case class User(name: String, id: Int)

  /**
   * 倒排索引
   */
  class InvertedIndex(val userMap: Map[String, User]) {

    /**
     * 另外一种方法就是替换掉线程不安全容器
     */
    //def this() = this(mutable.HashMap[String, User])

    def this() = this(ConcurrentMap[String, User])

    def tokenizeName(name: String): mutable.Seq[String] = {
      name.split(" ").map(_.toLowerCase)
    }

    /**
     * 在这里并不是线程安全的
     * 多个客户端同时输入相同Hash值但值不同的Key的时候会出现覆盖
     */
    def add(term: String, user: User) {
      userMap + term -> user
    }

    /**
     * 所以我们可以在这里使用锁的方式
     */
    def add(user: User): Unit = {
      //tokenizeName(user.name).foreach(term => add(term, user))
      /**
       * 但是这种锁的粒度太大了，导致只有一个客户端能够迭代
       */
      //userMap.synchronized(tokenizeName(user.name).foreach(term => add(term, user)))
      /**
       * 所以讲粒度分化到每个操作底部
       */
      tokenizeName(user.name).foreach(term => userMap.synchronized(add(term, user)))
    }
  }

  /**
   * 加载倒排索引
   */
  trait UserMaker {
    /**
     * 没有副作用、没有涉及到条件校验赋值
     */
    def makeUser(line: String) = line.split(",") match {
      case Array(name, userId) => User(name, userId.trim().toInt)
    }
  }

  class FileRecordProducer(path: String) extends UserMaker {
    val index: InvertedIndex = new InvertedIndex

    /**
     * 对于文件的每一行，我们调用makeUser然后添加到索引中
     * 每一步都是线程安全的、虽然我们不能并行的读取文件、但是我们可以并行的构造用户然后把它添加到索引中去
     * 所以我们可以并行的使用这个对象。
     */
    def run(): Unit = {
      Source.fromFile(path, "Utf-8").getLines().foreach(line => index.add(makeUser(line)))
    }
  }

  /**
   * 我们可以构造一个生产者和消费者模型来使用它
   */
  class Producer(path: String, queue: BlockingQueue[String]) extends Runnable {
    override def run() {
      Source.fromFile(path, "Utf-8").getLines().foreach {
        line => queue.put(line)
      }
    }
  }

  abstract class Consumer(queue: BlockingQueue[String]) extends Runnable {
    override def run() {
      while (true) {
        val item = queue.take()
        consume(item)
      }
    }

    def consume(x: String)
  }

  val queue = new LinkedBlockingQueue[String]()

  /**
   * 一个生产者线程
   */
  val producer: Producer = new Producer("users.txt", queue)
  new Thread(producer).start()

  class IndexerConsumer(index: InvertedIndex, queue: BlockingQueue[String]) extends Consumer(queue) with UserMaker {
    override def consume(x: String): Unit = index.add(makeUser(x))
  }

  val cores = 9
  val index = new InvertedIndex
  val pool = Executors.newFixedThreadPool(cores)

  for (i <- 0 to cores) {
    pool.submit(new IndexerConsumer(index, queue))
  }


}
