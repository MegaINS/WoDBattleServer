package ru.megains.wod.network.packet.battleplayer

import ru.megains.wod.network.packet.{PacketBuffer, PacketWrite}

class SPacketBattleInfo(battleId:Int,team:Int) extends PacketWrite{




    override def writePacketData(buf: PacketBuffer): Unit = {
        buf.writeInt(battleId)
        buf.writeInt(team)
    }


}
