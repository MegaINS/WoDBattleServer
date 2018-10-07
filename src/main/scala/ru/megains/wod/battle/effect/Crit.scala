package ru.megains.wod.battle.effect

import ru.megains.wod.entity.Entity

class Crit(entity:Entity,crit: Int) extends Effect(entity) {


    override def use(): Unit = {
        entity.modCrit += crit
    }

}
