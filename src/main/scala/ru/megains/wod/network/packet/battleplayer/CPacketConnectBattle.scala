package ru.megains.wod.network.packet.battleplayer

import ru.megains.wod.network.handler.NetHandlerBattle
import ru.megains.wod.network.packet.{PacketBuffer, PacketRead}

class CPacketConnectBattle extends PacketRead[NetHandlerBattle]{


    var id:Int = -1
    override def readPacketData(buf: PacketBuffer): Unit = {
        id = buf.readInt()
    }


    override def processPacket(handler: NetHandlerBattle): Unit = {
        handler.processConnectBattle(this)
    }
}
