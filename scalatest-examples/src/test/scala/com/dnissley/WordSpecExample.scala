package com.dnissley

import org.scalatest._
import scala.collection.mutable.Stack

class WordStackSpec extends WordSpec {
  "A Stack" when {

    "non-empty" should {

      "pop values in last-in-first-out order" in {
        val stack = new Stack[Int]
        stack.push(1)
        stack.push(2)
        assert(stack.pop() === 2)
        assertResult(1) {
          stack.pop()
        }
      }

    }

    "empty" should {

      "throw NoSuchElementException when popped" in {
        val emptyStack = new Stack[String]
        intercept[NoSuchElementException] {
          emptyStack.pop()
        }
      }

      "ignore this test because that is all this test does" ignore {
        fail("execution won't reach this point because the test is ignored")
      }

      "fail this test because that is all this test does" in {
        fail("purposefully failed")
      }

      "cancel this test because its assumptions aren't valid" in {
        assume(false)
      }

    }

  }
}

