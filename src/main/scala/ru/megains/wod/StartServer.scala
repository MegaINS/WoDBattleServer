package ru.megains.wod

import ru.megains.wod.entity.mob.Mobs

object StartServer extends App{


    Mobs.load()
    val server = new BattleServer()
    server.run()


}
