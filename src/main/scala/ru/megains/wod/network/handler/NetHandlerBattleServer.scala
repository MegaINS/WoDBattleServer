package ru.megains.wod.network.handler

import ru.megains.wod.battle.BattleList
import ru.megains.wod.entity.mob.Mobs
import ru.megains.wod.entity.player.Players
import ru.megains.wod.entity.{Entity, EntityType}
import ru.megains.wod.network.NetworkManagerServer
import ru.megains.wod.network.packet.Packet
import ru.megains.wod.network.packet.battle.{PBattleStatus, PStartBattle}
import ru.megains.wod.{BattleServer, Logger}

class NetHandlerBattleServer(server: BattleServer, networkManager: NetworkManagerServer) extends INetHandler with Logger[NetHandlerBattleServer]{



    def startNewBattle(packet: PStartBattle): Unit = {
        var entity1: Entity = null
        var entity2: Entity = null
        packet.et2 match {
            case EntityType.mob =>
                entity2 =  Mobs.getMob(packet.id2)
            case EntityType.player =>
                entity2 =  Players.getPlayer(packet.id2)
        }
        packet.et1 match {
            case EntityType.mob =>
                entity1 =  Mobs.getMob(packet.id1)
            case EntityType.player =>
                entity1 =  Players.getPlayer(packet.id1)
        }
        BattleList.createBattle(packet.id,entity1,entity2)
        sendPacket(new PBattleStatus(packet.id,1))

    }


    override def sendPacket(packetIn: Packet[_ <: INetHandler]) {

        try
            networkManager.sendPacket(packetIn)

        catch {
            case throwable: Throwable =>
                log.error("sendPacket", throwable)
        }
    }




    override def onDisconnect(msg: String): Unit = {

    }

    override def disconnect(msg: String): Unit = {

    }
}
