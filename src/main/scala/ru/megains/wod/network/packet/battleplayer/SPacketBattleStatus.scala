package ru.megains.wod.network.packet.battleplayer


import ru.megains.wod.entity.Entity
import ru.megains.wod.network.packet.{Packet, PacketBuffer}

class SPacketBattleStatus(entity:Entity) extends Packet{



    override def readPacketData(buf: PacketBuffer): Unit = {

    }

    override def writePacketData(buf: PacketBuffer): Unit = {
        buf.writeByte(entity.turnStatus.id)
    }

    override def processPacket(handler: Nothing): Unit = {

    }
}
