package ru.megains.wod.battle.effect

import ru.megains.wod.entity.Entity

class Giant(entity:Entity,maxHP: Int) extends Effect(entity) {



    override def update = true

    override def use(): Unit = {
       // entity.changeMaxHp(maxHP)
       // entity.changeHp(maxHP)
      //  entity.setDamage(TypeAttack.heal, maxHP, true)
      //  entity.getBattleUser
    }

}
