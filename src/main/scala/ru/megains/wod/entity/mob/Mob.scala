package ru.megains.wod.entity.mob

import ru.megains.wod.battle.TurnStatus
import ru.megains.wod.entity.Entity

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


    @throws[CloneNotSupportedException]
    override def clone: Mob = super.clone.asInstanceOf[Mob]
}
