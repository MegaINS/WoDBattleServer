package ru.megains.wod.battle

import ru.megains.wod.Logger
import ru.megains.wod.entity.mob.Mob
import ru.megains.wod.entity.player.{Player, Players}
import ru.megains.wod.entity.{Entity, Status}
import ru.megains.wod.network.handler.INetHandler
import ru.megains.wod.network.packet.Packet
import ru.megains.wod.network.packet.battleplayer.{SPacketBattleEnd, SPacketBattleStatus, SPacketBattleTarget}

import scala.collection.mutable.ArrayBuffer
import scala.util.Random


class Battle(val id:Int) extends Thread with Logger[Battle]{



    var isActive = true
    val teams = Array( ArrayBuffer[Entity](), ArrayBuffer[Entity]())
    val teamsNoTarget = Array( ArrayBuffer[Entity](), ArrayBuffer[Entity]())
    val teamsLife = Array(0,0)
    var battleId = 0

    def addEntity(entity: Entity,team:Int): Unit ={
        entity.team = team
        entity.battle = this
        teams(team) += entity
        teamsLife(team)+=1
        entity.battleId = battleId
        battleId+=1
        if(entity.isInstanceOf[Mob]){
            entity.start()
        }
    }





    override def run(): Unit = {

        var start:Long = 0
        var sum:Long =0
        var i:Int = 0
        while (isActive){
            start = System.currentTimeMillis()
           // log.info("Start round")
            for(team<- teams){
                team.filter(_.status!=Status.end).foreach(_.update())
            }

            if(teamsLife(0)==0||teamsLife(1)==0){
                isActive = false
            }
            sum += System.currentTimeMillis() - start
            i+=1
            if(i == 100){
                i = 0
                       // log.info(s"sum 100  = $sum, ${sum/100}")
                sum = 0
            }
          //  log.info("End round")
            Thread.sleep(1)
        }

      endBattle()
    }


    def endBattle(): Unit ={



        for(i<-teams.indices){
            val win: Boolean = teamsLife(i) != 0
            teams(i).foreach(e=>
                    e.sendPacket(new SPacketBattleEnd(win))
            )
        }




        teams.foreach(team=> team.foreach {
            case e: Player => Players.delete(e.id)
            case _ =>
        })

        println("Battle the end")
    }


    def getEntityNoTarget(team: Int): Entity = {
        if(teamsNoTarget(team).isEmpty){
            null
        }else{
            teamsNoTarget(team)(Random.nextInt(teamsNoTarget(team).length))
        }
    }

    def setTurn(entity: Entity, target: Entity): Unit = {

        entity.turnStatus = TurnStatus(entity.team)
        target.turnStatus = TurnStatus(target.team)

        if(entity.turnStatus == TurnStatus.myTurn) {
            entity.roundsStartTime = System.currentTimeMillis
        }else{
            target.roundsStartTime = System.currentTimeMillis
        }
        entity.rounds = 2
        entity.roundsTime = entity.roundsTimeConst
        target.rounds = 2
        target.roundsTime = entity.roundsTimeConst

        entity.sendPacket(new SPacketBattleStatus(entity))
        target.sendPacket(new SPacketBattleStatus(target))
    }

    def targetSearch(entity: Entity, team: Int):Boolean = {
        // Logs.setLogs("Ищу противника "+player.name+" "+player.team);
        val teamTarget = if(team == 0) 1 else 0
        var target:Entity = null
        if(teamsLife(teamTarget) == 0) return false
        target = getEntityNoTarget(teamTarget)
        if (target != null) {
            teamsNoTarget(team) -= entity
            teamsNoTarget(teamTarget) -= target
            target.target = entity
            entity.target = target
            setTurn(entity, target)
            target.sendPacket(new SPacketBattleTarget(entity.battleId))
            entity.sendPacket(new SPacketBattleTarget(target.battleId))
            true
        }else{


            false
        }
    }

    def addTarget(entitys: Entity* ):Unit = {
        for(entity<- entitys){
            teamsNoTarget(entity.team) += entity
            entity.target = null
            entity.turnStatus = TurnStatus.noTarget
            entity.sendPacket(new SPacketBattleStatus(entity))
        }

    }

    def sendAll(packetIn: Packet[_ <: INetHandler]): Unit ={
        teams.foreach(team=> team.foreach(e=>e.sendPacket(packetIn)))
    }


    def killEntity(entity: Entity): Unit = {
        entity.hp = 0
        entity.status = Status.end
        entity.turnStatus = TurnStatus.dead
        log.info(entity.name + " = died")
        teamsLife(entity.team)-=1
        entity.sendPacket(new SPacketBattleStatus(entity))
    }
}
