package com.damianfanaro.akka

import akka.actor.AbstractActor
import akka.actor.Props
import akka.actor.Terminated

class RobotMaster : AbstractActor() {

    init {
        for (index in 0..9) {
            val child = context.actorOf(Props.create(Robot::class.java))
            context.watch(child)
        }
    }

    override fun createReceive(): Receive = receiveBuilder()
            .match(StartChildRobots::class.java, this::onStartChildBots)
            .match(StopChildRobots::class.java, this::onStopChildBots)
            .match(Terminated::class.java, this::onChildTerminated)
            .build()

    private fun onStartChildBots(startChildRobots: StartChildRobots) {
        val move = Move(Direction.FORWARD)
        for (child in context.children) {
            println("Master started moving $child")
            child.tell(move, self)
        }
    }

    private fun onStopChildBots(stopChildRobots: StopChildRobots) {
        val stop = Stop()
        for (child in context.children) {
            println("Master started moving $child")
            child.tell(stop, self)
        }
    }

    private fun onChildTerminated(terminated: Terminated) {
        println("Child has stopped, starting a new one")
        val child = context.actorOf(Props.create(Robot::class.java))
        context.watch(child)
    }

}

class StartChildRobots
class StopChildRobots