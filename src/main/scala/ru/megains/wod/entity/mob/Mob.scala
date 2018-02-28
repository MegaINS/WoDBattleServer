package ru.megains.wod.entity.mob

import ru.megains.wod.battle.TurnStatus
import ru.megains.wod.entity.{Entity, EntityStat}
import ru.megains.wod.entity.EntityStat.EntityStat

class Mob(val id:Int) extends Entity with Cloneable{



    var roundsPass:Long = 0



    override def update(): Unit = {
        if (target == null) if (!battle.targetSearch(this, team)) return
        turnStatus match {
            case TurnStatus.myTurn =>
                if (roundsPass == 0) roundsPass = (Math.random * 1500).toInt + 500
                else if (System.currentTimeMillis - roundsStartTime > roundsPass) attackTarget(1)
            case _ =>
        }

    }
    def load(stats: List[(EntityStat, Int)]) = {
        stats.foreach{
            case (stat,value) =>
                stat match {
                    case EntityStat.mHp=>
                        hp = value
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

    @throws[CloneNotSupportedException]
    override def clone: Mob = super.clone.asInstanceOf[Mob]
}
