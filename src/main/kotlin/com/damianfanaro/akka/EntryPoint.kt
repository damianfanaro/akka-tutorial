package com.damianfanaro.akka

import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props

fun main() {

    val system = ActorSystem.create("robot-system")

    val masterRobot = system.actorOf(Props.create(RobotMaster::class.java), "master.robot")

    masterRobot.tell(StartChildRobots(), ActorRef.noSender())

    Thread.sleep(3000)

    masterRobot.tell(StopChildRobots(), ActorRef.noSender())

    Thread.sleep(3000)

    system.terminate()

}