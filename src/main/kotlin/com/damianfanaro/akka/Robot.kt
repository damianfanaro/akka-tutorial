package com.damianfanaro.akka

import akka.actor.AbstractActor

class Robot : AbstractActor() {

    private lateinit var direction: Direction
    private var moving = false

    override fun createReceive(): Receive = receiveBuilder()
            .match(Move::class.java, this::onMove)
            .match(Stop::class.java, this::onStop)
            .build()

    private fun onMove(move: Move) {
        moving = true
        direction = move.direction
        println("path[${self.path()}] I am now moving $direction")

        val random = java.util.Random()
        val nextInt = random.nextInt(10)
        if (nextInt % 2 == 0) {
            context.stop(self)
        }
    }

    private fun onStop(stop: Stop) {
        moving = false
        println("I stopped moving")
    }

}

enum class Direction {
    FORWARD, BACKWARDS, RIGHT, LEFT
}

class Stop
class Move(val direction: Direction)
