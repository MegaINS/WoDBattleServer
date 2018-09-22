package ru.megains.wod.entity.mob

import anorm.SQL
import ru.megains.wod.Parsers
import ru.megains.wod.db.Database

import scala.collection.mutable

object Mobs extends Database{


    private val mobsMap = new mutable.HashMap[Int,Mob]()


    def getMob(id:Int): Mob ={



        mobsMap.getOrElse(id,default = null).clone()
    }


    def load(): Unit ={
        withConnection(implicit c=>{
            val mobs = SQL(s"SELECT * FROM mob_info ").as(Parsers.mob *)
            for(mob<-mobs){
                val stats = SQL(s"SELECT * FROM mob_stat WHERE mob_id='${mob.id}' ").as(Parsers.stat *)
                mob.load(stats)
                mobsMap += mob.id -> mob
            }
        })
    }
}
