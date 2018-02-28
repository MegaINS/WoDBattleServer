package ru.megains.wod.network.packet.battle

import ru.megains.wod.network.packet.{Packet, PacketBuffer}

class PBattleStatus(id:Int,status:Int) extends Packet{



    override def readPacketData(buf: PacketBuffer): Unit = {

    }

    override def writePacketData(buf: PacketBuffer): Unit = {
        buf.writeInt(id)
        buf.writeByte(status)
    }

    override def processPacket(handler: Nothing): Unit = {

    }
}
