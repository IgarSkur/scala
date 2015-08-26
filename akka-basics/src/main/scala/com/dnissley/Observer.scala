package com.dnissley

import akka.actor.Actor

class Observer extends Actor {
  def receive = {
    case DrunkardData(position, steps) => println(s"The drunkard's location is $position and they have walked $steps steps.")
  }
}

