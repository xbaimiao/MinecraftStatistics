package com.xbaimiao.minecraft.statistics

import com.xbaimiao.minecraft.statistics.manager.Server
import studio.trc.minecraft.serverpinglib.API.MCServerSocket
import studio.trc.minecraft.serverpinglib.Protocol.ProtocolVersion

/**
 * @author 小白
 * @date 2023/4/2 20:22
 **/

fun main() {
    println(Server("GTE", "play.gtemc.cn", 25565, "GTE", 0.0).toJson())

    val socket = MCServerSocket.getInstance("qiyunlou.cc", 25565)
    println(socket.ip + ":" + socket.port)
    val online = socket.getStatus(ProtocolVersion.v1_12_2)
}