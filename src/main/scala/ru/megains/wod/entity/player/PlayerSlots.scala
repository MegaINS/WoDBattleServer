package ru.megains.wod.entity.player

import ru.megains.wod.battle.effect._
import ru.megains.wod.db.{DBPlayerSlot, Database}
import ru.megains.wod.item.{ItemParam, ItemUser, SlotType}
import ru.megains.wod.item.SlotType.SlotType
import ru.megains.wod.network.packet.battleplayer.SPacketSlotUpdate

import scala.collection.mutable

class PlayerSlots(val player: Player) extends Database {

    var openSlots:Int =  DBPlayerSlot.loadOpenSlot(player.id)
    var slotsItem: mutable.HashMap[SlotType, ItemUser] = DBPlayerSlot.load(player.id)


    def use(slotType: SlotType):Unit ={

        slotsItem.get(slotType) match {
            case Some(value) =>
                val effect:Effect = value.slot match {
                    case SlotType.gig =>
                        new Giant(player,value.itemBase.itemParams(ItemParam.hp))
                    case SlotType.heal =>
                        new Heal(player,value.itemBase.itemParams(ItemParam.timeAct),value.itemBase.itemParams(ItemParam.hp))
                    case SlotType.vamp =>
                        new Vamp(player,value.itemBase.itemParams(ItemParam.power),value.itemBase.itemParams(ItemParam.vamp))
                    case SlotType.mosh =>
                        new Power(player,value.itemBase.itemParams(ItemParam.power))
                    case SlotType.krit =>
                        new Crit(player,value.itemBase.itemParams(ItemParam.krit))
                }



                player.addEffect(effect)

                value.amount -=1

                player.sendPacket(new SPacketSlotUpdate(slotType,value.amount))
                if(value.amount == 0){
                    slotsItem -= slotType
                }
            case None =>
        }


    }


}
