package com.legopiraat.coexistence

import akka.actor.typed.{ActorSystem => TypedSystem }
import akka.actor.{ActorSystem => UntypedSystem}
import akka.actor.typed.scaladsl.adapter._

object UntypedMain extends App {
  val untypedSystem: UntypedSystem = UntypedSystem("UntypedActorSystem")
  val typedSystem: TypedSystem[Nothing] = untypedSystem.toTyped

  val untypedActor = untypedSystem.actorOf(UntypedActor.props(), "UntypedActor")
}
