package ru.megains.wod.network.packet.battleplayer

import ru.megains.wod.network.packet.{PacketBuffer, PacketWrite}

class SPacketBattleEnd(win:Boolean) extends PacketWrite{




    override def writePacketData(buf: PacketBuffer): Unit = {
        buf.writeBoolean(win)

    }


}
