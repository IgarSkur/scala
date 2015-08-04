package com.dnissley

import scala.collection.mutable.Stack
import org.mockito.Mockito._

object Verification {
  def runExample(): Unit = {
    println("--------------------")
    println("VERIFICATION EXAMPLE")
    println("--------------------")
    println()

    println("Creating a mock of class mutable.Stack[Int]")
    val stack = mock(classOf[Stack[Int]])

    def push(i: Int): Unit = {
      println(s"Pushing $i onto the mock stack")
      stack.push(i)
    }

    def pop(): Unit = {
      println(s"Just popped ${stack.pop()} off the mock stack")
    }

    push(1)

    val pushedOne: Boolean = 
      try {
        verify(stack).push(1)
        true
      }
      catch {
        case e: Throwable => false
      }

    val neverPushedNine: Boolean = 
      try {
        verify(stack, never).push(9)
        true
      }
      catch {
        case e: Throwable => false
      }

    println(s"Is it true we pushed 1 onto the stack? $pushedOne")
    println(s"Is it true we pushed 9 onto the stack? ${!neverPushedNine}")

    println()
  }
}

