package ru.megains.wod.network

import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.locks.ReentrantReadWriteLock

import io.netty.channel._
import ru.megains.wod.network.handler.{INetHandler, NetHandlerBattleServer}
import ru.megains.wod.network.packet.{CHandshake, ConnectionState, Packet, PacketRead}
import ru.megains.wod.{BattleServer, Logger}


class NetworkManagerServer(server:BattleServer) extends SimpleChannelInboundHandler[PacketRead[INetHandler]] with Logger[NetworkManagerServer] {


    var channel: Channel = _
    var packetListener: INetHandler = _
    var disconnected = false

    private val readWriteLock: ReentrantReadWriteLock = new ReentrantReadWriteLock
    private val outboundPacketsQueue: ConcurrentLinkedQueue[Packet[_ <: INetHandler]] = new ConcurrentLinkedQueue[Packet[_ <: INetHandler]]


    override def channelActive(ctx: ChannelHandlerContext): Unit = {
        super.channelActive(ctx)
        channel = ctx.channel()
        setConnectionState(ConnectionState.HANDSHAKING)
        sendPacket(new CHandshake(0,"",0,ConnectionState.BATTLE_SERVER))
        setConnectionState(ConnectionState.BATTLE_SERVER)
        setNetHandler(new NetHandlerBattleServer(server,this))
        log.info("Connect Base server")
    }

    override def exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable): Unit = {
        log.info("exceptionCaught", cause)
        closeChannel("exceptionCaught")
    }

    override def channelInactive(ctx: ChannelHandlerContext): Unit = {
        closeChannel("channelInactive")
    }


    def setConnectionState(connectionState: ConnectionState) {

        channel.attr(NetworkManager.PROTOCOL_ATTRIBUTE_KEY).set(connectionState)
        channel.config.setAutoRead(true)

    }

    def setNetHandler(handler: INetHandler) {
        log.debug("Set listener of {} to {}", Array[AnyRef](this, handler))
        packetListener = handler
    }


    def sendPacket(packetIn: Packet[_ <: INetHandler]) {
        if (isChannelOpen) {
            flushOutboundQueue()
            dispatchPacket(packetIn)
        } else {
            readWriteLock.writeLock.lock()
            try {
                outboundPacketsQueue.add(packetIn)
            } finally readWriteLock.writeLock.unlock()
        }

    }

    def isChannelOpen: Boolean = channel != null && channel.isOpen

    private def flushOutboundQueue() {
        if (isChannelOpen) {
            readWriteLock.readLock.lock()
            try {
                while (!outboundPacketsQueue.isEmpty) {
                    val packet: Packet[_] = outboundPacketsQueue.poll
                    dispatchPacket(packet)
                }
            }
            finally readWriteLock.readLock.unlock()
        }
    }

    private def dispatchPacket(inPacket: Packet[_]) {
        val enumconnectionstate: ConnectionState = ConnectionState.getFromPacket(inPacket)
        val enumconnectionstate1: ConnectionState = channel.attr(NetworkManager.PROTOCOL_ATTRIBUTE_KEY).get
        if (enumconnectionstate1 ne enumconnectionstate) {
            log.debug("Disabled auto read")
            channel.config.setAutoRead(false)
        }
        if (channel.eventLoop.inEventLoop) {
            if (enumconnectionstate ne enumconnectionstate1) this.setConnectionState(enumconnectionstate)
            val channelfuture: ChannelFuture = this.channel.writeAndFlush(inPacket)
            channelfuture.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE)
        }
        else {

            channel.eventLoop.execute(new Runnable() {
                def run() {
                    if (enumconnectionstate ne enumconnectionstate1) setConnectionState(enumconnectionstate)
                    val channelfuture1: ChannelFuture = channel.writeAndFlush(inPacket)

                    channelfuture1.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE)
                }
            })
        }

    }

    def closeChannel(error: String): Unit = {
        if (channel.isOpen) {
            channel.close().awaitUninterruptibly
            println(error)
        }
    }


    override def messageReceived(ctx: ChannelHandlerContext, packet: PacketRead[INetHandler]): Unit = {


        if (channel.isOpen) {
            if (packet.isImportant) {
                packet.processPacket(packetListener)
            } else {
                server.gameLogicHandler.addPacketToProcess(() => {
                    packet.processPacket(packetListener)
                })
            }
        }
    }
    def processReceivedPackets() {
        flushOutboundQueue()




        channel.flush
    }

    def hasNoChannel: Boolean = channel == null


    def checkDisconnected() {
        if (channel != null && !channel.isOpen) if (disconnected) log.warn("handleDisconnection() called twice")
        else {
            disconnected = true
            //  if (getExitMessage != null) getNetHandler.onDisconnect(getExitMessage)
            // else
            if (packetListener != null) packetListener.onDisconnect("Disconnected")
        }
    }

}

