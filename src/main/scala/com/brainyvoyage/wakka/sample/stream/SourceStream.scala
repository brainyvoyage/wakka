package com.brainyvoyage.wakka.sample.stream

import akka.{Done, NotUsed}
import akka.actor.ActorSystem
import akka.util.ByteString

import scala.concurrent._
import scala.concurrent.duration._
import java.nio.file.Paths

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, IOResult, ThrottleMode}
import akka.stream.scaladsl._


object SourceStream extends App {
  implicit val system: ActorSystem = ActorSystem("Number-Stream")
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  val source: Source[Int, NotUsed] = Source(1 to 100)
  val done: Future[Done] = source.runForeach(i => println(i))(materializer)

  implicit val ec: ExecutionContextExecutor = system.dispatcher

  def lineSink(fileName: String): Sink[String, Future[IOResult]] = Flow[String]
    .map(s => ByteString(s + "\n"))
    .toMat(FileIO.toPath(Paths.get(fileName)))(Keep.right)

  val factorials = source.scan(BigInt(1))((acc, next) => acc * (next + 1))


  val result: Future[IOResult] = factorials
    .map(num => ByteString(s"$num\n"))
    .runWith(FileIO.toPath(Paths.get("factorial.txt")))

  val result2:Future[IOResult] = factorials
    .map(_.toString)
    .runWith(lineSink("factorial2.txt"))

  val result3: Future[Done] = factorials
    .zipWith(source)((num, idx) ⇒ s"$idx! = $num")
    .throttle(1, 1.second, 1, ThrottleMode.shaping)
    .runForeach(println)
  result3.onComplete(_ ⇒ system.terminate)
//  done.onComplete(_ ⇒ system.terminate)

}
