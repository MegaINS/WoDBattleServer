package ru.megains.wod.network.packet

class CHandshake() extends PacketWrite {

    var connectionState: ConnectionState = _

    override def isImportant: Boolean = true

    def this(version: Int, ip: String, port: Int, connectionState: ConnectionState) {
        this()
        this.connectionState = connectionState
    }



    override def writePacketData(packetBuffer: PacketBuffer): Unit = {
        packetBuffer.writeByte(connectionState.id)
    }


}
