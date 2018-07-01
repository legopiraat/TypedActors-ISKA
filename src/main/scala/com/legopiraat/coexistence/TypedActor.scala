package com.legopiraat.coexistence

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior, SupervisorStrategy}

import scala.util.Random

object TypedActor {

  sealed trait Command
  case class Ping(replyTo: ActorRef[Pong.type]) extends Command
  case object Pong

  case object NumberFiveException extends Exception

  val randomNumberGenerator = new Random()

  val behavior: Behavior[Command] = Behaviors.receive { (ctx, msg) =>
    msg match {
      case Ping(replyTo) =>
        val randomNumber = randomNumberGenerator.nextInt(6)
        if (randomNumber == 5) {
          ctx.log.info("Number 5 found throwing NumberFiveException")
          throw NumberFiveException
        } else {
          ctx.log.info(s"${ctx.self} got Ping from $replyTo")

          replyTo ! Pong
          Behaviors.same
        }
    }
  }

  Behaviors.supervise(Behaviors.supervise(behavior)
    .onFailure[NumberFiveException.type](SupervisorStrategy.stop))

}
