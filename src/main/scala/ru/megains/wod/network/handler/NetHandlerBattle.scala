package ru.megains.wod.network.handler

import ru.megains.wod.entity.player.{Player, Players}
import ru.megains.wod.network.NetworkManager
import ru.megains.wod.network.packet.Packet
import ru.megains.wod.network.packet.battleplayer._
import ru.megains.wod.{BattleServer, Logger}

class NetHandlerBattle(server: BattleServer, networkManager: NetworkManager) extends INetHandler with Logger[NetHandlerBattle]{



    var player:Player =_

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

    def processConnectBattle(packet: CPacketConnectBattle): Unit = {
        player = Players.getPlayer(packet.id)
        player.connection = this
        player.start()
        player.sendPacket(new SPacketBattleInfo(player.battleId,player.team))
        player.sendPacket(new SPacketLoadEntity(player.battle.teams))
    }

    def processAttack(packet: CPacketAttack): Unit = {
        player.attackTarget(packet.id)
    }
    def processSlotUse(packetIn: SPacketSlotUse): Unit = {
        player.slots.use(packetIn.slot)
    }
}
