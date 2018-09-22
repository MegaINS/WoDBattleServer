package ru.megains.wod.entity.player

import ru.megains.wod.battle.TurnStatus
import ru.megains.wod.entity.{Entity, Status}
import ru.megains.wod.network.handler.INetHandler
import ru.megains.wod.network.packet.Packet

class Player( val id:Int) extends Entity {


    var connection:INetHandler = _

    var money:Int = 0
    var exp:Int = 0
    var timeDisconnect = 0
    var timeStart:Long = 0
    var timeDisconnectConst = 20




    def load(info: PlayerInfo): Player ={
        hp = 200
        name = "!!!!!!"//info.name
        level = info.levelIn
        money = info.moneyIn
        exp = info.expIn
       // location = Locations.getLocation(info.locationIn)
      //  backpack.load()
      //  body.load()
        timeStart = System.currentTimeMillis()
        this
    }

//    def sendData(): Unit ={
//        sendPacket(new SPacketPlayerInfo(this))
//        sendPacket(new SPacketLocInfo(location))
//        sendPacket(new SPacketInventory(InventoryType.backpack, backpack))
//        sendPacket(new SPacketBody(body))
//    }



    def setConnection(connectionIn: INetHandler): Unit = {
        if(connection ne null){
            connection.disconnect("reLogin")
        }
        connection = connectionIn
    }
   override def sendPacket(packetIn: Packet[_ <: INetHandler]): Unit = {
       if(connection!= null){
           connection.sendPacket(packetIn)
       }

    }

    override def update(): Unit = {

        status match {
            case Status.await=>
                if (timeDisconnect < timeDisconnectConst) {
                    if (System.currentTimeMillis - timeStart > 1000) {
                        timeDisconnect += 1
                        timeStart += 1000
                    }
                } else if (timeDisconnect >= timeDisconnectConst) {
                    battle.killEntity(this)
                }
            case Status.life =>
                turnStatus match {
                    case TurnStatus.noTarget =>
                        battle.targetSearch(this, team)

                    case TurnStatus.myTurn =>
                        if (roundsTime <= 0) {
                            missedRounds += 1
                            if (missedRounds >= 3) {
                                log.info(name + "= died")
                                battle.killEntity(this)
                                battle.addTarget(target)
                                //  target.getBattleUser()
                                target = null
                                // getBattleUser()
                                return
                            }
                            passRun()
                        }
                        else {
                            if (System.currentTimeMillis - roundsStartTime > 1000) {
                                roundsTime -= 1
                                roundsStartTime += 1000
                            }
                            // getStatus();
                        }
                    case _ =>
                }


            case Status.end =>


        }
    }
}
