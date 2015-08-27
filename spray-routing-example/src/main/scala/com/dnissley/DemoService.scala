package com.dnissley

import akka.actor._
import spray.routing.HttpService

class DemoServiceActor extends Actor with DemoService {
  def actorRefFactory = context
  def receive = runRoute(demoRoute)
}

trait DemoService extends HttpService {
  implicit def executionContext = actorRefFactory.dispatcher
  
  val demoRoute = {
    path("ping") {
      get {
        complete("pong")
      }
    }
  }
}

