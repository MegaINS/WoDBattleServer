package ru.megains.wod

import io.netty.channel.{ChannelHandlerContext, SimpleChannelInboundHandler}
import io.netty.handler.codec.http.websocketx.WebSocketFrame


class TextFrameHandler extends SimpleChannelInboundHandler[WebSocketFrame] {


    override def messageReceived(ctx: ChannelHandlerContext, msg: WebSocketFrame): Unit = {

        println(msg)

       // val request: String = msg.text()
     //   println(request)
      //  ctx.channel.writeAndFlush(new TextWebSocketFrame (request.toUpperCase) )
    }
}
