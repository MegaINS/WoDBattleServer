package ru.megains.wod.network.packet.battleplayer

import ru.megains.wod.entity.Entity
import ru.megains.wod.network.packet.{Packet, PacketBuffer}

import scala.collection.mutable.ArrayBuffer

class SPacketLoadEntity(teams:Array[ArrayBuffer[Entity]]) extends Packet{


    override def readPacketData(buf: PacketBuffer): Unit = {

    }

    override def writePacketData(buf: PacketBuffer): Unit = {
        buf.writeShort(teams(0).length)
        teams(0).foreach(e=>{
            buf.writeInt(e.battleId)
            buf.writeStringToBuffer(e.name)
            buf.writeInt(e.level)
            buf.writeInt(e.hp)
            buf.writeInt(e.status.id)


        })
        buf.writeShort(teams(1).length)
        teams(1).foreach(e=>{
            buf.writeInt(e.battleId)
            buf.writeStringToBuffer(e.name)
            buf.writeInt(e.level)
            buf.writeInt(e.hp)
            buf.writeInt(e.status.id)


        })
    }

    override def processPacket(handler: Nothing): Unit = {

    }
}