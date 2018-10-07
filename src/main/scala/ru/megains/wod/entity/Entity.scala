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

    var modCrit = 0
    var modDam = 0
    var modVamp = 0


    def start(): Unit = {
        if (status == Status.await) {
            status = Status.life
           // battle.authorizationUser(this, team)
            battle.addTarget(this)
        }
       // getBattleUser()
    }

    def load(stats: List[(EntityStat, Int)]): Unit = {
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
                if (Math.random < 0.1) typeAttack = TypeAttack.dodge
                else if (Math.random  < (10 + modCrit)/100.0f) typeAttack = TypeAttack.crit
                else if (Math.random < 0.1) typeAttack = TypeAttack.block
                else typeAttack = TypeAttack.plain


                if ((typeAttack == TypeAttack.plain) || (typeAttack == TypeAttack.crit)) {
                    var hit:Double = ((damageMin + ((damageMax - damageMin) * Math.random)) + (power / 10)) * (1 + modDam/100.0)
                    if (typeAttack == TypeAttack.crit) {
                        hit *= (1.8 + (0.4 * Random.nextFloat()))
                    }
                    // if (isBlock) hit /= 2
                    // if (target.isBlock) hit /= 2
                    if (target.hp - hit <= 0) {
                        hit = target.hp
                        if(modVamp!= 0){
                            var heal:Int = hit *(modVamp/100) toInt

                            heal = if (hp + heal > hpMax)  hpMax - hp else heal

                            hp += heal
                            battle.sendAll(new SPacketBattleDamage(this,TypeAttack.heal,heal))
                        }

                        battle.sendAll(new SPacketBattleDamage(target,typeAttack,-hit.toInt))
                        battle.killEntity(target)

                        battle.addTarget(this)
                    } else {
                        target.hp -= hit.toInt
                        if(modVamp!= 0){
                            var heal:Int = hit *(modVamp/100) toInt

                            heal = if (hp + heal > hpMax)  hpMax - hp else heal

                            hp += heal
                            battle.sendAll(new SPacketBattleDamage(this,TypeAttack.heal,heal))
                        }

                        battle.sendAll(new SPacketBattleDamage(target,typeAttack,-hit.toInt))
                        passRun()
                    }
                }else{
                    battle.sendAll(new SPacketBattleDamage(target,typeAttack,0))
                    passRun()
                }



               // log.info(s"$toString attack ${target.toString}  -$hit hp")
                //  }
                // else setDamage(typeAttack, hit, false)




            case _ =>
        }

    }

    def resetAllModif() = {
        modCrit = 0
        modDam = 0
        modVamp = 0
    }

    def passRun(): Unit = {
        resetAllModif()
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