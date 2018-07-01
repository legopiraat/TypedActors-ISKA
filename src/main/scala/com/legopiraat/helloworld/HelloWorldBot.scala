package com.legopiraat.helloworld

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors

object HelloWorldBot {

  def bot(greetingCounter: Int, max: Int): Behavior[HelloWorld.Greeted] =
    Behaviors.receive { (ctx, msg) =>
      val greeted = greetingCounter + 1
      ctx.log.info("Greeting {} for {}", greeted, msg.whom)

      if (greeted == max) {
        Behaviors.stopped
      } else {
        msg.from ! HelloWorld.Greet(msg.whom, ctx.self)
        bot(greeted, max)
      }
    }
}
