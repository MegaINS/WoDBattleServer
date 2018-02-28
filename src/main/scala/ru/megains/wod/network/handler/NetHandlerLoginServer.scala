package ru.megains.wod.network.handler

import ru.megains.wod.BattleServer
import ru.megains.wod.network.NetworkManager

class NetHandlerLoginServer(server: BattleServer, networkManager: NetworkManager) extends INetHandler{

    var name: String = _
   // val db = WoDDatabase.db

//    override def processLoginStart(packetIn: CPacketLoginStart): Unit = {
//        val userEmail:String =  packetIn.email
//        val userPassword:String = packetIn.pass
//        db.withConnection(implicit c=>
//            SQL(s"SELECT * FROM users_auth WHERE email='$userEmail'").as(Parsers.userAuth.singleOpt).getOrElse(default = (0,"","")) match {
//                case (id,mail,password) =>
//                    if (userEmail == mail && userPassword == password){
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


