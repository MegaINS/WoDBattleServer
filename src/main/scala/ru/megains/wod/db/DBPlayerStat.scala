package ru.megains.wod.db

import anorm.SQL
import ru.megains.wod.Parsers
import ru.megains.wod.entity.EntityStat.EntityStat

object DBPlayerStat extends Database {


    def load(id:Int): List[(EntityStat,Int)] ={

        withConnection(implicit c=>{
            SQL(s"SELECT * FROM player_stat WHERE player_id='$id' ").as(Parsers.stat *)
        })

    }
}
