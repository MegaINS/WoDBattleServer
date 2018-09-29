package ru.megains.wod.battle.effect

import ru.megains.wod.entity.Entity

class Heal(entity: Entity,rounds: Int, life: Int) extends Effect(entity,rounds) {



    override def use(): Unit = {
       // var life = this.life
      //  if (entity.getHp + life > entity.getMaxHP) life = entity.getMaxHP - entity.getHp
       // entity.changeHp(life)
       // entity.setDamage(TypeAttack.heal, life, true)
       // entity.getBattleUser
    }

}
