package ru.megains.wod.network.handler

import ru.megains.wod.BattleServer
import ru.megains.wod.network.NetworkManager
import ru.megains.wod.network.packet.CHandshake
import ru.megains.wod.network.packet.ConnectionState.{BATTLE, LOGIN}

class NetHandlerHandshakeTCP(server: BattleServer, networkManager: NetworkManager) extends INetHandler {


    def processHandshake(packetIn: CHandshake): Unit = {

        packetIn.connectionState match {
            case LOGIN =>
                networkManager.setConnectionState(LOGIN)
                networkManager.setNetHandler(new NetHandlerLoginServer(server, networkManager))

            case BATTLE =>
                networkManager.setConnectionState(BATTLE)
                networkManager.setNetHandler(new NetHandlerBattle(server, networkManager))

            case _ =>
                throw new UnsupportedOperationException("Invalid intention " + packetIn.connectionState)
        }
    }

    override def onDisconnect(msg: String): Unit = {

    }

    override def disconnect(msg: String): Unit = {

    }
}
