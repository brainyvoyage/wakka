package com.brainyvoyage.wakka.sample

import akka.actor.{Actor, ActorLogging, Props}


// Recommended pattern for creating actors by defining a props() in companion object of the Actor
object IotSupervisor {
  def props(): Props = Props(new IotSupervisor)
}

class IotSupervisor extends Actor with ActorLogging {
  override def preStart(): Unit = log.info("IoT Application Started")

  override def postStop(): Unit = log.info("IoT Application Stopped")

  override def receive: Receive = Actor.emptyBehavior
}