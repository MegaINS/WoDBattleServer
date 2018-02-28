package ru.megains.wod.network.packet.battleplayer

import ru.megains.wod.network.packet.{Packet, PacketBuffer}

class SPacketBattleEnd(win:Boolean) extends Packet{


    override def readPacketData(buf: PacketBuffer): Unit = {

    }

    override def writePacketData(buf: PacketBuffer): Unit = {
        buf.writeBoolean(win)

    }

    override def processPacket(handler: Nothing): Unit = {

    }
}
