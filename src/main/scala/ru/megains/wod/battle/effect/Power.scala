package ru.megains.wod.battle.effect

import ru.megains.wod.entity.Entity

class Power(entity: Entity,power: Int) extends Effect(entity) {


    override def use(): Unit = {
       // entity.changeModifDamage(power)
    }

}
