package com.dnissley

import org.scalatest._
import scala.collection.mutable.Stack

class FlatStackSpec extends FlatSpec {
  "A Stack" should "pop values in last-in-first-out order" in {
    val stack = new Stack[Int]
    stack.push(1)
    stack.push(2)
    assert(stack.pop() === 2)
    assertResult(1) {
      stack.pop()
    }
  }

  it should "throw NoSuchElementException if an empty stack is popped" in {
    val emptyStack = new Stack[String]
    intercept[NoSuchElementException] {
      emptyStack.pop()
    }
  }

  ignore should "ignore this test because that is all this test does" in {
    fail("execution won't reach this point because the test is ignored")
  }

  it should "fail this test because that is all this test does" in {
    fail("purposefully failed")
  }

  it should "cancel this test because its assumptions aren't valid" in {
    assume(false)
  }
}

