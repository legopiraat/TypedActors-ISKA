package com.legopiraat.helloworld

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}

object HelloWorld {

  case class Greet(whom: String, replyTo: ActorRef[Greeted])
  case class Greeted(whom: String, from: ActorRef[Greet])

  val greeter: Behavior[Greet] = Behaviors.receive { (ctx, msg) =>
    ctx.log.info("Hello {}!", msg.whom)
    msg.replyTo ! Greeted(msg.whom, ctx.self)
    Behaviors.same
  }
}
