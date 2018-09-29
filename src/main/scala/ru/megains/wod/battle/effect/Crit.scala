package ru.megains.wod.battle.effect

import ru.megains.wod.entity.Entity

class Crit(entity:Entity,rounds: Int,crit: Int) extends Effect(entity) {


    override def use(): Unit = {
       // entity.changeModifCrit(crit)
    }

}
