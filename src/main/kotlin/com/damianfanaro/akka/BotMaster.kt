package com.damianfanaro.akka

import akka.actor.AbstractActor
import akka.actor.Props

class BotMaster : AbstractActor() {

    init {
        for (index in 0..9) {
            context.actorOf(Props.create(AkkaBot::class.java))
        }
    }

    override fun createReceive(): Receive = receiveBuilder()
            .match(StartChildBots::class.java, this::onStartChildBots)
            .match(StopChildBots::class.java, this::onStopChildBots)
            .build()

    private fun onStartChildBots(startChildBots: StartChildBots) {
        val move = Move(Direction.FORWARD)
        for (child in context.children) {
            println("Master started moving $child")
            child.tell(move, self)
        }
    }

    private fun onStopChildBots(stopChildBots: StopChildBots) {
        val stop = Stop()
        for (child in context.children) {
            println("Master started moving $child")
            child.tell(stop, self)
        }
    }

}

class StartChildBots
class StopChildBots