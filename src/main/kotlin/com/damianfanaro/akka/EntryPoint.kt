package com.damianfanaro.akka

import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props

fun main() {

    val system = ActorSystem.create("bot-system")

    val masterBot = system.actorOf(Props.create(BotMaster::class.java), "master.bot")

    masterBot.tell(StartChildBots(), ActorRef.noSender())

    Thread.sleep(3000)

    masterBot.tell(StopChildBots(), ActorRef.noSender())

    Thread.sleep(3000)

    system.terminate()

}