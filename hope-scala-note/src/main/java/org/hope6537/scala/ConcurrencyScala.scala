package org.hope6537.scala

import java.net.{ServerSocket, Socket}
import java.util.concurrent._

import akka.actor.{Actor, ActorSystem, Props}
import com.typesafe.config.ConfigFactory


/**
 * Scala并发
 */
class ConcurrencyScala {

  def basicMode() = {

    /**
     * 基本的runnable线程模式
     */
    val hello = new Thread(new Runnable {
      override def run() = println("Runnable")
    })
    hello.start()


    /**
     * 创建一个网络请求响应服务
     */
    class NetWorkService(port: Int, poolSize: Int) extends Runnable {
      val serverSocket = new ServerSocket(port)

      override def run(): Unit = {
        val socket = serverSocket.accept()

        /**
         * 但是在单位时间下只能有一个请求可以被响应
         */
        //new Handler(socket).run()

        /**
         * 但是我们可以把每个请求放入一个线程中处理
         */
        new Thread(new Handler(socket)).start()

      }
    }
    /**
     * 请求控制，打印输出信息
     */
    class Handler(socket: Socket) extends Runnable {

      override def run(): Unit = {
        socket.getOutputStream.write(message)
        socket.getOutputStream.close()
        println(Thread.currentThread().getName)
      }

      def message = (Thread.currentThread().getName + "\n").getBytes("UTF-8")
    }

    new NetWorkService(2020, 2).run()
  }

  /**
   * 用我们最熟悉的执行器进行操作~
   */
  def execMode() = {
    /**
     * 我们可以重写刚才那个阻塞式服务器的请求
     */
    class NetWorkService(port: Int, poolSize: Int) extends Runnable {
      val serverSocket = new ServerSocket(port)
      /**
       * 设置线程池
       */
      val pool: ExecutorService = Executors.newFixedThreadPool(poolSize)

      override def run(): Unit = {
        try {
          while (true /*&& !Thread.interrupted()*/ ) {
            val socket = serverSocket.accept()
            pool.execute(new Handler(socket))
          }
        } finally {
          //将不再容纳新线程
          pool.shutdown()
        }
      }

    }
    /**
     * 请求控制，打印输出信息
     */
    class Handler(socket: Socket) extends Runnable {

      override def run(): Unit = {
        socket.getOutputStream.write(message)
        socket.getOutputStream.close()
        println(message)
      }

      def message = (Thread.currentThread().getName + "\n").getBytes("UTF-8")
    }

    new NetWorkService(2021, 2).run()
  }

  def ajaxFutures() = {
    val future = new FutureTask[String](new Callable[String] {
      override def call(): String = "return String"
    })
    val exec: ExecutorService = Executors.newCachedThreadPool()
    exec.execute(future)
    var blockingResult: String = null
    println("->" + blockingResult)
    blockingResult = future.get()
    println("->" + blockingResult)
  }

  /**
   * 线程安全的方法在这里变成了两种
   * 1、synchronized代码块
   * 2、原子、瞬时共享变量
   */
  def threadSafe() = {


    class Person(/*标记瞬时@volatile*/ var name: String) {
      def set(name: String): Unit = {
        this.synchronized {
          this.name = name
        }
      }
    }


  }

  def basicActor() = {

    import akka.actor.ActorDSL._
    import akka.actor.ActorSystem

    implicit val system = ActorSystem()

    //通过ActorDSL函数、可以接受一个Actor的构造器Act，启动并返回Actor。
    val echoServer = actor(new Act {
      become {
        case msg => println("echo " + msg)
      }
    })
    echoServer ! "hi"
    system.shutdown()

  }

  /**
   * actor-akka机理
   */
  def actorSystem() = {
    implicit val system = ActorSystem()
    class EchoServer(name: String) extends Actor {
      def receive = {
        case msg => println("server" + name + " echo " + msg +
          " by " + Thread.currentThread().getName)
      }
    }
    val echoServers = (1 to 10).map(x =>
      system.actorOf(Props(new EchoServer(x.toString))
        .withDispatcher(CallingThreadDispatcher.Id)))
    (1 to 10).foreach(msg =>
      echoServers(scala.util.Random.nextInt(10)) ! msg.toString)

    system.shutdown()

  }

  /**
   * 这个例子是访问若干URL，并记录时间。如果能并行访问，就可以大幅提高性能。
   * 尝试将urls.map修改为urls.par.map这样每个map中的函数都可以并发执行。当函数式和并发结合，就会这样让人兴奋。
   */
  def parallelsCollection() = {
    val urls = List(
      "http://www.baidu.com",
      "http://www.alibaba.com"
    )

    def fromURL(url: String) = scala.io.Source.fromURL(url).getLines().mkString("\n")
    val t = System.currentTimeMillis()
    urls.map(fromURL) //791
    //urls.par.map(fromURL) //102
    println("time: " + (System.currentTimeMillis - t) + "ms")

  }

  /**
   * WordCount实例 并行版
   */
  def wordCount() = {
    val file = List("warn 2013 msg", "warn 2012 msg",
      "error 2013 msg", "warn 2013 time")

    /**
     * 将字符串分割同时以key作为标识来计数
     */
    def wordcount(str: String): Int = str.split(" ").count("msg" == _)

    //par.reduceLeft(_+_) 修改成sum
    //最后将函数传输到List的map中执行
    //val num = file.par.map(wordcount).par.reduceLeft(_ + _)
    val num = file.par.map(item => item.split(" ").count("msg" == _)).par.sum

    println("wordcount:" + num)

  }

  /**
   * 远程传输
   */
  def remote() = {

    //重载
    implicit val system = akka.actor.ActorSystem("RemoteSystem", ConfigFactory.load.getConfig("remote"))
    //定义服务器对象
    class EchoServer extends Actor {
      //将获得的信息打印
      override def receive = {
        case msg: String => println("echo " + msg)
      }
    }
    //服务器变量 作为并行Actor的工厂，创造名称为name的Actor
    val server = system.actorOf(Props[EchoServer], name = "echoServer")

    //然后根据端口和akka路径获得通信通道
    val echoClient = system.actorSelection("akka://RemoteSystem@127.0.0.1:2552/user/echoServer")
    echoClient ! "Hi Remote"

    system.shutdown()

  }

}

object ConcurrencyScalaClient extends App {

  val obj = new ConcurrencyScala
  /*obj.basicActor()
  obj.actorSystem()*/
  //obj.parallelsCollection()
  //obj.wordCount()
  //obj.remote()
  //obj.basicMode()
  //obj.execMode()
  obj.ajaxFutures()
}
