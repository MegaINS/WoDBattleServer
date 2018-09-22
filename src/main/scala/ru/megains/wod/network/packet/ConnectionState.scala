package ru.megains.wod.network.packet

import ru.megains.wod.network.packet.battle.{CHandshake=>CH, PBattleStatus, PStartBattle}
import ru.megains.wod.network.packet.battleplayer._

import scala.collection.mutable


sealed abstract class ConnectionState(val name: String, val id: Int) {


    val serverIdPacket = new mutable.HashMap[ Class[_ <: Packet[_]],Int]()
    val clientPacketId = new mutable.HashMap[Int,Class[_ <: PacketRead[_]]]()


    def registerServerPacket(packet: Class[_ <: Packet[_]]): Unit = {
        serverIdPacket +=  packet -> serverIdPacket.size
    }
    def registerClientPacket(packet: Class[_ <: PacketRead[_]]): Unit = {
        clientPacketId += clientPacketId.size -> packet
    }
    def getServerPacketId(packet: Class[_ <: Packet[_]]): Int = {
        serverIdPacket(packet)
    }


    def getClientPacket(id: Int): PacketRead[_] = {
        val packet = clientPacketId(id)
        if (packet ne null) packet.newInstance() else null.asInstanceOf[PacketRead[_]]
    }

}


object ConnectionState {



    val STATES_BY_CLASS = new mutable.HashMap[Class[_ <: Packet[_]], ConnectionState]()

    def getFromId(id: Int): ConnectionState = {
        STATES_BY_ID(id)
    }


    def getFromPacket(inPacket: Packet[_]): ConnectionState = {
        STATES_BY_CLASS(inPacket.getClass)
    }


    case object HANDSHAKING extends ConnectionState("HANDSHAKING", 0) {
      //  registerServerPacket(classOf[SPacketDisconnect])
        registerServerPacket(classOf[CHandshake])
        registerClientPacket(classOf[CH])
    }


    case object STATUS extends ConnectionState("STATUS", 1) {

    }

    case object LOGIN extends ConnectionState("LOGIN", 2) {


    }


    case object PLAY extends ConnectionState("PLAY", 3) {



    }
    case object BATTLE_SERVER extends ConnectionState("BATTLE_SERVER", 4) {


        registerClientPacket(classOf[PStartBattle])
        registerServerPacket(classOf[PBattleStatus])

    }
    case object BATTLE extends ConnectionState("BATTLE", 5) {

        registerClientPacket(classOf[CPacketConnectBattle])
        registerClientPacket(classOf[CPacketAttack])


       // registerServerPacket(classOf[CPacketConnectBattle])
        registerServerPacket(classOf[SPacketBattleInfo])
        registerServerPacket(classOf[SPacketLoadEntity])
        registerServerPacket(classOf[SPacketBattleStatus])
        registerServerPacket(classOf[SPacketBattleDamage])
        registerServerPacket(classOf[SPacketBattleTarget])
        registerServerPacket(classOf[SPacketBattleEnd])

    }

    val STATES_BY_ID = Array(HANDSHAKING, STATUS, LOGIN, PLAY,BATTLE_SERVER,BATTLE)
    addClass(HANDSHAKING)
    addClass(STATUS)
    addClass(LOGIN)
    addClass(PLAY)
    addClass(BATTLE_SERVER)
    addClass(BATTLE)
    def addClass(state: ConnectionState): Unit = {
        state.clientPacketId.values.foreach(packet =>
            STATES_BY_CLASS += packet -> state)

        state.serverIdPacket.keySet.foreach(packet =>
            STATES_BY_CLASS += packet -> state)
    }


}
