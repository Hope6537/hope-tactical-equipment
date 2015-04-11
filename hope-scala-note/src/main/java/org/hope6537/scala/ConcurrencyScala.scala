package org.hope6537.scala

/**
 * Scala并发
 */
class ConcurrencyScala {

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
    import akka.actor.{Actor, ActorSystem, Props}
    import akka.testkit.CallingThreadDispatcher
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
    import akka.actor.{Actor, Props}
    import com.typesafe.config.ConfigFactory

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
  obj.remote()
}
