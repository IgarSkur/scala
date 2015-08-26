package com.dnissley

import akka.actor.Actor
import scala.util.Random

class Drunkard extends Actor {
  var steps = 0
  var position = 0

  def walk: Unit = {
    if (Random.nextBoolean())
      position += 1
    else
      position -= 1

    steps += 1
  }

  def receive = {
    case Walk => {
      walk
      sender ! DrunkardData(position, steps)
    }
    case GetData => sender ! DrunkardData(position, steps)
  }
}
