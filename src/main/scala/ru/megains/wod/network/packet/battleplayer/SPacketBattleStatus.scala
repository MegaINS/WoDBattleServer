package ru.megains.wod.network.packet.battleplayer


import ru.megains.wod.entity.Entity
import ru.megains.wod.network.packet.{PacketBuffer, PacketWrite}

class SPacketBattleStatus(entity:Entity) extends PacketWrite{





    override def writePacketData(buf: PacketBuffer): Unit = {
        buf.writeByte(entity.turnStatus.id)
    }


}
