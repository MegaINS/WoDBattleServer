package ru.megains.wod.network.packet.battle

import ru.megains.wod.network.packet.{PacketBuffer, PacketWrite}

class PBattleStatus(id:Int,status:Int) extends PacketWrite{



    override def writePacketData(buf: PacketBuffer): Unit = {
        buf.writeInt(id)
        buf.writeByte(status)
    }

}
