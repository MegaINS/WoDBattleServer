package ru.megains.wod.battle.effect

import ru.megains.wod.TypeAttack
import ru.megains.wod.entity.Entity
import ru.megains.wod.network.packet.battleplayer.SPacketBattleDamage

class Giant(entity:Entity,maxHP: Int) extends Effect(entity) {



    override def update = true

    override def use(): Unit = {

        entity.hp +=maxHP
        entity.hpMax +=maxHP
        entity.battle.sendAll(new SPacketBattleDamage(entity,TypeAttack.maxHeal,maxHP))

    }

}
