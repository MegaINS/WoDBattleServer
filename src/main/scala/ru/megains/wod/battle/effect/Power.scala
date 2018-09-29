package ru.megains.wod.battle.effect

import ru.megains.wod.entity.Entity

class Power(entity: Entity, rounds: Int,power: Int) extends Effect(entity) {


    override def use(): Unit = {
       // entity.changeModifDamage(power)
    }

}
