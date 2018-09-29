package ru.megains.wod.network.packet.battleplayer

import ru.megains.wod.TypeAttack.TypeAttack
import ru.megains.wod.entity.Entity
import ru.megains.wod.network.packet.{PacketBuffer, PacketWrite}

class SPacketBattleDamage(entity:Entity,typeAttack: TypeAttack, hit:Int) extends PacketWrite{



    override def writePacketData(buf: PacketBuffer): Unit = {
        buf.writeInt(entity.battleId)
        buf.writeByte(entity.team)
        buf.writeByte(typeAttack.id)
        buf.writeInt(hit)

    }


}
