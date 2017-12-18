package com.brainyvoyage.wakka.sample

import akka.actor.ActorSystem
import scala.io.StdIn

object IotApp extends App {


  val system = ActorSystem("iot-system")

  try {
    // Create top level supervisor
    val supervisor = system.actorOf(IotSupervisor.props(), "iot-supervisor")
    // Exit the system after ENTER is pressed
    StdIn.readLine()
  } finally {
    system.terminate()
  }
}