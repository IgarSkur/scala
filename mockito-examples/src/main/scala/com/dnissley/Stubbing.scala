package com.dnissley

import scala.collection.mutable.Stack
import org.mockito.Mockito._

object Stubbing {
  def runExample(): Unit = {
    println("----------------")
    println("STUBBING EXAMPLE")
    println("----------------")
    println()

    println("Creating a mock of class mutable.Stack[Int]")
    val stack = mock(classOf[Stack[Int]])
    
    println("Stubbing pop() to always return 5")
    when(stack.pop()).thenReturn(5)

    println("Calling pop 7 times...")

    (1 to 7).foreach(_ => println(s"Just popped ${stack.pop()} off the stack"))

    println()
  }
}

