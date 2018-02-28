package ru.megains.wod.network.packet.battleplayer

import ru.megains.wod.network.handler.NetHandlerBattle
import ru.megains.wod.network.packet.{Packet, PacketBuffer}

class CPacketConnectBattle extends Packet[NetHandlerBattle]{


    var id:Int = -1
    override def readPacketData(buf: PacketBuffer): Unit = {
        id = buf.readInt()
    }

    override def writePacketData(buf: PacketBuffer): Unit = {

    }

    override def processPacket(handler: NetHandlerBattle): Unit = {
        handler.processConnectBattle(this)
    }
}
