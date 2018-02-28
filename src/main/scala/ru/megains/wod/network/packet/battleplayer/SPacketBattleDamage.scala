package ru.megains.wod.network.packet.battleplayer

import ru.megains.wod.entity.Entity
import ru.megains.wod.network.packet.{Packet, PacketBuffer}

class SPacketBattleDamage(hit:Int,entity:Entity) extends Packet{



    override def readPacketData(buf: PacketBuffer): Unit = {

    }

    override def writePacketData(buf: PacketBuffer): Unit = {
        buf.writeInt(hit)
        buf.writeInt(entity.battleId)
        buf.writeByte(entity.team)
    }

    override def processPacket(handler: Nothing): Unit = {

    }
}
