package ru.megains.wod.network.packet.battleplayer

import ru.megains.wod.item.SlotType
import ru.megains.wod.item.SlotType.SlotType
import ru.megains.wod.network.handler.NetHandlerBattle
import ru.megains.wod.network.packet.{PacketBuffer, PacketRead}

class SPacketSlotUse extends PacketRead[NetHandlerBattle] {



    var slot: SlotType = _

    override def readPacketData(buf: PacketBuffer): Unit = {
        slot = SlotType(buf.readInt())
    }


    override def processPacket(handler: NetHandlerBattle): Unit = {
        handler.processSlotUse(this)
    }
}
