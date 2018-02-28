package ru.megains.wod.network.packet.battle

import ru.megains.wod.entity.EntityType
import ru.megains.wod.entity.EntityType.EntityType
import ru.megains.wod.network.handler.NetHandlerBattleServer
import ru.megains.wod.network.packet.{Packet, PacketBuffer}

class PStartBattle() extends Packet[NetHandlerBattleServer]{

    var id:Int = -1
    var id1:Int = -1
    var id2:Int = -1
    var et1:EntityType = _
    var et2:EntityType = _

    override def readPacketData(buf: PacketBuffer): Unit = {
        id = buf.readInt()
        id1 = buf.readInt()
        id2 = buf.readInt()
        et1 = EntityType(buf.readByte())
        et2 = EntityType(buf.readByte())
    }

    override def writePacketData(buf: PacketBuffer): Unit = {

    }

    override def processPacket(handler: NetHandlerBattleServer): Unit = {
        handler.startNewBattle(this)
    }
}
