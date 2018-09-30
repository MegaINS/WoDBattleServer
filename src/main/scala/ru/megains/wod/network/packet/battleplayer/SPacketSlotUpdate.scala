package ru.megains.wod.network.packet.battleplayer

import ru.megains.wod.item.SlotType.SlotType
import ru.megains.wod.network.packet.{PacketBuffer, PacketWrite}

class SPacketSlotUpdate(slotType: SlotType, value:Int) extends PacketWrite{


    override def writePacketData(buf: PacketBuffer): Unit = {
        buf.writeInt(slotType.id)
        buf.writeInt(value)
    }
}
