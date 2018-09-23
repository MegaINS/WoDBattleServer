package ru.megains.wod.entity.player

import anorm.SQL
import ru.megains.wod.Parsers
import ru.megains.wod.db.{DBPlayerStat, Database}

import scala.collection.mutable

object Players  extends Database {

    private val playersMap = new mutable.HashMap[Int,Player]()


    def getPlayer(id:Int): Player ={

        playersMap.getOrElseUpdate(id,load(id))

    }


    def load(id:Int): Player ={
        withConnection(implicit c=>{
            val playerInfo = SQL(s"SELECT * FROM player_info WHERE id='$id' ").as(Parsers.playerInfo.single)
            val stat = DBPlayerStat.load(id)
            val player = new Player(id)
            player.load(playerInfo)
            player.load(stat)
            player
        })
    }
    def delete(id:Int): Unit ={
        playersMap -= id
    }
}
