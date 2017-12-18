package com.brainyvoyage.wakka.sample

import akka.actor.ActorSystem
import akka.testkit.TestProbe
import org.scalatest.{Matchers, FlatSpec}

class DeviceTest extends FlatSpec with Matchers {
  implicit val system = ActorSystem()
  "reply with latest temperature reading" should "match with request id and set temperature " in {
    val probe = TestProbe()
    val deviceActor = system.actorOf(Device.props("group", "device"))

    deviceActor.tell(Device.RecordTemperature(requestId = 1, 24.0), probe.ref)
    probe.expectMsg(Device.TemperatureRecorded(requestId = 1))

    deviceActor.tell(Device.ReadTemperature(requestId = 2), probe.ref)
    val response1 = probe.expectMsgType[Device.RespondTemperature]
    response1.requestId should ===(2)
    response1.value should ===(Some(24.0))

    deviceActor.tell(Device.RecordTemperature(requestId = 3, 55.0), probe.ref)
    probe.expectMsg(Device.TemperatureRecorded(requestId = 3))

    deviceActor.tell(Device.ReadTemperature(requestId = 4), probe.ref)
    val response2 = probe.expectMsgType[Device.RespondTemperature]
    response2.requestId should ===(4)
    response2.value should ===(Some(55.0))
  }
}
