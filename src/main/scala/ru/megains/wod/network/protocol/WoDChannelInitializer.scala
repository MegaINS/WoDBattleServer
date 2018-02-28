package ru.megains.wod.network.protocol

import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.channel.{ChannelInitializer, ChannelOption}
import ru.megains.wod.BattleServer
import ru.megains.wod.network.{NetworkManager, NetworkManagerServer}
import ru.megains.wod.network.handler.NetHandlerHandshakeTCP

class WoDChannelInitializer(server:BattleServer,isServer:Boolean) extends ChannelInitializer[NioSocketChannel]{


    override def initChannel(ch: NioSocketChannel): Unit = {
        if(isServer){
            val networkManager = new NetworkManagerServer(server)
            ch.pipeline()
                    .addLast("serverCodec", new WoDServerCodec)
                    .addLast("messageCodec", new WoDMessageCodec)
                    .addLast("packetHandler", networkManager)
            ch.config.setOption(ChannelOption.TCP_NODELAY, Boolean.box(true))

        }else{
            val networkManager = new NetworkManager(server)
            ch.pipeline()
                    .addLast("serverCodec", new WoDServerCodec)
                    .addLast("messageCodec", new WoDMessageCodec)
                    .addLast("packetHandler", networkManager)
            ch.config.setOption(ChannelOption.TCP_NODELAY, Boolean.box(true))

            networkManager.setNetHandler(new NetHandlerHandshakeTCP(server, networkManager))
        }

    }
}
