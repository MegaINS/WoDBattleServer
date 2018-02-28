package ru.megains.wod

import java.net.InetAddress

import ru.megains.wod.network.{NetworkManager, NetworkManagerServer, NetworkSystem}

class BattleServer {


    var port = 8080
    var host:String = "localhost"
    var isActive = true
    var inetaddress: InetAddress = InetAddress.getByName(host)
    var networkSystem:NetworkSystem = _
    var networkBaseServer: NetworkManagerServer = _
    val gameLogicHandler = new GameLogicHandler(this)



    def run(): Unit ={
        networkSystem = new NetworkSystem(this)
        networkSystem.startLan(inetaddress,11000)
        NetworkManager.createNetworkManagerAndConnect(inetaddress,port,this)

        while (isActive){

            Thread.sleep(1)





        }
    }



}
