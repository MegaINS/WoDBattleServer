package ru.megains.wod.network.packet.battle

import ru.megains.wod.network.handler.NetHandlerHandshakeTCP
import ru.megains.wod.network.packet.{ConnectionState, PacketBuffer, PacketRead}

class CHandshake() extends PacketRead[NetHandlerHandshakeTCP] {

    var connectionState: ConnectionState = _

    override def isImportant: Boolean = true

    override def readPacketData(packetBuffer: PacketBuffer): Unit = {
        connectionState = ConnectionState.getFromId(packetBuffer.readByte())
    }

    override def processPacket(handler: NetHandlerHandshakeTCP) {
        handler.processHandshake(this)
    }
}
