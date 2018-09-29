package ru.megains.wod.entity

import ru.megains.wod.TypeAttack.TypeAttack
import ru.megains.wod.{Logger, TypeAttack}
import ru.megains.wod.battle.TurnStatus.TurnStatus
import ru.megains.wod.battle.effect.Effect
import ru.megains.wod.battle.{Battle, TurnStatus}
import ru.megains.wod.entity.EntityStat.EntityStat
import ru.megains.wod.entity.Status.Status
import ru.megains.wod.network.handler.INetHandler
import ru.megains.wod.network.packet.Packet
import ru.megains.wod.network.packet.battleplayer.{SPacketBattleDamage, SPacketBattleStatus}

import scala.collection.mutable.ArrayBuffer
import scala.util.Random


abstract class Entity extends Logger[Entity] {
    def sendPacket(packetIn: Packet[_ <: INetHandler]): Unit = {}


    var status: Status = Status.await
    var team:Int = -1
    var battle:Battle =_
    var target:Entity = _
    var rounds: Int = 0
    var roundsTime: Int = 0
    var missedRounds: Int = 0
    var damageMin:Int = 0
    var damageMax:Int = 0
    var hp = 0
    var hpMax = 0
    var battleId:Int = -1
    var name:String =""
    var level:Int = 0
    var power:Int = 0
    var turnStatus:TurnStatus = TurnStatus.noTarget
    var typeAttack:TypeAttack = TypeAttack.plain
    var roundsTimeConst = 10

    var roundsStartTime:Long = 0

    val effects:ArrayBuffer[Effect] = new ArrayBuffer[Effect]()



    def start(): Unit = {
        if (status == Status.await) {
            status = Status.life
           // battle.authorizationUser(this, team)
            battle.addTarget(this)
        }
       // getBattleUser()
    }

    def load(stats: List[(EntityStat, Int)]) = {
        stats.foreach{
            case (stat,value) =>
                stat match {
                    case EntityStat.hp=>
                        hp = value
                    case EntityStat.hpMax=>
                        hpMax = value
                    case EntityStat.pow=>
                        power = value
                    case EntityStat.maxDam=>
                        damageMax = value
                    case EntityStat.minDam=>
                        damageMin = value
                    case _ =>
                }

        }

    }
    def update():Unit

    def attackTarget(blow: Int): Unit = {
       // var typeAttack = null
        turnStatus match {
            case TurnStatus.myTurn=>
                missedRounds = 0
                if (Math.random < 0.5) typeAttack = TypeAttack.dodge
                else if (Math.random * 100 < 50 ) typeAttack = TypeAttack.crit
                else if (Math.random < 0.5) typeAttack = TypeAttack.block
                else typeAttack = TypeAttack.plain

                var hit = 0.0
                if ((typeAttack == TypeAttack.plain) || (typeAttack == TypeAttack.crit)) {
                    hit = (damageMin + ((damageMax - damageMin) * Math.random)) + (power / 10)

                    /* ((modifDamage / 100) + 1)  + power*/
                    if (typeAttack == TypeAttack.crit) {
                        hit *= (1.8 + (0.4 * Random.nextFloat()))
                    }
                    // if (isBlock) hit /= 2
                    // if (target.isBlock) hit /= 2
                    if (target.hp - hit <= 0) {
                        hit = target.hp
                        battle.sendAll(new SPacketBattleDamage(target,typeAttack,-hit.toInt))
                         //   setDamage(typeAttack, hit, false)
                         //  target.getBattleUser()
                        battle.killEntity(target)
                         // setKilledEntity(target)
                        battle.addTarget(this)
                    } else {
                        //setDamage(typeAttack, hit, false)
                        target.hp -= hit.toInt
                        battle.sendAll(new SPacketBattleDamage(target,typeAttack,-hit.toInt))
                        passRun()
                    }
                }else{
                    battle.sendAll(new SPacketBattleDamage(target,typeAttack,-hit.toInt))
                    passRun()
                }



               // log.info(s"$toString attack ${target.toString}  -$hit hp")
                //  }
                // else setDamage(typeAttack, hit, false)




            case _ =>
        }

    }

    def passRun(): Unit = {
      //  resetAllModif()
        if (rounds == 0) {
            battle.addTarget(this, target)
           // target.getBattleUser()
           // getBattleUser()
        }
        else {
            turnStatus = TurnStatus.targetTurn
            target.turnStatus = TurnStatus.myTurn
          //  target.updateEffects()
            target.roundsTime = roundsTimeConst
            target.roundsStartTime = System.currentTimeMillis()
            target.rounds -=1
            sendPacket(new SPacketBattleStatus(this))
            target.sendPacket(new SPacketBattleStatus(target))
           // target.getBattleUser()
            //getBattleUser()
        }
    }




}


object Status extends Enumeration{
    type Status = Value
    val await,life,end = Value

}