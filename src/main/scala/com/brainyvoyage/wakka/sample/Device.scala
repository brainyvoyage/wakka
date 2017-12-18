package com.brainyvoyage.wakka.sample

import akka.actor.{Actor, ActorLogging, Props}

class Device(groupId: String, deviceId: String) extends Actor with ActorLogging {

  import Device._

  var lastTemperatureReading: Option[Double] = None

  override def preStart(): Unit = log.info("Device actor {}-{} started", groupId, deviceId)

  override def postStop(): Unit = log.info("Device actor {}-{} stopped", groupId, deviceId)

  override def receive: PartialFunction[Any, Unit] = {
    case ReadTemperature(id) =>
      sender() ! RespondTemperature(id, lastTemperatureReading)
    case RecordTemperature(id, value) =>
      log.info("Recorded temperature reading {} with {}", value, id)
      lastTemperatureReading = Some(value)
      sender() ! TemperatureRecorded(id)
  }
}

object Device extends {
  def props(groupID: String, deviceId: String): Props = Props(new Device(groupID, deviceId))

  final case class ReadTemperature(requestId: Long)

  final case class RespondTemperature(requestId: Long, value: Option[Double])

  final case class RecordTemperature(requestId: Long, value: Double)

  final case class TemperatureRecorded(requestId: Long)

}
