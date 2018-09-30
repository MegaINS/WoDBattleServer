package ru.megains.wod.battle.effect

import ru.megains.wod.entity.Entity

abstract class Effect( entity: Entity,var rounds: Int = 0) {



    def update: Boolean = {
        use()
        rounds -= 1
        rounds > 0
    }

    def use(): Unit
}
