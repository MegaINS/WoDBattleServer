package ru.megains.wod.battle.effect

import ru.megains.wod.entity.Entity

class Vamp(entity: Entity, power: Int, vamp: Int ) extends Effect(entity) {


    override def use(): Unit = {
        entity.modDam += power
        entity.modVamp += vamp
    }

}
