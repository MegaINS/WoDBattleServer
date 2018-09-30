package ru.megains.wod

import anorm.SqlParser.get
import anorm.{RowParser, ~}
import ru.megains.wod.entity.EntityStat
import ru.megains.wod.entity.EntityStat.EntityStat
import ru.megains.wod.entity.mob.Mob
import ru.megains.wod.entity.player.{PlayerInfo}
import ru.megains.wod.item._
import ru.megains.wod.item.ItemParam.ItemParam
import ru.megains.wod.item.SlotType.SlotType

import scala.collection.immutable.HashMap

object Parsers {

    val itemParam : RowParser[(ItemParam,Int)] = {
        get[String]("param") ~
                get[Int]("value")  map{
            case param~value =>(ItemParam.withName(param) ,value)
        }
    }

    var playerStat:RowParser[(EntityStat,Int)] ={
        get[String]("stat") ~
                get[Int]("value")map{
            case stat~value =>(EntityStat.withName(stat) ,value)
        }


    }

    var loc_object : RowParser[(Int,Int,String,Int)] = {
        get[Int]("id") ~
                get[Int]("loc_id") ~
                get[String]("object_type") ~
                get[Int]("object_id") map{
            case id~locId~objectType~objectId =>(id,locId,objectType,objectId)
        }
    }

    var loc_store: RowParser[(Int,Int,Int)] = {
        get[Int]("id") ~
                get[Int]("loc_id") ~
                get[Int]("store_id") map{
            case id~id1~id2 =>(id,id1,id2)
        }
    }

    val storeStoreTab: RowParser[(Int,Int)] = {
        get[Int]("store_id") ~
                get[Int]("store_tab_id")  map{
            case storeId~tabId =>(storeId,tabId)
        }
    }

    var storeTabItemBase : RowParser[(Int,Int)] = {
        get[Int]("store_tab_id") ~
                get[Int]("item_base_id")  map{
            case tabId~iremId =>(tabId,iremId)
        }
    }

    val loc_loc: RowParser[(Int,Int,Int)] = {
        get[Int]("id") ~
                get[Int]("loc_id_1") ~
                get[Int]("loc_id_2") map{
            case id~id1~id2 =>(id,id1,id2)
        }
    }


    val itemBase: RowParser[ItemBase] = {
        get[Int]("id") ~
                get[String]("name") ~
                get[String]("img") ~
                get[Int]("level") ~
                get[Int]("cost") ~
                get[Boolean]("weight") ~
                get[Boolean]("private")~
                get[String]("action")~
                get[String]("slot")~
                get[Boolean]("stack")map{
            case id~name~img~level~cost~weight~privat~action~slot~stack => new ItemBase(id,name,img,level,cost,weight,privat,ItemAction.withName(action),SlotType.withName(slot),stack )
        }
    }

    val itemUser: RowParser[ItemUser]={
        get[Int]("id")~
                get[Int]("item_id")~
                get[Int]("amount")map{
            case id~baseId~amount => new ItemUser(id,baseId,amount)
        }
    }







    val playerAuth: RowParser[(Int, String, String)] = {
        get[Int]("id") ~
                get[String]("email") ~
                get[String]("password") map{
            case id~mail~password => (id,mail,password)
        }
    }

//    val playerInfo: RowParser[PlayerInfo] = {
//        get[Int]("id") ~
//        get[String]("nick") ~
//        get[Int]("level") ~
//        get[Int]("exp") ~
//        get[Int]("location") ~
//        get[Int]("money") ~
//        get[Int]("battle") map{
//            case id~nick~level~exp~loc~money~battle =>new PlayerInfo(id,nick,level,exp,loc,money,battle)
//        }
//    }
    val playerInfo: RowParser[PlayerInfo] = {
        get[Int]("id") ~
                get[Int]("level") ~
                get[Int]("exp") ~
                get[Int]("location") ~
                get[Int]("money") map{
            case id~level~exp~loc~money =>new PlayerInfo(id,level,exp,loc,money)
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

    val playerSlot: RowParser[Map[SlotType, Int]] ={

        get[Int]("slot_1") ~
                get[Int]("slot_2") ~
                get[Int]("slot_3") ~
                get[Int]("slot_4") ~
                get[Int]("slot_5") ~
                get[Int]("slot_6") ~
                get[Int]("slot_7") ~
                get[Int]("slot_8") ~
                get[Int]("slot_9") ~
                get[Int]("slot_10") map{

            case  item1~item2~item3~item4~item5~item6~item7~item8~item9~item10 =>

                HashMap[SlotType,Int](
                    SlotType.elixir1 ->item1,
                    SlotType.elixir2 ->item2,
                    SlotType.elixir3 ->item3,
                    SlotType.elixir4 ->item4,
                    SlotType.elixir5 ->item5,
                    SlotType.elixir6 ->item6,
                    SlotType.elixir7 ->item7,
                    SlotType.elixir8 ->item8,
                    SlotType.elixir9 ->item9,
                    SlotType.elixir10 ->item10
                )
        }


    }




}
