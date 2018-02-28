package ru.megains.wod.entity

import ru.megains.wod.Logger
import ru.megains.wod.battle.TurnStatus.TurnStatus
import ru.megains.wod.battle.{Battle, TurnStatus}
import ru.megains.wod.entity.Status.Status
import ru.megains.wod.network.handler.INetHandler
import ru.megains.wod.network.packet.Packet
import ru.megains.wod.network.packet.battleplayer.{SPacketBattleDamage, SPacketBattleStatus}


abstract class Entity extends Logger[Entity] {
    def sendPacket(packetIn: Packet[_ <: INetHandler]): Unit = {}


    var status: Status = Status.await
    var team:Int = -1
    var battle:Battle =_
   // var myTurn:Boolean = false
    var target:Entity = _
    var rounds: Int = 0
    var roundsTime: Int = 0
    var missedRounds: Int = 0
    var damageMin:Int = 10
    var damageMax:Int = 20
    var hp = 100
    var battleId:Int = -1
    var name:String =""
    var level:Int = 0
    var power:Int = 0
    var turnStatus:TurnStatus = TurnStatus.noTarget

    var roundsTimeConst = 10

    var roundsStartTime:Long = 0

    def start(): Unit = {
        if (status eq Status.await) {
            status = Status.life
           // battle.authorizationUser(this, team)
            battle.addTarget(this)
        }
       // getBattleUser()
    }

    def update():Unit

    def attackTarget(blow: Int): Unit = {
       // var typeAttack = null
        turnStatus match {
            case TurnStatus.myTurn=>
                missedRounds = 0
                // if (Math.random < 0.5) typeAttack = TypeAttack.dodge
                // else if (Math.random * 100 < 50 + modifCrit) typeAttack = TypeAttack.crit
                //  else if (Math.random < 0.5) typeAttack = TypeAttack.block
                // else typeAttack = TypeAttack.plain
                var hit = 0
                // if ((typeAttack eq TypeAttack.plain) || (typeAttack eq TypeAttack.crit)) {
                hit = ((damageMin + ((damageMax - damageMin) * Math.random)) + (power/10)) .toInt /* ((modifDamage / 100) + 1)  + power*/
                // if (typeAttack eq TypeAttack.crit) hit *= (1.8 + (0.4 * Math.random))
                // if (isBlock) hit /= 2
                // if (target.isBlock) hit /= 2
                if (target.hp - hit <= 0) {
                    hit = target.hp
                    battle.sendAll(new SPacketBattleDamage(-hit,target))
                    //   setDamage(typeAttack, hit, false)
                    //  target.getBattleUser()
                    battle.killEntity(target)
                    // setKilledEntity(target)
                    battle.addTarget(this)

                    return
                }
                else {
                    //setDamage(typeAttack, hit, false)
                    target.hp -= hit
                }
                battle.sendAll(new SPacketBattleDamage(-hit,target))
               // log.info(s"$toString attack ${target.toString}  -$hit hp")
                //  }
                // else setDamage(typeAttack, hit, false)
                passRun()



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