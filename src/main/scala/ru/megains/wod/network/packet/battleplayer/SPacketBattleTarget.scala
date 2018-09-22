package ru.megains.wod.network.packet.battleplayer

import ru.megains.wod.network.packet.{PacketBuffer, PacketWrite}

class SPacketBattleTarget(id:Int) extends PacketWrite{





    override def writePacketData(buf: PacketBuffer): Unit = {
        buf.writeInt(id)
    }


}


