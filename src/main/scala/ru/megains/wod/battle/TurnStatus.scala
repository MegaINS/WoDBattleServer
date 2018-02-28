package ru.megains.wod.battle

object TurnStatus extends Enumeration{
    type TurnStatus = Value
    val myTurn,targetTurn,noTarget,dead = Value
}
