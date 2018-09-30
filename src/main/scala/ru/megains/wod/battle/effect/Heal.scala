package ru.megains.wod.battle.effect

import ru.megains.wod.TypeAttack
import ru.megains.wod.entity.Entity
import ru.megains.wod.network.packet.battleplayer.SPacketBattleDamage

class Heal(entity: Entity,rounds: Int, life: Int) extends Effect(entity,rounds) {



    override def use(): Unit = {

        val rLife = if (entity.hp + life > entity.hpMax)  entity.hpMax - entity.hp else life



        entity.hp += rLife

        entity.battle.sendAll(new SPacketBattleDamage(entity,TypeAttack.heal,rLife))

    }

}
