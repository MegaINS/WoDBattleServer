package ru.megains.wod.network.packet.battleplayer

import ru.megains.wod.network.packet.{Packet, PacketBuffer}

class SPacketBattleTarget(id:Int) extends Packet{



    override def readPacketData(buf: PacketBuffer): Unit = {

    }

    override def writePacketData(buf: PacketBuffer): Unit = {
        buf.writeInt(id)
    }

    override def processPacket(handler: Nothing): Unit = {

    }
}


