package com.dnissley

import akka.actor.{ ActorSystem, Props }
import akka.io.IO
import spray.can.Http

object Program extends App {
  implicit val system = ActorSystem("on-spray-can")
  val service = system.actorOf(Props[DemoServiceActor], "demo-service")
  IO(Http) ! Http.Bind(service, "localhost", port = 8080)
}

