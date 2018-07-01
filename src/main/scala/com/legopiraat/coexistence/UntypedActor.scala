package com.legopiraat.coexistence

import akka.actor.Props
import akka.actor.typed.ActorRef
import akka.actor.typed.scaladsl.adapter._
import akka.{actor => untyped}

object UntypedActor {
  def props(): Props = Props[UntypedActor]
}

class UntypedActor extends untyped.Actor with untyped.ActorLogging {

  val second: ActorRef[TypedActor.Command] = context.spawn(TypedActor.behavior, "TypedActor")

  second ! TypedActor.Ping(self)

  override def receive: Receive = {
    case TypedActor.Pong =>
      log.info(s"$self got Pong from ${sender()}")
      second ! TypedActor.Ping(self)
  }
}
