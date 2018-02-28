package ru.megains.wod.network.packet.battleplayer

import ru.megains.wod.network.packet.{Packet, PacketBuffer}

class SPacketBattleInfo(battleId:Int,team:Int) extends Packet{


    override def readPacketData(buf: PacketBuffer): Unit = {

    }

    override def writePacketData(buf: PacketBuffer): Unit = {
        buf.writeInt(battleId)
        buf.writeInt(team)
    }

    override def processPacket(handler: Nothing): Unit = {

    }
}
