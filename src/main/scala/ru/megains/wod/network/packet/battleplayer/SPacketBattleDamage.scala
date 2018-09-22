package ru.megains.wod.network.packet.battleplayer

import ru.megains.wod.entity.Entity
import ru.megains.wod.network.packet.{PacketBuffer, PacketWrite}

class SPacketBattleDamage(hit:Int,entity:Entity) extends PacketWrite{



    override def writePacketData(buf: PacketBuffer): Unit = {
        buf.writeInt(hit)
        buf.writeInt(entity.battleId)
        buf.writeByte(entity.team)
    }


}
