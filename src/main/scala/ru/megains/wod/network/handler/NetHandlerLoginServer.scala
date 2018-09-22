package ru.megains.wod.network.handler

import ru.megains.wod.BattleServer
import ru.megains.wod.network.NetworkManager

class NetHandlerLoginServer(server: BattleServer, networkManager: NetworkManager) extends INetHandler{

    var name: String = _
   // val db = WoDDatabase.db

//    override def processLoginStart(packetIn: CPacketLoginStart): Unit = {
//        val playerEmail:String =  packetIn.email
//        val playerPassword:String = packetIn.pass
//        db.withConnection(implicit c=>
//            SQL(s"SELECT * FROM players_auth WHERE email='$playerEmail'").as(Parsers.playerAuth.singleOpt).getOrElse(default = (0,"","")) match {
//                case (id,mail,password) =>
//                    if (playerEmail == mail && playerPassword == password){
//                        networkManager.sendPacket(new SPacketLoginSuccess())
//                        server.playerList.initializeConnectionToPlayer(networkManager, id)
//                    }else{
//
//                    }
//            }
//
//        )
//    }


    override def onDisconnect(msg: String): Unit = {

    }

    override def disconnect(msg: String): Unit = {

    }
}


