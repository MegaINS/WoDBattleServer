package ru.megains.wod.battle

import ru.megains.wod.entity.Entity
import ru.megains.wod.entity.mob.Mobs

import scala.collection.mutable

object BattleList {
    val battleMap = new mutable.HashMap[Int,Battle]()


    def createBattle(id:Int, entity1:Entity,entity2:Entity): Unit ={

        val battle = new Battle(id)
        battle.addEntity(entity1,0)
        battle.addEntity(entity2,1)
        for(_<- Range(0,20000)){
          // battle.addEntity(Mobs.getMob(1),1)
           battle.addEntity(Mobs.getMob(1),0)
        }




        battleMap += id -> battle
        battle.start()

        println(s"Start battle$id")
    }
}
