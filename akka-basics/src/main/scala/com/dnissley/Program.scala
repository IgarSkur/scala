package com.dnissley

import akka.actor.{ ActorRef, ActorSystem, Props, Actor, Inbox }
import scala.concurrent.duration._
import scala.util.Random

object Program extends App {
  val system = ActorSystem("drunk-actor-system")

  val drunkard: ActorRef = system.actorOf(Props[Drunkard], "drunkard")
  val observer: ActorRef = system.actorOf(Props[Observer], "bystander")

  val inbox: Inbox = Inbox.create(system)

  // if we don't care about the response message
  drunkard.tell(Walk, ActorRef.noSender)
  // or more simply
  drunkard ! Walk

  // if we do care about the response message
  inbox.send(drunkard, Walk)
  val DrunkardData(position, steps) = inbox.receive(2.seconds)
  println(s"Received the drunkard's location: $position")

  // Scheduling a message to be sent repeatedly
  system.scheduler.schedule(0.seconds, 100.milliseconds, drunkard, Walk)(system.dispatcher, observer)
}

