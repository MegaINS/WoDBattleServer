package ru.megains.wod.entity.player

import anorm.SQL
import ru.megains.wod.Parsers
import ru.megains.wod.db.WoDDatabase

import scala.collection.mutable

object Players {
    val db = WoDDatabase.db
    private val playersMap = new mutable.HashMap[Int,Player]()


    def getPlayer(id:Int): Player ={

        playersMap.getOrElseUpdate(id,load(id))

    }


    def load(id:Int): Player ={
        db.withConnection(implicit c=>{
            val playerInfo = SQL(s"SELECT * FROM users_info WHERE id='$id' ").as(Parsers.userInfo.single)
            new Player(id).load(playerInfo)
        })
    }
    def delete(id:Int): Unit ={
        playersMap -= id
    }
}
