package com.legopiraat.helloworld

import akka.actor.typed.{ActorSystem, Behavior}
import akka.actor.typed.scaladsl.Behaviors

object HelloWorldMain extends App {

  case class Start(name: String)

  val main: Behavior[Start] = Behaviors.setup { context =>
    val greeter = context.spawn(HelloWorld.greeter, "Greeter")

    Behaviors.receiveMessage({ msg =>
      val replyTo = context.spawn(HelloWorldBot.bot(greetingCounter = 0, max = 3), msg.name)

      greeter ! HelloWorld.Greet(msg.name, replyTo)
      Behaviors.same
    })
  }

  val system: ActorSystem[Start] = ActorSystem(main, "HelloWorldMain")

  system ! HelloWorldMain.Start("World")
  system ! HelloWorldMain.Start("Akka")
}
