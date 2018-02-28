package ru.megains.wod

import anorm.SqlParser.get
import anorm.{RowParser, ~}
import ru.megains.wod.entity.EntityStat
import ru.megains.wod.entity.EntityStat.EntityStat
import ru.megains.wod.entity.mob.Mob
import ru.megains.wod.entity.player.PlayerInfo

object Parsers {

//    val itemBase: RowParser[Item] = {
//        get[Int]("id") ~
//        get[String]("name") ~
//        get[String]("img") ~
//        get[Int]("tupe") ~
//        get[Int]("level") ~
//        get[Int]("cost") ~
//        get[Boolean]("weight") ~
//        get[Boolean]("privat")~
//        get[String]("action")~
//        get[String]("slot")~
//        get[Boolean]("stack")map{
//            case id~name~img~tupe~level~cost~weight~privat~action~slot~stack => new Item(id,name,img,tupe,level,cost,weight,privat,ItemAction.withName(action),BodySlot.withName(slot),stack )
//        }
//    }

//    val itemUser: RowParser[ItemUser]={
//        get[Int]("id")~
//        get[Int]("item_base")~
//        get[String]("place")~
//        get[Int]("amount")map{
//            case id~baseId~plase~amount => new ItemUser(id,baseId,plase,amount)
//        }
//    }







    val userAuth: RowParser[(Int, String, String)] = {
        get[Int]("id") ~
                get[String]("email") ~
                get[String]("password") map{
            case id~mail~password => (id,mail,password)
        }
    }

    val userInfo: RowParser[PlayerInfo] = {
        get[Int]("id") ~
        get[String]("nick") ~
        get[Int]("level") ~
        get[Int]("exp") ~
        get[Int]("location") ~
        get[Int]("money") ~
        get[Int]("battle") map{
            case id~nick~level~exp~loc~money~battle =>new PlayerInfo(id,nick,level,exp,loc,money,battle)
        }
    }

    val mob: RowParser[Mob] = {
        get[Int]("id") ~
        get[String]("name") ~
        get[Int]("level")map{
            case id~name~level =>
                val mob = new Mob(id)
                mob.level = level
                mob.name = name
                mob
        }
    }

    val stat:RowParser[(EntityStat,Int)] ={
        get[String]("stat") ~
        get[Int]("value")map{
            case stat~value =>(EntityStat.withName(stat) ,value)

        }


    }






}
